package com.quangnguyen.stackoverflowclient.ui.questions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.util.schedulers.RunOn;
import io.reactivex.Scheduler;
import java.util.List;
import javax.inject.Inject;

import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.*;

/**
 * A presenter with life-cycle aware.
 *
 * @author QuangNguyen (quangctkm9207).
 */
public class QuestionsPresenter implements QuestionsContract.Presenter, LifecycleObserver {

  private QuestionRepository repository;

  private QuestionsContract.View view;

  private List<Question> caches;

  private Scheduler computationScheduler;
  private Scheduler uiScheduler;

  @Inject
  public QuestionsPresenter(QuestionRepository repository, QuestionsContract.View view,
      @RunOn(COMPUTATION) Scheduler computationScheduler, @RunOn(UI) Scheduler uiScheduler) {
    this.repository = repository;
    this.view = view;
    this.computationScheduler = computationScheduler;
    this.uiScheduler = uiScheduler;
    // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
    if (view instanceof LifecycleOwner) {
      ((LifecycleOwner) view).getLifecycle().addObserver(this);
    }
  }

  @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  public void attachView() {

  }

  @Override
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void detachView() {
    // Clean up your resources here
  }

  @Override
  public void loadQuestions(boolean onlineRequired) {
    // Clear old data on view
    view.clearQuestions();
    // Load new one and populate it into view
    repository.loadQuestions(onlineRequired)
        .subscribeOn(computationScheduler)
        .observeOn(uiScheduler)
        .subscribe(list -> {
          view.stopLoadingIndicator();
          if (list == null || list.isEmpty()) {
            view.showNoDataMessage();
          } else {
            view.showQuestions(list);
            caches = list;
          }
        }, error -> {
          view.stopLoadingIndicator();
          view.showErrorMessage(error.getLocalizedMessage());
        }, () -> view.stopLoadingIndicator());
  }

  @Override
  public void getQuestion(long questionId) {
    // Load question detail from cache
    if (caches != null && caches.size() != 0) {
      for (Question question : caches) {
        if (question.getId() == questionId) {
          view.showQuestionDetail(question);
          break;
        }
      }
    }
  }
}
