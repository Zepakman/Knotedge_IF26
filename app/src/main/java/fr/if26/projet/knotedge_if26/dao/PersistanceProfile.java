package fr.if26.projet.knotedge_if26.dao;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Profile;

public interface PersistanceProfile {

    void addProfile(Profile p);

    void updateProfile(Profile p);

    int countProfile();

    Profile getProfile(int id);

    ArrayList<Profile> getAllProfiles();

}
