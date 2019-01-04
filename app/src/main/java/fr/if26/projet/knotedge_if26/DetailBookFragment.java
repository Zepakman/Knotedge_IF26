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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.util.AdapterNote;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class DetailBookFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvName, tvAuthor, tvDescription, tvDate, tvListTag;
    private Button buttonBack,buttonEdit;

    private int idBook = 0;
    private Book book;
    private List<String> listTagNameOfThis = new ArrayList<String>();
    private List<Note> listNotesNameOfThis = new ArrayList<Note>();

    private RecyclerView recyclerViewNotes, recyclerViewClass;
    private AdapterNote adapterNotes, adapterClasses;


    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_book, container, false);

        tvName = view.findViewById(R.id.detail_book_name);
        tvAuthor = view.findViewById(R.id.detail_book_author);
        tvListTag = view.findViewById(R.id.detail_book_tags);
        tvDescription = view.findViewById(R.id.detail_book_description);
        tvDate = view.findViewById(R.id.detail_book_date);
        buttonBack = view.findViewById(R.id.detail_book_back);
        buttonEdit = view.findViewById(R.id.detail_book_edit);

        knotedgePersistance = new KnotedgePersistance(getContext());

        Bundle bundle = getArguments();
        idBook = bundle.getInt("id");
        book = knotedgePersistance.getBookById(idBook);
        listTagNameOfThis = knotedgePersistance.getAllTagsByBook(idBook);
        listNotesNameOfThis = knotedgePersistance.getAllNotesByBook(idBook);

        tvName.setText(book.getName());
        tvAuthor.setText(book.getAuthor());
        tvDate.setText(book.getDate());
        tvDescription.setText(book.getDescription());

        String tags;
        StringBuffer buffer = new StringBuffer();


        for(int j=0;j<listTagNameOfThis.size();j++) {
            buffer.append(listTagNameOfThis.get(j) + ",");
        }
        tags = buffer.toString();
        tvListTag.setText(tags);


        //VIEW NOTES
        recyclerViewNotes = (RecyclerView) view.findViewById(R.id.detail_book_notes);
        adapterNotes = new AdapterNote(view.getContext(), listNotesNameOfThis);
        recyclerViewNotes.setAdapter(adapterNotes);
        recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationNotes = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewNotes.addItemDecoration(decorationNotes);
        if(listNotesNameOfThis.size()==0) {
            Toast.makeText(getContext(), "No Notes", Toast.LENGTH_SHORT).show();
        }
        adapterNotes.setOnItemClickListener(new AdapterNote.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listNotesNameOfThis.get(position).getId();
                listener.loadDetailNote(id);
            }
        });


        //VIEW CLASSES
        recyclerViewClass = (RecyclerView) view.findViewById(R.id.detail_book_classes);
        adapterClasses = new AdapterNote(view.getContext(), listNotesNameOfThis);
        recyclerViewClass.setAdapter(adapterClasses);
        recyclerViewClass.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationClasses = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewClass.addItemDecoration(decorationClasses);
        if(listNotesNameOfThis.size()==0) {
            Toast.makeText(getContext(), "No Notes", Toast.LENGTH_SHORT).show();
        }
        adapterClasses.setOnItemClickListener(new AdapterNote.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listNotesNameOfThis.get(position).getId();
                listener.loadDetailNote(id);
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllBooks();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadEditBook(idBook);
            }
        });

        return view;
    }

}


