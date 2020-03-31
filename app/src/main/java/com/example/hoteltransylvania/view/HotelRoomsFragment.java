package com.example.hoteltransylvania.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteltransylvania.R;
import com.example.hoteltransylvania.adapter.RecyclerAdapter;
import com.example.hoteltransylvania.firebase.FirebaseEvents;
import com.example.hoteltransylvania.model.HotelRoom;
import com.example.hoteltransylvania.util.DebugLogger;
import com.example.hoteltransylvania.viewmodel.HotelViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;


public class HotelRoomsFragment extends Fragment implements RecyclerAdapter.UserClickListener {

    private static List<HotelRoom> hotelRoomList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FirebaseEvents firebaseEvents;
    private HotelViewModel hotelViewModel;

    @BindView(R.id.hotelRecyclerView)
    RecyclerView hotelRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_room, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        //firebaseEvents = new FirebaseEvents();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelRecyclerView.setAdapter(new RecyclerAdapter(hotelRoomList,this,getActivity()));
        hotelRecyclerView.addItemDecoration(itemDecoration);

        refreshView();

        DebugLogger.logDebug("Hotel size2: "+hotelRoomList.size());
    }


    @OnClick(R.id.logoutHotel)
    public void backFromHotel(){
        compositeDisposable.clear();
        ((MainActivity)getContext()).clerkLoggedOut();
    }

    @OnClick(R.id.addHotelRoomButton)
    public void createNewRoom(){

        hotelViewModel.sendNewHotelRoom(hotelRoomList.size());
        compositeDisposable.add(hotelViewModel.getHotelRooms().subscribe(rxRoomList -> {
                hotelRoomList=rxRoomList;
        }, throwable -> {
            DebugLogger.logError(throwable);

        }, ()->{
            DebugLogger.logDebug("Hotel size1: "+hotelRoomList.size());

            refreshView();
                }

        ));
    }


    @Override
    public void displayHotelRoom(HotelRoom hotelRoom){
        hotelViewModel.setCurrentHotelRoomInstance(hotelRoom);

        ((MainActivity)getContext()).openCheckFrag();
    }

    public void refreshView(){
        compositeDisposable.add(hotelViewModel.getHotelRooms().subscribe(rxRoomList -> {
            hotelRoomList=rxRoomList;
            RecyclerAdapter recycleAdaptor = new RecyclerAdapter(rxRoomList, this,getActivity());
            hotelRecyclerView.setAdapter(null);
            hotelRecyclerView.setAdapter(recycleAdaptor);
            recycleAdaptor.notifyDataSetChanged();
            DebugLogger.logDebug("Hotel size1: "+hotelRoomList.size());

            }, throwable -> DebugLogger.logError(throwable)));
    }



    public void disposeEverything(){
        compositeDisposable.dispose();
    }
}
