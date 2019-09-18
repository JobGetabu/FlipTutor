package cs.dal.krush;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import cs.dal.krush.helpers.BottomNavigationViewHelper;
import cs.dal.krush.studentFragments.StudentHomeFragment;
import cs.dal.krush.studentFragments.StudentProfileFragment;
import cs.dal.krush.studentFragments.StudentBookingFragment;
import cs.dal.krush.studentFragments.StudentHistoryFragment;

/**
 * StudentMainActivity is the main entry point for all student features
 * This activity renders the bottom nav menu and handles the click listeners
 * When a menu item is clicked the corresponding fragment is inserted into the fragment view
 */
public class StudentMainActivity extends FragmentActivity
{
    BottomNavigationView bottomNav;
    static int USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        //Retrieve user id from login activity
        USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

        //Create bundle to send userId to other fragments
        final Bundle bundle = new Bundle();
        bundle.putInt("USER_ID", USER_ID);

        //Set initial fragment to student home page:
        StudentHomeFragment homeFragment = new StudentHomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.student_fragment_container, homeFragment).commit();

        //Custom bottom nav bar with disabled shifting:
        bottomNav = (BottomNavigationView) findViewById(R.id.student_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);

        //Nav bar listener
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menu_item = item.getItemId();
                FragmentManager manager = getSupportFragmentManager();
                manager.popBackStack();
                FragmentTransaction transaction = manager.beginTransaction();
                switch(menu_item) {
                    case R.id.menu_home:
                        StudentHomeFragment home = new StudentHomeFragment();
                        home.setArguments(bundle);
                        transaction.replace(R.id.student_fragment_container, home);
                        transaction.commit();
                        return true;
                    case R.id.menu_booking:
                        StudentBookingFragment quickbook = new StudentBookingFragment();
                        quickbook.setArguments(bundle);
                        transaction.replace(R.id.student_fragment_container, quickbook);
                        transaction.commit();
                        return true;
                    case R.id.menu_profile:
                        StudentProfileFragment profile = new StudentProfileFragment();
                        profile.setArguments(bundle);
                        transaction.replace(R.id.student_fragment_container, profile);
                        transaction.commit();
                        return true;
                    case R.id.menu_sessions:
                        StudentHistoryFragment sessions = new StudentHistoryFragment();
                        sessions.setArguments(bundle);
                        transaction.replace(R.id.student_fragment_container, sessions);
                        transaction.commit();
                        return true;
                }
                return false;
            }
        });
    }

}

