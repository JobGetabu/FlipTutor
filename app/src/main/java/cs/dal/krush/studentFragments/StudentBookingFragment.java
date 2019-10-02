package cs.dal.krush.studentFragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cs.dal.krush.R;
import cs.dal.krush.StudentCursorAdapters.BookingTutorCursorAdapter;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Student Booking fragment. This fragment belongs to the StudentMainActivity class
 * and is accessed through the student's bottom navigation bar.
 *
 * The student can book a tutoring session through this fragment.
 *
 * Source:
 * [5] List View. (n.d.). Retrieved March 12, 2017,
 * from https://developer.android.com/guide/topics/ui/layout/listview.html
 */
public class StudentBookingFragment extends Fragment {

    private DBHelper mydb;
    private ListView tutorsListView;
    private Context C;
    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_booking, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //get Context:
        C = getContext();

        //init DB connection:
        mydb = new DBHelper(C);

        //fetch UI elements:
        tutorsListView = (ListView)view.findViewById(R.id.availableTutorsListView);
        TextView pageTitle = (TextView)view.findViewById(R.id.bookingTitleLabel);
        Switch filterByCourses = (Switch)view.findViewById(R.id.filterByCoursesSwitch);

        //fetch custom app font:
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);
        filterByCourses.setTypeface(typeFace);

        //Set OnCheckListener:
        filterByCourses.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //get tutors filtered by the student's school:
                    Cursor cursorTutorResponse = mydb.tutor.getTutorsFilteredBySchoolForCursorAdapter(USER_ID);
                    //set tutor's listview adapter:
                    BookingTutorCursorAdapter profileAdapter = new BookingTutorCursorAdapter(C, cursorTutorResponse);
                    tutorsListView.setAdapter(profileAdapter);
                } else {
                    //get all tutors from DB:
                    Cursor cursorTutorResponse = mydb.tutor.getAllForCursorAdapter();
                    //set tutor's listview adapter:
                    BookingTutorCursorAdapter profileAdapter = new BookingTutorCursorAdapter(C, cursorTutorResponse);
                    tutorsListView.setAdapter(profileAdapter);
                }
            }
        });

        //get all tutors from DB:
        final Cursor cursorTutorResponse = mydb.tutor.getAllForCursorAdapter();

        //set tutor's listview adapter:
        BookingTutorCursorAdapter profileAdapter = new BookingTutorCursorAdapter(C, cursorTutorResponse);
        tutorsListView.setAdapter(profileAdapter);

        // Click listener for tutors list
        tutorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get tutor id
                cursorTutorResponse.moveToPosition(position);
                int TUTOR_ID = cursorTutorResponse.getInt(cursorTutorResponse.getColumnIndex("_id"));

                // Add USER_ID and TUTOR_ID to session details fragment for displaying
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

        return view;
    }
}
