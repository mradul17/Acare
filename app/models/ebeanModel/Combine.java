package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Model;

import java.util.Date;

@Entity
public class Combine extends Model {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public String mname;

    public String mcode;

    public String msystemcode;

    public String msystemcodename;

    public String rname;

    public String rcode;

    public String rsystemcode;

    public String rsystemcodename;

    public String funits;

    public String period;

    public String dunits;

    public String quantity;

    public String startdate;

    public String enddate; 
}