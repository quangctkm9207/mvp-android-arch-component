package com.quangnguyen.stackoverflowclient.ui.base;

import android.view.View;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    void OnItemClick(View view, int position);
  }

  @FunctionalInterface
  interface OnItemLongClickListener {
    void OnItemLongClick(View view, int position);
  }
}
