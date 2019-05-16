package com.example.a5046assign2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    List<HashMap<String, String>> foodListArray;
    SimpleAdapter myListAdapter;
    ListView foodList;
    HashMap<String,String> map = new HashMap<String,String>();
    String[] colHEAD = new String[] {"foodName","calorieAmount","fat"};
    int[] dataCell = new int[] {R.id.FoodName,R.id.CalorieAmount,R.id.Fat};
    Button addButton;
    EditText addEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        Log.i("myLog",category);
        FoodCategoryAsyncTask getAllFood = new FoodCategoryAsyncTask();
        getAllFood.execute(category);
        addButton = this.findViewById(R.id.addButton);
        addEditText = this.findViewById(R.id.addEditText);

        foodList = this.findViewById(R.id.listView); foodListArray = new
                ArrayList<HashMap<String, String>>();

        myListAdapter = new
                SimpleAdapter(this,foodListArray,R.layout.list_view2,colHEAD,dataCell);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String newfood = addEditText.getText().toString();
                String[] foodsArray = newfood.split(",");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("foodName", foodsArray[0]);
                map.put("calorieAmount", foodsArray[1]);
                map.put("fat", foodsArray[2]);
                addMap(map);
            }
        });

    }
    protected void addMap(HashMap map){
        foodListArray.add(map);
        myListAdapter = new
                SimpleAdapter(this,foodListArray,R.layout.list_view,colHEAD,dataCell);
        foodList.setAdapter(myListAdapter);
    }

    private class FoodCategoryAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground (String...params){
            return RestFood.findFoodByCategory(params[0]);
        }
        @Override
        protected void onPostExecute (String food){


            foodList.setAdapter(myListAdapter);
            try {
                JSONArray jsonArray= new JSONArray(food);
                int length =jsonArray.length();
                for (int i=0;i<length;i++){
                    String foodName= jsonArray.getJSONObject(i).getString("foodName");
                    String calorieAmount= jsonArray.getJSONObject(i).getString("calorieAmount");
                    String fat = jsonArray.getJSONObject(i).getString("fat");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("foodName", "foodName: "+foodName);
                    map.put("calorieAmount", "calorieAmount:"+calorieAmount);
                    map.put("fat","fat: "+fat);
                    foodListArray.add(map);
                    Log.i("myTag",foodName);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
