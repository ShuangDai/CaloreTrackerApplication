package com.example.a5046assign2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FoodListActivity extends AppCompatActivity {

    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        FoodCategoryAsyncTask getAllCourses = new FoodCategoryAsyncTask();
        getAllCourses.execute("fruit");

    }

    private class FoodCategoryAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground (String...params){
            return RestFood.findFoodByUserCategory(params[0]);
        }
        @Override
        protected void onPostExecute (String food){
            resultTextView = (TextView)findViewById(R.id.tvResult);
            resultTextView.setText(food);
        }
    }

}
