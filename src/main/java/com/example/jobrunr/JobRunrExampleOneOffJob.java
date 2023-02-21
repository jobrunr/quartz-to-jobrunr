package com.example.jobrunr;

import org.jobrunr.scheduling.JobScheduler;

import java.time.Instant;

public class JobRunrExampleOneOffJob {

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
        jobScheduler.<HelloJob>enqueue(x -> x.sayHello("JobRunr", Instant.now()));

        // keep the main thread running
        Thread.currentThread().join();
    }
}