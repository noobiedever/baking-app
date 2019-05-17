package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Step;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private Step[] mStepsData;
    private Ingredient[] mRecipeIngredients;

    interface StepClickHandler {
        void onClick(Step[] steps, int position);
    }

    private StepClickHandler mStepClickHandler;

    public StepAdapter(StepClickHandler clickHandler, Ingredient[] ingredients) {
        this.mStepClickHandler = clickHandler;
        this.mRecipeIngredients = ingredients;
    }

    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_step_cards;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapterViewHolder holder, int position) {

        String text = mStepsData[position].getShortDescription();
        holder.mStepDescriptionTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        if(mStepsData == null) return 0;
        return mStepsData.length;
    }

    public void setStepsData(Step[] steps) {
        this.mStepsData = steps;
        notifyDataSetChanged();
    }

    public Step[] getData() {
        return mStepsData;
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        @BindView(R.id.tv_recipe_step_description)
        TextView mStepDescriptionTextView;

        public StepAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mStepClickHandler.onClick(mStepsData, position);
        }
    }
}
