package com.example.hoteltransylvania.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.hoteltransylvania.firebase.FirebaseEvents;
import com.example.hoteltransylvania.model.Booking;
import com.example.hoteltransylvania.model.Clerk;
import com.example.hoteltransylvania.model.HotelRoom;
import com.example.hoteltransylvania.view.MainActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotelViewModel extends AndroidViewModel {
    private FirebaseEvents firebaseEvent;
    private static HotelRoom currentHotelRoom;
    private Booking currentBooking;
    private Clerk currentClerk;

    public HotelViewModel(@NonNull Application application) {
        super(application);
        firebaseEvent = new FirebaseEvents();
        firebaseEvent.setBookings();
        firebaseEvent.setClerks();
        firebaseEvent.setHotelRooms();
    }

    public Observable<List<Booking>> getBookings() {
        return firebaseEvent.getBookings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<HotelRoom>> getHotelRooms() {
        return firebaseEvent.getHotelRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Clerk>> getClerks() {
        return firebaseEvent.getClerks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void sendNewBooking(String ID, String name) {
        Booking booking = new Booking(ID, name);
        firebaseEvent.sendNewBooking(booking);
    }

    public void sendNewHotelRoom(int size) {

        firebaseEvent.sendNewHotelRoom(size);
    }

    public void updateHotelItem(HotelRoom hotelRoom){
        firebaseEvent.updateHotelItem(hotelRoom);
    }

    public void sendNewClerks(String userName, String passWord) {
        Clerk clerk = new Clerk(userName,passWord);

        firebaseEvent.sendNewClerks(clerk);
    }

    public void setCurrentClerk(Clerk clerk){
        this.currentClerk = clerk;
    }

    public void setCurrentHotelRoomInstance(HotelRoom hotelRoomInstance) {
        this.currentHotelRoom=hotelRoomInstance;
    }

    public HotelRoom getHotelRoomInstance() {
        return currentHotelRoom;
    }

}
