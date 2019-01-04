package fr.if26.projet.knotedge_if26;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;

public interface TransmissionListener {

    void createNewObject(String name, String date, String description, int type);

    void createNewBook(String name, String description, String author, String date);

    void createNewNote(String title, String content);

    void createNewRelationTagObject(Tag t, Object o);

    void createNewRelationTagBook(Tag t, Book b);

    void createNewRelationObjectBook(Object o, Book b);

    void createNewRelationBooks(Book b1, Book b2);

    void createNewRelationObjects(Object o1, Object o2);

    void createNewRelationNoteBook(Note n, Book b);

    void creatNewRelationNoteObject(Note n, Object o);

    void loadFragmentAllClasses();

    void loadFragmentAllNotes();

    void loadFragmentAllBooks();

    void loadFragmentAllTags();

    void loadFragmentProfile();

    void loadFragmentSettingsProfile();

    void modifyProfile(Profile p);

    void loadDetailBook(int id);

    void loadDetailNote(int id);

    void loadDetailObject(int id);

    void loadEditNote(int id);

    void loadEditClass(int id);

    void loadEditBook(int id);

    void loadFragmentViewTagTimeLine();

    void loadFragmentViewTagRelation();
}
