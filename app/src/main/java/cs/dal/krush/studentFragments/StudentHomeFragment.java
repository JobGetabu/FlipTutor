package cs.dal.krush.studentFragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cs.dal.krush.R;
import cs.dal.krush.StudentCursorAdapters.HomeQuickBookCursorAdapter;
import cs.dal.krush.StudentCursorAdapters.SessionCursorAdapter;
import cs.dal.krush.models.DBHelper;


/**
 * Sets up the Student Home fragment. This fragment belongs to the StudentMainActivity class
 * and is accessed through the student's bottom navigation bar.
 *
 * Source:
 * [5] List View. (n.d.). Retrieved March 12, 2017,
 * from https://developer.android.com/guide/topics/ui/layout/listview.html
 */
public class StudentHomeFragment extends Fragment {

    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.student_home, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //get Context:
        Context C = getContext();

        //init DB connection:
        DBHelper mydb = new DBHelper(C);

        //fetch UI elements:
        ListView upcomingSessionsListView = (ListView)view.findViewById(R.id.upcomingSessionsListView);
        ListView tutorsListView = (ListView)view.findViewById(R.id.availableTutorsListView);
        TextView pageTitle = (TextView)view.findViewById(R.id.homeTitleLabel);
        TextView sessionsLabel = (TextView)view.findViewById(R.id.upcomingSessionsLabel);
        TextView bookTutorLabel = (TextView)view.findViewById(R.id.bookTutorLabel);
        FloatingActionButton helpButton = (FloatingActionButton)view.findViewById(R.id.helpButtonStudent);

        //fetch custom app font:
        final Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);
        sessionsLabel.setTypeface(typeFace);
        bookTutorLabel.setTypeface(typeFace);

        //get all tutoring sessions by the student:
        final Cursor cursorSessionsResponse = mydb.tutoringSession.getDataByStudentIdForCursorAdapter(USER_ID);

        //set sessions listview adapter:
        SessionCursorAdapter sessionsAdapter = new SessionCursorAdapter(C, cursorSessionsResponse);
        upcomingSessionsListView.setAdapter(sessionsAdapter);

        //get all distinct tutors that the user has previously had a tutoring session with:
        final Cursor cursorTutorResponse = mydb.tutor.getPreviouslyUsedTutorsForCursorAdapter(USER_ID);

        //set tutor's listview adapter:
        HomeQuickBookCursorAdapter quickBookAdapter = new HomeQuickBookCursorAdapter(C, cursorTutorResponse);
        tutorsListView.setAdapter(quickBookAdapter);

        // Click listeners for listviews
        upcomingSessionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                //Get id of session clicked on
                cursorSessionsResponse.moveToPosition(position);
                int SESSION_ID = cursorSessionsResponse.getInt(cursorSessionsResponse.getColumnIndex("id"));

                // Add USER_ID and SESSION_ID to session details fragment for displaying
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("SESSION_ID", SESSION_ID);

                // Swap into new fragment
                StudentUpcSessionsDetailsFragment session = new StudentUpcSessionsDetailsFragment();
                session.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.student_fragment_container, session);
                transaction.addToBackStack(null);
                transaction.commit();

            }

        });

        tutorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                cursorTutorResponse.moveToPosition(position);
                int TUTOR_ID = cursorTutorResponse.getInt(cursorTutorResponse.getColumnIndex("_id"));

                // Add USER_ID and SESSION_ID to session details fragment for displaying
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("TUTOR_ID", TUTOR_ID);

                // Swap into new fragment
                StudentBookingDetailsFragment tutor = new StudentBookingDetailsFragment();
                tutor.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.student_fragment_container, tutor);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        final String introBody = "\nWelcome to Krush!\nWe help you connect with tutors for a wide " +
                "range of topics. Whether you need help on a specific assignment or need that extra " +
                "weekly follow up Krush will help you find the right tutor for your topic, school " +
                "and budget.\n";
        final String homeBody = "\nHome is where you can have glimpse of your upcoming tutoring sessions or use the ‘Quick Book’ to book a session in a cinch.\n" +
                "\n" +
                "Your upcoming sessions are all the session you booked and paid for. You can click " +
                "on the individual sessions to view details of that session such as time, location " +
                "and the tutor’s name. Further, you can cancel the session in case you have " +
                "a change of plans.\n" +
                "\n" +
                "Need a tutor in hurry for a tune up before the exam? We got you covered! " +
                "You can book a tutor in no time with the ‘Quick Book’ feature. Simply select one " +
                "of the tutors you previously had session with, select your time and pay. " +
                "That’s it!\n";
        final String bookingBody = "\nBooking a tutor has never been easier. Forget the billboard, " +
                "Krush brings tutoring to the 21st century. In the booking menu, you can view all " +
                "the tutors in Krush. You can even filter to see tutors for your school. Once you " +
                "select a tutor, you can browse details about that tutor include their rate and " +
                "rating. Then you need to select a time from their availabilities. That’s it! Pay " +
                "for your session with a credit card and you’re on your way to better grades!\n";
        final String sessionBody = "\nIn the sessions section, you can browse all your tutoring " +
                "sessions (past and present). You can click on a specific session to submit a " +
                "review for a tutor or listen to the audio recording for that session!\n";
        final String profileBody = "\n" +
                "In the profile section, you can view and edit your profile details. We strongly " +
                "recommend using your camera associate your face with your profile! Setting your " +
                "profile picture makes it easy to meet a new tutor in a public location. \n";

                //display student help dialog
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.student_help);
                // Set dialog title
                dialog.setTitle("Custom Dialog");
                dialog.show();

                //fetch UI components
                TextView studentHelpHeader = (TextView) dialog.findViewById(R.id.studentHelpHeader);
                TextView studentHelpIntro = (TextView) dialog.findViewById(R.id.studentHelpIntro);
                TextView homeStudentHelpLabel = (TextView) dialog.findViewById(R.id.homeStudentHelpLabel);
                TextView homeStudentHelpText = (TextView) dialog.findViewById(R.id.homeStudentHelpText);
                TextView bookingStudentHelpLabel = (TextView) dialog.findViewById(R.id.bookingStudentHelpLabel);
                TextView bookingStudentHelpText = (TextView) dialog.findViewById(R.id.bookingStudentHelpText);
                TextView seesionsStudentHelpLabel = (TextView) dialog.findViewById(R.id.seesionsStudentHelpLabel);
                TextView sessionsStudentHelpText = (TextView) dialog.findViewById(R.id.sessionsStudentHelpText);
                TextView profileStudentHelpLabel = (TextView) dialog.findViewById(R.id.profileStudentHelpLabel);
                TextView profileStudentHelpText = (TextView) dialog.findViewById(R.id.profileStudentHelpText);


                //set logo font style
                studentHelpHeader.setTypeface(typeFace);
                homeStudentHelpLabel.setTypeface(typeFace);
                bookingStudentHelpLabel.setTypeface(typeFace);
                seesionsStudentHelpLabel.setTypeface(typeFace);
                profileStudentHelpLabel.setTypeface(typeFace);

                //set text in dialogue
                studentHelpIntro.setText(introBody);
                homeStudentHelpText.setText(homeBody);
                bookingStudentHelpText.setText(bookingBody);
                sessionsStudentHelpText.setText(sessionBody);
                profileStudentHelpText.setText(profileBody);

                //close dialogue button
                Button closeButton = (Button) dialog.findViewById(R.id.declineButton);
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


        return view;
    }

}