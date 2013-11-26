package pl.edu.agh.dfs.models;

/**
 * This is class for keeping information about user of our application
 */
public class User {
    private long id;
    private String login;
    private String password;
    private String roleName;

    /**
     * Get user id.
     * @return Id of the user.
     */
    public long getId() {
        return id;
    }

    /**
     * Set user id.
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get user login.
     * @return Login of the user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set user login.
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get user password.
     * @return Password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user role.
     * @return Role of the user.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set user role.
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
