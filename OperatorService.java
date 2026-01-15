import java.util.List;

public class OperatorService {

    public static double totalSetor() {
        double sum = 0;
        List<Transaksi> all = DataStore.getSemuaTransaksi();
        for (Transaksi t : all) {
            if (t instanceof TransaksiSetor) sum += t.total();
        }
        return sum;
    }

    public static double totalTarik() {
        double sum = 0;
        List<Transaksi> all = DataStore.getSemuaTransaksi();
        for (Transaksi t : all) {
            if (t instanceof TransaksiTarik) {
                TransaksiTarik tt = (TransaksiTarik) t;
                if (tt.getStatus() == StatusTransaksi.SUCCESS) sum += tt.total();
            }
        }
        return sum;
    }

    public static double totalSaldoNasabah() {
        double sum = 0;
        // DataStore tidak expose list internal, jadi hitung lewat transaksi global sederhana:
        // Untuk versi tugas, cukup: saldo = setor sukses - tarik sukses (akumulasi)
        // (atau kamu bisa extend DataStore untuk expose nasabahList)
        return totalSetor() - totalTarik();
    }
}