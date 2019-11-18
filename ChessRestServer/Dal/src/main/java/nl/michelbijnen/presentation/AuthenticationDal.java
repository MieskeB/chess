package nl.michelbijnen.presentation;

import java.sql.PreparedStatement;

public class AuthenticationDal extends MySqlConnector implements IAuthenticationDal {
    @Override
    public boolean isAuthenticated(String privateKey) {
        String query = "SELECT User.Id FROM User INNER JOIN Auth ON User.PrivateKey = Auth.Id WHERE Auth.Id = ? AND Auth.Expires > now()";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, privateKey);

            return this.executeQuery(stmt).next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.close();
        }
    }
}
