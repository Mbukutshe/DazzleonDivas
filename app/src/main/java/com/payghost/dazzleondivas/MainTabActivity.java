package com.payghost.dazzleondivas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Dashboard");

        TabHost tabHost = getTabHost();

        TabHost.TabSpec Gallery = tabHost.newTabSpec("Gallery");
        Gallery.setIndicator("Gallery");
        Intent GroupIntent = new Intent(this,ImageUpload.class);
        Gallery.setContent(GroupIntent);

        TabHost.TabSpec Event = tabHost.newTabSpec("Message");
        Event.setIndicator("Message");
        Intent ShowIntent = new Intent(this,Message.class);
        Event.setContent(ShowIntent);

        TabHost.TabSpec specials = tabHost.newTabSpec("Specials");
        specials.setIndicator("Specials");
        Intent AccountIntent = new Intent(this,AddPost.class);
        specials.setContent(AccountIntent);

        TabHost.TabSpec chats = tabHost.newTabSpec("Contacts");
        chats.setIndicator("Contacts");
        Intent ChatsIntent = new Intent(this,Users.class);
        chats.setContent(ChatsIntent);

        TabHost.TabSpec Accounts = tabHost.newTabSpec("Accounts");
        Accounts.setIndicator("Accounts");
        Intent AccountsIntent = new Intent(this,Register.class);
        chats.setContent(AccountsIntent);

        tabHost.addTab(specials);
        tabHost.addTab(Gallery);
        tabHost.addTab(Event);
        tabHost.addTab(chats);
        tabHost.addTab(Accounts);
    }
}
