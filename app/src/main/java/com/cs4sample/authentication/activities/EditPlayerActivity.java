package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditPlayerActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Toolbar mToolbar;
    private ImageView profileImageView;
    private EditText nameEditText;
    private EditText ageEditText;
    private EditText positionEditText;
    private Button doneButton;
    private DatabaseManager mDatabaseManager;
    private static final int PICK_IMAGE = 111;
    private String playerUsername;
    private String addPLayer;
    private Player mPlayer;
    private String encodedPath;
    private static final String PLAYER_NAME = Player.ROW_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        mToolbar = findViewById(R.id.editPlayerToolBar);
        profileImageView = findViewById(R.id.profileImageView);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        positionEditText = findViewById(R.id.positionEditText);
        doneButton = findViewById(R.id.doneButton);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        doneButton.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        mDatabaseManager = new DatabaseManager(this);


        //get intent extras
        getIntentExtras();
        // set toolbar title
        setToolbarTitle();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.doneButton) {
            if (addPLayer != null) {
                mDatabaseManager.savePlayer(getPlayer());
                Toast.makeText(this, "save successful", Toast.LENGTH_SHORT).show();
            }else {
                if (updatePlayer()) {
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Oops! Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (id == R.id.profileImageView) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);

    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                byte[] bytes = null;
                try {
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    bytes = baos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                savePhotoToFolder(uri);
                saveReceivedImage(uri, String.valueOf(System.currentTimeMillis()));
            }

        }


    }


    private void setToolbarTitle() {
        if (addPLayer != null) {
            mToolbar.setTitle("Add Player");
        }else {
            mToolbar.setTitle("Edit");
        }
    }

    private void getIntentExtras() {
        if (getIntent().getExtras() != null) {
            playerUsername = getIntent().getStringExtra(EditPlayerActivity.PLAYER_NAME);

        }
    }

    public Player getPlayer() {
        mPlayer = new Player();
        String name = nameEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {
            mPlayer.setName(name);
        }

        if (!TextUtils.isEmpty(position)) {
            mPlayer.setPosition(position);
        }

        if (!TextUtils.isEmpty(age)) {
            mPlayer.setAge(age);
        }

        if (!TextUtils.isEmpty(encodedPath)) {
            mPlayer.setImage(encodedPath);

        }

        nameEditText.setText("");
        ageEditText.setText("");
        positionEditText.setText("");
        profileImageView.setImageBitmap(null);
        return mPlayer;
    }

    private boolean updatePlayer() {
        mPlayer = new Player();
        String name = nameEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {
            mPlayer.setName(name);
        }

        if (!TextUtils.isEmpty(position)) {
            mPlayer.setPosition(position);
        }

        if (!TextUtils.isEmpty(age)) {
            mPlayer.setAge(age);
        }

        if (!TextUtils.isEmpty(encodedPath)) {
            mPlayer.setImage(encodedPath);
        }

        nameEditText.setText("");
        ageEditText.setText("");
        positionEditText.setText("");
        profileImageView.setImageBitmap(null);
        finish();
        return mDatabaseManager.updatePlayer(playerUsername, mPlayer);
    }

    private void saveReceivedImage(Uri uri, String imageName){
        try {
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.Source source = ImageDecoder
                        .createSource(this.getContentResolver(), uri);
                bitmap = ImageDecoder.decodeBitmap(source);
                profileImageView.setImageBitmap(bitmap);
            }else {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                profileImageView.setImageBitmap(bitmap);
            }

            File path = new File(this.getFilesDir(), "Auth" + File.separator + "Images");
            if(!path.exists()){
                path.mkdirs();
            }
            File outFile = new File(path, imageName + ".jpeg");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            encodePath(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void encodePath(File file) {
        encodedPath = Base64.encodeToString(file.getPath().getBytes(), Base64.DEFAULT);
//        Log.d("successful path", encodedPath);
//        byte[] imageBytes = Base64.decode(encodedPath, Base64.DEFAULT);
//        String decodedPath = new String(imageBytes);
//        if (decodedPath != null) {
//            Log.d("decoded path", decodedPath);
//        }else  {
//            Log.d("decoded path", "photo null");
//        }

    }
}
