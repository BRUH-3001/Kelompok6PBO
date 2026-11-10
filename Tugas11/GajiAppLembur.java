/*
Nama Program   : GajiAppLembur.py
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 10 November 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai
                 serta biaya parkir kendaraan (Mobil, Truk, Motor) menggunakan
                 Array of Object, Inheritance, dan Polimorfisme
                 (10 Class: Waktu, Person, Pegawai, ArrayPegawai, Kendaraan,
                 Mobil, Truk, Motor, ArrayKendaraan, Menu).
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
 
    // Method Constructor
    public Pegawai() {
        super();
        this.nip = "";
        this.gol = 0;
        this.datang = new Waktu();
        this.pulang = new Waktu();
    }
 
    public Pegawai(String nip, String nama, String alamat, int gol, Waktu datang, Waktu pulang) {
        super(nama, alamat);
        this.nip = nip;
        this.gol = gol;
        this.datang = datang;
        this.pulang = pulang;
    }
 
    // Getter NIP (dipakai untuk verifikasi pegawai di parkir)
    public String getNip() {
        return nip;
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
 
    // Method Proses (dihitung langsung dari atribut yang ada)
    public Waktu getLamaKerja() {
        return datang.hitungSelisih(pulang);
    }
 
    private int getDetikKerja() {
        Waktu w = getLamaKerja();
        return w.getJam() * 3600 + w.getMenit() * 60 + w.getDetik();
    }
 
    public Waktu getLamaLembur() {
        int sisa = getDetikKerja() - 8 * 3600;
        if (sisa <= 0) {
            return new Waktu(0, 0, 0);
        }
        return new Waktu(sisa / 3600, (sisa % 3600) / 60, sisa % 60);
    }
 
    public String getStatus() {
        return (getDetikKerja() >= 8 * 3600) ? "ok" : "peringatan";
    }
 
    public int getGajiHarian() {
        switch (gol) {
            case 1: return 150000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 500000;
            default: return 0;
        }
    }
 
    public int getUangLembur() {
        int jamLembur = getLamaLembur().getJam();
        switch (gol) {
            case 1: return jamLembur * 50000;
            case 2: return jamLembur * 75000;
            case 3: return jamLembur * 150000;
            case 4: return jamLembur * 200000;
            default: return 0;
        }
    }
 
    public double getTotalGaji() {
        return getGajiHarian() + getUangLembur();
    }
 
    // Method Output
    public void cetakRow(int no, DecimalFormat rupiah) {
        System.out.printf("| %-2d | %-3s | %-10s | %-10s | %-3d | %-8s | %-8s | %-8s | %-10s | %-11s | %-10s | %-10s | %-10s |%n",
                no, nip, nama, alamat, gol,
                datang.formatWaktu(), pulang.formatWaktu(),
                getLamaKerja().formatWaktu(),
                getLamaLembur().formatLembur(),
                rupiah.format(getGajiHarian()),
                rupiah.format(getUangLembur()),
                rupiah.format(getTotalGaji()),
                getStatus());
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
 
    // Dipakai modul parkir: cari pegawai berdasarkan NIP (null jika tidak ada)
    public Pegawai cariByNip(String nip) {
        for (int i = 0; i < jumlahData; i++) {
            if (listPegawai[i] != null && listPegawai[i].getNip().equals(nip)) {
                return listPegawai[i];
            }
        }
        return null;
    }
 
    public Pegawai getPegawai(int index) {
        return listPegawai[index];
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
 
//Modul Parkir
 
abstract class Kendaraan {
    public static final int TAMBAHAN_AREA_DALAM = 1000;
 
    protected String plat;
    protected Person pemilik;      
    protected boolean areaDalam;   
    protected Waktu masuk;
    protected Waktu keluar;
 
    // Constructor
    public Kendaraan(String plat, Person pemilik, boolean areaDalam, Waktu masuk, Waktu keluar) {
        this.plat = plat;
        this.pemilik = pemilik;
        this.areaDalam = areaDalam && bolehAreaDalam();
        this.masuk = masuk;
        this.keluar = keluar;
    }
 
    public abstract String getJenis();
    protected abstract int getTarifUmum();            
    protected abstract int getTarifHarianPegawai();  
 
    protected boolean bolehAreaDalam() {
        return true;
    }
 
    protected boolean pegawaiBerhakTarifHarian() {
        return true;
    }
 
    // Getter
    public String getPlat() { return plat; }
    public boolean isAreaDalam() { return areaDalam; }
    public Waktu getMasuk() { return masuk; }
    public Waktu getKeluar() { return keluar; }
 
    public String getNamaPemilik() {
        return (pemilik == null) ? "-" : pemilik.getNama();
    }
 
    // Polimorfisme: Pegawai adalah Person, dicek dengan instanceof
    public boolean isPegawai() {
        return pemilik instanceof Pegawai;
    }
 
    public String getStatus() {
        if (pemilik == null) return "Tanpa ID";
        return isPegawai() ? "Pegawai" : "Umum";
    }
 
    // Proses
    public Waktu getDurasi() {
        return masuk.hitungSelisih(keluar);
    }
 
    // Dibulatkan ke atas per jam penuh, minimal 1 jam
    public int getJamTagihan() {
        Waktu d = getDurasi();
        int detik = d.getJam() * 3600 + d.getMenit() * 60 + d.getDetik();
        int jam = (detik + 3599) / 3600;
        return (jam < 1) ? 1 : jam;
    }
 
    public boolean tarifHarian() {
        return isPegawai() && pegawaiBerhakTarifHarian();
    }
 
    public int getTarifPerJam() {
        return getTarifUmum() + (areaDalam ? TAMBAHAN_AREA_DALAM : 0);
    }
 
    public int hitungBiaya() {
        if (tarifHarian()) {
            return getTarifHarianPegawai();
        }
        return getJamTagihan() * getTarifPerJam();
    }
 
    public String getTarifTeks(DecimalFormat rupiah) {
        if (tarifHarian()) {
            return rupiah.format(getTarifHarianPegawai()) + "/hari";
        }
        return rupiah.format(getTarifPerJam()) + "/jam";
    }
 
    // Input Kendaraan (dipakai ArrayKendaraan)
    public static DecimalFormat buatFormatRupiah() {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        return new DecimalFormat("#,###", simbol);
    }
}
 
class Mobil extends Kendaraan {
    public static final int TARIF_UMUM = 3000;
    public static final int TARIF_PEGAWAI = 6000;
 
    public Mobil(String plat, Person pemilik, boolean areaDalam, Waktu masuk, Waktu keluar) {
        super(plat, pemilik, areaDalam, masuk, keluar);
    }
 
    @Override
    public String getJenis() { return "Mobil"; }
 
    @Override
    protected int getTarifUmum() { return TARIF_UMUM; }
 
    @Override
    protected int getTarifHarianPegawai() { return TARIF_PEGAWAI; }
}
 
class Truk extends Kendaraan {
    public static final int TARIF_UMUM = 5000;
 
    public Truk(String plat, Person pemilik, boolean areaDalam, Waktu masuk, Waktu keluar) {
        super(plat, pemilik, areaDalam, masuk, keluar);
    }
 
    @Override
    public String getJenis() { return "Truk"; }
 
    @Override
    protected int getTarifUmum() { return TARIF_UMUM; }
 
    @Override
    protected int getTarifHarianPegawai() { return 0; } // tidak dipakai
 
    // Truk pegawai tetap kena tarif umum
    @Override
    protected boolean pegawaiBerhakTarifHarian() { return false; }
 
    // Improvisasi: truk hanya boleh parkir di luar gedung
    @Override
    protected boolean bolehAreaDalam() { return false; }
}
 
class Motor extends Kendaraan {
    public static final int TARIF_UMUM = 2000;
    public static final int TARIF_PEGAWAI = 3000;
 
    public Motor(String plat, Person pemilik, boolean areaDalam, Waktu masuk, Waktu keluar) {
        super(plat, pemilik, areaDalam, masuk, keluar);
    }
 
    @Override
    public String getJenis() { return "Motor"; }
 
    @Override
    protected int getTarifUmum() { return TARIF_UMUM; }
 
    @Override
    protected int getTarifHarianPegawai() { return TARIF_PEGAWAI; }
}
 
class ArrayKendaraan {
    private Kendaraan[] listKendaraan;
    private int jumlahData;
 
    // Lebar kolom tabel laporan
    private static final int[] LEBAR = {2, 10, 5, 10, 8, 6, 8, 8, 8, 5, 10, 10};
 
    // Constructor
    public ArrayKendaraan(int kapasitas) {
        this.jumlahData = kapasitas;
        this.listKendaraan = new Kendaraan[kapasitas];
    }
 
    // ---------- Input ----------
    public void inputDaftarKendaraan(Scanner input, ArrayPegawai dataPegawai) {
        for (int i = 0; i < jumlahData; i++) {
            System.out.println("\n--- Input Kendaraan ke-" + (i + 1) + " ---");
            listKendaraan[i] = inputKendaraan(input, dataPegawai);
            System.out.println("----------------------------------");
        }
    }
 
    private Kendaraan inputKendaraan(Scanner input, ArrayPegawai dataPegawai) {
        int jenis = Waktu.bacaAngkaValidasi(input, "Jenis (1=Mobil, 2=Truk, 3=Motor) : ", 1, 3);
        input.nextLine();
 
        System.out.print("Masukkan Plat Nomor : ");
        String plat = input.nextLine();
 
        boolean areaDalam = false;
        if (jenis == 2) {
            System.out.println("Area : Luar Gedung (truk hanya boleh parkir di luar)");
        } else {
            int area = Waktu.bacaAngkaValidasi(input, "Area (1=Luar Gedung, 2=Dalam Gedung) : ", 1, 2);
            areaDalam = (area == 2);
        }
 
        // Tentukan status pemilik
        Person pemilik = null;
        boolean selesai = false;
        while (!selesai) {
            int status = Waktu.bacaAngkaValidasi(input,
                    "Status (1=Pegawai, 2=Umum dengan identitas, 3=Umum tanpa identitas) : ", 1, 3);
            if (status == 1) {
                System.out.print("Masukkan NIP Pegawai : ");
                String nip = input.next();
                Pegawai p = dataPegawai.cariByNip(nip);
                if (p == null) {
                    System.out.println("  [!] NIP tidak ditemukan di data pegawai, pilih status lagi.");
                } else {
                    pemilik = p;
                    System.out.println("  Pegawai terverifikasi : " + p.getNama());
                    selesai = true;
                }
            } else if (status == 2) {
                input.nextLine(); 
                System.out.print("Masukkan Nama   : ");
                String nama = input.nextLine();
                System.out.print("Masukkan Alamat : ");
                String alamat = input.nextLine();
                pemilik = new Person(nama, alamat);
                selesai = true;
            } else {
                selesai = true; 
            }
        }
 
        Waktu masuk = new Waktu();
        Waktu keluar = new Waktu();
        masuk.inputWaktu(input, "Masuk");
        keluar.inputWaktu(input, "Keluar");
 
        // Polimorfisme: variabel bertipe Kendaraan, objek bisa Mobil/Truk/Motor
        Kendaraan k;
        switch (jenis) {
            case 1:  k = new Mobil(plat, pemilik, areaDalam, masuk, keluar); break;
            case 2:  k = new Truk(plat, pemilik, areaDalam, masuk, keluar); break;
            default: k = new Motor(plat, pemilik, areaDalam, masuk, keluar); break;
        }
        return k;
    }
 
    public void HardcodeData(ArrayPegawai dp) {
        listKendaraan[0] = new Mobil("D 1234 AB", dp.getPegawai(0), false, new Waktu(8, 0, 0), new Waktu(17, 15, 10));
        listKendaraan[1] = new Motor("D 5678 CD", dp.getPegawai(1), true, new Waktu(8, 0, 0), new Waktu(15, 30, 0));
        listKendaraan[2] = new Truk("D 9012 EF", dp.getPegawai(2), false, new Waktu(7, 0, 0), new Waktu(10, 20, 0));
        listKendaraan[3] = new Mobil("B 4321 XY", new Person("Budi", "Dago 7"), true, new Waktu(9, 0, 0), new Waktu(11, 30, 0));
        listKendaraan[4] = new Motor("D 7788 GH", null, false, new Waktu(10, 0, 0), new Waktu(10, 30, 0));
        listKendaraan[5] = new Truk("F 1122 ZZ", null, false, new Waktu(6, 0, 0), new Waktu(12, 0, 0));
    }
 
    // ---------- Output ----------
    private String garis() {
        StringBuilder sb = new StringBuilder("+");
        for (int w : LEBAR) {
            for (int i = 0; i < w + 2; i++) sb.append('-');
            sb.append('+');
        }
        return sb.toString();
    }
 
    private String baris(String... kolom) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < kolom.length; i++) {
            sb.append(String.format(" %-" + LEBAR[i] + "s |", kolom[i]));
        }
        return sb.toString();
    }
 
    public void cetakLaporan() {
        DecimalFormat rupiah = Kendaraan.buatFormatRupiah();
 
        System.out.println("\n                Laporan Parkir Gedung PT Informatika");
        System.out.println(garis());
        System.out.println(baris("No", "Plat", "Jenis", "Pemilik", "Status", "Area",
                "Masuk", "Keluar", "Durasi", "Jam", "Tarif", "Biaya"));
        System.out.println(garis());
 
        int total = 0;
        int biayaMax = listKendaraan[0].hitungBiaya();
        int biayaMin = listKendaraan[0].hitungBiaya();
        int jmlMobil = 0, jmlTruk = 0, jmlMotor = 0, jmlPegawai = 0;
 
        for (int i = 0; i < jumlahData; i++) {
            Kendaraan k = listKendaraan[i];
            if (k == null) continue;
 
            int biaya = k.hitungBiaya(); // polimorfisme: tarif sesuai jenis kendaraan
            System.out.println(baris(
                    String.valueOf(i + 1),
                    k.getPlat(),
                    k.getJenis(),
                    k.getNamaPemilik(),
                    k.getStatus(),
                    k.isAreaDalam() ? "Dalam" : "Luar",
                    k.getMasuk().formatWaktu(),
                    k.getKeluar().formatWaktu(),
                    k.getDurasi().formatWaktu(),
                    String.valueOf(k.getJamTagihan()),
                    k.getTarifTeks(rupiah),
                    rupiah.format(biaya)));
 
            total += biaya;
            if (biaya > biayaMax) biayaMax = biaya;
            if (biaya < biayaMin) biayaMin = biaya;
 
            if (k instanceof Mobil) jmlMobil++;
            else if (k instanceof Truk) jmlTruk++;
            else if (k instanceof Motor) jmlMotor++;
            if (k.isPegawai()) jmlPegawai++;
        }
        System.out.println(garis());
 
        System.out.println("Jumlah Kendaraan   : " + jumlahData
                + " (Mobil: " + jmlMobil + ", Truk: " + jmlTruk + ", Motor: " + jmlMotor + ")");
        System.out.println("Kendaraan Pegawai  : " + jmlPegawai + " | Umum: " + (jumlahData - jmlPegawai));
        System.out.println("Total Pendapatan   : Rp " + rupiah.format(total));
        System.out.println("Rata-rata Biaya    : Rp " + rupiah.format((double) total / jumlahData));
        System.out.println("Biaya Tertinggi    : Rp " + rupiah.format(biayaMax));
        System.out.println("Biaya Terendah     : Rp " + rupiah.format(biayaMin));
    }
}
 
class Menu {
    private Scanner input;
    private ArrayPegawai dataPegawai; // data pegawai aktif, dipakai gaji & verifikasi parkir
 
    public Menu(Scanner input) {
        this.input = input;
        this.dataPegawai = new ArrayPegawai(3);
        this.dataPegawai.HardcodeData();
    }
 
    public void jalankanMenu() {
        int pilihan;
        do {
            System.out.println("\n=================================================");
            System.out.println("              SISTEM PT INFORMATIKA");
            System.out.println("=================================================");
            System.out.println("1. Gaji Harian & Lembur Pegawai");
            System.out.println("2. Parkir Kendaraan");
            System.out.println("0. Keluar");
 
            pilihan = Waktu.bacaAngkaValidasi(input, "Pilih menu : ", 0, 2);
 
            switch (pilihan) {
                case 1:
                    menuGaji();
                    break;
                case 2:
                    menuParkir();
                    break;
                case 0:
                    tampilkanPesan("\nProgram selesai. Terima kasih!");
                    break;
                default:
                    tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (pilihan != 0);
    }
 
    private void menuGaji() {
        int pilihan;
        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM GAJI HARIAN PT INFORMATIKA");
            System.out.println("=================================================");
            System.out.println("1. Input Data Pegawai Dinamis (3 Objek)");
            System.out.println("2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)");
            System.out.println("0. Kembali ke Menu Utama");
 
            pilihan = Waktu.bacaAngkaValidasi(input, "Pilih menu : ", 0, 2);
 
            switch (pilihan) {
                case 1:
                    dataPegawai = new ArrayPegawai(3);
                    dataPegawai.inputDaftarPegawai(input);
                    dataPegawai.cetakLaporan();
                    break;
                case 2:
                    dataPegawai = new ArrayPegawai(3);
                    dataPegawai.HardcodeData();
                    dataPegawai.cetakLaporan();
                    break;
                default:
                    break;
            }
        } while (pilihan != 0);
    }
 
    private void menuParkir() {
        int pilihan;
        do {
            System.out.println("\n=================================================");
            System.out.println("         PROGRAM PARKIR GEDUNG PT INFORMATIKA");
            System.out.println("=================================================");
            System.out.println("1. Input Data Kendaraan Dinamis");
            System.out.println("2. Gunakan Data Hardcode (6 Objek - Contoh)");
            System.out.println("3. Lihat Aturan & Tarif Parkir");
            System.out.println("0. Kembali ke Menu Utama");
 
            pilihan = Waktu.bacaAngkaValidasi(input, "Pilih menu : ", 0, 3);
 
            switch (pilihan) {
                case 1:
                    int jumlah = Waktu.bacaAngkaValidasi(input, "Jumlah kendaraan (1-10) : ", 1, 10);
                    ArrayKendaraan arrDinamis = new ArrayKendaraan(jumlah);
                    arrDinamis.inputDaftarKendaraan(input, dataPegawai);
                    arrDinamis.cetakLaporan();
                    break;
                case 2:
                    ArrayKendaraan arrHardcode = new ArrayKendaraan(6);
                    arrHardcode.HardcodeData(dataPegawai);
                    arrHardcode.cetakLaporan();
                    break;
                case 3:
                    tampilkanAturanParkir();
                    break;
                default:
                    break;
            }
        } while (pilihan != 0);
    }
 
    private void tampilkanAturanParkir() {
        DecimalFormat r = Kendaraan.buatFormatRupiah();
        System.out.println("\n================= ATURAN & TARIF PARKIR =================");
        System.out.println("Tarif Umum (person atau tanpa identitas), per jam:");
        System.out.println("  Mobil : Rp " + r.format(Mobil.TARIF_UMUM));
        System.out.println("  Truk  : Rp " + r.format(Truk.TARIF_UMUM));
        System.out.println("  Motor : Rp " + r.format(Motor.TARIF_UMUM));
        System.out.println("Tarif Pegawai, per hari (<= 24 jam):");
        System.out.println("  Mobil : Rp " + r.format(Mobil.TARIF_PEGAWAI));
        System.out.println("  Motor : Rp " + r.format(Motor.TARIF_PEGAWAI));
        System.out.println("  Truk  : mengikuti tarif umum");
        System.out.println("Aturan tambahan:");
        System.out.println("  - Durasi dibulatkan ke atas per jam, minimal 1 jam.");
        System.out.println("  - Area dalam gedung: tarif umum + Rp " + r.format(Kendaraan.TAMBAHAN_AREA_DALAM) + " per jam.");
        System.out.println("  - Tarif harian pegawai tidak dikenai tambahan area dalam.");
        System.out.println("  - Truk hanya boleh parkir di area luar gedung.");
        System.out.println("  - Pegawai diverifikasi lewat NIP pada data pegawai.");
        System.out.println("=========================================================");
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


