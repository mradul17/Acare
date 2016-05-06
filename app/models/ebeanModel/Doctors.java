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

    public String name;

    @Column(name="address1")
    public String address1;

    @Column(name="address2")
    public String address2;

    @Column(name="pincode")
    public String pinCode;

    public String state;

    public String country;
    
    @Column(name="daytimephonenumber")
    public String dayTimePhonenumber;
    
    @Column(name="offtimephonenumber")
    public String offTimePhonenumber;

    
    public String email;

    @Column(name="encryptedpassword")
    @JsonIgnore
    public String encryptedPassword;

    public String token;

    public Boolean activate;

    public static Finder<Long, Doctors> find = new Finder<Long,Doctors>(Doctors.class);
}