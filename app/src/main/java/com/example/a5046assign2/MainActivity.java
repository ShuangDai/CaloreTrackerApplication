package com.example.a5046assign2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText=(EditText) findViewById(R.id.editText) ;
        Button btnSearch = (Button) findViewById(R.id.btnSearchInfo);
        Button btnSearchNutrition = (Button) findViewById(R.id.btnSearchNutrition);



        Button btnSteps = (Button) findViewById(R.id.btnSteps);
        btnSteps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StepActivity.class);
                startActivity(intent);
            }
        });

        Button home = (Button) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                SearchGoogleAsyncTask searchGoogleAsyncTask=new SearchGoogleAsyncTask();
                searchGoogleAsyncTask.execute(keyword);

            }
        });


        btnSearchNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                SearchFatSecreteAsyncTask searchFateSecreteAsyncTask=new SearchFatSecreteAsyncTask();
                searchFateSecreteAsyncTask.execute(keyword);

            }
        });

//        Button findAllCoursesBtn = (Button) findViewById(R.id.btnFindAll);
//        resultTextView = (TextView) findViewById(R.id.resultTextView);
//        findAllCoursesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CoursesAsyncTask getAllCourses = new CoursesAsyncTask();
//                getAllCourses.execute();
//            }
//        });


    }


    private class CoursesAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestCustomer.findAllCourses();
        }
        @Override
        protected void onPostExecute (String courses){
            TextView resultTextView = (TextView) findViewById(R.id.tvResult);
            resultTextView.setText(courses);
        }
    }

    private class SearchFatSecreteAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SearchFatSecretAPI api = new SearchFatSecretAPI();
            String s= api.searchFood(params[0],1);
            return s;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) findViewById(R.id.tvResult);
            tv.setText(SearchFatSecretAPI.getSnippet(result));
        }
    }

    private class SearchGoogleAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) findViewById(R.id.tvResult);
            tv.setText(SearchGoogleAPI.getSnippet(result));
            String url = SearchGoogleAPI.getImageURL(result);
            new DownloadImageFromInternet((ImageView) findViewById(R.id.imageId))
                    .execute(url);
        }
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }






}