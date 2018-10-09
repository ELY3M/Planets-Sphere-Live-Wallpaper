package own.planetsspherelivewallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import java.lang.reflect.Array;
import java.util.Random;

public class Light {
    private Canvas Canvas;
    int[][] alpha = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{this.netSize, this.netSize}));
    private boolean[] booleanX;
    private boolean[] booleanY;
    private ColorMatrix flashColorMatrix;
    private ColorMatrixColorFilter flashColorMatrixColorFilter;
    private Bitmap flashcoloredBitmap;
    private int netSize;
    private Paint paint = new Paint();
    private float[] posX;
    private float[] posY;
    private float[] pushspeed;
    private float[] rotated;
    private boolean[] rotatedboolean;
    private float[] rotatedspeed;
    private int speed = IndividualWallpaperService.starSpeedValue;
    private float[] speedX;
    private float[] speedY;

    public Light(Resources res, int id, int size) {
        this.netSize = size;
        this.rotated = new float[this.netSize];
        this.posX = new float[this.netSize];
        this.posY = new float[this.netSize];
        this.speedX = new float[this.netSize];
        this.speedY = new float[this.netSize];
        this.booleanX = new boolean[this.netSize];
        this.booleanY = new boolean[this.netSize];
        this.rotatedboolean = new boolean[this.netSize];
        this.rotatedspeed = new float[this.netSize];
        this.pushspeed = new float[this.netSize];
        this.alpha = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.netSize, this.netSize});
        this.flashcoloredBitmap = BitmapFactory.decodeResource(res, id);
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
            if (rn.nextInt(2) == 0) {
                this.rotatedboolean[n] = true;
            } else {
                this.rotatedboolean[n] = false;
            }
            this.speedX[n] = (float) (((double) ((this.speed * rn.nextInt(100)) / 400)) + 0.8d);
            this.speedY[n] = (float) (((double) ((this.speed * rn.nextInt(100)) / 400)) + 0.8d);
            this.rotatedspeed[n] = (float) (((double) (rn.nextInt(50) / 100)) + 0.3d);
            this.pushspeed[n] = 0.0f;
        }
    }

    public void paint(Canvas c) {
        for (int n = 0; n < this.netSize; n++) {
            Matrix a = new Matrix();
            Paint trans = new Paint(1);
            this.posX[n] = getNewPosx(this.posX[n], this.booleanX[n], n);
            this.posY[n] = getNewPosy(this.posY[n], this.booleanY[n], n);
            a.postRotate(getRotation(n), (float) (this.flashcoloredBitmap.getWidth() / 2), (float) (this.flashcoloredBitmap.getHeight() / 2));
            a.postTranslate(this.posX[n], this.posY[n]);
            c.drawBitmap(this.flashcoloredBitmap, a, trans);
        }
    }

    private float getRotation(int n) {
        this.rotated[n] = this.rotated[n] + this.rotatedspeed[n];
        return this.rotated[n];
    }

    private float getNewPosx(float position, boolean b, int indexX) {
        if (b && position < ((float) IndividualWallpaperService.ScreenWidth)) {
            position = (this.speedX[indexX] + position) + this.pushspeed[indexX];
        } else if (b && position >= ((float) IndividualWallpaperService.ScreenWidth)) {
            b = false;
            position = (position - this.speedX[indexX]) - this.pushspeed[indexX];
        } else if (!b && position > ((float) (-this.flashcoloredBitmap.getWidth()))) {
            position = (position - this.speedX[indexX]) - this.pushspeed[indexX];
        } else if (!b && position <= ((float) this.flashcoloredBitmap.getWidth())) {
            b = true;
            position = (this.speedX[indexX] + position) + this.pushspeed[indexX];
        }
        if (this.pushspeed[indexX] < 0.0f) {
            this.pushspeed[indexX] = this.pushspeed[indexX] - 1.0f;
        } else {
            this.pushspeed[indexX] = 0.0f;
        }
        this.booleanX[indexX] = b;
        return position;
    }

    private float getNewPosy(float position, boolean b, int indexX) {
        if (b && position < ((float) IndividualWallpaperService.ScreenHeight)) {
            position = (this.speedY[indexX] + position) + this.pushspeed[indexX];
        } else if (b && position >= ((float) IndividualWallpaperService.ScreenHeight)) {
            b = false;
            position = (position - this.speedY[indexX]) - this.pushspeed[indexX];
        } else if (!b && position > ((float) (-this.flashcoloredBitmap.getHeight()))) {
            position = (position - this.speedY[indexX]) - this.pushspeed[indexX];
        } else if (!b && position <= ((float) this.flashcoloredBitmap.getHeight())) {
            b = true;
            position = (this.speedY[indexX] + position) + this.pushspeed[indexX];
        }
        if (this.pushspeed[indexX] < 0.0f) {
            this.pushspeed[indexX] = this.pushspeed[indexX] - 1.0f;
        } else {
            this.pushspeed[indexX] = 0.0f;
        }
        this.booleanY[indexX] = b;
        return position;
    }

    public static void ontouch(float x, float y) {
    }
}
