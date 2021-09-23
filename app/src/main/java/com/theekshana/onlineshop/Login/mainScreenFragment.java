package com.theekshana.onlineshop.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.theekshana.onlineshop.R;

public class mainScreenFragment extends Fragment {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    MaterialButton createaccount,login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mainscreen, container, false);
        createaccount = view.findViewById(R.id.create);
        login = view.findViewById(R.id.loginquickly);

        //open create account
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), creataccount.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(view.findViewById(R.id.create), "transition_login");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }


            }
        });

        //login open
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), loginuser.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(view.findViewById(R.id.loginquickly), "transition_login");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });
        return view;
    }

   

    private void openCreatAccount(View view) {

//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = new createAccountFragment();
//        fragmentTransaction.add(R.id.contanier,fragment,"createaccount");
//        fragmentTransaction.commit();


    }
}
