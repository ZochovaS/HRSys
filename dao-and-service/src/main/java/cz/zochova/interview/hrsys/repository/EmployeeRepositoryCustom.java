package cz.zochova.interview.hrsys.repository;

import cz.zochova.interview.hrsys.dto.EmployeeReportDTO;

import java.util.List;

public interface EmployeeRepositoryCustom {

    List<EmployeeReportDTO> getCountPerPositionTable();
}
