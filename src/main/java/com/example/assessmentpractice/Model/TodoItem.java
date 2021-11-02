package com.example.assessmentpractice.Model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

@Entity(name="todoItem")
@Table(name="todoList")
@DynamicInsert
@DynamicUpdate
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String content = "";
    boolean completed = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
