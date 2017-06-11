package com.quangnguyen.stackoverflowclient.di.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Module
public class AppModule {
  private Context contex;
  public AppModule(Application context) {
    this.contex = context;
  }
  @Provides
  public Context provideContext() {
    return contex;
  }
}
