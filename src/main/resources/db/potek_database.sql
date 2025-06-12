package id.sti.potek.controller;

import id.sti.potek.model.Ulasan;
import id.sti.potek.service.UlasanService;

import java.util.List;

public class UlasanController {
    private final UlasanService service = new UlasanService();

    public void kirimUlasan(int idUser, String idKamar, int rating, String komentar) { // Ubah double ke int
        service.kirimUlasan(idUser, idKamar, rating, komentar);
    }

    public List<Ulasan> getUlasanKamar(String idKamar) {
        return service.getUlasanKamar(idKamar);
    }
}