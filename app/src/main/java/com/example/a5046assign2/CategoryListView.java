package com.example.a5046assign2;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryListView extends Fragment {

    ListView listItemView;
    View vDisplayCategoryList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        String[] listItemsValue = new String[] {"Drink", "Meal", "Meat", "Snack", "Bread", "Cake", "Fruit", "Vegies", "Other"};
        vDisplayCategoryList = inflater.inflate(R.layout.activity_category_list_view, container,
                false);
        listItemView = (ListView)vDisplayCategoryList.findViewById(R.id.your_list_view_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_view, R.id.category, listItemsValue);
        listItemView.setAdapter(adapter);
        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(),FoodListActivity.class);
                intent.putExtra("category",listItemsValue[position]);
                startActivity(intent);
                Toast.makeText(getActivity(), listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });
        return vDisplayCategoryList;
    }



}

