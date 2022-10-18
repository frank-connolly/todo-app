package io.frankconnolly.todoapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Boolean isComplete = Boolean.FALSE;

    public Todo() {
    }

    public Todo(Long id, String content, Boolean isComplete) {
        this.id = id;
        this.content = content;
        this.isComplete = isComplete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompletedStatus() {
        return isComplete;
    }

    public void setCompletedStatus(Boolean complete) {
        isComplete = complete;
    }
}
