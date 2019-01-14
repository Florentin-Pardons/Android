package be.flo.messagerie.Javabean;

import java.util.Date;

public class Message {

    private int id;
    private String text;
    private Date date;
    public Conversation conversation;
    public Utilisateur utilisateur;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Message() {
    }

    public Message(int id, String text, Date date, Conversation conversation, Utilisateur utilisateur) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.conversation = conversation;
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", conversation=" + conversation +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
