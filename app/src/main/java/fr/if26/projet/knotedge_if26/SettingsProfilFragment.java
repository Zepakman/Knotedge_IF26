package fr.if26.projet.knotedge_if26;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//https://www.youtube.com/watch?v=i5UcFAdKe5M&ab_channel=WintechTutorials
//Tutorial for photo
public class SettingsProfilFragment extends Fragment {

    private Button profilePictureButton;
    private Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    public Intent cropIntent, CamIntent, GaleryIntent;
    public Uri uri;
    final int RequestPermissionCode = 1;

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings_profil, container, false);

        profilePictureButton = view.findViewById(R.id.profil_picture_button);
        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            RequestRuntimePermission();
        }
        return view;
    }

    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Toast.makeText(getView().getContext(), "L'accès à la Caméra nous permet de changer la photo de profil", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    private void selectImage() {

        final CharSequence[] items = {"Appareil Photo", "Galerie", "Retour"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
        builder.setTitle("Changer la photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Appareil Photo")) {
                    CameraOpen();

                } else if (items[i].equals("Galerie")) {
                    GaleryOpen();

                } else if (items[i].equals("Retour")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    private void CameraOpen() {
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        CamIntent.putExtra("crop", "true");
        CamIntent.putExtra("aspectX", 1);
        CamIntent.putExtra("aspectY", 1);
        try {

            CamIntent.putExtra("return-data", true);
            startActivityForResult(CamIntent, REQUEST_CAMERA);

        } catch (ActivityNotFoundException e) {
// Do nothing for now
        }

    }

    private void GaleryOpen() {
        GaleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GaleryIntent.createChooser(GaleryIntent, "Choisissez un Fichier"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                //uri = data.getData();
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bmp = bundle.getParcelable("data");
                    BitmapDrawable bmpdrawable = new BitmapDrawable(getView().getContext().getResources(), bmp);
                    profilePictureButton.setBackground(bmpdrawable);
                }

                //CropImage();
            } else if (requestCode == SELECT_FILE) {
                if (data != null) {
                    uri = data.getData();
                    CropImage();
                }

            } else if (requestCode == 1) {
                if (data != null) {

                    Bundle bundle = data.getExtras();
                    Bitmap bmp = bundle.getParcelable("data");
                    BitmapDrawable bmpdrawable = new BitmapDrawable(getView().getContext().getResources(), bmp);
                    profilePictureButton.setBackground(bmpdrawable);


                }
            }
        }
    }

    private void CropImage() {
        try {
            cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");


            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scaleUpIfNeeded", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, 1);
        } catch (ActivityNotFoundException ex) {

        }

    }

}
