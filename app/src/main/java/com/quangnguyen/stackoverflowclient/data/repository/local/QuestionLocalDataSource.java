package com.quangnguyen.stackoverflowclient.data.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.quangnguyen.stackoverflowclient.data.Config;
import com.quangnguyen.stackoverflowclient.data.database.QuestionDao;
import com.quangnguyen.stackoverflowclient.data.database.StackOverflowDb;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionDataSource;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class QuestionLocalDataSource implements QuestionDataSource {

  private QuestionDao questionDao;

  @Inject
  public QuestionLocalDataSource(Context context) {
    StackOverflowDb stackOverflowDb =
        Room.databaseBuilder(context, StackOverflowDb.class, Config.DATABASE_NAME).build();
    this.questionDao = stackOverflowDb.questionDao();
  }

  @Override
  public Flowable<List<Question>> loadQuestions(boolean forceRemote) {
    return questionDao.getAllQuestions();
  }

  @Override
  public void addQuestion(Question question) {
    // Insert new one
    questionDao.insert(question);
  }

  @Override
  public void clearData() {
    // Clear old data
    questionDao.deleteAll();
  }
}
