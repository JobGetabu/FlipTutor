package cs.dal.krush.tutorFragments;


import android.database.Cursor;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * Tutor calendar view.
 * The calendar functionality is provided by:
 * https://github.com/alamkanak/Android-Week-View/tree/master/sample/src/main/java/com/alamkanak/weekview/sample
 *
 */
public class TutorCalendarFragment extends Fragment {

    /**
     * Declare variables
     */
    private WeekView mWeekView;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.tutor_calendar_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        mWeekView = (WeekView) getView().findViewById(R.id.weekView);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {

            }
        });

        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                //get all of the tutoring sessions from the db
                //format the month
                //look in the db for records that have this month

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                Calendar calendarStartDate = new GregorianCalendar();
                calendarStartDate.set(Calendar.MONTH, newMonth-1);
                calendarStartDate.set(Calendar.YEAR, newYear);
                calendarStartDate.set(Calendar.HOUR_OF_DAY, 0);
                calendarStartDate.set(Calendar.MINUTE,0);
                calendarStartDate.set(Calendar.DAY_OF_MONTH,1);
                String compareStartDate = formatter.format(calendarStartDate.getTime());

                Calendar calendarEndDate = new GregorianCalendar();
                calendarEndDate.set(Calendar.MONTH, newMonth-1);
                calendarEndDate.set(Calendar.YEAR, newYear);
                calendarEndDate.set(Calendar.HOUR_OF_DAY, 23);
                calendarEndDate.set(Calendar.MINUTE,59);
                calendarEndDate.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
                String compareEndDate = formatter.format(calendarEndDate.getTime());

                Cursor rs;
                db = new DBHelper(getContext());
                rs = db.tutoringSession.getDataBySchedule(1);

                int i = 1;
                try {
                    while (rs.moveToNext()) {
                        Calendar convertedStartTime = Calendar.getInstance();
                        Calendar convertedEndTime = Calendar.getInstance();

                        Date creationDate = formatter.parse(rs.getString(rs.getColumnIndex("start_time")));
                        Date enDate = formatter.parse(rs.getString(rs.getColumnIndex("end_time")));

                        convertedStartTime.setTime(creationDate);
                        convertedEndTime.setTime(enDate);
                        WeekViewEvent event = new WeekViewEvent(i, "", convertedStartTime, convertedEndTime);
                        events.add(event);
                        i++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    rs.close();
                }

                return events;
            }
        };

        mWeekView.setMonthChangeListener(mMonthChangeListener);

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Get the title of a specific event
     * @param time
     * @return formatted title
     */
    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

}
