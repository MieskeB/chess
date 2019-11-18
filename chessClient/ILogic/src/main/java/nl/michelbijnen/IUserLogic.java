package nl.michelbijnen;

public interface IUserLogic {
    boolean login(String username, String password);
    boolean register(String username, String password, String firstName, String lastName, String email, String gender);
    boolean update(String username, String password, String firstName, String lastName, String email, String gender, String oldPassword);
    boolean resetPassword(String username);
    boolean getLoggedInUser();
    boolean findMatch();
    boolean cancelFindMatch();
}
