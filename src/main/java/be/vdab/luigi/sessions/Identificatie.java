package be.vdab.luigi.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Component      // data kept in session must be a bean
@SessionScope   // enables spring to produce one bean per session, not default one bean (for all sessions)

public class Identificatie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Email
    private String emailAdres;

    // public Identificatie() { }

    @Email
    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(@Email String emailAdres) {
        this.emailAdres = emailAdres;
    }
}

// Finally, the from property must match an email address format:
        // @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
        // private String from;

