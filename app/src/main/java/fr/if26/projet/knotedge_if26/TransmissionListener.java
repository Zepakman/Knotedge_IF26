package fr.if26.projet.knotedge_if26;

public interface TransmissionListener {

    void createNewObject(String name, String date, String description, int type);

    void createNewBook(String name, String description, String author, String date);

    boolean createNewNote(String title, String content);

}
