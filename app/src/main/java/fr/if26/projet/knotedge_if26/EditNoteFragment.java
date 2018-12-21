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
import java.util.Calendar;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Note;

public class EditNoteFragment extends Fragment {

    private View view;
    private Button buttonEdit, buttonCancel;
    private EditText etTitle, etContent;
    private int idNote;
    private Note note;
    private KnotedgePersistance knotedgePersistance;

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
                listener.loadDetailNote(idNote);
            }
        });

        return view;
    }
}
