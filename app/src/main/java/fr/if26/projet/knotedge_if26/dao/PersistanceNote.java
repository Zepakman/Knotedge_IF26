package fr.if26.projet.knotedge_if26.dao;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Note;

public interface PersistanceNote {

    void addNote(Note n);

    void removeNote(Note n);

    void updateNote(Note n);

    int countNote();

    ArrayList<Note> getAllNotes();

    Note getNoteById(int id);
}
