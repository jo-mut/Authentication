package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Toolbar mHomeToolbar;
    private RecyclerView mPlayerRecyclerView;
    private PlayersAdapter mPlayersAdapter;
    private List<Player> mPlayers = new ArrayList<>();
    private DatabaseManager mDatabaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // initialize home views
        mHomeToolbar = findViewById(R.id.homeToolbar);
        mHomeToolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        mHomeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
}
