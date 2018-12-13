package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Event;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Person;
import fr.if26.projet.knotedge_if26.entity.Place;
import fr.if26.projet.knotedge_if26.entity.Profile;


//test
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TransmissionListener {

    private DrawerLayout mDrawerLayout;

    private KnotedgePersistance knotedgePersistance;

    //TODO: À changer
    private int USER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        knotedgePersistance = new KnotedgePersistance(this);
        if(knotedgePersistance.countProfile()==0) {
           knotedgePersistance.createDefaultUser();
        }

        //for test
        knotedgePersistance.testInit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //This preload the profil tab in lauching the app
        //The if statement prevent from reload profil tab when rotating the app
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfilFragment()).commit();
            loadFragmentProfile();
            navigationView.setCheckedItem(R.id.nav_profil);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profil:
                loadFragmentProfile();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfilFragment()).commit();
                break;
            case R.id.nav_new_class:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewClassFragment()).commit();
                break;
            case R.id.nav_new_note:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewNoteFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void loadFragmentProfile() {
        //Pour transmettre les valeurs d'utilisateur à ProfileFragment:
        ProfilFragment profilFragment = new ProfilFragment();
        Bundle bundle = new Bundle();
        Profile profile = knotedgePersistance.getProfile(USER_ID);
        int countNote = knotedgePersistance.countNote();
        int countClass = knotedgePersistance.countClass();
        int countTag = knotedgePersistance.countTag();
        bundle.putString("firstName", profile.getFirstName());
        bundle.putString("lastName", profile.getLastName());
        bundle.putString("photo", profile.getPhoto());
        bundle.putInt("nNote", countNote);
        bundle.putInt("nClass", countClass);
        bundle.putInt("nTag", countTag);
        profilFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profilFragment).commit();
    }


    @Override
    public void createNewObject(String name, String date, String description, int type) {
        switch (type) {
            case 0 :
                Person p = new Person(name, description, date);
                knotedgePersistance.addPerson(p);
                Toast.makeText(this, "Personne créée", Toast.LENGTH_LONG).show();
            case 1 :
                Event e = new Event(name, description, date);
                knotedgePersistance.addEvent(e);
                Toast.makeText(this, "Événement créé", Toast.LENGTH_LONG).show();
            case 2 :
                Place pl = new Place(name, description);
                knotedgePersistance.addPlace(pl);
                Toast.makeText(this, "Lieu créée", Toast.LENGTH_LONG).show();
            case 3 :
                Object o = new Object(name, description);
                knotedgePersistance.addObject(o);
                Toast.makeText(this, "Objet créée", Toast.LENGTH_LONG).show();
            default :
                Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void createNewBook(String name, String description, String author, String date) {
        Book b = new Book(name, description, author, date);
        knotedgePersistance.addBook(b);
        Toast.makeText(this, "Livre créé", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean createNewNote(String title, String content) {

        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());

        Note n = new Note(title, content, date, date);
        knotedgePersistance.addNote(n);
        return true;
    }
}
