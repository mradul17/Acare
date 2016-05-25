package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.avaje.ebean.Model;
import javax.persistence.FetchType;

import java.util.Date;

@Entity
public class Careplans extends Model {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public Doctors did;

    @ManyToOne
    public Patients pid;

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

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public Date updateAt;
}