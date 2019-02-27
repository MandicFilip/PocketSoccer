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

public class LeftPreGameAdapter extends RecyclerView.Adapter<LeftPreGameAdapter.LeftHolder> {

    private static final int HOME_DEFAULT_INDEX = 0;

    private Context context;

    private ArrayList<String> countryNames;

    private int selected = HOME_DEFAULT_INDEX;

    public int getSelected() {
        return selected;
    }

    public LeftPreGameAdapter(Context context, ArrayList<String> countryNames) {
        this.context = context;
        this.countryNames = countryNames;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeftHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.left_pre_game_holder, viewGroup, false);
        return new LeftHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftHolder leftHolder, int i) {
        leftHolder.getTextView().setText(countryNames.get(i));
    }

    @Override
    public int getItemCount() {
        return (countryNames != null) ? countryNames.size() : 0;
    }

    public class LeftHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LeftHolder(@NonNull final View itemView) {
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

            textView = itemView.findViewById(R.id.left_text_view_country_id);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
