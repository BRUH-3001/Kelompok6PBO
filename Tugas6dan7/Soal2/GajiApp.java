import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

// ==========================================
// CLASS VALIDATOR (Handling Error Input)
// ==========================================
class Validator {
    // Helper: membaca validasi angka dengan batas min & max
    public static int bacaAngkaValidasi(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String teks = input.nextLine();
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

    // Helper: membaca input string agar aman (tidak terlewat oleh nextInt sebelumnya)
    public static String bacaString(Scanner input, String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }
}

// ==========================================
// CLASS GAJI
// ==========================================
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
        // Menggunakan Validator untuk semua input
        nip = Validator.bacaString(input, "Masukkan NIP            : ");
        nama = Validator.bacaString(input, "Masukkan nama lengkap   : ");
        golongan = Validator.bacaAngkaValidasi(input, "Masukkan golongan (1-4) : ", 1, 4);
        jamMasuk = Validator.bacaAngkaValidasi(input, "Jam masuk (0-23)        : ", 0, 23);
        menitMasuk = Validator.bacaAngkaValidasi(input, "Menit masuk (0-59)      : ", 0, 59);
        jamKeluar = Validator.bacaAngkaValidasi(input, "Jam keluar (0-23)       : ", 0, 23);
        menitKeluar = Validator.bacaAngkaValidasi(input, "Menit keluar (0-59)     : ", 0, 59);
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

// ==========================================
// CLASS MENU (Menangani Tampilan & Alur)
// ==========================================
class MenuGaji {
    public void jalankan() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM GAJI KARYAWAN (jalankan objek)");
            System.out.println("=================================================");
            System.out.println("1. Input Konstan (Hardcode)");
            System.out.println("2. Input Lewat Constructor");
            System.out.println("3. Input Dari Dalam Kelas");
            System.out.println("0. Keluar");
            
            // Validasi input menu
            pilihan = Validator.bacaAngkaValidasi(input, "Pilih menu (0-3) : ", 0, 3);

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
            }
        } while (pilihan != 0);

        input.close();
    }

    private void modeKonstan() {
        Gaji karyawan = new Gaji("E001", "Budi Santoso", 3, 8, 0, 16, 30);
        karyawan.proses();
        karyawan.cetak();
    }

    private void modeConstructor(Scanner input) {
        // Menggunakan Validator agar input tidak error
        String nip = Validator.bacaString(input, "Masukkan NIP            : ");
        String nama = Validator.bacaString(input, "Masukkan nama lengkap   : ");
        int golongan = Validator.bacaAngkaValidasi(input, "Masukkan golongan (1-4) : ", 1, 4);
        int jamMasuk = Validator.bacaAngkaValidasi(input, "Jam masuk (0-23)        : ", 0, 23);
        int menitMasuk = Validator.bacaAngkaValidasi(input, "Menit masuk (0-59)      : ", 0, 59);
        int jamKeluar = Validator.bacaAngkaValidasi(input, "Jam keluar (0-23)       : ", 0, 23);
        int menitKeluar = Validator.bacaAngkaValidasi(input, "Menit keluar (0-59)     : ", 0, 59);

        Gaji karyawan = new Gaji(nip, nama, golongan, jamMasuk, menitMasuk, jamKeluar, menitKeluar);
        karyawan.proses();
        karyawan.cetak();
    }

    private void modeInputDalam(Scanner input) {
        Gaji karyawan = new Gaji();
        karyawan.inputDariKelas(input);
        karyawan.proses();
        karyawan.cetak();
    }
}

// ==========================================
// MAIN EXECUTION
// ==========================================
public class GajiApp {
    public static void main(String[] args) {
        MenuGaji menu = new MenuGaji();
        menu.jalankan();
    }
}