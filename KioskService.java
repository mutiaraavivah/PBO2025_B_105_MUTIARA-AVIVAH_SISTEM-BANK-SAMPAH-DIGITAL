public class KioskService {

    // Mesin: setor => hitung otomatis => saldo langsung masuk
    public static TransaksiSetor prosesSetor(Nasabah nasabah, Sampah sampah, double beratKg) {
        if (nasabah == null || sampah == null || beratKg <= 0) return null;

        String id = DataStore.nextSetorId();
        String waktu = DataStore.now();

        TransaksiSetor ts = new TransaksiSetor(
                id, waktu, nasabah.getUsername(),
                sampah.getNama(), beratKg, sampah.getHargaPerKg()
        );

        nasabah.credit(ts.total());
        nasabah.addTransaksi(ts);
        DataStore.addTransaksiGlobal(ts);

        return ts;
    }

    // Mesin: tarik => cek saldo => jika cukup, saldo berkurang
    // Return null jika sukses, atau pesan error jika gagal.
    public static String prosesTarik(Nasabah nasabah, double nominal) {
        if (nasabah == null) return "Session nasabah tidak ditemukan.";
        if (nominal <= 0) return "Nominal harus > 0.";

        String id = DataStore.nextTarikId();
        String waktu = DataStore.now();

        boolean ok = nasabah.debit(nominal);
        if (!ok) {
            TransaksiTarik tt = new TransaksiTarik(id, waktu, nasabah.getUsername(), nominal, StatusTransaksi.FAILED);
            nasabah.addTransaksi(tt);
            DataStore.addTransaksiGlobal(tt);
            return "Saldo tidak cukup.";
        }

        TransaksiTarik tt = new TransaksiTarik(id, waktu, nasabah.getUsername(), nominal, StatusTransaksi.SUCCESS);
        nasabah.addTransaksi(tt);
        DataStore.addTransaksiGlobal(tt);

        return null;
    }
}