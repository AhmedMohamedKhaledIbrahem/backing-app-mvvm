package com.ahmedelfeky.backingapp.Utails;

import android.view.View;
import android.widget.TextView;

import com.ahmedelfeky.backingapp.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView tvRecipe;
    CardView recipeCard;
    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvRecipe=itemView.findViewById(R.id.tv_recipe_name);
        recipeCard=itemView.findViewById(R.id.cv_recipe_card);

    }
}
