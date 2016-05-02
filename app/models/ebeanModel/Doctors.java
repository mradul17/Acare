 package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.avaje.ebean.Model;

@Entity
public class Doctors extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;

    @NotNull
    @Column(name="address1")
    public String address1;

    @NotNull
    @Column(name="address2")
    public String address2;

    @NotNull
    @Column(name="pincode")
    public String pinCode;

    @NotNull
    public String state;

    @NotNull
    public String country;

    @NotNull
    @Column(name="daytimephonenumber")
    public String dayTimePhonenumber;

    @NotNull
    @Column(name="offtimephonenumber")
    public String offTimePhonenumber;

    @NotNull
    public String email;

    @NotNull
    @Column(name="encryptedpassword")
    @JsonIgnore
    public String encryptedPassword;

    public static Finder<Long, Doctors> find = new Finder<Long,Doctors>(Doctors.class);
}