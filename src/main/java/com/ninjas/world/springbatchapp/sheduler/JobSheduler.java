package com.ninjas.world.springbatchapp.sheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sonu Kumar
 */
@Service
public class JobSheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("secondJob")
    @Autowired
    private Job secondJob;


    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void secondJobScheduler() {
        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("currentTime", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParametersMap);
        try {
            JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
            System.out.println("Job ExecutionId :: " + jobExecution.getJobId());
        } catch (Exception exception) {
            System.out.println("Exception occurred while starting job");
        }
    }
}
