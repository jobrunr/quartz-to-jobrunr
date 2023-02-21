package com.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.Instant;

import static org.quartz.JobBuilder.newJob;

public class QuartzExampleOneOffJob {

    // we create an implementation of a Job that will be executed
    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            // get the variables from the JobDataMap
            String framework = jobExecutionContext.getMergedJobDataMap().getString("framework");
            Instant createdAt = (Instant) jobExecutionContext.getMergedJobDataMap().get("createdAt");

            // run the actual business code
            System.out.println(framework + " says Hello at " + createdAt);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // create a JobKey so we can trigger it instantly
            JobKey jobKey = new JobKey("job1", "group1");

            // define the job and tie it to our HelloJob class
            JobDetail jobDetail = newJob(HelloJob.class)
                    .withIdentity(jobKey)
                    .storeDurably() // otherwise it cannot be triggered immediately
                    .build();

            // store the job in the job store
            scheduler.addJob(jobDetail, true);

            // create the JobDataMap
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("framework", "Quartz");
            jobDataMap.put("createdAt", Instant.now());

            // trigger the job using the JobKey and the JobDataMap
            scheduler.triggerJob(jobKey, jobDataMap);

            // and start it off
            scheduler.start();

            // keep the main thread running
            Thread.currentThread().join();
            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}