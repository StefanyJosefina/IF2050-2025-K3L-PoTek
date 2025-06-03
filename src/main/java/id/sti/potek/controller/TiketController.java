package id.sti.potek.controller;

import id.sti.potek.model.Tiket;
import id.sti.potek.service.TiketService;

import java.util.List;

public class TiketController {
    private final TiketService tiketService = new TiketService();

    public List<Tiket> cariTiket(String asal, String tujuan, String tanggal) {
        return tiketService.cariTiket(asal, tujuan, tanggal);
    }
}
