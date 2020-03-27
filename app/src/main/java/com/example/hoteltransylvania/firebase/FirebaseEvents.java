package com.example.hoteltransylvania.firebase;


import androidx.annotation.NonNull;

import com.example.hoteltransylvania.model.Booking;
import com.example.hoteltransylvania.model.Clerk;
import com.example.hoteltransylvania.model.HotelRoom;
import com.example.hoteltransylvania.util.DebugLogger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;


public class FirebaseEvents {
    private DatabaseReference bookingReference;
    private DatabaseReference hotelReference;
    private DatabaseReference clerkReference;

    public FirebaseEvents(){
        bookingReference = FirebaseDatabase.getInstance().getReference().child("Booking/");
        hotelReference = FirebaseDatabase.getInstance().getReference().child("HotelRoom/");
        clerkReference = FirebaseDatabase.getInstance().getReference().child("Clerk/");
    }

    public Observable<List<Booking>> getBookings(){
        List<Booking> bookings = new ArrayList<>();
        bookingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot currentSnap : dataSnapshot.getChildren()){
                    Booking currentBooking = currentSnap.getValue(Booking.class);
                    bookings.add(currentBooking);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //DebugLogger.logError(databaseError);
            }
        });
        return Observable.just(bookings);
    }

    public void sendNewBooking(Booking booking){
        String bookingKey = bookingReference.push().getKey();
        if (bookingKey!=null)
            bookingReference.child(bookingKey).setValue(booking);

    }


    public Observable<List<HotelRoom>> getHotelRooms(){
        List<HotelRoom> hotelRooms = new ArrayList<>();
        hotelReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot currentSnap : dataSnapshot.getChildren()){
                    HotelRoom currentHotelRoom = currentSnap.getValue(HotelRoom.class);
                    hotelRooms.add(currentHotelRoom);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //todo
            }
        });
        return Observable.just(hotelRooms);
    }

    public void sendNewHotelRoom(int size){
        String hotelKey = hotelReference.push().getKey();

        String url = "https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
        Random random = new Random();
        int roomNumber = size+1;
        int randomNumber = random.nextInt(901)+100;
        HotelRoom hotelRoom = new HotelRoom(hotelKey,roomNumber,true,url,randomNumber);

        if (hotelKey!=null)
            hotelReference.child(hotelKey).setValue(hotelRoom);

    }

    public void updateHotelItem(HotelRoom hotelRoom){
        DatabaseReference updateReference = hotelReference.child(hotelRoom.getHotelPushKey());
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("available",hotelRoom.isAvailable());
        updateReference.updateChildren(userUpdates);
    }

    public Observable<List<Clerk>> getClerks(){
        List<Clerk> clerks = new ArrayList<>();
        clerkReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot currentSnap : dataSnapshot.getChildren()){
                    Clerk currentClerk = currentSnap.getValue(Clerk.class);
                    clerks.add(currentClerk);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //todo
            }
        });
        return Observable.just(clerks);
    }

    public void sendNewClerks(Clerk clerk){
        String clerkKey = clerkReference.push().getKey();
        if (clerkKey!=null)
            clerkReference.child(clerkKey).setValue(clerk);

    }


}
