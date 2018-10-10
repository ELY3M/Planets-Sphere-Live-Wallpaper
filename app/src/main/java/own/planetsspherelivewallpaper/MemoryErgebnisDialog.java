package own.planetsspherelivewallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MemoryErgebnisDialog extends Activity {
    public static Context context;
    public static Editor prefEditor;
    public static SharedPreferences prefs;
    public int counter;
    public ImageButton ende;
    public String feature = "";
    public int featurefirst = 0;
    public int featurelast = 0;
    public int memoryscore = 0;
    String TAG = "Planets MemoryErgebnisDialog";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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

        this.counter = Integer.parseInt(myscore);
        Log.d(TAG, "COUNTER Firts" + this.counter);
        setContentView(R.layout.finish);
        TextView ergebnis = (TextView) findViewById(R.id.ergebnis);
        TextView maybe = (TextView) findViewById(R.id.ergebnismaybe);
        TextView featuretext = (TextView) findViewById(R.id.featuretext);
        this.featurefirst = getLevel(this.counter);
        this.memoryscore = MemoryManager.score / 110;
        ergebnis.setText("Yeah, you have earned " + this.memoryscore + " points.");
        this.counter += this.memoryscore;
        maybe.setText("Your actual Score is " + this.counter + " points.");
        int stringnew = this.counter;
        this.featurelast = getLevel(this.counter);
        if (this.featurelast > this.featurefirst) {
            featuretext.setText("You've unlocked a new feature: " + this.feature);
        }
        Log.d(TAG, "COUNTER LAST" + stringnew);

        //File myappdir = new File(Constants.appPath);
        //File myscorefile = new File(Constants.appPath, Constants.scorefile);
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
                    myOutWriter.write(String.valueOf(counter));
                    myOutWriter.close();
                    fOut.close();
                } catch(Exception e)
                {

                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }


        try {

            FileOutputStream fOut = new FileOutputStream(myscorefile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(String.valueOf(counter));
            myOutWriter.close();
            fOut.close();

        } catch (Exception e2) {
            e2.printStackTrace();
        }

        this.ende = (ImageButton) findViewById(R.id.back);
        this.ende.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (v == MemoryErgebnisDialog.this.ende) {
                    MemoryErgebnisDialog.this.startActivity(new Intent(MemoryErgebnisDialog.this, MemoryManager.class));
                }
            }
        });
    }

    private int getLevel(int v) {
        int i;
        String[] features = context.getResources().getStringArray(R.array.feature);
        String[] featurevalue = context.getResources().getStringArray(R.array.featurevalue);
        int[] list = new int[features.length];
        for (i = 0; i < features.length; i++) {
            list[i] = Integer.parseInt(featurevalue[i].toString());
        }
        for (i = 0; i < features.length; i++) {
            if (v < list[i]) {
                this.feature = features[i - 1];
                return i + 1;
            }
        }
        return i + 1;  //was 17
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}
