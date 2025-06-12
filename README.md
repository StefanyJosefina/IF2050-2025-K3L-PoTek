# Tugas Besar IF2050 - Dasar Rekayasa Perangkat Lunak

## Table of Contents
- [Description](#description)
- [Contributors](#contributors)
- [Features](#features)
- [How To Run](#how-to-run)
- [Implemented Modules](#implemented-modules)
- [Database Tables](#database-tables)

---

## Description
PoTek (Pemesanan Online Tiket & Kamar) adalah aplikasi yang memudahkan pelanggan dalam memesan tiket transportasi dan kamar hotel secara online. Aplikasi ini dilengkapi empat fitur utama: autentikasi, pemesanan tiket, pemesanan kamar, dan ulasan kamar. Dengan layanan pelanggan yang responsif dan keamanan data yang terjamin, PoTek meningkatkan efisiensi dan kepuasan pelanggan di sektor perjalanan dan perhotelan

---

## Contributors
<table>
  <tr align="center">
    <td>
      <a href="https://github.com/StefanyJosefina"><strong>Stefany J. Santono</strong></a><br>
      <img src="https://github.com/StefanyJosefina.png" width="80" height="80"><br>
      18223116
    </td>
    <td>
      <a href="https://github.com/millicecup"><strong>Matilda A. Sumaryo</strong></a><br>
      <img src="https://github.com/millicecup.png" width="80" height="80"><br>
      18223125
    </td>
    <td>
      <a href="https://github.com/meerancor33"><strong>Theresia I. M. S.</strong></a><br>
      <img src="https://github.com/meerancor33.png" width="80" height="80"><br>
      18223126
    </td>
    <td>
      <a href="https://github.com/auliaazkaazzahra"><strong>Aulia A. Azzahra</strong></a><br>
      <img src="https://github.com/auliaazkaazzahra.png" width="80" height="80"><br>
      18223131
    </td>
    <td>
      <a href="https://github.com/sonyaaputri"><strong>Sonya P. Fadilah</strong></a><br>
      <img src="https://github.com/sonyaaputri.png" width="80" height="80"><br>
      18222138
    </td>
  </tr>
</table>

**Asisten:** Angelica Kierra Ninta Gurning

---

## Pembagian Tugas
| **Nama**                    | **Tugas**          |                  
|-----------------------------|--------------------|
| Stefany Josefina Santono    | Kamar              |
| Matilda Angelina Sumaryo    | Tiket              |
| Theresia Ivana M S          | Main dan Database  |
| Aulia Azka Azzahra          | User / Autentikasi |
| Sonya Putri Fadilah         | Ulasan             |

---

## Features
- Autentikasi
- Pemesanan Tiket
- Pemesanan Hotel
- Ulasan Hotel


---

## How To Run
1. Clone repository ini : ```git clone <URL_REPOSITORY>```
2. Pastikan file database potek_database.db berada di path yang sesuai agar aplikasi dapat menemukan file database. Pindah ke direktori repository menggunakan perintah : ```cd <NAMA_FOLDER_REPOSITORY>```   
3. Jalankan aplikasi : ``` .\gradlew clean run ```
   
---

## Implemented Modules

| **Modul**     | **Deskripsi**                      | **Capture Screen**|
|---------------|------------------------------------|-------------------
|User           | Menyimpan data pengguna            |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/HomeUI.jpeg)
|Kamar          | Menyimpan detail kamar hotel       |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/HotelUI.jpeg)
|Ulasan         | Review pengguna untuk kamar hotel  |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/UlasanUI.jpeg)
|Tiket          | Informasi tiket transportasi       |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/TiketUI.jpeg)
|PemesananHotel | Pesanan kamar hotel                |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/PemesananUI_Penginapan.png)
|PemesananTiket | Pemesanan tiket transportasi       |![Screenshot Aplikasi](https://github.com/StefanyJosefina/IF2050-2025-K3L-PoTek/blob/main/doc/PemesananUI_Transport.png)



---

## Database Tables

| 📦 Nama Tabel        | 🧬 Atribut                                                                 |
|----------------------|---------------------------------------------------------------------------|
| `User`               | `idUser`, `nama`, `email`, `password`, `tgl_lahir`                        |
| `Kamar`              | `idKamar`, `namaHotel`, `tipeKamar`, `lokasi`, `harga`, `tersedia`, `jumlahKamar` |
| `Ulasan`             | `idUlasan`, `idUser`, `idKamar`, `rating`, `komentar`                     |
| `Tiket`              | `idTiket`, `keberangkatan`, `tujuan`, `tanggal`, `jam`, `harga`, `totalKursi`, `tersediaKursi` |
| `PemesananHotel`     | `idPesananKamar`, `idKamar`, `idUser`, `tanggalCheckIn`, `tanggalCheckOut`, `namaPemesan`, `noHPPemesan`, `emailPemesan`, `jumlahKamar`, `jumlahTamu`, `totalHarga` |
| `PemesananTiket`     | `idPesanTiket`, `idTiket`, `idUser`, `namaPemesan`, `noHpPemesan`, `emailPemesan`, `namaPenumpang`, `noHpPenumpang`, `emailPenumpang`, `noKursi` |



