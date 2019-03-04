package com.example.mandula.pocketsoccer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.database.entities.Game;

import java.util.ArrayList;

public class FaceToFaceStatisticsAdapter extends RecyclerView.Adapter<FaceToFaceStatisticsAdapter.MyHolder> {

    private Context context;
    private ArrayList<Game> games;

    public FaceToFaceStatisticsAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
        notifyDataSetChanged();
    }

    public void emptyList() {
        games.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.left_pre_game_holder, viewGroup, false);
        return new FaceToFaceStatisticsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.setData(games.get(i));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView dateTimeTextView;
        private TextView playersTextView;
        private TextView resultTextView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            dateTimeTextView = itemView.findViewById(R.id.date_time_view_id);
            playersTextView = itemView.findViewById(R.id.players_view_id);
            resultTextView = itemView.findViewById(R.id.result_view_id);
        }

        public void setData(Game game) {
            dateTimeTextView.setText(game.getDate().toString());
            playersTextView.setText(formatPlayersString(game.getHomeUser(), game.getAwayUser()));
            resultTextView.setText(formatResultString(game.getHomeGoals(), game.getAwayGoals()));
        }

        private String formatPlayersString(String home, String away) {
            return home + " vs " + away;
        }

        private String formatResultString(int homeGoals, int awayGoals) {
            return Integer.toString(homeGoals) + " : " + Integer.toString(awayGoals);
        }
    }
}
