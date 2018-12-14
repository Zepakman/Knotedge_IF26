package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.util.AdapterBook;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class AllBooksFragment extends Fragment {

    private View view;
    private ArrayList<Book> allBooks;
    private AdapterBook adapter;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_books, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_books);

        Bundle bundle = getArguments();
        allBooks = (ArrayList) bundle.getSerializable("books");

        adapter = new AdapterBook(view.getContext(), allBooks);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);


        return view;
    }
}
