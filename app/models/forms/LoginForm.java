package models.forms;

import services.User;

import play.data.validation.ValidationError;
import play.data.validation.Constraints.Required;

import java.util.List;
import java.util.ArrayList;

public class LoginForm {

    @Required(message="Please enter email")
    public String email;

    @Required(message="Please enter password")
    public String password;

    /*public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<ValidationError>();
    	if(User.isValid(email,password)) {
    		return null;
    	}
        errors.add(new ValidationError("Credentials", "Invalid Credentials."));
    	return errors;
    }*/

    public String validate() {
        
        if(User.isValid(email,password)) {
            return null;
        }
        return "Invalid Credentials.";
    }
}