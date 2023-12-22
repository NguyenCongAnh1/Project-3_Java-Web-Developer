package com.udacity.jdnd.course3.critter.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Employee;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.Data.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = convertToSchedule(scheduleDTO);
        return  convertToScheduleDTO(scheduleService.createSchedule(savedSchedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedule().stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleDTOsByPetId(petId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleDTOsByEmployeeId(employeeId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleDTOSByCustomerId(customerId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
    }

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


}
