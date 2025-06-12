package id.sti.potek.service;

import id.sti.potek.dao.UlasanDAO;
import id.sti.potek.model.Ulasan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UlasanService {

    private final UlasanDAO dao = new UlasanDAO();

    public void kirimUlasan(int idUser, String idKamar, double rating, String komentar) {
        String tanggal = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        Ulasan ulasan = new Ulasan(idUser, idKamar, rating, komentar, tanggal);
        dao.insertUlasan(ulasan);
    }

    public List<Ulasan> getUlasanKamar(String idKamar) {
        return dao.getAllByKamar(idKamar);
    }
}