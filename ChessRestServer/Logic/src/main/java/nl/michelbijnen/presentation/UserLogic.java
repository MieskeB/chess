package nl.michelbijnen.presentation;

import java.util.UUID;

public class UserLogic implements IUserLogic {

    private IUserDal context;

    public UserLogic(IUserDal context) {
        this.context = context;
    }

    @Override
    public Authentication login(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return null;
        }

        String publicKey = this.context.getPublicKey(username, password);

        if (publicKey.equals("")) {
            return null;
        }

        String privateKey = this.context.updatePrivateKey(publicKey);

        Authentication authentication = new Authentication();
        authentication.setPublicKey(publicKey);
        authentication.setPrivateKey(privateKey);
        return authentication;
    }

    @Override
    public boolean register(User user) {
        return this.context.createUser(user);
    }

    @Override
    public boolean forgotPassword(String email) {
        String publicKey = this.context.getPublicKey(email);
        String updatedPassword = UUID.randomUUID().toString().replace("-", "");
        //TODO send email to user with the new password
        return this.context.resetPassword(publicKey, updatedPassword);
    }

    @Override
    public User getUser(String userId) {
        return this.context.getUser(userId);
    }

    @Override
    public boolean update(User user, String currentPassword) {
        return this.context.update(user, currentPassword);
    }

    @Override
    public boolean addScore(String userId, int score) {
        User user = this.getUser(userId);
        return this.context.updateCurrentScore(userId, user.getScore() + score);
    }
}
