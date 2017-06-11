package com.quangnguyen.stackoverflowclient.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.model.User;
import javax.inject.Inject;
import javax.inject.Singleton;
/**
 * @author QuangNguyen (quangctkm9207).
 */
@Database(entities = Question.class, version = 1)
public abstract class StackOverflowDb extends RoomDatabase {

  public abstract QuestionDao questionDao();
}
