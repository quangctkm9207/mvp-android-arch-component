package com.quangnguyen.stackoverflowclient;

import android.app.Application;
import com.quangnguyen.stackoverflowclient.di.component.DaggerQuestionRepositoryComponent;
import com.quangnguyen.stackoverflowclient.di.component.QuestionRepositoryComponent;
import com.quangnguyen.stackoverflowclient.di.module.AppModule;
import timber.log.Timber;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class AndroidApplication extends Application {

  public QuestionRepositoryComponent repositoryComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initializeDependence();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

  }

  private void initializeDependence() {
    repositoryComponent = DaggerQuestionRepositoryComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public QuestionRepositoryComponent getQuestionRepositoryComponent() {
    return repositoryComponent;
  }
}
