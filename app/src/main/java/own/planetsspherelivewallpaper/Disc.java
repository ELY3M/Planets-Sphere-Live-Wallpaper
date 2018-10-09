package own.planetsspherelivewallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Disc {
    public static final int DefaultBackground = 2130837504;
    public static final int DefaultBorder = 2;
    public static final int DefaultDiamondNumber = 10;
    public static final int DefaultDiamondSpeed = 20;
    public static final int DefaultDiamondStyle = 2130837510;
    public static final int DefaultDiamondStyle2 = 0;
    public static final int DefaultDirection = 2;
    public static final int DefaultDiscColor = -13421773;
    public static final int DefaultFlashColor = -16777216;
    public static final int DefaultFramerate = 80;
    public static final int DefaultLightColor = -16711936;
    public static final int DefaultRotation = 2;
    public static final int DefaultSparkleNumber = 30;
    public static final int DefaultSparkleStyle = 0;
    public static final int DefaultSpeedValue = 20;
    public static final int DefaultStarNumber = 20;
    public static final int DefaultStarSpeed = 10;
    public static final int DefaultStarStyle = 2130837510;
    static boolean gravity = false;
    static float rotate = 0.0f;
    static boolean rotation = false;
    public static boolean rotationactive = false;
    public static int rotationactivevalue = 0;
    static boolean rotationleft;
    static float speed = 0.0f;
    private Bitmap coloreddiscbitmap;
    private Context context;
    private Canvas discCanvas;
    private String[] discColorHex = new String[5];
    private ColorMatrix discColorMatrix;
    private ColorMatrixColorFilter discColorMatrixColorFilter;
    private float[] discColorMatrixFloat = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private float[] discColorPercent = new float[4];
    private Bitmap discbitmap;
    private Flash flash;
    private Flash flash2;
    private Bitmap flashbitmap;
    private Light light;
    private Bitmap lightbitmap;
    private Paint paint = new Paint();
    private SharedPreferences prefs = IndividualWallpaperService.prefs;
    private Resources resources;
    private Sparkle sparkle;

    public Disc(Context context, int c, int c2, int f, int rotvalue) {
        Resources res = context.getResources();
        this.context = context;
        this.resources = res;
        this.flash = new Flash(res, c, c2, f);
        if (IndividualWallpaperService.sparkleStyleValue != 0) {
            this.sparkle = new Sparkle(res, IndividualWallpaperService.sparkleStyleValue);
        }
        speed = ((float) IndividualWallpaperService.speedValue) / 30.0f;
        switch (IndividualWallpaperService.rotationValue) {
            case 1:
                rotation = true;
                break;
            case 2:
                rotation = false;
                break;
        }
        switch (IndividualWallpaperService.gravityValue) {
            case 1:
                gravity = true;
                return;
            case 2:
                gravity = false;
                return;
            default:
                return;
        }
    }

    public void paint(Canvas c) {
        c.save();
        Paint p = new Paint(1);
        if (IndividualWallpaperService.sparkleStyleValue != 0) {
            this.sparkle.paint(c);
        }
        this.flash.paint(c);
        switch (IndividualWallpaperService.gravityValue) {
            case 1:
                gravity = true;
                break;
            case 2:
                gravity = false;
                break;
        }
        switch (IndividualWallpaperService.rotationValue) {
            case 1:
                rotation = true;
                break;
            case 2:
                rotation = false;
                break;
        }
        if (rotationactivevalue <= 0 || !rotation) {
            rotate = 0.0f;
        } else {
            rotationactivevalue--;
            rotate -= 0.4f;
        }
        c.restore();
    }

    public static void ontouch(float x, float y) {
        switch (IndividualWallpaperService.rotationValue) {
            case 1:
                rotation = true;
                break;
            case 2:
                rotation = false;
                break;
        }
        if (rotation) {
            rotationactivevalue = 20;
            rotate = 8.0f;
            rotationactive = true;
        }
    }
}
