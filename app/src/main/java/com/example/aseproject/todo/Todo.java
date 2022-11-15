package com.example.aseproject.todo;

public class Todo {
    private String id;
    private String title;
    private String note;

    Todo(String id, String title, String todo) {
        this.id = id;
        this.title = title;
        this.note = todo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
