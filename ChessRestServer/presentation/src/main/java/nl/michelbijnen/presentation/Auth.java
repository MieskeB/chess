package nl.michelbijnen.presentation;

public class Auth {

    private IAuthenticationLogic iAuthenticationLogic = Factory.getIAuthenticationLogic();

    public boolean authentication(String privateKey) {
        return iAuthenticationLogic.isAuthenticated(privateKey);
    }
}
