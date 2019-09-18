package cs.dal.krush.studentFragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cs.dal.krush.R;
import cs.dal.krush.appFragments.SessionLocationFragment;
import cs.dal.krush.models.DBHelper;
import cs.dal.krush.tutorFragments.TutorHomeFragment;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * This fragment displays the details of a tutoring session.
 * It is passed the SESSION_ID that corresponds to the session clicked on the home fragment
 */
public class StudentUpcSessionsDetailsFragment extends Fragment {

    static int USER_ID;
    static int SESSION_ID;
    String AudioSavePathInDevice = null;
    DBHelper mydb;
    Cursor sessionCursor, student, hasRecorded;
    TextView titleField, tutorNameField, tutorEmailField, locationField,
            startField, endField, tutorLabel, sessionInfoLabel, schoolField;
    ImageView tutorPicture;
    Button cancelButton, sessionDetailLocation, sessionRecordStart, sessionRecordStop;
    MediaRecorder mediaRecorder;
    public static final int RequestPermissionCode = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_home_upc_session_details, container, false);

        // Get USER_ID and SESSION_ID
        USER_ID = getArguments().getInt("USER_ID");
        SESSION_ID = getArguments().getInt("SESSION_ID");

        // Initialize db connection
        mydb = new DBHelper(getContext());

        // Get session
        sessionCursor = mydb.tutoringSession.getSessionHistoryDetailsBySessionIdForTutorCursorAdapter(SESSION_ID);
        sessionCursor.moveToFirst();

        //Get student
        student = mydb.student.getData(USER_ID);
        student.moveToFirst();

        // Get Views
        tutorPicture = (ImageView) view.findViewById(R.id.student_upc_details_tutor_picture);
        titleField = (TextView) view.findViewById(R.id.student_upc_details_title);
        tutorNameField = (TextView) view.findViewById(R.id.student_upc_details_tutor_name);
        tutorEmailField = (TextView) view.findViewById(R.id.student_upc_details_tutor_email);
        locationField = (TextView) view.findViewById(R.id.student_upc_details_location);
        startField = (TextView) view.findViewById(R.id.student_upc_details_start);
        endField = (TextView) view.findViewById(R.id.student_upc_details_end);
        tutorLabel = (TextView) view.findViewById(R.id.student_upc_details_tutor_label);
        sessionInfoLabel = (TextView) view.findViewById(R.id.student_upc_details_session_label);
        schoolField = (TextView) view.findViewById(R.id.student_upc_details_school);
        cancelButton = (Button) view.findViewById(R.id.student_cancel_session_button);
        sessionDetailLocation = (Button) view.findViewById(R.id.sessionDetailLocation);
        sessionRecordStart = (Button) view.findViewById(R.id.sessionRecordStart);
        sessionRecordStop = (Button) view.findViewById(R.id.sessionRecordStop);
        sessionRecordStop.setVisibility(view.INVISIBLE);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

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
        sessionDetailLocation.setTypeface(typeFace);
        cancelButton.setTypeface(typeFace);
        sessionRecordStart.setTypeface(typeFace);
        sessionRecordStop.setTypeface(typeFace);

        // Get values from database
        String title = sessionCursor.getString(sessionCursor.getColumnIndex("title"));
        String tutorName = sessionCursor.getString(sessionCursor.getColumnIndex("f_name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("l_name"));
        String tutorEmail = sessionCursor.getString(sessionCursor.getColumnIndex("email"));
        final String startTime = sessionCursor.getString(sessionCursor.getColumnIndex("start_time"));
        String endTime = sessionCursor.getString(sessionCursor.getColumnIndex("end_time"));
        String location = sessionCursor.getString(sessionCursor.getColumnIndex("location"));
        String imgPath = sessionCursor.getString(sessionCursor.getColumnIndex("profile_pic"));
        String school = sessionCursor.getString(sessionCursor.getColumnIndex("name")) + " " +
                sessionCursor.getString(sessionCursor.getColumnIndex("type"));
        final String locationId = sessionCursor.getString(sessionCursor.getColumnIndex("location_id"));

        // Set values to view
        titleField.setText(title);
        tutorNameField.setText(tutorName);
        tutorEmailField.setText(tutorEmail);
        startField.setText("Starts At: " + startTime);
        endField.setText("Ends At: " + endTime);
        locationField.setText("Meeting Location: " + location);
        schoolField.setText("School: " + school);
        if (imgPath != null && !imgPath.isEmpty()) {
            Bitmap profilePic = BitmapFactory.decodeFile(imgPath);
            tutorPicture.setImageBitmap(profilePic);
        }

        DateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date now = new Date();
        String currentTime = timeFormatter.format(now);
        boolean hasStarted = true;

        try {
            hasStarted = timeFormatter.parse(startTime).before(timeFormatter.parse(currentTime));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(hasStarted)
            sessionRecordStart.setVisibility(view.VISIBLE);

        //setup OnClickListeners:
        sessionDetailLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Add LOCATION_ID to be passed to new view
                Bundle bundle = new Bundle();
                bundle.putInt("LOCATION_ID", Integer.parseInt(locationId));

                // Swap into new fragment
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionLocationFragment newFragment = new SessionLocationFragment();

                newFragment.setArguments(bundle);

                ft.replace(R.id.student_fragment_container, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Build the "Are you sure?" dialog window:
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to cancel this session?").setTitle("Cancel Session");
        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Initialize db connection
                mydb = new DBHelper(getContext());

                // user clicked on "Yes" - destroy the tutoring session
                mydb.tutoringSession.deleteTutoringSession(SESSION_ID);
                mydb.audioRecording.deleteAudioRecording(SESSION_ID);

                // Set tutors available time booked to 0
                ContentValues cv = new ContentValues();
                cv.put("booked", "0");
                String start = "\"" + startTime + "\"";
                mydb.getWritableDatabase().update("available_time", cv, "start_time="+start, null);

                // return to home fragment
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);
                StudentHomeFragment home = new StudentHomeFragment();
                home.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.student_fragment_container, home);
                transaction.commit();
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //user clicked on "No" - return to the view

            }
        });
        final AlertDialog dialog = builder.create();

        // Set on click listener for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display "are you sure" dialog window:
                dialog.show();
            }
        });

        // Record session, save to file on device with title student name + session time
        sessionRecordStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkPermission()){
                    mydb = new DBHelper(getContext());

                    // Get session
                    sessionCursor = mydb.tutoringSession.getData(SESSION_ID);
                    sessionCursor.moveToFirst();

                    //Get student
                    student = mydb.student.getData(USER_ID);
                    student.moveToFirst();
                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + student.getString(student.getColumnIndex("f_name")) +
                                    student.getString(student.getColumnIndex("l_name")) + sessionCursor.getString(sessionCursor.getColumnIndex("start_time")) + ".3gp";
                    Log.e("File name", AudioSavePathInDevice);
                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    hasRecorded = mydb.audioRecording.getDataByStudentAndSessionId(USER_ID, SESSION_ID);
                    hasRecorded.moveToFirst();
                    if(hasRecorded.getCount() > 0)
                        mydb.audioRecording.update(USER_ID, SESSION_ID);
                    else
                        mydb.audioRecording.insert(USER_ID, SESSION_ID);

                    sessionRecordStart.setVisibility(v.INVISIBLE);
                    sessionRecordStop.setVisibility(v.VISIBLE);
                    sessionCursor.close();
                    student.close();
                    hasRecorded.close();
                    mydb.close();
                }else {
                    requestPermission();
                }
            }

        });

        sessionRecordStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                sessionRecordStart.setVisibility(view.VISIBLE);
                sessionRecordStop.setVisibility(view.INVISIBLE);
            }
        });

        sessionCursor.close();
        mydb.close();
        return view;
    }

    /**
     *  init media recorder
     */
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

    }

    /**
     * check device permissions
     * @return boolean permission
     */
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * set device permissions
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
}
