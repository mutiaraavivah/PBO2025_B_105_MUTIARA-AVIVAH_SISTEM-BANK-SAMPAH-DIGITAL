public abstract class Transaksi {
    protected final String idTransaksi;
    protected final String waktu;
    protected final String usernameNasabah;
    protected StatusTransaksi status;

    public Transaksi(String idTransaksi, String waktu, String usernameNasabah, StatusTransaksi status) {
        this.idTransaksi = idTransaksi;
        this.waktu = waktu;
        this.usernameNasabah = usernameNasabah;
        this.status = status;
    }

    public String getIdTransaksi() { return idTransaksi; }
    public String getWaktu() { return waktu; }
    public String getUsernameNasabah() { return usernameNasabah; }
    public StatusTransaksi getStatus() { return status; }

    public abstract double total();
    public abstract String ringkasan();
}