package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Model;

@Entity
public class Practices extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Column(name="practiceName")
    public String practiceName;

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
    @Column(name="daytimephonenumber")
    public String dayTimePhonenumber;

    @NotNull
    @Column(name="offtimephonenumber")
    public String offTimePhonenumber;

    @NotNull
    public String email;

    public static Finder<Long, Practices> find = new Finder<Long,Practices>(Practices.class);
}

