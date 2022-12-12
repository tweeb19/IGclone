package com.example.CommentCRUD.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

public class CommentModel {
    int id;
    int postId;
    String username;
    String body;
    LocalDate createdOn;

    public CommentModel() {
    }
    public CommentModel(int postId, String username, String body, LocalDate createdOn){
        this.postId = postId;
        this.username = username;
        this.body = body;
        this.createdOn = createdOn;
    }
    public CommentModel(int id, int postId, String username, String body, LocalDate createdOn) {
        this.id = id;
        this.postId = postId;
        this.username = username;
        this.body = body;
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                postId == comment.postId &&
                Objects.equals(username, comment.username) &&
                Objects.equals(body, comment.body) &&
                Objects.equals(createdOn, comment.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, username, body, createdOn);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postId=" + postId +
                ", username='" + username + '\'' +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
