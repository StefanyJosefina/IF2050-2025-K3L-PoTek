package id.sti.potek.service;

import java.util.List;

import id.sti.potek.dao.KamarDAO;
import id.sti.potek.model.Kamar;

public class KamarService {
    private final KamarDAO dao = new KamarDAO();

    public List<Kamar> cariKamar(String lokasi, String tipeKamar, int jumlahKamar) {
        return dao.cariKamarTersedia(lokasi, tipeKamar, jumlahKamar);
    }

    public boolean updateKetersediaan(String idKamar, int tersedia) {
        return dao.updateKetersediaan(idKamar, tersedia);
    }

    public boolean updateHarga(String idKamar, int hargaBaru) {
        return dao.updateHarga(idKamar, hargaBaru);
    }
}