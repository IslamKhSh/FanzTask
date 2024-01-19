package com.fanz.task.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fanz.task.R;
import com.fanz.task.entity.Player;

import java.util.ArrayList;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerItemViewHolder> {

    private ArrayList<Player> players;
    private MainView mainView;

    public PlayersAdapter(ArrayList<Player> players, MainView mainView) {
        this.players = players;
        this.mainView = mainView;
    }

    @NonNull
    @Override
    public PlayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerItemViewHolder holder, int position) {
        holder.setPlayerData(players.get(position), position);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class PlayerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView playerName;
        private TextView playerPosition;
        private TextView playerScore;
        private ImageView playerImage;
        private View playerView;

        public PlayerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.tv_player_name);
            playerPosition = itemView.findViewById(R.id.tv_player_position);
            playerScore = itemView.findViewById(R.id.tv_player_score);
            playerImage = itemView.findViewById(R.id.img_player);
            playerView = itemView.findViewById(R.id.player_card);
        }

        public void setPlayerData(Player player, int index) {
            playerImage.setImageResource((index % 2) == 0 ? R.drawable.salah : R.drawable.cr7);
            playerName.setText(player.getName());
            playerPosition.setText(player.getPosition());
            playerScore.setText("#" + player.getScore());
            playerView.setOnClickListener(v -> {
                mainView.onPlayerClicked(index);
            });
        }
    }
}
