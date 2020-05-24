package cz.zochova.interview.hrsys.dto;

public class EmployeeDTO {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private int age;

    private JobPositionDTO jobPosition;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long employeeId, String firstName, String lastName, int age, JobPositionDTO jobPosition) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.jobPosition = jobPosition;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public JobPositionDTO getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPositionDTO jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", jobPosition=" + jobPosition +
                '}';
    }
}
