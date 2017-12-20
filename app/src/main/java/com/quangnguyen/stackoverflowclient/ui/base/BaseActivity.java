package com.quangnguyen.stackoverflowclient.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;
import com.quangnguyen.stackoverflowclient.AndroidApplication;
import com.quangnguyen.stackoverflowclient.data.QuestionRepositoryComponent;

public class BaseActivity extends AppCompatActivity {
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

  protected QuestionRepositoryComponent getQuestionRepositoryComponent() {
    return ((AndroidApplication) getApplication()).getQuestionRepositoryComponent();
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }
}
