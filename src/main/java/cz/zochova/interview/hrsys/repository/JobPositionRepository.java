package cz.zochova.interview.hrsys.repository;

import cz.zochova.interview.hrsys.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
}
