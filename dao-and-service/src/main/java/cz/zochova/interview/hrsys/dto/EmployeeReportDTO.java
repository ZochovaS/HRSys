package cz.zochova.interview.hrsys.dto;

import java.io.Serializable;

public class EmployeeReportDTO implements Serializable {

    private String position;

    private int countOfEmployees;

    private int averageAge;

    public EmployeeReportDTO(String position, int countOfEmployees, int averageAge) {
        this.position = position;
        this.countOfEmployees = countOfEmployees;
        this.averageAge = averageAge;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCountOfEmployees() {
        return countOfEmployees;
    }

    public void setCountOfEmployees(int countOfEmployees) {
        this.countOfEmployees = countOfEmployees;
    }

    public int getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(int averageAge) {
        this.averageAge = averageAge;
    }

    @Override
    public String toString() {
        return "EmployeeReport{" +
                "positionName='" + position + '\'' +
                ", countOfEmployees=" + countOfEmployees +
                ", averageAge=" + averageAge +
                '}';
    }
}
