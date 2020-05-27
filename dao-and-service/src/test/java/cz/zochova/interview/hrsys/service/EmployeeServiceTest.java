package cz.zochova.interview.hrsys.service;

import cz.zochova.interview.hrsys.dto.EmployeeDTO;
import cz.zochova.interview.hrsys.dto.JobPositionDTO;
import cz.zochova.interview.hrsys.model.Employee;
import cz.zochova.interview.hrsys.model.JobPosition;
import cz.zochova.interview.hrsys.repository.EmployeeRepository;
import cz.zochova.interview.hrsys.service.exception.EmployeeNotSavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> expectedEmployees;
    private Employee expectedEmployeeOne;
    private Employee expectedEmployeeTwo;
    private Employee expectedEmployeeThree;
    private JobPosition analyst;

    @BeforeEach
    public void init(){
        employeeService = new DefaultEmployeeService(employeeRepository);
        MockitoAnnotations.initMocks(this);

        prepareDataForTest();
    }

    @Test
    public void getAll() {
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        List<EmployeeDTO> actualEmployeeDTOs = employeeService.getAll();

        assertEquals(expectedEmployees.size(), actualEmployeeDTOs.size());
        for (int i = 0; i < expectedEmployees.size(); i++) {
            compareEmployeeToEmployeeDTO(expectedEmployees.get(i), actualEmployeeDTOs.get(i));
        }
    }

    @Test
    public void findByLastName() {
        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(expectedEmployeeOne);
        expectedList.add(expectedEmployeeThree);
        final String lastNameToBeSearched;
        if (expectedEmployeeOne.getLastName().equals(expectedEmployeeThree.getLastName())) {
            lastNameToBeSearched = expectedEmployeeOne.getLastName();
        } else {
            throw new AssertionError("Preparation data last name for test do not match.");
        }

        when(employeeRepository.findByLastName(lastNameToBeSearched)).thenReturn(expectedList);

        List<EmployeeDTO> actualEmployeeDTOs = employeeService.findByLastName(lastNameToBeSearched);

        assertEquals(expectedList.size(), actualEmployeeDTOs.size());
        for (int i = 0; i < expectedList.size(); i++) {
            compareEmployeeToEmployeeDTO(expectedList.get(i), actualEmployeeDTOs.get(i));
        }
    }

    @Test
    public void findByLastNameThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> employeeService.findByLastName(null));
    }

    @Test
    public void add() throws EmployeeNotSavedException {
        Employee employeeToBeAdded = new Employee(583798L, "Dominika", "Pelcova", 25, analyst);
        EmployeeDTO employeeDTOToBeAdded = new EmployeeDTO(employeeToBeAdded.getEmployeeId(), employeeToBeAdded.getFirstName(),
                employeeToBeAdded.getLastName(), employeeToBeAdded.getAge(), new JobPositionDTO(employeeToBeAdded.getJobPosition().getPositionName()));
        List<Employee> expectedEmployeesUpdated = expectedEmployees;
        expectedEmployeesUpdated.add(employeeToBeAdded);

        when(employeeRepository.save(employeeToBeAdded)).thenReturn(employeeToBeAdded);
        when(employeeRepository.findAll()).thenReturn(expectedEmployeesUpdated);

        employeeService.add(employeeDTOToBeAdded);
        List<EmployeeDTO> actualEmployeeDTOsUpdated = employeeService.getAll();


        assertEquals(expectedEmployeesUpdated.size(), actualEmployeeDTOsUpdated.size());
        for (int i = 0; i < expectedEmployeesUpdated.size(); i++) {
            compareEmployeeToEmployeeDTO(expectedEmployeesUpdated.get(i), actualEmployeeDTOsUpdated.get(i));
        }
    }

    @Test
    public void addThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> employeeService.add(null));
    }

    @Test
    public void remove() {
        EmployeeDTO employeeToBeRemoved = new EmployeeDTO(expectedEmployeeTwo.getEmployeeId(), expectedEmployeeTwo.getFirstName(),
                expectedEmployeeTwo.getLastName(), expectedEmployeeTwo.getAge(), new JobPositionDTO(expectedEmployeeTwo.getJobPosition().getPositionName()));
        List<Employee> expectedEmployeesUpdated = expectedEmployees;
        expectedEmployeesUpdated.remove(expectedEmployeeTwo);

        when(employeeRepository.findById(expectedEmployeeTwo.getEmployeeId())).thenReturn(Optional.of(expectedEmployeeTwo));
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        employeeService.remove(employeeToBeRemoved);
        List<EmployeeDTO> actualEmployeeDTOsUpdated = employeeService.getAll();


        assertEquals(expectedEmployeesUpdated.size(), actualEmployeeDTOsUpdated.size());
        for (int i = 0; i < expectedEmployeesUpdated.size(); i++) {
            compareEmployeeToEmployeeDTO(expectedEmployeesUpdated.get(i), actualEmployeeDTOsUpdated.get(i));
        }
    }

    @Test
    public void removeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> employeeService.remove((Long) null));
    }

    @Test
    public void removeThrowsNoSuchElementException() {
        EmployeeDTO employeeDTONotPresentInDB = new EmployeeDTO(64357095L, "Jana", "Gajdova", 29, new JobPositionDTO("analyst"));

        assertThrows(NoSuchElementException.class,
                () -> employeeService.remove(employeeDTONotPresentInDB));
    }

    private static void compareEmployeeToEmployeeDTO(Employee employee, EmployeeDTO employeeDTO) {
        assertEquals(employee.getEmployeeId(), employeeDTO.getEmployeeId());
        assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
        assertEquals(employee.getLastName(), employeeDTO.getLastName());
        assertEquals(employee.getAge(), employeeDTO.getAge());
        assertEquals(employee.getJobPosition().getPositionName(), employeeDTO.getJobPosition().getPositionName());
    }

    private void prepareDataForTest() {
        JobPosition developer = new JobPosition();
        developer.setPositionName("java developer");
        JobPosition tester = new JobPosition();
        tester.setPositionName("tester");
        analyst = new JobPosition();
        analyst.setPositionName("Software analyst");

        expectedEmployeeOne = new Employee(123456L, "Karel", "Novak", 45, tester);
        expectedEmployeeTwo = new Employee(968796L, "Theodor", "Rumler", 30, developer);
        expectedEmployeeThree = new Employee(756987L, "Mariana", "Novak", 26, developer);

        expectedEmployees = new ArrayList<>();
        expectedEmployees.add(expectedEmployeeOne);
        expectedEmployees.add(expectedEmployeeTwo);
    }
}