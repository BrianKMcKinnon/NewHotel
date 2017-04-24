/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author brian
 */

public class ReservationDatabase 
{
    
    // Variables
    private Connection connection = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Class Constructor
     */
    public ReservationDatabase(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/RESERVATIONS");
           
        }catch(Exception e){
            System.out.println("Error opening connections: " + e);
            e.printStackTrace();
        }
    }
        
    /**
     * Searches database for taken rooms
     * @param startDate
     * @param endDate
     * @return ArrayList of Rooms
     */
    public ArrayList<Room> queryDatabase(Calendar startDate, Calendar endDate){
        ArrayList<Room> takenRooms = new ArrayList<>();
        
        String s_startDate = dateFormat.format(startDate.getTime());
        String s_endDate = dateFormat.format(endDate.getTime());
        
        String query = "SELECT * FROM RESERVATIONS WHERE "
                + "(STARTDATE <= '" + s_startDate + "') AND "
                + "(ENDDATE >= '" + s_endDate + "')";
        
        if(connection != null){
            try{
                Statement statement= connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                
                while(resultSet.next())
                    takenRooms.add(new Room(resultSet.getInt(4), Room.RoomType.valueOf(resultSet.getInt(9))));
            }
            catch(Exception e){
                e.printStackTrace();
            }
           
        }
        
        return takenRooms;
    }
    
    /**
     * Searches database for reservations
     * @param startStay
     * @param endStay
     * @return ArrayList of all booked reservations
     */
    public ArrayList<Reservation> findReservations(Calendar startStay, Calendar endStay) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        
        String startQuery = dateFormat.format(startStay.getTime());
        String endQuery = dateFormat.format(endStay.getTime());
        
        String query = "SELECT * FROM RESERVATIONS WHERE (STARTDATE BETWEEN '" + startQuery + "' AND '" + endQuery + "') OR "
                + "(ENDDATE BETWEEN '" + startQuery + "' AND '" + endQuery + "')";
        
        if(connection != null) {
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                
                while(resultSet.next()) {
                    String delims = "-";
                    String[] start = resultSet.getString(2).split(delims);
                    String[] end = resultSet.getString(3).split(delims);
                    
                    int startYear = Integer.parseInt(start[0]);
                    int startMonth = Integer.parseInt(start[1]) - 1;
                    int startDay = Integer.parseInt(start[2]);
                    int endYear = Integer.parseInt(end[0]);
                    int endMonth = Integer.parseInt(end[1]) - 1;
                    int endDay = Integer.parseInt(end[2]);
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(startYear, startMonth, startDay);
                    Calendar endDate = Calendar.getInstance();
                    endDate.set(endYear, endMonth, endDay);
                    reservations.add(new Reservation(      new ReservationDatabase(),   // Dummy Reservation Database object
                                                           resultSet.getString(1),      // Reservation Code
                                                           resultSet.getInt(4),         // Room Number
                                                           startDate,                   // Start Date
                                                           endDate,                     // End Date
                                                           resultSet.getString(7),      // First Name
                                                           resultSet.getString(8)));    // Last Name
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        
        return reservations;
    }
    
    /**
     * Returns an ArrayList of booked Rooms
     * @param startDate
     * @param endDate
     * @param roomType
     * @return ArrayList of Rooms
     */
     
    public ArrayList<Room> queryDatabase(Calendar startDate, Calendar endDate, Room.RoomType roomType) {
        ArrayList<Room> takenRooms = new ArrayList<>();
        
        String s_startDate = dateFormat.format(startDate.getTime());
        String s_endDate = dateFormat.format(endDate.getTime());
                
        String query = "SELECT * FROM RESERVATIONS WHERE (((STARTDATE >= '" + s_startDate + "') AND "
                + "(STARTDATE <= '" + s_endDate + "')) OR ((ENDDATE >= '" + s_startDate + "') AND "
                + "(ENDDATE <= '" + s_endDate + "')) OR ((STARTDATE <= '" + s_startDate + "') AND "
                + "(ENDDATE >= '" + s_endDate + "')) OR ((STARTDATE >= '" + s_startDate + "') AND "
                + "(ENDDATE <= '" + s_endDate + "'))) AND (ROOMTYPE = "
                + Room.RoomType.valueOf(roomType) + ")";
        System.out.println(query);        
    
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                                
                while (resultSet.next()) {
                    Room room = new Room(resultSet.getInt(4), Room.RoomType.valueOf(resultSet.getInt("ROOMTYPE")));
                    takenRooms.add(new Room(resultSet.getInt(4), Room.RoomType.valueOf(resultSet.getInt("ROOMTYPE"))));
                    System.out.println(Integer.toString(resultSet.getInt(4)));
                }
            }
            
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return takenRooms;
    }    
    
    public ArrayList<Room> queryDatabase(Calendar startDate, Calendar endDate, int roomNum, String resNum) {
        ArrayList<Room> takenRooms = new ArrayList<>();
        
        String s_startDate = dateFormat.format(startDate.getTime());
        String s_endDate = dateFormat.format(endDate.getTime());
                
        String query = "SELECT * FROM RESERVATIONS WHERE (((STARTDATE >= '" + s_startDate + "') AND "
                + "(STARTDATE <= '" + s_endDate + "')) OR ((ENDDATE >= '" + s_startDate + "') AND "
                + "(ENDDATE <= '" + s_endDate + "')) OR ((STARTDATE <= '" + s_startDate + "') AND "
                + "(ENDDATE >= '" + s_endDate + "')) OR ((STARTDATE >= '" + s_startDate + "') AND "
                + "(ENDDATE <= '" + s_endDate + "'))) AND (ROOMNUMBER = "
                + roomNum + ") AND (RESERVATION != '"
                + resNum + "')";
        System.out.println(query);        
    
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                                
                while (resultSet.next()) {
                    takenRooms.add(new Room(resultSet.getInt(4), Room.RoomType.valueOf(resultSet.getInt("ROOMTYPE"))));
                    System.out.println(Integer.toString(resultSet.getInt(4)));
                }
            }
            
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return takenRooms;
    }
    
    /**
     * Returns Reservation
     * @param reservation
     * @return a reservation object
     */
    public Reservation queryDatabase(String reservation){
        Reservation result = null;
        String query = "SELECT * FROM RESERVATIONS WHERE RESERVATION = '" + reservation + "'";
        
        if(connection != null){
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                
                while(resultSet.next()) {
                    String delims = "-";
                    String[] start = resultSet.getString(2).split(delims);
                    String[] end = resultSet.getString(3).split(delims);
                    
                    int startYear = Integer.parseInt(start[0]);
                    int startMonth = Integer.parseInt(start[1]) - 1;
                    int startDay = Integer.parseInt(start[2]);
                    int endYear = Integer.parseInt(end[0]);
                    int endMonth = Integer.parseInt(end[1]) - 1;
                    int endDay = Integer.parseInt(end[2]);
                    
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(startYear, startMonth, startDay);
                    Calendar endDate = Calendar.getInstance();
                    endDate.set(endYear, endMonth, endDay);
                    
                    result = (new Reservation(             new ReservationDatabase(),   // Dummy Reservation Database object
                                                           resultSet.getString(1),      // Reservation Code
                                                           resultSet.getInt(4),         // Room Number
                                                           startDate,                   // Start Date
                                                           endDate,                     // End Date
                                                           resultSet.getString(7),      // First Name
                                                           resultSet.getString(8)));    // Last Name
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        
        return result;
    }
    
    /**
     * Returns ArrayList of Rooms with given roomtype
     * @param room
     * @return ArrayList of Rooms
     */
    public ArrayList<Room> queryDatabase(Room.RoomType roomType) {
        //currentReservations.clear();
        ArrayList<Room> takenRooms = null;
        ResultSet rs = null;
        String query = "SELECT * FROM RESERVATIONS WHERE ROOMTYPE = " + roomType.name();
        
        if(connection != null) {
            try {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery(query);
                while(rs.next()){
                    takenRooms.add(new Room(rs.getInt(4), roomType));
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return takenRooms;
    }
    
    /**
     * Returns ArrayList of Reservations with the given names.
     * @param firstName
     * @param lastName
     * @return ArrayList of current reservations
     */
    public ArrayList<Reservation> queryDatabase(String firstName, String lastName){
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM RESERVATIONS WHERE "
                + "FIRSTNAME = '" + firstName + "' AND "
                + "LASTNAME = '" + lastName + "'";
        
        if(connection != null){
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                
                while(resultSet.next()){
                    String delims = "-";
                    String[] start = resultSet.getString(2).split(delims);
                    String[] end = resultSet.getString(3).split(delims);
                    int startYear = Integer.parseInt(start[0]);
                    int startMonth = Integer.parseInt(start[1]) - 1;
                    int startDay = Integer.parseInt(start[2]);
                    int endYear = Integer.parseInt(end[0]);
                    int endMonth = Integer.parseInt(end[1]) - 1;
                    int endDay = Integer.parseInt(end[2]);
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(startYear, startMonth, startDay);
                    Calendar endDate = Calendar.getInstance();
                    endDate.set(endYear, endMonth, endDay);
                    reservations.add(new Reservation(             new ReservationDatabase(),   // Dummy Reservation Database object
                                                           resultSet.getString(1),      // Reservation Code
                                                           resultSet.getInt(4),         // Room Number
                                                           startDate,                   // Start Date
                                                           endDate,                     // End Date
                                                           resultSet.getString(7),      // First Name
                                                           resultSet.getString(8)));    // Last Name
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return reservations;
    }
    
    /**
     * Makes a reservation with the given Reservation object
     * @param reservation 
     */
    public void makeReservation(Reservation reservation){
        String startDate = dateFormat.format(reservation.getStartDate().getTime());
        String endDate = dateFormat.format(reservation.getEndDate().getTime());       

        String query = "INSERT INTO RESERVATIONS VALUES (" 
                + "'" + reservation.getReservationCode() 
                + "','"  + startDate
                + "','" + endDate
                + "',"  + Integer.toString(reservation.getRoom().getRoomNumber())
                + ","   + Double.toString(reservation.getRoom().getCost())
                + ","   + Double.toString(reservation.getReservationTotal())
                + ",'"  + reservation.getFirstName()
                + "','" + reservation.getLastName()
                + "'," + Room.RoomType.valueOf(reservation.getRoom().getRoomType())
                + ")";
        
        System.out.println(query);
               
        if(connection != null){
            try{
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Changes/Modifies a reservation
     * @param reservation 
     */
    public void changeReservation(Reservation reservation){
        String startDate = dateFormat.format(reservation.getStartDate().getTime());
        String endDate = dateFormat.format(reservation.getEndDate().getTime());
        
        String query = "UPDATE RESERVATIONs SET STARTDATE = '" + startDate
                + "', ENDDATE = '"  + endDate
                + "', ROOMNUMBER = "+ Integer.toString(reservation.getRoom().getRoomNumber())
                + ", RATE = "       + Double.toString(reservation.getRoom().getCost())
                + ", TOTAL = "      + Double.toString(reservation.getReservationTotal())
                + ", FIRSTNAME = '" + reservation.getFirstName()
                + "', LASTNAME = '"  + reservation.getLastName() 
                + "' WHERE RESERVATION = '" + reservation.getReservationCode() + "'";
        
        if(connection != null){
            try{
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Deletes a reservation
     * @param resCode 
     */
    public void deleteReservation(String resCode){
        String query = "DELETE FROM RESERVATIONS WHERE RESERVATION = '" + resCode + "'";
        if(connection != null){
            try{
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
}
