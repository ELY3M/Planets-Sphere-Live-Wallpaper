package own.planetsspherelivewallpaper;

import static android.os.Build.VERSION.SDK_INT;
import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import static java.lang.System.exit;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class OpenActivity extends Activity {
    private int REQUEST_CODE = 1;
    Context context;
    String punktestand = "";
    String TAG = "Planets openactivity";

    void askExternalStorageManager() throws InterruptedException {
        if(SDK_INT >= 30) {
            if (Environment.isExternalStorageManager()) {
                Log.i(TAG, "ExternalStorageManager Perms are already granted :)");
            } else {
                Toast.makeText(this, "This app need access to your phone memory or SD Card to make files and write files (/wX/ on your phone memory or sd card)\nThe all file access settings will open. Make sure to toggle it on to enable all files access for this app to function fully.\n You need to restart the app after you enabled the all files access for this app in the settings.\n", Toast.LENGTH_LONG).show();
                Intent permissionIntent = new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(permissionIntent);
                Thread.sleep(13000); //sleep for 13 secs and force restart
                exit(0);
            }
        }
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.open);


        //stupid perm//
        if(SDK_INT >= 30) {
            try {
                askExternalStorageManager();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Log.i(TAG, "perm granted :)");
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Log.i(TAG, "perm denied :(");
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            Log.i(TAG, "asking for perm");
                        }
                    }).check();
        }

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


        this.punktestand = myscore;
        ((TextView) findViewById(R.id.openscore)).setText(myscore);
    }


}
