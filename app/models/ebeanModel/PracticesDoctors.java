package models.ebeanModel;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;


import com.avaje.ebean.Model;

@Entity
public class PracticesDoctors extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @NotNull
    @Column(name="pid")
    public Practices pid;

    @ManyToOne
    @NotNull
    @Column(name="did")
    public Doctors did;

    public static Finder<Long, PracticesDoctors> find = new Finder<Long,PracticesDoctors>(PracticesDoctors.class);
}
