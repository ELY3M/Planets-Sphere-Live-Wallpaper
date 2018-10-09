package own.planetsspherelivewallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import java.util.Random;

public class Sparkle {
    private Canvas Canvas;
    private int[] alpha;
    private Bitmap bitmap;
    private boolean[] booleanX;
    private boolean[] booleanY;
    private ColorMatrix flashColorMatrix;
    private ColorMatrixColorFilter flashColorMatrixColorFilter;
    private Bitmap[] flashcoloredBitmap;
    private int netSize;
    private Paint paint;
    private float[] posX;
    private float[] posY;
    private float[] pushspeed;
    private float[] rotated;
    private boolean[] rotatedboolean;
    private float[] rotatedspeed;
    private int[] sized;
    private float[] speedX;
    private float[] speedY;
    Paint trans;
    private boolean[] transboolean;

    public Sparkle(Resources res, int id) {
        this.netSize = 10;
        this.trans = new Paint();
        this.paint = new Paint();
        this.netSize = IndividualWallpaperService.sparkleNumberValue;
        this.rotated = new float[this.netSize];
        this.sized = new int[this.netSize];
        this.posX = new float[this.netSize];
        this.alpha = new int[this.netSize];
        this.transboolean = new boolean[this.netSize];
        this.posY = new float[this.netSize];
        this.flashcoloredBitmap = new Bitmap[this.netSize];
        this.speedX = new float[this.netSize];
        this.speedY = new float[this.netSize];
        this.booleanX = new boolean[this.netSize];
        this.booleanY = new boolean[this.netSize];
        this.rotatedboolean = new boolean[this.netSize];
        this.rotatedspeed = new float[this.netSize];
        this.pushspeed = new float[this.netSize];
        this.bitmap = BitmapFactory.decodeResource(res, id);
        Matrix matrix = new Matrix();
        for (int n = 0; n < this.netSize; n++) {
            Random rn1 = new Random();
            this.posX[n] = (float) rn1.nextInt(IndividualWallpaperService.ScreenWidth);
            this.posY[n] = (float) rn1.nextInt(IndividualWallpaperService.ScreenHeight);
            this.rotated[n] = (float) rn1.nextInt(360);
            Random rn = new Random();
            if (rn.nextInt(2) == 0) {
                this.booleanX[n] = true;
            } else {
                this.booleanX[n] = false;
            }
            this.sized[n] = rn.nextInt(3) + 1;
            if (rn.nextInt(2) == 0) {
                this.transboolean[n] = true;
            } else {
                this.transboolean[n] = false;
            }
            this.alpha[n] = rn.nextInt(250);
            this.speedX[n] = (float) (((double) (rn.nextInt(100) / 100)) + 0.8d);
            this.speedY[n] = (float) (((double) (rn.nextInt(100) / 100)) + 0.8d);
            this.rotatedspeed[n] = (float) (((double) (rn.nextInt(50) / 100)) + 0.3d);
            this.pushspeed[n] = 0.0f;
            Matrix m = new Matrix();
            m.postScale((float) ((((double) this.sized[n]) * 0.2d) + 0.2d), (float) ((((double) this.sized[n]) * 0.2d) + 0.2d));
            this.flashcoloredBitmap[n] = Bitmap.createBitmap(this.bitmap, 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), m, true);
        }
    }

    public void paint(Canvas c) {
        for (int n = 0; n < this.netSize; n++) {
            Matrix a = new Matrix();
            a.postTranslate(this.posX[n], this.posY[n]);
            this.trans.setAlpha(getTransparency(this.alpha[n], this.transboolean[n], n));
            c.drawBitmap(this.flashcoloredBitmap[n], a, this.trans);
        }
    }

    private int getTransparency(int i, boolean b, int index) {
        Random rnlocal = new Random();
        if (b) {
            this.alpha[index] = i + 12;
            if (this.alpha[index] < 255) {
                return this.alpha[index];
            }
            this.transboolean[index] = false;
            return 255;
        }
        this.alpha[index] = i - 12;
        if (this.alpha[index] > 0) {
            return this.alpha[index];
        }
        if (this.alpha[index] <= 10000000) {
            this.transboolean[index] = true;
            this.posX[index] = (float) rnlocal.nextInt(IndividualWallpaperService.ScreenWidth);
            this.posY[index] = (float) rnlocal.nextInt(IndividualWallpaperService.ScreenHeight);
        }
        return 0;
    }

    public static void ontouch(float x, float y) {
    }
}
