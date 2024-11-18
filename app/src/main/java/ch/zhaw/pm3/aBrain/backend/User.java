package ch.zhaw.pm3.aBrain.backend;

import java.sql.Blob;
import java.util.Date;

/**
 * The type User.
 *
 * @author baermlau, wilphi01
 * @version 1.0
 */
public class User {
    
    private static final User user = new User();
    public enum Sex{FEMALE(1), MALE(2), OTHER(3);
        int numericalValue;
        Sex(int numericalValue) {
            this.numericalValue = numericalValue;
        }
        public int getNumericalValue() {
            return numericalValue;
        }
    }

    private int userId;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Sex sex;
    private Address address;
    private String email;
    private String password;
    private Blob profilePicture;
    private int points;
    
    public static User getInstance(){
        return user;
    }
    
    /**
     * Instantiates a new User.
     *
     * @param userId     the user id
     * @param firstName  the first name
     * @param lastName   the last name
     * @param birthday   the birthday
     * @param sex        the sex
     * @param postalCode the postal code
     * @param city       the city
     * @param country    the country
     * @param email      the email
     * @param password   the password
     * @param points     the points
     */
    public void setUser(int userId, String firstName, String lastName, Date birthday, Sex sex, String postalCode, String city, String country, String email, String password, Blob profilePicture, int points) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.sex = sex;
        this.address = new Address(postalCode,city,country);
        this.email = email; // TODO check email format
        this.password = password;
        this.profilePicture = profilePicture;
        this.points = points;
    }
    
    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }
    
    /**
     * Gets sex.
     *
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }
    
    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {return address;}
    
    
    /**
     * Add and get points int.
     *
     * @param points the points
     * @return the int
     */
    public int addAndGetPoints(int points){
        this.points += points;
        return this.points;
    }
    
    /**
     * Get points int.
     *
     * @return the int
     */
    public int getPoints(){
        return points;
    }
    
    /**
     * The type Address.
     */
    record Address(String postalCode, String city, String country) { }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }


}
