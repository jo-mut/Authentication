package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mHomeToolbar;
    private ImageView mAddImageView;
    private RecyclerView mPlayerRecyclerView;
    private PlayersAdapter mPlayersAdapter;
    private List<Player> mPlayers = new ArrayList<>();
    private DatabaseManager mDatabaseManager;
    private static final String ADD_PLAYER = "add player";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // initialize home views
        mHomeToolbar = findViewById(R.id.homeToolbar);
        mAddImageView = findViewById(R.id.addImageView);
//        initialize click listeners
        mAddImageView.setOnClickListener(this);
        // set support action bar
        setSupportActionBar(mHomeToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        // back button on toolbar
//        mHomeToolbar.setNavigationIcon(R.drawable.ic_left_arrow);
//        mHomeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        mPlayerRecyclerView = findViewById(R.id.playerRecyclerView);
        mDatabaseManager = new DatabaseManager(this);
        mPlayers = mDatabaseManager.getPlayers();

        setRecyclerView();

    }

    private void setRecyclerView() {
        mPlayersAdapter = new PlayersAdapter(this, mPlayers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPlayerRecyclerView.setLayoutManager(layoutManager);
        mPlayerRecyclerView.setHasFixedSize(false);
        mPlayerRecyclerView.setAdapter(mPlayersAdapter);
        mPlayersAdapter.notifyItemInserted(mPlayers.size() - 1);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.addImageView) {
            Intent intent = new Intent(HomeActivity.this, EditPlayerActivity.class);
            intent.putExtra(HomeActivity.ADD_PLAYER, "add player");
            startActivity(intent);
        }

    }
}
