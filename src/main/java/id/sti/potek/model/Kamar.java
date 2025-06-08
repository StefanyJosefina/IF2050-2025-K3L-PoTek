package id.sti.potek.model;

public class Kamar {
    private String id;
    private String namaHotel;
    private String lokasi;
    private int harga;
    private boolean tersedia;
    private String tipeKamar;

    public Kamar(String id, String namaHotel, String lokasi, int harga, boolean tersedia, String tipe) {
        this.id = id;
        this.namaHotel = namaHotel;
        this.lokasi = lokasi;
        this.harga = harga;
        this.tersedia = tersedia;
        this.tipeKamar = tipeKamar;
    }

    public String getId() { return id; }
    public String getNamaHotel() { return namaHotel; }
    public String getLokasi() { return lokasi; }
    public int getHarga() { return harga; }
    public boolean isTersedia() { return tersedia; }
    public String getTipeKamar() { return tipeKamar; }

    public void setId(String id) { this.id = id; }
    public void setNamaHotel(String namaHotel) { this.namaHotel = namaHotel; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public void setHarga(int harga) { this.harga = harga; }
    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
    public void setTipe(String tipeKamar) { this.tipeKamar = tipeKamar; }
}