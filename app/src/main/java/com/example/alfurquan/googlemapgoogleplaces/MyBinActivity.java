package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyBinActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    Intent intent;
    Button btnCreate,btnShow,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bin);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dustbins");
        btnCreate = (Button)findViewById(R.id.create_bin);
        btnShow = (Button)findViewById(R.id.show_bin);
        btnDelete = (Button)findViewById(R.id.delete_bin);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyBinActivity.this,BinsActivity.class);
                startActivity(intent);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyBinActivity.this,CreateBinsActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyBinActivity.this,DeleteBinsActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        intent = new Intent(MyBinActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bins:
                        break;
                    case R.id.location:
                        intent = new Intent(MyBinActivity.this, MapActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
