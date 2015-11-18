package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.FavoritesAdapter;
import com.taeyeona.amaizingunicornrecipes.Activity.MissingIngredients;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Chau on 11/7/2015.
 */
public class IngredientsFragment extends Fragment {

    ImageButton favorite;
 //   StringBuilder ingredients = new StringBuilder();
    private StringBuilder ingredients = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = (TextView) getActivity().findViewById(R.id.textView5);
        final TextView text2 = (TextView) getActivity().findViewById(R.id.textView8);
        text.setText(getArguments().getString("Title"));

        if(getArguments().getString("API").equals("Food2Fork")){
            final JSONRequest request = new JSONRequest();
            request.createResponse(Auth.GET_URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                    "", "", "", "", "", "", "", "", "", 0.0, 0.0, getArguments().getString("RecipeID"), null, null);
            request.sendResponse(getActivity().getApplicationContext());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONObject response = request.getResponse();
                    try {
                        JSONObject recipe = response.getJSONObject("recipe");
                        Log.d(IngredientsFragment.class.getSimpleName(), recipe.toString());
                        JSONArray ingredientsList = recipe.getJSONArray("ingredients");
                        Log.d(IngredientsFragment.class.getSimpleName(), ingredientsList.toString());
                        for (int i = 0; i < ingredientsList.length(); i++) {
                            ingredients.append(ingredientsList.getString(i));
                            ingredients.append('\n');
                            Log.d(IngredientsFragment.class.getSimpleName(), ingredients.toString());
                        }
                        //Putting a setText here because if put outside, it won't show F2F's ingredient list
                        text2.setText("Ingredients:\n" + ingredients.toString());
                        text2.setMovementMethod(new ScrollingMovementMethod());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);

        }else{
            String[] ingredientLines = getArguments().getStringArray("Ingredients");
            for (int i = 0; i < ingredientLines.length; i++) {
                ingredients.append(ingredientLines[i]);
                ingredients.append('\n');
            }
            text2.setText("Ingredients:\n" + ingredients.toString());
            text2.setMovementMethod(new ScrollingMovementMethod());
        }

        Button but = (Button) getActivity().findViewById(R.id.button4);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MissingIngredients.class);
                intent.putExtra("Ingredients", ingredients.toString());
                startActivity(intent);

            }
        });

        ImageButton fav = (ImageButton) getActivity().findViewById(R.id.favoriteButton);
        final Favorites favObj = new Favorites(getActivity().getApplicationContext());
        fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favObj.storeRecipe(getArguments().getString("Title"));
            }
        });

    }
}
