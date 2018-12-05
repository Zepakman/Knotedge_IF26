package fr.if26.projet.knotedge_if26.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Person;

public class KnotedgePersistance extends SQLiteOpenHelper implements Persistance {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "knotedge.db";
    private static final String TABLE_PERSON = "person";
    private static final String PERSON_NAME = "person_name";
    private static final String PERSON_DATE_BIRTH = "person_date";
    private static final String PERSON_ID = "person_id";
    private static final String PERSON_DESCRIPTION = "person_description";
    private int pk_person = 0;

    private static final String TABLE_EVENT = "event";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DATE = "event_date";
    private static final String EVENT_ID = "event_id";
    private static final String EVENT_DESCRIPTION = "event_description";
    private int pk_event = 0;


    public KnotedgePersistance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String table_person_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PERSON + "(" +
                PERSON_ID + " INTEGER primary key, " +
                PERSON_NAME + " TEXT, " +
                PERSON_DATE_BIRTH + " DATE, " +
                PERSON_DESCRIPTION + " TEXT" + ")";
        final String table_event_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_EVENT + "(" +
                EVENT_ID + " INTEGER primary key, " +
                EVENT_NAME + " TEXT, " +
                EVENT_DATE + " DATE, " +
                EVENT_DESCRIPTION + " TEXT" + ")";
        db.execSQL(table_person_create);
        db.execSQL(table_event_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addPerson(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERSON_ID, pk_increment("person"));
        values.put(PERSON_NAME, p.getName());
        values.put(PERSON_DATE_BIRTH, p.getDate());
        values.put(PERSON_DESCRIPTION, p.getDescription());

        db.insert(TABLE_PERSON, null, values);
        db.close();
    }

    @Override
    public void addEvent(Event t) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_ID, pk_increment("event"));
        values.put(EVENT_NAME, t.getName());
        values.put(EVENT_DATE, t.getDate());
        values.put(EVENT_DESCRIPTION, t.getDescription());

        db.insert(TABLE_EVENT, null, values);
        db.close();
    }


    public int pk_increment(String type) {
        switch (type) {
            case "person" :
                this.pk_person++;
                return pk_person;
            case "event" :
                this.pk_event++;
                return pk_event;
            default:
                return 0;
        }

    }
}
