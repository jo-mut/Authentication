package com.cs4sample.authentication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.activities.EditPlayerActivity;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.viewholders.PlayersViewHolder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayersAdapter extends
        RecyclerView.Adapter<PlayersViewHolder> {
    private Context mContext;
    private List<Player> mPlayers = new ArrayList<>();
    private static final String PLAYER_NAME = Player.ROW_NAME;


    public PlayersAdapter(Context mContext, List<Player> mPlayers) {
        this.mContext = mContext;
        this.mPlayers = mPlayers;
    }


    @NonNull
    @Override
    public PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_players, parent, false);
        return new PlayersViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull PlayersViewHolder holder, int position) {
        final Player player = mPlayers.get(position);
        holder.mPlayerNameTextView.setText(player.getName());
        holder.mPlayerAgeTextView.setText("Age: " + player.getAge());
        holder.mPlayerPositionTextView.setText("Position: " + player.getPosition());

        if (player.getImage() != null) {

            ByteArrayInputStream stream = new ByteArrayInputStream(player.getImage());
            Log.d("player stream", stream.toString());
            Bitmap profileImage = BitmapFactory
                    .decodeStream(stream);
            if (profileImage != null) {
//                Log.d("player photo", "photo present");
                holder.mProfileImageView.setImageBitmap(profileImage);
            }else  {
                Log.d("player photo", "photo null");
            }
        }


        holder.mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditPlayerActivity.class);
                intent.putExtra(PlayersAdapter.PLAYER_NAME, player.getName());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlayers.size() - 1;
    }
}
