package com.example.hoteltransylvania.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoteltransylvania.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterFragment extends Fragment {

    @BindView(R.id.register_username_edit)
    EditText registerUserName;
    @BindView(R.id.register_password_edit)
    EditText registerPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

    }

    @OnClick(R.id.register_button)
    public void registerClerk(View view){
        String userName = registerUserName.getText().toString().trim();
        String passWord = registerPassword.getText().toString().trim();
        ((MainActivity)getContext()).registerClerk(userName, passWord);
        ((MainActivity)getContext()).backFromRegister();

    }

    @OnClick(R.id.backFButton)
    public void backButton(View view){

        ((MainActivity)getContext()).backFromRegister();
    }


}
