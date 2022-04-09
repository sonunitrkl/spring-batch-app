package com.ninjas.world.springbatchapp.service;

import com.ninjas.world.springbatchapp.model.JobParameterRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sonu Kumar
 */
@Service
public class JobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    private Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    private Job secondJob;

    @Async
    public void startJob(String jobName, List<JobParameterRequest> jobParameterRequestList) {

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobParameterRequestList.stream().forEach( jobParameterRequest -> {
            jobParametersMap.put(jobParameterRequest.getParamKey(),new JobParameter(jobParameterRequest.getParamValue()));
        });
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        try {
            if ("first-job".equals(jobName)) {
                jobLauncher.run(firstJob, jobParameters);
            } else {
                jobLauncher.run(secondJob, jobParameters);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred while starting job");
        }

    }
}
