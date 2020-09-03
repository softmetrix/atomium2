package com.softmetrixgroup.atomium.config;

import com.softmetrixgroup.Step;
import com.softmetrixgroup.atomium.services.JobDataService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobDataService jobDataService;

    public Job demoJob(){
        JobBuilder jobBuilder = jobBuilderFactory.get("job2");
        SimpleJobBuilder simpleJobBuilder = null;
        String jarFilePath = "/Users/mmedojevic/Desktop/atomium-jars/atomium-test-job.jar";
        List<Step> steps = jobDataService.getStepsFromJar(jarFilePath);
        int count = 0;
        for(Step step : steps) {
            Tasklet tasklet = new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                    String className = step.getClass().getName();
                    System.out.println(className + " execution starts");
                    step.run();
                    System.out.println(className + " execution ends");
                    return RepeatStatus.FINISHED;
                }
            };
            if(count++ == 0) {
                simpleJobBuilder = jobBuilder.start(stepBuilderFactory.get("step"+count).tasklet(tasklet).build());
            } else {
                simpleJobBuilder = simpleJobBuilder.next(stepBuilderFactory.get("step"+count).tasklet(tasklet).build());
            }
        }

        return simpleJobBuilder.build();
    }
}
