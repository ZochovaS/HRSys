package cz.zochova.interview.hrsys.model;

import cz.zochova.interview.hrsys.dto.EmployeeReportDTO;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@SqlResultSetMapping(name = "EmployeeReportResult", classes = {
        @ConstructorResult(targetClass = EmployeeReportDTO.class, columns = {
                @ColumnResult(name = "POSITION"),
                @ColumnResult(name = "COUNT_OF_EMPLOYEES", type = int.class),
                @ColumnResult(name = "AVERAGE_AGE", type = int.class)})})
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"FIRST_NAME", "LAST_NAME", "AGE"}))
public class Employee implements Serializable {
    private static final long serialVersionUID = 123533551001L;

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotEmpty(message = "Employee first name cannot be empty.")
    @Size(max = 100, message = "Employee first name is too long. Cannot exceed 100 chars.")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotEmpty(message = "Employee last name cannot be empty.")
    @Size(max = 100, message = "Employee last name is too long. Cannot exceed 100 chars.")
    @Column(name = "LAST_NAME")
    private String lastName;

    @Min(1)
    @Max(200)
    private int age;

    @ManyToOne
    @JoinColumn(name ="JOB_POSITION_ID")
    private JobPosition jobPosition;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, int age, JobPosition jobPosition) {
        this.employeeId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.jobPosition = jobPosition;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", jobPosition=" + jobPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getAge() == employee.getAge() &&
                getFirstName().equals(employee.getFirstName()) &&
                getLastName().equals(employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getAge());
    }
}
