package aakashresearchlab.com.srmlib;

public class Signup_user
{
    String firstname,lastname;
    String regno;
    String key;

    public Signup_user(String firstname, String lastname, String regno, String branch,String key) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.regno = regno;
        this.key = key;
        this.branch = branch;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Signup_user(String firstname, String lastname, String regno, String branch)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.regno = regno;
        this.branch = branch;

    }

    String branch;
}
