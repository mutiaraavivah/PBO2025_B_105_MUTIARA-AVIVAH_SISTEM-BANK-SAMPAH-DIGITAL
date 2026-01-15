public class TransaksiSetor extends Transaksi {
    private final String namaSampah;
    private final double beratKg;
    private final double hargaPerKg;

    public TransaksiSetor(String id, String waktu, String usernameNasabah,
                          String namaSampah, double beratKg, double hargaPerKg) {
        super(id, waktu, usernameNasabah, StatusTransaksi.SUCCESS);
        this.namaSampah = namaSampah;
        this.beratKg = beratKg;
        this.hargaPerKg = hargaPerKg;
    }

    public String getNamaSampah() { return namaSampah; }
    public double getBeratKg() { return beratKg; }
    public double getHargaPerKg() { return hargaPerKg; }

    @Override
    public double total() {
        return beratKg * hargaPerKg;
    }

    @Override
    public String ringkasan() {
        return "[" + idTransaksi + "] SETOR | " + namaSampah + " | " +
                String.format("%.2f", beratKg) + " kg x Rp " +
                String.format("%,.0f", hargaPerKg).replace(',', '.') +
                " = Rp " + String.format("%,.0f", total()).replace(',', '.') +
                " | " + waktu;
    }
}