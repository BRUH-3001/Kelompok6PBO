/*
Nama Program   : Program Selisih Waktu (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 28/9/2026 
Deskripsi      : Program OOP untuk mencari selisih waktu antara dua objek Waktu,
                 dengan dua cara proses (Cara 1: fungsi return, Cara 2: void),
                 dan input/output baik di dalam maupun di luar class.
                 3 objek dibuat dengan 3 cara berbeda (konstanta, input di
                 luar class, input di dalam class).
*/

import java.util.Scanner;

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

    // input dalam class
    public void inputWaktu(Scanner scanner) {
        System.out.print("Masukkan jam   : ");
        this.jam = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Masukkan menit : ");
        this.menit = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Masukkan detik : ");
        this.detik = Integer.parseInt(scanner.nextLine().trim());
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

public class Soal2_SelisihWaktu {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;

        System.out.println("=======================================");
        System.out.println("   PROGRAM SELISIH WAKTU");
        System.out.println("=======================================");

        do {
            tampilkanMenu();
            pilihan = inputPilihan();

            switch (pilihan) {
                case 1: jalankanObjek1(); break;
                case 2: jalankanObjek2(); break;
                case 3: jalankanObjek3(); break;
                case 0:
                    System.out.println("Terima kasih, program selesai.");
                    break;
                default:
                    System.out.println(">> Pilihan tidak valid, coba lagi.");
            }
        } while (pilihan != 0);
    }

    static void tampilkanMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Objek 1 - via constructor dg konstanta");
        System.out.println("2. Objek 2 - via constructor, input di luar class");
        System.out.println("3. Objek 3 - input di dalam class (pakai inputWaktu())");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    static int inputPilihan() {
        int p = -1;
        try {
            p = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            p = -1;
        }
        return p;
    }

    static int inputAngka(String label) {
        System.out.print(label);
        return Integer.parseInt(sc.nextLine().trim());
    }

    static void tampilkanKeduaHasil(Waktu w1, Waktu w2) {
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

    static void jalankanObjek1() {
        System.out.println("\n>> Objek 1: nilai konstan lewat constructor");
        Waktu w1 = new Waktu(8, 0, 0);
        Waktu w2 = new Waktu(17, 15, 10);
        tampilkanKeduaHasil(w1, w2);
    }

    static void jalankanObjek2() {
        System.out.println("\n>> Objek 2: input diambil DI LUAR class, lalu dioper ke constructor");
        System.out.println("Waktu 1:");
        Waktu w1 = new Waktu(inputAngka("  Jam   : "), inputAngka("  Menit : "), inputAngka("  Detik : "));
        System.out.println("Waktu 2:");
        Waktu w2 = new Waktu(inputAngka("  Jam   : "), inputAngka("  Menit : "), inputAngka("  Detik : "));
        tampilkanKeduaHasil(w1, w2);
    }

    static void jalankanObjek3() {
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
