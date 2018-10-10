package own.planetsspherelivewallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class IndividualWallpaperService extends LiveWallpaper implements OnSharedPreferenceChangeListener {
    public static String BackgroundColorValueKey = "BackgroundColorValue";
    public static String BackgroundValueKey = "BackgroundValueValue";
    public static String BorderValueKey = "discborder";
    public static String DiamondNumberValueKey = "DiamondNumberValueValue";
    public static String DiamondSpeedValueKey = "DiamondSpeedValueValue";
    public static String DiamondStyle2ValueKey = "DiamondStyle2Value";
    public static String DiamondStyleValueKey = "DiamondStyleValue";
    public static String DirectionValueKey = "DirectionValue";
    public static String DiscColorValueKey = "DiscColorValue";
    public static String FlashColorValueKey = "FlashColorValue";
    public static String FlashValueKey = "FlashValue";
    public static String FramerateKey = "FramerateValue";
    public static String LightColorValueKey = "LightColorValue";
    public static String PermanenceValueKey = "PermanenceValue";
    public static String RotationValueKey = "RotationValue";
    public static final String SHARED_PREFS_NAME = "clockSettings";
    public static int ScreenHeight;
    public static int ScreenWidth;
    public static String SparkleNumberValueKey = "SparkleNumberValueValue";
    public static String SparkleStyleValueKey = "SparkleStyleValue";
    public static String SpeedValueKey = "SpeedValue";
    public static String StarNumberValueKey = "StarNumberValueValue";
    public static String StarSpeedValueKey = "StarSpeedValueValue";
    public static String StarStyleValueKey = "StarStyleValue";
    public static int backgroundColorValue;
    public static int backgroundValue;
    public static boolean border;
    public static int borderValue;
    public static Context context;
    public static int diamondNumberValue;
    public static int diamondSpeedValue;
    public static int diamondStyle2Value;
    public static int diamondStyleValue;
    public static boolean diamondpack;
    public static int directionValue;
    public static int discColorValue;
    public static int flashColorValue;
    public static int framerateValue;
    public static int gravityValue;
    public static int lightColorValue;
    public static boolean permanence;
    public static boolean permanenceValue;
    public static Editor prefEditor;
    public static SharedPreferences prefs;
    public static int punktestand = 0;
    public static boolean rotatioValue;
    public static int rotationValue;
    public static int sparkleNumberValue;
    public static int sparkleStyleValue;
    public static int speedValue;
    public static boolean spherepack;
    public static int starNumberValue;
    public static int starSpeedValue;
    public static int starStyleValue;
    public static boolean touch;
    private Display Display;
    public final String TAG = "Planets Ind Wallpapers";
    Bitmap background;
    Bitmap backgroundresized;
    private Disc disc;
    Resources res;

    public void onCreate() {
        Log.d(TAG, "onCreate()");
        prefs = getSharedPreferences(SHARED_PREFS_NAME, 0);
        prefEditor = prefs.edit();
        prefs.registerOnSharedPreferenceChangeListener(this);
        String myscore = "0";
        context = this;

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


        punktestand = Integer.parseInt(myscore);
        context = this;
        this.Display = ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        ScreenWidth = this.Display.getWidth();
        ScreenHeight = this.Display.getHeight();
        starStyleValue = prefs.getInt(StarStyleValueKey, R.drawable.element1);
        sparkleStyleValue = prefs.getInt(SparkleStyleValueKey, 0);
        diamondStyleValue = prefs.getInt(DiamondStyleValueKey, R.drawable.element1);
        diamondStyle2Value = prefs.getInt(DiamondStyle2ValueKey, 0);
        diamondNumberValue = prefs.getInt(DiamondNumberValueKey, 10);
        starNumberValue = prefs.getInt(StarNumberValueKey, 20);
        sparkleNumberValue = prefs.getInt(SparkleNumberValueKey, 30);
        diamondSpeedValue = prefs.getInt(DiamondSpeedValueKey, 20);
        starSpeedValue = prefs.getInt(StarSpeedValueKey, 10);
        lightColorValue = prefs.getInt(LightColorValueKey, Disc.DefaultLightColor);
        flashColorValue = prefs.getInt(FlashColorValueKey, Disc.DefaultFlashColor);
        discColorValue = prefs.getInt(DiscColorValueKey, Disc.DefaultDiscColor);
        backgroundValue = prefs.getInt(BackgroundValueKey, R.drawable.background1);
        speedValue = prefs.getInt(SpeedValueKey, 20);
        rotationValue = prefs.getInt(RotationValueKey, 2);
        borderValue = prefs.getInt(BorderValueKey, 2);
        directionValue = prefs.getInt(DirectionValueKey, 2);
        framerateValue = prefs.getInt(FramerateKey, 80);
        prefEditor.commit();
        createDisc();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(FramerateKey)) {
            framerateValue = prefs.getInt(FramerateKey, 80);
            createDisc();
        }
        if (key.equals(StarStyleValueKey)) {
            starStyleValue = prefs.getInt(StarStyleValueKey, R.drawable.element1);
            createDisc();
        }
        if (key.equals(SparkleStyleValueKey)) {
            sparkleStyleValue = prefs.getInt(SparkleStyleValueKey, 0);
            createDisc();
        }
        if (key.equals(DiamondStyleValueKey)) {
            diamondStyleValue = prefs.getInt(DiamondStyleValueKey, R.drawable.element1);
            createDisc();
        }
        if (key.equals(DiamondStyle2ValueKey)) {
            diamondStyle2Value = prefs.getInt(DiamondStyle2ValueKey, 0);
            createDisc();
        }
        if (key.equals(DiamondNumberValueKey)) {
            diamondNumberValue = prefs.getInt(DiamondNumberValueKey, 10);
            createDisc();
        }
        if (key.equals(StarNumberValueKey)) {
            starNumberValue = prefs.getInt(StarNumberValueKey, 20);
            createDisc();
        }
        if (key.equals(SparkleNumberValueKey)) {
            sparkleNumberValue = prefs.getInt(SparkleNumberValueKey, 30);
            createDisc();
        }
        if (key.equals(DiamondSpeedValueKey)) {
            diamondSpeedValue = prefs.getInt(DiamondSpeedValueKey, 20);
            createDisc();
        }
        if (key.equals(StarSpeedValueKey)) {
            starSpeedValue = prefs.getInt(StarSpeedValueKey, 10);
            createDisc();
        }
        if (key.equals(BackgroundValueKey)) {
            backgroundValue = prefs.getInt(BackgroundValueKey, R.drawable.background1);
            createDisc();
        }
        createDisc();
    }

    protected void onSizeChanged(int width, int height) {
        this.background = BitmapFactory.decodeResource(getResources(), backgroundValue);
    }

    protected int getFramerate() {
        return framerateValue;
    }

    protected void onTouchEvent(MotionEvent event) {
        if (event.getAction() == 2) {
            Disc.ontouch(event.getX(), event.getY());
        }
    }

    protected void paint(Canvas c) {
        Paint p1 = new Paint();
        this.background = BitmapFactory.decodeResource(getResources(), backgroundValue);
        //TODO resize background to match screen size!
        this.backgroundresized = Bitmap.createScaledBitmap(this.background, ScreenWidth, ScreenHeight, false);
        c.drawBitmap(this.backgroundresized, 0.0f, 0.0f, p1);
        this.disc.paint(c);
    }

    private void createDisc() {
        this.disc = new Disc(this, diamondStyleValue, diamondStyle2Value, diamondNumberValue, rotationValue);
    }
}
