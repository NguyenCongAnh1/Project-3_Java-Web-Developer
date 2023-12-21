package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.Data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByPets_id(Long petId);
    List<Schedule> findByEmployees_id(Long employeeId);

    @Query("SELECT DISTINCT s from Schedule s join s.pets p join p.owner c where c.id= :customerId")
    List<Schedule> findByCustomers_id(@Param("customerId") Long customerId);

}
