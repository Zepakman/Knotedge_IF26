package fr.if26.projet.knotedge_if26.entity;

public class Note {

    private int id;
    private String title;
    private String content;
    private String date_create;
    private String date_edit;

    public Note (String title, String content, String date_create, String date_edit) {
        this.title = title;
        this.content = content;
        this.date_create = date_create;
        this.date_edit = date_edit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDate_edit() {
        return date_edit;
    }

    public void setDate_edit(String date_edit) {
        this.date_edit = date_edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
