package com.payghost.dazzleondivas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText Username, Password;
    private ScrollView linearLayout;
    private TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = (TextView)findViewById(R.id.tvRegister);

        linearLayout = (ScrollView)findViewById(R.id.layout_login);
        linearLayout.getBackground().setAlpha(230);

        login = (Button)findViewById(R.id.btn_login);
        Username = (EditText)findViewById(R.id.input_username);
        Password = (EditText)findViewById(R.id.input_password);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                if(username.equalsIgnoreCase("payghost") && password.equalsIgnoreCase("qbzhiptu0016$#") || username.equalsIgnoreCase("Admin@dazzl") && password.equalsIgnoreCase("ebaam$#") ){
                                    startActivity(new Intent(getApplicationContext(),MainTabActivity.class));
                                }else {
                                    startActivity(new Intent(getApplicationContext(),Files.class));
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}

