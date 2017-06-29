package com.quangnguyen.stackoverflowclient.ui;

import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsContract;
import com.quangnguyen.stackoverflowclient.ui.questions.QuestionsPresenter;
import com.quangnguyen.stackoverflowclient.util.schedulers.RunOn;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.COMPUTATION;
import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.UI;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionsPresenterTest {
  ;
  private static final Question question1 = new Question();
  private static final Question question2 = new Question();
  private static final Question question3 = new Question();
  private List<Question> questions = Arrays.asList(question1, question2, question3);

  @Mock
  private QuestionRepository repository;

  @Mock
  private QuestionsContract.View view;

  @RunOn(COMPUTATION)
  private Scheduler computationScheduler;

  @RunOn(UI)
  private Scheduler uiScheduler;

  private TestScheduler testScheduler;

  private QuestionsPresenter presenter;

  @Before
  public void setUp() {
    // Make sure to use TestScheduler for RxJava testing
    testScheduler = new TestScheduler();
    computationScheduler = testScheduler;
    uiScheduler = testScheduler;
    presenter = new QuestionsPresenter(repository, view, computationScheduler, uiScheduler);
  }

  @Test
  public void loadQuestions_FromRepoToView_WithDataReturned() {
    doReturn(Flowable.just(questions)).when(repository).loadQuestions(true);
    presenter.loadQuestions(true);
    testScheduler.triggerActions(); // Trigger actions for test scheduler

    verify(view).clearQuestions();
    verify(view).showQuestions(questions);
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }

  @Test
  public void loadQuestions_FromRepoToView_WithNoDataReturned() {
    doReturn(Flowable.just(new ArrayList<Question>())).when(repository).loadQuestions(true);
    presenter.loadQuestions(true);
    testScheduler.triggerActions(); // Trigger actions for test scheduler

    verify(view).clearQuestions();
    verify(view, never()).showQuestions(questions);
    verify(view).showNoDataMessage();
    verify(view, atLeastOnce()).stopLoadingIndicator();
  }
}
