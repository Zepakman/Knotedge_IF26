package fr.if26.projet.knotedge_if26.dao;


import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;

public interface PersistanceEntity {

    /* ----------------- add ----------------- */

    void addPerson(Person p);

    void addEvent(Event t);

    void addPlace(Place p);

    void addObject(Object o);

    void addBook(Book b);

    /* ----------------- remove ----------------- */

    void removeBook(Book b);

    void removeObject(Object o);


    /* ----------------- update ----------------- */

    void updateBook(Book b);

    void updateClass(Object o);


    /* ----------------- get All ----------------- */

    ArrayList<Object> getAllObjects();

    ArrayList<Person> getAllPersons();

    ArrayList<Event> getAllEvents();

    ArrayList<Place> getAllPlaces();

    ArrayList<Book> getAllBooks();


    /* ----------------- getters ----------------- */


    Object getObjectById(int id);

    Book getBookById(int id);

    Object getLastObject();

    Book getLastBook();

    /* ----------------- count ----------------- */

    int countClass();

    int countBook();

}
