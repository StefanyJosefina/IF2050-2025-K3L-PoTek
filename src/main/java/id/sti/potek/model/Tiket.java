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
    public int getTotalKursi() {
        return tersediaKursi; 
    }
    public int getWaktuPerjalanan() {
        String kota1 = keberangkatan.toLowerCase();
        String kota2 = tujuan.toLowerCase();
        String kode = idTiket != null ? idTiket.toUpperCase() : "T";

        // Tiket biasa
        if (kode.startsWith("T")) {
            if ((kota1.equals("jakarta") && kota2.equals("bandung")) ||
                (kota1.equals("bandung") && kota2.equals("jakarta"))) {
                return 3;
            }
            if ((kota1.equals("jakarta") && kota2.equals("surabaya")) ||
                (kota1.equals("surabaya") && kota2.equals("jakarta"))) {
                return 12;
            }
            if ((kota1.equals("padang") && kota2.equals("bandung")) ||
                (kota1.equals("bandung") && kota2.equals("padang"))) {
                return 28;
            }
        }

        // Kereta
        if (kode.startsWith("K")) {
            if ((kota1.equals("jakarta") && kota2.equals("bandung")) ||
                (kota1.equals("bandung") && kota2.equals("jakarta"))) {
                return 1; // 45 menit → dibulatkan jadi 1 jam
            }
            if ((kota1.equals("jakarta") && kota2.equals("surabaya")) ||
                (kota1.equals("surabaya") && kota2.equals("jakarta"))) {
                return 9;
            }
        }

        // Pesawat
        if (kode.startsWith("P")) {
            if ((kota1.equals("jakarta") && kota2.equals("padang")) ||
                (kota1.equals("padang") && kota2.equals("jakarta"))) {
                return 3;
            }
        }

        // Default jika tidak cocok
        return 2;
    }


    // Getters & Setters
    // ...
}
