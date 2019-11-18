package nl.michelbijnen;

import nl.michelbijnen.IUserRest;
import nl.michelbijnen.UserRest;

public class Factory {
    public static IUserRest getUserRest() {
        return new UserRest();
    }
}
