package fr.if26.projet.knotedge_if26.dao;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Tag;

public interface PersistanceRelation {

    void addTagObject(Tag t, Object o);

    void addTagBook(Tag t, Book b);

    void addRelationObjects(Object o1, Object o2);
}
