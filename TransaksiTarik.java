public class TransaksiTarik extends Transaksi {
    private final double nominal;

    public TransaksiTarik(String id, String waktu, String usernameNasabah, double nominal, StatusTransaksi status) {
        super(id, waktu, usernameNasabah, status);
        this.nominal = nominal;
    }

    public double getNominal() { return nominal; }

    @Override
    public double total() {
        return nominal;
    }

    @Override
    public String ringkasan() {
        String st = (status == StatusTransaksi.SUCCESS) ? "SUCCESS" : "FAILED";
        return "[" + idTransaksi + "] TARIK | Rp " +
                String.format("%,.0f", nominal).replace(',', '.') +
                " | " + st + " | " + waktu;
    }
}