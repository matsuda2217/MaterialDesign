package com.example.tt.myapplication.Services;


import com.example.tt.myapplication.logging.L;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by TT
 */
public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this,"on start job");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        return false;
    }
}
