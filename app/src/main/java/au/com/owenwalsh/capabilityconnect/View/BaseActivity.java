package au.com.owenwalsh.capabilityconnect.View;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.R;


public class BaseActivity extends AppCompatActivity {
    private NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected int frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        FrameLayout frameLayout = (FrameLayout)  findViewById(R.id.content_frame);
        // Initializing Toolbar and setting it as the actionbar



        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.tutorial:
                        Toast.makeText(getApplicationContext(),"Tutorials Selected",Toast.LENGTH_SHORT).show();
                        Intent tutorialIntent = new Intent(getApplicationContext(), TutorialListActivity.class);
                        startActivity(tutorialIntent);

                        return true;

                    case R.id.students:
                        Toast.makeText(getApplicationContext(),"Students Selected",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StudentListActivity.class);
                        startActivity(intent);

                        return true;
                    case R.id.weeks:
                        Toast.makeText(getApplicationContext(),"Weeks Selected",Toast.LENGTH_SHORT).show();
                        Intent weeksIntent = new Intent(getApplicationContext(), WeeksListActivity.class);
                        startActivity(weeksIntent);
                        return true;
                    case R.id.assessments:
                        Toast.makeText(getApplicationContext(),"Assessments Selected",Toast.LENGTH_SHORT).show();
                        Intent assessmentsIntent = new Intent(getApplicationContext(), AssessmentListActivity.class);
                        startActivity(assessmentsIntent);
                        return true;
                    case R.id.competency:
                        Toast.makeText(getApplicationContext(),"Competency Selected",Toast.LENGTH_SHORT).show();
                        Intent competencyIntent = new Intent(getApplicationContext(), CompetencyListActivity.class);
                        startActivity(competencyIntent);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };


        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }


}
