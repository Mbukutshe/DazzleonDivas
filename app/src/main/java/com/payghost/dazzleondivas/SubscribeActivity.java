package com.payghost.dazzleondivas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubscribeActivity extends AppCompatActivity {

    private EditText name,surname,dob,suburb;
    private Button subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        name = (EditText)findViewById(R.id.sub_name);
        surname = (EditText)findViewById(R.id.sub_surname);
        dob = (EditText)findViewById(R.id.sub_date_of_birth);
        suburb = (EditText)findViewById(R.id.sub_suburb);
        subscribe = (Button)findViewById(R.id.btn_subscribe);

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty() && !dob.getText().toString().isEmpty() && !suburb.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Thanks",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
