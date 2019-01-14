package be.flo.messagerie.Javabean;

public class Session {

    private static Session instance;
    private Utilisateur user;

    public Utilisateur getUser(){return user;}
    public void setUser(Utilisateur user){this.user=user;}

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
            instance.user=null;
        }
        return instance;
    }

    private Session(){}
}
