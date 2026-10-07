/*
Nama Program   : Program Gaji Lembur (C++)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 6/10/2026
Deskripsi      : Program menghitung total gaji dengan Class Menu & Validator
*/

#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <limits>

using namespace std;

// Fungsi pembantu untuk format angka dengan pemisah ribuan (titik)
string formatRupiah(double uang) {
    string str = to_string((long long)uang);
    int n = str.length();
    for (int i = n - 3; i > 0; i -= 3) {
        str.insert(i, ".");
    }
    return str;
}

// Fungsi pembantu untuk format waktu HH:MM:SS
string formatJamMenitDetik(int jam, int menit, int detik) {
    char buffer[10];
    snprintf(buffer, sizeof(buffer), "%02d:%02d:%02d", jam, menit, detik);
    return string(buffer);
}

// ============================================
// CLASS VALIDATOR (Handling Error Input)
// ============================================
class Validator {
public:
    static int bacaAngkaValidasi(const string& prompt, int min, int max) {
        int nilai;
        while (true) {
            cout << prompt;
            if (!(cin >> nilai)) {
                // Menghapus status error pada cin
                cin.clear();
                // Mengabaikan sisa input pada buffer sampai newline
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
                cout << "  [!] Input harus berupa angka, tidak boleh huruf atau simbol!\n";
                continue;
            }

            if (nilai >= min && nilai <= max) {
                return nilai;
            } else {
                cout << "  [!] Nilai tidak valid! Harus antara " << min << " sampai " << max << ".\n";
            }
        }
    }
};

// ============================================
// CLASS WAKTU
// ============================================
class Waktu {
public:
    int jam, menit, detik;

    Waktu(int j = 0, int m = 0, int d = 0) : jam(j), menit(m), detik(d) {}

    void inputWaktu(string jenis) {
        cout << "Masukkan Waktu " << jenis << ":\n";
        // Menggunakan Validator untuk rentang jam, menit, detik
        jam = Validator::bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        menit = Validator::bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        detik = Validator::bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
    }

    Waktu hitungSelisih(Waktu akhir) {
        int awalSec = jam * 3600 + menit * 60 + detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhirSec - awalSec;

        if (diff < 0) diff += 24 * 3600;

        return Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    string formatWaktu() {
        return formatJamMenitDetik(jam, menit, detik);
    }

    string formatLembur() {
        char buffer[10];
        snprintf(buffer, sizeof(buffer), "%d:%02d:%02d", jam, menit, detik);
        return string(buffer);
    }
};

// ============================================
// CLASS PEGAWAI
// ============================================
class Pegawai {
private:
    string nip, nama, statusPeringatan;
    int gol;
    Waktu datang, pulang, lamaKerja, lamaLembur;
    double gajiHarian, uangLembur, totalGaji;

public:
    Pegawai() : nip(""), nama(""), gol(0), gajiHarian(0), uangLembur(0), totalGaji(0) {}

    Pegawai(string n, string nm, int g, Waktu d, Waktu p) 
        : nip(n), nama(nm), gol(g), datang(d), pulang(p), gajiHarian(0), uangLembur(0), totalGaji(0) {}

    void inputPegawai() {
        cout << "Masukkan NIP          : "; cin >> nip;
        cout << "Masukkan Nama         : "; cin >> ws; getline(cin, nama);
        // Menggunakan Validator untuk golongan
        gol = Validator::bacaAngkaValidasi("Masukkan Golongan(1-4): ", 1, 4);
        
        datang.inputWaktu("Datang");
        pulang.inputWaktu("Pulang");
        cout << "----------------------------------\n";
    }

    void proses() {
        lamaKerja = datang.hitungSelisih(pulang);
        
        int detikKerja = lamaKerja.jam * 3600 + lamaKerja.menit * 60 + lamaKerja.detik;
        int detik8Jam = 8 * 3600;

        if (detikKerja >= detik8Jam) {
            statusPeringatan = "peringatan";
            int sisaLembur = detikKerja - detik8Jam;
            lamaLembur = Waktu(sisaLembur / 3600, (sisaLembur % 3600) / 60, sisaLembur % 60);
        } else {
            statusPeringatan = "ok";
            lamaLembur = Waktu(0, 0, 0);
        }

        int jamLemburAktif = lamaLembur.jam;

        switch (gol) {
            case 1: gajiHarian = 150000; uangLembur = jamLemburAktif * 50000; break;
            case 2: gajiHarian = 200000; uangLembur = jamLemburAktif * 75000; break;
            case 3: gajiHarian = 400000; uangLembur = jamLemburAktif * 150000; break;
            case 4: gajiHarian = 500000; uangLembur = jamLemburAktif * 200000; break;
            default: gajiHarian = 0; uangLembur = 0; break;
        }
        totalGaji = gajiHarian + uangLembur;
    }

    void cetakRow(int no) {
        cout << "| " << left << setw(2) << no 
             << " | " << setw(3) << nip 
             << " | " << setw(10) << nama 
             << " | " << setw(3) << gol 
             << " | " << setw(8) << datang.formatWaktu() 
             << " | " << setw(8) << pulang.formatWaktu() 
             << " | " << setw(8) << lamaKerja.formatWaktu() 
             << " | " << setw(10) << lamaLembur.formatLembur() 
             << " | " << right << setw(11) << formatRupiah(gajiHarian) 
             << " | " << setw(11) << formatRupiah(uangLembur) 
             << " | " << setw(11) << formatRupiah(totalGaji) 
             << " | " << left << setw(10) << statusPeringatan << " |\n";
    }
};

// ============================================
// CLASS MENU (Menangani Tampilan & Alur)
// ============================================
class MenuGaji {
private:
    vector<Pegawai> daftarPegawai;

    void cetakLaporan() {
        if(daftarPegawai.empty()) {
            cout << "Data belum diisi!\n";
            return;
        }
        cout << "\nDaftar Gaji Harian PT Informatika\n";
        string garis = "+----+-----+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+\n";
        cout << garis;
        cout << "| No | NIP | Nama       | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |\n";
        cout << garis;
        for (size_t i = 0; i < daftarPegawai.size(); i++) {
            daftarPegawai[i].cetakRow(i + 1);
        }
        cout << garis;
    }

public:
    void jalankan() {
        int pilihan;

        do {
            cout << "\n=================================================\n";
            cout << "         PROGRAM GAJI HARIAN PT INFORMATIKA\n";
            cout << "=================================================\n";
            cout << "1. Input Data Pegawai Dinamis (3 Objek)\n";
            cout << "2. Gunakan Data Hardcode (3 Objek)\n";
            cout << "0. Keluar\n";
            
            // Validasi input menu
            pilihan = Validator::bacaAngkaValidasi("Pilih menu (0-2) : ", 0, 2);

            if (pilihan == 1) {
                daftarPegawai.clear();
                for (int i = 0; i < 3; i++) {
                    cout << "\n--- Input Pegawai ke-" << (i + 1) << " ---\n";
                    Pegawai p;
                    p.inputPegawai();
                    p.proses();
                    daftarPegawai.push_back(p);
                }
                cetakLaporan();
            } 
            else if (pilihan == 2) {
                daftarPegawai.clear();
                Pegawai p1("001", "Ali", 3, Waktu(8, 0, 0), Waktu(17, 15, 10));
                Pegawai p2("002", "Budi", 2, Waktu(7, 30, 0), Waktu(12, 0, 0));
                Pegawai p3("003", "Citra", 4, Waktu(8, 0, 0), Waktu(20, 30, 0));
                
                p1.proses(); p2.proses(); p3.proses();
                daftarPegawai.push_back(p1);
                daftarPegawai.push_back(p2);
                daftarPegawai.push_back(p3);
                
                cetakLaporan();
            }
        } while (pilihan != 0);

        cout << "\nProgram selesai.\n";
    }
};

// ============================================
// CLASS UTAMA (Main)
// ============================================
int main() {
    MenuGaji aplikasi;
    aplikasi.jalankan();
    return 0;
}