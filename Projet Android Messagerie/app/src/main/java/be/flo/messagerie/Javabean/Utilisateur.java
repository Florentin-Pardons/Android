package be.flo.messagerie.Javabean;

public class Utilisateur {

    private int id;
    private String pseudo;
    private String motDePasse;
    private boolean sexe;
    private String ville;
    private double latitude;
    private double longitude;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public boolean getSexe() {return sexe;}
    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public Utilisateur() {
    }

    public Utilisateur(int id, String pseudo, String motDePasse, boolean sexe, String ville, double latitude, double longitude) {
        this.id = id;
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.sexe = sexe;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", sexe=" + sexe +
                ", ville='" + ville + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}