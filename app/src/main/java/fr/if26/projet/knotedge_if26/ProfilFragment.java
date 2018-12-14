package fr.if26.projet.knotedge_if26;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.if26.projet.knotedge_if26.util.Tools;

public class ProfilFragment extends Fragment {

    private ImageView imageViewProfile;
    private TextView nameProfile;
    private Button numberNotes;
    private Button numberClasses;
    private Button numberBooks;
    private Button numberTags;

    private String firstName;
    private String lastName;
    private byte[] photo;
    private int nClass;
    private int nNote;
    private int nBook;
    private int nTag;

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profil, container, false);
        imageViewProfile = (ImageView) view.findViewById(R.id.profile_image);
        nameProfile = (TextView) view.findViewById(R.id.profile_name);
        numberNotes = (Button) view.findViewById(R.id.notes_number);
        numberClasses = (Button) view.findViewById(R.id.classes_number);
        numberBooks = (Button) view.findViewById(R.id.books_number);
        numberTags = (Button) view.findViewById(R.id.tags_number);

        Bundle bundle = getArguments();
        firstName = bundle.getString("firstName");
        lastName = bundle.getString("lastName");
        photo = bundle.getByteArray("photo");
        nClass = bundle.getInt("nClass");
        nNote = bundle.getInt("nNote");
        nBook = bundle.getInt("nBook");
        nTag = bundle.getInt("nTag");

        nameProfile.setText(firstName + " " + lastName);
        Bitmap bmp = Tools.byteToBitmap(photo);
        //BitmapDrawable bmpdrawable = new BitmapDrawable(getView().getContext().getResources(), bmp);
        //imageViewProfile.setBackground(bmpdrawable);
        imageViewProfile.setImageBitmap(bmp);

        numberClasses.setText(nClass+"");
        numberTags.setText(nTag+"");
        numberBooks.setText(nBook+"");
        numberNotes.setText(nNote+"");

        numberNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllNotes();
            }
        });

        numberClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllClasses();
            }
        });

        numberBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllBooks();
            }
        });
        numberTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllTags();
            }
        });

        return view;
    }
}
