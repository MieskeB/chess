package nl.michelbijnen.presentation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class UserDal extends MySqlConnector implements IUserDal {
    @Override
    public String getPublicKey(String username, String password) {
        String query = "SELECT Id FROM User WHERE Username = ? AND Password = ?";
        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = this.executeQuery(stmt);

            if (resultSet.next()) {
                return resultSet.getString("Id");
            }

            return "";

        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            this.close();
        }
    }

    @Override
    public String getPublicKey(String email) {
        String query = "SELECT Id FROM User WHERE Email = ?";
        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, email);

            ResultSet resultSet = this.executeQuery(stmt);

            if (resultSet.next()) {
                return resultSet.getString("Id");
            }

            return "";

        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            this.close();
        }
    }

    @Override
    public User getUser(String publicKey) {
        String query = "SELECT * FROM User WHERE Id = ?";
        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, publicKey);

            ResultSet resultSet = this.executeQuery(stmt);

            if (resultSet.next()) {
                User user = new User();
                user.setId(publicKey);
                user.setUsername(resultSet.getString("Username"));
                user.setPassword("");
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getString("Gender"));
                user.setScore(resultSet.getInt("Score"));
                return user;
            }

            return null;

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            this.close();
        }
    }

    @Override
    public String updatePrivateKey(String publicKey) {
        String privateKey = UUID.randomUUID().toString();

        {
            String query = "INSERT INTO Auth(Id, Expires) VALUES(?, (now() + INTERVAL 1 DAY))";

            try {
                this.open();

                PreparedStatement stmt = this.getStatement(query);
                stmt.setString(1, privateKey);

                if (this.executeUpdate(stmt) == 0) {
                    return "";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            } finally {
                this.close();
            }
        }
        {
            String query = "UPDATE User SET PrivateKey = ? WHERE Id = ?";

            try {
                this.open();

                PreparedStatement stmt = this.getStatement(query);
                stmt.setString(1, privateKey);
                stmt.setString(2, publicKey);

                if (this.executeUpdate(stmt) == 0) {
                    return "";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            } finally {
                this.close();
            }
        }

        return privateKey;
    }

    @Override
    public boolean removePrivateKey(String publicKey) {
        String query = "UPDATE Auth SET Expires = (now() - INTERVAL 1 SECOND) WHERE Id = (SELECT PrivateKey FROM User WHERE Id = ?)";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, publicKey);

            return this.executeUpdate(stmt) != 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.close();
        }
    }

    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO User(Id, Username, Password, FirstName, LastName, Email, Gender, Score, PrivateKey) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getGender());
            stmt.setInt(8, user.getScore());
            stmt.setString(9, "0");

            return this.executeUpdate(stmt) != 0;

        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            this.close();
        }
    }

    @Override
    public boolean resetPassword(String userId, String newPassword) {
        String query = "UPDATE User SET Password = ? WHERE Id = ?";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, userId);

            return this.executeUpdate(stmt) != 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.close();
        }
    }

    @Override
    public boolean update(User user, String currentPassword) {
        String query = "UPDATE User SET Username = ?, Password = ?, FirstName = ?, LastName = ?, Email = ?, Gender = ? WHERE Id = ? AND Password = ?";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getId());
            stmt.setString(8, currentPassword);

            return this.executeUpdate(stmt) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.close();
        }
    }

    @Override
    public int getCurrentScore(String userId) {
        String query = "SELECT Score FROM User WHERE Id = ?";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = this.executeQuery(stmt);

            if (resultSet.next()) {
                return resultSet.getInt("Score");
            }

            return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            this.close();
        }
    }

    @Override
    public boolean updateCurrentScore(String userId, int score) {
        String query = "UPDATE User SET Score = ? WHERE Id = ?";

        try {
            this.open();

            PreparedStatement stmt = this.getStatement(query);
            stmt.setInt(1, score);
            stmt.setString(2, userId);

            return this.executeUpdate(stmt) != 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.close();
        }
    }
}
