package com.example.hoteltransylvania.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    private List<HotelRoom> hotelRoomList = new ArrayList<>();
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
        //firebaseEvents = new FirebaseEvents();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelRecyclerView.setAdapter(new RecyclerAdapter(hotelRoomList,this,getActivity()));
        hotelRecyclerView.addItemDecoration(itemDecoration);

        compositeDisposable.add(((MainActivity)getContext()).getAllHotelRooms().subscribe(rxRoomList -> {
            refreshView(rxRoomList);
        }, throwable -> {
            DebugLogger.logError(throwable);

        }));

        DebugLogger.logDebug("Hotel size: "+hotelRoomList.size());
    }


    @OnClick(R.id.logoutHotel)
    public void backFromHotel(){
        compositeDisposable.dispose();
        ((MainActivity)getContext()).clerkLoggedOut();
    }

    @OnClick(R.id.addHotelRoomButton)
    public void createNewRoom(){
        ((MainActivity)getContext()).makeNewRoom(hotelRoomList.size());
        compositeDisposable.add(((MainActivity)getContext()).getAllHotelRooms().subscribe(rxRoomList -> {
            refreshView(rxRoomList);
        }, throwable -> {
            DebugLogger.logError(throwable);

        }));
    }

    @Override
    public void displayHotelRoom(HotelRoom hotelRoom){

        ((MainActivity)getContext()).setCurrentHotelRoomInstance(hotelRoom);
        ((MainActivity)getContext()).openCheckFrag();
    }

    private void refreshView(List<HotelRoom> rxRoomList){
        hotelRoomList = rxRoomList;
        RecyclerAdapter recycleAdaptor = new RecyclerAdapter(rxRoomList, this,getActivity());
        hotelRecyclerView.setAdapter(null);
        hotelRecyclerView.setAdapter(recycleAdaptor);
        recycleAdaptor.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void disposeEverything(){
        compositeDisposable.dispose();
    }
}
