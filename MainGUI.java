import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class MainGUI extends Application {

    private Stage stage;

    // Session
    private Nasabah sessionNasabah = null;
    private boolean operatorLoggedIn = false;

    // Reusable UI
    private Label topTitle;

    // ======= THEME (CSS) for TOP BAR (and optional global) =======
    private static final String THEME_CSS = """
        .root {
            -fx-font-family: "Segoe UI";
            -fx-background-color: #e6f0ff; /* pastel biru */
        }

        /* Top bar */
        .topbar {
            -fx-background-color: rgba(255,255,255,0.75);
            -fx-background-radius: 16;
            -fx-padding: 12 14;
            -fx-effect: dropshadow(gaussian, rgba(15,23,42,0.10), 18, 0, 0, 6);
        }
        .topbar-title {
            -fx-font-size: 18px;
            -fx-font-weight: 800;
            -fx-text-fill: #0f172a;
        }
        .btn-top {
            -fx-background-radius: 12;
            -fx-padding: 10 14;
            -fx-font-weight: 800;
            -fx-text-fill: #0f172a;
            -fx-background-color: rgba(255,255,255,0.85);
            -fx-border-color: rgba(15,23,42,0.10);
            -fx-border-radius: 12;
        }
        .btn-top:hover {
            -fx-background-color: rgba(255,255,255,1.0);
        }
        .btn-top-primary {
            -fx-background-color: rgba(59,130,246,0.16);
            -fx-border-color: rgba(59,130,246,0.35);
        }
        .btn-top-primary:hover {
            -fx-background-color: rgba(59,130,246,0.22);
        }
        .btn-top-danger {
            -fx-background-color: rgba(239,68,68,0.12);
            -fx-border-color: rgba(239,68,68,0.35);
        }
        .btn-top-danger:hover {
            -fx-background-color: rgba(239,68,68,0.18);
        }
        """;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.getStyleClass().add("root"); // apply css root

        topTitle = new Label("Sistem Bank Sampah Digital");
        topTitle.getStyleClass().add("topbar-title"); // use new style

        root.setTop(topBar());           // NEW top bar
        root.setCenter(buildHomePane()); // YOUR pane

        Scene scene = new Scene(root, 980, 620);
        scene.getStylesheets().add("data:text/css," + encodeCss(THEME_CSS));
        stage.setTitle("Sistem Bank Sampah Digital");
        stage.setScene(scene);

        // FULL SCREEN feel
        stage.setMaximized(true);

        stage.show();
    }

    private String encodeCss(String css) {
        return css
                .replace("\n", "%0A")
                .replace(" ", "%20")
                .replace("#", "%23")
                .replace(";", "%3B")
                .replace("{", "%7B")
                .replace("}", "%7D")
                .replace(":", "%3A")
                .replace(",", "%2C")
                .replace("\"", "%22");
    }

    // =========================
    // TOP BAR (NEW)
    // =========================
    private HBox topBar() {
        Button btnHome = new Button("Menu Awal");
        btnHome.getStyleClass().addAll("btn-top", "btn-top-primary");
        btnHome.setOnAction(e -> setCenter(buildHomePane()));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnExit = new Button("Keluar");
        btnExit.getStyleClass().addAll("btn-top", "btn-top-danger");
        btnExit.setOnAction(e -> stage.close());

        HBox bar = new HBox(12, topTitle, spacer, btnHome, btnExit);
        bar.getStyleClass().add("topbar");
        bar.setAlignment(Pos.CENTER_LEFT);
        return bar;
    }

    private void setCenter(javafx.scene.Node node) {
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(node);
    }

    // =========================
    // HOME (YOUR CODE)
    // =========================
    private Pane buildHomePane() {
        topTitle.setText("Sistem Bank Sampah Digital");

        StackPane bg = new StackPane();
        bg.setPadding(new Insets(18));
        bg.setStyle(
                "-fx-background-radius: 18;" +
                        "-fx-background-color: #e6f0ff;"
        );

        VBox card = new VBox(16);
        card.setPadding(new Insets(26));
        card.setMaxWidth(520);
        card.setMinHeight(430);
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 22;" +
                        "-fx-border-radius: 22;" +
                        "-fx-border-color: rgba(255,255,255,0.35);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 26, 0, 0, 10);"
        );

        Label badge = new Label("KIOSK • BANK SAMPAH DIGITAL");
        badge.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 10;" +
                        "-fx-background-radius: 999;" +
                        "-fx-background-color: rgba(15, 23, 42, 0.08);"
        );

        Label title = new Label("Selamat Datang");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: 800; -fx-text-fill: #0f172a;");

        Label subtitle = new Label(
                "Setorkan sampahmu, dapatkan saldo otomatis, lalu tarik kapan saja.\n" +
                        "Mari jaga lingkungan dengan langkah kecil yang konsisten."
        );
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155;");

        HBox chips = new HBox(10);
        chips.setAlignment(Pos.CENTER_LEFT);

        Label chip1 = new Label("• Setor cepat");
        chip1.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 8 10;" +
                        "-fx-background-radius: 12;" +
                        "-fx-background-color: rgba(59,130,246,0.14);"
        );

        Label chip2 = new Label("• Saldo masuk otomatis");
        chip2.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 8 10;" +
                        "-fx-background-radius: 12;" +
                        "-fx-background-color: rgba(34,197,94,0.14);"
        );

        Label chip3 = new Label("• Tarik aman");
        chip3.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 8 10;" +
                        "-fx-background-radius: 12;" +
                        "-fx-background-color: rgba(124,58,237,0.14);"
        );

        chips.getChildren().addAll(chip1, chip2, chip3);

        Separator sep = new Separator();
        sep.setOpacity(0.35);

        Button btnNasabah = new Button("Masuk sebagai Nasabah");
        btnNasabah.setPrefWidth(460);
        btnNasabah.setPrefHeight(48);
        btnNasabah.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
        );
        btnNasabah.setOnAction(e -> setCenter(buildNasabahAuthPane()));

        Button btnOperator = new Button("Masuk sebagai Operator");
        btnOperator.setPrefWidth(460);
        btnOperator.setPrefHeight(48);
        btnOperator.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
        );
        btnOperator.setOnAction(e -> setCenter(buildOperatorLoginPane()));

        btnNasabah.setOnMouseEntered(e ->
                btnNasabah.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #2563eb, #0891b2);" +
                                "-fx-effect: dropshadow(gaussian, rgba(59,130,246,0.35), 16, 0, 0, 6);"
                )
        );
        btnNasabah.setOnMouseExited(e ->
                btnNasabah.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
                )
        );

        btnOperator.setOnMouseEntered(e ->
                btnOperator.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #6d28d9, #e11d48);" +
                                "-fx-effect: dropshadow(gaussian, rgba(124,58,237,0.35), 16, 0, 0, 6);"
                )
        );
        btnOperator.setOnMouseExited(e ->
                btnOperator.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
                )
        );

        Label foot = new Label("Tips: Harga/kg dapat berubah sesuai kebijakan operator.");
        foot.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");

        card.getChildren().addAll(badge, title, subtitle, chips, sep, btnNasabah, btnOperator, foot);

        bg.getChildren().add(card);
        StackPane.setAlignment(card, Pos.CENTER);

        BorderPane wrapper = new BorderPane(bg);
        wrapper.setPadding(new Insets(6));
        return wrapper;
    }

    // =========================
    // NASABAH AUTH (YOUR CODE)
    // =========================
    private Pane buildNasabahAuthPane() {
        topTitle.setText("Nasabah - Registrasi / Login");

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-tab-min-width: 160;" +
                        "-fx-tab-min-height: 42;"
        );

        Tab tabLogin = new Tab("Login");
        tabLogin.setContent(buildNasabahLoginPane());

        Tab tabReg = new Tab("Registrasi");
        tabReg.setContent(buildNasabahRegisterPane());

        tabs.getTabs().addAll(tabLogin, tabReg);

        StackPane bg = new StackPane();
        bg.setPadding(new Insets(18));
        bg.setStyle("-fx-background-radius: 18; -fx-background-color: #e6f0ff;");

        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setMaxWidth(720);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 22;" +
                        "-fx-border-radius: 22;" +
                        "-fx-border-color: rgba(255,255,255,0.35);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.14), 24, 0, 0, 10);"
        );

        Label badge = new Label("NASABAH • AKUN");
        badge.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 10;" +
                        "-fx-background-radius: 999;" +
                        "-fx-background-color: rgba(59,130,246,0.14);"
        );

        Label title = new Label("Masuk atau Buat Akun");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: #0f172a;");

        Label subtitle = new Label("Login untuk melanjutkan, atau registrasi jika belum punya akun.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

        VBox header = new VBox(6, badge, title, subtitle);
        header.setPadding(new Insets(0, 0, 6, 0));

        VBox tabsWrap = new VBox(tabs);
        tabsWrap.setPadding(new Insets(10));
        tabsWrap.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);"
        );

        card.getChildren().addAll(header, tabsWrap);

        bg.getChildren().add(card);
        StackPane.setAlignment(card, Pos.CENTER);

        BorderPane wrap = new BorderPane(bg);
        wrap.setPadding(new Insets(6));
        return wrap;
    }

    private Pane buildNasabahLoginPane() {
        GridPane gp = formGrid();

        TextField tfUser = new TextField();
        PasswordField tfPass = new PasswordField();
        Label info = new Label();
        info.setStyle("-fx-text-fill: #b00020;");

        tfUser.setPrefWidth(360);
        tfPass.setPrefWidth(360);

        tfUser.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.18);" +
                        "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-padding: 10 12;"
        );
        tfPass.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.18);" +
                        "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-padding: 10 12;"
        );

        Label lUser = new Label("Username");
        lUser.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        Label lPass = new Label("Password");
        lPass.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");

        gp.add(lUser, 0, 0);
        gp.add(tfUser, 1, 0);
        gp.add(lPass, 0, 1);
        gp.add(tfPass, 1, 1);

        Button btn = new Button("Login");
        btn.setDefaultButton(true);
        btn.setPrefWidth(360);
        btn.setPrefHeight(44);
        btn.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #2563eb, #0891b2);" +
                                "-fx-effect: dropshadow(gaussian, rgba(59,130,246,0.30), 16, 0, 0, 6);"
                )
        );
        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
                )
        );

        btn.setOnAction(e -> {
            String u = tfUser.getText().trim();
            String p = tfPass.getText();

            if (u.isEmpty() || p.isEmpty()) {
                info.setText("Username dan password wajib diisi.");
                return;
            }

            Nasabah n = DataStore.findNasabahByUsername(u);
            if (n == null || !n.checkPassword(p)) {
                info.setText("Login gagal. Username/password salah.");
                return;
            }

            sessionNasabah = n;
            setCenter(buildNasabahDashboardPane());
        });

        VBox box = new VBox(14, gp, btn, info);
        box.setPadding(new Insets(18));
        box.setMaxWidth(560);
        box.setStyle(
                "-fx-background-color: rgba(59,130,246,0.06);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(59,130,246,0.18);"
        );

        StackPane center = new StackPane(box);
        center.setPadding(new Insets(18));
        StackPane.setAlignment(box, Pos.CENTER);
        return center;
    }

    private Pane buildNasabahRegisterPane() {
        GridPane gp = formGrid();

        TextField tfNama = new TextField();
        TextField tfUser = new TextField();
        PasswordField tfPass = new PasswordField();
        PasswordField tfPass2 = new PasswordField();
        Label info = new Label();
        info.setStyle("-fx-text-fill: #b00020;");

        tfNama.setPrefWidth(360);
        tfUser.setPrefWidth(360);
        tfPass.setPrefWidth(360);
        tfPass2.setPrefWidth(360);

        String fieldStyle =
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.18);" +
                        "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-padding: 10 12;";

        tfNama.setStyle(fieldStyle);
        tfUser.setStyle(fieldStyle);
        tfPass.setStyle(fieldStyle);
        tfPass2.setStyle(fieldStyle);

        Label lNama = new Label("Nama Lengkap");
        lNama.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        Label lUser = new Label("Username");
        lUser.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        Label lPass = new Label("Password");
        lPass.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        Label lPass2 = new Label("Ulangi Password");
        lPass2.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");

        gp.add(lNama, 0, 0);
        gp.add(tfNama, 1, 0);
        gp.add(lUser, 0, 1);
        gp.add(tfUser, 1, 1);
        gp.add(lPass, 0, 2);
        gp.add(tfPass, 1, 2);
        gp.add(lPass2, 0, 3);
        gp.add(tfPass2, 1, 3);

        Button btn = new Button("Registrasi");
        btn.setPrefWidth(360);
        btn.setPrefHeight(44);
        btn.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #6d28d9, #e11d48);" +
                                "-fx-effect: dropshadow(gaussian, rgba(124,58,237,0.28), 16, 0, 0, 6);"
                )
        );
        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-radius: 14;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: 800;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
                )
        );

        btn.setOnAction(e -> {
            String nama = tfNama.getText().trim();
            String u = tfUser.getText().trim();
            String p1 = tfPass.getText();
            String p2 = tfPass2.getText();

            if (nama.isEmpty() || u.isEmpty() || p1.isEmpty()) {
                info.setText("Nama, username, dan password wajib diisi.");
                return;
            }
            if (!p1.equals(p2)) {
                info.setText("Password tidak sama.");
                return;
            }
            if (DataStore.usernameNasabahSudahAda(u)) {
                info.setText("Username sudah dipakai. Gunakan yang lain.");
                return;
            }

            Nasabah n = new Nasabah(nama, u, p1);
            DataStore.tambahNasabah(n);

            info.setStyle("-fx-text-fill: #1b5e20;");
            info.setText("Registrasi berhasil. Silakan login.");
            tfNama.clear(); tfUser.clear(); tfPass.clear(); tfPass2.clear();
        });

        VBox box = new VBox(14, gp, btn, info);
        box.setPadding(new Insets(18));
        box.setMaxWidth(560);
        box.setStyle(
                "-fx-background-color: rgba(124,58,237,0.06);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(124,58,237,0.18);"
        );

        StackPane center = new StackPane(box);
        center.setPadding(new Insets(18));
        StackPane.setAlignment(box, Pos.CENTER);
        return center;
    }

    // =========================
    // NASABAH DASHBOARD (YOUR CODE)
    // =========================
    private Pane buildNasabahDashboardPane() {
        if (sessionNasabah == null) return buildHomePane();

        topTitle.setText("Nasabah");

        StackPane bg = new StackPane();
        bg.setPadding(new Insets(18));
        bg.setStyle("-fx-background-radius: 18; -fx-background-color: #e6f0ff;");

        Label nama = new Label("Nama: " + sessionNasabah.getNama());
        nama.setStyle("-fx-text-fill: #0f172a;");
        Label user = new Label("Username: " + sessionNasabah.getUsername());
        user.setStyle("-fx-text-fill: #0f172a;");
        Label saldo = new Label("Saldo: " + fmtRp(sessionNasabah.getSaldo()));
        saldo.setStyle("-fx-font-size: 16px; -fx-font-weight: 900; -fx-text-fill: #0f172a;");

        VBox infoBox = new VBox(8, nama, user, saldo);
        infoBox.setPadding(new Insets(14));
        infoBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );

        Label setorTitle = new Label("Setor Sampah");
        setorTitle.setStyle("-fx-font-weight: 900; -fx-text-fill: #0f172a; -fx-font-size: 13px;");

        ObservableList<Sampah> sampahObs = FXCollections.observableArrayList(DataStore.getDaftarSampah());
        ComboBox<Sampah> cbSampah = new ComboBox<>(sampahObs);
        cbSampah.setPromptText("Pilih jenis sampah");
        cbSampah.setPrefWidth(360);
        cbSampah.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.16);" +
                        "-fx-background-color: white;"
        );

        TextField tfBerat = new TextField();
        tfBerat.setPromptText("Berat (kg), contoh: 2.5");
        tfBerat.setPrefWidth(360);
        tfBerat.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.16);" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 10 12;"
        );

        Button btnSetor = new Button("Setor");
        btnSetor.setPrefWidth(360);
        btnSetor.setPrefHeight(40);
        btnSetor.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
        );

        Label setorInfo = new Label();
        setorInfo.setStyle("-fx-text-fill: #334155;");

        btnSetor.setOnAction(e -> {
            Sampah s = cbSampah.getValue();
            if (s == null) {
                setorInfo.setStyle("-fx-text-fill: #b00020;");
                setorInfo.setText("Pilih jenis sampah.");
                return;
            }

            double berat;
            try {
                berat = Double.parseDouble(tfBerat.getText().trim());
            } catch (Exception ex) {
                setorInfo.setStyle("-fx-text-fill: #b00020;");
                setorInfo.setText("Berat harus angka (contoh: 2.5).");
                return;
            }

            if (berat <= 0) {
                setorInfo.setStyle("-fx-text-fill: #b00020;");
                setorInfo.setText("Berat harus > 0.");
                return;
            }

            double sebelum = sessionNasabah.getSaldo();
            TransaksiSetor ts = KioskService.prosesSetor(sessionNasabah, s, berat);
            saldo.setText("Saldo: " + fmtRp(sessionNasabah.getSaldo()));

            setorInfo.setStyle("-fx-text-fill: #1b5e20;");
            setorInfo.setText("Setor berhasil. +" + fmtRp(sessionNasabah.getSaldo() - sebelum) +
                    " (" + ts.getIdTransaksi() + ")");

            tfBerat.clear();
            cbSampah.setValue(null);
        });

        VBox setorBox = new VBox(10,
                setorTitle,
                new Label("Jenis Sampah"), cbSampah,
                new Label("Berat (kg)"), tfBerat,
                btnSetor,
                setorInfo
        );
        setorBox.setPadding(new Insets(14));
        setorBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(59,130,246,0.18);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );

        Label tarikTitle = new Label("Tarik Saldo (Cash-out)");
        tarikTitle.setStyle("-fx-font-weight: 900; -fx-text-fill: #0f172a; -fx-font-size: 13px;");

        TextField tfTarik = new TextField();
        tfTarik.setPromptText("Nominal, contoh: 10000");
        tfTarik.setPrefWidth(360);
        tfTarik.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.16);" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 10 12;"
        );

        Button btnTarik = new Button("Tarik");
        btnTarik.setPrefWidth(360);
        btnTarik.setPrefHeight(40);
        btnTarik.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
        );

        Label tarikInfo = new Label();
        tarikInfo.setStyle("-fx-text-fill: #334155;");

        btnTarik.setOnAction(e -> {
            double nominal;
            try {
                nominal = Double.parseDouble(tfTarik.getText().trim());
            } catch (Exception ex) {
                tarikInfo.setStyle("-fx-text-fill: #b00020;");
                tarikInfo.setText("Nominal harus angka.");
                return;
            }

            if (nominal <= 0) {
                tarikInfo.setStyle("-fx-text-fill: #b00020;");
                tarikInfo.setText("Nominal harus > 0.");
                return;
            }

            String err = KioskService.prosesTarik(sessionNasabah, nominal);
            if (err != null) {
                tarikInfo.setStyle("-fx-text-fill: #b00020;");
                tarikInfo.setText(err);
                return;
            }

            saldo.setText("Saldo: " + fmtRp(sessionNasabah.getSaldo()));
            tarikInfo.setStyle("-fx-text-fill: #1b5e20;");
            tarikInfo.setText("Tarik berhasil. Silakan ambil uang di dispenser.");

            tfTarik.clear();
        });

        VBox tarikBox = new VBox(10, tarikTitle, tfTarik, btnTarik, tarikInfo);
        tarikBox.setPadding(new Insets(14));
        tarikBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(124,58,237,0.18);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );

        Label riwTitle = new Label("Riwayat Transaksi");
        riwTitle.setStyle("-fx-font-weight: 900; -fx-text-fill: #0f172a; -fx-font-size: 13px;");

        ObservableList<String> riwayatObs = FXCollections.observableArrayList(sessionNasabah.getRiwayatRingkasan());
        ListView<String> listRiw = new ListView<>(riwayatObs);
        listRiw.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);"
        );

        Button btnRefresh = new Button("Refresh");
        btnRefresh.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #0f172a;" +
                        "-fx-background-color: rgba(15,23,42,0.10);"
        );
        btnRefresh.setOnAction(e -> riwayatObs.setAll(sessionNasabah.getRiwayatRingkasan()));

        VBox riwBox = new VBox(10, riwTitle, listRiw, btnRefresh);
        riwBox.setPadding(new Insets(14));
        riwBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );

        Button btnBack = new Button("Kembali ke Menu Awal");
        btnBack.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #0f172a;" +
                        "-fx-background-color: rgba(15,23,42,0.10);"
        );
        btnBack.setOnAction(e -> {
            sessionNasabah = null;
            setCenter(buildHomePane());
        });

        HBox actions = new HBox(10, btnBack);
        actions.setPadding(new Insets(10, 0, 0, 0));

        VBox left = new VBox(12, infoBox, setorBox, tarikBox, actions);
        left.setPrefWidth(420);

        BorderPane main = new BorderPane();
        main.setLeft(left);
        main.setCenter(riwBox);
        BorderPane.setMargin(riwBox, new Insets(0, 0, 0, 12));

        VBox shell = new VBox(14, main);
        shell.setPadding(new Insets(16));
        shell.setMaxWidth(930);
        shell.setStyle(
                "-fx-background-color: rgba(255,255,255,0.55);" +
                        "-fx-background-radius: 22;" +
                        "-fx-border-radius: 22;" +
                        "-fx-border-color: rgba(255,255,255,0.35);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 26, 0, 0, 10);"
        );

        bg.getChildren().add(shell);
        StackPane.setAlignment(shell, Pos.CENTER);

        BorderPane wrap = new BorderPane(bg);
        wrap.setPadding(new Insets(6));
        return wrap;
    }

    // =========================
    // OPERATOR LOGIN (YOUR CODE)
    // =========================
    private Pane buildOperatorLoginPane() {
        topTitle.setText("Operator - Login");

        StackPane bg = new StackPane();
        bg.setPadding(new Insets(18));
        bg.setStyle("-fx-background-radius: 18; -fx-background-color: #e6f0ff;");

        GridPane gp = formGrid();
        TextField tfUser = new TextField();
        PasswordField tfPass = new PasswordField();
        Label info = new Label();
        info.setStyle("-fx-text-fill: #b00020;");

        tfUser.setPrefWidth(360);
        tfPass.setPrefWidth(360);

        String fieldStyle =
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.18);" +
                        "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-padding: 10 12;";

        tfUser.setStyle(fieldStyle);
        tfPass.setStyle(fieldStyle);

        Label lUser = new Label("Username");
        lUser.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: 800;");
        Label lPass = new Label("Password");
        lPass.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: 800;");

        gp.add(lUser, 0, 0);
        gp.add(tfUser, 1, 0);
        gp.add(lPass, 0, 1);
        gp.add(tfPass, 1, 1);

        Button btn = new Button("Login Operator");
        btn.setDefaultButton(true);
        btn.setPrefWidth(360);
        btn.setPrefHeight(44);
        btn.setStyle(
                "-fx-background-radius: 14;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
        );

        btn.setOnAction(e -> {
            String u = tfUser.getText().trim();
            String p = tfPass.getText();

            if (u.equals(DataStore.OPERATOR_USERNAME) && p.equals(DataStore.OPERATOR_PASSWORD)) {
                operatorLoggedIn = true;
                setCenter(buildOperatorDashboardPane());
            } else {
                info.setText("Login gagal.");
            }
        });

        Label badge = new Label("OPERATOR • CONSOLE");
        badge.setStyle(
                "-fx-text-fill: #0f172a;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 10;" +
                        "-fx-background-radius: 999;" +
                        "-fx-background-color: rgba(124,58,237,0.14);"
        );

        Label title = new Label("Masuk Operator");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: #0f172a;");

        Label subtitle = new Label("Masukkan akun operator untuk mengelola harga dan ringkasan transaksi.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

        VBox card = new VBox(14, badge, title, subtitle, gp, btn, info);
        card.setPadding(new Insets(22));
        card.setMaxWidth(720);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 22;" +
                        "-fx-border-radius: 22;" +
                        "-fx-border-color: rgba(255,255,255,0.35);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.14), 24, 0, 0, 10);"
        );

        bg.getChildren().add(card);
        StackPane.setAlignment(card, Pos.CENTER);

        BorderPane wrap = new BorderPane(bg);
        wrap.setPadding(new Insets(6));
        return wrap;
    }

    // =========================
    // OPERATOR DASHBOARD (YOUR CODE)
    // =========================
    private Pane buildOperatorDashboardPane() {
        if (!operatorLoggedIn) return buildOperatorLoginPane();

        topTitle.setText("Operator");

        StackPane bg = new StackPane();
        bg.setPadding(new Insets(18));
        bg.setStyle("-fx-background-radius: 18; -fx-background-color: #e6f0ff;");

        TableView<Sampah> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);"
        );

        TableColumn<Sampah, String> cId = new TableColumn<>("ID");
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sampah, String> cNama = new TableColumn<>("Nama");
        cNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Sampah, Double> cHarga = new TableColumn<>("Harga/Kg");
        cHarga.setCellValueFactory(new PropertyValueFactory<>("hargaPerKg"));

        table.getColumns().addAll(cId, cNama, cHarga);

        ObservableList<Sampah> sampahObs = FXCollections.observableArrayList(DataStore.getDaftarSampah());
        table.setItems(sampahObs);

        TextField tfNama = new TextField();
        tfNama.setPromptText("Nama sampah baru");
        tfNama.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.16);" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 10 12;"
        );

        TextField tfHarga = new TextField();
        tfHarga.setPromptText("Harga/kg");
        tfHarga.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.16);" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 10 12;"
        );

        Button btnTambah = new Button("Tambah Jenis");
        btnTambah.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #3b82f6, #06b6d4);"
        );

        Label info = new Label();
        info.setStyle("-fx-text-fill: #334155;");

        btnTambah.setOnAction(e -> {
            String nama = tfNama.getText().trim();
            double harga;
            try {
                harga = Double.parseDouble(tfHarga.getText().trim());
            } catch (Exception ex) {
                info.setStyle("-fx-text-fill: #b00020;");
                info.setText("Harga harus angka.");
                return;
            }
            if (nama.isEmpty() || harga <= 0) {
                info.setStyle("-fx-text-fill: #b00020;");
                info.setText("Nama wajib, harga > 0.");
                return;
            }

            DataStore.tambahSampahBaru(nama, harga);
            sampahObs.setAll(DataStore.getDaftarSampah());

            info.setStyle("-fx-text-fill: #1b5e20;");
            info.setText("Berhasil menambah jenis sampah.");
            tfNama.clear();
            tfHarga.clear();
        });

        Button btnUpdate = new Button("Update Harga Jenis Terpilih");
        btnUpdate.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #7c3aed, #f43f5e);"
        );

        btnUpdate.setOnAction(e -> {
            Sampah selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                info.setStyle("-fx-text-fill: #b00020;");
                info.setText("Pilih baris sampah yang mau diupdate.");
                return;
            }
            double harga;
            try {
                harga = Double.parseDouble(tfHarga.getText().trim());
            } catch (Exception ex) {
                info.setStyle("-fx-text-fill: #b00020;");
                info.setText("Harga harus angka.");
                return;
            }
            if (harga <= 0) {
                info.setStyle("-fx-text-fill: #b00020;");
                info.setText("Harga harus > 0.");
                return;
            }

            DataStore.updateHargaSampah(selected.getId(), harga);
            sampahObs.setAll(DataStore.getDaftarSampah());

            info.setStyle("-fx-text-fill: #1b5e20;");
            info.setText("Harga berhasil diupdate.");
        });

        Label manageTitle = new Label("Kelola Jenis Sampah & Harga (Operator)");
        manageTitle.setStyle("-fx-font-weight: 900; -fx-text-fill: #0f172a; -fx-font-size: 14px;");

        VBox manageBox = new VBox(12,
                manageTitle,
                table,
                new Label("Nama (untuk tambah)"),
                tfNama,
                new Label("Harga/kg (untuk tambah/update)"),
                tfHarga,
                new HBox(10, btnTambah, btnUpdate),
                info
        );
        manageBox.setPadding(new Insets(16));
        manageBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );

        Label ringTitle = new Label("Ringkasan Transaksi (cek berkala)");
        ringTitle.setStyle("-fx-font-weight: 900; -fx-text-fill: #0f172a; -fx-font-size: 13px;");

        Label totalSetor = new Label();
        Label totalTarik = new Label();
        Label totalSaldoNasabah = new Label();

        totalSetor.setStyle("-fx-text-fill: #0f172a;");
        totalTarik.setStyle("-fx-text-fill: #0f172a;");
        totalSaldoNasabah.setStyle("-fx-text-fill: #0f172a;");

        Button btnRefresh = new Button("Refresh Ringkasan");
        btnRefresh.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: #0f172a;" +
                        "-fx-background-color: rgba(15,23,42,0.10);"
        );

        btnRefresh.setOnAction(e -> {
            totalSetor.setText("Total Kredit (Setor): " + fmtRp(OperatorService.totalSetor()));
            totalTarik.setText("Total Debit (Tarik): " + fmtRp(OperatorService.totalTarik()));
            totalSaldoNasabah.setText("Total Saldo Nasabah: " + fmtRp(OperatorService.totalSaldoNasabah()));
        });

        btnRefresh.fire();

        VBox summaryBox = new VBox(12, ringTitle, totalSetor, totalTarik, totalSaldoNasabah, btnRefresh);
        summaryBox.setPadding(new Insets(16));
        summaryBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.92);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(15, 23, 42, 0.10);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 18, 0, 0, 6);"
        );
        summaryBox.setPrefWidth(360);

        Button btnBack = new Button("Kembali ke Menu Awal");
        btnBack.setStyle(
                "-fx-background-radius: 12;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: #0f172a;" +
                        "-fx-background-color: rgba(15,23,42,0.10);"
        );
        btnBack.setOnAction(e -> {
            operatorLoggedIn = false;
            setCenter(buildHomePane());
        });

        BorderPane root = new BorderPane();
        root.setCenter(manageBox);
        root.setRight(summaryBox);
        BorderPane.setMargin(summaryBox, new Insets(0, 0, 0, 12));
        root.setBottom(new HBox(10, btnBack));
        BorderPane.setMargin(root.getBottom(), new Insets(10, 0, 0, 0));

        VBox shell = new VBox(14, root);
        shell.setPadding(new Insets(16));
        shell.setMaxWidth(930);
        shell.setStyle(
                "-fx-background-color: rgba(255,255,255,0.55);" +
                        "-fx-background-radius: 22;" +
                        "-fx-border-radius: 22;" +
                        "-fx-border-color: rgba(255,255,255,0.35);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 26, 0, 0, 10);"
        );

        bg.getChildren().add(shell);
        StackPane.setAlignment(shell, Pos.CENTER);

        BorderPane wrap = new BorderPane(bg);
        wrap.setPadding(new Insets(6));
        return wrap;
    }

    // =========================
    // UI helpers (YOUR CODE, unchanged)
    // =========================
    private GridPane formGrid() {
        GridPane gp = new GridPane();
        gp.setHgap(12);
        gp.setVgap(10);
        gp.setPadding(new Insets(10));

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(140);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        gp.getColumnConstraints().addAll(c1, c2);
        return gp;
    }

    private String cardStyle() {
        return "-fx-background-color: white;" +
                "-fx-border-color: #dddddd;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10;";
    }

    private String fmtRp(double v) {
        return "Rp " + String.format("%,.0f", v).replace(',', '.');
    }
}