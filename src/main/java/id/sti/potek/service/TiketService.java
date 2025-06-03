package id.sti.potek.service;


import id.sti.potek.dao.TiketDAO;
import id.sti.potek.model.Tiket;
import java.util.List;

public class TiketService {
    private final TiketDAO tiketDAO = new TiketDAO();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        if (asal == null || tujuan == null || tanggal == null ||
            asal.isEmpty() || tujuan.isEmpty() || tanggal.isEmpty()) {
            throw new IllegalArgumentException("Semua field harus diisi.");
        }
        return tiketDAO.cariTiket(asal, tujuan, tanggal);
    }
}
