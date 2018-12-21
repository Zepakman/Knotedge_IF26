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

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.MultiSelectionObjectSpinner;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class NewNoteFragment extends Fragment implements MultiSelectionObjectSpinner.OnMultipleItemsSelectedListener {

    private Button writeNoteButton;
    private EditText noteTitle;
    private EditText noteContent;
    private View view;
    private String title;
    private String content;
    private KnotedgePersistance knotedgePersistance;

    private MultiSelectionSpinner spinnerClass;
    private ArrayList<Object> selectedClasses;
    private List<String> selectedClassesNames;
    private ArrayList<Object> allClassesList;
    private ArrayList<String> allClassesNames;

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
        noteContent = (EditText) view.findViewById(R.id.new_note_note);

        knotedgePersistance = new KnotedgePersistance(this.getContext());


        writeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = noteTitle.getText().toString();
                content = noteContent.getEditableText().toString();

                listener.createNewNote(title, content);
            }
        });

        return view;
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

    }
}
