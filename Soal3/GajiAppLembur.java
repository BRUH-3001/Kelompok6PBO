/*
Nama Program   : Program Gaji Lembur (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 28/9/2026
Deskripsi      : Program menghitung total gaji yang diterima oleh karyawan
*/


import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

// ============================================
// CLASS 1: Waktu
// ============================================
class Waktu {
    int jam, menit, detik; // Atribut

    // Method Constructor
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

    // Method Input
    public void inputWaktu(Scanner input, String jenis) {
        System.out.println("Masukkan Waktu " + jenis + ":");
        System.out.print("  Jam (0-23)   : ");
        this.jam = input.nextInt();
        System.out.print("  Menit (0-59) : ");
        this.menit = input.nextInt();
        System.out.print("  Detik (0-59) : ");
        this.detik = input.nextInt();
    }

    // Method Proses (Hitung Selisih Waktu)
    public Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this.jam * 3600 + this.menit * 60 + this.detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhirSec - awalSec;

        // Menangani jika melewati tengah malam (pulang di hari berikutnya)
        if (diff < 0) {
            diff += 24 * 3600;
        }

        return new Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    // Method Output (Format Jam)
    public String formatWaktu() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }

    public String formatLembur() {
        // Format khusus lembur sesuai contoh output (misal: 1:15:10)
        return String.format("%d:%02d:%02d", jam, menit, detik); 
    }
}

// ============================================
// CLASS 2: Pegawai
// ============================================
class Pegawai {
    // Atribut
    private String nip;
    private String nama;
    private int gol;
    private Waktu datang;
    private Waktu pulang;

    private Waktu lamaKerja;
    private Waktu lamaLembur;
    private double gajiHarian;
    private double uangLembur;
    private double totalGaji;
    private String statusPeringatan;

    // Method Constructor
    public Pegawai() {
        this.nip = "";
        this.nama = "";
        this.gol = 0;
        this.datang = new Waktu();
        this.pulang = new Waktu();
        this.lamaKerja = new Waktu();
        this.lamaLembur = new Waktu();
    }

    public Pegawai(String nip, String nama, int gol, Waktu datang, Waktu pulang) {
        this.nip = nip;
        this.nama = nama;
        this.gol = gol;
        this.datang = datang;
        this.pulang = pulang;
        this.lamaKerja = new Waktu();
        this.lamaLembur = new Waktu();
    }

    // Method Input
    public void inputPegawai(Scanner input) {
        System.out.print("Masukkan NIP          : ");
        nip = input.next();
        input.nextLine(); // membersihkan buffer
        System.out.print("Masukkan Nama         : ");
        nama = input.nextLine();
        System.out.print("Masukkan Golongan(1-4): ");
        gol = input.nextInt();
        datang.inputWaktu(input, "Datang");
        pulang.inputWaktu(input, "Pulang");
        System.out.println("----------------------------------");
    }

    // Method Proses
    public void proses() {
        lamaKerja = datang.hitungSelisih(pulang);
        
        // Konversi ke total detik agar kalkulasi akurat
        int detikKerja = lamaKerja.jam * 3600 + lamaKerja.menit * 60 + lamaKerja.detik;
        int detik8Jam = 8 * 3600;

        if (detikKerja >= detik8Jam) {
            statusPeringatan = "peringatan";
            int sisaLembur = detikKerja - detik8Jam;
            lamaLembur = new Waktu(sisaLembur / 3600, (sisaLembur % 3600) / 60, sisaLembur % 60);
        } else {
            statusPeringatan = "ok";
            lamaLembur = new Waktu(0, 0, 0);
        }

        // Pembulatan ke bawah dengan mengambil integer nilai jam lembur saja
        int jamLemburAktif = lamaLembur.jam; 

        switch (gol) {
            case 1:
                gajiHarian = 150000;
                uangLembur = jamLemburAktif * 50000;
                break;
            case 2:
                gajiHarian = 200000;
                uangLembur = jamLemburAktif * 75000;
                break;
            case 3:
                gajiHarian = 400000;
                uangLembur = jamLemburAktif * 150000;
                break;
            case 4:
                gajiHarian = 500000;
                uangLembur = jamLemburAktif * 200000;
                break;
            default:
                gajiHarian = 0;
                uangLembur = 0;
                break;
        }
        totalGaji = gajiHarian + uangLembur;
    }

    // Method Output (Mencetak baris tabel)
    public void cetakRow(int no, DecimalFormat rupiah) {
        System.out.printf("| %-2d | %-3s | %-10s | %-3d | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |%n",
                no, nip, nama, gol,
                datang.formatWaktu(), pulang.formatWaktu(),
                lamaKerja.formatWaktu(),
                lamaLembur.formatLembur(),
                rupiah.format(gajiHarian),
                rupiah.format(uangLembur),
                rupiah.format(totalGaji),
                statusPeringatan);
    }
}

// ============================================
// CLASS 3: Utama (Main)
// ============================================
public class GajiAppLembur {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;
        Pegawai[] daftarPegawai = new Pegawai[3]; // Minimal 3 objek

        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM GAJI HARIAN PT INFORMATIKA");
            System.out.println("=================================================");
            System.out.println("1. Input Data Pegawai Dinamis (3 Objek)");
            System.out.println("2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu : ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    for (int i = 0; i < 3; i++) {
                        System.out.println("\n--- Input Pegawai ke-" + (i + 1) + " ---");
                        daftarPegawai[i] = new Pegawai();
                        daftarPegawai[i].inputPegawai(input);
                        daftarPegawai[i].proses();
                    }
                    cetakLaporan(daftarPegawai);
                    break;
                case 2:
                    // Data hardcode (dummy) untuk menguji format sesuai contoh
                    daftarPegawai[0] = new Pegawai("001", "Ali", 3, new Waktu(8, 0, 0), new Waktu(17, 15, 10)); // Sesuai contoh (lembur 1 jam)
                    daftarPegawai[1] = new Pegawai("002", "Budi", 2, new Waktu(7, 30, 0), new Waktu(12, 0, 0));  // Kurang dari 8 jam (Peringatan)
                    daftarPegawai[2] = new Pegawai("003", "Citra", 4, new Waktu(8, 0, 0), new Waktu(20, 30, 0)); // Lembur > 4 jam
                    
                    // Memproses semua objek di array
                    for (int i = 0; i < 3; i++) {
                        daftarPegawai[i].proses();
                    }
                    cetakLaporan(daftarPegawai);
                    break;
                case 0:
                    System.out.println("\nProgram selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (pilihan != 0);

        input.close();
    }

    private static void cetakLaporan(Pegawai[] daftar) {
        if (daftar[0] == null) {
            System.out.println("Data belum diisi! Silakan input data terlebih dahulu.");
            return;
        }

        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);

        System.out.println("\nDaftar Gaji Harian PT Informatika");
        String garis = "+----+-----+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+";
        System.out.println(garis);
        // Header disejajarkan sesuai dengan panjang spasi kolom agar lebih rapi
        System.out.printf("| %-2s | %-3s | %-10s | %-3s | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |%n",
                "No", "NIP", "Nama", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur", "Gaji Harian", "Lembur", "Total", "Status");
        System.out.println(garis);

        for (int i = 0; i < daftar.length; i++) {
            if (daftar[i] != null) {
                daftar[i].cetakRow(i + 1, rupiah);
            }
        }
        System.out.println(garis);
    }
}