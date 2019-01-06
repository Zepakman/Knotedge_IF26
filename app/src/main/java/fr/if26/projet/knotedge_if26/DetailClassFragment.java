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
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.AdapterBook;
import fr.if26.projet.knotedge_if26.util.AdapterNote;
import fr.if26.projet.knotedge_if26.util.AdapterObject;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class DetailClassFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvName, tvType, tvDescription, tvDate, tvListTag;
    private Button buttonBack,buttonEdit;

    private int idClass = 0;
    private Object object;
    private List<String> listTagNameOfThis = new ArrayList<String>();
    private List<Note> listNotesNameOfThis = new ArrayList<Note>();
    private List<Book> listBookNameOfThis = new ArrayList<Book>();
    private List<Object> listObjectNameOfThis = new ArrayList<Object>();

    private RecyclerView recyclerViewNotes, recyclerViewObject, recyclerViewBooks;
    private AdapterNote adapterNotes;
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
        view = inflater.inflate(R.layout.fragment_detail_class, container, false);

        tvName = view.findViewById(R.id.detail_class_name);
        tvType = view.findViewById(R.id.detail_class_type);
        tvDescription = view.findViewById(R.id.detail_class_description);
        tvDate = view.findViewById(R.id.detail_class_date);
        tvListTag = view.findViewById(R.id.detail_class_tags);
        buttonBack = view.findViewById(R.id.detail_class_back);
        buttonEdit = view.findViewById(R.id.detail_class_edit);

        knotedgePersistance = new KnotedgePersistance(getContext());

        Bundle bundle = getArguments();
        idClass = bundle.getInt("id");
        object  = knotedgePersistance.getObjectById(idClass);
        listTagNameOfThis = knotedgePersistance.getAllTagsByClass(idClass);
        listNotesNameOfThis = knotedgePersistance.getAllNoteByObject(idClass);
        listObjectNameOfThis = knotedgePersistance.getAllObjectsByObject(idClass);
        listBookNameOfThis = knotedgePersistance.getAllBooksByObject(idClass);

        tvName.setText(object.getName());
        tvType.setText(object.getType());
        tvDescription.setText(object.getDescription());
        tvDate.setText(object.getDate());

        String tags;
        StringBuffer buffer = new StringBuffer();


        for(int j=0;j<listTagNameOfThis.size();j++) {
            buffer.append(listTagNameOfThis.get(j) + ",");
        }
        tags = buffer.toString();
        tvListTag.setText(tags);


        //VIEW NOTES
        recyclerViewNotes = (RecyclerView) view.findViewById(R.id.detail_class_notes);
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
        recyclerViewObject = (RecyclerView) view.findViewById(R.id.detail_class_object);
        adapterObject = new AdapterObject(view.getContext(), listObjectNameOfThis);
        recyclerViewObject.setAdapter(adapterObject);
        recyclerViewObject.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationObjects = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewObject.addItemDecoration(decorationObjects);
        if(listNotesNameOfThis.size()==0) {
        }
        adapterObject.setOnItemClickListener(new AdapterObject.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listObjectNameOfThis.get(position).getId();
                listener.loadDetailObject(id);
            }
        });
        recyclerViewBooks = (RecyclerView) view.findViewById(R.id.detail_class_books);
        adapterBook = new AdapterBook(view.getContext(), listBookNameOfThis);
        recyclerViewBooks.setAdapter(adapterBook);
        recyclerViewBooks.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration decorationBooks = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerViewBooks.addItemDecoration(decorationBooks);
        if(listNotesNameOfThis.size()==0) {
        }
        adapterBook.setOnItemClickListener(new AdapterBook.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = listBookNameOfThis.get(position).getId();
                listener.loadDetailObject(id);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentAllClasses();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadEditClass(idClass);
            }
        });

        return view;
    }
}
