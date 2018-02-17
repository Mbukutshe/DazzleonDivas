package com.payghost.dazzleondivas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class AddPost extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUpload;
    EditText EdTitle,EdDes;
    private ImageView browse;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="http://mydm.co.za/Diva/postupload.php";
    private String KEY_IMAGE = "image";
    private String KEY_TITLE = "title";
    private String KEY_DESCRIPTION = "description";
    private RequestQueue requestQueue;
    String title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        buttonUpload = (Button)findViewById(R.id.btnPost);
        browse = (ImageView)findViewById(R.id.browse);

        EdTitle = (EditText)findViewById(R.id.input_title);
        EdDes = (EditText)findViewById(R.id.input_description);

        browse.setOnClickListener(this);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){

        if(! EdTitle.getText().toString().isEmpty() && ! EdTitle.getText().toString().isEmpty() ) {
            try {

                //Showing the progress dialog
                final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                //Disimissing the progress dialog
                                loading.dismiss();
                                //Showing toast message of the response
                                Toast.makeText(AddPost.this, s, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                //Dismissing the progress dialog
                                loading.dismiss();

                                //Showing toast
                                Toast.makeText(AddPost.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Converting Bitmap to String
                        String image = getStringImage(bitmap);

                        //Getting Image Name
                        //Creating parameters
                        Map<String, String> params = new Hashtable<String, String>();
                        // getting text from Edittext
                        title = EdTitle.getText().toString();
                        description = EdDes.getText().toString();
                        //Adding parameters
                        params.put(KEY_IMAGE, image);
                        params.put(KEY_TITLE, title);
                        params.put(KEY_DESCRIPTION, description);
                        //returning parameters
                        return params;
                    }
                };

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //Adding request to the queue
                requestQueue.add(stringRequest);

            } catch (Exception e) {
                Toast.makeText(AddPost.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(AddPost.this, "All fields are required.", Toast.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                browse.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == browse){
            showFileChooser();
        }

        if(v == buttonUpload){

        }
    }
}
