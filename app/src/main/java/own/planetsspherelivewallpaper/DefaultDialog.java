package own.planetsspherelivewallpaper;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DefaultDialog extends Dialog implements OnClickListener {
    public static Context context;
    public static Editor prefEditor;
    public static SharedPreferences prefs;
    public int counter;
    public Button ende;
    public String feature = "";
    public int featurefirst = 0;
    public int featurelast = 0;

    public DefaultDialog(Context c) {
        super(c);
        context = c;
        setContentView(R.layout.defaultdialog);
        this.ende = (Button) findViewById(R.id.ende);
        this.ende.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == this.ende) {
            dismiss();
        }
    }
}
