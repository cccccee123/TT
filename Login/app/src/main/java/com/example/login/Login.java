package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.login.Fragment.FavoritesFragment;

import com.example.login.Fragment.HomeFragment;

import com.example.login.Fragment.SearchFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_REQUEST_CODE = 1234 ;
    List<AuthUI.IdpConfig>providers;
    Button signout;
    private DrawerLayout drawer;


    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        BottomNavigationView bottomNav =findViewById(R.id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new Fav()).commit();



        signout=findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logout
                AuthUI.getInstance()
                        .signOut(Login.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                signout.setEnabled(false);
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                });

            }
        });


        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)

                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                //Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Show email on Toast
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                //Set Button Signout
                signout.setEnabled(true);
            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
//private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//        new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Fragment selectedFragment = null;
//
//                switch (menuItem.getItemId()) {
//                    case R.id.nav_money:
//                        selectedFragment = new HomeFragment();
//                        break;
//
//                    case R.id.nav_favorites:
//                        selectedFragment = new FavoritesFragment();
//                        break;
//
//                    case R.id.nav_search:
//                        selectedFragment = new SearchFragment();
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        selectedFragment).commit();
//                return true;
//            }
//
//
//        };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.nav_search:
//                Intent intent = new Intent(this, Search.class);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"You click Catergory",Toast.LENGTH_LONG);
//                break;
//            case R.id.nav_home:
//                intent = new Intent(this, Home.class);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"You click Home",Toast.LENGTH_LONG);
//                break;
//
//            case R.id.nav_favorites:
//                intent = new Intent(this, Favorites.class);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"You click Favorites",Toast.LENGTH_LONG);
//                break;

            case R.id.nav_search:
                Intent intent = new Intent(this, ProductActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"You click contact",Toast.LENGTH_LONG);

                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoritesFragment()).commit();
                break;


            case R.id.signout:
                 intent = new Intent(this, Login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"You click contact",Toast.LENGTH_LONG);

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.contact) {
            Intent intent = new Intent(this, Insert.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"You click contact",Toast.LENGTH_LONG).show();
        }
        else if (id== R.id.about) {
            Toast.makeText(getApplicationContext(), "You click about", Toast.LENGTH_LONG).show();

        }

        return  true;
    }

}