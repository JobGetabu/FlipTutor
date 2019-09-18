package cs.dal.krush.helpers;


/**
 * This function contains all validations done on user inputs
 */
public class ValidationHelper {

    /**
     * Validates if email is of valid format or not
     *
     * Source:
     * [1]"How to get all possible combinations of a list’s elements?",
     * Stackoverflow.com, 2017. [Online].
     * Available: http://stackoverflow.com/questions/464864/how-to-get-all-possible-combinations-
     * of-a-list-s-elements. [Accessed: 08- Mar- 2017].
     *
     * @param email
     * @return true if email is valid
     */
    public static boolean Email_Validate(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
