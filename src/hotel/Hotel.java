/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import hotel.GUIFrames.Welcome;

/**
 *
 * @author brian
 */
public class Hotel {

    /**
     * Main Function
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       HotelSystem hotelSystem = HotelSystem.initializeSystem(50);
       Welcome frame = new Welcome();
       frame.setVisible(true);
    }  
}
