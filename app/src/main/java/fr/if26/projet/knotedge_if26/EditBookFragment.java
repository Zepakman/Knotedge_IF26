package fr.if26.projet.knotedge_if26;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class EditBookFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private View view;
    private Button buttonEdit, buttonCancel, buttonAddTag;
    private EditText etName, etDescription, etDate, etAuthor;
    private int idBook;
    private Book book;
    private KnotedgePersistance knotedgePersistance;
    final Calendar myCalendar = Calendar.getInstance();

    private MultiSelectionSpinner spinnerTag, spinnerClass, spinnerNotes;
    private List<String> listSelectedTags, listSelectedNotes, listSelectedObjects;

    private String m_Text;


    private String newName, newDescription, newDate, newAuthor;

    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_book, container, false);

        knotedgePersistance = new KnotedgePersistance(getContext());
        buttonCancel = view.findViewById(R.id.edit_book_cancel);
        buttonEdit = view.findViewById(R.id.edit_book_ok);
        etName = view.findViewById(R.id.edit_book_name);
        etDescription = view.findViewById(R.id.edit_book_description);
        etAuthor = view.findViewById(R.id.edit_book_author);
        etDate = view.findViewById(R.id.edit_book_date);

        Bundle bundle = getArguments();
        idBook = bundle.getInt("id");
        book = knotedgePersistance.getBookById(idBook);
        etName.setText(book.getName());
        etDescription.setText(book.getDescription());
        etAuthor.setText(book.getAuthor());
        etDate.setText(book.getDate());


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(view.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonAddTag = view.findViewById(R.id.edit_book_add_tag);
        buttonAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme));
                builder.setTitle("Tag à ajouter");

                // Set up the input
                final EditText newTag = new EditText(view.getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                newTag.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(newTag);

                // Set up the buttons
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = newTag.getText().toString();
                        Tag tag = new Tag(m_Text);
                        KnotedgePersistance knotedgePersistance = new KnotedgePersistance(view.getContext());
                        ArrayList<String> tagList = knotedgePersistance.getAllTagsName();
                        if (!tagList.contains(m_Text)) {
                            knotedgePersistance.addTag(tag);
                            //the new created tag has no id so cannot added to tagList directly
                            tagList = knotedgePersistance.getAllTagsName();
                            spinnerTag.setItems(tagList);
                            spinnerTag.invalidate();

                        } else {
                            Toast.makeText(view.getContext(), "Tag déjà présent dans la liste", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        spinnerTag = view.findViewById(R.id.edit_book_list_tag);
        final ArrayList<String> tagList = knotedgePersistance.getAllTagsName();
        final ArrayList<String> selectedTagList = knotedgePersistance.getAllTagsByBook(idBook);
        // Specify the layout to use when the list of choices appears
        if (tagList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<>();
            debugList.add("You don't have any tags set");
            spinnerTag.setItems(debugList);
        } else {
            spinnerTag.setItems(tagList);
            spinnerTag.setSelection(selectedTagList);
        }
        spinnerTag.setListener(this);

        spinnerClass = view.findViewById(R.id.edit_book_related_classes);
        final ArrayList<String> objectDoubleList = knotedgePersistance.getAllObjectsName();
        final ArrayList<Object> objectSelected = knotedgePersistance.getAllObjectsByBook(idBook);
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

        spinnerNotes = view.findViewById(R.id.edit_book_related_notes);
        final ArrayList<String> notesDoubleList = knotedgePersistance.getAllNotesByTitle();
        final ArrayList<String> notesList = new ArrayList<>();
        String notes;
        // Specify the layout to use when the list of choices appears
        if (notesDoubleList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<>();
            debugList.add("You don't have any other notes yet");
            spinnerNotes.setItems(debugList);

        } else {
            for (int i = 0; i < notesDoubleList.size(); i++) {
                notes = notesDoubleList.get(i);
                notesList.add(notes);
            }
            spinnerNotes.setItems(notesList);
            spinnerNotes.setSelection(new ArrayList<String>());
        }
        // Apply the adapter to the spinner
        spinnerNotes.setListener(this);


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadDetailBook(idBook);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = etName.getText().toString();
                newAuthor = etAuthor.getText().toString();
                newDate = etDate.getText().toString();
                newDescription = etDescription.getText().toString();
                book.setName(newName);
                book.setDescription(newDescription);
                book.setDate(newDate);
                book.setAuthor(newAuthor);
                knotedgePersistance.updateBook(book);

                knotedgePersistance.removeAllRelationsWithBook(idBook);

                if (!tagList.isEmpty()) {
                    listSelectedTags = spinnerTag.getSelectedStrings();
                    for (Iterator<String> i = listSelectedTags.iterator(); i.hasNext(); ) {
                        String t = i.next();
                        listener.createNewRelationTagBook(knotedgePersistance.getTag(t), knotedgePersistance.getBookById(idBook));
                    }
                }

                if (!notesList.isEmpty()) {
                    listSelectedNotes = spinnerNotes.getSelectedStrings();
                    for (Iterator<String> i = listSelectedNotes.iterator(); i.hasNext(); ) {
                        String t = i.next();
                        listener.createNewRelationNoteBook(knotedgePersistance.getNoteByTitle(t), knotedgePersistance.getBookById(idBook));
                    }
                }

                if (!objectList.isEmpty()) {
                    listSelectedObjects = spinnerClass.getSelectedStrings();
                    for (Iterator<String> i = listSelectedObjects.iterator(); i.hasNext(); ) {
                        String t[] = i.next().split(" : ");
                        t[0] = t[0].trim();
                        t[1] = t[1].trim();
                        if (t[0].equals("Book")) {
                            listener.createNewRelationBooks(knotedgePersistance.getBookById(idBook), knotedgePersistance.getBookByTitle(t[1]));
                        } else {
                            listener.createNewRelationObjectBook(knotedgePersistance.getObjectByName(t[1]), knotedgePersistance.getBookById(idBook));
                        }

                    }
                }

                listener.loadDetailBook(idBook);
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        //Toast.makeText(view.getContext(), strings.toString(), Toast.LENGTH_LONG).show();
    }
}
