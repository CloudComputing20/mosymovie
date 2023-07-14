package com.example.mosymovie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class ScheduledConfig {
    private final int POOL_SIZE = 10;
    public TaskScheduler schedular(){
        ThreadPoolTaskScheduler schedular = new ThreadPoolTaskScheduler();
        schedular.setPoolSize(POOL_SIZE);
        return schedular;
    }

}
