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

public class NewNoteFragment extends Fragment {

    private Button writeNoteButton;
    private EditText noteTitle;
    private EditText noteContent;
    private View view;
    private String title;
    private String content;

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
        noteTitle = (EditText) view.findViewById(R.id.new_note_title);
        noteContent = (EditText) view.findViewById(R.id.nav_new_note);

        writeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = noteTitle.getText().toString();
                content = noteTitle.getText().toString();
                listener.createNewNote(title, content);
            }
        });

        return view;
    }
}
