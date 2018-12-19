package fr.if26.projet.knotedge_if26;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.entity.Object;

public interface TransmissionListener {

    void createNewObject(String name, String date, String description, int type);

    void createNewBook(String name, String description, String author, String date);

    void createNewNote(String title, String content);

    void createNewRelationTagObject(Tag t, Object o);

    void createNewRelationTagBook(Tag t, Book b);

    void loadFragmentAllClasses();

    void loadFragmentAllNotes();

    void loadFragmentAllBooks();

    void loadFragmentAllTags();

    void loadFragmentSettingsProfile();

    void modifyProfile(Profile p);

}
