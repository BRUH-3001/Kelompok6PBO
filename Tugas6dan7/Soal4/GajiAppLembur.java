/*
Nama Program   : Program Gaji Lembur (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 6 Oktober 2026
Deskripsi      : Program menghitung total gaji yang diterima oleh karyawan
                 dengan 4 class: Waktu, Pegawai, Menu, dan GajiAppLembur
*/

import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

//class 1
class Waktu {
    private int jam, menit, detik;

    //constructor
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

    //setter
    public void setJam(int jam) {
        this.jam = jam;
    }

    public void setMenit(int menit) {
        this.menit = menit;
    }

    public void setDetik(int detik) {
        this.detik = detik;
    }

    public void setWaktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    //getter
    public int getJam() {
        return jam;
    }

    public int getMenit() {
        return menit;
    }

    public int getDetik() {
        return detik;
    }

    // input dg validator
    public void inputWaktu(Scanner input, String jenis) {
        System.out.println("Masukkan Waktu " + jenis + ":");
        this.jam = bacaAngkaValidasi(input, "  Jam (0-23)   : ", 0, 23);
        this.menit = bacaAngkaValidasi(input, "  Menit (0-59) : ", 0, 59);
        this.detik = bacaAngkaValidasi(input, "  Detik (0-59) : ", 0, 59);
    }

    // helper: membaca validasi
    public static int bacaAngkaValidasi(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String teks = input.next();
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

    // proses
    public Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this.jam * 3600 + this.menit * 60 + this.detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhirSec - awalSec;

        if (diff < 0) {
            diff += 24 * 3600;
        }

        return new Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    // ============ OUTPUT FORMAT ============
    public String formatWaktu() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }

    public String formatLembur() {
        return String.format("%d:%02d:%02d", jam, menit, detik);
    }

    @Override
    public String toString() {
        return formatWaktu();
    }
}

//class 2
class Menu {
    private Scanner input;

    public Menu(Scanner input) {
        this.input = input;
    }

    public int tampilkanMenuUtama() {
        System.out.println("\n=================================================");
        System.out.println("         PROGRAM GAJI HARIAN PT INFORMATIKA");
        System.out.println("=================================================");
        System.out.println("1. Input Data Pegawai Dinamis (3 Objek)");
        System.out.println("2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu : ");

        return Waktu.bacaAngkaValidasi(input, "", 0, 2);
    }

    public static void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}

//class 3
class Pegawai {
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

    // method constructor
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

    // method input
    public void inputPegawai(Scanner input) {
        System.out.print("Masukkan NIP          : ");
        nip = input.next();
        input.nextLine(); // membersihkan buffer
        System.out.print("Masukkan Nama         : ");
        nama = input.nextLine();
        System.out.print("Masukkan Golongan(1-4): ");
        gol = Waktu.bacaAngkaValidasi(input, "", 1, 4);
        datang.inputWaktu(input, "Datang");
        pulang.inputWaktu(input, "Pulang");
        System.out.println("----------------------------------");
    }

    // method proses
    public void proses() {
        lamaKerja = datang.hitungSelisih(pulang);

        //kKonversi ke total detik agar kalkulasi akurat
        int detikKerja = lamaKerja.getJam() * 3600 + lamaKerja.getMenit() * 60 + lamaKerja.getDetik();
        int detik8Jam = 8 * 3600;

        if (detikKerja >= detik8Jam) {
            statusPeringatan = "ok";
            int sisaLembur = detikKerja - detik8Jam;
            lamaLembur = new Waktu(sisaLembur / 3600, (sisaLembur % 3600) / 60, sisaLembur % 60);
        } else {
            statusPeringatan = "peringatan";
            lamaLembur = new Waktu(0, 0, 0);
        }

        // pembulatan ke bawah dengan mengambil integer nilai jam lembur
        int jamLemburAktif = lamaLembur.getJam();

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

    // method output
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

//class 4
public class GajiAppLembur {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu(input);
        int pilihan;
        Pegawai[] daftarPegawai = new Pegawai[3];

        do {
            pilihan = menu.tampilkanMenuUtama();

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
                    daftarPegawai[0] = new Pegawai("001", "Ali", 3, new Waktu(8, 0, 0), new Waktu(17, 15, 10));
                    daftarPegawai[1] = new Pegawai("002", "Budi", 2, new Waktu(7, 30, 0), new Waktu(12, 0, 0));
                    daftarPegawai[2] = new Pegawai("003", "Citra", 4, new Waktu(8, 0, 0), new Waktu(20, 30, 0));

                    for (int i = 0; i < 3; i++) {
                        daftarPegawai[i].proses();
                    }
                    cetakLaporan(daftarPegawai);
                    break;
                case 0:
                    Menu.tampilkanPesan("\nProgram selesai. Terima kasih!");
                    break;
                default:
                    Menu.tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
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
