package com.quangnguyen.stackoverflowclient;

import android.app.Application;
import com.quangnguyen.stackoverflowclient.data.DaggerQuestionRepositoryComponent;
import com.quangnguyen.stackoverflowclient.data.QuestionRepositoryComponent;
import timber.log.Timber;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class AndroidApplication extends Application {

  private QuestionRepositoryComponent repositoryComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initializeDependencies();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

  }

  private void initializeDependencies() {
    repositoryComponent = DaggerQuestionRepositoryComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public QuestionRepositoryComponent getQuestionRepositoryComponent() {
    return repositoryComponent;
  }
}
