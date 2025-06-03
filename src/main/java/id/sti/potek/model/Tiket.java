package id.sti.potek.model;


public class Tiket {
    private String idTiket;
    private String keberangkatan;
    private String tujuan;
    private String tanggal;
    private String jam;
    private int harga;
    private int tersediaKursi;

    // Constructors
    public Tiket() {}
    public Tiket(String idTiket, String keberangkatan, String tujuan, String tanggal, String jam, int harga, int tersediaKursi) {
        this.idTiket = idTiket;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.jam = jam;
        this.harga = harga;
        this.tersediaKursi = tersediaKursi;
    }
    public String getIdTiket() {
        return idTiket;
    }
    public void setIdTiket(String idTiket) {
        this.idTiket = idTiket;
    }
    public String getKeberangkatan() {
        return keberangkatan;
    }
    public void setKeberangkatan(String keberangkatan) {
        this.keberangkatan = keberangkatan;
    }
    public String getTujuan() {
        return tujuan;
    }
    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
    public String getTanggal() {
        return tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    public String getJam() {
        return jam;
    }
    public void setJam(String jam) {
        this.jam = jam;
    }
    public int getHarga() {
        return harga;
    }
    public void setHarga(int harga) {
        this.harga = harga;
    }
    public int getTersediaKursi() {
        return tersediaKursi;
    }
    public void setTersediaKursi(int tersediaKursi) {
        this.tersediaKursi = tersediaKursi;
    }

    // Getters & Setters
    // ...
}
