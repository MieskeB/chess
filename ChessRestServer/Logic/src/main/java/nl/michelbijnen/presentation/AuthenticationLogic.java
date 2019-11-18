package nl.michelbijnen.presentation;

public class AuthenticationLogic implements IAuthenticationLogic {

    private IAuthenticationDal context;

    public AuthenticationLogic(IAuthenticationDal context) {
        this.context = context;
    }

    @Override
    public boolean isAuthenticated(String privateKey) {
        return this.context.isAuthenticated(privateKey);
    }
}
