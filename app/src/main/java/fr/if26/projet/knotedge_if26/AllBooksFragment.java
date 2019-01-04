package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.util.AdapterBook;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class AllBooksFragment extends Fragment {

    private View view;
    private ArrayList<Book> allBooks;
    private AdapterBook adapter;
    private RecyclerView recyclerView;

    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_books, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_books);
        Bundle bundle = getArguments();
        setHasOptionsMenu(true);

        allBooks = (ArrayList) bundle.getSerializable("books");

        adapter = new AdapterBook(this.getContext(), allBooks);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        if (allBooks.size() == 0) {
            Toast.makeText(getContext(), "No Books", Toast.LENGTH_SHORT).show();
        }

        adapter.setOnItemClickListener(new AdapterBook.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = allBooks.get(position).getId();
                listener.loadDetailBook(id);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.return_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_return:
                listener.loadFragmentProfile();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
