package xyz.imxqd.day_11;

/**
 * Created by imxqd on 2016/12/4.
 */

public class User {

    private String name;
    private String number;
    private boolean isChecked;
    private Sex sex;

    enum Sex {
        Male, Female
    }

    public User(String name, String number, boolean isChecked, Sex sex) {
        this.name = name;
        this.number = number;
        this.isChecked = isChecked;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return name + " : " + number + "\n";
    }

    public String detailString() {
        return "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", isChecked=" + isChecked +
                ", sex=" + sex;
    }
}
