package com.quangnguyen.stackoverflowclient.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.quangnguyen.stackoverflowclient.data.Config;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface QuestionDao {
  @Query("SELECT * FROM " + Config.QUESTION_TABLE_NAME)
  Flowable<List<Question>> getAllQuestions();

  @Query("SELECT * FROM " + Config.QUESTION_TABLE_NAME + " WHERE id == :id")
  Flowable<Question> getQuestionById(int id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Question question);

  @Query("DELETE FROM " + Config.QUESTION_TABLE_NAME)
  void deleteAll();
}
