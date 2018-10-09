package own.planetsspherelivewallpaper;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class List extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] feature = getResources().getStringArray(R.array.feature);
        String[] featurevalue = getResources().getStringArray(R.array.featurevalue);
        String[] list = new String[feature.length];
        for (int i = 0; i < feature.length; i++) {
            list[i] = featurevalue[i] + " points     " + feature[i];
        }
        setListAdapter(new ArrayAdapter(this, R.layout.list, list));

    }

    public void onDestroy() {
        super.onDestroy();
    }
}
