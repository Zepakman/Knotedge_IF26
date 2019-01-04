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
import java.util.Iterator;
import java.util.List;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class NewNoteFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

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
    private List<String> listSelectedObjects;
    private MultiSelectionSpinner spinnerRelatedClasses;


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




        //ADD ClASS RELATIONSHIP
        spinnerRelatedClasses = view.findViewById(R.id.list_of_related_classes_spinner);
        final ArrayList<String> objectDoubleList = knotedgePersistance.getAllObjectsName();
        final ArrayList<String> objectList = new ArrayList<>();
        String object;
        // Specify the layout to use when the list of choices appears
        if (objectDoubleList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<>();
            debugList.add("You don't have any other objects yet");
            spinnerRelatedClasses.setItems(debugList);

        }
        else {
            for (int i = 0; i < objectDoubleList.size(); i++ ) {
                object = objectDoubleList.get(i); // + " : " + objectDoubleList.get(0).get(1);
                objectList.add(object);
            }
            spinnerRelatedClasses.setItems(objectList);
            spinnerRelatedClasses.setSelection(new ArrayList<String>());
        }
        // Apply the adapter to the spinner
        spinnerRelatedClasses.setListener(this);

        writeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = noteTitle.getText().toString();
                content = noteContent.getEditableText().toString();

                listener.createNewNote(title, content);
                if (!objectList.isEmpty()) {
                    listSelectedObjects = spinnerRelatedClasses.getSelectedStrings();
                    for (Iterator<String> i = listSelectedObjects.iterator(); i.hasNext();) {
                        String t[] = i.next().split(" ");
                        if (t[0] == "Book") {
                            listener.createNewRelationNoteBook(knotedgePersistance.getLastNote(), knotedgePersistance.getBookByTitle(t[2]));
                        }
                        else {listener.creatNewRelationNoteObject(knotedgePersistance.getLastNote(), knotedgePersistance.getObjectByName(t[2]));}

                    }
                }
                listener.loadFragmentProfile();
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
