package com.ninjas.world.springbatchapp.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Sonu Kumar
 */
public class JobRunner implements CommandLineRunner {
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("Time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job,jobParameters);
    }
}
