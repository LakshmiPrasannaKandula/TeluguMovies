package com.example.dell.moviedb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s="https://api.themoviedb.org/3/discover/movie?api_key=09aedacfebd0c729aff684f082080554&with_original_language=te";
    ArrayList<Model>  movieModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress);
        movieModel=new ArrayList<>();
        recyclerView=findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new Adapter(this,movieModel));
        new MovieTask().execute();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular:
                break;
            case  R.id.toprated:
                break;

        }
        return super.onOptionsItemSelected(item);
    }*/

    public  class  MovieTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url= new URL(s);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner= new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return  scanner.next();
                }
                else {
                    return  null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(s);
            try {
                JSONObject jsonObject= new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObj=jsonArray.getJSONObject(i);
                    String title=jsonObj.getString("title");
                    String poster_path=jsonObj.getString("poster_path");
                    String overview=jsonObj.getString("overview");
                    String release_date=jsonObj.getString("release_date");
                    movieModel.add(new Model(title,poster_path,overview,release_date));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
