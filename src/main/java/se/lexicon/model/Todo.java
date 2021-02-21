package se.lexicon.model;

import java.time.LocalDate;
import java.util.Objects;

public class Todo {
    private int toDoId;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private int assigneeId;

    public Todo(int toDoId, String title, String description, LocalDate deadLine, boolean done, int assigneeId) {
        this.toDoId = toDoId;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public Todo(String title, String description, LocalDate deadLine, boolean done, int assigneeId) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public Todo() {
    }

    public int getToDoId() {
        return toDoId;
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return toDoId == todo.toDoId && done == todo.done && assigneeId == todo.assigneeId && Objects.equals(title, todo.title) && Objects.equals(description, todo.description) && Objects.equals(deadLine, todo.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toDoId, title, description, deadLine, done, assigneeId);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "toDoId=" + toDoId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", assigneeId=" + assigneeId +
                '}';
    }
}
