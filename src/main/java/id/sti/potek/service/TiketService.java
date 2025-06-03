package id.sti.potek.service;


import java.util.List;

import id.sti.potek.dao.TiketDAO;
import id.sti.potek.model.Tiket;

public class TiketService {
    private final TiketDAO tiketDAO = new TiketDAO();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        if (asal == null || tujuan == null || tanggal == null ||
            asal.isEmpty() || tujuan.isEmpty() || tanggal.isEmpty()) {
            throw new IllegalArgumentException("Semua field harus diisi.");
        }
        return tiketDAO.cariTiket(asal, tujuan, tanggal);
    }
    public List<Tiket> getAllTiket() {
        return tiketDAO.getAllTiket();
    }
    public boolean simpanPemesanan(String idTiket, String namaPemesan, String noHpPemesan, String emailPemesan,
                                    String namaPenumpang, String noHpPenumpang, String emailPenumpang, int noKursi) {
        if (idTiket == null || namaPemesan == null || noHpPemesan == null || emailPemesan == null ||
            namaPenumpang == null || noHpPenumpang == null || emailPenumpang == null ||
            idTiket.isEmpty() || namaPemesan.isEmpty() || noHpPemesan.isEmpty() || emailPemesan.isEmpty() ||
            namaPenumpang.isEmpty() || noHpPenumpang.isEmpty() || emailPenumpang.isEmpty()) {
            throw new IllegalArgumentException("Semua field harus diisi.");
        }
        return tiketDAO.simpanPemesanan(idTiket, namaPemesan, noHpPemesan, emailPemesan,
                namaPenumpang, noHpPenumpang, emailPenumpang, noKursi);
    }
    public List<Integer> getKursiTerbooking(String idTiket) {
        if (idTiket == null || idTiket.isEmpty()) {
            throw new IllegalArgumentException("ID Tiket harus diisi.");
        }
        return tiketDAO.getKursiTerbooking(idTiket);
    }
    
}
