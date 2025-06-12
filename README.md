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
| **No** | **Nama** | **NIM**  | **GitHub** |
| ------ | ------------------------- | ------------- | ----------- |
| 1      | Stefany Josefina Santono  | 18223116      | [@StefanyJosefina](https://github.com/StefanyJosefina) |
| 2      | Matilda Angelina Sumaryo  | 18222125      | [@millicecup](https://github.com/millicecup) |
| 3      | Theresia Ivana M S        | 18222126      | [@meerancor33](https://github.com/meerancor33) |
| 4      | Aulia Azka Azzahra        | 18223131      | [@auliaazkaazzahra](https://github.com/auliaazkaazzahra) |
| 5      | Sonya Putri Fadilah       | 18222138      | [@sonyaaputri](https://github.com/sonyaaputri) |

**Asisten:** Angelica Kierra Ninta Gurning

---

## Features
- Autentikasi
- Pemesanan Tiket
- Pemesanan Hotel
- Ulasan Hotel


---

## How To Run
1. Clone repository ini:
   ```git clone <URL_REPOSITORY>```
3. Pindah ke direktori repository menggunakan perintah `cd`
   ```cd <NAMA_FOLDER_REPOSITORY>```
4. Jalankan aplikasi
``` .\gradlew clean run ```
---

## Implemented Modules

| **Modul**     | **Deskripsi**                      |
|---------------|------------------------------------|
|User           | Menyimpan data pengguna            |
|Kamar          | Menyimpan detail kamar hotel       |
|Ulasan         | Review pengguna untuk kamar hotel  |
|Tiket          | Informasi tiket transportasi       |
|PemesananHotel | Pesanan kamar hotel                |
|PemesananTiket | Pemesanan tiket transportasi       |


---

## Database Tables

| **Nama Tabel** | **Atribut** |
|----------------|-------------|
|User           | idUser, nama, email, password, tgl_lahir           |
|Kamar          | idKamar, namaHotel, tipeKamar, lokasi, harga, tersedia, jumlahKamar,    |
|Ulasan         | idUlasan, idUser, idKamar, rating, komentar |
|Tiket          | idTiket, keberangkatan, tujuan, tanggal, jam, harga, totalKursi, tersediaKursi    |
|PemesananHotel | idPesananKamar, idKamar, idUser, tanggalCheckIn, tanggalCheckOut, namaPemesan, noHPPemesan, emailPemesan, jumlahKamar, jumlahTamu, totalHarga                |
|PemesananTiket | idPesanTiket, idTiket, idUser, namaPemesan, noHpPemesan, emailPemesan, namaPenumpang, noHpPenumpang, emailPenumpang, noKursi    |


