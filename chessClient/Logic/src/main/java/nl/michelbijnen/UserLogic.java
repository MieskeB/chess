package nl.michelbijnen;

public class UserLogic implements IUserLogic {

    IUserDal userDal;
    IController controller;

    public UserLogic(IUserDal userDal) {
        this.userDal = userDal;
    }

    @Override
    public boolean login(String username, String password) {
        this.userDal.login(username, password);
        return true;
    }

    @Override
    public boolean register(String username, String password, String firstName, String lastName, String email, String gender) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setGender(gender);
        this.userDal.register(user);
        return true;
    }

    @Override
    public boolean update(String username, String password, String firstName, String lastName, String email, String gender, String oldPassword) {
        return false;
    }

    @Override
    public boolean resetPassword(String username) {
        return false;
    }

    @Override
    public boolean getLoggedInUser() {
        this.userDal.getUser(Session.getInstance().getLoggedInUserId());
        return true;
    }

    @Override
    public boolean findMatch() {
        this.userDal.findMatch(Session.getInstance().getLoggedInUser().getUsername(), Session.getInstance().getLoggedInUser().getScore());
        return true;
    }

    @Override
    public boolean cancelFindMatch() {
        this.userDal.cancelFindMatch();
        return true;
    }
}
