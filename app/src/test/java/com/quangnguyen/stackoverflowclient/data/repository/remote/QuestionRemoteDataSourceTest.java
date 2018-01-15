package com.quangnguyen.stackoverflowclient.data.repository.remote;

import com.quangnguyen.stackoverflowclient.data.Config;
import com.quangnguyen.stackoverflowclient.data.api.QuestionResponse;
import com.quangnguyen.stackoverflowclient.data.api.QuestionService;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

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
    QuestionResponse questionResponse = new QuestionResponse();
    TestSubscriber<List<Question>> subscriber = new TestSubscriber<>();
    given(questionService.loadQuestionsByTag(Config.ANDROID_QUESTION_TAG)).willReturn(Flowable.just(questionResponse));

    remoteDataSource.loadQuestions(anyBoolean()).subscribe(subscriber);

    then(questionService).should().loadQuestionsByTag(Config.ANDROID_QUESTION_TAG);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addQuestion_NoThingToDoWithRemoteService() {
    Question question = mock(Question.class);
    remoteDataSource.addQuestion(question);

    then(questionService).shouldHaveZeroInteractions();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void clearData_NoThingToDoWithRemoteService() {
    remoteDataSource.clearData();

    then(questionService).shouldHaveZeroInteractions();
  }
}