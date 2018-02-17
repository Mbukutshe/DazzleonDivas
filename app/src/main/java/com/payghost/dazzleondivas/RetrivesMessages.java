package com.payghost.dazzleondivas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RetrivesMessages extends AppCompatActivity {

    private String JSON_STRING;
    String message;
    String time;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    noticeViewAdapter noticeViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrives_messages);

        recyclerView = (RecyclerView)findViewById(R.id.listMessage);
        linearlayout = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();
    }

    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<noticeItems> arrList = new ArrayList<noticeItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                message = jo.getString(Config.TAG_MESSAGE_DISPLAY);
                time = jo.getString(Config.TAG_MESSAGE_TIME);


                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_MESSAGE_DISPLAY,message);
                Tenants.put(Config.TAG_MESSAGE_TIME,time);

                list.add(Tenants);

                arrList.add(new noticeItems(message,time));
            }
            noticeViewAdapter = new noticeViewAdapter(getApplicationContext(),arrList);
            recyclerView.setAdapter(noticeViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                RetrivesMessages.this, list, R.layout.list_row_messages,
                new String[]{Config.TAG_MESSAGE_DISPLAY,Config.TAG_MESSAGE_TIME},
                new int[]{R.id.displayMessage,R.id.messageTime});
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RetrivesMessages.this,"Fetching Messages"," Please Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_MESSAGES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
