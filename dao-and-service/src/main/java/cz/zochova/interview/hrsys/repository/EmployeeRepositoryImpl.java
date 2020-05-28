package cz.zochova.interview.hrsys.repository;

import cz.zochova.interview.hrsys.dto.EmployeeReportDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EmployeeReportDTO> getCountPerPositionTable() {
        return entityManager.createNativeQuery (
                "SELECT J.POSITION_NAME AS POSITION, COUNT(*) AS COUNT_OF_EMPLOYEES, AVG(E.AGE)  AS AVERAGE_AGE\n" +
                        "FROM EMPLOYEE E\n" +
                        "JOIN JOB_POSITION J ON E.JOB_POSITION_ID = J.JOB_POSITION_ID\n" +
                        "GROUP BY J.POSITION_NAME \n" +
                        "ORDER BY COUNT_OF_EMPLOYEES DESC", "EmployeeReportResult").getResultList();
    }
}
