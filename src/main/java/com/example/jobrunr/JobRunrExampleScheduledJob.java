package com.example.jobrunr;

import org.jobrunr.scheduling.JobScheduler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.Instant.now;

public class JobRunrExampleScheduledJob {

    // we can use any class or bean without implementing an interface
    public static class HelloJob {
        public void sayHello(String framework, Instant createdAt) {
            // run the actual business code
            System.out.println(framework + " says Hello at " + createdAt);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create a JobScheduler using a factory method with defaults
        JobScheduler jobScheduler = JobRunrFactory.initializeJobRunr();

        // Create and trigger your job
        jobScheduler.<HelloJob>schedule(now().plus(10, ChronoUnit.SECONDS), x -> x.sayHello("JobRunr", now()));

        // keep the main thread running
        Thread.currentThread().join();
    }
}