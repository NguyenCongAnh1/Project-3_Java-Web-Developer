package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Customer;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ObjectMapper objectMapper;


    public Pet savePet(PetDTO petDTO) {
        Customer owner = customerRepository.findById(petDTO.getOwnerId()).orElseThrow(() -> {
            return null;
        });
        Pet pet = objectMapper.convertValue(petDTO, Pet.class);
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public Customer getOwnerByPet(Long pedId) {
        Pet foundPet = petRepository.findById(pedId).orElseThrow(() -> {
            return null;
        });
        return foundPet.getOwner();
    }

    public Pet findPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(() -> {
            return null;
        });

    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

}
