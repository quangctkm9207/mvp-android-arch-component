package com.quangnguyen.stackoverflowclient.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import com.quangnguyen.stackoverflowclient.data.Config;

/**
 * @author QuangNguyen (quangctkm9207).
 */
@Entity(tableName = Config.QUESTION_TABLE_NAME)
public class Question {

  @SerializedName("question_id")
  @PrimaryKey
  private long id;

  @SerializedName("owner")
  @Embedded(prefix = "owner_")
  private User user;

  @SerializedName("creation_date")
  @ColumnInfo(name = "creation_date")
  private long creationDate;

  @SerializedName("title")
  private String title;

  @SerializedName("link")
  private String link;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }
}
