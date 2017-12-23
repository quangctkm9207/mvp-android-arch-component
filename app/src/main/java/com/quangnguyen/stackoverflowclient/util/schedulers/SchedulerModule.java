package com.quangnguyen.stackoverflowclient.util.schedulers;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.IO;
import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.UI;

/**
 * Provides common Schedulers used by RxJava
 */
@Module
public class SchedulerModule {

  @Provides
  @RunOn(IO)
  Scheduler provideIo(){
    return Schedulers.io();
  }

  @Provides
  @RunOn(UI)
  Scheduler provideUi() {
    return AndroidSchedulers.mainThread();
  }
}
