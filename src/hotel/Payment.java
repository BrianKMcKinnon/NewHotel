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
        // This page was intentionally left blank.
    }
    
    /**
     * Returns whether or not the credit card number is valid (valid if has 16 digits)
     * @param creditCard    String of digits of a credit card
     * @return boolean if payment is valid
     */
    public static boolean checkPayment(String creditCard){
        return (creditCard.length() == 16);
    }
}