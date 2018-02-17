package com.payghost.dazzleondivas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText username,name,surname,DateEtxt,password,confiPass;
    Button btnRegister;

    private String user,pass;
    private Spinner spinner;
    private String role;

    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);

        Firebase.setAndroidContext(this);

        spinner  = (Spinner)findViewById(R.id.sp_role);

        String[] foods = {"Employee","Admin",""};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getApplication(), R.layout.spinner_text,foods );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(langAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                role = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        username =(EditText) findViewById(R.id.regusername);
        name = (EditText) findViewById(R.id.regName);
        surname = (EditText) findViewById(R.id.regsurname);
        DateEtxt = (EditText) findViewById(R.id.regDOB);
        password = (EditText) findViewById(R.id.regpassword);
        confiPass = (EditText) findViewById(R.id.regConfpassword);

        DateEtxt.setInputType(InputType.TYPE_NULL);
        DateEtxt.requestFocus();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        btnRegister = (Button) findViewById(R.id.btnreg);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqlReg();
                FirebaseReg();
            }
        });


    }

    private void setDateTimeField() {

        DateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == DateEtxt) {
            DatePickerDialog.show();
        }
    }

    public void SqlReg(){

        String nam = name.getText().toString();
        String Sname = surname.getText().toString();
        String date_of_birth = DateEtxt.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest2 registerRequest = new RegisterRequest2(nam,Sname,date_of_birth,responseListener);
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(registerRequest);
    }

    public void FirebaseReg(){

        user = username.getText().toString();
        pass = password.getText().toString();

        if(user.equals("")){
            username.setError("can't be blank");
        }
        else if(pass.equals("")){
            password.setError("can't be blank");
        }else if (!pass.equalsIgnoreCase(confiPass.getText().toString())){
            password.setError("password don't match");
        }
        /*else if(!user.matches("[A-Za-z0-9]+")){
            username.setError("only alphabet or number allowed");
        }*/
        else if(user.length()<5){
            username.setError("at least 5 characters long");
        }
        else if(pass.length()<5){
            password.setError("at least 5 characters long");
        }
        else {
            final ProgressDialog pd = new ProgressDialog(Register.this);
            pd.setMessage("Loading...");
            pd.show();

            String url = "https://mystory-e07e5.firebaseio.com/users.json";


            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    Firebase reference = new Firebase("https://mystory-e07e5.firebaseio.com/users");

                    if(s.equals("null")) {

                        reference.child(user).child("password").setValue(pass);
                        reference.child(user).child("fullname").setValue(name.getText().toString());
                        reference.child(user).child("lastname").setValue(surname.getText().toString());
                        reference.child(user).child("dob").setValue(DateEtxt.getText().toString());
                        reference.child(user).child("role").setValue(role);
                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(user)) {

                                reference.child(user).child("password").setValue(pass);
                                reference.child(user).child("fullname").setValue(name.getText().toString());
                                reference.child(user).child("lastname").setValue(surname.getText().toString());
                                reference.child(user).child("dob").setValue(DateEtxt.getText().toString());
                                reference.child(user).child("role").setValue(role);
                                Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(Register.this, "username already exists", Toast.LENGTH_LONG).show();
                                username.setText("");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }

            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError );
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(Register.this);
            rQueue.add(request);
        }

    }
}
