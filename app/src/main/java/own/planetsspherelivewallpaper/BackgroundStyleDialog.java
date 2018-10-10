package own.planetsspherelivewallpaper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
*
* Background setting
*
* */

public class BackgroundStyleDialog extends Dialog implements OnClickListener {
    public static final int Leer = 2130837548;
    public static final int LightFive = 2130837518;
    public static final int LightFour = 2130837516;
    public static final int LightOne = 2130837504;
    public static final int LightSix = 2130837520;
    public static final int LightThree = 2130837508;
    public static final int LightTwo = 2130837506;
    LinearLayout handLayout;
    int light = 0;
    RadioButton lightFive;
    RadioButton lightFour;
    RadioButton lightOne;
    RadioButton lightSix;
    RadioButton lightThree;
    RadioButton lightTwo;
    RadioGroup lights;
    public int score = 0;

    String TAG = "Planets Background";

    BackgroundStyleDialog(Context context, DialogInterface.OnClickListener l, int defaultHand) {
        super(context);
        setContentView(R.layout.symbollayout);
        setTitle(R.string.set6);
        String myscore = "0";

        File myappdir = new File(Constants.appPath);
        File myscorefile = new File(Constants.appPath, Constants.scorefile);
        if(!myappdir.exists()) {
            Log.i(TAG, "creating score dir");
            myappdir.mkdirs();
        }

        if(!myscorefile.exists()) {
            Log.i(TAG, "creating score file");
            try {
                myscorefile.createNewFile();
                try
                {
                    FileOutputStream fOut = new FileOutputStream(myscorefile);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(myscore);
                    myOutWriter.close();
                    fOut.close();
                } catch(Exception e)
                {

                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }


        File file = new File(Constants.appPath, Constants.scorefile);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                myscore = line;
                Log.i(TAG, "got score from file: "+myscore);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (myscore != null) {
            this.score = Integer.parseInt(myscore);
        }
        this.handLayout = (LinearLayout) findViewById(R.id.symboldialoglayout);
        this.lightOne = (RadioButton) findViewById(R.id.symbol_one);
        this.lightTwo = (RadioButton) findViewById(R.id.symbol_two);
        this.lightThree = (RadioButton) findViewById(R.id.symbol_three);
        this.lightFour = (RadioButton) findViewById(R.id.symbol_four);
        this.lightFive = (RadioButton) findViewById(R.id.symbol_five);
        this.lightSix = (RadioButton) findViewById(R.id.symbol_six);
        this.lights = (RadioGroup) findViewById(R.id.symbolradiogroup);
        switch (IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background1)) {
            case R.drawable.background1:
                this.lights.check(this.lightOne.getId());
                break;
            case R.drawable.background2:
                this.lights.check(this.lightTwo.getId());
                break;
            case R.drawable.background3:
                this.lights.check(this.lightThree.getId());
                break;
            case R.drawable.backgroundstarry:
                this.lights.check(this.lightFour.getId());
                break;
            case R.drawable.backgroundmoon:
                this.lights.check(this.lightFive.getId());
                break;
            case R.drawable.element6:
                this.lights.check(this.lightSix.getId());
                break;
            default:
                this.lights.check(this.lightOne.getId());
                break;
        }
        this.lightOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.background1icon, 0);
        this.lightOne.setOnClickListener(this);

        if (this.score >= 480) {
            this.lightTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.background2icon, 0);
            this.lightTwo.setOnClickListener(this);
        } else {
            this.lightTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }

        if (this.score >= 1345) {
            this.lightThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.background3icon, 0);
            this.lightThree.setOnClickListener(this);
        } else {
            this.lightThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }

        this.lightFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.backgroundstarryicon, 0);
        this.lightFour.setOnClickListener(this);

        this.lightFive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.backgroundmoonicon, 0);
        this.lightFive.setOnClickListener(this);

        this.lightSix.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        //lightSix.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == this.lightOne) {
            this.lightOne.setChecked(true);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background1);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background1);
            dismiss();
        }
        if (v == this.lightTwo) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(true);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background2);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background2);
            dismiss();
        }
        if (v == this.lightThree) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(true);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background3);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.background3);
            dismiss();
        }
        if (v == this.lightFour) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(true);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.backgroundstarry);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.backgroundstarry);
            dismiss();
        }
        if (v == this.lightFive) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(true);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.backgroundmoon);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.backgroundmoon);
            dismiss();
        }
        if (v == this.lightSix) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(true);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.element6);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.backgroundValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.BackgroundValueKey, R.drawable.element6);
            dismiss();
        }
    }
}
