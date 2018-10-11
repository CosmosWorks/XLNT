package technologies.lnthales.com.lttech;

public class StudentInfo {
    String name, ps, email, ph;
    int id;

    public StudentInfo() {
    }

    public StudentInfo(String ps, String name, String email, String ph) {
        this.ps = ps;
        this.name= name;
        this.email = email;
        this.ph =ph;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPs() {


        return ps;
    }

    public void setPs(String ps) {

        this.ps = ps;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {


        this.email = email;
    }

    public String getPh()
    {
        return ph;
    }

    public void setPh(String ph)
    {
        this.ph = ph;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }
}
