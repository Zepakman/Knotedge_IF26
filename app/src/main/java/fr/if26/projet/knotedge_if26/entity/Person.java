package fr.if26.projet.knotedge_if26.entity;

public class Person extends Object{

    private String date;

    public Person(String name, String description, String date) {
        super(name, description);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
