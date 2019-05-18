package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private Recipe[] mRecipeData;

    interface RecipeClickHandler {
        void onClick(Recipe recipe);
    }

    private final RecipeClickHandler mClickHandler;

    public RecipeAdapter(RecipeClickHandler handler) {
        this.mClickHandler = handler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tv_recipe_title) TextView mRecipeTitleTextView;
        @BindView(R.id.iv_recipe_image) ImageView mRecipeIconImageView;

        public RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeData[adapterPosition];
            mClickHandler.onClick(recipe);
        }
    }
    @NonNull
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_cards;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId, parent, false);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeAdapterViewHolder holder, int position) {
        holder.mRecipeTitleTextView.setText(mRecipeData[position].getName());

        if(mRecipeData[position].getImage() == null || mRecipeData[position].getImage().equals("")) {
            holder.mRecipeIconImageView.setImageResource(R.drawable.ic_recipe_image);
        } else {
            Picasso.get().load(mRecipeData[position].getImage()).into(holder.mRecipeIconImageView);
        }

    }

    @Override
    public int getItemCount() {
        if(mRecipeData == null) return 0;

        return mRecipeData.length;
    }

    public void setRecipeData(Recipe[] data) {
        this.mRecipeData = data;
        notifyDataSetChanged();
    }

    public Recipe[] getRecipeData() {
        return this.mRecipeData;
    }
}
