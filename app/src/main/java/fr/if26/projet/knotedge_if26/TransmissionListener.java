package fr.if26.projet.knotedge_if26;

import fr.if26.projet.knotedge_if26.entity.Profile;

public interface TransmissionListener {

    void createNewObject(String name, String date, String description, int type);

    void createNewBook(String name, String description, String author, String date);

    void createNewNote(String title, String content);

    void loadFragmentAllClasses();

    void loadFragmentAllNotes();

    void loadFragmentAllBooks();

    void loadFragmentAllTags();

    void loadFragmentSettingsProfile();

    void modifyProfile(Profile p);

}
