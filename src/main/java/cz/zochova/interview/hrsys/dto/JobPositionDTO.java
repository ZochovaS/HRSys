package cz.zochova.interview.hrsys.dto;

public class JobPositionDTO {

    private long jobPositionId;

    private String positionName;

    public JobPositionDTO() {
    }

    public JobPositionDTO(String positionName) {
        this.positionName = positionName;
    }

    public long getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(long jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "JobPositionDTO{" +
                "jobPositionId=" + jobPositionId +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
