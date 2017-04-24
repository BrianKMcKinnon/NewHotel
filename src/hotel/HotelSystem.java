/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author Chandler
 */
public class HotelSystem {
    private final int roomCount;
    private final ArrayList<Room> allRooms;
    private final ReservationDatabase reservationDatabase;
    private static HotelSystem instance = null;
    
    
    /**
     * Private constructor to maintain Singleton design,
     * initializes all Room objects and the reservationDatabase.
     * @param roomCount     total number of rooms
     */
    protected HotelSystem(int roomCount) {
        this.roomCount = roomCount;
        allRooms = new ArrayList<>();
        
        for (int i = 0; i < roomCount; i++) {
            switch (i % 4) {
                case 0:
                    allRooms.add(new Room(i + 1, Room.RoomType.SUITE));
                    break;
                case 1:
                    allRooms.add(new Room(i + 1, Room.RoomType.KING));
                    break;
                case 2:
                    allRooms.add(new Room(i + 1, Room.RoomType.QUEEN));
                    break;
                default:
                    allRooms.add(new Room(i + 1, Room.RoomType.SINGLE));
                    break;
            }
        }
        
        reservationDatabase = new ReservationDatabase();
    }
    
    /**
     * Links to the private constructor
     * @param roomCount     total number of rooms
     * @return single instance of HotelSystem
     */
    public static HotelSystem initializeSystem(int roomCount) {
        if (instance == null)
            instance = new HotelSystem(roomCount);
        return instance;
    }
    
    /**
     * Maintains the Singleton design pattern
     * @return single instance of HotelSystem
     */
    public static HotelSystem getInstance() {
        return instance;
    }
    
    /**
     * Returns the total number of rooms in Hotel
     * @return total number of rooms
     */
    public int getRoomCount() {
        return roomCount;
    }
    
    /**
     * Searches the database for rooms that are booked under the
     * given parameters, compares them to allRooms, then returns 
     * a list of available rooms.
     * @param startDate Check in date
     * @param endDate   Check out date
     * @param roomType  Type of room
     * @return ArrayList of available rooms
     */
    public ArrayList<Room> findAvailableRooms(Calendar startDate, Calendar endDate, Room.RoomType roomType) {
        ArrayList<Room> temp = reservationDatabase.queryDatabase(startDate, endDate, roomType);
        ArrayList<Room> results = allRooms;
        
        for (Room room : temp)
            results.remove(room);
        
        return results;
    }
    
    /**
     * Searches the database for rooms that are booked under the
     * given parameters, compares them to allRooms, then returns 
     * a list of available rooms.
     * @param startDate Check in date
     * @param endDate   Check out date
     * @param roomType  Type of room
     * @return ArrayList of available rooms
     */
    public Room findAvailableRoom(Calendar startDate, Calendar endDate, Room.RoomType roomType) {
        ArrayList<Room> notAvailable = reservationDatabase.queryDatabase(startDate, endDate, roomType);
        ArrayList<Integer> notAvailableRoomNumber = new ArrayList<>();
        
        for (Room room : notAvailable) {
            notAvailableRoomNumber.add(room.getRoomNumber());
        }
        
        for (Room room : allRooms) {
            if (room.roomType == roomType && !notAvailableRoomNumber.contains(room.getRoomNumber()))
                return room;
            }
        
        return null;
    }
    
    /**
     * Queries the database for the given reservation
     * code.
     * @param reservationCode
     * @return Reservation
     */
    public Reservation lookUpReservation(String reservationCode) {
        return reservationDatabase.queryDatabase(reservationCode);
    }
    
    /**
     * Queries the database for reservations within the given
     * parameters.
     * @param startDate
     * @param endDate
     * @return ArrayList of Reservations
     */
    public ArrayList<Reservation> lookUpReservation(Calendar startDate, Calendar endDate) {
        return reservationDatabase.findReservations(startDate, endDate);
    }
    
    /**
     * Queries the database for reservations within the given
     * parameters.
     * @param firstName
     * @param lastName
     * @return ArrayList of Reservations
     */
    public ArrayList<Reservation> lookUpReservation(String firstName, String lastName) {
        return reservationDatabase.queryDatabase(firstName, lastName);
    }
    
    /**
     * Modifies the reservation in the database.
     * @param reservation 
     */
    public void changeReservation(Reservation reservation) {
        reservationDatabase.changeReservation(reservation);
    }
    
    /**
     * Creates the reservation in the database.
     * @param reservation 
     */
    public void makeReservation(Reservation reservation){
        reservationDatabase.makeReservation(reservation);
    }
    
    /**
     * Removes the reservation in the database.
     * @param reservation 
     */
    public void removeReservation(Reservation reservation) {
        reservationDatabase.deleteReservation(reservation.getReservationCode());
    }    
    
    /**
     * Queries the database for the reservation.
     * @param reservation
     * @return boolean if the reservation exists
     */
    public boolean reservationExist(String reservation) {
        return (reservationDatabase.queryDatabase(reservation) != null);
    }  

    /**
     * Returns all the rooms
     * @return ArrayList of room objects
     */
    public ArrayList<Room> getAllRooms() {
        return allRooms;
    }
}
