package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingsFragment extends Fragment {

    private Button editProfilButton;


    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        editProfilButton = (Button) view.findViewById(R.id.edit_profil_button);

        editProfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFragmentSettingsProfile();
            }
        });


        return view;
    }
}
