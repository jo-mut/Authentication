package com.cs4sample.authentication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.viewholders.PlayersViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends
        RecyclerView.Adapter<PlayersViewHolder> {
    private Context mContext;
    private List<Player> mPlayers = new ArrayList<>();

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
        Player player = mPlayers.get(position);
        holder.mPlayerNameTextView.setText(player.getName());
        holder.mPlayerAgeTextView.setText(player.getAge());
        holder.mPlayerPositionTextView.setText(player.getPosition());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlayers.size() - 1;
    }
}
