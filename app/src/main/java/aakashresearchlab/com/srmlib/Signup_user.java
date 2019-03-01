package aakashresearchlab.com.srmlib;

public class Signup_user
{
    String name;
    String regno;
    String key;
    String branch;

    public Signup_user(String name, String regno, String branch, String key) {
        this.name = name;
        this.regno = regno;
        this.key = key;
        this.branch = branch;
    }

    public Signup_user() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
