package own.planetsspherelivewallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryManager extends Activity {
    private static int COL_COUNT = -1;
    private static int ROW_COUNT = -1;
    public static int endminutes;
    public static int endsec;
    public static int endtries;
    private static Object lock = new Object();
    public static int score;
    private Drawable backImage;
    private ButtonListener buttonListener;
    private boolean[][] cardcheck;
    private int[][] cards;
    private Context context;
    private int extrapoints;
    private MemoryCard firstCard;
    private UpdateCardsHandler handler;
    private int hours;
    private List<Drawable> images;
    private ImageButton levelone;
    private ImageButton levelthree;
    private ImageButton leveltwo;
    private TableLayout mainTable;
    private int minutes;
    private int seconds;
    private MemoryCard seconedCard;
    int turns;

    String TAG = "Planets MemoryManager";

    class ButtonListener implements OnClickListener {
        ButtonListener() {
        }

        public void onClick(View v) {
            synchronized (MemoryManager.lock) {
                if (MemoryManager.this.firstCard == null || MemoryManager.this.seconedCard == null) {
                    int id = v.getId();
                    turnCard((Button) v, id / 100, id % 100);
                    return;
                }
            }
        }

        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable((Drawable) MemoryManager.this.images.get(MemoryManager.this.cards[x][y]));
            if (MemoryManager.this.firstCard == null) {
                MemoryManager.this.firstCard = new MemoryCard(button, x, y);
            } else if (MemoryManager.this.firstCard.x != x || MemoryManager.this.firstCard.y != y) {
                MemoryManager.this.seconedCard = new MemoryCard(button, x, y);
                MemoryManager memoryManager = MemoryManager.this;
                memoryManager.turns++;
                new Timer(false).schedule(new TimerTask() {
                    public void run() {
                        try {
                            synchronized (MemoryManager.lock) {
                                MemoryManager.this.handler.sendEmptyMessage(0);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }, 1300);
            }
        }
    }

    class UpdateCardsHandler extends Handler {
        UpdateCardsHandler() {
        }

        public void handleMessage(Message msg) {
            synchronized (MemoryManager.lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (MemoryManager.this.cards[MemoryManager.this.seconedCard.x][MemoryManager.this.seconedCard.y] == MemoryManager.this.cards[MemoryManager.this.firstCard.x][MemoryManager.this.firstCard.y]) {
                MemoryManager.this.firstCard.button.setVisibility(View.INVISIBLE);
                MemoryManager.this.seconedCard.button.setVisibility(View.INVISIBLE);
                MemoryManager.this.cardcheck[MemoryManager.this.seconedCard.x][MemoryManager.this.seconedCard.y] = false;
                MemoryManager.this.cardcheck[MemoryManager.this.firstCard.x][MemoryManager.this.firstCard.y] = false;
                MemoryManager memoryManager = MemoryManager.this;
                memoryManager.extrapoints = memoryManager.extrapoints + 150;
                boolean inter = false;
                for (int i = 0; i < MemoryManager.COL_COUNT; i++) {
                    for (int k = 0; k < MemoryManager.ROW_COUNT; k++) {
                        if (MemoryManager.this.cardcheck[i][k]) {
                            inter = true;
                        }
                    }
                }
                if (!inter) {
                    MemoryManager.score = 100000 / MemoryManager.this.turns;
                    Date curdt = new Date();
                    int curhours = curdt.getHours();
                    int curminutes = curdt.getMinutes();
                    curhours -= MemoryManager.this.hours;
                    curminutes -= MemoryManager.this.minutes;
                    int curseconds = curdt.getSeconds() - MemoryManager.this.seconds;
                    if (curseconds < 0) {
                        curseconds += 60;
                        curminutes--;
                    }
                    if (curminutes < 0) {
                        curminutes += 60;
                        curhours--;
                    }
                    if (curminutes != 0) {
                        MemoryManager.score /= curminutes;
                    }
                    if (curseconds != 0) {
                        MemoryManager.score /= curseconds;
                    }
                    MemoryManager.score += MemoryManager.this.extrapoints;
                    MemoryManager.endtries = MemoryManager.this.turns;
                    MemoryManager.endminutes = curminutes;
                    MemoryManager.endsec = curseconds;
                    MemoryManager.this.startActivity(new Intent(MemoryManager.this, MemoryErgebnisDialog.class));
                }
            } else {
                MemoryManager.this.seconedCard.button.setBackgroundDrawable(MemoryManager.this.backImage);
                MemoryManager.this.firstCard.button.setBackgroundDrawable(MemoryManager.this.backImage);
            }
            MemoryManager.this.firstCard = null;
            MemoryManager.this.seconedCard = null;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String myscore = "0";


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


        if (myscore != null) {
            score = Integer.parseInt(myscore);
        }
        this.extrapoints = 0;
        this.handler = new UpdateCardsHandler();
        setContentView(R.layout.main);
        this.buttonListener = new ButtonListener();
        this.mainTable = (TableLayout) findViewById(R.id.TableLayout03);
        this.levelone = (ImageButton) findViewById(R.id.level1);
        this.leveltwo = (ImageButton) findViewById(R.id.level2);
        this.levelthree = (ImageButton) findViewById(R.id.level3);
        this.levelone.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MemoryManager.this.newGame(3, 4);
            }
        });
        this.leveltwo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MemoryManager.score >= 670) {
                    MemoryManager.this.newGame(4, 5);
                } else {
                    new DefaultDialog(MemoryManager.this.context).show();
                }
            }
        });
        this.levelthree.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MemoryManager.score >= 25000) {
                    MemoryManager.this.newGame(6, 7);
                } else {
                    new DefaultDialog(MemoryManager.this.context).show();
                }
            }
        });
        this.context = this.mainTable.getContext();
    }

    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;
        long start = SystemClock.uptimeMillis();

        ///TODO This is bug!!!  smashing images.....
        /*
        if (c > 3) {
            loadImages();
            this.backImage = getResources().getDrawable(R.drawable.memoryicon1);
        } else {
            loadImages2();
            this.backImage = getResources().getDrawable(R.drawable.memoryicon2);
        }
        */

        loadImages2();
        this.backImage = getResources().getDrawable(R.drawable.memoryicon2);

        this.cards = (int[][]) Array.newInstance(Integer.TYPE, new int[]{COL_COUNT, ROW_COUNT});
        this.cardcheck = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{COL_COUNT, ROW_COUNT});
        for (int i = 0; i < COL_COUNT; i++) {
            for (int k = 0; k < ROW_COUNT; k++) {
                this.cardcheck[i][k] = true;
            }
        }
        this.mainTable.removeView(findViewById(R.id.TableRow01));
        this.mainTable.removeView(findViewById(R.id.TableRow02));
        TableRow tr = (TableRow) findViewById(R.id.TableRow03);
        tr.removeAllViews();
        this.mainTable = new TableLayout(this.context);
        tr.addView(this.mainTable);
        for (int y = 0; y < ROW_COUNT; y++) {
            this.mainTable.addView(createRow(y));
        }
        this.firstCard = null;
        loadCards();
        this.turns = 0;
        Date dt = new Date();
        this.hours = dt.getHours();
        this.minutes = dt.getMinutes();
        this.seconds = dt.getSeconds();
    }

    private void loadImages() {
        ArrayList<Drawable> img = new ArrayList();
        img.add(getResources().getDrawable(R.drawable.frage1));
        img.add(getResources().getDrawable(R.drawable.frage2));
        img.add(getResources().getDrawable(R.drawable.frage3));
        img.add(getResources().getDrawable(R.drawable.frage4));
        img.add(getResources().getDrawable(R.drawable.frage5));
        img.add(getResources().getDrawable(R.drawable.frage6));
        img.add(getResources().getDrawable(R.drawable.frage7));
        img.add(getResources().getDrawable(R.drawable.frage8));
        img.add(getResources().getDrawable(R.drawable.frage9));
        img.add(getResources().getDrawable(R.drawable.frage10));
        img.add(getResources().getDrawable(R.drawable.frage11));
        img.add(getResources().getDrawable(R.drawable.frage12));
        img.add(getResources().getDrawable(R.drawable.frage13));
        img.add(getResources().getDrawable(R.drawable.frage14));
        img.add(getResources().getDrawable(R.drawable.frage15));
        img.add(getResources().getDrawable(R.drawable.frage16));
        img.add(getResources().getDrawable(R.drawable.frage17));
        img.add(getResources().getDrawable(R.drawable.frage18));
        img.add(getResources().getDrawable(R.drawable.frage19));
        img.add(getResources().getDrawable(R.drawable.frage20));
        img.add(getResources().getDrawable(R.drawable.frage21));
        this.images = img;
    }

    private void loadImages2() {
        ArrayList<Drawable> img = new ArrayList();
        img.add(getResources().getDrawable(R.drawable.mem1));
        img.add(getResources().getDrawable(R.drawable.mem2));
        img.add(getResources().getDrawable(R.drawable.mem3));
        img.add(getResources().getDrawable(R.drawable.mem4));
        img.add(getResources().getDrawable(R.drawable.mem5));
        img.add(getResources().getDrawable(R.drawable.mem6));
        img.add(getResources().getDrawable(R.drawable.mem7));
        img.add(getResources().getDrawable(R.drawable.mem8));
        img.add(getResources().getDrawable(R.drawable.mem9));
        img.add(getResources().getDrawable(R.drawable.mem10));
        img.add(getResources().getDrawable(R.drawable.mem11));
        img.add(getResources().getDrawable(R.drawable.mem12));
        img.add(getResources().getDrawable(R.drawable.mem13));
        img.add(getResources().getDrawable(R.drawable.mem14));
        img.add(getResources().getDrawable(R.drawable.mem15));
        img.add(getResources().getDrawable(R.drawable.mem16));
        img.add(getResources().getDrawable(R.drawable.mem17));
        img.add(getResources().getDrawable(R.drawable.mem18));
        img.add(getResources().getDrawable(R.drawable.mem19));
        img.add(getResources().getDrawable(R.drawable.mem20));
        img.add(getResources().getDrawable(R.drawable.mem21));
        this.images = img;
    }

    private void loadCards() {
        try {
            int i;
            int size = ROW_COUNT * COL_COUNT;
            Log.i(TAG, "size=" + size);
            ArrayList<Integer> list = new ArrayList();
            for (i = 0; i < size; i++) {
                list.add(new Integer(i));
            }
            Random r = new Random();
            for (i = size - 1; i >= 0; i--) {
                int t = 0;
                if (i > 0) {
                    t = r.nextInt(i);
                }
                this.cards[i % COL_COUNT][i / COL_COUNT] = ((Integer) list.remove(t)).intValue() % (size / 2);
                Log.i(TAG, "card[" + (i % COL_COUNT) + "][" + (i / COL_COUNT) + "]=" + this.cards[i % COL_COUNT][i / COL_COUNT]);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private TableRow createRow(int y) {
        TableRow row = new TableRow(this.context);
        row.setHorizontalGravity(17);
        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    private View createImageButton(int x, int y) {
        Button button = new Button(this.context);
        button.setBackgroundDrawable(this.backImage);
        button.setId((x * 100) + y);
        button.setOnClickListener(this.buttonListener);
        return button;
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}
