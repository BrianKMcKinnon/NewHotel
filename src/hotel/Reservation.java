/**
 *
 * @author Atchima
 */

package hotel;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Random;

public class Reservation {
    
    // Variables 
    private String reservationCode;
    private final Room room;
    private final Calendar startDate;
    private final Calendar endDate;
    private String firstName;
    private String lastName;
    
    /**
     * Class constructor
     * @param room
     * @param startDate
     * @param endDate
     * @param firstName
     * @param lastName
     */
    public Reservation(Room room, Calendar startDate, Calendar endDate, String firstName, String lastName) {
        reservationCode = generateReservationCode();
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Class constructor, should only be used by database to recreate a reservation
     * object from a string.
     * @param resdata   Dummy parameter to ensure correct usage
     * @param reservationCode
     * @param roomNumber
     * @param startDate
     * @param endDate
     * @param firstName
     * @param lastName 
     */
    public Reservation(ReservationDatabase resdata, String reservationCode, int roomNumber, Calendar startDate, Calendar endDate, String firstName, String lastName) {
        if (resdata instanceof ReservationDatabase) {
            this.reservationCode = reservationCode;
            this.room = HotelSystem.getInstance().getAllRooms().get(roomNumber - 1);
            this.startDate = startDate;
            this.endDate = endDate;
            this.firstName = firstName;
            this.lastName = lastName;
        }
        
        else {
                reservationCode = generateReservationCode();
                this.room = HotelSystem.getInstance().getAllRooms().get(roomNumber - 1);
                this.startDate = startDate;
                this.endDate = endDate;
                this.firstName = firstName;
                this.lastName = lastName;
        }
    }
    
    /**
     * Obtains reservation code.
     * @return a reservation code
     */
    public String getReservationCode() {   
        return reservationCode;
    }    

     /**
     * Obtains a room object for the specific reservation
     * @return a room object
     */
    public Room getRoom() {
        return room;
    }
    
    /**
     * Obtains a start date of the specific reservation
     * @return a start date
     */
    public Calendar getStartDate() {
        Calendar clone = Calendar.getInstance();
        clone = (Calendar)startDate.clone();
        return clone;   //return shallow copy of date
    }
    
    /**
     * Obtains an end date of the specific reservation
     * @return an ending date
     */
    public Calendar getEndDate() {
        Calendar clone = Calendar.getInstance();
        clone = (Calendar)endDate.clone();
        return clone;
    }
    
    /**
     * Obtains the duration of stay, end - start date
     * @return duration of stay
     */
    public int getDurationOfStay() {
        return (int) ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }
    
    
    /**
     * Obtains first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Obtains last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets both guest names
     * @param fName
     * @param lName 
     */
    public void setName(String fName, String lName) {
        firstName = fName;
        lastName = lName;
    }
    
    /**
     * Obtains the total cost of the reservation
     * @return total room
     */
    public double getReservationTotal() {
        return getDurationOfStay() * room.getCost();
    }
    
    
    /**
     * Generates a random numeric string of 6 digits
     * @return a reservation code
     */
    private String generateRandom() {        
        Random random = new Random();
        int n = 100000 + random.nextInt(900000);

        return Integer.toString(n);
    }
    
    /**
     * Generates a reservation code and ensures that it has not 
     * yet been used.
     * @return reservation code
     */
    private String generateReservationCode() {
        String resCode = generateRandom();
        
        while(HotelSystem.getInstance().reservationExist(resCode))
            resCode = generateRandom();
        return resCode;
    }
}
