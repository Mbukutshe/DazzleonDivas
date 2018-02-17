package com.payghost.dazzleondivas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.payghost.dazzleondivas.Gallery.GalleryAdapter;
import com.payghost.dazzleondivas.Gallery.GalleryItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GalleryImages extends AppCompatActivity {
    private String JSON_STRING;
    private String image;

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_images);
        getSupportActionBar().setTitle("Gallery");

        recyclerView = (RecyclerView)findViewById(R.id.listGallerys);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/300);

        if(columns == 1){
            columns = 2;
        }else {
            columns = Math.round(dpWidth/300);
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),columns);
        recyclerView.setLayoutManager(layoutManager);

        getJSON();
    }

    private void showGallery(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<GalleryItems> arrList = new ArrayList<GalleryItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                image = jo.getString(Config.TAG_IMAGE);

                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_IMAGE,image);

                list.add(Tenants);

                arrList.add(new GalleryItems(image));
            }
            galleryAdapter = new GalleryAdapter(getApplicationContext(),arrList);
            recyclerView.setAdapter(galleryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GalleryImages.this,"Loading Gallery"," Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showGallery();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_GALLERY);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            startActivity(new Intent(GalleryImages.this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
