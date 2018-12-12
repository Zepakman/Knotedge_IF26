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
    private TextView numberTags;

    private String firstName;
    private String lastName;
    private String photo;
    private int nClass;
    private int nNote;
    private int nTag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        imageViewProfile = view.findViewById(R.id.profile_image);
        nameProfile = view.findViewById(R.id.profile_name);
        numberNotes = view.findViewById(R.id.notes_number);
        numberClasses = view.findViewById(R.id.classes_number);
        numberTags = view.findViewById(R.id.tags_number);

        Bundle bundle = getArguments();
        firstName = bundle.getString("firstName");
        lastName = bundle.getString("lastName");
        photo = bundle.getString("photo");
        nClass = bundle.getInt("nClass");
        nNote = bundle.getInt("nNote");
        nTag = bundle.getInt("nTag");

        nameProfile.setText(firstName + " " + lastName);
        numberClasses.setText(nClass+"");
        numberTags.setText(nTag+"");
        numberNotes.setText(nNote+"");

        return view;
    }
}
