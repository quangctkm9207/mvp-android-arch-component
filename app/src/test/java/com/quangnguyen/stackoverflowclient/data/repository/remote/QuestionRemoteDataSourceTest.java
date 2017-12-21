package com.quangnguyen.stackoverflowclient.data.repository.remote;

import com.quangnguyen.stackoverflowclient.data.Config;
import com.quangnguyen.stackoverflowclient.data.api.QuestionService;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class QuestionRemoteDataSourceTest {
  @Mock QuestionService questionService;

  private QuestionRemoteDataSource remoteDataSource;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    remoteDataSource = new QuestionRemoteDataSource(questionService);
  }

  @Test
  public void loadQuestions_ShouldReturnFromRemoteService() {
    List<Question> questions = Arrays.asList(new Question(), new Question());
    TestSubscriber<List<Question>> subscriber = new TestSubscriber<>();
    doReturn(Flowable.just(questions)).when(questionService).loadQuestionsByTag(Config.ANDROID_QUESTION_TAG);

    remoteDataSource.loadQuestions(anyBoolean()).subscribe(subscriber);

    verify(questionService).loadQuestionsByTag(Config.ANDROID_QUESTION_TAG);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addQuestion_NoThingToDoWithRemoteService() {
    Question question = mock(Question.class);
    remoteDataSource.addQuestion(question);
    verifyZeroInteractions(questionService);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void clearData_NoThingToDoWithRemoteService() {
    remoteDataSource.clearData();
    verifyZeroInteractions(questionService);
  }
}