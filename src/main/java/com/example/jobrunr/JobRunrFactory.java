package com.example.jobrunr;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.InMemoryStorageProvider;

public class JobRunrFactory {

    public static JobScheduler initializeJobRunr() {
        return JobRunr.configure()
                .useStorageProvider(new InMemoryStorageProvider()) // we use the InMemory Storage Provider
                .useBackgroundJobServer() // we enable background job processing (you may not want this on a web application)
                .initialize()
                .getJobScheduler();
    }

}
