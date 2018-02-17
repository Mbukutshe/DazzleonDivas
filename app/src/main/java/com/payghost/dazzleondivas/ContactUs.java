package com.payghost.dazzleondivas;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactUs extends AppCompatActivity implements View.OnClickListener  {
    Button btnContact;
    EditText number,date,name,surname,email;
    private ImageView phone,sms;
    private Spinner spinner,spinner2;
    private String service,event_type;

    private CheckBox chkPhone, chkEmail, chkWhatapps;
    private String phone_address,email_address,Whatapps_address;

    private RadioGroup radioGroup;
    private RadioButton radioSexButton;

    private android.app.DatePickerDialog DatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setTitle("Contact Me");

        date = (EditText)findViewById(R.id.message);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        phone = (ImageView)findViewById(R.id.imPhone);
        sms = (ImageView)findViewById(R.id.phone);
        btnContact = (Button)findViewById(R.id.btnContact);

        number = (EditText)findViewById(R.id.number);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.last_name);
        email = (EditText)findViewById(R.id.email);

        spinner  = (Spinner)findViewById(R.id.spinner1);
        spinner2  = (Spinner)findViewById(R.id.spinner_event_type);

        chkPhone = (CheckBox) findViewById(R.id.chkPhone);
        chkEmail = (CheckBox) findViewById(R.id.chkEmail);
        chkWhatapps = (CheckBox) findViewById(R.id.chkWhatapp);


        String[] services = {"What services do you require ? ","Hair","Makeup","Hair & makeup","Hair, makeup & lashes","Interested in purchasing Nuskin products ","Interested in purchasing WoW cosmetic products"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getApplication(), R.layout.spinner_text,services);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(langAdapter);

        String[] types = {"What type of event is it ? ","Wedding","Prom","Party","Bridal","Bridal & retinue"};
        ArrayAdapter<CharSequence> langAdapter2 = new ArrayAdapter<CharSequence>(getApplication(), R.layout.spinner_text,types);
        langAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner2.setAdapter(langAdapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                service = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                event_type = spinner2.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            getSupportActionBar().setTitle("Message");
            setContentView(R.layout.activity_message);

            final EditText SMS_MESSAGE = (EditText)findViewById(R.id.message);
            final Button  Save = (Button)findViewById(R.id.btnMessage);

             Save.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     sendSMS("0817112040",SMS_MESSAGE.getText().toString().trim());
                     setContentView(R.layout.activity_contact_us);
                     getSupportActionBar().setTitle("WE WILL GET BACK TO YOU");
                 }
             });
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","info@dazzleondivas.co.za", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "DAZZLE ON DIVAS INFO");
                intent.putExtra(Intent.EXTRA_TEXT,

                        "Name :\t"+name.getText().toString()+"\n \n" +
                        "Surname :\t"+surname.getText().toString() +"\n \n" +
                        "Email :\t"+email.getText().toString() +"\n \n" +
                        "Phone :\t"+number.getText().toString() +"\n \n" +
                        "Date of Event :\t" + surname.getText().toString() +"\n \n" +
                        "Services Required :\t" + service +"\n \n" +
                        "Event Type  :\t"+ event_type +"\n \n" +
                        "Preferred Method Of Contact :\t"+"\n" + phone_address +"\n" + email_address +"\n" + Whatapps_address +"\n \n" +
                        "Best Time To Contact Me :\t"+ radioSexButton.getText().toString() +"\n \n");

                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone();
            }
        });
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chkPhone:
                if (checked)
                // Put some meat on the sandwich
                    phone_address = "Please call me";
            else
                // Remove the meat
                    phone_address = " ";
                break;
            case R.id.chkEmail:
                if (checked)
                // Cheese me
                email_address = "Please contact me vie email";
            else
                // I'm lactose intolerant
                    email_address = " ";
                break;

            case R.id.chkWhatapp:
                if (checked)
                    // Cheese me
                    Whatapps_address = "Please contact me vie Whatsapp";
                else
                    // I'm lactose intolerant
                    Whatapps_address = " ";
                    break;
            // TODO: Veggie sandwich
        }
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
            startActivity(new Intent(ContactUs.this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void phone()
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel","0318364247",null));
        try
        {
            startActivity(intent);
        }
        catch (Exception e)
        {
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(ContactUs.this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(ContactUs.this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        ContactUs.this.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(ContactUs.this,"SMS sent",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(ContactUs.this, "Generic failure",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(ContactUs.this, "No service",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(ContactUs.this,"Null PDU",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(ContactUs.this,"Radio off",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        ContactUs.this.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(ContactUs.this, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(ContactUs.this, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private void setDateTimeField() {

        date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == date) {
            DatePickerDialog.show();
        }
    }
}
