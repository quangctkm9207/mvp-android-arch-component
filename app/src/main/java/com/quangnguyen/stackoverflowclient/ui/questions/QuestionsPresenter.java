package com.quangnguyen.stackoverflowclient.ui.questions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.data.repository.QuestionRepository;
import com.quangnguyen.stackoverflowclient.util.schedulers.RunOn;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;

import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.IO;
import static com.quangnguyen.stackoverflowclient.util.schedulers.SchedulerType.UI;

/**
 * A presenter with life-cycle aware.
 */
public class QuestionsPresenter implements QuestionsContract.Presenter, LifecycleObserver {

  private QuestionRepository repository;

  private QuestionsContract.View view;

  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private CompositeDisposable disposeBag;

  @Inject public QuestionsPresenter(QuestionRepository repository, QuestionsContract.View view,
      @RunOn(IO) Scheduler ioScheduler, @RunOn(UI) Scheduler uiScheduler) {
    this.repository = repository;
    this.view = view;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;

    // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
    if (view instanceof LifecycleOwner) {
      ((LifecycleOwner) view).getLifecycle().addObserver(this);
    }

    disposeBag = new CompositeDisposable();
  }

  @Override @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) public void onAttach() {
    loadQuestions(false);
  }

  @Override @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) public void onDetach() {
    // Clean up any no-longer-use resources here
    disposeBag.clear();
  }

  @Override public void loadQuestions(boolean onlineRequired) {
    // Clear old data on view
    view.clearQuestions();

    // Load new one and populate it into view
    Disposable disposable = repository.loadQuestions(onlineRequired)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
    disposeBag.add(disposable);
  }

  @Override public void getQuestion(long questionId) {
    Disposable disposable = repository.getQuestion(questionId)
        .filter(question -> question != null)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(question -> view.showQuestionDetail(question));
    disposeBag.add(disposable);
  }

  @Override public void search(final String questionTitle) {
    // Load new one and populate it into view
    Disposable disposable = repository.loadQuestions(false)
        .flatMap(Flowable::fromIterable)
        .filter(question -> question.getTitle() != null)
        .filter(question -> question.getTitle().toLowerCase().contains(questionTitle.toLowerCase()))
        .toList()
        .toFlowable()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(questions -> {
          if (questions.isEmpty()) {
            // Clear old data in view
            view.clearQuestions();
            // Show notification
            view.showEmptySearchResult();
          } else {
            // Update filtered data
            view.showQuestions(questions);
          }
        });

    disposeBag.add(disposable);
  }

  /**
   * Updates view after loading data is completed successfully.
   */
  private void handleReturnedData(List<Question> list) {
    view.stopLoadingIndicator();
    if (list != null && !list.isEmpty()) {
      view.showQuestions(list);
    } else {
      view.showNoDataMessage();
    }
  }

  /**
   * Updates view if there is an error after loading data from repository.
   */
  private void handleError(Throwable error) {
    view.stopLoadingIndicator();
    view.showErrorMessage(error.getLocalizedMessage());
  }
}
