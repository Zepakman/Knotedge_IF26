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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class EditNoteFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private View view;
    private Button buttonEdit, buttonCancel;
    private EditText etTitle, etContent;
    private int idNote;
    private Note note;
    private KnotedgePersistance knotedgePersistance;

    private MultiSelectionSpinner spinnerClass;
    private List<String> listSelectedObjects;

    private String newTitle;
    private String newContent;

    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        knotedgePersistance = new KnotedgePersistance(getContext());
        buttonCancel = (Button) view.findViewById(R.id.edit_note_cancel);
        buttonEdit = (Button) view.findViewById(R.id.edit_note_ok);
        etTitle = (EditText) view.findViewById(R.id.edit_note_title);
        etContent = (EditText) view.findViewById(R.id.edit_note_content);


        Bundle bundle = getArguments();
        idNote = bundle.getInt("id");
        note = knotedgePersistance.getNoteById(idNote);
        etTitle.setText(note.getTitle());
        etContent.setText(note.getContent());

        spinnerClass=view.findViewById(R.id.edit_note_related_classes);
        final ArrayList<String> objectDoubleList = knotedgePersistance.getAllObjectsName();
        final ArrayList<String> objectList = new ArrayList<>();
        String object;
        // Specify the layout to use when the list of choices appears
        if (objectDoubleList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<>();
            debugList.add("You don't have any other objects yet");
            spinnerClass.setItems(debugList);

        } else {
            for (int i = 0; i < objectDoubleList.size(); i++) {
                object = objectDoubleList.get(i);
                objectList.add(object);
            }
            spinnerClass.setItems(objectList);
            spinnerClass.setSelection(new ArrayList<String>());
        }
        // Apply the adapter to the spinner
        spinnerClass.setListener(this);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadDetailNote(idNote);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Calendar calendar = Calendar.getInstance();
                String date = sdf.format(calendar.getTime());

                newTitle = etTitle.getText().toString();
                newContent = etContent.getText().toString();
                note.setTitle(newTitle);
                note.setContent(newContent);
                note.setDate_edit(date);
                knotedgePersistance.updateNote(note);

                knotedgePersistance.removeAllRelationsWithNote(idNote);

                if (!objectList.isEmpty()) {
                    listSelectedObjects = spinnerClass.getSelectedStrings();
                    Iterator<String> it = listSelectedObjects.iterator();
                    while(it.hasNext()) {
                        String t[] = it.next().split(":");
                        t[0] = t[0].trim();
                        t[1] = t[1].trim();
                        if (t[0].equals("Book")) {
                            knotedgePersistance.addNoteBook(knotedgePersistance.getNoteById(idNote), knotedgePersistance.getBookByTitle(t[1]));
                            //listener.createNewRelationNoteBook(knotedgePersistance.getNoteById(idNote), knotedgePersistance.getBookByTitle(t[1]));
                        } else {
                            knotedgePersistance.addNoteObject(knotedgePersistance.getNoteById(idNote), knotedgePersistance.getObjectByName(t[1]));
                            //listener.createNewRelationNoteObject(knotedgePersistance.getNoteById(idNote), knotedgePersistance.getObjectByName(t[1]));
                        }
                    }
                }

                listener.loadDetailNote(idNote);
            }
        });

        return view;
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        //Toast.makeText(view.getContext(), strings.toString(), Toast.LENGTH_LONG).show();
    }
}
