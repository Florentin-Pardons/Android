package be.flo.messagerie.Javabean;

import java.io.Serializable;
import java.util.Date;

public class Conversation implements Serializable {

    private int id;
    private String sujet;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSujet() {
        return sujet;
    }
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Conversation() {
    }

    public Conversation(int id, String sujet) {
        this.id = id;
        this.sujet = sujet;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", sujet='" + sujet + '\'' +
                '}';
    }
}
