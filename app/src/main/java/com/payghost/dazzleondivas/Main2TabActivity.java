package com.payghost.dazzleondivas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class Main2TabActivity extends TabActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Employees Dashboard");

        TabHost tabHost = getTabHost();

        TabHost.TabSpec Document = tabHost.newTabSpec("Documents");
        Document.setIndicator("Documents");
        Intent DocumentIntent = new Intent(this,Files.class);
        Document.setContent(DocumentIntent);


        TabHost.TabSpec chats = tabHost.newTabSpec("Contacts");
        chats.setIndicator("Contacts");
        Intent ChatsIntent = new Intent(this,Users.class);
        chats.setContent(ChatsIntent);

        tabHost.addTab(Document);
        tabHost.addTab(chats);
    }
}
