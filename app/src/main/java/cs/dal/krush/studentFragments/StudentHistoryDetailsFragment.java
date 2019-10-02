package cs.dal.krush.studentFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * This fragment displays the details of a session when a tutor clicks on a
 * completed session in the tutor Sessions History list view
 */
public class StudentHistoryDetailsFragment extends Fragment {

    static int USER_ID;
    static int SESSION_ID;
    static int TUTOR_ID;
    String AudioSavePathInDevice = null;
    DBHelper mydb;
    Cursor student, sessionCursor;
    TextView titleField, tutorNameField, tutorEmailField, locationField,
            startField, endField, tutorLabel, sessionInfoLabel, schoolField, rateTutor;
    ImageView tutorPicture;
    RatingBar ratingBarView;
    Button playRecording, stopRecording;
    MediaPlayer mediaPlayer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_sessions_details, container, false);

        // Get USER_ID and SESSION_ID
        USER_ID = getArguments().getInt("USER_ID");
        SESSION_ID = getArguments().getInt("SESSION_ID");
        TUTOR_ID = getArguments().getInt("TUTOR_ID");

        // Initialize db connection
        mydb = new DBHelper(getContext());

        // Get session
        sessionCursor = mydb.tutoringSession.getSessionHistoryDetailsBySessionIdForTutorCursorAdapter(SESSION_ID);
        sessionCursor.moveToFirst();

        // Get Views
        tutorPicture = (ImageView) view.findViewById(R.id.student_history_details_tutor_picture);
        titleField = (TextView) view.findViewById(R.id.student_history_details_title);
        tutorNameField = (TextView) view.findViewById(R.id.student_history_details_tutor_name);
        tutorEmailField = (TextView) view.findViewById(R.id.student_history_details_tutor_email);
        locationField = (TextView) view.findViewById(R.id.student_history_details_location);
        startField = (TextView) view.findViewById(R.id.student_history_details_start);
        endField = (TextView) view.findViewById(R.id.student_history_details_end);
        tutorLabel = (TextView) view.findViewById(R.id.student_history_details_tutor_label);
        sessionInfoLabel = (TextView) view.findViewById(R.id.student_history_details_session_label);
        schoolField = (TextView)view.findViewById(R.id.student_history_details_school);
        rateTutor = (TextView)view.findViewById(R.id.student_history_details_rate_tutor);
        ratingBarView = (RatingBar) view.findViewById(R.id.tutor_details_rating);
        playRecording = (Button) view.findViewById(R.id.sessionPlayRecording);
        stopRecording = (Button) view.findViewById(R.id.sessionStopRecording);
        playRecording.setVisibility(view.INVISIBLE);
        stopRecording.setVisibility(view.INVISIBLE);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FredokaOne-Regular.ttf");

        //Set custom app font
        titleField.setTypeface(typeFace);
        tutorNameField.setTypeface(typeFace);
        tutorEmailField.setTypeface(typeFace);
        locationField.setTypeface(typeFace);
        startField.setTypeface(typeFace);
        endField.setTypeface(typeFace);
        tutorLabel.setTypeface(typeFace);
        sessionInfoLabel.setTypeface(typeFace);
        schoolField.setTypeface(typeFace);
        rateTutor.setTypeface(typeFace);
        playRecording.setTypeface(typeFace);
        stopRecording.setTypeface(typeFace);

        // Get values from database
        String title = sessionCursor.getString(sessionCursor.getColumnIndex("title"));
        String tutorName = sessionCursor.getString(sessionCursor.getColumnIndex("f_name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("l_name"));
        String tutorEmail = sessionCursor.getString(sessionCursor.getColumnIndex("email"));
        String startTime = sessionCursor.getString(sessionCursor.getColumnIndex("start_time"));
        String endTime = sessionCursor.getString(sessionCursor.getColumnIndex("end_time"));
        String location = sessionCursor.getString(sessionCursor.getColumnIndex("location"));
        String imgPath = sessionCursor.getString(sessionCursor.getColumnIndex("profile_pic"));
        String school = sessionCursor.getString(sessionCursor.getColumnIndex("name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("type"));

        // Set values to view
        titleField.setText(title);
        tutorNameField.setText(tutorName);
        tutorEmailField.setText(tutorEmail);
        startField.setText("Started At: " + startTime);
        endField.setText("Ended At: " + endTime);
        locationField.setText("Meeting Location: " + location);
        schoolField.setText("School: " + school);
        if(imgPath != null && !imgPath.isEmpty()) {
            Bitmap profilePic = BitmapFactory.decodeFile(imgPath);
            tutorPicture.setImageBitmap(profilePic);
        }

        Cursor hasRecording = mydb.audioRecording.getDataByStudentAndSessionId(USER_ID, SESSION_ID);
        hasRecording.moveToFirst();

        if (hasRecording.getCount() > 0)
            playRecording.setVisibility(view.VISIBLE);

        final Cursor hasRatedBefore = mydb.tutorRating.getTutorRatingByTutorAndStudentId(TUTOR_ID, USER_ID);
        hasRatedBefore.moveToFirst();

        if(hasRatedBefore.getCount() > 0)
            ratingBarView.setRating(Float.parseFloat(hasRatedBefore.getString(hasRatedBefore.getColumnIndex("rating"))));

        ratingBarView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser) {
                    mydb = new DBHelper(getContext());
                    if (hasRatedBefore.getCount() > 0)
                        mydb.tutorRating.updateTutorRating(rating, USER_ID, TUTOR_ID);
                    else
                        mydb.tutorRating.insert(rating, USER_ID, TUTOR_ID);
                    Cursor tutorRatingFromDB = mydb.tutorRating.getTutorRatingByTutorId(TUTOR_ID);
                    Cursor tutor = mydb.tutor.getData(TUTOR_ID);
                    tutorRatingFromDB.moveToFirst();
                    tutor.moveToFirst();
                    int n = tutorRatingFromDB.getCount();
                    float newTutorRating = 0;
                    for (int i = 0; i < n; i++) {
                        newTutorRating += Float.parseFloat(tutorRatingFromDB.getString(tutorRatingFromDB.getColumnIndex("rating")));
                        tutorRatingFromDB.move(1);
                    }
                    newTutorRating = newTutorRating/n;
                    newTutorRating = Float.parseFloat(String.format("%.1f", newTutorRating));

                    mydb.tutor.updateTutorRating(TUTOR_ID, newTutorRating, n);
                    ratingBarView.setRating(rating);
                    mydb.close();
                }
            }
        });


        playRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                // Initialize db connection
                mydb = new DBHelper(getContext());

                // Get session
                sessionCursor = mydb.tutoringSession.getSessionHistoryDetailsBySessionIdForTutorCursorAdapter(SESSION_ID);
                sessionCursor.moveToFirst();

                //Get student
                student = mydb.student.getData(USER_ID);
                student.moveToFirst();

                playRecording.setVisibility(view.INVISIBLE);
                stopRecording.setVisibility(view.VISIBLE);
                AudioSavePathInDevice =
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + student.getString(student.getColumnIndex("f_name")) +
                                student.getString(student.getColumnIndex("l_name")) + sessionCursor.getString(sessionCursor.getColumnIndex("start_time")) + ".3gp";
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sessionCursor.close();
                student.close();
                mydb.close();
                mediaPlayer.start();

            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                mediaPlayer = null;
                playRecording.setVisibility(v.VISIBLE);
                stopRecording.setVisibility(v.INVISIBLE);
            }
        });

        sessionCursor.close();
        mydb.close();

        return view;
    }
}
