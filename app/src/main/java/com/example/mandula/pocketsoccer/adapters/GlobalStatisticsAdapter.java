package com.example.mandula.pocketsoccer.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.database.entity.Game;
import com.example.mandula.pocketsoccer.activities.FaceToFaceStatisticsActivity;
import com.example.mandula.pocketsoccer.activities.GlobalStatisticsActivity;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class GlobalStatisticsAdapter extends RecyclerView.Adapter<GlobalStatisticsAdapter.MyHolder> {

    private List<Game> games;
    private GlobalStatisticsActivity activity;

    public GlobalStatisticsAdapter(GlobalStatisticsActivity context, List<Game> games) {
        this.activity = context;
        this.games = games;
        notifyDataSetChanged();
    }

    public void emptyList() {
        games.clear();
        notifyDataSetChanged();
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.global_stats_holder, viewGroup, false);
        return new GlobalStatisticsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.setData(games.get(i));
    }

    @Override
    public int getItemCount() {
        return (games != null) ? games.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView dateTimeTextView;
        private TextView playersTextView;
        private TextView resultTextView;

        private String homeUser;
        private String awayUser;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            dateTimeTextView = itemView.findViewById(R.id.global_date_time_view_id);
            playersTextView = itemView.findViewById(R.id.global_players_view_id);
            resultTextView = itemView.findViewById(R.id.global_result_view_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, FaceToFaceStatisticsActivity.class);

                    intent.putExtra("HOME_USER", homeUser);
                    intent.putExtra("AWAY_USER", awayUser);

                    activity.startActivityForResult(intent,
                            GlobalStatisticsActivity.FACE_TO_FACE_STATS_CALL_CODE);
                }
            });
        }

        public void setData(Game game) {

            homeUser = game.getHomeUser();
            awayUser = game.getAwayUser();

            dateTimeTextView.setText(formatDateTime(game.getDateTime()));
            playersTextView.setText(formatPlayersString(game.getHomeUser(), game.getAwayUser()));
            resultTextView.setText(formatResultString(game.getHomeGoals(), game.getAwayGoals()));
        }

        private String formatDateTime(long dateTime) {
            Date date = new Date(dateTime);
            Time time = new Time(dateTime);
            return date.toString() + " " + time.toString();
        }

        private String formatPlayersString(String home, String away) {
            return home + " vs " + away;
        }

        private String formatResultString(int homeGoals, int awayGoals) {
            return Integer.toString(homeGoals) + " : " + Integer.toString(awayGoals);
        }
    }
}
