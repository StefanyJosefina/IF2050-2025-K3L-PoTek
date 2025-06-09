// model/User.java
package id.sti.potek.model;

public class User {
    private String idUser;
    private String nama;
    private String email;
    private String tgl_lahir;
    private String noHp;
    private String password;

    public User(String idUser, String nama, String tgl_lahir, String noHp, String email, String password) {
        this.idUser = idUser;
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.noHp = noHp;
        this.email = email;
        this.password = password;
}

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getTanggalLahir() {
        return tgl_lahir;
    }
    public void setTanggalLahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }
    public String getNoHp() {
        return noHp;
    }
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
