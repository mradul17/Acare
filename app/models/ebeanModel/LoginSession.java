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
public class LoginSession extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
  
    @NotNull
    public Long userId;

    @NotNull
    public String token;

    @NotNull
    @Column(columnDefinition="datetime")
    public Date createAt;

    @NotNull
    @Column(columnDefinition="datetime")
    public Date expireAt;

    @NotNull
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public Date lastModifiedAt;

    @NotNull
    public Boolean expired;

    @NotNull
    public String ipAddresses;

    public static Finder<Long, LoginSession> find = new Finder<Long,LoginSession>(LoginSession.class);
}