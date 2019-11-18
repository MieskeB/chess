package nl.michelbijnen.presentation;

public interface IAuthenticationLogic {
    boolean isAuthenticated(String privateKey);
}
