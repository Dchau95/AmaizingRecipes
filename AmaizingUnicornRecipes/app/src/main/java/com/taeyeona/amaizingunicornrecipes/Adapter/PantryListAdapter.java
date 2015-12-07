package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import com.taeyeona.amaizingunicornrecipes.R;
import java.util.ArrayList;


/**
 * Created by thomastse on 11/27/15.
 */
public class PantryListAdapter extends ArrayAdapter<String> implements View.OnClickListener{
    ArrayList<String> selected;

    public PantryListAdapter(Context context, String[] list) {
        super(context,R.layout.checklist, list);
        selected = new ArrayList<String>();
    }

    public ArrayList<String> getSelected(){
        return selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View theView = inflater.inflate(R.layout.checklist, parent, false);
        CheckBox check = (CheckBox) theView.findViewById(R.id.checkBox);
        check.setText(getItem(position));
        check.setOnClickListener(this);

        return theView;
    }

    @Override
    public void onClick(View view) {
        CheckBox check = (CheckBox)view;

        if(check.isChecked()){
            selected.add(check.getText().toString());
        }else{
            selected.remove(check.getText().toString());
        }

    }


}
