package nl.michelbijnen;

public interface IUserDal {
    void login(String username, String password);
    void register(User user);
    void update(User user, String currentPassword);
    void resetPassword(String username);
    void getUser(String userId);
    void findMatch(String username, int score);
    void cancelFindMatch();
}
