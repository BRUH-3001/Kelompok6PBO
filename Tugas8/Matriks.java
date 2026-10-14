/*
Nama Program   : Program Penjumlahan dan Perkalian Matriks (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 7 Oktober 2026
Deskripsi      : Program OOP array 2 dimensi untuk menjumlahkan dan
                 mengalikan 2 buah matriks dengan 2 class: MyMatriks dan Menu.
                 Proses dibuat dalam 2 bentuk: void (passing object) dan
                 fungsi (return object).
*/

import java.util.Scanner;

//class 1
class MyMatriks {
    //atribut
    private int baris;
    private int kolom;
    private int[][] nilai;

    //constructor
    public MyMatriks() {
        this.baris = 2;
        this.kolom = 2;
        this.nilai = new int[baris][kolom];
    }

    public MyMatriks(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
        this.nilai = new int[baris][kolom];
    }

    //setter & getter
    public void setOrdo(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
        this.nilai = new int[baris][kolom];
    }

    public int getBaris() {
        return baris;
    }

    public int getKolom() {
        return kolom;
    }

    //input
    public void isiMatriks(Scanner input, String nama) {
        System.out.println("Isi Matriks " + nama + " (" + baris + "x" + kolom + ")");
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                this.nilai[i][j] = bacaInt(input,
                        "  Masukkan nilai " + nama + "[" + (i + 1) + "," + (j + 1) + "] : ",
                        Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        }
    }

    // helper: baca bilangan bulat dengan validasi
    public static int bacaInt(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int nilai = Integer.parseInt(input.next());
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.println("  [!] Nilai harus antara " + min + " sampai " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input harus berupa bilangan bulat!");
            }
        }
    }

    //syarat operasi
    public boolean bisaDijumlah(MyMatriks B) {
        return this.baris == B.baris && this.kolom == B.kolom;
    }

    public boolean bisaDikali(MyMatriks B) {
        return this.kolom == B.baris;
    }

    //proses bentuk void (passing object): hasil disimpan di objek pemanggil (this)
    public void addMatriks(MyMatriks A, MyMatriks B) {
        setOrdo(A.baris, A.kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                this.nilai[i][j] = A.nilai[i][j] + B.nilai[i][j];
            }
        }
    }

    public void mulMatriks(MyMatriks A, MyMatriks B) {
        setOrdo(A.baris, B.kolom);
        for (int i = 0; i < A.baris; i++) {
            for (int j = 0; j < B.kolom; j++) {
                int jumlah = 0;
                for (int k = 0; k < A.kolom; k++) {
                    jumlah += A.nilai[i][k] * B.nilai[k][j];
                }
                this.nilai[i][j] = jumlah;
            }
        }
    }

    //proses bentuk fungsi (return object): this = matriks kiri, parameter = matriks kanan
    public MyMatriks plusMatriks(MyMatriks B) {
        MyMatriks C = new MyMatriks(this.baris, this.kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                C.nilai[i][j] = this.nilai[i][j] + B.nilai[i][j];
            }
        }
        return C;
    }

    public MyMatriks kaliMatriks(MyMatriks B) {
        MyMatriks C = new MyMatriks(this.baris, B.kolom);
        for (int i = 0; i < this.baris; i++) {
            for (int j = 0; j < B.kolom; j++) {
                int jumlah = 0;
                for (int k = 0; k < this.kolom; k++) {
                    jumlah += this.nilai[i][k] * B.nilai[k][j];
                }
                C.nilai[i][j] = jumlah;
            }
        }
        return C;
    }

    //output
    public void cetakMatriks(String judul) {
        System.out.println(judul + " (" + baris + "x" + kolom + ")");
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                System.out.printf("%6d", this.nilai[i][j]);
            }
            System.out.println();
        }
    }
}

//class 2
class Menu {
    private Scanner input;
    private MyMatriks A;
    private MyMatriks B;
    private int pilihan;
    private boolean sudahIsi;

    //constructor
    public Menu(Scanner input) {
        this.input = input;
        this.A = new MyMatriks();
        this.B = new MyMatriks();
        this.pilihan = -1;
        this.sudahIsi = false;
    }

    //output
    public void tampilkanMenuUtama() {
        System.out.println("\n=================================================");
        System.out.println("            PROGRAM OPERASI MATRIKS");
        System.out.println("=================================================");
        System.out.println("1. Input Matriks A dan B");
        System.out.println("2. Penjumlahan A + B");
        System.out.println("3. Perkalian A x B");
        System.out.println("0. Keluar");
    }

    public static void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    //input
    public void inputPilihan() {
        pilihan = MyMatriks.bacaInt(input, "Pilih menu : ", 0, 3);
    }

    public void inputMatriks() {
        System.out.println("\nOrdo Matriks A:");
        int bA = MyMatriks.bacaInt(input, "  Jumlah baris : ", 1, 10);
        int kA = MyMatriks.bacaInt(input, "  Jumlah kolom : ", 1, 10);
        A.setOrdo(bA, kA);

        System.out.println("\nOrdo Matriks B:");
        int bB = MyMatriks.bacaInt(input, "  Jumlah baris : ", 1, 10);
        int kB = MyMatriks.bacaInt(input, "  Jumlah kolom : ", 1, 10);
        B.setOrdo(bB, kB);

        System.out.println();
        A.isiMatriks(input, "A");
        System.out.println();
        B.isiMatriks(input, "B");

        System.out.println("\n-------------------------------------");
        A.cetakMatriks("Matriks A");
        B.cetakMatriks("Matriks B");
        sudahIsi = true;
    }

    //proses
    public void prosesJumlah() {
        System.out.println("\n=== PENJUMLAHAN A + B ===");
        if (A.bisaDijumlah(B)) {
            MyMatriks C = new MyMatriks();
            C.addMatriks(A, B);                      // bentuk void
            C.cetakMatriks("Hasil (void, C.addMatriks(A, B))");

            MyMatriks D = A.plusMatriks(B);          // bentuk fungsi
            D.cetakMatriks("Hasil (fungsi, A.plusMatriks(B))");
        } else {
            tampilkanPesan("Tidak bisa dijumlahkan: ordo A dan B harus sama.");
        }
    }

    public void prosesKali() {
        System.out.println("\n=== PERKALIAN A x B ===");
        if (A.bisaDikali(B)) {
            MyMatriks E = new MyMatriks();
            E.mulMatriks(A, B);                      // bentuk void
            E.cetakMatriks("Hasil (void, E.mulMatriks(A, B))");

            MyMatriks F = A.kaliMatriks(B);          // bentuk fungsi
            F.cetakMatriks("Hasil (fungsi, A.kaliMatriks(B))");
        } else {
            tampilkanPesan("Tidak bisa dikalikan: jumlah kolom A harus sama dengan jumlah baris B.");
        }
    }

    public void prosesPilihan() {
        switch (pilihan) {
            case 1:
                inputMatriks();
                break;
            case 2:
                if (sudahIsi) prosesJumlah();
                else tampilkanPesan("Data belum diisi! Pilih menu 1 terlebih dahulu.");
                break;
            case 3:
                if (sudahIsi) prosesKali();
                else tampilkanPesan("Data belum diisi! Pilih menu 1 terlebih dahulu.");
                break;
            case 0:
                tampilkanPesan("\nProgram selesai. Terima kasih!");
                break;
            default:
                tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
        }
    }

    public void jalankan() {
        do {
            tampilkanMenuUtama();
            inputPilihan();
            prosesPilihan();
        } while (pilihan != 0);
    }
}

//class utama
public class Matriks {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu(input);
        menu.jalankan();
        input.close();
    }
}
