package com.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.Instant.now;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzExampleRecurringJob {

    // we create an implementation of a Job that will be executed
    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            // get the variables from the JobDataMap
            String framework = jobExecutionContext.getMergedJobDataMap().getString("framework");
            Instant createdAt = (Instant) jobExecutionContext.getMergedJobDataMap().get("createdAt");

            // run the actual business code
            System.out.println(framework + " says Hello at " + Instant.now() + "(created at " + createdAt + ")");
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
                    .build();

            // create the JobDataMap
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("framework", "Quartz");
            jobDataMap.put("createdAt", now());

            // Create the trigger for the job using a cronSchedule that runs every 30 seconds and pass the JobDataMap
            Trigger trigger =  newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(cronSchedule("0/30 * * * * ? *"))
                    .forJob(jobKey)
                    .usingJobData(jobDataMap)
                    .build();

            // schedule the job using the jobDetail and the trigger
            scheduler.scheduleJob(jobDetail, trigger);

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