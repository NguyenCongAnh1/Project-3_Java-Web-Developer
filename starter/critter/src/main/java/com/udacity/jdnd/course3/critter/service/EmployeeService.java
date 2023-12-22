package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.Data.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee e) {
        return employeeRepository.save(e);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) throws Exception {
        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("Id not Found"));
        foundEmployee.setDaysAvailable(daysAvailable);
        employeeRepository.save(foundEmployee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    return null;
                });
    }

    public List<Employee> findEmployeesForService(LocalDate localDate, Set<EmployeeSkill> skills){
        DayOfWeek day = localDate.getDayOfWeek();
        return employeeRepository.findEmployeeBySkillsAndAndDaysAvailable(skills, day);
    }


}
