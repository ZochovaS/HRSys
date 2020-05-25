package cz.zochova.interview.hrsys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"POSITION_NAME"}))
public class JobPosition implements Serializable {
    private static final long serialVersionUID = 567880771001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_POSITION_ID")
    private long jobPositionId;

    @NotEmpty(message = "Position name cannot be empty.")
    @Size(max = 100, message = "Position name is too long. Cannot exceed 100 chars.")
    @Column(name = "POSITION_NAME")
    private String positionName;

    public JobPosition() {
    }

    public JobPosition(String positionName) {
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
        return "JobPosition{" +
                "id=" + jobPositionId +
                ", positionName='" + positionName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobPosition)) return false;
        JobPosition that = (JobPosition) o;
        return getPositionName().equals(that.getPositionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPositionName());
    }
}
