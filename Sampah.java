public class Sampah {
    private final String id;
    private final String nama;
    private double hargaPerKg;

    public Sampah(String id, String nama, double hargaPerKg) {
        this.id = id;
        this.nama = nama;
        this.hargaPerKg = hargaPerKg;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public double getHargaPerKg() { return hargaPerKg; }

    public void setHargaPerKg(double hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

    @Override
    public String toString() {
        return nama + " (Rp " + String.format("%,.0f", hargaPerKg).replace(',', '.') + "/kg)";
    }
}