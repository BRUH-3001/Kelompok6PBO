/*
Nama Program   : GajiAppLembur.cpp
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 15 Oktober 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai (C++)
                 menggunakan Array biasa of Object (4 Class: Waktu, Pegawai, ArrayPegawai, Menu).
*/

#include <iostream>
#include <string>
#include <iomanip>
#include <limits>
#include <cstdio>

using namespace std;

class Waktu {
private:
    int jam, menit, detik;

public:
    Waktu() {
        this->jam = 0;
        this->menit = 0;
        this->detik = 0;
    }

    Waktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    void setJam(int jam) { 
        this->jam = jam; 
    }
    void setMenit(int menit) { 
        this->menit = menit; 
    }
    void setDetik(int detik) { 
        this->detik = detik; 
    }
    void setWaktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
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
            if (cin >> nilai) {
                if (nilai >= minVal && nilai <= maxVal) {
                    return nilai;
                } else {
                    cout << "  [!] Nilai tidak valid! Harus antara " << minVal << " sampai " << maxVal << "." << endl;
                }
            } else {
                cout << "  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!" << endl;
                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
            }
        }
    }

    void inputWaktu(string jenis) {
        cout << "Masukkan Waktu " << jenis << ":" << endl;
        this->jam = bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        this->menit = bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        this->detik = bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
    }

    Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this->jam * 3600 + this->menit * 60 + this->detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhirSec - awalSec;

        if (diff < 0) {
            diff += 24 * 3600;
        }

        return Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    string formatWaktu() {
        char buffer[10];
        snprintf(buffer, sizeof(buffer), "%02d:%02d:%02d", jam, menit, detik);
        return string(buffer);
    }

    string formatLembur() {
        char buffer[10];
        snprintf(buffer, sizeof(buffer), "%d:%02d:%02d", jam, menit, detik);
        return string(buffer);
    }
};

class Pegawai {
private:
    string nip;
    string nama;
    int gol;
    Waktu datang;
    Waktu pulang;

    Waktu lamaKerja;
    Waktu lamaLembur;
    double gajiHarian;
    double uangLembur;
    double totalGaji;
    string statusPeringatan;

public:
    Pegawai() {
        this->nip = "";
        this->nama = "";
        this->gol = 0;
        this->gajiHarian = 0;
        this->uangLembur = 0;
        this->totalGaji = 0;
        this->statusPeringatan = "";
    }

    Pegawai(string nip, string nama, int gol, Waktu datang, Waktu pulang) {
        this->nip = nip;
        this->nama = nama;
        this->gol = gol;
        this->datang = datang;
        this->pulang = pulang;
        this->gajiHarian = 0;
        this->uangLembur = 0;
        this->totalGaji = 0;
        this->statusPeringatan = "";
    }

    void inputPegawai() {
        cout << "Masukkan NIP          : ";
        cin >> nip;
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << "Masukkan Nama         : ";
        getline(cin, nama);
        gol = Waktu::bacaAngkaValidasi("Masukkan Golongan(1-4): ", 1, 4);
        datang.inputWaktu("Datang");
        pulang.inputWaktu("Pulang");
        cout << "----------------------------------" << endl;
    }

    void proses() {
        lamaKerja = datang.hitungSelisih(pulang);

        int detikKerja = lamaKerja.getJam() * 3600 + lamaKerja.getMenit() * 60 + lamaKerja.getDetik();
        int detik8Jam = 8 * 3600;

        if (detikKerja >= detik8Jam) {
            statusPeringatan = "ok";
            int sisaLembur = detikKerja - detik8Jam;
            lamaLembur = Waktu(sisaLembur / 3600, (sisaLembur % 3600) / 60, sisaLembur % 60);
        } else {
            statusPeringatan = "peringatan";
            lamaLembur = Waktu(0, 0, 0);
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

    static string formatRupiah(double nilai) {
        string str = to_string((long long)nilai);
        int n = str.length();
        for (int i = n - 3; i > 0; i -= 3) {
            str.insert(i, ".");
        }
        return str;
    }

    void cetakRow(int no) {
        printf("| %-2d | %-3s | %-10s | %-3d | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |\n",
               no, nip.c_str(), nama.c_str(), gol,
               datang.formatWaktu().c_str(), pulang.formatWaktu().c_str(),
               lamaKerja.formatWaktu().c_str(),
               lamaLembur.formatLembur().c_str(),
               formatRupiah(gajiHarian).c_str(),
               formatRupiah(uangLembur).c_str(),
               formatRupiah(totalGaji).c_str(),
               statusPeringatan.c_str());
    }
};

class ArrayPegawai {
private:
    Pegawai listPegawai[3]; // Array 
    int jumlahData;

public:
    ArrayPegawai(int kapasitas = 3) {
        this->jumlahData = kapasitas;
    }

    void inputDaftarPegawai() {
        for (int i = 0; i < jumlahData; i++) {
            cout << "\n--- Input Pegawai ke-" << (i + 1) << " ---" << endl;
            listPegawai[i].inputPegawai();
        }
    }

    void HardcodeData() {
        listPegawai[0] = Pegawai("001", "Azrel", 3, Waktu(8, 30, 0), Waktu(16, 0, 0));
        listPegawai[1] = Pegawai("002", "Kemal", 4, Waktu(9, 0, 0), Waktu(21, 30, 0));
        listPegawai[2] = Pegawai("003", "Yunus", 2, Waktu(12, 40, 0), Waktu(23, 30, 0));
    }

    void prosesSemua() {
        for (int i = 0; i < jumlahData; i++) {
            listPegawai[i].proses();
        }
    }

    void cetakLaporan() {
        cout << "\nDaftar Gaji Harian PT Informatika" << endl;
        string garis = "+----+-----+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+";
        cout << garis << endl;
        printf("| %-2s | %-3s | %-10s | %-3s | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |\n",
               "No", "NIP", "Nama", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur", "Gaji Harian", "Lembur", "Total", "Status");
        cout << garis << endl;

        for (int i = 0; i < jumlahData; i++) {
            listPegawai[i].cetakRow(i + 1);
        }
        cout << garis << endl;
    }
};

class Menu {
public:
    static void tampilkanPesan(string pesan) {
        cout << pesan << endl;
    }

    void jalankanMenu() {
        int pilihan;
        do {
            cout << "\n=================================================" << endl;
            cout << "         PROGRAM GAJI HARIAN PT INFORMATIKA" << endl;
            cout << "=================================================" << endl;
            cout << "1. Input Data Pegawai Dinamis (3 Objek)" << endl;
            cout << "2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)" << endl;
            cout << "0. Keluar" << endl;

            pilihan = Waktu::bacaAngkaValidasi("Pilih menu : ", 0, 2);

            switch (pilihan) {
                case 1: {
                    ArrayPegawai arrDinamis(3);
                    arrDinamis.inputDaftarPegawai();
                    arrDinamis.prosesSemua();
                    arrDinamis.cetakLaporan();
                    break;
                }
                case 2: {
                    ArrayPegawai arrHardcode(3);
                    arrHardcode.HardcodeData();
                    arrHardcode.prosesSemua();
                    arrHardcode.cetakLaporan();
                    break;
                }
                case 0:
                    tampilkanPesan("\nProgram selesai. Terima kasih!");
                    break;
                default:
                    tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (pilihan != 0);
    }
};

int main() {
    Menu menu;
    menu.jalankanMenu();
    return 0;
}