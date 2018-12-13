package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteFragment extends Fragment {

    private Button writeNoteButton;
    private EditText noteTitle;
    private EditText noteContent;
    private View view;

    private TransmissionListener listener;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_note, container, false);

        writeNoteButton = (Button) view.findViewById(R.id.button_write_note);
        noteTitle = view.findViewById(R.id.new_note_title);
        noteContent = view.findViewById(R.id.nav_new_note);

        writeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();
                boolean indice = listener.createNewNote(title, content);
                if (indice) {
                    Toast.makeText(view.getContext(), "Note Envoyée", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Erreur", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
