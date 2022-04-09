package com.ninjas.world.springbatchapp.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

/**
 * @author Sonu Kumar
 */
@Service
public class FirstStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before step" + stepExecution.getStepName());
        System.out.println("Job params :: " + stepExecution.getJobParameters());
        System.out.println("step ExecutionContext:: " + stepExecution.getExecutionContext());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After step" + stepExecution.getStepName());
        System.out.println("Job params :: " + stepExecution.getJobParameters());
        System.out.println("step ExecutionContext:: " + stepExecution.getExecutionContext());
        return null;
    }
}
