package com.quangnguyen.stackoverflowclient.data;

import com.quangnguyen.stackoverflowclient.data.repository.Local;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionDataSource;
import com.quangnguyen.stackoverflowclient.data.repository.Remote;
import com.quangnguyen.stackoverflowclient.data.repository.local.QuestionLocalDataSource;
import com.quangnguyen.stackoverflowclient.data.repository.remote.QuestionRemoteDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Module
public class QuestionRepositoryModule {

  @Provides
  @Local
  @Singleton
  public QuestionDataSource provideLocalDataSource(QuestionLocalDataSource questionLocalDataSource) {
    return questionLocalDataSource;
  }

  @Provides
  @Remote
  @Singleton
  public QuestionDataSource provideRemoteDataSource(QuestionRemoteDataSource questionRemoteDataSource) {
    return questionRemoteDataSource;
  }

}
