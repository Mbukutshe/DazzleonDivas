package com.payghost.dazzleondivas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.payghost.dazzleondivas.Post.PostAdapter;
import com.payghost.dazzleondivas.Post.PostItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private String JSON_STRING;
    String title;
    String image;
    String description;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    PostAdapter postviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView)findViewById(R.id.listMessage);
        linearlayout = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();
    }

    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<PostItems> arrList = new ArrayList<PostItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                title = jo.getString(Config.TAG_POST_TITLE);
                image = jo.getString(Config.TAG_IMAGE);
                description = jo.getString(Config.TAG_POST_DESRCRIPTION);


                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_POST_TITLE,title);
                Tenants.put(Config.TAG_POST_DESRCRIPTION,description);
                Tenants.put(Config.TAG_IMAGE,image);

                list.add(Tenants);

                arrList.add(new PostItems(image,title,description));
            }
            postviewAdapter = new PostAdapter(getApplicationContext(),arrList);
            recyclerView.setAdapter(postviewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Main2Activity.this, list, R.layout.list_row_messages,
                new String[]{Config.TAG_MESSAGE_DISPLAY,Config.TAG_MESSAGE_TIME},
                new int[]{R.id.displayMessage,R.id.messageTime});
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Main2Activity.this,"Fetching Post"," Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMessages();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_POST);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
