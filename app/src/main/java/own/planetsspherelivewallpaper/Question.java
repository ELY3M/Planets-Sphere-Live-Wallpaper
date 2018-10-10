package own.planetsspherelivewallpaper;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Random;

public class Question extends Activity {
    public static Context context;
    static int score;
    ImageView frage;
    RadioButton one;
    int questioncounter;
    int rightanswer;
    int rightanswerint;
    boolean set = true;
    ImageButton test1;
    TextView textone;
    TextView textthree;
    TextView texttwo;
    RadioButton three;
    RadioButton two;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.questioncounter = 0;
        context = this;
        setContentView(R.layout.question);
        this.rightanswer = 0;
        this.rightanswerint = 0;
        score = 0;
        this.one = (RadioButton) findViewById(R.id.radio1);
        this.two = (RadioButton) findViewById(R.id.radio2);
        this.three = (RadioButton) findViewById(R.id.radio3);
        this.textone = (TextView) findViewById(R.id.text1);
        this.texttwo = (TextView) findViewById(R.id.text2);
        this.textthree = (TextView) findViewById(R.id.text3);
        this.frage = (ImageView) findViewById(R.id.frage);
        this.test1 = (ImageButton) findViewById(R.id.question);
        this.test1.setVisibility(255);  //255
        if (this.set) {
            startGame();
        }
        if (this.questioncounter > 7) {
            new FinishDialog(context).show();
        }
        this.one.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Question.this.one.setChecked(true);
                Question.this.two.setClickable(false);
                Question.this.three.setClickable(false);
                Question.this.test1.setVisibility(View.VISIBLE);
                if (Question.this.rightanswer == 1) {
                    Question.this.textone.setTextColor(Disc.DefaultLightColor);
                } else {
                    Question.this.textone.setTextColor(-65536);
                }
            }
        });
        this.two.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Question.this.two.setChecked(true);
                Question.this.one.setClickable(false);
                Question.this.three.setClickable(false);
                Question.this.test1.setVisibility(View.VISIBLE);
                if (Question.this.rightanswer == 2) {
                    Question.this.texttwo.setTextColor(Disc.DefaultLightColor);
                } else {
                    Question.this.texttwo.setTextColor(-65536);
                }
            }
        });
        this.three.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Question.this.three.setChecked(true);
                Question.this.two.setClickable(false);
                Question.this.one.setClickable(false);
                Question.this.test1.setVisibility(View.VISIBLE);
                if (Question.this.rightanswer == 3) {
                    Question.this.textthree.setTextColor(Disc.DefaultLightColor);
                } else {
                    Question.this.textthree.setTextColor(-65536);
                }
            }
        });
        this.test1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Question.this.checkRadioButton(Question.this.one, Question.this.two, Question.this.three);
                Question.this.textone.setTextColor(-1);
                Question.this.texttwo.setTextColor(-1);
                Question.this.textthree.setTextColor(-1);
                Question.this.test1.setVisibility(255);
                Question.this.set = true;
                Question question = Question.this;
                question.questioncounter++;
                if (Question.this.questioncounter < 9) {
                    Question.this.startGame();
                }
            }
        });
    }

    private void startGame() {
        setRandomText(this.frage, this.textone, this.texttwo, this.textthree);
        this.set = false;
        this.test1.setVisibility(View.VISIBLE);
        this.one.setClickable(true);
        this.two.setClickable(true);
        this.three.setClickable(true);
        if (this.questioncounter > 7) {
            new FinishDialog(context).show();
            this.questioncounter = 0;
            this.one.setChecked(false);
            this.two.setChecked(false);
            this.three.setChecked(false);
            setRandomText(this.frage, this.textone, this.texttwo, this.textthree);
            startGame();
        }
    }

    private void setRandomText(ImageView q, TextView t1, TextView t2, TextView t3) {
        this.rightanswer = new Random().nextInt(3) + 1;
        if (this.rightanswer == 1) {
            getRandomQuestion(q, t1, this.rightanswerint);
            getOtherRandom(t2, this.rightanswerint);
            getOtherRandom(t3, this.rightanswerint);
        }
        if (this.rightanswer == 2) {
            getRandomQuestion(q, t2, this.rightanswerint);
            getOtherRandom(t1, this.rightanswerint);
            getOtherRandom(t3, this.rightanswerint);
        }
        if (this.rightanswer == 3) {
            getRandomQuestion(q, t3, this.rightanswerint);
            getOtherRandom(t1, this.rightanswerint);
            getOtherRandom(t2, this.rightanswerint);
        }
    }

    private void getOtherRandom(TextView t2, int right) {
        switch (new Random().nextInt(20)) {
            case 0:
                if (right == R.string.antwort1) {
                    t2.setText(R.string.antwort2);
                    return;
                } else {
                    t2.setText(R.string.antwort1);
                    return;
                }
            case 1:
                if (right == R.string.antwort2) {
                    t2.setText(R.string.antwort3);
                    return;
                } else {
                    t2.setText(R.string.antwort2);
                    return;
                }
            case 2:
                if (right == R.string.antwort3) {
                    t2.setText(R.string.antwort1);
                    return;
                } else {
                    t2.setText(R.string.antwort3);
                    return;
                }
            case 3:
                if (right == R.string.antwort4) {
                    t2.setText(R.string.antwort5);
                    return;
                } else {
                    t2.setText(R.string.antwort4);
                    return;
                }
            case 4:
                if (right == R.string.antwort5) {
                    t2.setText(R.string.antwort6);
                    return;
                } else {
                    t2.setText(R.string.antwort5);
                    return;
                }
            case 5:
                if (right == R.string.antwort6) {
                    t2.setText(R.string.antwort7);
                    return;
                } else {
                    t2.setText(R.string.antwort6);
                    return;
                }
            case 6:
                if (right == R.string.antwort7) {
                    t2.setText(R.string.antwort9);
                    return;
                } else {
                    t2.setText(R.string.antwort7);
                    return;
                }
            case 7:
                if (right == R.string.antwort8) {
                    t2.setText(R.string.antwort2);
                    return;
                } else {
                    t2.setText(R.string.antwort8);
                    return;
                }
            case 8:
                if (right == R.string.antwort9) {
                    t2.setText(R.string.antwort10);
                    return;
                } else {
                    t2.setText(R.string.antwort9);
                    return;
                }
            case 9:
                if (right == R.string.antwort10) {
                    t2.setText(R.string.antwort20);
                    return;
                } else {
                    t2.setText(R.string.antwort10);
                    return;
                }
            case 10:
                if (right == R.string.antwort11) {
                    t2.setText(R.string.antwort19);
                    return;
                } else {
                    t2.setText(R.string.antwort11);
                    return;
                }
            case 11:
                if (right == R.string.antwort12) {
                    t2.setText(R.string.antwort18);
                    return;
                } else {
                    t2.setText(R.string.antwort12);
                    return;
                }
            case 12:
                if (right == R.string.antwort13) {
                    t2.setText(R.string.antwort17);
                    return;
                } else {
                    t2.setText(R.string.antwort13);
                    return;
                }
            case 13:
                if (right == R.string.antwort14) {
                    t2.setText(R.string.antwort19);
                    return;
                } else {
                    t2.setText(R.string.antwort14);
                    return;
                }
            case 14:
                if (right == R.string.antwort15) {
                    t2.setText(R.string.antwort16);
                    return;
                } else {
                    t2.setText(R.string.antwort15);
                    return;
                }
            case 15:
                if (right == R.string.antwort16) {
                    t2.setText(R.string.antwort15);
                    return;
                } else {
                    t2.setText(R.string.antwort16);
                    return;
                }
            case 16:
                if (right == R.string.antwort17) {
                    t2.setText(R.string.antwort14);
                    return;
                } else {
                    t2.setText(R.string.antwort17);
                    return;
                }
            case 17:
                if (right == R.string.antwort18) {
                    t2.setText(R.string.antwort13);
                    return;
                } else {
                    t2.setText(R.string.antwort18);
                    return;
                }
            case 18:
                if (right == R.string.antwort19) {
                    t2.setText(R.string.antwort12);
                    return;
                } else {
                    t2.setText(R.string.antwort19);
                    return;
                }
            case 19:
                if (right == R.string.antwort20) {
                    t2.setText(R.string.antwort11);
                    return;
                } else {
                    t2.setText(R.string.antwort20);
                    return;
                }
            case 20:
                if (right == R.string.antwort21) {
                    t2.setText(R.string.antwort9);
                    return;
                } else {
                    t2.setText(R.string.antwort21);
                    return;
                }
            default:
                return;
        }
    }

    private void getRandomQuestion(ImageView q, TextView t1, int right) {
        int randomint = new Random().nextInt(21);
        Log.d("", "frage1" + randomint);
        switch (randomint) {
            case 0:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage1));
                t1.setText(R.string.antwort1);
                right = R.string.antwort1;
                break;
            case 1:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage2));
                t1.setText(R.string.antwort2);
                right = R.string.antwort2;
                break;
            case 2:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage3));
                t1.setText(R.string.antwort3);
                right = R.string.antwort3;
                break;
            case 3:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage4));
                t1.setText(R.string.antwort4);
                right = R.string.antwort4;
                break;
            case 4:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage5));
                t1.setText(R.string.antwort5);
                right = R.string.antwort5;
                break;
            case 5:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage6));
                t1.setText(R.string.antwort6);
                right = R.string.antwort6;
                break;
            case 6:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage7));
                t1.setText(R.string.antwort7);
                right = R.string.antwort7;
                break;
            case 7:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage8));
                t1.setText(R.string.antwort8);
                right = R.string.antwort8;
                break;
            case 8:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage9));
                t1.setText(R.string.antwort9);
                right = R.string.antwort9;
                break;
            case 9:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage10));
                t1.setText(R.string.antwort10);
                right = R.string.antwort10;
                break;
            case 10:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage11));
                t1.setText(R.string.antwort11);
                right = R.string.antwort11;
                break;
            case 11:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage12));
                t1.setText(R.string.antwort12);
                right = R.string.antwort12;
                break;
            case 12:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage13));
                t1.setText(R.string.antwort13);
                right = R.string.antwort13;
                break;
            case 13:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage14));
                t1.setText(R.string.antwort14);
                right = R.string.antwort14;
                break;
            case 14:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage15));
                t1.setText(R.string.antwort15);
                right = R.string.antwort15;
                break;
            case 15:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage16));
                t1.setText(R.string.antwort16);
                right = R.string.antwort16;
                break;
            case 16:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage17));
                t1.setText(R.string.antwort17);
                right = R.string.antwort17;
                break;
            case 17:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage18));
                t1.setText(R.string.antwort18);
                right = R.string.antwort18;
                break;
            case 18:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage19));
                t1.setText(R.string.antwort19);
                right = R.string.antwort19;
                break;
            case 19:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage20));
                t1.setText(R.string.antwort20);
                right = R.string.antwort20;
                break;
            case 20:
                q.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.frage21));
                t1.setText(R.string.antwort21);
                right = R.string.antwort21;
                break;
        }
        this.rightanswerint = right;
    }

    private void checkRadioButton(RadioButton one, RadioButton two, RadioButton three) {
        if (this.rightanswer == 1) {
            if (one.isChecked()) {
                score++;
            }
        } else if (this.rightanswer == 2) {
            if (two.isChecked()) {
                score++;
            }
        } else if (this.rightanswer == 3 && three.isChecked()) {
            score++;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
