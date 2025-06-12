package id.sti.potek.model;

public class Pemesanan {
    private String idPesanan;
    private String idUser;      // Tambahan untuk idUser
    private String idTiket;
    private String namaPemesan;
    private String noHpPemesan;
    private String emailPemesan;
    private String namaPenumpang;
    private String noHpPenumpang;
    private String emailPenumpang;
    private int noKursi;

    // Constructors
    public Pemesanan() {}
    
    public Pemesanan(String idPesanan, String idUser, String idTiket, String namaPemesan, 
                     String noHpPemesan, String emailPemesan, String namaPenumpang, 
                     String noHpPenumpang, String emailPenumpang, int noKursi) {
        this.idPesanan = idPesanan;
        this.idUser = idUser;
        this.idTiket = idTiket;
        this.namaPemesan = namaPemesan;
        this.noHpPemesan = noHpPemesan;
        this.emailPemesan = emailPemesan;
        this.namaPenumpang = namaPenumpang;
        this.noHpPenumpang = noHpPenumpang;
        this.emailPenumpang = emailPenumpang;
        this.noKursi = noKursi;
    }

    // Getters and Setters
    public String getIdPesanan() {
        return idPesanan;
    }
    
    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }
    
    public String getIdUser() {
        return idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    public String getIdTiket() {
        return idTiket;
    }
    
    public void setIdTiket(String idTiket) {
        this.idTiket = idTiket;
    }
    
    public String getNamaPemesan() {
        return namaPemesan;
    }
    
    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }
    
    public String getNoHpPemesan() {
        return noHpPemesan;
    }
    
    public void setNoHpPemesan(String noHpPemesan) {
        this.noHpPemesan = noHpPemesan;
    }
    
    public String getEmailPemesan() {
        return emailPemesan;
    }
    
    public void setEmailPemesan(String emailPemesan) {
        this.emailPemesan = emailPemesan;
    }
    
    public String getNamaPenumpang() {
        return namaPenumpang;
    }
    
    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }
    
    public String getNoHpPenumpang() {
        return noHpPenumpang;
    }
    
    public void setNoHpPenumpang(String noHpPenumpang) {
        this.noHpPenumpang = noHpPenumpang;
    }
    
    public String getEmailPenumpang() {
        return emailPenumpang;
    }
    
    public void setEmailPenumpang(String emailPenumpang) {
        this.emailPenumpang = emailPenumpang;
    }
    
    public int getNoKursi() {
        return noKursi;
    }
    
    public void setNoKursi(int noKursi) {
        this.noKursi = noKursi;
    }
}