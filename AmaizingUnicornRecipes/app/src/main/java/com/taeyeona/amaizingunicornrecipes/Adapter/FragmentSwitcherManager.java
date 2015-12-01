package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.graphics.Typeface;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by thomastse on 11/20/15.
 */
public class FragmentSwitcherManager implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private ArrayList<Button> buttons;
    private ArrayList<View> bars;
    private ViewPager theViewPager;
    private ActivePage active;

    public FragmentSwitcherManager(ViewPager viewPager){
        buttons = new ArrayList<Button>();
        bars = new ArrayList<View>();
        theViewPager = viewPager;
        theViewPager.addOnPageChangeListener(this);
    }

    public void add(Button newButton, View newBar){
        newButton.setOnClickListener(this);
        buttons.add(newButton);
        bars.add(newBar);
        if(active == null){
            active = new ActivePage(newButton, newBar);
        }
    }

    @Override
    public void onClick(View view) {
        int index = buttons.indexOf((Button)view);
        active.getButton().setTypeface(null, Typeface.NORMAL);
        active.getView().setVisibility(View.INVISIBLE);

        active.setButton(buttons.get(index));
        active.setView(bars.get(index));
        theViewPager.setCurrentItem(index);
    }

    public void setViewPager(ViewPager pager){
        theViewPager = pager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        active.getButton().setTypeface(null, Typeface.NORMAL);
        active.getView().setVisibility(View.INVISIBLE);

        active.setButton(buttons.get(position));
        active.setView(bars.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setPage(int position){
        onPageSelected(position);
        theViewPager.setCurrentItem(position);
    }

    class ActivePage {

        private Button button;
        private View view;

        public ActivePage(Button button, View view){
            setButton(button);
            setView(view);
        }

        public Button getButton() {
            return button;
        }

        public View getView() {
            return view;
        }

        public void setButton(Button button) {
            this.button = button;
            button.setTypeface(null, Typeface.BOLD);
        }

        public void setView(View view) {
            this.view = view;
            view.setVisibility(View.VISIBLE);
        }

    }
}
