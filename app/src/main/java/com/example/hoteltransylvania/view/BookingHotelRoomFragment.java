package com.example.hoteltransylvania.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hoteltransylvania.R;
import com.example.hoteltransylvania.model.Booking;
import com.example.hoteltransylvania.model.HotelRoom;
import com.example.hoteltransylvania.util.DebugLogger;
import com.example.hoteltransylvania.viewmodel.HotelViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BookingHotelRoomFragment extends Fragment {
    private HotelRoom hotelRoom;
    private Booking booking;
    private HotelViewModel hotelViewModel;
    private static List<Booking> bookingList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @BindView(R.id.bookingImage)
    ImageView bookingImage;
    @BindView(R.id.roomNumberView)
    TextView roomNumberView;
    @BindView(R.id.roomAvailabilityView)
    TextView roomAvailabilityView;
    @BindView(R.id.priceView)
    TextView priceView;
    @BindView(R.id.guestNameView)
    TextView guestNameView;
    @BindView(R.id.namedEdit)
    EditText namedEdit;

    @BindView(R.id.backFButton)
    Button backButton;
    @BindView(R.id.addBookingButton)
    Button addBookingButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_hotel_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,view);
        DebugLogger.logDebug("view created");

        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        hotelRoom = hotelViewModel.getHotelRoomInstance();

        //Glide.with(this).load(hotelRoomEntity.getImageUrl()).into(bookingImage);
        roomNumberView.setText("Room Number: "+hotelRoom.getHotelRoomNumber());
        roomAvailabilityView.setText("Availability: "+hotelRoom.isAvailable());
        priceView.setText("Price: "+hotelRoom.getPrice());


        if (!hotelRoom.isAvailable()){
            addBookingButton.setEnabled(false);
            namedEdit.setVisibility(View.INVISIBLE);
            namedEdit.setEnabled(false);
            guestNameView.setVisibility(View.VISIBLE);
            guestNameView.setEnabled(true);

            compositeDisposable.add(hotelViewModel.getBookings().subscribe(rxBookingList -> {
                DebugLogger.logDebug("test room: "+rxBookingList.size());

                unavailableRoom(rxBookingList);

                    }, throwable -> {
                DebugLogger.logError(throwable);

            }));

            Log.d("TAG_H","false: "+namedEdit.getText().toString().trim());
        }
        else
            guestNameView.setText("");

    }



    public void unavailableRoom(List<Booking> rxBookingList) {

        //DebugLogger.logDebug("Unavailable room: "+rxBookingList.size()+"");

        for (int i=0; i<rxBookingList.size();i++)
        {
            //DebugLogger.logDebug("Stuff:" +rxBookingList.get(i).getGuestName());

            if (rxBookingList.get(i).getHotelRoomID().equals(hotelRoom.getHotelPushKey()))
                booking = rxBookingList.get(i);
        }
        guestNameView.setText("Guest: " + booking.getGuestName());

    }

    @OnClick(R.id.addBookingButton)
    public void addBooking(){
        Log.d("TAG_H", "Booking entered:" +namedEdit.getText().toString().trim());
        hotelRoom.setAvailable(false);

        hotelViewModel.updateHotelItem(hotelRoom);
        hotelViewModel.sendNewBooking(hotelRoom.getHotelPushKey(),namedEdit.getText().toString().trim());
        namedEdit.setText("");
        ((MainActivity)getContext()).backFromBooking();
    }

    @OnClick(R.id.backFButton)
    public void backToPrevious(View view){
        compositeDisposable.clear();

        ((MainActivity)getContext()).backFromBooking();

    }


    public void disposeEverything(){
        compositeDisposable.dispose();
    }

}
