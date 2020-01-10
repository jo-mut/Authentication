package com.cs4sample.authentication.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.print.PrinterId;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends DialogFragment implements View.OnClickListener{
    private ImageView profileImageView;
    private EditText nameEditText;
    private EditText ageEditText;
    private EditText positionEditText;
    private Button doneButton;
    private DatabaseManager mDatabaseManager;
    private static final int PICK_IMAGE = 23;
    private static final int RESULT_OK = 4;
    private byte[] imageArray = null;
    private String playerUsername;
    private static final String PLAYER_NAME = Player.ROW_NAME;



    public static EditFragment newInstance(String title){
        EditFragment editFragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(EditFragment.class.getName(), title);
        editFragment.setArguments(args);
        return editFragment;

    }

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit, container, false);
        profileImageView = view.findViewById(R.id.profileImageView);
        nameEditText = view.findViewById(R.id.nameEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        positionEditText = view.findViewById(R.id.positionEditText);
        doneButton = view.findViewById(R.id.doneButton);

        doneButton.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        mDatabaseManager = new DatabaseManager(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            playerUsername = bundle.getString(EditFragment.PLAYER_NAME);
//            Toast.makeText(getContext(), "Username " + playerUsername, Toast.LENGTH_SHORT).show();

        }

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.doneButton) {
            if (updatePlayer(playerUsername)) {
                Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                dismiss();
            }else {
                Toast.makeText(getContext(), "Oops! Update failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.profileImageView) {
            Intent intent = new Intent();
            // Show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode
                == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Log.d("Picked Image:", uri.toString());
            try {
                ImageDecoder.Source source = ImageDecoder
                        .createSource(getContext().getContentResolver(), uri);
                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                profileImageView.setImageBitmap(bitmap);
                imageArray = getPhotoBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getPhotoBitmap(Bitmap bitmap) {
        bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
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

    private boolean updatePlayer(String username) {
        Player player = new Player();
        String name = nameEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();


        if (!TextUtils.isEmpty(name)) {
            player.setName(name);
        }

        if (!TextUtils.isEmpty(position)) {
            player.setPosition(position);
        }

        if (!TextUtils.isEmpty(age)) {
            player.setAge(age);
        }

        if (imageArray != null) {
            player.setImage(imageArray);

        }

        nameEditText.setText("");
        ageEditText.setText("");
        positionEditText.setText("");
        profileImageView.setImageBitmap(null);
        return mDatabaseManager.updatePlayer(username, player);
    }
}
