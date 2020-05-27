package cz.zochova.interview.hrsys.controller;

import cz.zochova.interview.hrsys.dto.EmployeeDTO;
import cz.zochova.interview.hrsys.dto.EmployeeReportDTO;
import cz.zochova.interview.hrsys.dto.JobPositionDTO;
import cz.zochova.interview.hrsys.model.Employee;
import cz.zochova.interview.hrsys.model.JobPosition;
import cz.zochova.interview.hrsys.service.EmployeeService;
import cz.zochova.interview.hrsys.service.JobPositionService;
import cz.zochova.interview.hrsys.service.exception.EmployeeNotSavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    private ModelAndView expectedModelAndView;
    private List<EmployeeDTO> expectedEmployees;
    private EmployeeDTO expectedEmployeeOne;
    private JobPositionDTO analyst;
    private EmployeeReportDTO expectedEmployeeReport;

    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private JobPositionService jobPositionService;

    @BeforeEach
    public void init(){
        employeeController = new EmployeeController(employeeService, jobPositionService);
        MockitoAnnotations.initMocks(this);

        expectedModelAndView = new ModelAndView();
        prepareDataForTest();
    }

    @Test
    public void showListOfEmployees() {
        final String modelAndViewName = "employees";

        when(employeeService.getAll()).thenReturn(expectedEmployees);
        expectedModelAndView.setViewName(modelAndViewName);
        expectedModelAndView.addObject("employees", expectedEmployees);

        final ModelAndView result = employeeController.showListOfEmployees();

        verify(employeeService).getAll();
        assertEquals(expectedModelAndView.getViewName(), modelAndViewName,
                "ModelAndView name was not equal with expected.");
        assertEquals(expectedModelAndView.getModel(), result.getModel(),
                "ModelAndView Model was not equal with expected.");
    }

    @Test
    public void showEmployeeReport() {
        final String modelAndViewName = "employeeReport";

        when(employeeService.getCountPerPositionTable()).thenReturn(Collections.singletonList(expectedEmployeeReport));
        expectedModelAndView.setViewName(modelAndViewName);
        expectedModelAndView.addObject("employeeReportTable", Collections.singletonList(expectedEmployeeReport));

        final ModelAndView result = employeeController.showEmployeeReport();

        verify(employeeService).getCountPerPositionTable();
        assertEquals(expectedModelAndView.getViewName(), modelAndViewName,
                "ModelAndView name was not equal with expected.");
        assertEquals(expectedModelAndView.getModel(), result.getModel(),
                "ModelAndView Model was not equal with expected.");
    }

    @Test
    public void showAddEmployee() throws EmployeeNotSavedException {
        final String modelAndViewName = "addEmployee";

        when(jobPositionService.getAll()).thenReturn(Collections.singletonList(analyst));
        expectedModelAndView.setViewName(modelAndViewName);
        expectedModelAndView.addObject("employee", new EmployeeDTO());
        expectedModelAndView.addObject("jobPositions ", Collections.singletonList(analyst));

        final ModelAndView actualResult = employeeController.showAddEmployee();

        verify(jobPositionService).getAll();
        assertEquals(expectedModelAndView.getViewName(), modelAndViewName,
                "ModelAndView name was not equal with expected.");
    }

    @Test
    public void processAddEmployee() throws EmployeeNotSavedException {
        final EmployeeDTO employeeToBeSaved = new EmployeeDTO(756987L, "Mariana", "Novak", 26, analyst);
        final Employee expectedEmployeeInDatabase = new Employee(employeeToBeSaved.getEmployeeId(), employeeToBeSaved.getFirstName(),
                employeeToBeSaved.getLastName(), employeeToBeSaved.getAge(), new JobPosition(employeeToBeSaved.getJobPosition().getPositionName()));
        final ModelAndView expectedResult = new ModelAndView("redirect:/HRsystem");

        final ModelAndView actualResult = employeeController.processAddEmployee(employeeToBeSaved);

        verify(employeeService).add(employeeToBeSaved);
        assertEquals(expectedResult.getView(), actualResult.getView(), "Result was not equal with expected.");
    }

    @Test
    void deleteEmployeeByButton() {
        final Long preparedId = expectedEmployeeOne.getEmployeeId();

        final ModelAndView actualResult = employeeController.deleteEmployeeByButton(preparedId);

        verify(employeeService).remove(preparedId);
    }

    private void prepareDataForTest(){
        JobPositionDTO tester = new JobPositionDTO();
        tester.setPositionName("tester");
        analyst = new JobPositionDTO();
        analyst.setPositionName("Software analyst");

        expectedEmployeeOne = new EmployeeDTO(123456L, "Karel", "Novak", 45, tester);

        expectedEmployees = new ArrayList<>();
        expectedEmployees.add(expectedEmployeeOne);

        expectedEmployeeReport = new EmployeeReportDTO("Java developer", 3, 50);
    }

}