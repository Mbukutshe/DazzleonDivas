package com.payghost.dazzleondivas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoreDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        final TextView title = (TextView)findViewById(R.id.more_title);
        final TextView description = (TextView)findViewById(R.id.more_description);
        final ImageView image = (ImageView)findViewById(R.id.more_image);
        final TextView back = (TextView)findViewById(R.id.more_back);

        Bundle bundle = getIntent().getExtras();

        String des = bundle.getString("description");
        String img = bundle.getString("image");
        String tit = bundle.getString("title");

        title.setText(tit);
        description.setText(des);

        Picasso.with(getApplicationContext()).load(img).into(image);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
