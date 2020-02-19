package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs4sample.authentication.BuildConfig;
import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Forms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetailsPhotosActivity extends AppCompatActivity
        implements View.OnClickListener {
    private ImageView profileImageView;
    private ImageView idFrontImageView;
    private ImageView idBacKImageView;
    private Button mSubmitButton;
    private Button mPreviousButton;
    private Toolbar mToolbar;

    private static final int PROFILE_IMAGE = 0;
    private static final int ID_FRONT = 1;
    private static final int ID_BACK = 2;
    private String encodedPath;
    private String profileImage;
    private String idFront;
    private String idBack;
    private DatabaseManager mDatabaseManager;
    public String photoFileName =  System.currentTimeMillis() + "";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        profileImageView = findViewById(R.id.profileImageView);
        idFrontImageView = findViewById(R.id.idFrontImageView);
        idBacKImageView = findViewById(R.id.idBackImageView);
        mSubmitButton = findViewById(R.id.submitButton);
        mPreviousButton = findViewById(R.id.previousButton);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        profileImageView.setOnClickListener(this);
        idFrontImageView.setOnClickListener(this);
        idBacKImageView.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);

        mDatabaseManager = new DatabaseManager(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Forms.profilePhoto != null) {
            profileImageView.setImageURI(Uri.parse(decodePath(Forms.profilePhoto)));
        }

        if (Forms.idBack != null) {
            idBacKImageView.setImageURI(Uri.parse(decodePath(Forms.idBack)));

        }

        if (Forms.idFront != null) {
            idFrontImageView.setImageURI(Uri.parse(decodePath(Forms.idFront)));
        }

    }

    @Override
    public void onClick(View v) {
        if (v == profileImageView){
            openCameraIntent(PROFILE_IMAGE);
        }

        if (v == idFrontImageView){
            openCameraIntent(ID_FRONT);
        }

        if (v == idBacKImageView){
            openCameraIntent(ID_BACK);
        }

        if (v == mSubmitButton) {
            showConfirmationDialog();
        }

        if (v == mPreviousButton) {
            finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PROFILE_IMAGE:
                    Forms.profilePhoto = encodePath(photoFile);
                    break;
                case ID_FRONT:
                    Forms.idFront = encodePath(photoFile);
                    break;
                case ID_BACK:
                    Forms.idBack = encodePath(photoFile);
                    break;
            }

            if (Forms.profilePhoto != null) {
                profileImageView.setImageURI(Uri.parse(decodePath(Forms.profilePhoto)));
            }

            if (Forms.idBack != null) {
                idBacKImageView.setImageURI(Uri.parse(decodePath(Forms.idBack)));

            }

            if (Forms.idFront != null) {
                idFrontImageView.setImageURI(Uri.parse(decodePath(Forms.idFront)));
            }

        }


    }

    private void outPutBitmapArray(ContentResolver cr, Uri uri) {
        byte[] bytes = null;
        try {
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bytes = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private String encodePath(File file) {
        encodedPath = Base64.encodeToString(file.getPath().getBytes(), Base64.DEFAULT);
        return encodedPath;
    }

    private String decodePath(String s) {
        byte[] imageBytes = Base64.decode(s, Base64.DEFAULT);
        String decodedPath = new String(imageBytes);
        return decodedPath;
    }

    private void openCameraIntent(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File path = new File(this.getFilesDir(), "auth" + File.separator + "images");
        if(!path.exists()){
            path.mkdirs();
        }
         photoFile = getPhotoFileUri();
         Uri fileProvider = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    public File getPhotoFileUri() {
        File path = new File(this.getFilesDir(), "auth" + File.separator + "images");
//        File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Authentication");
        if (!path.exists() && !path.mkdirs()){
            Log.d("Authentication", "failed to create directory");
        }
        File file = new File(path.getPath() + File.separator + System.currentTimeMillis() + ".jpeg");

//        try {
//            Bitmap bitmap = null;
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return file;
    }

    private void showConfirmationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_confirm_dialog, null);
        final TextView mBirthDateTextView = view.findViewById(R.id.dateOfBirthTextView);
        final TextView mOtherNamesTextView = view.findViewById(R.id.otherTextView);
        final TextView mSurnameTextView = view.findViewById(R.id.surnameTextView);
        final TextView mIdNumberTextView = view.findViewById(R.id.idNumberTextView);
        final TextView genderTextView = view.findViewById(R.id.genderTextView);
        final TextView countryTextView = view.findViewById(R.id.countryTextView);
        final TextView organisationTextView = view.findViewById(R.id.organisationTextView);
        final TextView contributorTextView = view.findViewById(R.id.contributionTextView);
        final TextView typeOfInsuranceTextView = view.findViewById(R.id.typeOfInsuranceTextView);
        final ImageView profileImageView = view.findViewById(R.id.profileImageView);
        final ImageView idFrontImageView = view.findViewById(R.id.idFrontImageView);
        final ImageView idBacKImageView = view.findViewById(R.id.idBackImageView);

        mBirthDateTextView.setText(Forms.dateOfBirth);
        mSurnameTextView.setText(Forms.surname);
        mIdNumberTextView.setText(Forms.idNumber);
        mOtherNamesTextView.setText(Forms.otherNames);
        genderTextView.setText(Forms.gender);
        contributorTextView.setText(Forms.contributionPayer);
        typeOfInsuranceTextView.setText(Forms.insuranceType);
        organisationTextView.setText(Forms.employerOrganisation);
        countryTextView.setText(Forms.country);

        if (Forms.profilePhoto != null ) {
        byte[] imageBytes = Base64.decode(Forms.profilePhoto, Base64.DEFAULT);
        String decodedPath = new String(imageBytes);
        Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
        profileImageView.setImageBitmap(bitmap);

    }

        if (Forms.idBack != null) {
        byte[] imageBytes = Base64.decode(Forms.idBack, Base64.DEFAULT);
        String decodedPath = new String(imageBytes);
        Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
        idBacKImageView.setImageBitmap(bitmap);
    }

        if (Forms.idFront != null) {
        byte[] imageBytes = Base64.decode(Forms.idFront, Base64.DEFAULT);
        String decodedPath = new String(imageBytes);
        Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
        idFrontImageView.setImageBitmap(bitmap);
    }

    Button submitButton = view.findViewById(R.id.submitButton);
        builder.setView(view);
    final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDatabaseManager.saveRegData()) {
                Log.d("successful reg save", "run" );
                alertDialog.dismiss();
            }
        }
    });

}

}
