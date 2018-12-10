package fr.if26.projet.knotedge_if26.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;

public class KnotedgePersistance extends SQLiteOpenHelper implements Persistance {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "knotedge.db";

    private static final String TABLE_OBJECT = "object";
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_TAG = "tag";
    private static final String TABLE_RELATION_TAG_OBJECT = "relation_tag_object";
    private static final String TABLE_RELATION_OBJECTS = "relation_objects";
    private static final String TABLE_RELATION_TAG_BOOK = "relation_tag_book";
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_NOTE = "note";

    private static final String OBJECT_NAME = "name_obj";
    private static final String OBJECT_DATE = "date_obj";
    private static final String OBJECT_ID = "id_obj";
    private static final String OBJECT_DESCRIPTION = "description_obj";
    private static final String OBJECT_TYPE = "type_obj";
    private int pk_object = 0;

    private static final String BOOK_TITLE = "book_title";
    private static final String BOOK_AUTHOR = "book_author";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_DESCRIPTION = "book_description";
    private int pk_book = 0;

    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";
    private int pk_tag=0;

    private static final String RELATION_NAME = "relation_name";

    private static final String NOTE_ID = "note_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_DATE_CREATE = "note_date_create";
    private static final String NOTE_DATE_EDIT = "note_date_edit";
    private int pk_note=0;

    private static final String PROFILE_ID = "user_id";
    private static final String PROFILE_NAME = "user_name";
    private int pk_profile = 0;

    public KnotedgePersistance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String table_profile_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PROFILE + "(" +
                PROFILE_ID + " INTEGER primary key, " +
                PROFILE_NAME + " TEXT" + ")";

        final String table_object_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_OBJECT + "(" +
                OBJECT_ID + " INTEGER primary key, " +
                OBJECT_NAME + " TEXT, " +
                OBJECT_DATE + " DATE, " +
                OBJECT_DESCRIPTION + " TEXT," +
                OBJECT_TYPE + " TEXT" + ")";

        final String table_book_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_BOOK + "(" +
                BOOK_ID + " INTEGER primary key, " +
                BOOK_TITLE + " TEXT, " +
                BOOK_AUTHOR + " TEXT, " +
                BOOK_DESCRIPTION + " TEXT" + ")";

        final String table_tag_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_TAG + "(" +
                TAG_ID + " INTEGER primary key, " +
                TAG_NAME + " TEXT " + ")";

        final String table_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NOTE + "(" +
                NOTE_ID + " INTEGER primary key, " +
                NOTE_TITLE + " TEXT, " +
                NOTE_CONTENT + " TEXT, " +
                NOTE_DATE_CREATE + " TEXT, " +
                NOTE_DATE_EDIT + " TEXT " + ")";

        final String table_relation_tag_obj_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_TAG_OBJECT + "(" +
                TAG_ID + " INTEGER, " +
                OBJECT_ID + " INTEGER, " +
                 "PRIMARY KEY(" + TAG_ID+ "," + OBJECT_ID +"))";

        final String table_relation_objs_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_OBJECTS + "(" +
                OBJECT_ID + "1 INTEGER, " +
                OBJECT_ID + "2 INTEGER, " +
                RELATION_NAME + ", TEXT, " +
                "PRIMARY KEY(" + OBJECT_ID+ "1," + OBJECT_ID +"2))";

        final String table_relation_tag_book_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_TAG_BOOK + "(" +
                TAG_ID + " INTEGER, " +
                BOOK_ID + " INTEGER, " +
                "PRIMARY KEY(" + TAG_ID+ "," + BOOK_ID +"))";

        db.execSQL(table_profile_create);
        db.execSQL(table_object_create);
        db.execSQL(table_book_create);
        db.execSQL(table_tag_create);
        db.execSQL(table_note_create);
        db.execSQL(table_relation_tag_obj_create);
        db.execSQL(table_relation_objs_create);
        db.execSQL(table_relation_tag_book_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addPerson(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("object");

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, id);
        p.setId(id);
        values.put(OBJECT_NAME, p.getName());
        values.put(OBJECT_DATE, p.getDate());
        values.put(OBJECT_DESCRIPTION, p.getDescription());
        values.put(OBJECT_TYPE, "person");

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addEvent(Event t) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("object");

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, id);
        t.setId(id);
        values.put(OBJECT_NAME, t.getName());
        values.put(OBJECT_DATE, t.getDate());
        values.put(OBJECT_DESCRIPTION, t.getDescription());
        values.put(OBJECT_TYPE, "event");

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addPlace(Place p) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("object");

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, id);
        p.setId(id);
        values.put(OBJECT_NAME, p.getName());
        values.put(OBJECT_DATE, "");
        values.put(OBJECT_DESCRIPTION, p.getDescription());
        values.put(OBJECT_TYPE, "place");

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addObject(Object o) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("object");

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, id);
        o.setId(id);
        values.put(OBJECT_NAME, o.getName());
        values.put(OBJECT_DATE, "");
        values.put(OBJECT_DESCRIPTION, o.getDescription());
        values.put(OBJECT_TYPE, "object");

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addProfile(Profile p) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("profile");

        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, id);
        p.setId(id);
        values.put(PROFILE_NAME, p.getName());

        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    @Override
    public void addBook(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("book");

        ContentValues values = new ContentValues();
        values.put(BOOK_ID, id);
        b.setId(id);
        values.put(BOOK_TITLE, b.getName());
        values.put(BOOK_AUTHOR, b.getAuthor());
        values.put(BOOK_DESCRIPTION, b.getDescription());

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    @Override
    public void addTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("tag");

        ContentValues values = new ContentValues();
        values.put(TAG_ID, id);
        t.setId(id);
        values.put(TAG_NAME, t.getName());

        db.insert(TABLE_TAG, null, values);
        db.close();
    }

    @Override
    public void addNote(Note n) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = pk_increment("note");

        ContentValues values = new ContentValues();
        values.put(NOTE_ID, id);
        n.setId(id);
        values.put(NOTE_TITLE, n.getTitle());
        values.put(NOTE_CONTENT, n.getContent());
        values.put(NOTE_DATE_CREATE, n.getDate_create());
        values.put(NOTE_DATE_EDIT, n.getDate_edit());

        db.insert(TABLE_NOTE, null, values);
        db.close();
    }

    @Override
    public void addTagObject(Tag t, Object o) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_ID, t.getId());
        values.put(OBJECT_ID, o.getId());

        db.insert(TABLE_RELATION_TAG_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addTagBook(Tag t, Book b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_ID, t.getId());
        values.put(BOOK_ID, b.getId());

        db.insert(TABLE_RELATION_TAG_BOOK, null, values);
        db.close();
    }

    @Override
    public void addRelationObjects(Object o1, Object o2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID+"1", o1.getId());
        values.put(OBJECT_ID+"2", o2.getId());

        db.insert(TABLE_RELATION_OBJECTS, null, values);
        db.close();
    }


    public int pk_increment(String type) {
        switch (type) {
            case "object" :
                this.pk_object++;
                return pk_object;
            case "book" :
                this.pk_book++;
                return pk_book;
            case "tag" :
                this.pk_tag++;
                return pk_tag;
            case "note" :
                this.pk_note++;
                return pk_note;
            case "profile" :
                this.pk_profile++;
                return pk_profile;
            default:
                return 0;
        }

    }
}
