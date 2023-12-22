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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.checkerframework.checker.nullness.Opt.orElseThrow;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);

    }

    public List<Schedule> getAllSchedule() {
//        return scheduleRepository.findAll().stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
        return scheduleRepository.findAll();
    }


    public List<Schedule> getScheduleDTOsByPetId(Long petId){

//        return scheduleRepository.findByPets_id(petId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
        return scheduleRepository.findByPets_id(petId);
    }

    public List<Schedule> getScheduleDTOsByEmployeeId(Long employeeId){
//        return scheduleRepository.findByEmployees_id(employeeId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
        return scheduleRepository.findByEmployees_id(employeeId);
    }


    public List<Schedule> getScheduleDTOSByCustomerId(Long customerId){
//        return scheduleRepository.findByCustomers_id(customerId).stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
        return scheduleRepository.findByCustomers_id(customerId);
    }



}
