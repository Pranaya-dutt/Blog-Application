package com.mountblue.blog.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Post_tag {
    @Id
    private int postId;
    @Id
    private int tagId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "undated_at")
    private Date updatedAt;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
