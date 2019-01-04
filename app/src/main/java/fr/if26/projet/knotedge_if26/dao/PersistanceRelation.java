package fr.if26.projet.knotedge_if26.dao;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Tag;

public interface PersistanceRelation {

    /* ----------------- add ----------------- */

    void addTagObject(Tag t, Object o);

    void addTagBook(Tag t, Book b);

    void addRelationObjects(Object o1, Object o2);

    void addNoteObject(Note n, Object o);

    void addRelationBooks(Book b1, Book b2);

    void addObjectBook(Object o, Book b);

    void addNoteBook(Note n, Book b);

    /* ----------------- count ----------------- */

    int countRelationsBookTag();

    int countRelationsObjectTag();

    /* ----------------- getters ----------------- */
    ArrayList<String> getAllTagsByBook(int bkId);

    ArrayList<String> getAllTagsByClass(int objId);

    ArrayList<Note> getAllNotesByBook(int bkId);

    ArrayList<Note> getAllNoteByObject(int clsId);

    ArrayList<Object> getAllObjectsByTagName(String nameTag);

    ArrayList<Book> getAllBooksByTagName(String nameTag);

    ArrayList<Book> getAllBooksByBook(int bkId);

    //ArrayList<Book> getAllBooksByObjects(int objId);


    /* ----------------- remove ----------------- */

    void removeAllRelationsWithObject(int clsId);

    void removeAllRelationsWithBook(int bkId);

    void removeAllRelationsWithNote(int noteId);

    void removeAllRelationsWithTag(int tagId);
}
