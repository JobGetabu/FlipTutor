package cs.dal.krush.adapters;

/**
 *  Represents a single row item in the CustomTutorDayTimeAdapter class. This may
 *  also be used to represent items in a ViewHolder, or an ArrayList to attach to adapters.
 */

public class TutorDayTimeRowitem {

    /**
     * Instance variables
     */
    private String text;

    /**
     * Overloaded constructor
     * @param text time for the row item
     */
    public TutorDayTimeRowitem(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text + "\n" + text;
    }
}
