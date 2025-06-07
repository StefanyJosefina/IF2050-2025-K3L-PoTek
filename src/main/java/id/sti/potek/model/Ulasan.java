package id.sti.potek.model;

public class Ulasan {
    private int id;
    private int idUser;
    private String idKamar;
    private double rating;
    private String komentar;
    private String tanggal;

    public Ulasan(int idUser, String idKamar, double rating, String komentar, String tanggal) {
        this.idUser = idUser;
        this.idKamar = idKamar;
        this.rating = rating;
        this.komentar = komentar;
        this.tanggal = tanggal;
    }

    public int getIdUser() { return idUser; }
    public String getIdKamar() { return idKamar; }
    public double getRating() { return rating; }
    public String getKomentar() { return komentar; }
    public String getTanggal() { return tanggal; }
}