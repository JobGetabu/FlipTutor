package cs.dal.krush.tutorFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Tutor Availability fragment. This fragment belongs to the TutorMainActivity class
 * and is accessed through the tutor's bottom navigation bar.
 *
 * The tutors can set their availability and schedule_view using this fragment.
 */
public class TutorAvailabilityFragment extends Fragment {

    Button btnDatePicker, btnStartTimePicker, btnEndTimePicker, btnSubmit, btnViewCalendar, btnViewLocation;
    EditText txtDate, txtStartTime, txtEndTime;
    TextView txtTitle, txtSelectAvailability, txtYourAvailability;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int sYear, sMonth, sDay, sStartHour, sStartMinute, sEndHour, sEndMinute;
    private String startTime,endTime;
    private DBHelper db;
    private RelativeLayout rl;
    private ListView lvTutorScheduleListView;
    private DateFormat timeFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
    private GregorianCalendar startTimeCalendar = new GregorianCalendar();
    private GregorianCalendar endTimeCalendar = new GregorianCalendar();

    static int USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_availability, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        db = new DBHelper(getContext());
        txtTitle=(TextView)getView().findViewById(R.id.txtAvailabilityTitle);
        txtSelectAvailability=(TextView)getView().findViewById(R.id.txtSelectAvailability);
        txtYourAvailability=(TextView)getView().findViewById(R.id.txtViewAvailability);
        btnDatePicker=(Button)getView().findViewById(R.id.btnDate);
        btnStartTimePicker=(Button)getView().findViewById(R.id.btnStartTime);
        btnEndTimePicker=(Button)getView().findViewById(R.id.btnEndTime);
        btnSubmit=(Button)getView().findViewById(R.id.btnSubmit);
        btnViewCalendar=(Button)getView().findViewById(R.id.btnViewCalendar);
        btnViewLocation=(Button)getView().findViewById(R.id.btnViewLocation);
        txtDate=(EditText)getView().findViewById(R.id.txtDate);
        txtStartTime=(EditText)getView().findViewById(R.id.txtStartTime);
        txtEndTime=(EditText)getView().findViewById(R.id.txtEndTime);
        lvTutorScheduleListView=(ListView)getView().findViewById(R.id.lvTutorScheduleListView);
        rl = (RelativeLayout)getView().findViewById(R.id.activity_tutor_calendar);

        txtStartTime.setEnabled(false);
        txtEndTime.setEnabled(false);
        txtDate.setEnabled(false);
        txtDate.setFocusable(false);
        txtStartTime.setFocusable(false);
        txtEndTime.setFocusable(false);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set custom app fonts
        txtTitle.setTypeface(typeFace);
        txtSelectAvailability.setTypeface(typeFace);
        txtYourAvailability.setTypeface(typeFace);

        loadSchedule();

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String nameMonth = formatMonth(monthOfYear, Locale.CANADA);
                                String nameDay = getFullDayName(dayOfMonth,Locale.CANADA);
                                sYear = year;
                                sMonth = monthOfYear;
                                sDay = dayOfMonth;

                                txtDate.setText( nameMonth + " " + dayOfMonth + ", " + nameDay );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                sStartHour = hourOfDay;
                                sStartMinute = minute;

                                startTimeCalendar.set(Calendar.HOUR, sStartHour);
                                startTimeCalendar.set(Calendar.MINUTE, sStartMinute);
                                startTimeCalendar.set(Calendar.HOUR_OF_DAY, sStartHour);

                                txtStartTime.setText(timeFormatter.format(startTimeCalendar.getTime()));

                                startTime = concatenateDateTime(sStartHour,sStartMinute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        btnEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                sEndHour = hourOfDay;
                                sEndMinute = minute;

                                endTimeCalendar.set(Calendar.HOUR, sEndHour);
                                endTimeCalendar.set(Calendar.MINUTE, sEndMinute);
                                endTimeCalendar.set(Calendar.HOUR_OF_DAY, sEndHour);

                                txtEndTime.setText(timeFormatter.format(endTimeCalendar.getTime()));

                                endTime = concatenateDateTime(sEndHour,sEndMinute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if(txtStartTime.length() == 0){
                    txtStartTime.setError("Start time is required!");
                    isValid = false;
                }else{
                    txtStartTime.setError(null);
                }

                if(txtEndTime.length() == 0){
                    txtEndTime.setError("End time is required!");
                    isValid = false;
                }else{
                    txtEndTime.setError(null);
                }

                if(txtDate.length() == 0){
                    txtDate.setError("Date is required!");
                    isValid = false;
                }else{
                    txtDate.setError(null);
                }

                if(isValid){
                    int id = USER_ID;
                    db.availableTime.insert(startTime,endTime,USER_ID);
                    Toast.makeText(getContext(), "Time slot added successfully!", Toast.LENGTH_SHORT).show();
                    txtDate.setText("");
                    txtStartTime.setText("");
                    txtEndTime.setText("");
                    startTime = null;
                    endTime = null;
                    sYear = 0;
                    sMonth = 0;
                    sDay = 0;
                    sStartHour = 0;
                    sStartMinute = 0;
                    sEndHour = 0;
                    sEndMinute = 0;
                }

                loadSchedule();

            }
        });

        btnViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                TutorCalendarFragment newFragment = new TutorCalendarFragment();

                ft.replace(R.id.tutor_fragment_container, newFragment);
                ft.addToBackStack(null);

                ft.commit();
            }
        });

        btnViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add USER_ID to be passed to new view
                Bundle bundle = new Bundle();
                bundle.putInt("USER_ID", USER_ID);

                // Swap into new fragment
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                TutorLocationFragment newFragment = new TutorLocationFragment();

                newFragment.setArguments(bundle);

                ft.replace(R.id.tutor_fragment_container, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lvTutorScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String date = tv.getText().toString();
                if(!date.equals("")){
                    String[] splitDate = date.split("[:]");
                    date = splitDate[0];

                    Bundle bundle = new Bundle();
                    bundle.putString("DATE", date);
                    bundle.putInt("USER_ID",USER_ID);
                    TutorSingleDayAvailabilityFragment newFragment = new TutorSingleDayAvailabilityFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.tutor_fragment_container, newFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }

            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Get the name of a month from integer
     * @param month to convert to String
     * @param locale default locale
     * @return formatted month name
     */
    public String formatMonth(int month, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("MMMM", locale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get the name of a day from integer
     * @param day to convert to String
     * @param locale default locale
     * @return formatted day name
     */
    public static String getFullDayName(int day, Locale locale) {

        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get the full date String from params
     * @param day to convert to String
     * @param month to convert to String
     * @param year to convert to String
     * @return formatted DateTime
     */
    private String getDate(int day, int month, int year) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        return formatter.format(calendar.getTime());
    }

    /**
     * Construct a full date String in the format of "yyyy-MM-dd HH:mm:ss"
     * using the currently set day of month and year.
     * @param hour conversion helper to use selected hour
     * @param minute conversion helper to use selected minute
     * @return concatendated DateTime
     */
    public String concatenateDateTime(int hour, int minute){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, sDay);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MONTH, sMonth);
        calendar.set(Calendar.YEAR, sYear);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.SECOND,0);

        return formatter.format(calendar.getTime());

    }

    /**
     * Loads the ListView of the tutor's availability
     */
    public void loadSchedule(){
        String previousDate = "";
        String tempDate = "";
        List<String> dateTimes = new ArrayList<String>();
        boolean firstRun = true;
        Cursor rs;
        rs = db.availableTime.getAllOrderedByDay(USER_ID);
        try {
            while (rs.moveToNext()) {
                String startTime, endTime;
                startTime = rs.getString(rs.getColumnIndex("start_time"));
                endTime = rs.getString(rs.getColumnIndex("end_time"));

                String[] resultStartTime = startTime.split("\\s");
                String[] resultEndTime = endTime.split("\\s");

                //set first run data to prevent missing first item
                if(firstRun){
                    tempDate = resultStartTime[0] + ": " + stripTime(resultStartTime[1]) + "-" +
                            stripTime(resultEndTime[1]) + ", ";
                    previousDate = resultStartTime[0];
                    firstRun = false;
                }else{
                    //check if the date is the same (e.g. check if temp is 2017-01-10 against the result)
                    if(previousDate.equals(resultStartTime[0])){
                        //same date, new time. So append the time.
                        tempDate = tempDate + stripTime(resultStartTime[1]) + "-" +
                                stripTime(resultEndTime[1]) + ", ";
                    }else{
                        //new date, so add the old date, reset, then re-assign new date
                        dateTimes.add(tempDate);
                        tempDate = "";
                        tempDate = resultStartTime[0] + ": " + stripTime(resultStartTime[1]) + "-" +
                                stripTime(resultEndTime[1]) + ", ";

                        previousDate = resultStartTime[0];

                    }
                }
            } // end while
        } finally {
            rs.close();
        }

        //dump data
        dateTimes.add(tempDate);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                dateTimes );

        lvTutorScheduleListView.setAdapter(arrayAdapter);

    }

    /**
     * Strips the leading zeros and seconds from a given time String.
     * @param time To convert from
     * @return convertedNewTime a stripped version of the original time argument
     */
    public String stripTime(String time){
        int index = 0;
        char[] splitTime = time.toCharArray();

        //strip leading zero
        for(char c : splitTime){
            //remove leading zero
            if(index == 0 && c == '0'){
                splitTime[index] = ' ';
                break;
            }
            index++;
        }

        //initialize new array minus the seconds (:ss part of time String)
        char[] newTime = new char[splitTime.length - 3];
        for(int i = 0;i<newTime.length;i++){
            newTime[i] = splitTime[i];
        }

        //convert char array to String
        String convertedNewTime = String.valueOf(newTime);
        return convertedNewTime;
    }
}
