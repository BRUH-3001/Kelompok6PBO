/*
Nama Program   : Program Selisih Waktu (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 6/10/2026 
Deskripsi      : Program OOP untuk mencari selisih waktu antara dua objek Waktu
                 dengan Class Validator dan Menu tersendiri.
*/

import java.util.Scanner;

// ==========================================
// CLASS VALIDATOR (Handling Error Input)
// ==========================================
class Validator {
    public static int bacaAngkaValidasi(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String teks = input.nextLine().trim();
                int nilai = Integer.parseInt(teks);

                if (nilai >= min && nilai <= max) {
                    return nilai;
                } else {
                    System.out.println("  [!] Nilai tidak valid! Harus antara " + min + " sampai " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!");
            }
        }
    }
}

// ==========================================
// CLASS WAKTU
// ==========================================
class Waktu {
    private int jam;
    private int menit;
    private int detik;

    public Waktu() {
        this.jam = 0;
        this.menit = 0;
        this.detik = 0;
    }

    public Waktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    // setter 
    public void setWaktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    // input dalam class menggunakan Validator
    public void inputWaktu(Scanner scanner) {
        this.jam = Validator.bacaAngkaValidasi(scanner, "  Masukkan jam (0-23)   : ", 0, 23);
        this.menit = Validator.bacaAngkaValidasi(scanner, "  Masukkan menit (0-59) : ", 0, 59);
        this.detik = Validator.bacaAngkaValidasi(scanner, "  Masukkan detik (0-59) : ", 0, 59);
    }

    public int getJam() { return jam; }
    public int getMenit() { return menit; }
    public int getDetik() { return detik; }

    private int keTotalDetik() {
        return jam * 3600 + menit * 60 + detik;
    }

    // output dalam class
    public void tampilkanWaktu() {
        System.out.printf("%02d:%02d:%02d%n", jam, menit, detik);
    }

    // Cara 2 void
    public void selisihWaktu(Waktu w1, Waktu w2) {
        int totalDetik1 = w1.keTotalDetik();
        int totalDetik2 = w2.keTotalDetik();
        int selisih = Math.abs(totalDetik1 - totalDetik2);

        this.jam = selisih / 3600;
        this.menit = (selisih % 3600) / 60;
        this.detik = selisih % 60;
    }

    // Cara 1 return
    public Waktu selisihWaktu2(Waktu w) {
        int totalDetikThis = this.keTotalDetik();
        int totalDetikW = w.keTotalDetik();
        int selisih = Math.abs(totalDetikThis - totalDetikW);

        int jamHasil = selisih / 3600;
        int menitHasil = (selisih % 3600) / 60;
        int detikHasil = selisih % 60;

        return new Waktu(jamHasil, menitHasil, detikHasil);
    }
}

// ==========================================
// CLASS MENU (Menangani Tampilan & Alur)
// ==========================================
class MenuWaktu {
    private Scanner sc;

    public MenuWaktu() {
        sc = new Scanner(System.in);
    }

    public void jalankan() {
        int pilihan;

        System.out.println("=======================================");
        System.out.println("   PROGRAM SELISIH WAKTU");
        System.out.println("=======================================");

        do {
            tampilkanMenu();
            // Validasi input menu
            pilihan = Validator.bacaAngkaValidasi(sc, "Pilih menu (0-3): ", 0, 3);

            switch (pilihan) {
                case 1: jalankanObjek1(); break;
                case 2: jalankanObjek2(); break;
                case 3: jalankanObjek3(); break;
                case 0:
                    System.out.println("Terima kasih, program selesai.");
                    break;
            }
        } while (pilihan != 0);
        
        sc.close();
    }

    private void tampilkanMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Objek 1 - via constructor dg konstanta");
        System.out.println("2. Objek 2 - via constructor, input di luar class");
        System.out.println("3. Objek 3 - input di dalam class (pakai inputWaktu())");
        System.out.println("0. Keluar");
    }

    // output luar
    private void tampilkanKeduaHasil(Waktu w1, Waktu w2) {
        System.out.print("Waktu 1 : "); w1.tampilkanWaktu();
        System.out.print("Waktu 2 : "); w2.tampilkanWaktu();

        // Cara 1 (return)
        Waktu hasilReturn = w1.selisihWaktu2(w2);
        System.out.print("Selisih (cara return) : "); hasilReturn.tampilkanWaktu();

        // Cara 2 (void)
        Waktu hasilVoid = new Waktu();
        hasilVoid.selisihWaktu(w1, w2);
        System.out.print("Selisih (cara void)   : "); hasilVoid.tampilkanWaktu();
    }

    private void jalankanObjek1() {
        System.out.println("\n>> Objek 1: nilai konstan lewat constructor");
        Waktu w1 = new Waktu(8, 0, 0);
        Waktu w2 = new Waktu(17, 15, 10);
        tampilkanKeduaHasil(w1, w2);
    }

    private void jalankanObjek2() {
        System.out.println("\n>> Objek 2: input diambil DI LUAR class, lalu dioper ke constructor");
        System.out.println("Waktu 1:");
        int j1 = Validator.bacaAngkaValidasi(sc, "  Jam (0-23)   : ", 0, 23);
        int m1 = Validator.bacaAngkaValidasi(sc, "  Menit (0-59) : ", 0, 59);
        int d1 = Validator.bacaAngkaValidasi(sc, "  Detik (0-59) : ", 0, 59);
        Waktu w1 = new Waktu(j1, m1, d1);

        System.out.println("Waktu 2:");
        int j2 = Validator.bacaAngkaValidasi(sc, "  Jam (0-23)   : ", 0, 23);
        int m2 = Validator.bacaAngkaValidasi(sc, "  Menit (0-59) : ", 0, 59);
        int d2 = Validator.bacaAngkaValidasi(sc, "  Detik (0-59) : ", 0, 59);
        Waktu w2 = new Waktu(j2, m2, d2);

        tampilkanKeduaHasil(w1, w2);
    }

    private void jalankanObjek3() {
        System.out.println("\n>> Objek 3: objek dibuat kosong, input diminta DI DALAM class");
        Waktu w1 = new Waktu();
        System.out.println("Waktu 1:");
        w1.inputWaktu(sc);

        Waktu w2 = new Waktu();
        System.out.println("Waktu 2:");
        w2.inputWaktu(sc);

        tampilkanKeduaHasil(w1, w2);
    }
}

// ==========================================
// MAIN EXECUTION
// ==========================================
public class Soal2_SelisihWaktu {
    public static void main(String[] args) {
        MenuWaktu menu = new MenuWaktu();
        menu.jalankan();
    }
}