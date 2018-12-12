package fr.if26.projet.knotedge_if26.dao;

import android.media.Image;

import fr.if26.projet.knotedge_if26.entity.Profile;

public interface PersistanceProfile {

    void addProfile(Profile p);

    void updateProfilePhoto(Image i);

    void createDefaultUser();

    int countProfile();

    Profile getProfile(int id);
}
