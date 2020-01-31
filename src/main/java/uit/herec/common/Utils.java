package uit.herec.common;

import java.io.File;

public final class Utils {
    public static boolean deleteFolder(File dir) {
        if(dir == null) return false;
        if(dir.isDirectory()) {
            File[] files = dir.listFiles();
            if(files != null) {
                for (File file : files) {
                    if(file.isDirectory()) {
                        deleteFolder(file);
                    }else {
                        file.delete();
                    }
                }
            }
        }
        return dir.delete();
    }
}
