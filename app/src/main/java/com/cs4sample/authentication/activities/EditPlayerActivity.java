package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.models.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    private byte[] imageArray = null;
    private String playerUsername;
    private String addPLayer;
    private Player mPlayer;
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
            }else {
                if (updatePlayer(playerUsername)) {
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
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
                Bitmap bitmap = null;
                try {

                    if (Build.VERSION.SDK_INT >= 29) {
                        ImageDecoder.Source source = ImageDecoder
                                .createSource(this.getContentResolver(), uri);
                        bitmap = ImageDecoder.decodeBitmap(source);
                        profileImageView.setImageBitmap(bitmap);
                        imageArray = getPhotoBitmap(bitmap);
                    }else {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        profileImageView.setImageBitmap(bitmap);
                        imageArray = getPhotoBitmap(bitmap);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

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
            addPLayer = getIntent().getStringExtra(EditPlayerActivity.PLAYER_NAME);

        }
    }

    private byte[] getPhotoBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageArray = baos.toByteArray();
        try {
            baos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return imageArray;

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

        if (imageArray != null) {
            mPlayer.setImage(imageArray);

        }

        nameEditText.setText("");
        ageEditText.setText("");
        positionEditText.setText("");
        profileImageView.setImageBitmap(null);
        return mPlayer;
    }

    private boolean updatePlayer(String username) {
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

        if (imageArray != null) {
            mPlayer.setImage(imageArray);

        }

        nameEditText.setText("");
        ageEditText.setText("");
        positionEditText.setText("");
        profileImageView.setImageBitmap(null);
        finish();
        return mDatabaseManager.updatePlayer(username, mPlayer);
    }

}
