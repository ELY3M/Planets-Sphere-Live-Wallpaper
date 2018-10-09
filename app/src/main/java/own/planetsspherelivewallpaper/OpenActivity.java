package own.planetsspherelivewallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class OpenActivity extends Activity {
    private int REQUEST_CODE = 1;
    Context context;
    String punktestand = "";
    String TAG = "Planets openactivity";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.open);
        ((ImageButton) findViewById(R.id.lwpgallery)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
                OpenActivity.this.startActivityForResult(intent, OpenActivity.this.REQUEST_CODE);
            }
        });
        ((ImageButton) findViewById(R.id.getquiz)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OpenActivity.this.startActivity(new Intent(OpenActivity.this, Question.class));
            }
        });
        ((ImageButton) findViewById(R.id.gotolist)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OpenActivity.this.startActivity(new Intent(OpenActivity.this, List.class));
            }
        });
        String myscore = "0";
        this.context = this;


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

        /*
        try {
            Log.i(TAG, "trying to open "+Constants.scorefilepath);
            BufferedReader input = new BufferedReader(new InputStreamReader(openFileInput(Constants.scorefilepath)));
            StringBuffer buffer = new StringBuffer();
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    myscore = buffer.toString();
                    break;
                }
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        this.punktestand = myscore;
        ((TextView) findViewById(R.id.openscore)).setText(myscore);
    }
}
