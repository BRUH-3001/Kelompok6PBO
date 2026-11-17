/*
Nama Program   : GajiAppLembur.java
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 24 Oktober 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai 
                 menggunakan Array of Object (5 Class: Waktu, Pegawai, ArrayPegawai, Menu, dan Person) dengan inheritance.
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

    // Getter
    public int getJam() { 
        return jam; 
    }
    public int getMenit() { 
        return menit; 
    }
    public int getDetik() { 
        return detik; 
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

    // Input Waktu
    public void inputWaktu(Scanner input, String jenis) {
        System.out.println("Masukkan Waktu " + jenis + ":");
        this.jam = bacaAngkaValidasi(input, "  Jam (0-23)   : ", 0, 23);
        this.menit = bacaAngkaValidasi(input, "  Menit (0-59) : ", 0, 59);
        this.detik = bacaAngkaValidasi(input, "  Detik (0-59) : ", 0, 59);
    }

    // Proses Selisih
    public Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this.jam * 3600 + this.menit * 60 + this.detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int selisih = akhirSec - awalSec;

        if (selisih < 0) {
            selisih += 24 * 3600;
        }

        return new Waktu(selisih / 3600, (selisih % 3600) / 60, selisih % 60);
    }

    // Output Format
    public String formatWaktu() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }

    public String formatLembur() {
        return String.format("%d:%02d:%02d", jam, menit, detik);
    }
}

class Person {
    protected String nama;
    protected String alamat;

    // Constructor
    public Person() {
        this.nama = "";
        this.alamat = "";
    }

    public Person(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    // Getter
    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    // Setter
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}

class Pegawai extends Person {
    private String nip;
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
        super();
        this.nip = "";
        this.gol = 0;
        this.datang = new Waktu();
        this.pulang = new Waktu();
        this.lamaKerja = new Waktu();
        this.lamaLembur = new Waktu();
    }

    public Pegawai(String nip, String nama, String alamat, int gol, Waktu datang, Waktu pulang) {
        super(nama, alamat);
        this.nip = nip;
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
        System.out.print("Masukkan Alamat       : ");
        alamat = input.nextLine();
        gol = Waktu.bacaAngkaValidasi(input, "Masukkan Golongan(1-4): ", 1, 4);
        datang.inputWaktu(input, "Datang");
        pulang.inputWaktu(input, "Pulang");
        System.out.println("----------------------------------");
    }

    // Method Proses
    public void proses() {
        lamaKerja = datang.hitungSelisih(pulang);

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

    public double getTotalGaji() {
        return totalGaji;
    }

    // Method Output
    public void cetakRow(int no, DecimalFormat rupiah) {
        System.out.printf("| %-2d | %-3s | %-10s | %-10s | %-3d | %-8s | %-8s | %-8s | %-10s | %-11s | %-10s | %-10s | %-10s |%n",
                no, nip, nama, alamat, gol,
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

    // Constructor
    public ArrayPegawai(int kapasitas) {
        this.jumlahData = kapasitas;
        this.listPegawai = new Pegawai[kapasitas];
        for (int i = 0; i < kapasitas; i++) {
            this.listPegawai[i] = new Pegawai();
        }
    }

    public void inputDaftarPegawai(Scanner input) {
        for (int i = 0; i < jumlahData; i++) {
            System.out.println("\n--- Input Pegawai ke-" + (i + 1) + " ---");
            listPegawai[i].inputPegawai(input);
        }
    }

    public void HardcodeData() {
        listPegawai[0] = new Pegawai("001", "Ali", "Dago 4", 3, new Waktu(8, 0, 0), new Waktu(17, 15, 10));
        listPegawai[1] = new Pegawai("004", "Ida", "Uber 3", 2, new Waktu(8, 0, 0), new Waktu(15, 30, 0));
        listPegawai[2] = new Pegawai("005", "Adi", "Riung 10", 1, new Waktu(8, 0, 0), new Waktu(18, 0, 0));
    }

    public void prosesSemua() {
        for (int i = 0; i < jumlahData; i++) {
            if (listPegawai[i] != null) {
                listPegawai[i].proses();
            }
        }
    }

    public void cetakLaporan() {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);

        System.out.println("\n               Daftar Gaji Harian PT Informatika");
        String garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+------------+------------+------------+";
        System.out.println(garis);
        System.out.printf("| %-2s | %-3s | %-10s | %-10s | %-3s | %-8s | %-8s | %-8s | %-10s | %-11s | %-10s | %-10s | %-10s |%n",
                "No", "NIP", "Nama", "Alamat", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur", "Gaji Harian", "Lembur", "Total", "Status");
        System.out.println(garis);

        double totalGajiKeseluruhan = 0;
        double gajiMax = listPegawai[0].getTotalGaji();
        double gajiMin = listPegawai[0].getTotalGaji();

        for (int i = 0; i < jumlahData; i++) {
            if (listPegawai[i] != null) {
                listPegawai[i].cetakRow(i + 1, rupiah);

                double g = listPegawai[i].getTotalGaji();
                totalGajiKeseluruhan += g;
                if (g > gajiMax) gajiMax = g;
                if (g < gajiMin) gajiMin = g;
            }
        }
        System.out.println(garis);

        double rataRata = totalGajiKeseluruhan / jumlahData;
        System.out.println("Rata-rata Gaji : Rp " + rupiah.format(rataRata));
        System.out.println("Gaji Tertinggi : Rp " + rupiah.format(gajiMax));
        System.out.println("Gaji Terendah  : Rp " + rupiah.format(gajiMin));
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