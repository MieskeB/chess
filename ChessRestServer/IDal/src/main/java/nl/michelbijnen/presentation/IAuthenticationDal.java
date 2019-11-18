package nl.michelbijnen.presentation;

public interface IAuthenticationDal {
    boolean isAuthenticated(String privateKey);
}
