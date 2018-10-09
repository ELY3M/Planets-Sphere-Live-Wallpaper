package own.planetsspherelivewallpaper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RotatingDialog extends Dialog implements OnClickListener {
    public static final int LightOne = 1;
    public static final int LightTwo = 2;
    int light = 0;
    RadioButton lightOne;
    RadioButton lightTwo;
    RadioGroup lights;

    RotatingDialog(Context context, DialogInterface.OnClickListener l, int defaultHand) {
        super(context);
        setTitle(R.string.set5summary);
        LinearLayout handLayout = new LinearLayout(getContext());
        handLayout.setLayoutParams(new LayoutParams(-2, -2));
        handLayout.setOrientation(1);
        handLayout.setPadding(10, 0, 10, 10);
        this.lights = new RadioGroup(getContext());
        this.lights.setLayoutParams(new LayoutParams(-2, -2));
        this.lightOne = new RadioButton(getContext());
        this.lightTwo = new RadioButton(getContext());
        if (IndividualWallpaperService.rotationValue == 1) {
            this.lightOne.setChecked(true);
        }
        if (IndividualWallpaperService.rotationValue == 2) {
            this.lightTwo.setChecked(true);
        }
        this.lights.check(this.lights.getCheckedRadioButtonId());
        this.lightOne.setText(R.string.rotset1);
        this.lightOne.setCompoundDrawablePadding(10);
        this.lightTwo.setText(R.string.rotset2);
        this.lightTwo.setCompoundDrawablePadding(10);
        this.lights.addView(this.lightOne);
        this.lights.addView(this.lightTwo);
        this.lightOne.setOnClickListener(this);
        this.lightTwo.setOnClickListener(this);
        handLayout.addView(this.lights);
        setContentView(handLayout);
    }

    public void onClick(View v) {
        if (v == this.lightOne) {
            this.lightOne.setChecked(true);
            this.lightTwo.setChecked(false);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.RotationValueKey, 1);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.rotationValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.RotationValueKey, 1);
            dismiss();
        }
        if (v == this.lightTwo) {
            this.lightOne.setChecked(false);
            this.lightTwo.setChecked(true);
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.RotationValueKey, 2);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.rotationValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.RotationValueKey, 2);
            dismiss();
        }
    }
}
