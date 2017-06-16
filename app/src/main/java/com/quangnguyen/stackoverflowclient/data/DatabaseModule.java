package com.quangnguyen.stackoverflowclient.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.quangnguyen.stackoverflowclient.data.Config;
import com.quangnguyen.stackoverflowclient.data.database.QuestionDao;
import com.quangnguyen.stackoverflowclient.data.database.StackOverflowDb;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author QuangNguyen (quangctkm9207).
 */

@Module
public class DatabaseModule {
  private static final String DATABASE = "database_name";

  @Provides
  @Named(DATABASE)
  String provideDatabaseName() {
    return Config.DATABASE_NAME;
  }

  @Provides
  @Singleton
  StackOverflowDb provideStackOverflowDao(Context context, @Named(DATABASE) String databaseName) {
    return Room.databaseBuilder(context, StackOverflowDb.class, databaseName).build();
  }

  @Provides
  @Singleton
  QuestionDao provideQuestionDao(StackOverflowDb stackOverflowDb) {
    return stackOverflowDb.questionDao();
  }
}
