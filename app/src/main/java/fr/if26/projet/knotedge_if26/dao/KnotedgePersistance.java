package fr.if26.projet.knotedge_if26.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;

public class KnotedgePersistance extends SQLiteOpenHelper implements PersistanceEntity, PersistanceProfile, PersistanceRelation {

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
    private static final String TABLE_RELATION_OBJECT_NOTE = "relation_object_note";
    private static final String TABLE_RELATION_BOOK_NOTE = "relation_book_note";

    private static final String OBJECT_NAME = "name_obj";
    private static final String OBJECT_DATE = "date_obj";
    private static final String OBJECT_ID = "id_obj";
    private static final String OBJECT_DESCRIPTION = "description_obj";
    private static final String OBJECT_TYPE = "type_obj";
    private static final String OBJECT_TYPE_PERSON = "person";
    private static final String OBJECT_TYPE_EVENT = "event";
    private static final String OBJECT_TYPE_PLACE = "place";
    private static final String OBJECT_TYPE_OBJECT = "object";

    private static final String BOOK_ID = "book_id";
    private static final String BOOK_TITLE = "book_title";
    private static final String BOOK_AUTHOR = "book_author";
    private static final String BOOK_DESCRIPTION = "book_description";
    private static final String BOOK_DATE = "book_date";


    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";

    private static final String RELATION_NAME = "relation_name";

    private static final String NOTE_ID = "note_id";
    private static final String NOTE_TITLE = "note_title";
    private static final String NOTE_CONTENT = "note_content";
    private static final String NOTE_DATE_CREATE = "note_date_create";
    private static final String NOTE_DATE_EDIT = "note_date_edit";

    private static final String PROFILE_ID = "user_id";
    private static final String PROFILE_FIRST_NAME = "user_first_name";
    private static final String PROFILE_LAST_NAME = "user_last_name";
    private static final String PROFILE_EMAIL = "user_email";
    private static final String PROFILE_PHOTO = "user_photo";

    public KnotedgePersistance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String table_profile_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PROFILE + "(" +
                PROFILE_ID + " INTEGER primary key autoincrement, " +
                PROFILE_FIRST_NAME + " TEXT, " +
                PROFILE_LAST_NAME + " TEXT, " +
                PROFILE_EMAIL + " TEXT, " +
                PROFILE_PHOTO + " TEXT" + ")";

        final String table_object_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_OBJECT + "(" +
                OBJECT_ID + " INTEGER primary key autoincrement, " +
                OBJECT_NAME + " TEXT, " +
                OBJECT_DESCRIPTION + " TEXT," +
                OBJECT_DATE + " DATE, " +
                OBJECT_TYPE + " TEXT" + ")";

        final String table_book_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_BOOK + "(" +
                BOOK_ID + " INTEGER primary key autoincrement, " +
                BOOK_TITLE + " TEXT, " +
                BOOK_DESCRIPTION + " TEXT, " +
                BOOK_AUTHOR + " TEXT, " +
                BOOK_DATE + " TEXT" + ")";

        final String table_tag_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_TAG + "(" +
                TAG_ID + " INTEGER primary key autoincrement, " +
                TAG_NAME + " TEXT " + ")";

        final String table_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NOTE + "(" +
                NOTE_ID + " INTEGER primary key autoincrement, " +
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

        final String table_relation_object_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_OBJECT_NOTE + "(" +
                OBJECT_ID + " INTEGER, " +
                NOTE_ID + " INTEGER, " +
                "PRIMARY KEY(" + OBJECT_ID+ "," + NOTE_ID +"))";

        final String table_relation_book_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_BOOK_NOTE + "(" +
                BOOK_ID + " INTEGER, " +
                NOTE_ID + " INTEGER, " +
                "PRIMARY KEY(" + BOOK_ID+ "," + NOTE_ID +"))";

        db.execSQL(table_profile_create);
        db.execSQL(table_object_create);
        db.execSQL(table_book_create);
        db.execSQL(table_tag_create);
        db.execSQL(table_note_create);
        db.execSQL(table_relation_tag_obj_create);
        db.execSQL(table_relation_objs_create);
        db.execSQL(table_relation_tag_book_create);
        db.execSQL(table_relation_object_note_create);
        db.execSQL(table_relation_book_note_create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addPerson(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECT_NAME, p.getName());
        values.put(OBJECT_DESCRIPTION, p.getDescription());
        values.put(OBJECT_DATE, p.getDate());
        values.put(OBJECT_TYPE, OBJECT_TYPE_PERSON);

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addEvent(Event t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECT_NAME, t.getName());
        values.put(OBJECT_DESCRIPTION, t.getDescription());
        values.put(OBJECT_DATE, t.getDate());
        values.put(OBJECT_TYPE, OBJECT_TYPE_EVENT);

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addPlace(Place p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECT_NAME, p.getName());
        values.put(OBJECT_DESCRIPTION, p.getDescription());
        values.put(OBJECT_DATE, "");
        values.put(OBJECT_TYPE, OBJECT_TYPE_PLACE);

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addObject(Object o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECT_NAME, o.getName());
        values.put(OBJECT_DESCRIPTION, o.getDescription());
        values.put(OBJECT_DATE, "");
        values.put(OBJECT_TYPE, OBJECT_TYPE_OBJECT);

        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    @Override
    public void addProfile(Profile p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_FIRST_NAME, p.getFirstName());
        values.put(PROFILE_LAST_NAME, p.getLastName());
        values.put(PROFILE_EMAIL, p.getEmail());
        values.put(PROFILE_PHOTO, p.getPhoto());

        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    @Override
    public void addBook(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_TITLE, b.getName());
        values.put(BOOK_DESCRIPTION, b.getDescription());
        values.put(BOOK_AUTHOR, b.getAuthor());
        values.put(BOOK_DATE, b.getDate());

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    @Override
    public void addTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAG_NAME, t.getName());

        db.insert(TABLE_TAG, null, values);
        db.close();
    }

    @Override
    public void addNote(Note n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, n.getTitle());
        values.put(NOTE_CONTENT, n.getContent());
        values.put(NOTE_DATE_CREATE, n.getDate_create());
        values.put(NOTE_DATE_EDIT, n.getDate_edit());

        db.insert(TABLE_NOTE, null, values);
        db.close();
    }

    @Override
    public ArrayList<Object> getAllObjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_OBJECT + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Object> objectList = new ArrayList<>();
        Object o;
        while (cursor.moveToNext()) {
            o = new Object("","", "", "");
            o.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            o.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            o.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            o.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            o.setType(cursor.getString(cursor.getColumnIndex(OBJECT_TYPE)));
            objectList.add(o);
        }
        cursor.close();
        db.close();
        return objectList;
    }

    @Override
    public ArrayList<Person> getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + OBJECT_TYPE + " = " + OBJECT_TYPE_PERSON + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Person> personList = new ArrayList<>();
        Person p;
        while (cursor.moveToNext()) {
            p = new Person("", "","");
            p.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            p.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            p.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            p.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            personList.add(p);
        }
        cursor.close();
        db.close();
        return personList;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + OBJECT_TYPE + " = " + OBJECT_TYPE_EVENT + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Event> eventList = new ArrayList<>();
        Event e;
        while (cursor.moveToNext()) {
            e = new Event("", "", "");
            e.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            e.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            e.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            e.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            eventList.add(e);
        }
        cursor.close();
        db.close();
        return eventList;
    }

    @Override
    public ArrayList<Place> getAllPlaces() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + OBJECT_TYPE + " = " + OBJECT_TYPE_PLACE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Place> placeList = new ArrayList<>();
        Place p;
        while (cursor.moveToNext()) {
            p = new Place("", "", "");
            p.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            p.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            p.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            p.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            placeList.add(p);
        }
        cursor.close();
        db.close();
        return placeList;
    }

    @Override
    public ArrayList<Book> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_BOOK + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Book> bookList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Book b = new Book("", "", "", "");
            b.setId(cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
            b.setName(cursor.getString(cursor.getColumnIndex(BOOK_TITLE)));
            b.setDescription(cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION)));
            b.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
            b.setDate(cursor.getString(cursor.getColumnIndex(BOOK_DATE)));
            bookList.add(b);
        }
        cursor.close();
        db.close();
        return bookList;
    }

    @Override
    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NOTE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Note> noteList = new ArrayList<>();
        Note note;
        while (cursor.moveToNext()) {
            note = new Note("", "", "", "");
            note.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(NOTE_CONTENT)));
            note.setDate_create(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATE)));
            note.setDate_edit(cursor.getString(cursor.getColumnIndex(NOTE_DATE_EDIT)));
            noteList.add(note);
        }
        cursor.close();
        db.close();

        return noteList;
    }

    @Override
    public ArrayList<Tag> getAllTags() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TAG + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Tag> tagList = new ArrayList<>();
        Tag t;
        while (cursor.moveToNext()) {
            t = new Tag("");
            t.setId(cursor.getInt(cursor.getColumnIndex(TAG_ID)));
            t.setName(cursor.getString(cursor.getColumnIndex(TAG_NAME)));
            tagList.add(t);
        }
        cursor.close();
        db.close();

        return tagList;
    }


    @Override
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTE, null, NOTE_ID + "= ? ", new String[]{id+""}, null, null, null);
        Note note = null;
        if (cursor.moveToNext()) {
            note = null;
            note.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(NOTE_CONTENT)));
            note.setDate_create(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATE)));
            note.setDate_edit(cursor.getString(cursor.getColumnIndex(NOTE_DATE_EDIT)));
        }
        cursor.close();
        db.close();
        return note;
    }

    @Override
    public int countNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTE , null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public int countBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOK , null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public int countClass() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_OBJECT , null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public int countTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TAG , null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
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

    @Override
    public void updateProfilePhoto(Image i) {

    }


    @Override
    public int countProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PROFILE , null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Profile getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE, null, PROFILE_ID + "= ? ", new String[]{id+""}, null, null, null);
        Profile profile = new Profile("","","","");
        if (cursor.moveToNext()) {
            profile = new Profile("","","","");
            profile.setId(cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
            profile.setFirstName(cursor.getString(cursor.getColumnIndex(PROFILE_FIRST_NAME)));
            profile.setLastName(cursor.getString(cursor.getColumnIndex(PROFILE_LAST_NAME)));
            profile.setEmail(cursor.getString(cursor.getColumnIndex(PROFILE_EMAIL)));
            profile.setPhoto(cursor.getString(cursor.getColumnIndex(PROFILE_PHOTO)));
        }
        cursor.close();
        db.close();
        return profile;
    }

    @Override
    public ArrayList<Profile> getAllProfiles() {
        return null;
    }

    @Override
    public void createDefaultUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_ID, 1);
        values.put(PROFILE_FIRST_NAME, "Sifei");
        values.put(PROFILE_LAST_NAME, "LI");
        values.put(PROFILE_EMAIL, "doe.john@utt.fr");
        values.put(PROFILE_PHOTO, "");

        db.insert(TABLE_PROFILE, null, values);
        db.close();

    }

    public void testInit() {
        Person p1 = new Person("p1", "123", "2014-02-02");
        addPerson(p1);
        Tag t1 = new Tag("tag1");
        addTag(t1);
        Note n1 = new Note("title1", "contentnote","2014-02-02", "2014-02-02");
        addNote(n1);
    }
}
