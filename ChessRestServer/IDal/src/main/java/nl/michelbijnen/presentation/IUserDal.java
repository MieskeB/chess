package nl.michelbijnen.presentation;

public interface IUserDal {
    String getPublicKey(String username, String password);
    String getPublicKey(String email);
    User getUser(String publicKey);
    String updatePrivateKey(String publicKey);
    boolean removePrivateKey(String publicKey);
    boolean createUser(User user);
    boolean resetPassword(String userId, String newPassword);
    boolean update(User user, String currentPassword);
    int getCurrentScore(String userId);
    boolean updateCurrentScore(String userId, int score);
}
