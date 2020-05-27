package cz.zochova.interview.hrsys.service;

import cz.zochova.interview.hrsys.dto.EmployeeDTO;
import cz.zochova.interview.hrsys.model.Employee;
import cz.zochova.interview.hrsys.service.exception.EmployeeNotSavedException;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAll();

    List<EmployeeDTO> findByLastName(String lastNamePart);

    Employee add(EmployeeDTO employeeDTO) throws EmployeeNotSavedException;

    void remove(EmployeeDTO employeeDTO);
}
