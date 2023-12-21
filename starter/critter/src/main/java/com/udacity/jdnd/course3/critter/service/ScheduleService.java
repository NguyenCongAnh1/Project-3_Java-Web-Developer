package com.udacity.jdnd.course3.critter.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Employee;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.Data.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.checkerframework.checker.nullness.Opt.orElseThrow;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    public ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO result = objectMapper.convertValue(schedule, ScheduleDTO.class);
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        result.setEmployeeIds(employeeIds);
        result.setPetIds(petIds);
        return result;
    }

    public Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = objectMapper.convertValue(scheduleDTO, Schedule.class);
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Employee employee =
                    employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee " +
                            "not found with ID: " + employeeId));
            employees.add(employee);
        }
        for (Long petId : scheduleDTO.getPetIds()) {
            Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Pet not found with" +
                    " ID: " + petId));
            pets.add(pet);
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = convertToSchedule(scheduleDTO);
        return convertToScheduleDTO(scheduleRepository.save(savedSchedule));

    }

    public List<ScheduleDTO> getAllSchedule() {
        return scheduleRepository.findAll().stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleDTOsByPetId(Long petId){

        return scheduleRepository.findByPets_id(petId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleDTOsByEmployeeId(Long employeeId){
        return scheduleRepository.findByEmployees_id(employeeId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleDTOSByCustomerId(Long customerId){
        return scheduleRepository.findByCustomers_id(customerId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }


}
