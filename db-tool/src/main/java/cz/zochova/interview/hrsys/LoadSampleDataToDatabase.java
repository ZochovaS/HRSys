package cz.zochova.interview.hrsys;

import cz.zochova.interview.hrsys.model.Employee;
import cz.zochova.interview.hrsys.model.JobPosition;
import cz.zochova.interview.hrsys.repository.EmployeeRepository;
import cz.zochova.interview.hrsys.repository.JobPositionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class LoadSampleDataToDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadSampleDataToDatabase.class);

    private static final String [] sampleFirstNames = new String[] {"Martin", "Pavel", "Petr", "Chris", "Alex", "Jana", "Michaela", "Aneta", "Lucie", "Denisa"};

    private static final String [] SampleLastNames = new String[] {"Pelc", "Rumler", "Novak", "Kotrba", "Sparrow", "Clinton", "Johnes", "Stanec", "Smith", "Kouba"};

    @Autowired
    public LoadSampleDataToDatabase(JobPositionRepository jobPositionRepository,EmployeeRepository employeeRepository) {
        LOGGER.info("In constructor");
        List<JobPosition> jobPositions = createAndReturnJobPositions(jobPositionRepository);
        Random random = new Random();
        for (int i =0; i < 1000; i++) {
            Employee e = new Employee(null, sampleFirstNames[random.nextInt(9)], SampleLastNames[random.nextInt(9)],
                    random.nextInt(59) + 1, jobPositions.get(random.nextInt(jobPositions.size() - 1)));
            try {
                employeeRepository.save(e);
            } catch (DataIntegrityViolationException exception) {
                LOGGER.warn("Employee to be saved already exist in db.");
            }
        }
        LOGGER.info("Load of random data finished");
    }

    private static List<JobPosition> createAndReturnJobPositions(JobPositionRepository jobPositionRepository) {
        jobPositionRepository.save(new JobPosition("Java developer"));
        jobPositionRepository.save(new JobPosition("Software analyst"));
        jobPositionRepository.save(new JobPosition("Tester"));
        jobPositionRepository.save(new JobPosition("Product Manager"));
        jobPositionRepository.save(new JobPosition("Frontend developer"));
        jobPositionRepository.save(new JobPosition("System administrator"));
        return jobPositionRepository.findAll();
    }
}
