package com.quangnguyen.stackoverflowclient.di.module;

import com.quangnguyen.stackoverflowclient.data.repository.Local;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionDataSource;
import com.quangnguyen.stackoverflowclient.data.repository.Remote;
import com.quangnguyen.stackoverflowclient.data.repository.local.QuestionLocalDataSource;
import com.quangnguyen.stackoverflowclient.data.repository.remote.QuestionRemoteDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Module
public class QuestionRepositoryModule {

  @Provides
  @Local
  public QuestionDataSource provideLocalDataSource(QuestionLocalDataSource questionLocalDataSource) {
    return questionLocalDataSource;
  }

  @Provides
  @Remote
  public QuestionDataSource provideRemoteDataSource() {
    return new QuestionRemoteDataSource();
  }

}
