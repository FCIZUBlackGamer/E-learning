package com.example.rootyasmeen.FCI_Learn;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class Control_instructor extends Fragment{
    Spinner spinner;
    Button button, write, view_post, result;
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Uri filePath;
    String fileName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_p,container,false );
        button = (Button)view.findViewById( R.id.upload );
        write = (Button)view.findViewById( R.id.write_post );
        spinner = (Spinner)view.findViewById( R.id.spin_subject );
        view_post = (Button)view.findViewById( R.id.view_posts );
        result = (Button)view.findViewById( R.id.show_result );
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        requestStoragePermission();
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
//                        return;
//                    }
//                }
//                enable_button();

                showFileChooser();
            }
        } );
        write.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getActivity() , Write_Post.class);
                intent.putExtra("Subject",spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        } );
        view_post.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getActivity() , View_posts.class);
                intent.putExtra("Subject",spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        } );

    }

    public void uploadMultipart() {

        //getting the actual path of the image
        String path = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            path = FilePath.getPath(getActivity(), filePath);
        }else {
            Toast.makeText(getActivity(), "Your Android Version Doesn't Support This Feature!", Toast.LENGTH_LONG).show();
        }

        if (path == null) {

            Toast.makeText(getActivity(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(getActivity(), uploadId, "http://momenshaheen.16mb.com/e_learning/uploadfiles.php")
                        .addFileToUpload(path, "uploaded_file") //Adding file
                        .addParameter("name", fileName) //Adding text parameter to the request
                        .addParameter("subject", spinner.getSelectedItem().toString()) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            final EditText editText = new EditText( getActivity() );
            editText.setHint( "Enter File Name ... " );
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
            builder.setMessage( "Upload this File ?!" )
                    .setView( editText )
                    .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fileName = editText.getText().toString();
                            uploadMultipart();
                        }
                    } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            } ).show();
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
