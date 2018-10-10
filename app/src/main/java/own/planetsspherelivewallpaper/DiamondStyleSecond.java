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

public class DiamondStyleSecond extends Dialog implements OnClickListener {
    public static final int Leer = 2130837548;
    public static final int LightFive = 2130837516;
    public static final int LightFour = 2130837514;
    public static final int LightOne = 0;
    public static final int LightSix = 2130837518;
    public static final int LightThree = 2130837512;
    public static final int LightTwo = 2130837510;
    LinearLayout handLayout;
    int light = 0;
    RadioButton light10;
    RadioButton light11;
    RadioButton light12;
    RadioButton light13;
    RadioButton light14;
    RadioButton light15;
    RadioButton lightEight;
    RadioButton lightFive;
    RadioButton lightFour;
    RadioButton lightNine;
    RadioButton lightOne;
    RadioButton lightSeven;
    RadioButton lightSix;
    RadioButton lightThree;
    RadioButton lightTwo;
    RadioGroup lights;
    public int score = 0;

    String TAG = "Planets DiamondStyleSecond";

    DiamondStyleSecond(Context context, DialogInterface.OnClickListener l, int defaultHand) {
        super(context);
        setContentView(R.layout.symbollayout);
        setTitle(R.string.set1);
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
        switch (IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, 0)) {
            case 0:
                this.lights.check(this.lightOne.getId());
                break;
            case R.drawable.element1:
                this.lights.check(this.lightTwo.getId());
                break;
            case R.drawable.element2:
                this.lights.check(this.lightThree.getId());
                break;
            case R.drawable.element3:
                this.lights.check(this.lightFour.getId());
                break;
            case R.drawable.element4:
                this.lights.check(this.lightFive.getId());
                break;
            case R.drawable.element5:
                this.lights.check(this.lightSix.getId());
                break;
            default:
                this.lights.check(this.lightOne.getId());
                break;
        }
        this.lightOne.setText("none");
        this.lightOne.setOnClickListener(this);
        if (this.score >= 575) {
            this.lightTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.element1icon, 0);
            this.lightTwo.setOnClickListener(this);
        } else {
            this.lightTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }
        if (this.score >= 985) {
            this.lightThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.element2icon, 0);
            this.lightThree.setOnClickListener(this);
        } else {
            this.lightThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }
        if (this.score >= 5000) {
            this.lightFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.element3icon, 0);
            this.lightFour.setOnClickListener(this);
        } else {
            this.lightFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }
        if (this.score >= 10000) {
            this.lightFive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.element4icon, 0);
            this.lightFive.setOnClickListener(this);
        } else {
            this.lightFive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
        }
        if (this.score >= 20000) {
            this.lightSix.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.element5icon, 0);
            this.lightSix.setOnClickListener(this);
            return;
        }
        this.lightSix.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.leericon, 0);
    }

    public void onClick(View v) {
        if (v == this.lightOne) {
            this.lightOne.setChecked(true);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, 0);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, 0);
            dismiss();
        }
        if (v == this.lightTwo) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(true);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element1);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element1);
            dismiss();
        }
        if (v == this.lightThree) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(true);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element2);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element2);
            dismiss();
        }
        if (v == this.lightFour) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(true);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element3);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element3);
            dismiss();
        }
        if (v == this.lightFive) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(true);
            this.lightSix.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element4);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element4);
            dismiss();
        }
        if (v == this.lightSix) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(false);
            this.lightThree.setChecked(false);
            this.lightFour.setChecked(false);
            this.lightFive.setChecked(false);
            this.lightSix.setChecked(true);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element5);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.diamondStyle2Value = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.DiamondStyle2ValueKey, R.drawable.element5);
            dismiss();
        }
    }
}
