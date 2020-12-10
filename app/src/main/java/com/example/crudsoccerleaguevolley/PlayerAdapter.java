package com.example.crudsoccerleaguevolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private Context mContext;
    private ArrayList<PlayersItem> mPlayersList;

    public PlayerAdapter(Context context, ArrayList<PlayersItem> exampleList) {
        mContext = context;
        mPlayersList = exampleList;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        PlayersItem currentItem = mPlayersList.get(position);
        String imageUrl = currentItem.getmImageUrl();
        String id_team = currentItem.getmIdTeam();
        String first_name = currentItem.getmFirstName();
        String last_name = currentItem.getmLastName();
        String kit = currentItem.getmKit();
        String positionplayer = currentItem.getmPosition();
        String country = currentItem.getmCountry();

        holder.mTextViewIdTeam.setText(id_team);
        holder.mTextViewFirstName.setText(first_name);
        holder.mTextViewLastName.setText(last_name);
        holder.mTextViewKit.setText(kit);
        holder.mTextViewPosition.setText(positionplayer);
        holder.mTextViewCountry.setText(country);

        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mPlayersList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewIdTeam;
        public TextView mTextViewFirstName;
        public TextView mTextViewLastName;
        public TextView mTextViewKit;
        public TextView mTextViewPosition;
        public TextView mTextViewCountry;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewIdTeam = itemView.findViewById(R.id.text_view_id_team);
            mTextViewFirstName = itemView.findViewById(R.id.text_view_first_name);
            mTextViewLastName = itemView.findViewById(R.id.text_view_last_name);
            mTextViewKit = itemView.findViewById(R.id.text_view_kit);
            mTextViewPosition = itemView.findViewById(R.id.text_view_position);
            mTextViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }


}
