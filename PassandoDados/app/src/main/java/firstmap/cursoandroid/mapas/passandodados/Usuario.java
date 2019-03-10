package firstmap.cursoandroid.mapas.passandodados;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String login;
    private String mail;

    public Usuario(String login, String mail){
        this.login = login;
        this.mail=mail;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }


}
