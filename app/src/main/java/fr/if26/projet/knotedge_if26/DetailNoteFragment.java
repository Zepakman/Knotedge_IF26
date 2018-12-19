package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Note;

public class DetailNoteFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvTitle, tvContent;
    private Button buttonBack,buttonEdit;

    private int idNote = 0;
    private Note note;

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_note, container, false);

        tvTitle = (TextView) view.findViewById(R.id.detail_note_title);
        tvContent = (TextView) view.findViewById(R.id.detail_note_content);
        buttonBack = (Button) view.findViewById(R.id.detail_note_back);
        buttonEdit = (Button) view.findViewById(R.id.detail_note_edit);

        knotedgePersistance = new KnotedgePersistance(getContext());

        Bundle bundle = getArguments();
        idNote = bundle.getInt("id");
        note = knotedgePersistance.getNoteById(idNote);

        tvTitle.setText(note.getTitle());
        tvContent.setText(note.getContent());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllNotes();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
