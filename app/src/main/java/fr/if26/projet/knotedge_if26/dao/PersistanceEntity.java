package fr.if26.projet.knotedge_if26.dao;


import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Tag;

public interface PersistanceEntity {

    /* ----------------- add ----------------- */

    void addPerson(Person p);

    void addEvent(Event t);

    void addPlace(Place p);

    void addObject(Object o);

    void addBook(Book b);

    void addTag(Tag t);

    void addNote(Note n);

    /* ----------------- update ----------------- */

    /* ----------------- get All ----------------- */

    ArrayList<Object> getAllObjects();

    ArrayList<Person> getAllPersons();

    ArrayList<Event> getAllEvents();

    ArrayList<Place> getAllPlaces();

    ArrayList<Book> getAllBooks();

    ArrayList<Note> getAllNotes();

    ArrayList<Tag> getAllTags();

    /* ----------------- get ----------------- */

    Note getNote(int id);

    /* ----------------- count ----------------- */
    int countNote();

    int countClass();

    int countTag();

    int countBook();

}