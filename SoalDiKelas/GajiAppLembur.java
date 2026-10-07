/*
Nama Program   : GajiAppLembur.java
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 7 Oktober 2026
Deskripsi      : Program menghitung total gaji yang diterima oleh karyawan
                 dengan 4 class: Waktu, Pegawai, ArrayPegawai, dan Menu.
*/

import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class Waktu {
    private int jam, menit, detik;

    // Constructor
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

    // Setter
    public void setJam(int jam) { this.jam = jam; }
    public void setMenit(int menit) { this.menit = menit; }
    public void setDetik(int detik) { this.detik = detik; }

    public void setWaktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    // Getter
    public int getJam() { return jam; }
    public int getMenit() { return menit; }
    public int getDetik() { return detik; }

    // Input dg validator
    public void inputWaktu(Scanner input, String jenis) {
        System.out.println("Masukkan Waktu " + jenis + ":");
        this.jam = bacaAngkaValidasi(input, "  Jam (0-23)   : ", 0, 23);
        this.menit = bacaAngkaValidasi(input, "  Menit (0-59) : ", 0, 59);
        this.detik = bacaAngkaValidasi(input, "  Detik (0-59) : ", 0, 59);
    }

    // Helper: membaca validasi
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

    // Proses
    public Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this.jam * 3600 + this.menit * 60 + this.detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhirSec - awalSec;

        if (diff < 0) {
            diff += 24 * 3600;
        }

        return new Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    // Output Format
    public String formatWaktu() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }

    public String formatLembur() {
        return String.format("%d:%02d:%02d", jam, menit, detik);
    }
}

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
        gol = Waktu.bacaAngkaValidasi(input, "Masukkan Golongan(1-4): ", 1, 4);
        datang.inputWaktu(input, "Datang");
        pulang.inputWaktu(input, "Pulang");
        System.out.println("----------------------------------");
    }

    // Method Proses
    public void proses() {
        lamaKerja = datang.hitungSelisih(pulang);

        // Konversi ke total detik agar kalkulasi akurat
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

        // Pembulatan ke bawah dengan mengambil integer nilai jam lembur
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

    // Method Output
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

class ArrayPegawai {
    private Pegawai[] listPegawai;
    private int jumlahData;

    public ArrayPegawai(int kapasitas) {
        this.listPegawai = new Pegawai[kapasitas];
        this.jumlahData = kapasitas;
    }

    public void inputDaftarPegawai(Scanner input) {
        for (int i = 0; i < jumlahData; i++) {
            System.out.println("\n--- Input Pegawai ke-" + (i + 1) + " ---");
            listPegawai[i] = new Pegawai();
            listPegawai[i].inputPegawai(input);
        }
    }

    public void HardcodeData() {
        listPegawai[0] = new Pegawai("001", "Azrel", 3, new Waktu(8, 30, 0), new Waktu(16, 0, 0));
        listPegawai[1] = new Pegawai("002", "Kemal", 4, new Waktu(9, 0, 0), new Waktu(21, 30, 0));
        listPegawai[2] = new Pegawai("003", "Yunus", 2, new Waktu(12, 40, 0), new Waktu(23, 30, 0));
    }

    public void prosesSemua() {
        for (int i = 0; i < jumlahData; i++) {
            if (listPegawai[i] != null) {
                listPegawai[i].proses();
            }
        }
    }

    public void cetakLaporan() {
        if (listPegawai[0] == null) {
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

        for (int i = 0; i < jumlahData; i++) {
            if (listPegawai[i] != null) {
                listPegawai[i].cetakRow(i + 1, rupiah);
            }
        }
        System.out.println(garis);
    }
}

class Menu {
    private Scanner input;

    public Menu(Scanner input) {
        this.input = input;
    }

    public void jalankanMenu() {
        int pilihan;
        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM GAJI HARIAN PT INFORMATIKA");
            System.out.println("=================================================");
            System.out.println("1. Input Data Pegawai Dinamis (3 Objek)");
            System.out.println("2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)");
            System.out.println("0. Keluar");

            pilihan = Waktu.bacaAngkaValidasi(input, "Pilih menu : ", 0, 2);

            switch (pilihan) {
                case 1:
                    ArrayPegawai arrDinamis = new ArrayPegawai(3);
                    arrDinamis.inputDaftarPegawai(input);
                    arrDinamis.prosesSemua();
                    arrDinamis.cetakLaporan();
                    break;
                case 2:
                    ArrayPegawai arrHardcode = new ArrayPegawai(3);
                    arrHardcode.HardcodeData();
                    arrHardcode.prosesSemua();
                    arrHardcode.cetakLaporan();
                    break;
                case 0:
                    tampilkanPesan("\nProgram selesai. Terima kasih!");
                    break;
                default:
                    tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (pilihan != 0);
    }

    public static void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}

public class GajiAppLembur {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu(input);

        menu.jalankanMenu();

        input.close();
    }
}