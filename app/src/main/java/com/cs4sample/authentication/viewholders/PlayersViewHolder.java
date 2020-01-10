package com.cs4sample.authentication.viewholders;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs4sample.authentication.R;

public class PlayersViewHolder extends RecyclerView.ViewHolder {
    Context context;
    View view;
    public TextView mPlayerNameTextView;
    public TextView mPlayerAgeTextView;
    public TextView mPlayerPositionTextView;
    public ImageView mEditImageView;
    public ImageView mProfileImageView;

    public PlayersViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        view = itemView;

        mPlayerNameTextView = (TextView) view.findViewById(R.id.playerNameTexView);
        mPlayerAgeTextView = (TextView) view.findViewById(R.id.playerAgeTexView);
        mPlayerPositionTextView = (TextView) view.findViewById(R.id.playerPositionTexView);
        mEditImageView = (ImageView) view.findViewById(R.id.editPlayerImageView);
        mProfileImageView =  (ImageView) view.findViewById(R.id.profileImageView);
    }
}
