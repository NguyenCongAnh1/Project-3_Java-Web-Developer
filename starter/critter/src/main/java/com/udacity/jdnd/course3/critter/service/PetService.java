package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Customer;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;
    @Autowired
    ObjectMapper objectMapper;


    public Pet savePet(Pet pet, Long ownerId) {
        Customer owner = customerRepository.findById(ownerId).orElseThrow(() -> null);
        pet.setOwner(owner);
        Pet savedPet = petRepository.save(pet);
        owner.addPet(savedPet);
        customerRepository.save(owner);
        return savedPet;
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
