/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

/**
 *
 * @author Atchima
 */
public class Room {

    // Variables
    private final int roomNumber;
    private final double roomCost;
    public final RoomType roomType;

    /**
     * Enumerator as a description of the room
     */
    public enum RoomType {
        SUITE, KING, QUEEN, SINGLE;

        /**
         * Obtains given roomType as an int
         * @param roomType
         * @return 
         */
        public static int valueOf(RoomType roomType) {
            switch (roomType) {
                case SUITE:
                    return 0;
                case KING:
                    return 1;
                case QUEEN:
                    return 2;
                case SINGLE:
                    return 3;
                default:
                    return 3;
            }
        }

        /**
         * Obtains the name of room roomType
         *
         * @param input
         * @return the name of room roomType
         */
        public static RoomType valueOf(int input) {
            switch (input) {
                case 0:
                    return RoomType.SUITE;
                case 1:
                    return RoomType.KING;
                case 2:
                    return RoomType.QUEEN;
                case 3:
                    return RoomType.SINGLE;
                default:
                    return RoomType.SINGLE;
            }
        }
    }

    /**
     * Class constructor
     *
     * @param roomNumber
     * @param type
     */
    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.roomType = type;
        this.roomCost = setCost();
    }

    private int setCost() {
        switch (roomType) {
            case SUITE:
                return 300;
            case KING:
                return 200;
            case QUEEN:
                return 150;
            case SINGLE:
                return 100;
            default:
                return 100;
        }
    }

    /**
     * Obtains a room number
     *
     * @return room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Obtains a room cost
     *
     * @return room cost
     */
    public double getCost() {
        return roomCost;
    }

    /**
     * Obtains a roomType of a room
     *
     * @return roomType of a room
     */
    public RoomType getRoomType() {
        return roomType;
    }
}
