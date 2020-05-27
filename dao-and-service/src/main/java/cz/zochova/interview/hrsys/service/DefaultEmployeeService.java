package cz.zochova.interview.hrsys.service;

import cz.zochova.interview.hrsys.dto.EmployeeDTO;
import cz.zochova.interview.hrsys.dto.JobPositionDTO;
import cz.zochova.interview.hrsys.model.Employee;
import cz.zochova.interview.hrsys.model.JobPosition;
import cz.zochova.interview.hrsys.repository.EmployeeRepository;
import cz.zochova.interview.hrsys.service.exception.EmployeeNotSavedException;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DefaultEmployeeService implements EmployeeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmployeeService.class);

    private final MapperFacade mapper;

    private EmployeeRepository employeeRepository;

    @Autowired
    public DefaultEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Employee.class, EmployeeDTO.class);
        mapperFactory.classMap(JobPosition.class, JobPositionDTO.class);
        mapper = mapperFactory.getMapperFacade();
    }

    @Override
    public List<EmployeeDTO> getAll(){
        return mapper.mapAsList(employeeRepository.findAll(), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findByLastName(String lastNamePart) {
        if (lastNamePart == null) {
            throw new IllegalArgumentException("Last name to be searched cannot be null");
        }
        List<Employee> searchResult = employeeRepository.findByLastName(lastNamePart);

        if (searchResult.isEmpty()) {
            return Collections.emptyList();
        } else {
            return mapper.mapAsList(searchResult, EmployeeDTO.class);
        }
    }

    @Transactional
    @Override
    public Employee add(EmployeeDTO employeeDTO) throws EmployeeNotSavedException {
        if (employeeDTO == null) {
            throw new IllegalArgumentException("Employee to save cannot be null.");
        }
        Employee employee = mapper.map(employeeDTO, Employee.class);
        try {
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e){
            LOGGER.warn(String.format("Employee %s cannot be saved as it violates data integrity", employee.toString()));
            throw new EmployeeNotSavedException("Employee was not saved.");
        }
    }

    @Transactional
    @Override
    public void remove(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            throw new IllegalArgumentException("Employee to remove cannot be null.");
        }
        Employee employee = mapper.map(employeeDTO, Employee.class);
        Employee employeeInPersistenceContext = employeeRepository.findById(employee.getEmployeeId())
                .orElseThrow(() -> new NoSuchElementException("Employee to delete does not exist in database."));
        employeeRepository.delete(employeeInPersistenceContext);
    }
}
