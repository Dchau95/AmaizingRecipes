package com.taeyeona.amaizingunicornrecipes;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{

    private ImageView image;
    private ImageLoader imgLoader = VolleySingleton.getInstance(this).getImageLoader();
    private Button but;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        final String img = getIntent().getStringExtra("Picture");
        image = (ImageView) findViewById(R.id.imageView2);

        imgLoader.get(img, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                image.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        
/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IngredientsFragment firstFragment = new IngredientsFragment();
        final InstructionsFragment secondFragment = new InstructionsFragment();

        fragmentTransaction.replace(R.id.ingredient, firstFragment);

        but = (Button) findViewById(R.id.button3);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.instructions, secondFragment);
            }
        });

        fragmentTransaction.commit();*/

    }
}