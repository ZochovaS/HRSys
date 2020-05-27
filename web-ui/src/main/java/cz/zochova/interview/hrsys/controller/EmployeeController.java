package cz.zochova.interview.hrsys.controller;

import cz.zochova.interview.hrsys.dto.EmployeeDTO;
import cz.zochova.interview.hrsys.service.EmployeeService;
import cz.zochova.interview.hrsys.service.JobPositionService;
import cz.zochova.interview.hrsys.service.exception.EmployeeNotSavedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/HRsystem")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    private JobPositionService jobPositionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JobPositionService jobPositionService) {
        this.employeeService = employeeService;
        this.jobPositionService = jobPositionService;
        LOGGER.info("EmployeeController is instantiated");
        LOGGER.info("The application is running on: " + "http://localhost:8080/HRsystem");
    }

    @GetMapping
    public ModelAndView showListOfEmployees() {
        ModelAndView modelAndView = new ModelAndView("employees");
        modelAndView.addObject("employees", employeeService.getAll());
        return modelAndView;
    }

    @GetMapping("/employee-report")
    public ModelAndView showEmployeeReport() {
        ModelAndView modelAndView = new ModelAndView("employeeReport");
        modelAndView.addObject("employeeReportTable", employeeService.getCountPerPositionTable());
        return modelAndView;
    }

    @GetMapping("/employee-add")
    public ModelAndView showAddEmployee() {
        ModelAndView modelAndView = new ModelAndView("addEmployee");
        modelAndView.addObject("employee", new EmployeeDTO());
        modelAndView.addObject("jobPositions", jobPositionService.getAll());
        return modelAndView;
    }

    @PostMapping("/employee-add")
    public ModelAndView processAddEmployee(@Valid EmployeeDTO employee) {
        try {
            employeeService.add(employee);
        } catch (EmployeeNotSavedException e) {
            LOGGER.warn("Employee was not saved because of error: " + e.getMessage());
        }
        return new ModelAndView("redirect:/HRsystem");
    }

    @PostMapping(path = "", params = "delete_emp")
    public ModelAndView deleteEmployeeByButton(@RequestParam(name = "employeeId") Long employeeId) {
        employeeService.remove(employeeId);
        return new ModelAndView("redirect:/HRsystem");
    }

}