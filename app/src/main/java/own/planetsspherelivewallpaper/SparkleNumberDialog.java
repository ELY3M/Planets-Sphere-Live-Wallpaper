package own.planetsspherelivewallpaper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SparkleNumberDialog extends Dialog implements OnClickListener {
    protected final Display Display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    protected final int ScreenHeight = Display.getHeight();
    protected final int ScreenWidth = Display.getWidth();
    private LinearLayout SeekBarContainer;
    protected final String TAG = "Planets Sparkle Speed Dial";
    private Bitmap bm_invisible;
    private LinearLayout buttonContainer;
    private ImageButton clock;
    private int clockX;
    protected int defaultX;
    private Button default_btn;
    private RelativeLayout enlargeView;
    private LinearLayout handLayout;
    private ImageView invisible;
    private boolean moveable;
    private Button ok_btn;
    private boolean portraitMode;
    private PositionView positionView;
    private RelativeLayout positionViewContainer;
    private long touchTime;
    private boolean touchToMove;
    private TextView tx_xBar;
    private boolean vibrated;
    private Vibrator vibrator;
    private SeekBar xBar;
    private LinearLayout xBarContainer;
    private int xBarValue;
    private int xSeekBarRange;

    private class PositionView extends View implements OnSeekBarChangeListener, OnTouchListener {
        Canvas canvas = new Canvas();

        public PositionView(Context context) {
            super(context);
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        public void onDraw(Canvas canvas) {
            SparkleNumberDialog.this.clockX = SparkleNumberDialog.this.xBarValue;
            if (!SparkleNumberDialog.this.moveable && SparkleNumberDialog.this.touchToMove && System.currentTimeMillis() - SparkleNumberDialog.this.touchTime >= 300) {
                Log.d("Sparkle Speed Dialog", "!moveable");
                if (!SparkleNumberDialog.this.vibrated) {
                    SparkleNumberDialog.this.vibrator.vibrate(100);
                    SparkleNumberDialog.this.vibrated = true;
                }
            }
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (v == SparkleNumberDialog.this.clock) {
                if (event.getAction() == 0 && !SparkleNumberDialog.this.touchToMove) {
                    SparkleNumberDialog.this.touchTime = System.currentTimeMillis();
                    SparkleNumberDialog.this.touchToMove = true;
                    Log.d("Sparkle Speed Dialog", "down");
                }
                if (SparkleNumberDialog.this.touchToMove && System.currentTimeMillis() - SparkleNumberDialog.this.touchTime >= 300) {
                    Log.d("Sparkle Speed Dialog", "move");
                    if (!SparkleNumberDialog.this.moveable && SparkleNumberDialog.this.touchToMove && System.currentTimeMillis() - SparkleNumberDialog.this.touchTime >= 300) {
                        SparkleNumberDialog.this.moveable = true;
                    }
                    if (SparkleNumberDialog.this.moveable && SparkleNumberDialog.this.touchToMove) {
                        Log.d("Sparkle Speed Dialog", "moveable");
                    }
                }
                if (event.getAction() == 1) {
                    Log.d("Sparkle Speed Dialog", "up");
                    SparkleNumberDialog.this.touchToMove = false;
                    SparkleNumberDialog.this.moveable = false;
                    SparkleNumberDialog.this.vibrated = false;
                }
            }
            return false;
        }

        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            IndividualWallpaperService.sparkleNumberValue = arg1;
        }

        public void onStartTrackingTouch(SeekBar arg0) {
            onDraw(canvas);
        }

        public void onStopTrackingTouch(SeekBar arg0) {
            onDraw(canvas);
        }
    }

    SparkleNumberDialog(Context context, DialogInterface.OnClickListener l, int defaultHand) {
        super(context);
        Context context2 = getContext();
        getContext();
        vibrator = (Vibrator) context2.getSystemService(Context.VIBRATOR_SERVICE);
        handLayout = new LinearLayout(getContext());
        positionViewContainer = new RelativeLayout(getContext());
        positionView = new PositionView(getContext());
        clock = new ImageButton(getContext());
        SeekBarContainer = new LinearLayout(getContext());
        xBarContainer = new LinearLayout(getContext());
        tx_xBar = new TextView(getContext());
        xBar = new SeekBar(getContext());
        buttonContainer = new LinearLayout(getContext());
        ok_btn = new Button(getContext());
        default_btn = new Button(getContext());
        invisible = new ImageView(getContext());
        enlargeView = new RelativeLayout(getContext());
        touchToMove = false;
        moveable = false;
        vibrated = false;
        getDefaultValues();
        clockX = defaultX;
        setTitle(R.string.set8);
        xSeekBarRange = 250;
        if (ScreenHeight > ScreenWidth) {
            portraitMode = true;
        } else {
            portraitMode = false;
        }
        if (portraitMode) {
            xSeekBarRange = 250;
            buildPortraitUI();
            return;
        }
        xSeekBarRange = ScreenWidth / 8;
    }

    private void buildPortraitUI() {
        handLayout.setLayoutParams(new LayoutParams(-2, -2));
        handLayout.setGravity(1);
        handLayout.setOrientation(LinearLayout.VERTICAL);
        handLayout.setPadding(10, 0, 10, 10);
        tx_xBar.setText(R.string.set8summary);
        tx_xBar.setPadding(10, 0, 0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.weight = 1.0f;
        xBar.setLayoutParams(params);
        xBar.setOnSeekBarChangeListener(positionView);
        xBar.setMax(xSeekBarRange);
        xBar.setProgress(IndividualWallpaperService.sparkleNumberValue);
        xBar.setPadding(5, 0, 5, 0);
        xBarContainer.setLayoutParams(params);
        xBarContainer.setOrientation(LinearLayout.VERTICAL);
        xBarContainer.addView(tx_xBar);
        xBarContainer.addView(xBar);
        SeekBarContainer.setLayoutParams(new LayoutParams(-1, -2));
        SeekBarContainer.setOrientation(LinearLayout.HORIZONTAL);
        SeekBarContainer.setPadding(0, 0, 0, 10);
        SeekBarContainer.addView(xBarContainer);
        ok_btn.setLayoutParams(params);
        ok_btn.setText(R.string.ok);
        ok_btn.setOnClickListener(this);
        default_btn.setLayoutParams(params);
        default_btn.setText(R.string.Default);
        default_btn.setOnClickListener(this);
        buttonContainer.setLayoutParams(new LayoutParams(-1, -2));
        buttonContainer.setOrientation(LinearLayout.HORIZONTAL);
        buttonContainer.addView(default_btn);
        buttonContainer.addView(ok_btn);
        enlargeView.setLayoutParams(new LayoutParams(-2, -2));
        enlargeView.addView(invisible);
        enlargeView.addView(buttonContainer);
        handLayout.addView(positionViewContainer);
        handLayout.addView(SeekBarContainer);
        handLayout.addView(enlargeView);
        setContentView(handLayout);
    }

    public void onClick(View v) {
        if (v == ok_btn) {
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.SparkleNumberValueKey, IndividualWallpaperService.sparkleNumberValue);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.sparkleNumberValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.SparkleNumberValueKey, 30);
            dismiss();
        }
        if (v == default_btn) {
            IndividualWallpaperService.sparkleNumberValue = 30;
            clockX = defaultX;
            xBarValue = 30;
            xBar.setProgress(IndividualWallpaperService.sparkleNumberValue);
        }
    }

    private void getDefaultValues() {
        defaultX = 2;
    }
}
