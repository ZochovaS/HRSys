package cz.zochova.interview.hrsys.repository;

import cz.zochova.interview.hrsys.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where lower(e.lastName) like lower(?1) order by e.lastName asc")
    List<Employee> findByLastName(String lastNamePart);

}
