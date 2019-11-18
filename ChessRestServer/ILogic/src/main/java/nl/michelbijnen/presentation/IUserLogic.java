package nl.michelbijnen.presentation;

public interface IUserLogic {
    Authentication login(String username, String password);
    boolean register(User user);
    boolean forgotPassword(String email);
    boolean update(User user, String currentPassword);
    boolean addScore(String userId, int score);
    User getUser(String userId);
}
