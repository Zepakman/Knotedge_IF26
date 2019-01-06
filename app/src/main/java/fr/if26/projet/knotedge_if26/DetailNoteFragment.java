package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.AdapterBook;
import fr.if26.projet.knotedge_if26.util.AdapterObject;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class DetailNoteFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvTitle, tvContent;
    private Button buttonBack,buttonEdit;

    private int idNote = 0;
    private Note note;
    private List<Book> listBookNameOfThis = new ArrayList<Book>();
    private List<Object> listObjectNameOfThis = new ArrayList<Object>();

    private RecyclerView recyclerViewObject, recyclerViewBooks;
    private AdapterObject adapterObject;
    private AdapterBook adapterBook;

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
        listObjectNameOfThis = knotedgePersistance.getAllObjectsByNote(idNote);
        listBookNameOfThis = knotedgePersistance.getAllBooksByNote(idNote);



        tvTitle.setText(note.getTitle());
        tvContent.setText(note.getContent());

        //VIEW CLASSES
        recyclerViewObject = (RecyclerView) view.findViewById(R.id.detail_note_objects);
        adapterObject = new AdapterObject(view.getContext(), listObjectNameOfThis);
        recyclerViewObject.setAdapter(adapterObject);
        recyclerViewObject.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationObjects = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewObject.addItemDecoration(decorationObjects);
        if(listObjectNameOfThis.size()==0) {
        }
        adapterObject.setOnItemClickListener(new AdapterObject.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listObjectNameOfThis.get(position).getId();
                listener.loadDetailObject(id);
            }
        });
        recyclerViewBooks = (RecyclerView) view.findViewById(R.id.detail_note_books);
        adapterBook = new AdapterBook(view.getContext(), listBookNameOfThis);
        recyclerViewBooks.setAdapter(adapterBook);
        recyclerViewBooks.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationBooks = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewBooks.addItemDecoration(decorationBooks);
        if(listBookNameOfThis.size()==0) {
        }
        adapterBook.setOnItemClickListener(new AdapterBook.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listBookNameOfThis.get(position).getId();
                listener.loadDetailBook(id);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllNotes();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadEditNote(idNote);
            }
        });

        return view;
    }
}
