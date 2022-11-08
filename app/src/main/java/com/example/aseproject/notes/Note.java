package com.example.aseproject.notes;

public class Note {
    private String id;
    private String title;
    private String note;

    Note(String id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
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
