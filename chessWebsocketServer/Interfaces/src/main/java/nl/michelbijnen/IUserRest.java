package nl.michelbijnen;

public interface IUserRest {
    boolean addScore(String privateKey, String userId, int score);
    boolean forgotPassword(String email);
    User getUser(String privateKey, String userId);
    Authentication login(String username, String password);
    boolean logout(String privateKey, String userId);
    boolean register(User user);
    boolean update(String privateKey, User user, String oldPassword);
}
