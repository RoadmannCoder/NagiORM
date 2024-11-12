package main.java.Entities;

import main.java.Annotation.Entity;
import main.java.Annotation.PrimaryKey;

@Entity
public class Job {
    @PrimaryKey
    private Long id;
    private String jobName;

    public Job() {
    }

    public Job(Long id, String jobName) {
        this.id = id;
        this.jobName = jobName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                '}';
    }
}
