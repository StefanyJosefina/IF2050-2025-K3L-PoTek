package id.sti.potek.model;

public class Kamar {
    private String id;
    private String namaHotel;
    private String lokasi;
    private int harga;
    private int tersedia;
    private int jumlahKamar;
    private String tipeKamar;

    public Kamar(String idKamar, String namaHotel, String lokasi, int harga, int tersedia, int jumlahKamar, String tipeKamar) {
        this.id = idKamar;
        this.namaHotel = namaHotel;
        this.lokasi = lokasi;
        this.harga = harga;
        this.tersedia = tersedia;
        this.jumlahKamar = jumlahKamar;
        this.tipeKamar = tipeKamar;
    }

    public String getId() { return id; }
    public String getNamaHotel() { return namaHotel; }
    public String getLokasi() { return lokasi; }
    public int getHarga() { return harga; }
    public int getTersedia() { return tersedia; }
    public int getJumlahKamar() { return jumlahKamar; }
    public String getTipeKamar() { return tipeKamar; }

    public void setId(String id) { this.id = id; }
    public void setNamaHotel(String namaHotel) { this.namaHotel = namaHotel; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public void setHarga(int harga) { this.harga = harga; }
    public void setTersedia(int tersedia) { this.tersedia = tersedia; }
    public void setJumlahKamar(int jumlahKamar) { this.jumlahKamar = jumlahKamar; }
    public void setTipeKamar(String tipeKamar) { this.tipeKamar = tipeKamar; }
}