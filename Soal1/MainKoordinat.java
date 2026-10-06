/*
Nama Program : MainKoordinat.java
Nama Kelompok: Muhammad Yunus Habiby (140810250014)
               Azrel Sakhi Reswara (140810250098)
               Muhammad Kemal Firdaus (1408102500101)
Tanggal Buat : 06 Oktober 2026
Deskripsi    : Program OOP Koordinat Kartesian dengan Passing Object & Menu (Java)
*/

import java.util.Scanner;

class Koordinat {
    private float absis;   // Sumbu X
    private float ordinat; // Sumbu Y

    // --- Constructor ---
    public Koordinat() {
        this.absis = 0;
        this.ordinat = 0;
    }

    public Koordinat(float absis, float ordinat) {
        this.absis = absis;
        this.ordinat = ordinat;
    }

    // --- Setter & Getter ---
    public void setAbsis(float absis) { 
        this.absis = absis; 
    }
    public void setOrdinat(float ordinat) { 
        this.ordinat = ordinat; 
    }
    public void setKoordinat(float absis, float ordinat) {
        this.absis = absis;
        this.ordinat = ordinat;
    }

    public float getAbsis() { 
        return absis;
    }
    public float getOrdinat() { 
        return ordinat; 
    }

    // --- Input & Output (Dalam Class) ---
    public void inputKoordinat() {
        Scanner input = new Scanner(System.in);
        System.out.print("  Masukkan Absis (X)   : ");
        this.absis = input.nextFloat();
        System.out.print("  Masukkan Ordinat (Y) : ");
        this.ordinat = input.nextFloat();
    }

    public void printKoordinat() {
        System.out.println("(" + absis + ", " + ordinat + ")");
    }

    // --- CARA 1: Method Fungsi Return ---
    public Koordinat titikTengahReturn(Koordinat P) {
        return new Koordinat((P.absis + this.absis) / 2.0f, (P.ordinat + this.ordinat) / 2.0f);
    }

    public Koordinat cerminSumbuXReturn() {
        return new Koordinat(this.absis, -this.ordinat);
    }

    public Koordinat cerminSumbuYReturn() {
        return new Koordinat(-this.absis, this.ordinat);
    }

    public float hitungJarakReturn(Koordinat P) {
        return (float) Math.sqrt(Math.pow(P.absis - this.absis, 2) + Math.pow(P.ordinat - this.ordinat, 2));
    }

    // --- CARA 2: Method Prosedur Void ---
    public void titikTengahVoid(Koordinat P1, Koordinat P2) {
        this.absis = (P1.absis + P2.absis) / 2.0f;
        this.ordinat = (P1.ordinat + P2.ordinat) / 2.0f;
    }

    public void cerminSumbuXVoid() { 
        this.ordinat = -this.ordinat; 
    }
    public void cerminSumbuYVoid() { 
        this.absis = -this.absis; 
    }

    public void hitungJarakVoid(Koordinat P1, Koordinat P2) {
        float d = (float) Math.sqrt(Math.pow(P2.absis - P1.absis, 2) + Math.pow(P2.ordinat - P1.ordinat, 2));
        System.out.println("Jarak ke Titik Pembanding = " + d);
    }
}

class Menu {
    // Output Luar Class
    public static void printKoordinatLuar(Koordinat K) {
        System.out.println("Nilai Absis = " + K.getAbsis() + ", Nilai Ordinat = " + K.getOrdinat());
    }

    public static void subMenuProses(Koordinat ttkAktif, Koordinat ttkPembanding, String namaObjek, Scanner scan) {
        Koordinat ttkHasil = new Koordinat();

        System.out.println("\n========================================");
        System.out.println("   PILIHAN METODE UNTUK " + namaObjek);
        System.out.println("========================================");
        System.out.println("1. Cara 1 (Fungsi Return)");
        System.out.println("2. Cara 2 (Prosedur Void)");
        System.out.print("Pilih Metode (1-2): ");
        int cara = scan.nextInt();

        System.out.println("\n--- PILIHAN PROSES PERHITUNGAN ---");
        System.out.println("1. Titik Tengah (dengan titik pembanding)");
        System.out.println("2. Pencerminan Terhadap Sumbu X");
        System.out.println("3. Pencerminan Terhadap Sumbu Y");
        System.out.println("4. Hitung Jarak (dengan titik pembanding)");
        System.out.print("Pilih Proses (1-4): ");
        int proses = scan.nextInt();

        System.out.println("\n>>> HASIL PERHITUNGAN <<<");
        if (cara == 1) { // CARA 1 (RETURN)
            if (proses == 1) {
                ttkHasil = ttkAktif.titikTengahReturn(ttkPembanding);
                System.out.print("Titik Tengah = "); 
                ttkHasil.printKoordinat();
            } else if (proses == 2) {
                ttkHasil = ttkAktif.cerminSumbuXReturn();
                System.out.print("Hasil Pencerminan Sumbu X = "); 
                ttkHasil.printKoordinat();
            } else if (proses == 3) {
                ttkHasil = ttkAktif.cerminSumbuYReturn();
                System.out.print("Hasil Pencerminan Sumbu Y = "); 
                ttkHasil.printKoordinat();
            } else if (proses == 4) {
                System.out.println("Jarak ke Titik Pembanding = " + ttkAktif.hitungJarakReturn(ttkPembanding));
            }
        } else { // CARA 2 (VOID)
            if (proses == 1) {
                ttkHasil.titikTengahVoid(ttkAktif, ttkPembanding);
                System.out.print("Titik Tengah = "); 
                ttkHasil.printKoordinat();
            } else if (proses == 2) {
                Koordinat temp = new Koordinat(ttkAktif.getAbsis(), ttkAktif.getOrdinat());
                temp.cerminSumbuXVoid();
                System.out.print("Hasil Pencerminan Sumbu X = "); 
                temp.printKoordinat();
            } else if (proses == 3) {
                Koordinat temp = new Koordinat(ttkAktif.getAbsis(), ttkAktif.getOrdinat());
                temp.cerminSumbuYVoid();
                System.out.print("Hasil Pencerminan Sumbu Y = "); 
                temp.printKoordinat();
            } else if (proses == 4) {
                ttkHasil.hitungJarakVoid(ttkAktif, ttkPembanding);
            }
        }
    }

    // Sub Menu Pilihan Perhitungan
    public static void tampilkanMenuUtama(){
        Scanner scan = new Scanner(System.in);

         // Objek 1 & 2 Didefinisikan Awal
        Koordinat ttk1 = new Koordinat(2, 1); // Objek 1: Constructor Parameter
        Koordinat ttk2 = new Koordinat();      // Objek 2: Setter
        ttk2.setKoordinat(6, 3);

        int pilihanObjek;
        do {
            System.out.println("\n========================================");
            System.out.println("       MENU UTAMA OBJEK KOORDINAT");
            System.out.println("========================================");
            System.out.println("1. Objek 1 (Default Constructor : (2, 1))");
            System.out.println("2. Objek 2 (Set via Setter      : (6, 3))");
            System.out.println("3. Objek 3 (Input 2 Titik oleh User)");
            System.out.println("4. Keluar Program");
            System.out.print("Pilihan Objek (1-4): ");
            pilihanObjek = scan.nextInt();

            switch (pilihanObjek) {
                case 1:
                    System.out.println("\n[OBJEK 1 DIPILIH]");
                    System.out.print("Detail Koordinat Utama (Output Luar): "); 
                    printKoordinatLuar(ttk1);
                    System.out.print("Titik Pembanding (ttk2): "); 
                    ttk2.printKoordinat();
                    subMenuProses(ttk1, ttk2, "OBJEK 1", scan);
                    break;
                case 2:
                    System.out.println("\n[OBJEK 2 DIPILIH]");
                    System.out.print("Detail Koordinat Utama (Output Dalam): "); 
                    ttk2.printKoordinat();
                    System.out.print("Titik Pembanding (ttk1): "); 
                    ttk1.printKoordinat();
                    subMenuProses(ttk2, ttk1, "OBJEK 2", scan);
                    break;
                case 3:
                    Koordinat ttkUser1 = new Koordinat();
                    Koordinat ttkUser2 = new Koordinat();
                    System.out.println("\n[OBJEK 3 DIPILIH - INPUT 2 TITIK KOORDINAT]\n");
                    System.out.println("--- Input Titik Pertama (Titik Utama) ---");
                    ttkUser1.inputKoordinat();
                    System.out.println("--- Input Titik Kedua (Titik Pembanding) ---");
                    ttkUser2.inputKoordinat();

                    System.out.print("\nDetail Titik 1: "); 
                    ttkUser1.printKoordinat();
                    System.out.print("Detail Titik 2: "); 
                    ttkUser2.printKoordinat();

                    subMenuProses(ttkUser1, ttkUser2, "OBJEK 3", scan);
                    break;
                case 4:
                    System.out.println("\nProgram selesai!");
                    break;
                default:
                    System.out.println("\nPilihan tidak valid!");
                    break;
            }
        } while (pilihanObjek != 4);

        scan.close();
    }  
}

public class MainKoordinat {
    public static void main(String[] args) {
        Menu.tampilkanMenuUtama();
    }
}