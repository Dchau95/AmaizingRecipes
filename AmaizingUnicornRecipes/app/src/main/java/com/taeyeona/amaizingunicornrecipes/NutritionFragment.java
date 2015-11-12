package com.taeyeona.amaizingunicornrecipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Chau on 11/7/2015.
 */


public class NutritionFragment extends Fragment {

    StringBuilder nutrients = new StringBuilder();

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here
        text = (TextView) getActivity().findViewById(R.id.textView7);
        if(getArguments().getString("API").equals("Food2Fork")){
            // Don't show nutrients
        }else {
            String[] nutrientLines = getArguments().getStringArray("Nutrients");
            for (int i = 0; i < nutrientLines.length; i++) {
                nutrients.append(nutrientLines[i]);
                nutrients.append('\n');
            }
            text.setText(nutrients.toString());
        }

    }
}
