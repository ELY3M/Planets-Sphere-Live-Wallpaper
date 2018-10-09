package own.planetsspherelivewallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public abstract class LiveWallpaper extends WallpaperService {
    public static int LiveWallpaperHeight;
    public static int LiveWallpaperWidth;
    public final String TAG = "Planets WallpaperEngine";
    protected float xOffset;
    protected float xOffsetStep;
    protected int xPixelOffset;
    protected float yOffset;
    protected float yOffsetStep;
    protected int yPixelOffset;

    private final class FlashEngine extends Engine implements Runnable, OnSharedPreferenceChangeListener {
        private Handler handler = new Handler();
        private SharedPreferences prefs;
        private boolean running = false;
        private boolean visible = false;

        FlashEngine() {
            //super(LiveWallpaper.this);
            this.prefs = LiveWallpaper.this.getSharedPreferences(IndividualWallpaperService.SHARED_PREFS_NAME, 0);
            this.prefs.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(this.prefs, null);
        }

        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            setTouchEventsEnabled(true);
        }

        public void onDestroy() {
            stop();
            if (this.prefs != null) {
                this.prefs.unregisterOnSharedPreferenceChangeListener(this);
            }
            super.onDestroy();
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            start();
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            LiveWallpaper.this.onSizeChanged(width, height);
            start();
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            stop();
            super.onSurfaceDestroyed(holder);
        }

        public void onVisibilityChanged(boolean visible) {
            if (!visible) {
                stop();
            }
            super.onVisibilityChanged(visible);
            this.visible = visible;
            if (visible) {
                start();
            }
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            LiveWallpaper.this.xOffset = xOffset;
            LiveWallpaper.this.yOffset = yOffset;
            LiveWallpaper.this.xOffsetStep = xOffsetStep;
            LiveWallpaper.this.yOffsetStep = yOffsetStep;
            LiveWallpaper.this.xPixelOffset = xPixelOffset;
            LiveWallpaper.this.yPixelOffset = yPixelOffset;
        }

        public void onTouchEvent(MotionEvent event) {
            LiveWallpaper.this.onTouchEvent(event);
        }

        private void start() {
            stop();
            if (this.visible) {
                this.running = true;
                run();
            }
        }

        private void stop() {
            this.running = false;
            this.handler.removeCallbacks(this);
        }

        public void run() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas c = holder.lockCanvas();
            if (c != null) {
                try {
                    LiveWallpaper.this.paint(c);
                } finally {
                    holder.unlockCanvasAndPost(c);
                }
            }
            if (this.running) {
                this.handler.postDelayed(this, (long) (1000 / LiveWallpaper.this.getFramerate()));
            }
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (sharedPreferences == this.prefs) {
                LiveWallpaper.this.loadPreferences(sharedPreferences);
            }
        }
    }

    protected abstract void paint(Canvas canvas);

    protected String getPreferencesName() {
        return null;
    }

    protected void loadPreferences(SharedPreferences prefs) {
    }

    protected int getFramerate() {
        return IndividualWallpaperService.framerateValue;
    }

    protected void onSizeChanged(int width, int height) {
    }

    protected void onTouchEvent(MotionEvent event) {
    }

    public final Engine onCreateEngine() {
        Log.d(TAG, "onCreateEngine()");
        return new FlashEngine();
    }
}
