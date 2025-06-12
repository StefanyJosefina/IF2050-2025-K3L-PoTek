package id.sti.potek.model;

public class Ulasan {
    private int idUser;
    private String namaUser;
    private String idKamar;
    private int rating; // Ubah dari double ke int sesuai database
    private String komentar;

    // Constructor untuk insert
    public Ulasan(int idUser, String idKamar, int rating, String komentar) {
        this.idUser = idUser;
        this.idKamar = idKamar;
        this.rating = rating;
        this.komentar = komentar;
    }

    // Constructor untuk tampilkan ulasan
    public Ulasan(String namaUser, String idKamar, int rating, String komentar) {
        this.namaUser = namaUser;
        this.idKamar = idKamar;
        this.rating = rating;
        this.komentar = komentar;
    }

    public int getIdUser() { return idUser; }
    public String getIdKamar() { return idKamar; }
    public int getRating() { return rating; } // Return int
    public String getKomentar() { return komentar; }
    public String getNamaUser() { return namaUser; }
}