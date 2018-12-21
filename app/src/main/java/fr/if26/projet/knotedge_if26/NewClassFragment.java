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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class NewClassFragment extends Fragment implements AdapterView.OnItemSelectedListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private View view;
    final Calendar myCalendar = Calendar.getInstance();

    private LinearLayout layoutAuthorHide;
    private Spinner spinnerClass;
    private EditText txtName;
    private EditText txtDate;
    private EditText txtDescription;
    private EditText txtAuthor;
    private Button createClassButton;
    private Button addTagButton;
    private MultiSelectionSpinner spinnerTag;
    private List<String> listSelectedTags;

    private KnotedgePersistance knotedgePersistance;

    private String m_Text;
    private int typeClass = 0;

    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_class, container, false);

        knotedgePersistance = new KnotedgePersistance(this.getContext());

        //SET LAYOUT AUTHOR HIDDING
        layoutAuthorHide = view.findViewById(R.id.layout_author_for_hide);
        //SET NAME CODE
        txtName = view.findViewById(R.id.new_class_name);
        //SET DESCRIPTION CODE
        txtDescription = view.findViewById(R.id.description);
        //SET AUTHOR CODE
        txtAuthor = view.findViewById(R.id.new_author);


        //SPINNER CODE
        spinnerClass = view.findViewById(R.id.class_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.classes_string_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerClass.setAdapter(adapter);
        spinnerClass.setOnItemSelectedListener(this);


        //DATE PICKER CODE
        txtDate = view.findViewById(R.id.objDate);
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

        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(view.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //SELECT TAGS SPINNER
        spinnerTag = view.findViewById(R.id.list_of_tags_spinner);
        final ArrayList<String> tagList = knotedgePersistance.getAllTagsName();
        // Specify the layout to use when the list of choices appears
        if (tagList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<>();
            debugList.add("You don't have any tags set");
            spinnerTag.setItems(debugList);
        }
        else {
            spinnerTag.setItems(tagList);
        }
        // Apply the adapter to the spinner
        spinnerTag.setListener(this);


        //ADD TAG BUTTON
        addTagButton = view.findViewById(R.id.buttonAddTag);
        addTagButton.setOnClickListener(new View.OnClickListener() {
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


        //CREATE CLASS BUTTON
        createClassButton = view.findViewById(R.id.buttonCreateClass);
        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String date = txtDate.getText().toString();
                String description = txtDescription.getText().toString();
                String author = txtAuthor.getText().toString();

                /* ----------------- create object ----------------- */

                if (name != null && !name.equals("") && typeClass != 0) {
                    listener.createNewObject(name, date, description, typeClass);
                    if (!tagList.isEmpty()) {
                        listSelectedTags = spinnerTag.getSelectedStrings();
                        for (Iterator<String> i = listSelectedTags.iterator(); i.hasNext();) {
                            String t = i.next();
                            listener.createNewRelationTagObject(knotedgePersistance.getTag(t), knotedgePersistance.getLastObject());
                        }
                    }

                /* ----------------- create book ----------------- */

                } else if (name != null && !name.equals("") && typeClass == 0) {
                    listener.createNewBook(name, date, description, author);
                    if (!tagList.isEmpty()) {
                        listSelectedTags = spinnerTag.getSelectedStrings();

                        for (Iterator<String> i = listSelectedTags.iterator(); i.hasNext();) {
                            String t = i.next();
                            listener.createNewRelationTagBook(knotedgePersistance.getTag(t), knotedgePersistance.getLastBook());
                        }
                    }
                }



            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeClass = position;
        if (typeClass == 0) {
            txtAuthor.setVisibility(View.VISIBLE);
            layoutAuthorHide.setVisibility(View.VISIBLE);
        } else {
            txtAuthor.setVisibility(View.GONE);
            layoutAuthorHide.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(view.getContext(), strings.toString(), Toast.LENGTH_LONG).show();
    }
}



