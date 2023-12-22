package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.Data.Customer;
import com.udacity.jdnd.course3.critter.Data.Employee;
import com.udacity.jdnd.course3.critter.Data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer c = customerService.saveCustomer(objectMapper.convertValue(customerDTO, Customer.class));
        return objectMapper.convertValue(c, CustomerDTO.class);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> listResult = new ArrayList<>();
        for (Customer customer : customerService.getAllCustomers()) {
            CustomerDTO cDTO = objectMapper.convertValue(customer, CustomerDTO.class);
            if (customer.getPetIds() != null) {
                List<Long> petIds = customer.getPetIds().stream().map(Pet::getId).collect(Collectors.toList());
                cDTO.setPetIds(petIds);
            }

            listResult.add(cDTO);
        }
        return listResult;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = petService.getOwnerByPet(petId);
        CustomerDTO customerDTO = objectMapper.convertValue(customer, CustomerDTO.class);
        if (customer.getPetIds() != null) {
            List<Long> petIds = customer.getPetIds().stream().map(Pet::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee e = employeeService.saveEmployee(objectMapper.convertValue(employeeDTO, Employee.class));
        return objectMapper.convertValue(e, EmployeeDTO.class);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return objectMapper.convertValue(employeeService.getEmployee(employeeId), EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Exception {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<EmployeeDTO> listResult = new ArrayList<>();
        for (Employee employee : employeeService.findEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills())) {
            EmployeeDTO eDTO = objectMapper.convertValue(employee, EmployeeDTO.class);
            listResult.add(eDTO);
        }
        return listResult;

    }

}
