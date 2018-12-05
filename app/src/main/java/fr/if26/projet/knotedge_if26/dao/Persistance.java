package fr.if26.projet.knotedge_if26.dao;

import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Person;

public interface Persistance {

    void addPerson(Person p);

    void addEvent(Event t);

}
