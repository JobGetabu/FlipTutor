package cs.dal.krush.tutorFragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import cs.dal.krush.R;
import cs.dal.krush.TutorCursorAdapters.SessionCursorAdapter;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Tutor Home fragment. This fragment belongs to the TutorMainActivity class
 * and is accessed through the tutor's bottom navigation bar.
 *
 * The tutor can view their session history using this fragment.
 */
public class TutorHistoryFragment extends Fragment {

    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_sessions, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //get Context:
        Context C = getContext();

        //init DB connection:
        DBHelper mydb = new DBHelper(C);

        Cursor tutor = mydb.tutor.getData(USER_ID);
        tutor.moveToFirst();

        //fetch UI elements:
        ListView sessionHistoryListView = (ListView)view.findViewById(R.id.sessionHistoryListView);
        TextView pageTitle = (TextView)view.findViewById(R.id.sessionHistoryTitle);

        //fetch custom app font:
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);

        //get all tutoring sessions by the tutor:
        final Cursor cursorSessionsResponse = mydb.tutoringSession.getSessionHistoryByTutorIdForCursorAdapter(USER_ID);

        //set sessions listview adapter:
        SessionCursorAdapter sessionsAdapter = new SessionCursorAdapter(C, cursorSessionsResponse);
        sessionHistoryListView.setAdapter(sessionsAdapter);

        // Click listener for sessions history list:
        sessionHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get session id
                cursorSessionsResponse.moveToPosition(position);
                int SESSION_ID = cursorSessionsResponse.getInt(cursorSessionsResponse.getColumnIndex("id"));

                // Add USER_ID and SESSION_ID to session details fragment for displaying
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("SESSION_ID", SESSION_ID);

                // Swap into new fragment
                TutorHistoryDetailsFragment session = new TutorHistoryDetailsFragment();
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
