package com.example.mandula.pocketsoccer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.FlagManager;

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
        if (selected == i) leftHolder.getTextView().setTextColor(Color.BLUE);
        else leftHolder.getTextView().setTextColor(Color.BLACK);
        leftHolder.getTextView().setText(countryNames.get(i));
        leftHolder.onBind(countryNames.get(i));
    }

    @Override
    public int getItemCount() {
        return (countryNames != null) ? countryNames.size() : 0;
    }

    public class LeftHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public LeftHolder(@NonNull final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.left_text_view_country_id);

            imageView = itemView.findViewById(R.id.left_image_view_country_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String country = textView.getText().toString();
                    for (int i = 0; i < countryNames.size(); i++) {
                        if (countryNames.get(i).equals(country)) {
                            selected = i;
                            itemView.setSelected(true);
                            textView.setTextColor(Color.BLUE);
                            notifyDataSetChanged();
                        } else {
                            itemView.setSelected(false);
                            textView.setTextColor(Color.BLACK);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }

        public void onBind(String country) {
            imageView.setImageResource(FlagManager.getCountryFlag(country));
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
