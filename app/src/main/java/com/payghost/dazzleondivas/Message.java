package com.payghost.dazzleondivas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity {

    RequestQueue requestQueue;
    String insertUrlMessage = "http://mydm.co.za/TPA/InsertMessage.php";

    private Button sendMessage;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ScrollView layout = (ScrollView) findViewById(R.id.scrollViewLayout);
        layout.getBackground().setAlpha(230);


        message = (EditText)findViewById(R.id.message);
        sendMessage = (Button)findViewById(R.id.btnMessage);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!message.getText().toString().isEmpty()){
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrlMessage, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(response.toString());
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("message", message.getText().toString());
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(Message.this, "Broadcast message sent...", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Message.this, "Message is Required...", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
