package id.sti.potek.service;

import id.sti.potek.dao.KamarDAO;
import id.sti.potek.model.Kamar;

import java.util.List;

public class KamarService {
    private final KamarDAO dao = new KamarDAO();

    public List<Kamar> cariKamar(String lokasi, String tanggal) {
        return dao.filterKamar(lokasi, tanggal);
    }

    public boolean updateKetersediaan(String idKamar, boolean tersedia) {
        return dao.updateKetersediaan(idKamar, tersedia);
    }

    public boolean updateHarga(String idKamar, int hargaBaru) {
        return dao.updateHarga(idKamar, hargaBaru);
    }
}