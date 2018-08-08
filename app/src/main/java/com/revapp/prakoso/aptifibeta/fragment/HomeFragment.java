package com.revapp.prakoso.aptifibeta.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.revapp.prakoso.aptifibeta.AksesData;
import com.revapp.prakoso.aptifibeta.MainActivity;
import com.revapp.prakoso.aptifibeta.R;



public class HomeFragment extends android.app.Fragment {
    public static ImageView button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (ImageView)getView().findViewById(R.id.buttonAct);
        if(!AksesData.checkAccessibilityenable(getActivity())){
            button.setImageResource(R.drawable.notsafe);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DiaAlertPositif();
                }
            });
        }

        if(AksesData.checkAccessibilityenable(getActivity())){
            button.setImageResource(R.drawable.safe);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DiaAlertNegatif();
                }
            });
        }


    }

    private void DiaAlertPositif(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(true);
        alert.setMessage(R.string.alertPositif);
        alert.setTitle("Aktifkan APTIFI");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
            }
        });
        alert.show();
    }

    private void DiaAlertNegatif(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(true);
        alert.setMessage(R.string.alertNegatif);
        alert.setTitle("Aktifkan APTIFI");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
            }
        });
        alert.show();
    }


}