import java.util.ArrayList;
import java.util.List;

public class Nasabah extends User {
    private final String nama;
    private double saldo;
    private final List<Transaksi> riwayat = new ArrayList<>();

    public Nasabah(String nama, String username, String password) {
        super(username, password);
        this.nama = nama;
        this.saldo = 0.0;
    }

    public String getNama() { return nama; }
    public double getSaldo() { return saldo; }

    public void credit(double amount) {
        if (amount > 0) saldo += amount;
    }

    public boolean debit(double amount) {
        if (amount <= 0) return false;
        if (saldo < amount) return false;
        saldo -= amount;
        return true;
    }

    public void addTransaksi(Transaksi t) {
        if (t != null) riwayat.add(0, t); // terbaru di atas
    }

    public List<Transaksi> getRiwayat() {
        return riwayat;
    }

    public List<String> getRiwayatRingkasan() {
        List<String> out = new ArrayList<>();
        for (Transaksi t : riwayat) out.add(t.ringkasan());
        return out;
    }
}