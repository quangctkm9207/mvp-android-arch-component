package com.quangnguyen.stackoverflowclient.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.quangnguyen.stackoverflowclient.data.model.Question;

@Database(entities = Question.class, version = 1)
public abstract class StackOverflowDb extends RoomDatabase {

  public abstract QuestionDao questionDao();
}
