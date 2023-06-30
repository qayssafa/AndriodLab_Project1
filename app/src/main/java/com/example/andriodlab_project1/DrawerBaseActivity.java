package com.example.andriodlab_project1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andriodlab_project1.admin.AdminMainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
       drawerLayout =(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
         FrameLayout container = drawerLayout.findViewById(R.id.activitycontainer);
         container.addView(view);
            super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

      return false;
    }

    protected void allocateActivityTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    // public static TravelDestination travelDestination;
    //@Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        startActivity(new Intent(DrawerBaseActivity.this, AdminMainActivity.class));
        finish();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
               /* switch (id){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                        break;
                    case R.id.all:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AllFragment()).commit();
                        break;
                    case R.id.favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FavFragment()).commit();
                        break;
                    case R.id.sorted:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SortedFragment()).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(DrawerBaseActivity.this, MainActivity.class));
                        break;
                    default:
                        return true;
                }
                return false;
            }
        });
    }*/
}