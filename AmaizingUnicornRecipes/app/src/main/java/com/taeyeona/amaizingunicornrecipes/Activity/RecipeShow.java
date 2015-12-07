package com.taeyeona.amaizingunicornrecipes.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.taeyeona.amaizingunicornrecipes.Adapter.CustomPagerAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.ToggleDrawerAdapter;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;

import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.VolleySingleton;


/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{

    //DrawerLayout , prefListView , and prefListName manages preference drawer
    private DrawerLayout drawerLayout;
    private ListView prefListView;
    private String[] prefListName;


    // private ImageView image;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private FragmentSwitcherManager fragSwitcher;
    private Bitmap theImage;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        ImageLoader imgLoader = VolleySingleton.getInstance(this).getImageLoader();

        //Create drawer adapter to toggle search preferences with right side drawer
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_v2);
        prefListName = ProfileHash.getSearchSettings();
        prefListView = (ListView)findViewById((R.id.pref_drawer_right));
        prefListView.setAdapter(new ToggleDrawerAdapter(this, prefListName));


        final String img = getIntent().getStringExtra("Picture");
        Bundle bundle = new Bundle();
        bundle.putString("Title", getIntent().getStringExtra("Title"));
        bundle.putString("SourceName", getIntent().getStringExtra("SourceName"));
        bundle.putString("SourceUrl", getIntent().getStringExtra("SourceUrl"));
        bundle.putString("Picture", getIntent().getStringExtra("Picture"));

        if(getIntent().getStringExtra("API").equals("Food2Fork")){
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putString("API", "Food2Fork");
        }else{
            bundle.putString("API", "Edamam");
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putStringArray("Ingredients", getIntent().getStringArrayExtra("Ingredients"));
            bundle.putStringArray("Nutrients", getIntent().getStringArrayExtra("Nutrients"));
            bundle.putIntArray("Totals", getIntent().getIntArrayExtra("Totals"));
        }

        imgLoader.get(img, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                theImage = imageContainer.getBitmap();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        bundle.putParcelable("BMP", theImage);
        TextView title = (TextView) findViewById(R.id.main_title_text);
        title.setText(getIntent().getStringExtra("Title"));
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), bundle);
        mViewPager = (ViewPager) findViewById(R.id.main_pages);
        mViewPager.setAdapter(mCustomPagerAdapter);
        fragSwitcher = new FragmentSwitcherManager(mViewPager, 2);

        Button button;
        View view;

        button = (Button) findViewById(R.id.main_button_1);
        button.setText("Ingredients");
        view = findViewById(R.id.main_bar_1);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_2);
        button.setText("Instructions");
        view = findViewById(R.id.main_bar_2);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_3);
        button.setText("Nutrition Info");
        view = findViewById(R.id.main_bar_3);
        fragSwitcher.add(button, view);

        TextView txtView = (TextView) findViewById(R.id.main_settings_text);
        txtView.setText("Go Back");

        ImageButton imgButton = (ImageButton) findViewById(R.id.main_settings_button);
        imgButton.setBackgroundResource(R.drawable.pizza);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        
    }

}