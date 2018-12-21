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
import fr.if26.projet.knotedge_if26.entity.Object;

public class DetailClassFragment extends Fragment {

    private View view;

    private KnotedgePersistance knotedgePersistance;

    private TextView tvName, tvType, tvDescription, tvDate, tvListTag;
    private Button buttonBack,buttonEdit;

    private int idClass = 0;
    private Object object;
    private List<String> listTagNameOfThis = new ArrayList<>();

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
