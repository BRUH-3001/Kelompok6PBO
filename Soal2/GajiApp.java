import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class Gaji {
    private String nip;
    private String nama;
    private int golongan;

    private int jamMasuk, menitMasuk;
    private int jamKeluar, menitKeluar;
    private int lamaJam, lamaMenit;

    private float gapok;
    private float tunjangan;
    private float potongan;
    private float gajiTotal;

    public Gaji() {
        this.nip = "";
        this.nama = "";
        this.golongan = 0;
    }

    public Gaji(String nip, String nama, int golongan,
                int jamMasuk, int menitMasuk, int jamKeluar, int menitKeluar) {
        this.nip = nip;
        this.nama = nama;
        this.golongan = golongan;
        this.jamMasuk = jamMasuk;
        this.menitMasuk = menitMasuk;
        this.jamKeluar = jamKeluar;
        this.menitKeluar = menitKeluar;
    }

    public void inputDariKelas(Scanner input) {
        System.out.print("Masukkan NIP            : ");
        nip = input.nextLine();
        System.out.print("Masukkan nama lengkap   : ");
        nama = input.nextLine();
        System.out.print("Masukkan golongan (1-4) : ");
        golongan = input.nextInt();
        System.out.print("Jam masuk (0-23)        : ");
        jamMasuk = input.nextInt();
        System.out.print("Menit masuk (0-59)      : ");
        menitMasuk = input.nextInt();
        System.out.print("Jam keluar (0-23)       : ");
        jamKeluar = input.nextInt();
        System.out.print("Menit keluar (0-59)     : ");
        menitKeluar = input.nextInt();
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

    private void hitungLamaKerja() {
        int totalMasuk = jamMasuk * 60 + menitMasuk;
        int totalKeluar = jamKeluar * 60 + menitKeluar;
        int selisih = totalKeluar - totalMasuk;

        if (selisih < 0) {
            selisih += 24 * 60;
        }

        lamaJam = selisih / 60;
        lamaMenit = selisih % 60;
    }

    public void proses() {
        hitungGapok();
        hitungPotongan();
        hitungTunjangan();
        hitungLamaKerja();
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

        System.out.printf("Waktu Masuk  : %02d:%02d%n", jamMasuk, menitMasuk);
        System.out.printf("Waktu Keluar : %02d:%02d%n", jamKeluar, menitKeluar);
        System.out.printf("Lama Kerja   : %d jam %d menit%n", lamaJam, lamaMenit);
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
            System.out.println("1. Input Konstan (Hardcode)");
            System.out.println("2. Input Lewat Constructor");
            System.out.println("3. Input Dari Dalam Kelas");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu : ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    modeKonstan();
                    break;
                case 2:
                    modeConstructor(input);
                    break;
                case 3:
                    modeInputDalam(input);
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

    private static void modeKonstan() {
        Gaji karyawan = new Gaji("E001", "Budi Santoso", 3, 8, 0, 16, 30);
        karyawan.proses();
        karyawan.cetak();
    }

    private static void modeConstructor(Scanner input) {
        System.out.print("Masukkan NIP            : ");
        String nip = input.nextLine();
        System.out.print("Masukkan nama lengkap   : ");
        String nama = input.nextLine();
        System.out.print("Masukkan golongan (1-4) : ");
        int golongan = input.nextInt();
        System.out.print("Jam masuk (0-23)        : ");
        int jamMasuk = input.nextInt();
        System.out.print("Menit masuk (0-59)      : ");
        int menitMasuk = input.nextInt();
        System.out.print("Jam keluar (0-23)       : ");
        int jamKeluar = input.nextInt();
        System.out.print("Menit keluar (0-59)     : ");
        int menitKeluar = input.nextInt();
        input.nextLine();

        Gaji karyawan = new Gaji(nip, nama, golongan, jamMasuk, menitMasuk, jamKeluar, menitKeluar);
        karyawan.proses();
        karyawan.cetak();
    }

    private static void modeInputDalam(Scanner input) {
        Gaji karyawan = new Gaji();
        karyawan.inputDariKelas(input);
        karyawan.proses();
        karyawan.cetak();
    }
}