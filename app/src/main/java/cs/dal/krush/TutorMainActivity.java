package cs.dal.krush;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import cs.dal.krush.helpers.BottomNavigationViewHelper;
import cs.dal.krush.tutorFragments.TutorAvailabilityFragment;
import cs.dal.krush.tutorFragments.TutorHomeFragment;
import cs.dal.krush.tutorFragments.TutorProfileFragment;
import cs.dal.krush.tutorFragments.TutorHistoryFragment;

/**
 * TutorMainActivity is the main entry point for all tutor features
 * This activity renders the bottom nav menu and handles the click listeners
 * When a menu item is clicked the corresponding fragment is inserted into the fragment view
 */
public class TutorMainActivity extends FragmentActivity {
    BottomNavigationView bottomNav;
    static int USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_main);

        //Retrieve user id from login activity
        USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

        //Create bundle to send USER_ID to other fragments
        final Bundle bundle = new Bundle();
        bundle.putInt("USER_ID", USER_ID);

        //Set initial fragment to tutor home page
        TutorHomeFragment homeFragment = new TutorHomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.tutor_fragment_container, homeFragment).commit();

        //Custom bottom nav bar with disabled shifting
        bottomNav = (BottomNavigationView) findViewById(R.id.tutor_navigation);
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
                        TutorHomeFragment home = new TutorHomeFragment();
                        home.setArguments(bundle);
                        transaction.replace(R.id.tutor_fragment_container, home);
                        transaction.commit();
                        return true;
                    case R.id.menu_profile:
                        TutorProfileFragment profile = new TutorProfileFragment();
                        profile.setArguments(getIntent().getExtras());
                        transaction.replace(R.id.tutor_fragment_container, profile);
                        profile.setArguments(bundle);
                        transaction.commit();
                        return true;
                    case R.id.menu_sessions:
                        TutorHistoryFragment sessions = new TutorHistoryFragment();
                        sessions.setArguments(getIntent().getExtras());
                        transaction.replace(R.id.tutor_fragment_container, sessions);
                        sessions.setArguments(bundle);
                        transaction.commit();
                        return true;
                    case R.id.menu_availability:
                        TutorAvailabilityFragment calendar = new TutorAvailabilityFragment();
                        transaction.replace(R.id.tutor_fragment_container, calendar);
                        calendar.setArguments(bundle);
                        transaction.commit();
                        return true;
                }
                return false;
            }
        });
    }
}
