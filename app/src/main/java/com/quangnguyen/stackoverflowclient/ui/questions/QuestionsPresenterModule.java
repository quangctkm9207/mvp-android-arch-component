package com.quangnguyen.stackoverflowclient.ui.questions;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsPresenterModule {
  private QuestionsContract.View view;

  public QuestionsPresenterModule(QuestionsContract.View view) {
    this.view = view;
  }

  @Provides
  public QuestionsContract.View provideView() {
    return view;
  }
}
