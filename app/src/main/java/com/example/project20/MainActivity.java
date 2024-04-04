package com.example.project20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project20.ui.dashboard.DashboardFragment;
import com.example.project20.ui.home.HomeFragment;
import com.example.project20.ui.notes.NotesFragment;
import com.example.project20.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
       //Button button_home,button_pyqs,button_notes,button_notifications;
       @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_notes, R.id.navigation_pyqs, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

        }
        /*
private void init(){
    button_home = findViewById(R.id.navigation_home);
    button_notes = findViewById(R.id.navigation_notes);
    button_pyqs = findViewById(R.id.navigation_pyqs);
    button_notifications = findViewById(R.id.navigation_notifications);
    loadFrag(new HomeFragment(),0);
}
private void click()
{

    button_home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                    loadFrag(new HomeFragment(),1);
        }
    });
    button_notes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                   loadFrag(new NotesFragment(),1);
        }
    });
    button_pyqs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                   loadFrag(new DashboardFragment(),1);
        }
    });
    button_notifications.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                   loadFrag(new NotificationsFragment(),1);
        }
    });
}
public void loadFrag(Fragment fragment,int flag)
{
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction ft= fragmentManager.beginTransaction();
    if(flag==0) {
        ft.add(R.id.nav_host_fragment_activity_main, new HomeFragment());
    }
    else {
        ft.replace(R.id.nav_host_fragment_activity_main, new HomeFragment()).addToBackStack(null);
    }
    ft.commit();

}
//When you replace a fragment container with another fragment, the original fragment will be destroyed.

*/
// Name can be null.commit();



    }

