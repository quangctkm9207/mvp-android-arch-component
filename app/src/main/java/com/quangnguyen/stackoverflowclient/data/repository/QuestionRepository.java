package com.quangnguyen.stackoverflowclient.data.repository;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class QuestionRepository implements QuestionDataSource {

  private QuestionDataSource remoteDataSource;
  private QuestionDataSource localDataSource;

  @Inject
  public QuestionRepository(@Local QuestionDataSource localDataSource,
      @Remote QuestionDataSource remoteDataSource) {
    this.localDataSource = localDataSource;
    this.remoteDataSource = remoteDataSource;
  }

  @Override
  public Flowable<List<Question>> loadQuestions(boolean forceRemote) {
    Flowable<List<Question>> questions;
    if (forceRemote) {
      questions = remoteDataSource.loadQuestions(true).doOnEach(notification -> {
        // Save new data to local data source
        List<Question> list = notification.getValue();
        if (list != null && !list.isEmpty()) {
          saveDataToLocal(list);
        }
      });
    } else {
      questions =
          localDataSource.loadQuestions(false);
    }
    return questions;
  }

  @Override
  public void addQuestion(Question question) {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public void clearData() {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  // A helper method to save data in database after fetching new data from remote source.
  private void saveDataToLocal(List<Question> questions) {
    // Clear old data
    localDataSource.clearData();
    for (Question question : questions) {
      localDataSource.addQuestion(question);
    }
  }
}
