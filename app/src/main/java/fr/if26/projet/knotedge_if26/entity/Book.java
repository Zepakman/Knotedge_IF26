package fr.if26.projet.knotedge_if26.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String name;
    private String author;
    private String description;
    private String date;

    public Book(String name, String description, String author, String date) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
