package com.example.hoteltransylvania.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;

import com.example.hoteltransylvania.R;
import com.example.hoteltransylvania.model.Booking;
import com.example.hoteltransylvania.model.Clerk;
import com.example.hoteltransylvania.model.HotelRoom;
import com.example.hoteltransylvania.util.DebugLogger;
import com.example.hoteltransylvania.viewmodel.HotelViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.username_edittext)
    EditText usernameEditText;

    @BindView(R.id.password_edittext)
    EditText passwordEditText;

    private HotelViewModel hotelViewModel;
    private HotelRoomsFragment hotelRooms;
    private RegisterFragment registerFragment;
    private BookingHotelRoomFragment bookingHotelRoomFragment;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        hotelRooms = new HotelRoomsFragment();
        registerFragment = new RegisterFragment();
        bookingHotelRoomFragment = new BookingHotelRoomFragment();
    }


    @OnClick(R.id.login_button)
    public void loginClick(View view) {
        String clerkName = usernameEditText.getText().toString().trim();
        String clerkPassword = passwordEditText.getText().toString().trim();

        compositeDisposable.add(hotelViewModel.getClerks().subscribe(clerks -> {
            for(Clerk singleClerk : clerks) {
                if (singleClerk.getClerkUserName().equals(clerkName) && singleClerk.getClerkPassword().equals(clerkPassword)){
                    DebugLogger.logDebug("Testing");
                    hotelViewModel.setCurrentClerk(singleClerk);
                clerkLoginSuccess();
                }
                else{
                    clerkLoginFailed();
                }
            }
        }));
    }

    public void clerkLoginSuccess() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, hotelRooms)
                .commit();
    }

    @OnClick(R.id.new_clerk_button)
    public void registerClick(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame,registerFragment)
                .commit();
    }

    public void clerkLoginFailed() {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme))
                .setTitle("Login failed")
                .setMessage("Username or password incorrect. Please re-enter.")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                    }
                })
                .create()
                .show();
    }

    public void registerClerk(String userName, String passWord){
        hotelViewModel.sendNewClerks(userName,passWord);
    }


    public void clerkLoggedOut() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(hotelRooms)
                .commit();
    }

    public void backFromRegister() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(registerFragment)
                .commit();
    }

    public void backFromBooking() {
        hotelRooms.refreshView();
        getSupportFragmentManager()
                .beginTransaction()
                .remove(bookingHotelRoomFragment)
                .commit();
    }

    public void openCheckFrag() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bookingFrame, bookingHotelRoomFragment)
                .commit();
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        hotelRooms.disposeEverything();
        bookingHotelRoomFragment.disposeEverything();
    }

}
