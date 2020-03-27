package com.example.hoteltransylvania.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Booking {

    private String hotelRoomID;
    private String guestName;

    public Booking(String hotelRoomID, String guestName) {
        this.hotelRoomID = hotelRoomID;
        this.guestName = guestName;
    }
    public Booking(){}

    public String getHotelRoomID() {
        return hotelRoomID;
    }

    public void setHotelRoomID(String hotelRoomID) {
        this.hotelRoomID = hotelRoomID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}