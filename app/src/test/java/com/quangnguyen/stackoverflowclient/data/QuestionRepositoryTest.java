package com.quangnguyen.stackoverflowclient.data;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.Local;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionDataSource;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.data.repository.Remote;
import io.reactivex.Flowable;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionRepositoryTest {

  private static final Question question1 = new Question();
  private static final Question question2 = new Question();
  private static final Question question3 = new Question();
  private List<Question> questions = Arrays.asList(question1, question2, question3);

  @Mock
  @Local
  private QuestionDataSource localDataSource;

  @Mock
  @Remote
  private QuestionDataSource remoteDataSource;

  private QuestionRepository repository;

  @Before
  public void setup() {
    repository = new QuestionRepository(localDataSource, remoteDataSource);
  }

  @Test
  public void loadQuestion_FromLocal() {
    repository.loadQuestions(false);

    verify(localDataSource).loadQuestions(false);
    verify(remoteDataSource, never()).loadQuestions(true);
  }

  @Test
  public void loadQuestion_FromRemote() {
    doReturn(Flowable.just(questions)).when(remoteDataSource).loadQuestions(true);
    repository.loadQuestions(true);

    verify(remoteDataSource).loadQuestions(true);
    verify(localDataSource, never()).loadQuestions(false);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addQuestion_ShouldThrowException() {
    repository.addQuestion(question1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void clearData_ShouldThrowException() {
    repository.clearData();
  }
}
