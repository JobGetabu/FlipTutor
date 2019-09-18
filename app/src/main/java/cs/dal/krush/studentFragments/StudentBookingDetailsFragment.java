package cs.dal.krush.studentFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cs.dal.krush.R;
import cs.dal.krush.helpers.DateFormatHelper;
import cs.dal.krush.models.DBHelper;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static cs.dal.krush.studentFragments.StudentPaymentFragment.COST;


/**
 * This fragment displays the details of a tutor when a student clicks on their profile
 */
public class StudentBookingDetailsFragment extends Fragment implements View.OnClickListener {

    static int USER_ID;
    static int TUTOR_ID;
    DBHelper mydb;
    Cursor tutorCursor, timeCursor;
    ImageView tutorProfilePicView;
    TextView nameView, schoolView, rateView, locationView, schoolLabel, rateLabel, locationLabel, coursesLabel, timeLabel, ratingCount;
    RatingBar ratingBarView;
    Spinner courseSpinnerView, timeSpinnerView;
    Button bookButton;
    String name, location, rate;
    boolean isValid = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_tutor_details, container, false);

        // Get USER_ID and TUTOR_ID
        USER_ID = getArguments().getInt("USER_ID");
        TUTOR_ID = getArguments().getInt("TUTOR_ID");

        // Initialize db connection
        mydb = new DBHelper(getContext());

        // Get tutor
        tutorCursor = mydb.tutor.getData(TUTOR_ID);
        tutorCursor.moveToFirst();

        // Get Views
        nameView = (TextView) view.findViewById(R.id.tutor_details_name);
        schoolView = (TextView) view.findViewById(R.id.tutor_details_school);
        rateView = (TextView) view.findViewById(R.id.tutor_details_rate);
        ratingBarView = (RatingBar) view.findViewById(R.id.tutor_details_rating);
        ratingCount = (TextView) view.findViewById(R.id.ratingCount);
        tutorProfilePicView = (ImageView) view.findViewById(R.id.tutor_details_profile_picture);
        locationView = (TextView) view.findViewById(R.id.tutor_details_location);
        courseSpinnerView = (Spinner) view.findViewById(R.id.tutor_details_courses);
        timeSpinnerView = (Spinner) view.findViewById(R.id.tutor_details_time_slots);
        schoolLabel = (TextView) view.findViewById(R.id.tutor_details_school_label);
        rateLabel = (TextView) view.findViewById(R.id.tutor_details_rate_label);
        locationLabel = (TextView) view.findViewById(R.id.tutor_details_location_label);
        coursesLabel = (TextView) view.findViewById(R.id.tutor_details_courses_label);
        timeLabel = (TextView) view.findViewById(R.id.tutor_details_time_slots_label);
        bookButton = (Button) view.findViewById(R.id.tutor_details_book_button);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //Set custom app font
        nameView.setTypeface(typeFace);
        schoolView.setTypeface(typeFace);
        rateView.setTypeface(typeFace);
        locationView.setTypeface(typeFace);
        schoolLabel.setTypeface(typeFace);
        rateLabel.setTypeface(typeFace);
        locationLabel.setTypeface(typeFace);
        coursesLabel.setTypeface(typeFace);
        timeLabel.setTypeface(typeFace);

        // Currency formatter for rate
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        // Get values from database
        name = tutorCursor.getString(tutorCursor.getColumnIndex("f_name")) + " " + tutorCursor.getString(tutorCursor.getColumnIndex("l_name"));
        String school = mydb.tutor.getSchoolNameAndType(TUTOR_ID);
        rate = tutorCursor.getString(tutorCursor.getColumnIndex(("rate")));
        String rateDisplay;
        if(TextUtils.isEmpty(rate)){
            rateDisplay = "Tutor has not set a rate";
            bookButton.setEnabled(false);
        }
        else{
            rateDisplay = currencyFormatter.format(Float.parseFloat(rate));
        }
        String rating = tutorCursor.getString(tutorCursor.getColumnIndex("rating"));
        int totalRatings = mydb.tutor.getTutorRatingCount(TUTOR_ID);
        ratingCount.setText("(" + totalRatings + ")");
        ratingCount.setTypeface(typeFace);
        location = mydb.tutor.getLocationName(TUTOR_ID);
        ArrayList<String> courseNames = mydb.coursesTutors.getCourseNamesFromTutor(TUTOR_ID);

        // Tutor availability
        timeCursor = mydb.availableTime.getUpcomingDataByTutorId(TUTOR_ID);
        ArrayList<String> tutorAvailability = new ArrayList<>();
        String time, start, end;
        while(timeCursor.moveToNext())
        {
            // Get start and end times
            start = timeCursor.getString(timeCursor.getColumnIndex("start_time"));
            end = timeCursor.getString(timeCursor.getColumnIndex("end_time"));
            try{
                // Convert to readable dates for displaying
                start = DateFormatHelper.getStartDate(start);
                end = DateFormatHelper.getTimeFromDate(end);
            } catch(Exception ex)
            {
                ex.printStackTrace();
            }
            time = start + " - " + end;

            // Add to list of time slots
            tutorAvailability.add(time);
        }

        // Set values
        nameView.setText(name);
        schoolView.setText(school);
        rateView.setText(rateDisplay);
        locationView.setText(location);

        // Set list of course names
        if(courseNames != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getContext(), android.R.layout.simple_spinner_item, courseNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinnerView.setAdapter(adapter);
        }
        else{
            bookButton.setEnabled(false);
        }

        // Set list of time slots
        if(!tutorAvailability.isEmpty()) {
            ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(
                    getContext(), android.R.layout.simple_spinner_item, tutorAvailability);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinnerView.setAdapter(timeAdapter);
        }
        else {
            bookButton.setEnabled(false);
        }

        // Set Profile Picture
        String imagePath = tutorCursor.getString(tutorCursor.getColumnIndex("profile_pic"));
        if(imagePath != null && !imagePath.isEmpty()) {
            Bitmap profile_pic = BitmapFactory.decodeFile(imagePath);
            tutorProfilePicView.setImageBitmap(profile_pic);
        }

        // Set tutors rating
        if(rating != null) {
            ratingBarView.setRating(Float.parseFloat(rating));
        }

        bookButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        int tutorRate = Integer.parseInt(rate);
        float COST = 0;

        // Create tutoring session variables to send to payment fragment to create session on successful payment

        //LocationId
        int LOCATION_ID = mydb.tutor.getLocationId(TUTOR_ID);

        //start date
        int timeIndex = timeSpinnerView.getSelectedItemPosition();
        timeCursor.moveToPosition(timeIndex);
        String START_TIME = timeCursor.getString(timeCursor.getColumnIndex("start_time"));

        //end date
        String END_TIME = timeCursor.getString(timeCursor.getColumnIndex("end_time"));

        //Set a title
        String TITLE = courseSpinnerView.getSelectedItem().toString();

        // Calculate cost
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd H:mm:ss", Locale.getDefault());
        try
        {
            Date startDate = format.parse(START_TIME);
            Date endDate = format.parse(END_TIME);

            // Get difference between start and end times in hours
            float diff = (float) ((endDate.getTime() - startDate.getTime()) / (1000.00*60.00*60.00));

            // rates are per half hour, so double the difference and multiply by the tutors rate
            COST = diff*2*tutorRate;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // Close db
        timeCursor.close();
        tutorCursor.close();
        mydb.close();

        // Pass to payment Fragment
        Bundle bundle = new Bundle();
        bundle.putInt("USER_ID", USER_ID);
        bundle.putInt("TUTOR_ID", TUTOR_ID);
        bundle.putInt("LOCATION_ID", LOCATION_ID);
        bundle.putString("START_TIME", START_TIME);
        bundle.putString("END_TIME", END_TIME);
        bundle.putString("TITLE", TITLE);
        bundle.putFloat("COST", COST);

        // Start payment fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        StudentPaymentFragment payment = new StudentPaymentFragment();
        payment.setArguments(bundle);
        transaction.replace(R.id.student_fragment_container, payment);
        transaction.commit();

    }
}
