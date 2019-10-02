package cs.dal.krush.studentFragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cs.dal.krush.R;
import cs.dal.krush.StudentCursorAdapters.SessionCursorAdapter;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Student Sessions History fragment. This fragment belongs to the StudentMainActivity class
 * and is accessed through the student's bottom navigation bar.
 *
 * The student can view their previous sessions history along with audio recordings using this fragment.
 */

public class StudentHistoryFragment extends Fragment {

    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_sessions, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //get Context:
        Context C = getContext();

        //init DB connection:
        DBHelper mydb = new DBHelper(C);

        //fetch UI elements:
        ListView sessionHistoryListView = (ListView)view.findViewById(R.id.sessionHistoryListView);
        TextView pageTitle = (TextView)view.findViewById(R.id.sessionHistoryTitle);

        //fetch custom app font:
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);

        //get all tutoring sessions by the student:
        final Cursor cursorSessionsResponse = mydb.tutoringSession.getSessionHistoryByStudentIdForCursorAdapter(USER_ID);

        //set sessions listview adapter:
        SessionCursorAdapter sessionsAdapter = new SessionCursorAdapter(C, cursorSessionsResponse);
        sessionHistoryListView.setAdapter(sessionsAdapter);

        // Click listener for sessions history list:
        sessionHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get tutor id
                cursorSessionsResponse.moveToPosition(position);
                int SESSION_ID = cursorSessionsResponse.getInt(cursorSessionsResponse.getColumnIndex("id"));
                int TUTOR_ID = cursorSessionsResponse.getInt(cursorSessionsResponse.getColumnIndex("_id"));

                // Add USER_ID and TUTOR_ID to session details fragment for displaying
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("SESSION_ID", SESSION_ID);
                bundle.putInt("TUTOR_ID", TUTOR_ID);

                // Swap into new fragment
                StudentHistoryDetailsFragment session = new StudentHistoryDetailsFragment();
                session.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.student_fragment_container, session);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return view;
    }
}
