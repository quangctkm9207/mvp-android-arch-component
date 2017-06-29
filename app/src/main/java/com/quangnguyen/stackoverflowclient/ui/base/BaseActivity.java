package com.quangnguyen.stackoverflowclient.ui.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;
import com.quangnguyen.stackoverflowclient.AndroidApplication;
import com.quangnguyen.stackoverflowclient.data.QuestionRepositoryComponent;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

  protected void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  protected QuestionRepositoryComponent getQuestionRepositoryComponent() {
    return ((AndroidApplication) getApplication()).getQuestionRepositoryComponent();
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }
}
