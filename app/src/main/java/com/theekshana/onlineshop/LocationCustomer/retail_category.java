package com.theekshana.onlineshop.LocationCustomer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.select_cate_list;

import java.util.ArrayList;


public class retail_category extends Fragment {

    TabLayout tabLayout;
    ViewPager cateBackground;
    LinearLayout women,men,kid;
    TextView womenTv,menTv,kidsTv,women_clothing;
    TextView new_women,women_shoes,menNew,menClothing,menShoes,kidNew,KidClothing,kidShoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail_category,container,false);
        women = view.findViewById(R.id.women);
        men = view.findViewById(R.id.men);
        kid = view.findViewById(R.id.kid);
        womenTv = view.findViewById(R.id.womenTv);
        menTv = view.findViewById(R.id.menTv);
        kidsTv = view.findViewById(R.id.kidsTv);


        women_clothing = view.findViewById(R.id.women_clothing);
        new_women = view.findViewById(R.id.new_women);
        women_shoes = view.findViewById(R.id.women_shoes);

        menNew = view.findViewById(R.id.menNew);
        menClothing = view.findViewById(R.id.menClothing);
        menShoes = view.findViewById(R.id.menShoes);

        kidNew = view.findViewById(R.id.kidNew);
        KidClothing = view.findViewById(R.id.KidClothing);
        kidShoes = view.findViewById(R.id.kidShoes);


        womenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWomenCate();
            }
        });

        menTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showMenCate();
            }
        });

        kidsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showKidsCate();
            }
        });

        women_clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","women");
                startActivity(intent);
            }
        });

        new_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","women");
                startActivity(intent);
            }
        });

        women_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","shoes");
                startActivity(intent);
            }
        });

        menNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","men");
                startActivity(intent);
            }
        });

        menClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","men");
                startActivity(intent);
            }
        });

        menShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","shoes");
                startActivity(intent);
            }
        });


        kidNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","kids");
                startActivity(intent);
            }
        });

        KidClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","kids");

                startActivity(intent);
            }
        });

        kidShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), Category_main_page.class);
                intent.putExtra("name","shoes");
                startActivity(intent);
            }
        });

        return view;
    }

    private void showKidsCate() {
        women.setVisibility(View.GONE);
        men.setVisibility(View.GONE);
        kid.setVisibility(View.VISIBLE);
        kidsTv.setBackgroundResource(R.drawable.cate_shape_1);
        menTv.setBackgroundColor(getResources().getColor(android.R.color.white));
        womenTv.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private void showMenCate() {
        women.setVisibility(View.GONE);
        men.setVisibility(View.VISIBLE);
        kid.setVisibility(View.GONE);
        menTv.setBackgroundResource(R.drawable.cate_shape_1);
        womenTv.setBackgroundColor(getResources().getColor(android.R.color.white));
        kidsTv.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private void showWomenCate() {
        women.setVisibility(View.VISIBLE);
        men.setVisibility(View.GONE);
        kid.setVisibility(View.GONE);
        womenTv.setBackgroundResource(R.drawable.cate_shape_1);
        menTv.setBackgroundColor(getResources().getColor(android.R.color.white));
        kidsTv.setBackgroundColor(getResources().getColor(android.R.color.white));
    }


}