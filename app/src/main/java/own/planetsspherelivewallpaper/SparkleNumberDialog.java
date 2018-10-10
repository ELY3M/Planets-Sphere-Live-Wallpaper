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
    protected final int ScreenHeight = this.Display.getHeight();
    protected final int ScreenWidth = this.Display.getWidth();
    private LinearLayout SeekBarContainer;
    protected final String TAG = "Planets Sparkle Dial";
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
                Log.d(TAG, "!moveable");
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
                    Log.d(TAG, "down");
                }
                if (SparkleNumberDialog.this.touchToMove && System.currentTimeMillis() - SparkleNumberDialog.this.touchTime >= 300) {
                    Log.d(TAG, "move");
                    if (!SparkleNumberDialog.this.moveable && SparkleNumberDialog.this.touchToMove && System.currentTimeMillis() - SparkleNumberDialog.this.touchTime >= 300) {
                        SparkleNumberDialog.this.moveable = true;
                    }
                    if (SparkleNumberDialog.this.moveable && SparkleNumberDialog.this.touchToMove) {
                        Log.d(TAG, "moveable");
                    }
                }
                if (event.getAction() == 1) {
                    Log.d(TAG, "up");
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
            onDraw(this.canvas);
        }

        public void onStopTrackingTouch(SeekBar arg0) {
            onDraw(this.canvas);
        }
    }

    SparkleNumberDialog(Context context, DialogInterface.OnClickListener l, int defaultHand) {
        super(context);
        Context context2 = getContext();
        getContext();
        this.vibrator = (Vibrator) context2.getSystemService(Context.VIBRATOR_SERVICE);
        this.handLayout = new LinearLayout(getContext());
        this.positionViewContainer = new RelativeLayout(getContext());
        this.positionView = new PositionView(getContext());
        this.clock = new ImageButton(getContext());
        this.SeekBarContainer = new LinearLayout(getContext());
        this.xBarContainer = new LinearLayout(getContext());
        this.tx_xBar = new TextView(getContext());
        this.xBar = new SeekBar(getContext());
        this.buttonContainer = new LinearLayout(getContext());
        this.ok_btn = new Button(getContext());
        this.default_btn = new Button(getContext());
        this.invisible = new ImageView(getContext());
        this.enlargeView = new RelativeLayout(getContext());
        this.touchToMove = false;
        this.moveable = false;
        this.vibrated = false;
        getDefaultValues();
        this.clockX = this.defaultX;
        setTitle(R.string.set8);
        this.xSeekBarRange = 250;
        if (this.ScreenHeight > this.ScreenWidth) {
            this.portraitMode = true;
        } else {
            this.portraitMode = false;
        }
        if (this.portraitMode) {
            this.xSeekBarRange = 250;
            buildPortraitUI();
            return;
        }
        this.xSeekBarRange = this.ScreenWidth / 8;
    }

    private void buildPortraitUI() {
        this.handLayout.setLayoutParams(new LayoutParams(-2, -2));
        this.handLayout.setGravity(1);
        this.handLayout.setOrientation(LinearLayout.VERTICAL); //was 1
        this.handLayout.setPadding(10, 0, 10, 10);
        this.tx_xBar.setText(R.string.set8summary);
        this.tx_xBar.setPadding(10, 0, 0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.weight = 1.0f;
        this.xBar.setLayoutParams(params);
        this.xBar.setOnSeekBarChangeListener(this.positionView);
        this.xBar.setMax(this.xSeekBarRange);
        this.xBar.setProgress(IndividualWallpaperService.sparkleNumberValue);
        this.xBar.setPadding(5, 0, 5, 0);
        this.xBarContainer.setLayoutParams(params);
        this.xBarContainer.setOrientation(LinearLayout.VERTICAL); //was 1
        this.xBarContainer.addView(this.tx_xBar);
        this.xBarContainer.addView(this.xBar);
        this.SeekBarContainer.setLayoutParams(new LayoutParams(-1, -2));
        this.SeekBarContainer.setOrientation(LinearLayout.HORIZONTAL); //was 0
        this.SeekBarContainer.setPadding(0, 0, 0, 10);
        this.SeekBarContainer.addView(this.xBarContainer);
        this.ok_btn.setLayoutParams(params);
        this.ok_btn.setText(R.string.ok);
        this.ok_btn.setOnClickListener(this);
        this.default_btn.setLayoutParams(params);
        this.default_btn.setText(R.string.Default);
        this.default_btn.setOnClickListener(this);
        this.buttonContainer.setLayoutParams(new LayoutParams(-1, -2));
        this.buttonContainer.setOrientation(LinearLayout.HORIZONTAL); //was 0
        this.buttonContainer.addView(this.default_btn);
        this.buttonContainer.addView(this.ok_btn);
        this.enlargeView.setLayoutParams(new LayoutParams(-2, -2));
        this.enlargeView.addView(this.invisible);
        this.enlargeView.addView(this.buttonContainer);
        this.handLayout.addView(this.positionViewContainer);
        this.handLayout.addView(this.SeekBarContainer);
        this.handLayout.addView(this.enlargeView);
        setContentView(this.handLayout);
    }

    public void onClick(View v) {
        if (v == this.ok_btn) {
            IndividualWallpaperService.prefEditor.putInt(IndividualWallpaperService.SparkleNumberValueKey, IndividualWallpaperService.sparkleNumberValue);
            IndividualWallpaperService.prefEditor.commit();
            IndividualWallpaperService.sparkleNumberValue = IndividualWallpaperService.prefs.getInt(IndividualWallpaperService.SparkleNumberValueKey, 30);
            dismiss();
        }
        if (v == this.default_btn) {
            IndividualWallpaperService.sparkleNumberValue = 30;
            this.clockX = this.defaultX;
            this.xBarValue = 30;
            this.xBar.setProgress(IndividualWallpaperService.sparkleNumberValue);
        }
    }

    private void getDefaultValues() {
        this.defaultX = 2;
    }
}
