package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Model;

@Entity
public class Careplans extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(length=1000)
    @NotNull
    public String madicationName;

@NotNull
public Long patientid;
    

    public static Finder<Long, Careplans> find = new Finder<Long,Careplans>(Careplans.class);
}

