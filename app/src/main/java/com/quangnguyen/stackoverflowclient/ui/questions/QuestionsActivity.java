package com.quangnguyen.stackoverflowclient.ui.questions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.quangnguyen.stackoverflowclient.R;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class QuestionsActivity extends BaseActivity implements QuestionsContract.View {

  @BindView(R.id.question_recycler)
  RecyclerView questionRecyclerView;
  @BindView(R.id.refresh)
  SwipeRefreshLayout refreshLayout;
  @BindView(R.id.notiText)
  TextView notiText;

  private QuestionAdapter adapter;
  @Inject
  QuestionsPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initializePresenter();
    setupWidgets();
  }

  private void initializePresenter() {
    DaggerQuestionsComponent.builder()
        .questionsPresenterModule(new QuestionsPresenterModule(this))
        .questionRepositoryComponent(getQuestionRepositoryComponent())
        .build()
        .inject(this);
  }

  private void setupWidgets() {
    // Setup recycler view
    adapter = new QuestionAdapter(new ArrayList<>());
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    questionRecyclerView.setLayoutManager(layoutManager);
    questionRecyclerView.setAdapter(adapter);
    questionRecyclerView.setItemAnimator(new DefaultItemAnimator());
    adapter.setOnItemClickListener(
        (view, position) -> presenter.getQuestion(adapter.getItem(position).getId()));

    // Refresh layout
    refreshLayout.setOnRefreshListener(() -> presenter.loadQuestions(true));
    // Set notification text visible first
    notiText.setVisibility(View.GONE);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.loadQuestions(false);
  }

  @Override
  public void showQuestions(List<Question> questions) {
    notiText.setVisibility(View.GONE);
    adapter.replaceData(questions);
  }

  @Override
  public void showNoDataMessage() {
    notiText.setVisibility(View.VISIBLE);
    notiText.setText(getResources().getString(R.string.error_no_data));
  }

  @Override
  public void showErrorMessage(String error) {
    notiText.setVisibility(View.VISIBLE);
    notiText.setText(error);
  }

  @Override
  public void clearQuestions() {
    adapter.clearData();
  }

  @Override
  public void stopLoadingIndicator() {
    if (refreshLayout.isRefreshing()) {
      refreshLayout.setRefreshing(false);
    }
  }

  @Override
  public void showQuestionDetail(Question question) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(question.getLink()));
    startActivity(intent);
  }
}
