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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class QuestionLocalDataSourceTest {

  @Mock QuestionDao questionDao;

  private QuestionLocalDataSource localDataSource;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    localDataSource = new QuestionLocalDataSource(questionDao);
  }

  @Test public void loadQuestions_ShouldReturnFromDatabase() {
    // Given
    List<Question> questions = Arrays.asList(new Question(), new Question());
    TestSubscriber<List<Question>> subscriber = new TestSubscriber<>();
    given(questionDao.getAllQuestions()).willReturn(Flowable.just(questions));

    // When
    localDataSource.loadQuestions(false).subscribe(subscriber);

    // Then
    then(questionDao).should().getAllQuestions();
  }

  @Test public void addQuestion_ShouldInsertToDatabase() {
    Question question = new Question();
    localDataSource.addQuestion(question);

    then(questionDao).should().insert(question);
  }

  @Test public void clearData_ShouldDeleteAllDataInDatabase() {
    localDataSource.clearData();

    then(questionDao).should().deleteAll();
  }
}