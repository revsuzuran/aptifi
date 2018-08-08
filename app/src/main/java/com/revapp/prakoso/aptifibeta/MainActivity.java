package com.revapp.prakoso.aptifibeta;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.revapp.prakoso.aptifibeta.fragment.GearFragment;
import com.revapp.prakoso.aptifibeta.fragment.HomeFragment;
import com.revapp.prakoso.aptifibeta.fragment.InfoFragment;
import com.revapp.prakoso.aptifibeta.fragment.KeyFragment;
import com.revapp.prakoso.aptifibeta.fragment.StarFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static Typeface fontz;
    public ImageView home,setting,star,lock,info;
    public TextView indexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String customfont = "fonts/RobotoThin.ttf";
        fontz = Typeface.createFromAsset(this.getAssets(), customfont);
        indexView = (TextView)findViewById(R.id.index);

        home = (ImageView) findViewById(R.id.hom);
        home.setOnClickListener(this);
        setting = (ImageView) findViewById(R.id.gear);
        setting.setOnClickListener(this);
        star = (ImageView) findViewById(R.id.bintang);
        star.setOnClickListener(this);
        lock = (ImageView) findViewById(R.id.kunci);
        lock.setOnClickListener(this);
        info = (ImageView) findViewById(R.id.inf);
        info.setOnClickListener(this);
        MiuiStatusBar(MainActivity.this, true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.fragmentArea, homeFragment, "dasar").commit();
    }

    public static boolean MiuiStatusBar(Activity activity, boolean dark){
        Class<? extends Window> klas = activity.getWindow().getClass();
        try{
            int darkFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkFlag = field.getInt(layoutParams);
            Method  extraFlag = klas.getMethod("setExtraFlags", int.class, int.class);
            extraFlag.invoke(activity.getWindow(), dark ? darkFlag : 0, darkFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hom :
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragmentArea, homeFragment, "dasar").commit();
                home.setSelected(true);
                info.setSelected(false);
                setting.setSelected(false);
                lock.setSelected(false);
                star.setSelected(false);
                indexView.setText("");
                break;
            case R.id.inf :
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                InfoFragment infoFragment = new InfoFragment();
                fragmentTransaction.replace(R.id.fragmentArea, infoFragment, "inf").commit();
                info.setSelected(!info.isSelected());
                setting.setSelected(false);
                lock.setSelected(false);
                star.setSelected(false);
                home.setSelected(false);
                indexView.setText("About");
                break;
            case R.id.bintang :
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                StarFragment starFragment = new StarFragment();
                fragmentTransaction.replace(R.id.fragmentArea, starFragment, "bintang").commit();
                star.setSelected(!star.isSelected());
                setting.setSelected(false);
                lock.setSelected(false);
                info.setSelected(false);
                home.setSelected(false);
                indexView.setText("Costum");
                break;
            case R.id.gear :
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                GearFragment gearFragment = new GearFragment();
                fragmentTransaction.replace(R.id.fragmentArea, gearFragment, "gear").commit();
                setting.setSelected(!setting.isSelected());
                lock.setSelected(false);
                info.setSelected(false);
                star.setSelected(false);
                home.setSelected(false);
                indexView.setText("Setting");
                break;
            case R.id.kunci :
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                KeyFragment keyFragment1 = new KeyFragment();
                fragmentTransaction.replace(R.id.fragmentArea, keyFragment1, "key").commit();
                lock.setSelected(!lock.isSelected());
                setting.setSelected(false);
                info.setSelected(false);
                star.setSelected(false);
                home.setSelected(false);
                indexView.setText("Mode");
                break;
        }

    }

}
