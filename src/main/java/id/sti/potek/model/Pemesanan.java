package id.sti.potek.model;

import java.time.LocalDate;

public class Pemesanan {
    private String idPesanan;
    private String idTiket;           // null jika bukan transportasi
    private int noKursi;              // -1 jika bukan transportasi

    private String idKamar;           // null jika bukan hotel
    private LocalDate tanggalCheckIn;
    private LocalDate tanggalCheckOut;
    private int jumlahKamar;
    private int jumlahTamu;
    private int totalHarga;

    private String namaPemesan;
    private String noHpPemesan;
    private String emailPemesan;
    private String namaPenumpang;
    private String noHpPenumpang;
    private String emailPenumpang;

    public String getIdPesanan() { return idPesanan; }
    public void setIdPesanan(String idPesanan) { this.idPesanan = idPesanan; }

    public String getIdTiket() { return idTiket; }
    public void setIdTiket(String idTiket) { this.idTiket = idTiket; }

    public int getNoKursi() { return noKursi; }
    public void setNoKursi(int noKursi) { this.noKursi = noKursi; }

    public String getIdKamar() { return idKamar; }
    public void setIdKamar(String idKamar) { this.idKamar = idKamar; }

    public LocalDate getTanggalCheckIn() { return tanggalCheckIn; }
    public void setTanggalCheckIn(LocalDate tanggalCheckIn) { this.tanggalCheckIn = tanggalCheckIn; }

    public LocalDate getTanggalCheckOut() { return tanggalCheckOut; }
    public void setTanggalCheckOut(LocalDate tanggalCheckOut) { this.tanggalCheckOut = tanggalCheckOut; }

    public int getJumlahKamar() { return jumlahKamar; }
    public void setJumlahKamar(int jumlahKamar) { this.jumlahKamar = jumlahKamar; }

    public int getJumlahTamu() { return jumlahTamu; }
    public void setJumlahTamu(int jumlahTamu) { this.jumlahTamu = jumlahTamu; }

    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }

    public String getNamaPemesan() { return namaPemesan; }
    public void setNamaPemesan(String namaPemesan) { this.namaPemesan = namaPemesan; }

    public String getNoHpPemesan() { return noHpPemesan; }
    public void setNoHpPemesan(String noHpPemesan) { this.noHpPemesan = noHpPemesan; }

    public String getEmailPemesan() { return emailPemesan; }
    public void setEmailPemesan(String emailPemesan) { this.emailPemesan = emailPemesan; }

    public String getNamaPenumpang() { return namaPenumpang; }
    public void setNamaPenumpang(String namaPenumpang) { this.namaPenumpang = namaPenumpang; }

    public String getNoHpPenumpang() { return noHpPenumpang; }
    public void setNoHpPenumpang(String noHpPenumpang) { this.noHpPenumpang = noHpPenumpang; }

    public String getEmailPenumpang() { return emailPenumpang; }
    public void setEmailPenumpang(String emailPenumpang) { this.emailPenumpang = emailPenumpang; }
}
