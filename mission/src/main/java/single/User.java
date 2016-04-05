package single;

import java.util.ArrayList;
import java.util.List;

import vo.MissionData;
import vo.UserData;

/**
 * Created by yanqijs on 2016/2/18.
 */
public class User {
    private static UserData ourInstance = new UserData();

    public static UserData getInstance() {
        if (ourInstance == null)
            ourInstance = new UserData();
        return ourInstance;
    }

    private User() {

    }
}
