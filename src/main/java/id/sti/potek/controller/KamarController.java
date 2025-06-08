package id.sti.potek.controller;

import id.sti.potek.model.Kamar;
import id.sti.potek.service.KamarService;

import java.util.List;

public class KamarController {
    private final KamarService service = new KamarService();

    public List<Kamar> cariKamar(String lokasi, String tanggal) {
        return service.cariKamar(lokasi, tanggal);
    }

    public boolean setKetersediaan(String idKamar, boolean tersedia) {
        return service.updateKetersediaan(idKamar, tersedia);
    }

    public boolean setHargaBaru(String idKamar, int hargaBaru) {
        return service.updateHarga(idKamar, hargaBaru);
    }
}
