package single;

import java.util.ArrayList;
import java.util.List;

import vo.MissionData;

/**
 * Created by yanqijs on 2016/2/22.
 */
public class Mission {
    private static List<MissionData> ourInstance = new ArrayList<>();

    public static List<MissionData> getInstance() {
        if (ourInstance == null) {
            ourInstance = new ArrayList<>();
        }
        return ourInstance;
    }

    private Mission() {
    }
}
