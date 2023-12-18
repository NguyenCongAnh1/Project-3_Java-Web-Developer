package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet petResult = petService.savePet(petDTO);
        PetDTO petDTOResponse = objectMapper.convertValue(petResult, PetDTO.class);
        petDTOResponse.setOwnerId(petResult.getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        PetDTO petDTOResponse =  objectMapper.convertValue(pet, PetDTO.class);
        petDTOResponse.setOwnerId(pet.getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> listPetDTO = new ArrayList<>();
        for(Pet pet: petService.getAllPets()){
            PetDTO petDTO = objectMapper.convertValue(pet, PetDTO.class);
            petDTO.setOwnerId(pet.getOwner().getId());
            listPetDTO.add(petDTO);
        }
        return listPetDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> listPetDTO = new ArrayList<>();
        for(Pet pet: petService.getAllPetsByOwnerId(ownerId)){
            PetDTO petDTO = objectMapper.convertValue(pet, PetDTO.class);
            petDTO.setOwnerId(pet.getOwner().getId());
            listPetDTO.add(petDTO);
        }
        return listPetDTO;
    }
}
