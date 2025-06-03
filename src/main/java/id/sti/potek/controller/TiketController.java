package id.sti.potek.controller;

import id.sti.potek.model.Tiket;
import id.sti.potek.service.TiketService;

import java.util.List;

public class TiketController {
    private final TiketService tiketService = new TiketService();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        return tiketService.cariTiket(asal, tujuan, tanggal);
    }
    public List<Tiket> getAllTiket() {
        return tiketService.getAllTiket();
    }
    public boolean simpanPemesanan(String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                   String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        return tiketService.simpanPemesanan(idTiket, namaPemesan, noHpPemesan, emailPemesan,
                namaPenumpang, noHpPenumpang, emailPenumpang, noKursi);
    }
    public List<Integer> getKursiTerbooking(String idTiket) {
        return tiketService.getKursiTerbooking(idTiket);
    }
    
}
