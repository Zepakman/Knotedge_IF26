package fr.if26.projet.knotedge_if26;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewClassFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    final Calendar myCalendar = Calendar.getInstance();

    private LinearLayout layoutAuthorHide;
    private Spinner spinner;
    private EditText txtName;
    private EditText txtDate;
    private EditText txtDescription;
    private EditText txtAuthor;
    private Button createClassButton;

    private int typeClass = 0;

    private TransmissionListener listener;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_class, container, false);

        //SET LAYOUT AUTHOR HIDDING
        layoutAuthorHide = view.findViewById(R.id.layout_author_for_hide);

        //SET NAME CODE
        txtName = view.findViewById(R.id.new_class_name);
        //SET DESCRPTION CODE
        txtDescription = view.findViewById(R.id.new_class_name2);
        //SET AUTHOR CODE
        txtAuthor = view.findViewById(R.id.new_author);

        //SPINNER CODE
        spinner = view.findViewById(R.id.class_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.classes_string_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //DATE PICKER CODE
        txtDate = (EditText) view.findViewById(R.id.objDate);
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

        createClassButton = (Button) view.findViewById(R.id.buttonCreateClass);

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String date = txtDate.getText().toString();
                String description = txtDescription.getText().toString();
                String author = txtAuthor.getText().toString();

                if (name != null && !name.equals("") && typeClass != 0) {
                    listener.createNewObject(name, date, description, typeClass);
                } else if (name != null && !name.equals("") && typeClass == 0) {
                    listener.createNewBook(name, date, description, author);
                }
            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        typeClass = position;
        if(typeClass == 0) {
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


}



