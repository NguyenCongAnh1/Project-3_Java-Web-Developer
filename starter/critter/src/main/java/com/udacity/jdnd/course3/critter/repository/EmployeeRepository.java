package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.Data.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    @Query("SELECT DISTINCT e FROM Employee e JOIN e.skills skills WHERE skills IN :skills AND :day IN elements(e.daysAvailable)")
//    List<Employee> findEmployeeBySkillsAndAndDaysAvailable(@Param("skills") Set<EmployeeSkill> skills,
//                                                           @Param("day") DayOfWeek day);

    List<Employee> getAllBySkillsIn(Set<EmployeeSkill> skills);
}
