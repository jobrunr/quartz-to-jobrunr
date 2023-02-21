package com.example.jobrunr;

import org.jobrunr.scheduling.JobScheduler;

import java.time.Instant;

import static org.jobrunr.scheduling.cron.Cron.every30seconds;

public class JobRunrExampleRecurringJob {

    // we can use any class or bean without implementing an interface
    public static class HelloJob {
        public void sayHello(String framework, Instant createdAt) {
            // run the actual business code
            System.out.println(framework + " says Hello at " + Instant.now() + "(created at " + createdAt + ")");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create a JobScheduler using a factory method with defaults
        JobScheduler jobScheduler = JobRunrFactory.initializeJobRunr();

        // Schedule your job every 30 seconds
        jobScheduler.<HelloJob>scheduleRecurrently(every30seconds(), x -> x.sayHello("JobRunr", Instant.now()));

        // keep the main thread running
        Thread.currentThread().join();
    }
}
