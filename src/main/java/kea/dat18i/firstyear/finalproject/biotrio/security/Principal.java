package kea.dat18i.firstyear.finalproject.biotrio.security;


/**
 *  Stores our current logged-in user as well as is used to check whether this user
 *  exists in our database when authenticating a user in our CustomAuth class.
 *  Furthermore, the accessLevel attribute is used to make our navbar in
 *  our templates/fragments/ folder dynamic by checking which type of user
 *  is currently authenticated
 *
 */
public class Principal {

    private static int principal_id;
    private static String username;         // Can be either Employee username or Customer email
    private static String password;
    private static String phonenumber;      // Will be kept as null for employees as they don't have a phone number
    private static String accessLevel = ""; // Set to empty String value "" to avoid if-statement errors in our templates

    public Principal() {
    }


    public int getPrincipal_id() {
        return principal_id;
    }

    public void setPrincipal_id(int principal_id) {
        this.principal_id = principal_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }


    // for testing
    @Override
    public String toString() {
        return "Principal{" +
                "principal_id=" + principal_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }

    /**
     * Used to clear all stored data of a user from the Principal
     * to insure the integrity of our users data and provide robust
     * web security for our web application
     */
    public void clearAttributes() {
        principal_id = 0;
        username = null;
        password = null;
        phonenumber = null;
        accessLevel = "";
    }



}
