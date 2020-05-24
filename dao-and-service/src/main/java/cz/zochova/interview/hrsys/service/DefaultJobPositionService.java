package cz.zochova.interview.hrsys.service;

import cz.zochova.interview.hrsys.dto.JobPositionDTO;
import cz.zochova.interview.hrsys.model.JobPosition;
import cz.zochova.interview.hrsys.repository.JobPositionRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultJobPositionService implements JobPositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJobPositionService.class);

    private JobPositionRepository jobPositionRepository;

    private final MapperFacade mapper;

    @Autowired
    public DefaultJobPositionService(JobPositionRepository jobPositionRepository) {
        this.jobPositionRepository = jobPositionRepository;

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(JobPosition.class, JobPositionDTO.class);
        mapper = mapperFactory.getMapperFacade();
        LOGGER.info("DefaultJobPositionService is instantiated");
    }

    @Override
    public List<JobPositionDTO> getAll() {
        return mapper.mapAsList(jobPositionRepository.findAll(), JobPositionDTO.class);
    }
}
