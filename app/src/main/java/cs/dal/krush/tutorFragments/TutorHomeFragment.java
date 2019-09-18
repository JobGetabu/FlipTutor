package cs.dal.krush.tutorFragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import cs.dal.krush.R;
import cs.dal.krush.TutorCursorAdapters.SessionCursorAdapter;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Tutor Home fragment. This fragment belongs to the TutorMainActivity class
 * and is accessed through the tutor's bottom navigation bar.
 *
 *  Source:
 * [5] List View. (n.d.). Retrieved March 12, 2017,
 * from https://developer.android.com/guide/topics/ui/layout/listview.html
 */
public class TutorHomeFragment extends Fragment {

    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_home, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //get Context:
        Context C = getContext();

        //init DB connection:
        DBHelper mydb = new DBHelper(C);

        Cursor tutor = mydb.tutor.getData(USER_ID);
        tutor.moveToFirst();

        //fetch UI elements:
        ListView upcomingSessionsListView = (ListView)view.findViewById(R.id.upcomingSessionsListView);
        RatingBar tutorRating = (RatingBar) view.findViewById(R.id.rating);
        TextView ratingCount = (TextView) view.findViewById(R.id.ratingCount);
        TextView pageTitle = (TextView)view.findViewById(R.id.homeTitleLabel);
        TextView sessionsLabel = (TextView)view.findViewById(R.id.upcomingSessionsLabel);
        TextView ratingTitle = (TextView) view.findViewById(R.id.tutorRating);
        FloatingActionButton tutorHelpButton = (FloatingActionButton)view.findViewById(R.id.tutorHelpButton);

        String currentTutorRating = tutor.getString(tutor.getColumnIndex("rating"));
        if(currentTutorRating != null)
            tutorRating.setRating(Float.parseFloat(currentTutorRating));
        int totalRatings = mydb.tutor.getTutorRatingCount(USER_ID);
        ratingCount.setText("(" + totalRatings + ")");

        //fetch custom app font:
        final Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);
        sessionsLabel.setTypeface(typeFace);
        ratingTitle.setTypeface(typeFace);
        ratingCount.setTypeface(typeFace);


        //get all tutoring sessions by the tutor:
        final Cursor cursorSessionsResponse = mydb.tutoringSession.getDataByTutorIdForCursorAdapter(USER_ID);

        //set sessions listview adapter:
        SessionCursorAdapter sessionsAdapter = new SessionCursorAdapter(C, cursorSessionsResponse);
        upcomingSessionsListView.setAdapter(sessionsAdapter);

        final String introBody = "\n" +
                "Tired of using a billboard to get clients? Or want to make some extra income? " +
                "We got you covered! Krush streamlines the entire process from account creation, " +
                "getting your first client and collect your payment! \n" +
                "\n";
        final String homeBody = "\n" +
                "The home page is your dashboard: you can browse your upcoming sessions and keep " +
                "track of your rating. You can click on an upcoming session to view the details " +
                "of that session such the student information, data, time and location.\n" +
                "\n";
        final String availabilityBody = "\n" +
                "The availability section is where you set up your available times and set your " +
                "meeting location. Further, you can browse your current availabilities and remove " +
                "them if need be. To create a new availability simply select a date, start time, " +
                "end time and submit. Done! Easy right? Setting your location is just as easy, " +
                "click on ‘Set Location’ and at the bottom of the page enter the address where " +
                "you want to hold your sessions.\n" +
                "\n";
        final String sessionBody = "\nIn the sessions section, you can browse all your tutoring " +
                "sessions (past and present). You can click on a specific session to submit a " +
                "review for a tutor or listen to the audio recording for that session!\n";
        final String profileBody = "\n" +
                "In the profile section, you can view and edit your profile details. We strongly " +
                "recommend using your camera associate your face with your profile! Setting your " +
                "profile picture makes it easy to meet a new student in a public location. You can" +
                "also set your rate per half-hour. \n";


        //display tutor help dialog
        tutorHelpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.tutor_help);
                // Set dialog title
                dialog.setTitle("Custom Dialog");
                dialog.show();

                //fetch UI components
                TextView tutorHelpHeader = (TextView) dialog.findViewById(R.id.tutorHelpHeader);
                TextView tutorHelpIntro = (TextView) dialog.findViewById(R.id.tutorHelpIntro);
                TextView homeTutorHelpLabel = (TextView) dialog.findViewById(R.id.homeTutorHelpLabel);
                TextView homeTutorHelpText = (TextView) dialog.findViewById(R.id.homeTutorHelpText);
                TextView bookingTutorHelpLabel = (TextView) dialog.findViewById(R.id.bookingTutorHelpLabel);
                TextView bookingTutorHelpText = (TextView) dialog.findViewById(R.id.bookingTutorHelpText);
                TextView seesionsTutorHelpLabel = (TextView) dialog.findViewById(R.id.seesionsTutorHelpLabel);
                TextView sessionsTutorHelpText = (TextView) dialog.findViewById(R.id.sessionsTutorHelpText);
                TextView profileTutorHelpLabel = (TextView) dialog.findViewById(R.id.profileTutorHelpLabel);
                TextView profileTutorHelpText = (TextView) dialog.findViewById(R.id.profileTutorHelpText);

                //set logo font style
                tutorHelpHeader.setTypeface(typeFace);
                homeTutorHelpLabel.setTypeface(typeFace);
                bookingTutorHelpLabel.setTypeface(typeFace);
                seesionsTutorHelpLabel.setTypeface(typeFace);
                profileTutorHelpLabel.setTypeface(typeFace);

                //set text in dialogue
                tutorHelpIntro.setText(introBody);
                homeTutorHelpText.setText(homeBody);
                bookingTutorHelpText.setText(availabilityBody);
                sessionsTutorHelpText.setText(sessionBody);
                profileTutorHelpText.setText(profileBody);

                //close dialogue button
                Button closeButton = (Button) dialog.findViewById(R.id.declineTutorButton);
                // if decline button is clicked, close the custom dialog
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });
            }
        });

        // Click listener for upcoming session list:
        upcomingSessionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get tutor id
                cursorSessionsResponse.moveToPosition(position);
                int SESSION_ID = cursorSessionsResponse.getInt(cursorSessionsResponse.getColumnIndex("id"));

                // Add USER_ID and TUTOR_ID to session details fragment for displaying
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("SESSION_ID", SESSION_ID);

                // Swap into new fragment
                TutorUpcSessionsDetailsFragment session = new TutorUpcSessionsDetailsFragment();
                session.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tutor_fragment_container, session);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
    }
}
