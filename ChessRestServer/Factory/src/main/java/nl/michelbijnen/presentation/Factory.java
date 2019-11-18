package nl.michelbijnen.presentation;

public class Factory {

    public static IUserLogic getIUserLogic() {
        return new UserLogic(new UserDal());
    }

    public static IAuthenticationLogic getIAuthenticationLogic() {
        return new AuthenticationLogic(new AuthenticationDal());
    }
}
