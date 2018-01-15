package com.quangnguyen.stackoverflowclient.data.repository.local;

import com.quangnguyen.stackoverflowclient.data.database.QuestionDao;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class QuestionLocalDataSourceTest {

  @Mock QuestionDao questionDao;

  private QuestionLocalDataSource localDataSource;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    localDataSource = new QuestionLocalDataSource(questionDao);
  }

  @Test public void loadQuestions_ShouldReturnFromDatabase() {
    List<Question> questions = Arrays.asList(new Question(), new Question());
    TestSubscriber<List<Question>> subscriber = new TestSubscriber<>();
    doReturn(Flowable.just(questions)).when(questionDao).getAllQuestions();

    localDataSource.loadQuestions(false).subscribe(subscriber);

    verify(questionDao).getAllQuestions();
  }

  @Test public void addQuestion_ShouldInsertToDatabase() {
    Question question = new Question();
    localDataSource.addQuestion(question);

    verify(questionDao).insert(question);
  }

  @Test public void clearData_ShouldDeleteAllDataInDatabase() {
    localDataSource.clearData();

    verify(questionDao).deleteAll();
  }
}