package id.sti.potek.controller;

import java.util.List;

import id.sti.potek.dao.KamarDAO;
import id.sti.potek.model.Kamar;
import id.sti.potek.service.KamarService;

public class KamarController {

    private final KamarService service = new KamarService();
    private final KamarDAO kamarDAO = new KamarDAO();


    public List<Kamar> cariKamar(String lokasi, String tipeKamar, int jumlahKamar) {
        return service.cariKamar(lokasi, tipeKamar, jumlahKamar);
    }

    public List<String> getAllLokasi() {
        return kamarDAO.getAllLokasi();
    }

    public boolean setKetersediaan(String idKamar, int tersedia) {
        return service.updateKetersediaan(idKamar, tersedia);
    }

    public boolean setHargaBaru(String idKamar, int hargaBaru) {
        return service.updateHarga(idKamar, hargaBaru);
    }
}
