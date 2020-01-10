package com.cs4sample.authentication.adapters;

import android.content.Context;
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
import com.cs4sample.authentication.fragments.EditFragment;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.viewholders.PlayersViewHolder;

import java.util.ArrayList;
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

    public void notifyAdapter(List<Player> players) {
        notifyItemInserted(mPlayers.size() - 1);
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
        holder.mPlayerAgeTextView.setText(player.getAge());
        holder.mPlayerPositionTextView.setText(player.getPosition());

        if (player.getImage() != null) {
            Bitmap profileImage = BitmapFactory
                    .decodeByteArray(player.getImage(), 0, player.getImage().length);
            holder.mProfileImageView.setImageBitmap(profileImage);
        }


        holder.mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Bundle bundle = new Bundle();
                bundle.putString(PlayersAdapter.PLAYER_NAME, player.getName());
                EditFragment editFragment = EditFragment.newInstance(EditFragment.class.getName());
                Toast.makeText(mContext, "Username " + bundle.toString()
                        , Toast.LENGTH_SHORT).show();
                editFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                editFragment.show(fragmentManager, PlayersAdapter.class.getSimpleName());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlayers.size() - 1;
    }
}