package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.taeyeona.amaizingunicornrecipes.Fragment.ProfileFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.PantryFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.RecipeSearchFragment;

/**
 * Created by thomastse on 11/17/15.
 */
public class MainAdapter extends FragmentStatePagerAdapter {
    RecipeSearchFragment recipeSearchFragment;

    public MainAdapter(FragmentManager fm, RecipeSearchFragment rsFrag) {
        super(fm);
        this.recipeSearchFragment = rsFrag;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            default:
                return new ProfileFragment();
            case 1:
                return new PantryFragment();
            case 2:
                return this.recipeSearchFragment;
        }
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }



}
