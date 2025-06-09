package id.sti.potek.model;

import java.time.LocalDate;

public class PemesananHotel {
    private String idPesanan;
    private String idKamar;
    private LocalDate tanggalCheckIn;
    private LocalDate tanggalCheckOut;
    private int jumlahKamar;
    private int jumlahTamu;
    private int totalHarga;
    private String namaPemesan;
    private String noHpPemesan;
    private String emailPemesan;

    public String getIdPesanan() { return idPesanan; }
    public void setIdPesanan(String idPesanan) { this.idPesanan = idPesanan; }

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
}