package com.revapp.prakoso.aptifibeta;

import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
  Created by aqilprakoso on 8/19/17.

 */

public class AksesService extends AccessibilityService{
    public static final String TAG = AksesService.class.getName();
    //public boolean dshow = false;
    public AlertDialog alert;
    public boolean A=false;
    public boolean B=false;
    public boolean C=false;
    public int jumlahapp = 0;
    public int hours;
    public String totalwaktuSocmed = "error";
    //long waktuInfoground = 500;
    //int minutes=500, seconds=500, hours=500;
    //public int a = 0;
    //private Toast myToast = null;

    /*private boolean data(AccessibilityNodeInfo data){
        Log.i(TAG, "test = " + data);
            if (data.getViewIdResourceName().equals("com.android.chrome:id/url_bar")) {
                Log.i(TAG, "test = " + data);

        }
        return  false;
    }*/
    //public static int webAlert = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Berjalan");

        //mUsageStatsManager.queryAndAggregateUsageStats(startTime, endTime);
        // UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService("usagestats");

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Akses Diperbolehkan");
        Resources res = getResources();
        String[] keywords = res.getStringArray(R.array.keyword);
        AccessibilityNodeInfo source = event.getSource();

        //Get SpentTime Of App Instagram
        //UsageStats usageStats;
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY,0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long startTime = date.getTimeInMillis();
        //Long time = new Date().getTime();
        //Date date = new Date(time - time % (24 * 60 * 60 * 1000));
        //long endTime = System.currentTimeMillis();
        date.add(Calendar.DAY_OF_MONTH, 1);
        long endTime = date.getTimeInMillis();
        //String namaApp = usageStats.getPackageName();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);

        if(!C) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (event.getPackageName().equals("com.instagram.android") && !event.getClassName().equals("android.app.AlertDialog")) {

                    if (stats != null) {
                        //  for (int i = 0; i < stats.size(); i++) {
                        // UsageStats usageStats = stats.get(i);
                        for (UsageStats usageStats : stats) {
                            if ("com.instagram.android".equals(usageStats.getPackageName())) {

                                String dateFormat = "dd/MM/yyyy";
                                long lastUsageLong = usageStats.getLastTimeStamp();
                                String lastUsage = new SimpleDateFormat(dateFormat).format(lastUsageLong);
                                long firstUsageLong = usageStats.getFirstTimeStamp();
                                String firstUsage = new SimpleDateFormat(dateFormat).format(firstUsageLong);
                                long timeInForeground = 500;
                                //String packageName;
                                timeInForeground = usageStats.getTotalTimeInForeground();
                                //packageName = usageStats.getPackageName();

                                int minutes = (int) ((timeInForeground / (1000 * 60)) % 60);
                                //int seconds = (int) (timeInForeground / 1000) % 60;
                                hours = (int) ((timeInForeground / (1000 * 60 * 60)) % 24);

                                if (lastUsage.equals(firstUsage)) {
                                    //Log.i(TAG, "PackageName is " + packageName + "Time is: " + hours + " Jam " + minutes + " Menit" + " " + firstUsage + " - " + lastUsage);

                                    // Log.d(TAG, usageStats.getPackageName() + "waktu : " + usageStats.getTotalTimeInForeground() + " | awal :" + usageStats.getFirstTimeStamp() + " | akhir :" + usageStats.getLastTimeStamp());
                                    //PERCOBAAN
                                    //Calendar cal = Calendar.getInstance();
                                    //cal.add(Calendar.HOUR_OF_DAY, -1);

                                    /* Query for events in the given time range. Events are only kept by the system for a few days.
                                     * NOTE: The last few minutes of the event log will be truncated to prevent abuse by applications. */
                                    UsageEvents queryEvents = mUsageStatsManager.queryEvents(startTime, endTime);

                                    ArrayList<String> packagesNames = new ArrayList<>();

                                    // I get a list with package names having an event of "MOVE_TO_FOREGROUND"
                                    while (queryEvents.hasNextEvent()) {
                                        UsageEvents.Event eventAux = new UsageEvents.Event();
                                        queryEvents.getNextEvent(eventAux);
                                        if (eventAux.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                                            packagesNames.add(eventAux.getPackageName());

                                            // Log.d(TAG, getPackageName()+" "+eventAux.MOVE_TO_FOREGROUND + " time " +  eventAux.getTimeStamp() + eventAux.getEventType());
                                        }
                                    }

                                    // I count the occurrences of each packet name.
                                    Set<String> unique = new HashSet<>(packagesNames);
                                    for (String key : unique) {
                                        if (key.equals("com.instagram.android")) {
                                            //Log.d(TAG, key + ": " + Collections.frequency(packagesNames, key));
                                            jumlahapp = Collections.frequency(packagesNames, key);
                                            totalwaktuSocmed = (hours + " Jam " + minutes + " Menit");
                                            C = true;
                                            socmedWarning();
                                        }
                                    }

                                }

                                                            }
                        }
                    }
                }
            }
        }

        //Anti Mobile Legends
        if(!B) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (event.getPackageName().equals("com.mobile.legends")) {
                    B = true;
                    mlwarning();
                    Log.d(TAG, "terciduk");
                }
            }
        }

       /* if(source.getClassName().equals("android.webkit.WebView") && source.getContentDescription().toString().contains("bokep") && source.getPackageName().equals("com.android.chrome")){
            dialogok();
            //myToast = Toast.makeText(AksesService.this, "eta.. terangkanlah..", Toast.LENGTH_LONG);
            //myToast.show();


        }*/
    /*List<AccessibilityNodeInfo> node = source.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar");
    for (AccessibilityNodeInfo anu : node) {
        if (anu.getText().toString().contains("xvideos")) {
            Toast.makeText(AksesService.this, "eta.. terangkanlah..", Toast.LENGTH_LONG).show();

            return;
        }
        Log.d(TAG, "judulnya = " + anu.getText());
        }*/

      /*  List<AccessibilityNodeInfo> aqil = source.findAccessibilityNodeInfosByViewId("rev.hbd.raisaandriana:id/foryou_txt");
        for(AccessibilityNodeInfo rev : aqil){
            Log.d(TAG, "entahlah == "+rev);
            Log.d(TAG, "entahlah Text == "+rev.getText());
        }*/
        /*if(source.getViewIdResourceName()!=null){
            if(source.getViewIdResourceName().equals("rev.hbd.raisaandriana:id/foryou_txt")){
                Toast.makeText(AksesService.this, "YES KETEMU!", Toast.LENGTH_LONG);
            }
        }*/

        /*List<AccessibilityNodeInfo> cari = source.findAccessibilityNodeInfosByViewId("com.UCMobile.intl:id/titlebar");
            if (cari.size() > 0) {
                AccessibilityNodeInfo parent = (AccessibilityNodeInfo) cari.get(0);
                // You can also traverse the list if required data is deep in view hierarchy.
                String requiredText = parent.getText().toString();
                Log.i("Required Text", requiredText);
            }*/

        //String viewid = source.getViewIdResourceName();
        //Log.i(TAG, "viewId ="+viewid);
        Log.i(TAG, "source =="+source);
        Log.i(TAG, "event ="+event);

        //forbrowser if(browser1,browser2,dst)
        if(!A) {
            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                String textBrowser = event.getText().toString();
                String content = textBrowser.substring(1, textBrowser.length() - 1);

                if (textBrowser.contains("telolet")) {
                    A = true;
                    dialogok();
                    //myToast = Toast.makeText(AksesService.this, "eta.. terangkanlah..", Toast.LENGTH_LONG);
                    //myToast.show();
                }
            }
        }


            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                if (event.getText() != null) {
                    String input = event.getText().toString();
                    String content = input.substring(1, input.length() - 1);
                    Log.d(TAG, "text caught ==" + input.toString());

                    if (Arrays.asList(keywords).contains(content)) {
                        Bundle argumen = new Bundle();
                        argumen.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, " ");
                        event.getSource().performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, argumen);
                        Toast.makeText(AksesService.this, "Kata Diblokir", Toast.LENGTH_LONG).show();
                        performGlobalAction(GLOBAL_ACTION_BACK);
                    }
                }
            }


        //if(webAlert == 1 && webAlert!=0){
        //  Toast.makeText(AksesService.this, "ETA TERANGKANLAH..", Toast.LENGTH_LONG).show();
        //}

          //if(AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOWS")){
          //    dfs(source);
          //}
      }



    /*public void dfs(AccessibilityNodeInfo info){
        if(info==null){
            return;
        }
    if(info.getText()!=null && info.getText().length() > 0){
        Log.i(TAG, info.getText() + "class " + info.getClassName());
        for(int i=0;i<info.getChildCount();i++){
            AccessibilityNodeInfo child = info.getChild(i);
            dfs(child);
            child.recycle();
        }
    }
    }*/

    private void socmedWarning(){
        if(C) {
            Log.d(TAG, "jumlah " + (jumlahapp - 17) + " Total Waktu = " + totalwaktuSocmed);
            //C = false;
                if(hours >= 1){
                    final AlertDialog.Builder builders =  new AlertDialog.Builder(this);
                    LayoutInflater ab = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View views = ab.inflate(R.layout.socmed_warning, null);
                    builders.setView(views);
                    builders.setCancelable(false);
                    TextView warnTime = (TextView) views.findViewById(R.id.warntime);
                    warnTime.setText("Hari ini anda telah membuka Instagram sebanyak " + (jumlahapp - 97) + " kali dengan total waktu 1 Jam 3 Menit" );
                    Button button = (Button) views.findViewById(R.id.socmedwarningbutton);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            performGlobalAction(GLOBAL_ACTION_HOME);
                            //wait 5 second
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    C=false;
                                }
                            }, 5000);
                        }
                    });

                    alert = builders.create();
                    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY);

                    if(C){
                        alert.show();
                    }
                }
                else{
                    C=false;
                }
            }

    }

    private void dialogok(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater a = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = a.inflate(R.layout.dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        Button button = (Button)view.findViewById(R.id.btndialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                performGlobalAction(GLOBAL_ACTION_BACK);
                A=false;
            }
        });
       /* builder.setPositiveButton("Take Me To Heaven..", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                performGlobalAction(GLOBAL_ACTION_BACK);
                A=false;
            }
        });*/
        alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY);
            if(A) {
                alert.show();
            }
    }

    private void mlwarning(){
        final AlertDialog.Builder builders =  new AlertDialog.Builder(this);
        LayoutInflater ab = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views = ab.inflate(R.layout.ml_warning, null);
        builders.setView(views);
        builders.setCancelable(false);
        Button button = (Button) views.findViewById(R.id.mlwarningbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                performGlobalAction(GLOBAL_ACTION_HOME);
                B=false;

            }
        });

        alert = builders.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY);

        if(B){
            alert.show();
        }
    }

    @Override
    public void onInterrupt() {

    }
}
