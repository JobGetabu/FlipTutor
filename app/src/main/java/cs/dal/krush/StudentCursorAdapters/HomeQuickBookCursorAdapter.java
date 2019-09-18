package cs.dal.krush.StudentCursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;

import cs.dal.krush.R;

/**
 * This is the adapter class for the customized rows in the 'Quick Book a Tutor'
 * list view on the student home page.
 *
 * Source:
 * [4] C. (n.d.). Codepath/android_guides. Retrieved March 12, 2017,
 * from https://github.com/codepath/android_guides/wiki/Populating-a-ListView-with-a-CursorAdapter
 */
public class HomeQuickBookCursorAdapter extends CursorAdapter {

    // Currency formatter for tutor rate
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    //store the context set in the constructor
    private Context mContext;

    /**
     * Constructor that initializes the custom cursor adapter.
     *
     * @param context
     * @param cursor
     */
    public HomeQuickBookCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
        this.mContext = context;
    }

    /**
     * Overrides the newView method used to inflate a new view and return it.
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.student_booking_listentry, parent, false);
    }

    /**
     * Overrides the bindView method used to bind all data to a given view.
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        //fetch UI components:
        TextView header = (TextView)view.findViewById(R.id.firstLine);
        TextView subHeader = (TextView)view.findViewById(R.id.secondLine);
        ImageView image = (ImageView)view.findViewById(R.id.icon);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);

        //Get the tutor's profile image:
        String imageFileName = cursor.getString(cursor.getColumnIndexOrThrow("profile_pic"));
        if(imageFileName != null && !imageFileName.isEmpty()) {
            Bitmap profile_pic = BitmapFactory.decodeFile(imageFileName);
            image.setImageBitmap(profile_pic);
        }

        //Set the row's header text:
        String tutorFirstName = cursor.getString(cursor.getColumnIndexOrThrow("f_name"));
        String tutorLastName = cursor.getString(cursor.getColumnIndexOrThrow("l_name"));
        String tutorName = tutorFirstName + " " + tutorLastName;
        header.setText(tutorName);

        //Set the row's sub-header text:
        Float tutorRate = cursor.getFloat(cursor.getColumnIndexOrThrow("rate"));
        String tutorRating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));
        String text2content = currencyFormatter.format(tutorRate) + " per half hour";
        subHeader.setText(text2content);
        ratingBar.setRating(Float.parseFloat(tutorRating));
    }
}
