package own.planetsspherelivewallpaper;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class WallpaperSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {
    public static final String KEY = "flash_settings";
    public static final String TAG = "Planets FlashPrefs";
    public static int directionValue;
    private Preference about;
    public Context context;
    public DefaultDialog defaultd;
    public DiamondStyle dialog;
    public DiamondStyleSecond dialogsecond;
    public String directionString;
    private Preference mobile;
    private Preference rate;
    public int score = 0;
    private Preference set1;
    private Preference set10;
    private Preference set12;
    private Preference set2;
    private Preference set3;
    private Preference set4;
    private Preference set5;
    private Preference set6;
    private Preference set7;
    private Preference set8;
    private Preference set9;
    SharedPreferences settings;
    CheckBoxPreference test;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
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
        IndividualWallpaperService.punktestand = Integer.parseInt(myscore);
        this.context = this;
        addPreferencesFromResource(R.xml.settings);
        this.set1 = findPreference("set1");
        this.set1.setOnPreferenceClickListener(this);
        this.set2 = findPreference("set2");
        this.set2.setOnPreferenceClickListener(this);
        this.set3 = findPreference("set3");
        this.set3.setOnPreferenceClickListener(this);
        this.set4 = findPreference("set4");
        this.set4.setOnPreferenceClickListener(this);
        this.set5 = findPreference("set5");
        this.set5.setOnPreferenceClickListener(this);
        this.set6 = findPreference("set6");
        this.set6.setOnPreferenceClickListener(this);
        this.set7 = findPreference("set7");
        this.set7.setOnPreferenceClickListener(this);
        this.set8 = findPreference("set8");
        this.set8.setOnPreferenceClickListener(this);
        this.set9 = findPreference("set9");
        this.set9.setOnPreferenceClickListener(this);
        this.set10 = findPreference("set10");
        this.set10.setOnPreferenceClickListener(this);
        this.set12 = findPreference("set12");
        this.set12.setOnPreferenceClickListener(this);
        this.mobile = findPreference("mobile");
        this.mobile.setOnPreferenceClickListener(this);
        this.about = findPreference("about");
        this.about.setOnPreferenceClickListener(this);
    }

    protected void onStop() {
        super.onStop();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    public boolean onPreferenceClick(Preference key) {
        Intent intent;
        if (key == this.mobile) {
            startActivity(new Intent(this, Website.class));
        }
        if (key == this.about) {
            showInfo();
        }
        if (key == this.set1) {
            this.dialog = new DiamondStyle(this.context, null, 0);
            this.dialog.show();
        }
        if (key == this.set12) {
            this.dialogsecond = new DiamondStyleSecond(this.context, null, 0);
            this.dialogsecond.show();
        }
        if (key == this.set2) {
            startActivity(new Intent(this, Question.class));
        }
        if (key == this.set3) {
            if (this.score >= 290) {
                startActivity(new Intent(this, MemoryManager.class));
            } else {
                this.defaultd = new DefaultDialog(this.context);
                this.defaultd.show();
            }
        }
        if (key == this.set4) {
            startActivity(new Intent(this, List.class));
        }
        if (key == this.set5) {
            if (this.score >= 390) {
                new RotatingDialog(this.context, null, 0).show();
            } else {
                this.defaultd = new DefaultDialog(this.context);
                this.defaultd.show();
            }
        }
        if (key == this.set6) {
            new BackgroundStyleDialog(this.context, null, 0).show();
        }
        if (key == this.set7) {
            if (this.score >= 770) {
                new SpeedDialog(this.context, null, 0).show();
            } else {
                this.defaultd = new DefaultDialog(this.context);
                this.defaultd.show();
            }
        }
        if (key == this.set8) {
            if (this.score >= 515) {
                new SparkleNumberDialog(this.context, null, 0).show();
            } else {
                this.defaultd = new DefaultDialog(this.context);
                this.defaultd.show();
            }
        }
        if (key == this.set9) {
            new SparkleStyleDialog(this.context, null, 0).show();
        }
        if (key == this.set10) {
            new FramerateDialog(this.context, null, 0).show();
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        this.settings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void showInfo() {
        Builder builder3 = new Builder(this);
        builder3.setIcon(R.drawable.icon);
        builder3.setTitle(R.string.title_about_alert);
        builder3.setMessage(R.string.message_about);
        builder3.setPositiveButton(R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog3, int id) {
                dialog3.cancel();
            }
        });
        builder3.show();
    }
}
