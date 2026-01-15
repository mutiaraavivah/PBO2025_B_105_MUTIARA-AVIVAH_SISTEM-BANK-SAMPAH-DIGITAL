import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {

    private static final List<Nasabah> nasabahList = new ArrayList<>();
    private static final List<Sampah> daftarSampah = new ArrayList<>();
    private static final List<Transaksi> semuaTransaksi = new ArrayList<>();

    public static final String OPERATOR_USERNAME = "operator";
    public static final String OPERATOR_PASSWORD = "op123";

    private static final AtomicInteger counterSampah = new AtomicInteger(3);
    private static final AtomicInteger counterSetor = new AtomicInteger(0);
    private static final AtomicInteger counterTarik = new AtomicInteger(0);

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        daftarSampah.add(new Sampah("S01", "Plastik", 3500));
        daftarSampah.add(new Sampah("S02", "Kertas", 2000));
        daftarSampah.add(new Sampah("S03", "Logam", 7000));
    }

    public static List<Sampah> getDaftarSampah() {
        return new ArrayList<>(daftarSampah);
    }

    public static void tambahSampahBaru(String nama, double harga) {
        int next = counterSampah.incrementAndGet();
        String id = String.format("S%02d", next);
        daftarSampah.add(new Sampah(id, nama, harga));
    }

    public static void updateHargaSampah(String id, double harga) {
        for (Sampah s : daftarSampah) {
            if (s.getId().equalsIgnoreCase(id)) {
                s.setHargaPerKg(harga);
                return;
            }
        }
    }

    public static void tambahNasabah(Nasabah n) {
        if (n != null) nasabahList.add(n);
    }

    public static boolean usernameNasabahSudahAda(String username) {
        return findNasabahByUsername(username) != null;
    }

    public static Nasabah findNasabahByUsername(String username) {
        for (Nasabah n : nasabahList) {
            if (n.getUsername().equalsIgnoreCase(username)) return n;
        }
        return null;
    }

    public static void addTransaksiGlobal(Transaksi t) {
        if (t != null) semuaTransaksi.add(t);
    }

    public static List<Transaksi> getSemuaTransaksi() {
        return new ArrayList<>(semuaTransaksi);
    }

    public static String now() {
        return LocalDateTime.now().format(FMT);
    }

    public static String nextSetorId() {
        int x = counterSetor.incrementAndGet();
        return String.format("ST-%04d", x);
    }

    public static String nextTarikId() {
        int x = counterTarik.incrementAndGet();
        return String.format("TR-%04d", x);
    }
}