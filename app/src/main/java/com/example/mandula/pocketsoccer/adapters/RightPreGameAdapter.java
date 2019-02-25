package com.example.mandula.pocketsoccer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;

import java.util.ArrayList;

public class RightPreGameAdapter extends RecyclerView.Adapter<RightPreGameAdapter.RightHolder> {

    private static final int AWAY_DEFAULT_INDEX = 1;

    private Context context;

    private ArrayList<String> countryNames;

    private int selected = AWAY_DEFAULT_INDEX;

    public int getSelected() {
        return selected;
    }

    public RightPreGameAdapter(Context context, ArrayList<String> countryNames) {
        this.context = context;
        this.countryNames = countryNames;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RightHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.right_pre_game_holder, viewGroup, false);
        return new RightPreGameAdapter.RightHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RightHolder rightHolder, int i) {
        rightHolder.getTextView().setText(countryNames.get(i));
    }

    @Override
    public int getItemCount() {
        return (countryNames != null) ? countryNames.size() : 0;
    }

    public class RightHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public RightHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String country = textView.getText().toString();
                    for (int i = 0; i < countryNames.size(); i++) {
                        if (countryNames.get(i).equals(country)) {
                            selected = i;
                            itemView.setSelected(true);
                        } else {
                            itemView.setSelected(false);
                        }
                    }
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
