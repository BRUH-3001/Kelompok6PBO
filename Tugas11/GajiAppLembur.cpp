/*
 * Nama Program   : GajiAppLembur.cpp
 * Nama & NPM     : Muhammad Yunus Habiby (140810250014)
 *                  Azrel Sakhi Reswara (140810250098)
 *                  Muhammad Kemal Firdaus (140810250101)
 * Tanggal Dibuat : 10 November 2026
 * Deskripsi      : Program menghitung total gaji harian dan lembur pegawai
                    serta biaya parkir kendaraan (Mobil, Truk, Motor) menggunakan
                    Array of Object, Inheritance, dan Polimorfisme
                    (10 Class: Waktu, Person, Pegawai, ArrayPegawai, Kendaraan,
                    Mobil, Truk, Motor, ArrayKendaraan, Menu).
 */

#include <iostream>
#include <string>
#include <iomanip>
#include <cmath>
#include <sstream>

using namespace std;

class Waktu {
private:
    int jam, menit, detik;

public:
    Waktu(int j = 0, int m = 0, int d = 0) : jam(j), menit(m), detik(d) {}

    void setJam(int j) { 
        jam = j; 
    }
    void setMenit(int m) { 
        menit = m; 
    }
    void setDetik(int d) { 
        detik = d; 
    }
    void setWaktu(int j, int m, int d) { 
        jam = j; menit = m; detik = d; 
    }

    int getJam() { 
        return jam; 
    }
    int getMenit() { 
        return menit; 
    }
    int getDetik() { 
        return detik; 
    }

    static int bacaAngkaValidasi(string prompt, int minVal, int maxVal) {
        int nilai;
        while (true) {
            cout << prompt;
            if (cin >> nilai && nilai >= minVal && nilai <= maxVal) {
                return nilai;
            }
            cout << "  [!] Nilai tidak valid! Harus antara " << minVal << " sampai " << maxVal << ".\n";
            cin.clear();
            cin.ignore(10000, '\n');
        }
    }

    void inputWaktu(string jenis) {
        cout << "Masukkan Waktu " << jenis << ":\n";
        jam = bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        menit = bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        detik = bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
    }

    Waktu hitungSelisih(Waktu akhir) {
        int awalSec = jam * 3600 + menit * 60 + detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int selisih = akhirSec - awalSec;

        if (selisih < 0) {
            selisih += 24 * 3600;
        }

        return Waktu(selisih / 3600, (selisih % 3600) / 60, selisih % 60);
    }

    string formatWaktu() {
        stringstream ss;
        ss << setfill('0') << setw(2) << jam << ":"
           << setw(2) << menit << ":"
           << setw(2) << detik;
        return ss.str();
    }

    string formatLembur() {
        stringstream ss;
        ss << jam << ":"
           << setfill('0') << setw(2) << menit << ":"
           << setw(2) << detik;
        return ss.str();
    }
};

class Person {
protected:
    string nama;
    string alamat;

public:
    Person(string n = "", string a = "") : nama(n), alamat(a) {}

    string getNama() { 
        return nama;
    }
    string getAlamat() { 
        return alamat; 
    }
    void setNama(string n) { 
        nama = n; 
    }
    void setAlamat(string a) { 
        alamat = a; 
    }
};

class Pegawai : public Person {
private:
    string nip;
    int gol;
    Waktu datang;
    Waktu pulang;

public:
    Pegawai(string nip = "", string nama = "", string alamat = "", int gol = 0, Waktu d = Waktu(), Waktu p = Waktu())
        : Person(nama, alamat), nip(nip), gol(gol), datang(d), pulang(p) {}

    string getNip() { return nip; }
    int getGol() { return gol; }

    void inputPegawai() {
        cout << "Masukkan NIP          : ";
        cin >> ws;
        getline(cin, nip);
        cout << "Masukkan Nama         : ";
        getline(cin, nama);
        cout << "Masukkan Alamat       : ";
        getline(cin, alamat);
        gol = Waktu::bacaAngkaValidasi("Masukkan Golongan(1-4): ", 1, 4);
        datang.inputWaktu("Datang");
        pulang.inputWaktu("Pulang");
        cout << "----------------------------------\n";
    }

    Waktu getLamaKerja() {
        return datang.hitungSelisih(pulang);
    }

    int getDetikKerja() {
        Waktu w = getLamaKerja();
        return w.getJam() * 3600 + w.getMenit() * 60 + w.getDetik();
    }

    Waktu getLamaLembur() {
        int sisa = getDetikKerja() - 8 * 3600;
        if (sisa <= 0) return Waktu(0, 0, 0);
        return Waktu(sisa / 3600, (sisa % 3600) / 60, sisa % 60);
    }

    string getStatus() {
        return (getDetikKerja() >= 8 * 3600) ? "ok" : "peringatan";
    }

    int getGajiHarian() {
        switch (gol) {
            case 1: return 150000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 500000;
            default: return 0;
        }
    }

    int getUangLembur() {
        int jamLembur = getLamaLembur().getJam();
        switch (gol) {
            case 1: return jamLembur * 50000;
            case 2: return jamLembur * 75000;
            case 3: return jamLembur * 150000;
            case 4: return jamLembur * 200000;
            default: return 0;
        }
    }

    double getTotalGaji() {
        return static_cast<double>(getGajiHarian() + getUangLembur());
    }

    static string formatRupiah(double angka) {
        long long n = static_cast<long long>(angka);
        string s = to_string(n);
        int len = s.length() - 3;
        while (len > 0) {
            s.insert(len, ".");
            len -= 3;
        }
        return s;
    }

    void cetakRow(int no) {
        cout << "| " << left << setw(2) << no
             << " | " << setw(3) << nip
             << " | " << setw(10) << nama
             << " | " << setw(10) << alamat
             << " | " << setw(3) << gol
             << " | " << setw(8) << datang.formatWaktu()
             << " | " << setw(8) << pulang.formatWaktu()
             << " | " << setw(8) << getLamaKerja().formatWaktu()
             << " | " << setw(10) << getLamaLembur().formatLembur()
             << " | " << right << setw(11) << formatRupiah(getGajiHarian())
             << " | " << right << setw(10) << formatRupiah(getUangLembur())
             << " | " << right << setw(10) << formatRupiah(getTotalGaji())
             << " | " << left << setw(10) << getStatus() << " |\n";
    }
};

class ArrayPegawai {
private:
    Pegawai dataPegawai[100]; // Array Objek Statis
    int jumlahData;

public:
    ArrayPegawai(int kapasitas = 3) : jumlahData(kapasitas) {}

    void setJumlahData(int n) { jumlahData = n; }

    void inputDaftarPegawai() {
        for (int i = 0; i < jumlahData; i++) {
            cout << "\n--- Input Pegawai ke-" << (i + 1) << " ---\n";
            dataPegawai[i].inputPegawai();
        }
    }

    void HardcodeData() {
        jumlahData = 3;
        dataPegawai[0] = Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 0, 0), Waktu(17, 15, 10));
        dataPegawai[1] = Pegawai("004", "Ida", "Uber 3", 2, Waktu(8, 0, 0), Waktu(15, 30, 0));
        dataPegawai[2] = Pegawai("005", "Adi", "Riung 10", 1, Waktu(8, 0, 0), Waktu(18, 0, 0));
    }

    Pegawai cariByNip(string nip) {
        for (int i = 0; i < jumlahData; i++) {
            if (dataPegawai[i].getNip() == nip) {
                return dataPegawai[i];
            }
        }
        return Pegawai(); // Kembalikan objek kosong jika tidak ketemu
    }

    Pegawai getPegawai(int index) {
        if (index >= 0 && index < jumlahData) return dataPegawai[index];
        return Pegawai();
    }

    void cetakLaporan() {
        cout << "\n               Daftar Gaji Harian PT Informatika\n";
        string garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+------------+------------+------------+";
        cout << garis << "\n";
        cout << "| No | NIP | Nama       | Alamat     | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur     | Total      | Status     |\n";
        cout << garis << "\n";

        double totalGajiKeseluruhan = 0;
        double gajiMax = dataPegawai[0].getTotalGaji();
        double gajiMin = dataPegawai[0].getTotalGaji();

        for (int i = 0; i < jumlahData; i++) {
            dataPegawai[i].cetakRow(i + 1);
            double g = dataPegawai[i].getTotalGaji();
            totalGajiKeseluruhan += g;
            if (g > gajiMax) gajiMax = g;
            if (g < gajiMin) gajiMin = g;
        }
        cout << garis << "\n";

        double rataRata = (jumlahData > 0) ? totalGajiKeseluruhan / jumlahData : 0;
        cout << "Rata-rata Gaji : Rp " << Pegawai::formatRupiah(rataRata) << "\n";
        cout << "Gaji Tertinggi : Rp " << Pegawai::formatRupiah(gajiMax) << "\n";
        cout << "Gaji Terendah  : Rp " << Pegawai::formatRupiah(gajiMin) << "\n";
    }
};

class Kendaraan {
public:
    static const int TAMBAHAN_AREA_DALAM = 1000;

protected:
    string plat;
    Person pemilik;
    bool adaPemilik;
    bool isPegawaiStatus;
    bool areaDalam;
    Waktu masuk;
    Waktu keluar;
    string jenis;
    int tarifUmum;
    int tarifHarianPegawai;
    bool bolehDalam;
    bool berhakTarifHarian;

public:
    Kendaraan(string j = "Kendaraan", int tu = 0, int tp = 0, bool bd = true, bool bth = true)
        : jenis(j), tarifUmum(tu), tarifHarianPegawai(tp), bolehDalam(bd), berhakTarifHarian(bth),
          adaPemilik(false), isPegawaiStatus(false), areaDalam(false) {}

    void setAtributBase(string p, Person pem, bool hasPem, bool isPeg, bool ad, Waktu m, Waktu k) {
        plat = p;
        pemilik = pem;
        adaPemilik = hasPem;
        isPegawaiStatus = isPeg;
        areaDalam = ad && bolehDalam;
        masuk = m;
        keluar = k;
    }

    string getJenis() {
        return jenis; 
    }
    string getPlat() { 
        return plat; 
    }
    bool isAreaDalam() { 
        return areaDalam; 
    }
    Waktu getMasuk() { 
        return masuk; 
    }
    Waktu getKeluar() { 
        return keluar; 
    }
    bool isPegawai() { 
        return isPegawaiStatus; 
    }
    bool getBolehDalam() { 
        return bolehDalam; 
    }

    string getNamaPemilik() {
        return adaPemilik ? pemilik.getNama() : "-";
    }

    string getStatus() {
        if (!adaPemilik) return "Tanpa ID";
        return isPegawaiStatus ? "Pegawai" : "Umum";
    }

    Waktu getDurasi() {
        return masuk.hitungSelisih(keluar);
    }

    int getJamTagihan() {
        Waktu d = getDurasi();
        int detik = d.getJam() * 3600 + d.getMenit() * 60 + d.getDetik();
        int jam = (detik + 3599) / 3600;
        return (jam < 1) ? 1 : jam;
    }

    bool tarifHarian() {
        return isPegawaiStatus && berhakTarifHarian;
    }

    int getTarifPerJam() {
        return tarifUmum + (areaDalam ? TAMBAHAN_AREA_DALAM : 0);
    }

    int hitungBiaya() {
        if (tarifHarian()) {
            return tarifHarianPegawai;
        }
        return getJamTagihan() * getTarifPerJam();
    }

    string getTarifTeks() {
        if (tarifHarian()) {
            return Pegawai::formatRupiah(tarifHarianPegawai) + "/hari";
        }
        return Pegawai::formatRupiah(getTarifPerJam()) + "/jam";
    }
};

class Mobil : public Kendaraan {
public:
    Mobil() : Kendaraan("Mobil", 3000, 6000, true, true) {}
};

class Truk : public Kendaraan {
public:
    Truk() : Kendaraan("Truk", 5000, 0, false, false) {}
};

class Motor : public Kendaraan {
public:
    Motor() : Kendaraan("Motor", 2000, 3000, true, true) {}
};

class ArrayKendaraan {
private:
    Kendaraan dataKendaraan[100]; // Array Objek Statis (Tanpa Pointer)
    int jumlahData;
    static const int LEBAR[12];

public:
    ArrayKendaraan(int kapasitas = 6) : jumlahData(kapasitas) {}

    void setJumlahData(int n) { jumlahData = n; }

    void inputDaftarKendaraan(ArrayPegawai& dataPegawai) {
        for (int i = 0; i < jumlahData; i++) {
            cout << "\n--- Input Kendaraan ke-" + to_string(i + 1) + " ---\n";
            inputSatuKendaraan(i, dataPegawai);
            cout << "----------------------------------\n";
        }
    }

private:
    void inputSatuKendaraan(int index, ArrayPegawai& dataPegawai) {
        int jenis = Waktu::bacaAngkaValidasi("Jenis (1=Mobil, 2=Truk, 3=Motor) : ", 1, 3);
        
        if (jenis == 1) dataKendaraan[index] = Mobil();
        else if (jenis == 2) dataKendaraan[index] = Truk();
        else dataKendaraan[index] = Motor();

        string plat;
        cout << "Masukkan Plat Nomor : ";
        cin >> ws;
        getline(cin, plat);

        bool areaDalam = false;
        if (jenis == 2) {
            cout << "Area : Luar Gedung (truk hanya boleh parkir di luar)\n";
        } else {
            int area = Waktu::bacaAngkaValidasi("Area (1=Luar Gedung, 2=Dalam Gedung) : ", 1, 2);
            areaDalam = (area == 2);
        }

        Person pemilik;
        bool adaPemilik = false;
        bool isPegawai = false;
        bool selesai = false;

        while (!selesai) {
            int status = Waktu::bacaAngkaValidasi(
                    "Status (1=Pegawai, 2=Umum dengan identitas, 3=Umum tanpa identitas) : ", 1, 3);
            if (status == 1) {
                string nip;
                cout << "Masukkan NIP Pegawai : ";
                cin >> nip;
                Pegawai p = dataPegawai.cariByNip(nip);
                if (p.getNip() == "") {
                    cout << "  [!] NIP tidak ditemukan di data pegawai, pilih status lagi.\n";
                } else {
                    pemilik = p;
                    adaPemilik = true;
                    isPegawai = true;
                    cout << "  Pegawai terverifikasi : " << p.getNama() << "\n";
                    selesai = true;
                }
            } else if (status == 2) {
                string nama, alamat;
                cout << "Masukkan Nama   : ";
                cin >> ws;
                getline(cin, nama);
                cout << "Masukkan Alamat : ";
                getline(cin, alamat);
                pemilik = Person(nama, alamat);
                adaPemilik = true;
                isPegawai = false;
                selesai = true;
            } else {
                adaPemilik = false;
                isPegawai = false;
                selesai = true;
            }
        }

        Waktu masuk, keluar;
        masuk.inputWaktu("Masuk");
        keluar.inputWaktu("Keluar");

        dataKendaraan[index].setAtributBase(plat, pemilik, adaPemilik, isPegawai, areaDalam, masuk, keluar);
    }

public:
    void HardcodeData(ArrayPegawai& dp) {
        jumlahData = 6;
        
        Mobil m1;
        m1.setAtributBase("D 1234 AB", dp.getPegawai(0), true, true, false, Waktu(8, 0, 0), Waktu(17, 15, 10));
        dataKendaraan[0] = m1;

        Motor m2;
        m2.setAtributBase("D 5678 CD", dp.getPegawai(1), true, true, true, Waktu(8, 0, 0), Waktu(15, 30, 0));
        dataKendaraan[1] = m2;

        Truk t1;
        t1.setAtributBase("D 9012 EF", dp.getPegawai(2), true, true, false, Waktu(7, 0, 0), Waktu(10, 20, 0));
        dataKendaraan[2] = t1;

        Mobil m3;
        m3.setAtributBase("B 4321 XY", Person("Budi", "Dago 7"), true, false, true, Waktu(9, 0, 0), Waktu(11, 30, 0));
        dataKendaraan[3] = m3;

        Motor m4;
        m4.setAtributBase("D 7788 GH", Person(), false, false, false, Waktu(10, 0, 0), Waktu(10, 30, 0));
        dataKendaraan[4] = m4;

        Truk t2;
        t2.setAtributBase("F 1122 ZZ", Person(), false, false, false, Waktu(6, 0, 0), Waktu(12, 0, 0));
        dataKendaraan[5] = t2;
    }

private:
    string garis() {
        stringstream ss;
        ss << "+";
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < LEBAR[i] + 2; j++) ss << "-";
            ss << "+";
        }
        return ss.str();
    }

public:
    void cetakLaporan() {
        cout << "\n                Laporan Parkir Gedung PT Informatika\n";
        cout << garis() << "\n";
        cout << "| No | Plat       | Jenis | Pemilik    | Status  | Area   | Masuk    | Keluar   | Durasi   | Jam   | Tarif        | Biaya      |\n";
        cout << garis() << "\n";

        int total = 0;
        int biayaMax = dataKendaraan[0].hitungBiaya();
        int biayaMin = dataKendaraan[0].hitungBiaya();
        int jmlMobil = 0, jmlTruk = 0, jmlMotor = 0, jmlPegawai = 0;

        for (int i = 0; i < jumlahData; i++) {
            int biaya = dataKendaraan[i].hitungBiaya();
            cout << "| " << left << setw(LEBAR[0]) << (i + 1)
                 << " | " << setw(LEBAR[1]) << dataKendaraan[i].getPlat()
                 << " | " << setw(LEBAR[2]) << dataKendaraan[i].getJenis()
                 << " | " << setw(LEBAR[3]) << dataKendaraan[i].getNamaPemilik()
                 << " | " << setw(LEBAR[4]) << dataKendaraan[i].getStatus()
                 << " | " << setw(LEBAR[5]) << (dataKendaraan[i].isAreaDalam() ? "Dalam" : "Luar")
                 << " | " << setw(LEBAR[6]) << dataKendaraan[i].getMasuk().formatWaktu()
                 << " | " << setw(LEBAR[7]) << dataKendaraan[i].getKeluar().formatWaktu()
                 << " | " << setw(LEBAR[8]) << dataKendaraan[i].getDurasi().formatWaktu()
                 << " | " << setw(LEBAR[9]) << dataKendaraan[i].getJamTagihan()
                 << " | " << setw(LEBAR[10]) << dataKendaraan[i].getTarifTeks()
                 << " | " << right << setw(LEBAR[11]) << Pegawai::formatRupiah(biaya) << " |\n";

            total += biaya;
            if (biaya > biayaMax) biayaMax = biaya;
            if (biaya < biayaMin) biayaMin = biaya;

            if (dataKendaraan[i].getJenis() == "Mobil") jmlMobil++;
            else if (dataKendaraan[i].getJenis() == "Truk") jmlTruk++;
            else if (dataKendaraan[i].getJenis() == "Motor") jmlMotor++;
            if (dataKendaraan[i].isPegawai()) jmlPegawai++;
        }
        cout << garis() << "\n";

        cout << "Jumlah Kendaraan   : " << jumlahData
             << " (Mobil: " << jmlMobil << ", Truk: " << jmlTruk << ", Motor: " << jmlMotor << ")\n";
        cout << "Kendaraan Pegawai  : " << jmlPegawai << " | Umum: " << (jumlahData - jmlPegawai) << "\n";
        cout << "Total Pendapatan   : Rp " << Pegawai::formatRupiah(total) << "\n";
        cout << "Rata-rata Biaya    : Rp " << Pegawai::formatRupiah(static_cast<double>(total) / jumlahData) << "\n";
        cout << "Biaya Tertinggi    : Rp " << Pegawai::formatRupiah(biayaMax) << "\n";
        cout << "Biaya Terendah     : Rp " << Pegawai::formatRupiah(biayaMin) << "\n";
    }
};

const int ArrayKendaraan::LEBAR[12] = {2, 10, 5, 10, 8, 6, 8, 8, 8, 5, 10, 10};

class Menu {
private:
    ArrayPegawai dataPegawai;

public:
    Menu() {
        dataPegawai.HardcodeData();
    }

    static void tampilkanPesan(string pesan) {
        cout << pesan << "\n";
    }

    void jalankanMenu() {
        int pilihan;
        do {
            cout << "\n=================================================\n";
            cout << "              SISTEM PT INFORMATIKA\n";
            cout << "=================================================\n";
            cout << "1. Gaji Harian & Lembur Pegawai\n";
            cout << "2. Parkir Kendaraan\n";
            cout << "0. Keluar\n";

            pilihan = Waktu::bacaAngkaValidasi("Pilih menu : ", 0, 2);

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

private:
    void menuGaji() {
        int pilihan;
        do {
            cout << "\n=================================================\n";
            cout << "         PROGRAM GAJI HARIAN PT INFORMATIKA\n";
            cout << "=================================================\n";
            cout << "1. Input Data Pegawai Dinamis (3 Objek)\n";
            cout << "2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)\n";
            cout << "0. Kembali ke Menu Utama\n";

            pilihan = Waktu::bacaAngkaValidasi("Pilih menu : ", 0, 2);

            switch (pilihan) {
                case 1:
                    dataPegawai = ArrayPegawai(3);
                    dataPegawai.inputDaftarPegawai();
                    dataPegawai.cetakLaporan();
                    break;
                case 2:
                    dataPegawai = ArrayPegawai(3);
                    dataPegawai.HardcodeData();
                    dataPegawai.cetakLaporan();
                    break;
                case 0:
                    tampilkanPesan("Kembali ke Menu Utama...");
                    break;
                default:
                    break;
            }
        } while (pilihan != 0);
    }

    void menuParkir() {
        int pilihan;
        do {
            cout << "\n=================================================\n";
            cout << "         PROGRAM PARKIR GEDUNG PT INFORMATIKA\n";
            cout << "=================================================\n";
            cout << "1. Input Data Kendaraan Dinamis\n";
            cout << "2. Gunakan Data Hardcode (6 Objek - Contoh)\n";
            cout << "0. Kembali ke Menu Utama\n";

            pilihan = Waktu::bacaAngkaValidasi("Pilih menu : ", 0, 2);

            switch (pilihan) {
                case 1: {
                    int jumlah = Waktu::bacaAngkaValidasi("Jumlah kendaraan (1-10) : ", 1, 10);
                    ArrayKendaraan arrDinamis(jumlah);
                    arrDinamis.inputDaftarKendaraan(dataPegawai);
                    arrDinamis.cetakLaporan();
                    break;
                }
                case 2: {
                    ArrayKendaraan arrHardcode(6);
                    arrHardcode.HardcodeData(dataPegawai);
                    arrHardcode.cetakLaporan();
                    break;
                }
                case 0:
                    tampilkanPesan("Kembali ke Menu Utama...");
                    break;
                default:
                    break;
            }
        } while (pilihan != 0);
    }
};

int main() {
    Menu menu;
    menu.jalankanMenu();
    return 0;
}