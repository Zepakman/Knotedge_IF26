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

    private TextView tvName, tvType, tvListTag;
    private Button buttonBack,buttonEdit;

    private int idClass = 0;
    private Object object;
    private List<String> listTagNameOfThis = new ArrayList<String>();

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_class, container, false);

        tvName = (TextView) view.findViewById(R.id.detail_class_name);
        tvType = (TextView) view.findViewById(R.id.detail_class_type);
        tvListTag = (TextView) view.findViewById(R.id.detail_class_tags);
        buttonBack = (Button) view.findViewById(R.id.detail_class_back);
        buttonEdit = (Button) view.findViewById(R.id.detail_class_edit);

        knotedgePersistance = new KnotedgePersistance(getContext());

        Bundle bundle = getArguments();
        idClass = bundle.getInt("id");
        object  = knotedgePersistance.getObjectById(idClass);
        listTagNameOfThis = knotedgePersistance.getAllTagsByClass(idClass);

        tvName.setText(object.getName());
        tvType.setText(object.getType());

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
