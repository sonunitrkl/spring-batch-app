package com.ninjas.world.springbatchapp.config;

import com.ninjas.world.springbatchapp.listener.FirstJobListener;
import com.ninjas.world.springbatchapp.listener.FirstStepListener;
import com.ninjas.world.springbatchapp.model.StudentCSV;
import com.ninjas.world.springbatchapp.processor.FirstItemProcessor;
import com.ninjas.world.springbatchapp.reader.FirstItemReader;
import com.ninjas.world.springbatchapp.service.TaskService;
import com.ninjas.world.springbatchapp.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.io.File;

/**
 * @author Sonu Kumar
 */
@Configuration
@EnableBatchProcessing
public class SampleJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstJobListener firstJobListener;
    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("first-job")
                .incrementer(new RunIdIncrementer())   // in obsence of this increamentor, it will run for only once
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("first-step")
                .tasklet(firstTask())
                .listener(firstStepListener)
                .build();

    }

    @Bean
    public Step secondStep(){
        return stepBuilderFactory.get("second-step")
                .tasklet(taskService)
                .build();

    }

    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("this is the first tasklet");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    public Job secondJob(){
        return jobBuilderFactory.get("second-job")
                .incrementer(new RunIdIncrementer())   // in obsence of this increamentor, it will run for only once
                .start(firstStepChunk())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStepChunk(){    //Note: Item Processor is optional. ItemWriter and ItemReader is mandatory.
        return stepBuilderFactory.get("first-chunk-step")
                .<Integer,Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

//    private Tasklet secondTask(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("this is the second tasklet");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }

    public FlatFileItemReader<StudentCSV> flatFileItemReader(){
        FlatFileItemReader<StudentCSV> flatFileItemReader = new FlatFileItemReader<StudentCSV>();
        flatFileItemReader.setResource(new FileSystemResource(new File("")));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCSV>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("Id","FirstName","LastName","Email");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCSV>(){
                    {
                        setTargetType(StudentCSV.class);
                    }
                });
            }
        });
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
}
