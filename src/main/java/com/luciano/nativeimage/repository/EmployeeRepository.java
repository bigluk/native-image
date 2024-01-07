package com.luciano.nativeimage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.luciano.nativeimage.entity.Employee;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    boolean existsEmployeeByIdentificationNumberAndDepartment(Long idNumber, String department);

}
