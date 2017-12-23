package com.quangnguyen.stackoverflowclient.ui.questions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.quangnguyen.stackoverflowclient.R;
import com.quangnguyen.stackoverflowclient.data.model.Question;
import com.quangnguyen.stackoverflowclient.ui.base.BaseRecyclerViewAdapter;
import com.quangnguyen.stackoverflowclient.util.DateTimeUtils;
import io.reactivex.annotations.NonNull;
import java.security.InvalidParameterException;
import java.util.List;

class QuestionAdapter extends BaseRecyclerViewAdapter<QuestionAdapter.QuestionViewHolder> {
  class QuestionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title) TextView titleText;
    @BindView(R.id.text_user) TextView userText;
    @BindView(R.id.text_created_time) TextView createdTimeText;
    @BindView(R.id.image_profile) ImageView profileImage;

    public QuestionViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  private List<Question> questions;

  public QuestionAdapter(@NonNull List<Question> questions) {
    this.questions = questions;
  }

  @Override public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_question, viewGroup, false);
    return new QuestionViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    super.onBindViewHolder(viewHolder, i);
    QuestionViewHolder vh = (QuestionViewHolder) viewHolder; //safe cast
    Question question = questions.get(i);
    vh.titleText.setText(question.getTitle());
    vh.userText.setText(question.getUser().getName());
    vh.createdTimeText.setText(DateTimeUtils.formatRelativeTime(question.getCreationDate()));
    Glide.with(vh.profileImage).load(question.getUser().getImage()).into(vh.profileImage);
  }

  @Override public int getItemCount() {
    return questions.size();
  }

  public void replaceData(List<Question> questions) {
    this.questions.clear();
    this.questions.addAll(questions);
    notifyDataSetChanged();
  }

  public Question getItem(int position) {
    if (position < 0 || position >= questions.size()) {
      throw new InvalidParameterException("Invalid item index");
    }
    return questions.get(position);
  }

  public void clearData() {
    questions.clear();
    notifyDataSetChanged();
  }
}
