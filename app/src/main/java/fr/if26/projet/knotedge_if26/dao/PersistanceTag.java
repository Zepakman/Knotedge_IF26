package fr.if26.projet.knotedge_if26.dao;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Tag;

public interface PersistanceTag {

    void addTag(Tag t);

    void removeTag(Tag t);

    void updateTag(Tag t);

    ArrayList<Tag> getAllTags();

    ArrayList<String> getAllTagsName();

    Tag getTag(String t);

    int countTag();
}
