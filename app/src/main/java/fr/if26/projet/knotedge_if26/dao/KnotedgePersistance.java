package fr.if26.projet.knotedge_if26.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Profile;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.util.Tools;

public class KnotedgePersistance extends SQLiteOpenHelper implements PersistanceEntity, PersistanceProfile, PersistanceRelation, PersistanceTag, PersistanceNote {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "knotedge.db";

    private static final String TABLE_OBJECT = "object";
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_TAG = "tag";
    private static final String TABLE_RELATION_TAG_OBJECT = "relation_tag_object";
    private static final String TABLE_RELATION_OBJECTS = "relation_objects";
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_NOTE = "note";
    private static final String TABLE_RELATION_OBJECT_NOTE = "relation_object_note";
    private static final String TABLE_RELATION_BOOK_NOTE = "relation_book_note";
    private static final String TABLE_RELATION_TAG_BOOK = "relation_tag_book";
    private static final String TABLE_RELATION_BOOKS = "relation_books";
    private static final String TABLE_RELATION_OBJECT_BOOK = "relation_object_book";

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
                PROFILE_PHOTO + " BLOB" + ")";

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
                "PRIMARY KEY(" + TAG_ID + "," + OBJECT_ID + "))";

        final String table_relation_objs_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_OBJECTS + "(" +
                OBJECT_ID + "1 INTEGER, " +
                OBJECT_ID + "2 INTEGER, " +
                RELATION_NAME + ", TEXT, " +
                "PRIMARY KEY(" + OBJECT_ID + "1," + OBJECT_ID + "2))";

        final String table_relation_tag_book_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_TAG_BOOK + "(" +
                TAG_ID + " INTEGER, " +
                BOOK_ID + " INTEGER " + ")";

        final String table_relation_object_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_OBJECT_NOTE + "(" +
                OBJECT_ID + " INTEGER, " +
                NOTE_ID + " INTEGER, " +
                "PRIMARY KEY(" + OBJECT_ID + "," + NOTE_ID + "))";

        final String table_relation_book_note_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_BOOK_NOTE + "(" +
                BOOK_ID + " INTEGER, " +
                NOTE_ID + " INTEGER, " +
                "PRIMARY KEY(" + BOOK_ID + "," + NOTE_ID + "))";

        final String table_relation_books_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_BOOKS+ "(" +
                BOOK_ID + "1 INTEGER, " +
                BOOK_ID + "2 INTEGER, " +
                "PRIMARY KEY(" + BOOK_ID + "1," + BOOK_ID + "2))";

        final String table_relation_object_book_create = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RELATION_OBJECT_BOOK + "(" +
                OBJECT_ID + " INTEGER, " +
                BOOK_ID + " INTEGER, " +
                "PRIMARY KEY(" + OBJECT_ID + "," + BOOK_ID + "))";

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
        db.execSQL(table_relation_object_book_create);
        db.execSQL(table_relation_books_create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
          ================================  ADD OBJECT  ===================================
    */

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


     /*
          ================================  *REMOVE  ===================================
     */

    @Override
    public void removeBook(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BOOK, BOOK_ID + " = ?", new String[]{String.valueOf(b.getId())});
        db.delete(TABLE_RELATION_TAG_BOOK, BOOK_ID + " = ?", new String[]{String.valueOf(b.getId())});
        db.close();
    }

    @Override
    public void removeObject(Object o) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OBJECT, OBJECT_ID + " = ?", new String[]{String.valueOf(o.getId())});
        db.delete(TABLE_RELATION_TAG_OBJECT, OBJECT_ID + " = ?", new String[]{String.valueOf(o.getId())});
        db.close();
    }

    /*
          ================================  *UPDATE  ===================================
     */

    @Override
    public void updateBook(Book b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_TITLE, b.getName());
        contentValues.put(BOOK_DESCRIPTION, b.getDescription());
        contentValues.put(BOOK_AUTHOR, b.getAuthor());
        contentValues.put(BOOK_DATE, b.getDate());
        db.update(TABLE_BOOK, contentValues, BOOK_ID + "=?", new String[]{b.getId() + ""});
        db.close();
    }

    @Override
    public void updateClass(Object o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OBJECT_NAME, o.getName());
        contentValues.put(OBJECT_DESCRIPTION, o.getDescription());
        contentValues.put(OBJECT_DATE, o.getDate());
        contentValues.put(OBJECT_TYPE, o.getType());
        db.update(TABLE_OBJECT, contentValues, OBJECT_ID + "=?", new String[]{o.getId() + ""});
        db.close();
    }

    /*
          ================================  *GETALL  ===================================
    */

    @Override
    public ArrayList<Object> getAllObjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_OBJECT + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Object> objectList = new ArrayList<>();
        Object o;
        while (cursor.moveToNext()) {
            o = new Object("", "", "", "");
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
            p = new Person("", "", "");
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

    /*
          ================================  *GETONE  ===================================
    */

    @Override
    public Object getObjectById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_OBJECT, null, OBJECT_ID + "= ?", new String[]{id + ""}, null, null, null);
        Object o = new Object("", "", "", "");
        if (cursor.moveToNext()) {
            o.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            o.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            o.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            o.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            o.setType(cursor.getString(cursor.getColumnIndex(OBJECT_TYPE)));
        }
        cursor.close();
        db.close();
        return o;
    }


    @Override
    public Object getLastObject() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_OBJECT;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        Object o = new Object("", "", "", "");
        o.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
        o.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
        o.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
        o.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
        o.setType(cursor.getString(cursor.getColumnIndex(OBJECT_TYPE)));
        cursor.close();
        db.close();
        return o;
    }

    @Override
    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOK, null, BOOK_ID + "= ?", new String[]{id + ""}, null, null, null);
        Book b = new Book("", "", "", "");
        if (cursor.moveToNext()) {
            b.setId(cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
            b.setName(cursor.getString(cursor.getColumnIndex(BOOK_TITLE)));
            b.setDescription(cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION)));
            b.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
            b.setDate(cursor.getString(cursor.getColumnIndex(BOOK_DATE)));
        }
        cursor.close();
        db.close();
        return b;
    }

    @Override
    public Book getBookByTitle (String book) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOK, null, BOOK_TITLE + "= ?", new String[]{book + ""}, null, null, null);
        Book b = new Book("", "", "", "");
        int id = 0;

        if (cursor.moveToNext()) {
            b.setId(cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
            b.setName(cursor.getString(cursor.getColumnIndex(BOOK_TITLE)));
            b.setDescription(cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION)));
            b.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
            b.setDate(cursor.getString(cursor.getColumnIndex(BOOK_DATE)));
        }
        cursor.close();
        db.close();
        return b;
    }

    @Override
    public Note getNoteByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTE, null, NOTE_TITLE + "= ?", new String[]{title + ""}, null, null, null);
        Note n = new Note("", "", "", "");
        int id = 0;

        if (cursor.moveToNext()) {
            n.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
            n.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
            n.setContent(cursor.getString(cursor.getColumnIndex(NOTE_CONTENT)));
            n.setDate_edit(cursor.getString(cursor.getColumnIndex(NOTE_DATE_EDIT)));
            n.setDate_create(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATE)));
        }
        cursor.close();
        db.close();
        return n;
    }

    @Override
    public Object getObjectByName (String obj) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_OBJECT, null, OBJECT_NAME + "= ?", new String[]{obj + ""}, null, null, null);
        Object o = new Object("", "", "", "");
        int id = 0;

        if (cursor.moveToNext()) {
            o.setId(cursor.getInt(cursor.getColumnIndex(OBJECT_ID)));
            o.setName(cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            o.setDescription(cursor.getString(cursor.getColumnIndex(OBJECT_DESCRIPTION)));
            o.setDate(cursor.getString(cursor.getColumnIndex(OBJECT_DATE)));
            o.setType(cursor.getString(cursor.getColumnIndex(OBJECT_TYPE)));
        }
        cursor.close();
        db.close();
        return o;
    }

    @Override
    public Book getLastBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOK;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        Book b;
        b = new Book("", "", " ", "");
        b.setId(cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
        b.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
        b.setDescription(cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION)));
        b.setDate(cursor.getString(cursor.getColumnIndex(BOOK_DATE)));
        b.setName(cursor.getString(cursor.getColumnIndex(BOOK_TITLE)));
        cursor.close();
        db.close();
        return b;
    }

    @Override
    public Note getLastNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        Note n;
        n = new Note("", "", " ", "");
        n.setId(cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
        n.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
        n.setContent(cursor.getString(cursor.getColumnIndex(NOTE_CONTENT)));
        n.setDate_create(cursor.getString(cursor.getColumnIndex(NOTE_DATE_CREATE)));
        n.setDate_edit(cursor.getString(cursor.getColumnIndex(NOTE_DATE_EDIT)));
        cursor.close();
        db.close();
        return n;
    }

    /*
          ================================  *COUNT  ===================================
     */

    @Override
    public int countBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOK, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public int countClass() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_OBJECT, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }


    @Override
    public int countRelationsBookTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_RELATION_TAG_BOOK, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public int countRelationsObjectTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_RELATION_TAG_OBJECT, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }



    /*
          ================================  RELATIONS  ===================================
    */

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
    public void addObjectBook(Object o, Book b){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID,o.getId());
        values.put(BOOK_ID, b.getId());

        db.insert(TABLE_RELATION_OBJECT_BOOK, null, values);
        db.close();
    }

    @Override
    public void addRelationObjects(Object o1, Object o2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID + "1", o1.getId());
        values.put(OBJECT_ID + "2", o2.getId());

        db.insert(TABLE_RELATION_OBJECTS, null, values);
        db.close();
    }

    @Override
    public void addRelationBooks(Book b1, Book b2){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_ID + "1", b1.getId());
        values.put(BOOK_ID + "2", b2.getId());

        db.insert(TABLE_RELATION_BOOKS, null, values);
        db.close();
    }

    @Override
    public void addNoteObject(Note n, Object o) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, o.getId());
        values.put(NOTE_ID, n.getId());

        db.insert(TABLE_RELATION_OBJECT_NOTE, null, values);
        db.close();
    }

    @Override
    public void addNoteBook(Note n, Book b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_ID, b.getId());
        values.put(NOTE_ID, n.getId());

        db.insert(TABLE_RELATION_BOOK_NOTE, null, values);
        db.close();
    }

    @Override
    public ArrayList<String> getAllTagsByBook(int bkId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listTagId = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RELATION_TAG_BOOK, null, BOOK_ID + "= ?", new String[]{bkId + ""}, null, null, null);
        int i = 0;
        while (cursor.moveToNext()) {
            i = (cursor.getInt(cursor.getColumnIndex(TAG_ID)));
            listTagId.add(i);
        }
        cursor.close();
        ArrayList<String> listTagString = new ArrayList<>();
        String name;
        int tagId;
        for (int j = 0; j < listTagId.size(); j++) {
            tagId = listTagId.get(j);
            Cursor cursor2 = db.query(TABLE_TAG, null, TAG_ID + "= ?", new String[]{tagId + ""}, null, null, null);
            if (cursor2.moveToNext()) {
                name = (cursor2.getString(cursor2.getColumnIndex(TAG_NAME)));
                listTagString.add(name);
            }
            cursor2.close();
        }
        db.close();
        return listTagString;
    }

    //TODO: TEST!!!
    @Override
    public ArrayList<String> getAllTagsByClass(int objId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listTagId = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RELATION_TAG_OBJECT, null, OBJECT_ID + "= ?", new String[]{objId + ""}, null, null, null);
        int i = 0;
        while (cursor.moveToNext()) {
            i = (cursor.getInt(cursor.getColumnIndex(TAG_ID)));
            listTagId.add(i);
        }
        cursor.close();
        ArrayList<String> listTagString = new ArrayList<>();
        String name;
        int tagId;
        for (int j = 0; j < listTagId.size(); j++) {
            tagId = listTagId.get(j);
            Cursor cursor2 = db.query(TABLE_TAG, null, TAG_ID + "= ?", new String[]{tagId + ""}, null, null, null);
            if (cursor2.moveToNext()) {
                name = (cursor2.getString(cursor2.getColumnIndex(TAG_NAME)));
                listTagString.add(name);
            }
            cursor2.close();
        }
        db.close();
        return listTagString;
    }

    //TODO:TEST!!!!
    @Override
    public ArrayList<Note> getAllNotesByBook(int bkId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listNoteId = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RELATION_BOOK_NOTE, null, BOOK_ID + "= ?", new String[]{bkId + ""}, null, null, null);
        int i;
        while (cursor.moveToNext()) {
            i = (cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
            listNoteId.add(i);
        }
        cursor.close();
        ArrayList<Note> listNote = new ArrayList<>();
        Note n = new Note("", "", "", "");
        int noteId;
        for (int j = 0; j < listNoteId.size(); j++) {
            noteId = listNoteId.get(j);
            Cursor cursor2 = db.query(TABLE_NOTE, null, NOTE_ID + "= ?", new String[]{noteId + ""}, null, null, null);
            if (cursor2.moveToNext()) {
                n.setId(cursor2.getInt(cursor2.getColumnIndex(NOTE_ID)));
                n.setTitle((cursor2.getString(cursor2.getColumnIndex(NOTE_TITLE))));
                n.setContent(cursor2.getString(cursor2.getColumnIndex(NOTE_CONTENT)));
                n.setDate_create(cursor2.getString(cursor2.getColumnIndex(NOTE_DATE_CREATE)));
                n.setDate_edit(cursor2.getString(cursor2.getColumnIndex(NOTE_DATE_EDIT)));
                listNote.add(n);
            }
            cursor2.close();
        }
        db.close();
        return listNote;
    }

    @Override
    public ArrayList<Book> getAllBooksByBook(int bkId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listBookId = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RELATION_BOOKS, null, BOOK_ID + "= ?", new String[]{bkId + ""}, null, null, null);
        int i;
        while (cursor.moveToNext()) {
            i = (cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
            listBookId.add(i);
        }
        cursor.close();
        ArrayList<Book> listBook = new ArrayList<>();
        Book b = new Book("", "", "", "");
        int bookId;
        for (int j = 0; j < listBookId.size(); j++) {
            bookId = listBookId.get(j);
            Cursor cursor2 = db.query(TABLE_BOOK, null, BOOK_ID + "= ?", new String[]{bookId + ""}, null, null, null);
            if (cursor2.moveToNext()) {
                b.setId(cursor2.getInt(cursor2.getColumnIndex(BOOK_ID)));
                b.setName((cursor2.getString(cursor2.getColumnIndex(BOOK_TITLE))));
                b.setAuthor(cursor2.getString(cursor2.getColumnIndex(BOOK_AUTHOR)));
                b.setDate(cursor2.getString(cursor2.getColumnIndex(BOOK_DATE)));
                b.setDescription(cursor2.getString(cursor2.getColumnIndex(BOOK_DESCRIPTION)));
                listBook.add(b);
            }
            cursor2.close();
        }
        db.close();
        return listBook;
    }

    @Override
    public ArrayList<Note> getAllNoteByObject(int clsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> listNoteId = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RELATION_OBJECT_NOTE, null, OBJECT_ID + "= ?", new String[]{clsId + ""}, null, null, null);
        int i;
        while (cursor.moveToNext()) {
            i = (cursor.getInt(cursor.getColumnIndex(NOTE_ID)));
            listNoteId.add(i);
        }
        cursor.close();
        ArrayList<Note> listNote = new ArrayList<>();
        Note n = new Note("", "", "", "");
        int noteId;
        for (int j = 0; j < listNoteId.size(); j++) {
            noteId = listNoteId.get(j);
            Cursor cursor2 = db.query(TABLE_NOTE, null, NOTE_ID + "= ?", new String[]{noteId + ""}, null, null, null);
            if (cursor2.moveToNext()) {
                n.setId(cursor2.getInt(cursor2.getColumnIndex(NOTE_ID)));
                n.setTitle((cursor2.getString(cursor2.getColumnIndex(NOTE_TITLE))));
                n.setContent(cursor2.getString(cursor2.getColumnIndex(NOTE_CONTENT)));
                n.setDate_create(cursor2.getString(cursor2.getColumnIndex(NOTE_DATE_CREATE)));
                n.setDate_edit(cursor2.getString(cursor2.getColumnIndex(NOTE_DATE_EDIT)));
                listNote.add(n);
            }
            cursor2.close();
        }
        db.close();
        return listNote;
    }

    @Override
    public ArrayList<Object> getAllObjectsByTagName(String nameTag) {

        //TODO:
        return null;
    }

    @Override
    public ArrayList<Book> getAllBooksByTagName(String nameTag) {

        //TODO:
        return null;
    }

    //TODO:test!!!
    @Override
    public void removeAllRelationsWithObject(int clsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RELATION_TAG_OBJECT, OBJECT_ID + " = ?", new String[]{clsId + ""});
        db.delete(TABLE_RELATION_OBJECTS, OBJECT_ID + "1 = ?", new String[]{clsId + ""});
        db.delete(TABLE_RELATION_OBJECTS, OBJECT_ID + "2 = ?", new String[]{clsId + ""});
        db.delete(TABLE_RELATION_OBJECT_NOTE, OBJECT_ID + " = ?", new String[]{clsId + ""});
        db.close();
    }

    @Override
    public void removeAllRelationsWithBook(int bkId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RELATION_TAG_BOOK, BOOK_ID + " = ?", new String[]{bkId + ""});
        db.delete(TABLE_RELATION_BOOK_NOTE, BOOK_ID + " = ?", new String[]{bkId + ""});
        db.close();
    }

    @Override
    public void removeAllRelationsWithNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RELATION_BOOK_NOTE, NOTE_ID + " = ?", new String[]{noteId + ""});
        db.delete(TABLE_RELATION_OBJECT_NOTE, NOTE_ID + " = ?", new String[]{noteId + ""});
        db.close();
    }

    @Override
    public void removeAllRelationsWithTag(int tagId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RELATION_TAG_BOOK, TAG_ID + " = ?", new String[]{tagId + ""});
        db.delete(TABLE_RELATION_TAG_OBJECT, TAG_ID + " = ?", new String[]{tagId + ""});
        db.close();
    }
    /*
          ================================  *NOTE  ===================================
    */

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
    public int countNote() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTE, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTE, null, NOTE_ID + "= ?", new String[]{id + ""}, null, null, null);
        Note note = new Note("", "", "", "");
        if (cursor.moveToNext()) {
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
    public void updateNote(Note n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, n.getTitle());
        contentValues.put(NOTE_CONTENT, n.getContent());
        contentValues.put(NOTE_DATE_CREATE, n.getDate_create());
        contentValues.put(NOTE_DATE_EDIT, n.getDate_edit());
        db.update(TABLE_NOTE, contentValues, NOTE_ID + "=?", new String[]{n.getId() + ""});
        db.close();
    }

    @Override
    public void removeNote(Note n) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, NOTE_ID + " = ?", new String[]{String.valueOf(n.getId())});
        db.close();
    }
    /*
          ================================  *TAG  ===================================
    */

    @Override
    public void addTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAG_NAME, t.getName());
        db.insert(TABLE_TAG, null, values);
        db.close();
    }

    @Override
    public int countTag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TAG, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Tag getTag(String t) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAG, null, TAG_NAME + "= ?", new String[]{t + ""}, null, null, null);
        Tag tag = new Tag("");
        int id = 0;
        String name = "";
        if (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(TAG_ID));
            name = cursor.getString(cursor.getColumnIndex(TAG_NAME));
        }
        tag.setId(id);
        tag.setName(name);
        cursor.close();
        db.close();
        return tag;
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
    public ArrayList<String> getAllTagsName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TAG + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> tagList = new ArrayList<>();
        String tagName;
        while (cursor.moveToNext()) {
            tagName = (cursor.getString(cursor.getColumnIndex(TAG_NAME)));
            tagList.add(tagName);
        }
        cursor.close();
        db.close();

        return tagList;
    }

    @Override
    public ArrayList<String> getAllNotesByTitle() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NOTE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> titleList = new ArrayList<>();
        String Title;
        while (cursor.moveToNext()) {
            Title = (cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
            titleList.add(Title);
        }
        cursor.close();
        db.close();

        return titleList;
    }


    @Override
    public void removeTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TAG, TAG_ID + " = ?", new String[]{String.valueOf(t.getId())});
        db.delete(TABLE_RELATION_TAG_BOOK, TAG_ID + " = ?", new String[]{String.valueOf(t.getId())});
        db.delete(TABLE_RELATION_TAG_OBJECT, TAG_ID + " = ?", new String[]{String.valueOf(t.getId())});
        db.close();
    }

    @Override
    public void updateTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_NAME, t.getName());
        db.update(TABLE_TAG, contentValues, TAG_ID + "=?", new String[]{t.getId() + ""});
        db.close();
    }


    /*
          ================================  *PROFILE  ===================================
     */

    @Override
    public void addProfile(Profile p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_FIRST_NAME, p.getFirstName());
        values.put(PROFILE_LAST_NAME, p.getLastName());
        values.put(PROFILE_EMAIL, p.getEmail());
        values.put(PROFILE_PHOTO, Tools.bitmapToByte(p.getPhoto()));

        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    @Override
    public void updateProfile(Profile p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_FIRST_NAME, p.getFirstName());
        contentValues.put(PROFILE_LAST_NAME, p.getLastName());
        contentValues.put(PROFILE_EMAIL, p.getEmail());
        contentValues.put(PROFILE_PHOTO, Tools.bitmapToByte(p.getPhoto()));
        db.update(TABLE_PROFILE, contentValues, PROFILE_ID + "=?", new String[]{p.getId() + ""});
        db.close();
    }

    @Override
    public int countProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PROFILE, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Profile getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE, null, PROFILE_ID + "= ? ", new String[]{id + ""}, null, null, null);
        Profile profile = new Profile("", "", "", null);
        if (cursor.moveToNext()) {
            profile = new Profile("", "", "", null);
            profile.setId(cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
            profile.setFirstName(cursor.getString(cursor.getColumnIndex(PROFILE_FIRST_NAME)));
            profile.setLastName(cursor.getString(cursor.getColumnIndex(PROFILE_LAST_NAME)));
            profile.setEmail(cursor.getString(cursor.getColumnIndex(PROFILE_EMAIL)));
            profile.setPhoto(Tools.byteToBitmap(cursor.getBlob(cursor.getColumnIndex(PROFILE_PHOTO))));
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
    public ArrayList<String> getAllObjectsName() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> allObjectsAndBooksNames = new ArrayList<>();
        /*
        ADD OBJECTS
         */
        String sql = "SELECT * FROM " + TABLE_OBJECT + ";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<String> oneObject = new ArrayList<>();
        String object_type;
        String object_name;
        while (cursor.moveToNext()) {
            object_type = (cursor.getString(cursor.getColumnIndex(OBJECT_TYPE)));
            object_name = (cursor.getString(cursor.getColumnIndex(OBJECT_NAME)));
            allObjectsAndBooksNames.add(object_type + " : " + object_name);
        }

        /*
        ADD BOOKS
         */
        sql = "SELECT * FROM " + TABLE_BOOK + ";";
        cursor = db.rawQuery(sql, null);
        ArrayList<String> oneBook = new ArrayList<>();
        String book_title;
        while (cursor.moveToNext()) {
            book_title = (cursor.getString(cursor.getColumnIndex(BOOK_TITLE)));
            allObjectsAndBooksNames.add("Book : " + book_title);

        }

        cursor.close();
        db.close();
        return allObjectsAndBooksNames;
    }

    /*
              ================================  Others  ===================================
    */

}
