package cz.zochova.interview.hrsys.service;

import cz.zochova.interview.hrsys.dto.JobPositionDTO;

import java.util.List;

public interface JobPositionService {

    List<JobPositionDTO> getAll();
}
