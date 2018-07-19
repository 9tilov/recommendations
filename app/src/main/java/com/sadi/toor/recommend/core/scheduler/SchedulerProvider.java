package com.sadi.toor.recommend.core.scheduler;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

}
