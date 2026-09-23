package GithubRepo.Kelompok6PBO.Soal2;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class Gaji {
    private String nip;
    private String nama;
    private int golongan;

    private float gapok;
    private float tunjangan;
    private float potongan;
    private float gajiTotal;

    public Gaji() {
        this.nip = "";
        this.nama = "";
        this.golongan = 0;
    }

    public Gaji(String nip, String nama, int golongan) {
        this.nip = nip;
        this.nama = nama;
        this.golongan = golongan;
    }

    public void setNip(String nip) { this.nip = nip; }
    public void setNama(String nama) { this.nama = nama; }
    public void setGolongan(int golongan) { this.golongan = golongan; }

    public String getNip() { return nip; }
    public String getNama() { return nama; }
    public int getGolongan() { return golongan; }
    public float getGapok() { return gapok; }
    public float getTunjangan() { return tunjangan; }
    public float getPotongan() { return potongan; }
    public float getGajiTotal() { return gajiTotal; }

    public void inputGaji(Scanner input) {
        System.out.print("Masukkan NIP            : ");
        nip = input.nextLine();
        System.out.print("Masukkan nama lengkap   : ");
        nama = input.nextLine();
        System.out.print("Masukkan golongan (1-4) : ");
        golongan = input.nextInt();
        input.nextLine();
    }

    private void hitungGapok() {
        switch (golongan) {
            case 1: gapok = 1500000; break;
            case 2: gapok = 2000000; break;
            case 3: gapok = 3000000; break;
            case 4: gapok = 5000000; break;
            default: gapok = 0;
        }
    }

    private void hitungPotongan() {
        switch (golongan) {
            case 1: potongan = gapok * 0.01f; break;
            case 2: potongan = gapok * 0.02f; break;
            case 3: potongan = gapok * 0.02f; break;
            case 4: potongan = gapok * 0.04f; break;
            default: potongan = 0;
        }
    }

    private void hitungTunjangan() {
        switch (golongan) {
            case 1: tunjangan = gapok * 0.10f; break;
            case 2: tunjangan = gapok * 0.12f; break;
            case 3: tunjangan = gapok * 0.12f; break;
            case 4: tunjangan = gapok * 0.15f; break;
            default: tunjangan = 0;
        }
    }

    public void proses() {
        hitungGapok();
        hitungPotongan();
        hitungTunjangan();
        gajiTotal = gapok + tunjangan - potongan;
    }

    public void cetak() {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);

        String garis = "+----------------+----------------------+-----+-------------+-------------+-------------+-------------+";
        System.out.println(garis);
        System.out.printf("| %-14s | %-20s | %-3s | %-11s | %-11s | %-11s | %-11s |%n",
                "NIP", "Nama", "Gol", "Gaji Pokok", "Tunjangan", "Potongan", "Gaji Total");
        System.out.println(garis);
        System.out.printf("| %-14s | %-20s | %-3d | %11s | %11s | %11s | %11s |%n",
                nip, nama, golongan,
                rupiah.format(gapok), rupiah.format(tunjangan),
                rupiah.format(potongan), rupiah.format(gajiTotal));
        System.out.println(garis);
    }
}

public class GajiApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;
        int jumlahObjek = 0;

        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM GAJI KARYAWAN (jalankan objek)");
            System.out.println("=================================================");
            System.out.println("Objek yang sudah dijalankan : " + jumlahObjek);
            System.out.println("1. Jalankan objek Gaji baru (input data karyawan)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu : ");
            pilihan = input.nextInt();
            input.nextLine();

            if (pilihan == 1) {
                jumlahObjek++;
                System.out.println("\n--- Objek ke-" + jumlahObjek + " ---");
                Gaji karyawan = new Gaji();
                karyawan.inputGaji(input);
                karyawan.proses();
                karyawan.cetak();
            } else if (pilihan == 0) {
                if (jumlahObjek < 3) {
                    System.out.println("\nProgram ini wajib menjalankan minimal 3 objek sebelum keluar.");
                    System.out.println("Objek yang baru dijalankan: " + jumlahObjek + ". Silakan lanjutkan.");
                    pilihan = -1;
                } else {
                    System.out.println("\nProgram selesai. Total objek yang dijalankan: " + jumlahObjek);
                }
            } else {
                System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (!(pilihan == 0 && jumlahObjek >= 3));

        input.close();
    }
}
