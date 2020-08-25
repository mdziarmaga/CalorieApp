package com.example.calorieapp.SearchPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.calorieapp.MainActivity;
import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ListViewAdopter extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    List<Food_> food_list;
    ArrayList<Food_> food_arrayList;


    public ListViewAdopter(Context context, List<Food_> food_list) {
        mContext = context;
        this.food_list = food_list;
        inflater = LayoutInflater.from(mContext);
        this.food_list = new ArrayList<Food_>();
        this.food_list.addAll(food_list);

    }

    public class ViewHolder{
        TextView mLabel, mDesc;
        ImageView mIcon;
    }

    @Override
    public int getCount() {
        return food_list.size();
    }

    @Override
    public Object getItem(int i) {
        return food_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row, null);

            holder.mLabel = view.findViewById(R.id.mainLabel);
            holder.mDesc = view.findViewById(R.id.mainDesc);
            holder.mIcon = view.findViewById(R.id.mainPhoto);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //ustawiamy nazwy i makro produktów
        holder.mLabel.setText(food_list.get(postition).getLabel());
        holder.mDesc.setText(food_list.get(postition).getNutrients().toString());
        //ustawiamy zdjęcie w polu zdjęcia

        Picasso.get().load(food_list.get(postition).getImage()).into(holder.mIcon);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_ food_ = food_list.get(postition);
              new SearchActivity().openAddDialog(food_,view);

            }});
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        food_list.clear();
        if (charText.length()==0){
            food_list.addAll(food_arrayList);

        }
        else {}
        notifyDataSetChanged();
    }}


