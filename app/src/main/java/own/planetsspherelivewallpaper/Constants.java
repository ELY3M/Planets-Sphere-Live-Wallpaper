package own.planetsspherelivewallpaper;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String appPath = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/PlanetsSphere/");
    public static final String scorefile = "score.txt";
    public static final String scorefilepath = appPath+scorefile;
}
