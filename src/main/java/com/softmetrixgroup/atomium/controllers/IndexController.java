package com.softmetrixgroup.atomium.controllers;

import com.softmetrixgroup.atomium.config.BatchConfig;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    BatchConfig batchConfig;

    @GetMapping("/execute-test-job")
    @ResponseBody
    public String executeTestJob() {
        try {
            Job job = batchConfig.demoJob();
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong( "time.millis", System.currentTimeMillis(), true);
            JobParameters jobParameters = jobParametersBuilder.toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "execute-test-job";
    }

    @GetMapping("/page-visits")
    @ResponseBody
    public List<String> pageVisits() {
        List<String> pageVisits = new ArrayList<>();
        pageVisits.add("/index");
        pageVisits.add("/about-us");
        pageVisits.add("/contact");
        pageVisits.add("/newsletter");
        pageVisits.add("/blog");
        return pageVisits;
    }
}
