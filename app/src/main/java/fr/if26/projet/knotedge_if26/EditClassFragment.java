package fr.if26.projet.knotedge_if26;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.MultiSelectionSpinner;

public class EditClassFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private View view;
    private Button buttonEdit, buttonCancel, buttonTag;
    private TextView tvType;
    private EditText etName, etDescription, etDate;
    private int idClass;
    private Object object;
    private KnotedgePersistance knotedgePersistance;
    final Calendar myCalendar = Calendar.getInstance();

    private String newName;
    private String newDescription;
    private String newDate;

    private MultiSelectionSpinner spinnerTag;
    private List<String> listSelectedTags;

    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_class, container, false);

        knotedgePersistance = new KnotedgePersistance(getContext());
        buttonCancel = (Button) view.findViewById(R.id.edit_class_cancel);
        buttonEdit = (Button) view.findViewById(R.id.edit_class_ok);
        etName = (EditText) view.findViewById(R.id.edit_class_name);
        etDescription = (EditText) view.findViewById(R.id.edit_class_description);
        etDate = (EditText) view.findViewById(R.id.edit_class_date);
        tvType = (TextView) view.findViewById(R.id.edit_class_type);

        Bundle bundle = getArguments();
        idClass = bundle.getInt("id");
        object = knotedgePersistance.getObjectById(idClass);
        etName.setText(object.getName());
        etDescription.setText(object.getDescription());
        tvType.setText(object.getType());
        etDate.setText(object.getDate());

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

        spinnerTag = view.findViewById(R.id.edit_class_list_tag);
        final ArrayList<String> tagList = knotedgePersistance.getAllTagsName();
        // Specify the layout to use when the list of choices appears
        if (tagList.isEmpty()) {
            ArrayList<String> debugList = new ArrayList<String>();
            debugList.add("You don't have any tags set");
            spinnerTag.setItems(debugList);
        }
        else {
            spinnerTag.setItems(tagList);
        }
        // TODO: tag par défaut
        spinnerTag.setListener(this);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadDetailObject(idClass);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = etName.getText().toString();
                newDescription = etDescription.getText().toString();
                newDate = etDate.getText().toString();
                object.setName(newName);
                object.setDescription(newDescription);
                object.setDate(newDate);
                knotedgePersistance.updateClass(object);

                knotedgePersistance.removeAllRelationsWithObject(idClass);

                if (!tagList.isEmpty()) {
                    listSelectedTags = spinnerTag.getSelectedStrings();
                    for (Iterator<String> i = listSelectedTags.iterator(); i.hasNext();) {
                        String t = i.next();
                        listener.createNewRelationTagObject(knotedgePersistance.getTag(t), knotedgePersistance.getObjectById(idClass));
                    }
                }

                listener.loadDetailObject(idClass);
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
        Toast.makeText(view.getContext(), strings.toString(), Toast.LENGTH_LONG).show();
    }
}
