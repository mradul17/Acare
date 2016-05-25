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

import java.util.Date;

@Entity
public class Question extends Model {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public Long questionNumber;

    @ManyToOne
    public Doctors did;

    @ManyToOne
    public Patients pid;

    public String questionType;

    public String question;

    public String dependOnPreviousQuestion;

    public String previousQuestionAnswerShouldBe;

    public String choices;
}