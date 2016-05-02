package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Model;

@Entity
public class Patients extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;

    @NotNull
    @Column(name="dob") //Date Of Birth
    public String dob;

    @NotNull
    public String address1;

    @NotNull
    public String address2;

    @NotNull
    @Column(name="pincode")
    public String pinCode;

    @NotNull
    public String state;

    @NotNull
    public String country;

    @NotNull
    @Column(name="contactNumber1")
    public String contactNumber1;

    @NotNull
    @Column(name="contactNumber2")
    public String contactNumber2;

    @NotNull
    public String email;

    public static Finder<Long, Patients> find = new Finder<Long,Patients>(Patients.class);
}

