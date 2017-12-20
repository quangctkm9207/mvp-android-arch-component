package com.quangnguyen.stackoverflowclient.ui;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsContract;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsPresenter;
import com.quangnguyen.stackoverflowclient.util.schedulers.RunOn;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.COMPUTATION;
import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.UI;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class QuestionsPresenterTest {
  private static final Question QUESTION1 = new Question();
  private static final Question QUESTION2 = new Question();
  private static final Question QUESTION3 = new Question();
  private static final List<Question> NO_QUESTION = Collections.emptyList();
  private static final List<Question> THREE_QUESTIONS =
      Arrays.asList(QUESTION1, QUESTION2, QUESTION3);

  @Parameters public static Object[] data() {
    return new Object[] { NO_QUESTION, THREE_QUESTIONS };
  }

  @Parameter public List<Question> questions;

  @Mock private QuestionRepository repository;

  @Mock private QuestionsContract.View view;

  @RunOn(COMPUTATION) private Scheduler computationScheduler;

  @RunOn(UI) private Scheduler uiScheduler;

  private TestScheduler testScheduler;

  private QuestionsPresenter presenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    // Make sure to use TestScheduler for RxJava testing
    testScheduler = new TestScheduler();
    computationScheduler = testScheduler;
    uiScheduler = testScheduler;
    presenter = new QuestionsPresenter(repository, view, computationScheduler, uiScheduler);
  }

  @Test public void loadQuestions_ShouldAlwaysStopLoadingIndicatorOnView_WhenComplete() {
    when(repository.loadQuestions(true)).thenReturn(Flowable.just(questions));
    presenter.loadQuestions(true);
    testScheduler.triggerActions();
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowQuestionOnView_WithDataReturned() {
    // Given
    when(repository.loadQuestions(true)).thenReturn(Flowable.just(THREE_QUESTIONS));

    // When
    presenter.loadQuestions(true);
    testScheduler.triggerActions();

    // Then
    verify(view).clearQuestions();
    verify(view).showQuestions(THREE_QUESTIONS);
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowMessage_WhenNoDataReturned() {
    // Given
    when(repository.loadQuestions(true)).thenReturn(Flowable.just(NO_QUESTION));

    // When
    presenter.loadQuestions(true);
    testScheduler.triggerActions();

    // Then
    verify(view).clearQuestions();
    verify(view, never()).showQuestions(any());
    verify(view).showNoDataMessage();
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void getQuestion_ShouldShowDetailOnView() {
    // Given
    when(repository.getQuestion(1)).thenReturn(Flowable.just(QUESTION1));

    // When
    presenter.getQuestion(1);
    testScheduler.triggerActions();

    // Then
    verify(view).showQuestionDetail(QUESTION1);
  }

  @Test public void search_ResultShouldBeShownOnView_WhenFilteredDataIsNotEmpty() {
    // Given
    QUESTION1.setTitle("activity onCreate");
    QUESTION2.setTitle("activity onDestroy");
    QUESTION3.setTitle("fragment");
    when(repository.loadQuestions(false)).thenReturn(Flowable.just(THREE_QUESTIONS));

    // When
    presenter.search("activity");
    testScheduler.triggerActions();

    // Then
    // Return a list of questions which should contains only question 1.
    verify(view).showQuestions(Arrays.asList(QUESTION1, QUESTION2));
    verifyNoMoreInteractions(view);
  }

  @Test public void search_EmptyMessageShouldBeShownOnView_WhenDataIsEmpty() {
    // Given
    when(repository.loadQuestions(false)).thenReturn(Flowable.just(NO_QUESTION));

    // When
    presenter.search(any());
    testScheduler.triggerActions();

    // Then
    verify(view).clearQuestions();
    verify(view).showEmptySearchResult();
    verifyNoMoreInteractions(view);
  }

  @Test public void search_EmptyMessageShouldBeShownOnView_WhenNoDataMatchesQuery() {
    QUESTION1.setTitle("activity onCreate");
    QUESTION2.setTitle("activity onDestroy");
    QUESTION3.setTitle("fragment");
    // Given
    when(repository.loadQuestions(false)).thenReturn(Flowable.just(NO_QUESTION));

    // When
    presenter.search("invalid question");
    testScheduler.triggerActions();

    // Then
    verify(view).clearQuestions();
    verify(view).showEmptySearchResult();
    verifyNoMoreInteractions(view);
  }
}
