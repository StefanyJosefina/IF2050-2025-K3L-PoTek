package id.sti.potek.service;

import id.sti.potek.dao.UlasanDAO;
import id.sti.potek.model.Ulasan;

import java.util.List;

public class UlasanService {

    private final UlasanDAO dao = new UlasanDAO();

    public void kirimUlasan(int idUser, String idKamar, int rating, String komentar) { // Ubah double ke int
        Ulasan ulasan = new Ulasan(idUser, idKamar, rating, komentar);
        dao.insertUlasan(ulasan);
    }

    public List<Ulasan> getUlasanKamar(String idKamar) {
        return dao.getAllByKamar(idKamar);
    }
}