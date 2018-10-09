package own.planetsspherelivewallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import java.util.Random;

public class Flash {
    private static String TAG = "Planets Flash";
    private static int netSize = IndividualWallpaperService.diamondNumberValue;
    private Canvas Canvas;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private boolean[] booleanX;
    private boolean[] booleanY;
    private boolean[] dirrot;
    public Bitmap flashcoloredBitmap;
    private Bitmap[] imageview;
    private Paint paint;
    private float[] posX;
    private float[] posY;
    private float[] pushspeed;
    private float[] rotated;
    private boolean[] rotatedboolean;
    private float[] rotatedspeed;
    private int speed = IndividualWallpaperService.diamondSpeedValue;
    private float[] speedX;
    private float[] speedY;

    public Flash(Resources res, int id, int id2, int size) {
        this.bitmap = BitmapFactory.decodeResource(res, id);
        if (id2 != 0) {
            this.bitmap2 = BitmapFactory.decodeResource(res, id2);
        }
        netSize = size;
        this.rotated = new float[netSize];
        this.posX = new float[netSize];
        this.posY = new float[netSize];
        this.speedX = new float[netSize];
        this.speedY = new float[netSize];
        this.booleanX = new boolean[netSize];
        this.booleanY = new boolean[netSize];
        this.dirrot = new boolean[netSize];
        this.rotatedboolean = new boolean[netSize];
        this.rotatedspeed = new float[netSize];
        this.pushspeed = new float[netSize];
        this.imageview = new Bitmap[netSize];
        for (int n = 0; n < netSize; n++) {
            if (id2 == 0) {
                this.imageview[n] = this.bitmap;
            } else if (new Random().nextInt(2) == 0) {
                this.imageview[n] = this.bitmap;
            } else {
                this.imageview[n] = this.bitmap2;
            }
            Random rn1 = new Random();
            this.posX[n] = (float) rn1.nextInt(IndividualWallpaperService.ScreenWidth);
            this.posY[n] = (float) rn1.nextInt(IndividualWallpaperService.ScreenHeight);
            this.rotated[n] = (float) rn1.nextInt(360);
            Random rn = new Random();
            int randomint = rn.nextInt(2);
            int randomintdir = new Random().nextInt(2);
            if (randomint == 0) {
                this.booleanX[n] = true;
            } else {
                this.booleanX[n] = false;
            }
            if (randomintdir == 0) {
                this.dirrot[n] = true;
            } else {
                this.dirrot[n] = false;
            }
            Log.d(TAG, "DIRROT" + randomintdir);
            if (rn.nextInt(2) == 0) {
                this.rotatedboolean[n] = true;
            } else {
                this.rotatedboolean[n] = false;
            }
            this.speedX[n] = (float) (((double) ((this.speed * rn.nextInt(200)) / 2000)) + 0.4d);
            this.speedY[n] = (float) (((double) ((this.speed * rn.nextInt(200)) / 2000)) + 0.4d);
            this.rotatedspeed[n] = (float) (((double) (rn.nextInt(50) / 100)) + 0.3d);
            this.pushspeed[n] = 0.0f;
        }
        this.flashcoloredBitmap = Bitmap.createBitmap(this.bitmap.getWidth(), this.bitmap.getHeight(), Config.ARGB_8888);
        this.Canvas = new Canvas(this.flashcoloredBitmap);
        this.Canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.paint);
    }

    public void paint(Canvas c) {
        for (int n = 0; n < netSize; n++) {
            Matrix a = new Matrix();
            Paint trans = new Paint(1);
            this.posX[n] = getNewPosx(this.posX[n], this.booleanX[n], n);
            this.posY[n] = getNewPosy(this.posY[n], this.booleanY[n], n);
            a.postRotate(getRotation(n), (float) (this.imageview[n].getWidth() / 2), (float) (this.imageview[n].getHeight() / 2));
            a.postTranslate(this.posX[n], this.posY[n]);
            c.drawBitmap(this.imageview[n], a, trans);
        }
    }

    private float getRotation(int n) {
        if (this.dirrot[n]) {
            this.rotated[n] = ((this.rotated[n] + this.rotatedspeed[n]) + Disc.rotate) + Disc.speed;
        } else {
            this.rotated[n] = ((this.rotated[n] - this.rotatedspeed[n]) - Disc.rotate) - Disc.speed;
        }
        return this.rotated[n];
    }

    private float getNewPosx(float position, boolean b, int indexX) {
        float sp = this.speedX[indexX] * Disc.speed;
        if (b && position < ((float) IndividualWallpaperService.ScreenWidth)) {
            position = (position + sp) + this.pushspeed[indexX];
        } else if (b && position >= ((float) IndividualWallpaperService.ScreenWidth)) {
            b = false;
            position = (position - sp) - this.pushspeed[indexX];
            newrandomSpeedX(indexX);
        } else if (!b && position > ((float) (-this.flashcoloredBitmap.getWidth()))) {
            position = (position - sp) - this.pushspeed[indexX];
        } else if (!b && position <= ((float) this.flashcoloredBitmap.getWidth())) {
            b = true;
            position = (position + sp) + this.pushspeed[indexX];
            newrandomSpeedX(indexX);
        }
        if (this.pushspeed[indexX] < 0.0f) {
            this.pushspeed[indexX] = this.pushspeed[indexX] - 1.0f;
        } else {
            this.pushspeed[indexX] = 0.0f;
        }
        this.booleanX[indexX] = b;
        return position;
    }

    private void newrandomSpeedX(int indexX) {
        this.speedY[indexX] = (float) (((double) (new Random().nextInt(200) / 100)) + 0.2d);
    }

    private float getNewPosy(float position, boolean b, int indexX) {
        float sp = this.speedY[indexX] * Disc.speed;
        if (b && position < ((float) IndividualWallpaperService.ScreenHeight)) {
            position = (position + sp) + this.pushspeed[indexX];
        } else if (b && position >= ((float) IndividualWallpaperService.ScreenHeight)) {
            b = false;
            position = (position - sp) - this.pushspeed[indexX];
            newrandomSpeedY(indexX);
        } else if (!b && position > ((float) (-this.flashcoloredBitmap.getHeight()))) {
            position = (position - sp) - this.pushspeed[indexX];
        } else if (!b && position <= ((float) this.flashcoloredBitmap.getHeight())) {
            b = true;
            position = (position + sp) + this.pushspeed[indexX];
            newrandomSpeedY(indexX);
        }
        if (this.pushspeed[indexX] < 0.0f) {
            this.pushspeed[indexX] = this.pushspeed[indexX] - 1.0f;
        } else {
            this.pushspeed[indexX] = 0.0f;
        }
        this.booleanY[indexX] = b;
        return position;
    }

    private void newrandomSpeedY(int indexX) {
        this.speedY[indexX] = (float) (((double) (new Random().nextInt(200) / 100)) + 0.2d);
    }

    public static void ontouch(float x, float y) {
        Log.i(TAG, "touched x: "+x+" y: "+y);
    }
}
