package fr.if26.projet.knotedge_if26.dao;


import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;

public interface Persistance {

    void addPerson(Person p);

    void addEvent(Event t);

    void addPlace(Place p);

    void addObject(Object o);

    void addProfile(Profile p);

    void addBook(Book b);

    void addTag(Tag t);

    void addNote(Note n);

    void addTagObject(Tag t, Object o);

    void addTagBook(Tag t, Book b);

    void addRelationObjects(Object o1, Object o2);


}
