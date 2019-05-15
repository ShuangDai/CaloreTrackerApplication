package com.example.a5046assign2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryListView extends AppCompatActivity {

    ListView listItemView;

    // Define string array.
    String[] listItemsValue = new String[] {"Drink", "Meal", "Meat", "Snack", "Bread", "Cake", "Fruit", "Vegies", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_view);


        listItemView = (ListView)findViewById(R.id.your_list_view_id);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view, R.id.category, listItemsValue);

        listItemView.setAdapter(adapter);

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(CategoryListView.this,FoodListActivity.class);
                intent.putExtra("category",listItemsValue[position]);
                startActivity(intent);
                Toast.makeText(CategoryListView.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

}

