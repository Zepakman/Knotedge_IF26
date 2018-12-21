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

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;

public class DetailBookFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvName, tvAuthor,tvListTag;
    private Button buttonBack,buttonEdit;

    private int idBook = 0;
    private Book book;
    private List<String> listTagNameOfThis = new ArrayList<String>();

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_book, container, false);

        tvName = (TextView) view.findViewById(R.id.detail_book_name);
        tvAuthor = (TextView) view.findViewById(R.id.detail_book_author);
        tvListTag = (TextView) view.findViewById(R.id.detail_book_tags);
        buttonBack = (Button) view.findViewById(R.id.detail_book_back);
        buttonEdit = (Button) view.findViewById(R.id.detail_book_edit);

        knotedgePersistance = new KnotedgePersistance(getContext());


        Bundle bundle = getArguments();
        idBook = bundle.getInt("id");
        book = knotedgePersistance.getBookById(idBook);
        listTagNameOfThis = knotedgePersistance.getAllTagsByBook(idBook);

        tvName.setText(book.getName());
        tvAuthor.setText(book.getAuthor());

        String tags = new String();
        StringBuffer buffer = new StringBuffer();


        for(int j=0;j<listTagNameOfThis.size();j++) {
            buffer.append(listTagNameOfThis.get(j) + ",");
        }
        tags = buffer.toString();
        tvListTag.setText(tags);

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


