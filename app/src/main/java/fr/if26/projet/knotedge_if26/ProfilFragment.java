package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilFragment extends Fragment {

    private ImageView imageViewProfile;
    private TextView nameProfile;
    private TextView numberNotes;
    private TextView numberClasses;
    private TextView numberBooks;
    private TextView numberTags;

    private String firstName;
    private String lastName;
    private String photo;
    private int nClass;
    private int nNote;
    private int nBook;
    private int nTag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        imageViewProfile = (ImageView) view.findViewById(R.id.profile_image);
        nameProfile = (TextView) view.findViewById(R.id.profile_name);
        numberNotes = (TextView) view.findViewById(R.id.notes_number);
        numberClasses = (TextView) view.findViewById(R.id.classes_number);
        numberBooks = (TextView) view.findViewById(R.id.books_number);
        numberTags = (TextView) view.findViewById(R.id.tags_number);

        Bundle bundle = getArguments();
        firstName = bundle.getString("firstName");
        lastName = bundle.getString("lastName");
        photo = bundle.getString("photo");
        nClass = bundle.getInt("nClass");
        nNote = bundle.getInt("nNote");
        nBook = bundle.getInt("nBook");
        nTag = bundle.getInt("nTag");

        nameProfile.setText(firstName + " " + lastName);
        numberClasses.setText(nClass+"");
        numberTags.setText(nTag+"");
        numberBooks.setText(nBook+"");
        numberNotes.setText(nNote+"");

        return view;
    }
}
