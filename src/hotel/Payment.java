package hotel;

/**
 *
 * @author Chandler Davidson
 */
public class Payment {
    
    /**
     * Class constructor
     */
    public Payment()
    {
        // This block was intentionally left blank.
    }
    
    /**
     * Shows the acknowledgment of a payment class.
     * @param ccNumber
     * @param fName
     * @param lName
     * @param crv
     * @param expMonth
     * @param expYear
     * @return boolean if payment is valid
     */
    public static boolean checkPayment(String fName, String lName, String ccNumber, String crv, int expMonth, int expYear) {        
        return (ccNumber.length() == 16 && fName != "" && lName != "" && crv.length() == 3);
    }
}