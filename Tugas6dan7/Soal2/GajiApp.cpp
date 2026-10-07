#include <iostream>
#include <iomanip>
#include <string>
#include <cmath>
using namespace std;

class Gaji {
private:
    string nip;
    string nama;
    int golongan;

    int jamMasuk, menitMasuk;
    int jamKeluar, menitKeluar;
    int lamaJam, lamaMenit;

    float gapok;
    float tunjangan;
    float potongan;
    float gajiTotal;

    string formatRupiah(float angka) {
        long long nilai = (long long) llround(angka);
        string s = to_string(nilai);
        string hasil = "";
        int hitung = 0;
        for (int i = (int) s.size() - 1; i >= 0; i--) {
            hasil = s[i] + hasil;
            hitung++;
            if (hitung % 3 == 0 && i != 0) {
                hasil = "." + hasil;
            }
        }
        return hasil;
    }

    void hitungGapok() {
        switch (golongan) {
            case 1: gapok = 1500000; break;
            case 2: gapok = 2000000; break;
            case 3: gapok = 3000000; break;
            case 4: gapok = 5000000; break;
            default: gapok = 0;
        }
    }

    void hitungPotongan() {
        switch (golongan) {
            case 1: potongan = gapok * 0.01f; break;
            case 2: potongan = gapok * 0.02f; break;
            case 3: potongan = gapok * 0.02f; break;
            case 4: potongan = gapok * 0.04f; break;
            default: potongan = 0;
        }
    }

    void hitungTunjangan() {
        switch (golongan) {
            case 1: tunjangan = gapok * 0.10f; break;
            case 2: tunjangan = gapok * 0.12f; break;
            case 3: tunjangan = gapok * 0.12f; break;
            case 4: tunjangan = gapok * 0.15f; break;
            default: tunjangan = 0;
        }
    }

    void hitungLamaKerja() {
        int totalMasuk = jamMasuk * 60 + menitMasuk;
        int totalKeluar = jamKeluar * 60 + menitKeluar;
        int selisih = totalKeluar - totalMasuk;

        if (selisih < 0) {
            selisih += 24 * 60;
        }

        lamaJam = selisih / 60;
        lamaMenit = selisih % 60;
    }

public:
    Gaji() {
        nip = "";
        nama = "";
        golongan = 0;
        jamMasuk = menitMasuk = jamKeluar = menitKeluar = 0;
        gapok = tunjangan = potongan = gajiTotal = 0;
    }

    Gaji(string nip, string nama, int golongan,
         int jamMasuk, int menitMasuk, int jamKeluar, int menitKeluar) {
        this->nip = nip;
        this->nama = nama;
        this->golongan = golongan;
        this->jamMasuk = jamMasuk;
        this->menitMasuk = menitMasuk;
        this->jamKeluar = jamKeluar;
        this->menitKeluar = menitKeluar;
        gapok = tunjangan = potongan = gajiTotal = 0;
    }

    void inputDariKelas() {
        cout << "Masukkan NIP            : ";
        cin >> nip;
        cout << "Masukkan nama lengkap   : ";
        cin.ignore();
        getline(cin, nama);
        cout << "Masukkan golongan (1-4) : ";
        cin >> golongan;
        cout << "Jam masuk (0-23)        : ";
        cin >> jamMasuk;
        cout << "Menit masuk (0-59)      : ";
        cin >> menitMasuk;
        cout << "Jam keluar (0-23)       : ";
        cin >> jamKeluar;
        cout << "Menit keluar (0-59)     : ";
        cin >> menitKeluar;
    }

    void proses() {
        hitungGapok();
        hitungPotongan();
        hitungTunjangan();
        hitungLamaKerja();
        gajiTotal = gapok + tunjangan - potongan;
    }

    void cetak() {
        string garis = "+----------------+----------------------+-----+-------------+-------------+-------------+-------------+";
        cout << garis << endl;
        cout << "| " << left << setw(14) << "NIP" << " | " << setw(20) << "Nama" << " | " << setw(3) << "Gol"
             << " | " << setw(11) << "Gaji Pokok" << " | " << setw(11) << "Tunjangan" << " | "
             << setw(11) << "Potongan" << " | " << setw(11) << "Gaji Total" << " |" << endl;
        cout << garis << endl;
        cout << "| " << left << setw(14) << nip << " | " << setw(20) << nama << " | " << setw(3) << golongan
             << " | " << right << setw(11) << formatRupiah(gapok) << " | " << setw(11) << formatRupiah(tunjangan)
             << " | " << setw(11) << formatRupiah(potongan) << " | " << setw(11) << formatRupiah(gajiTotal) << " |" << endl;
        cout << garis << endl;

        cout << "Waktu Masuk  : " << setw(2) << setfill('0') << jamMasuk << ":"
             << setw(2) << setfill('0') << menitMasuk << endl;
        cout << "Waktu Keluar : " << setw(2) << setfill('0') << jamKeluar << ":"
             << setw(2) << setfill('0') << menitKeluar << endl;
        cout << setfill(' ');
        cout << "Lama Kerja   : " << lamaJam << " jam " << lamaMenit << " menit" << endl;
    }
};

void modeHardcode() {
    Gaji karyawan("E001", "Budi Santoso", 3, 8, 0, 16, 30);
    karyawan.proses();
    karyawan.cetak();
}

void modeConstructor() {
    string nip, nama;
    int golongan, jamMasuk, menitMasuk, jamKeluar, menitKeluar;

    cout << "Masukkan NIP            : ";
    cin >> nip;
    cout << "Masukkan nama lengkap   : ";
    cin.ignore();
    getline(cin, nama);
    cout << "Masukkan golongan (1-4) : ";
    cin >> golongan;
    cout << "Jam masuk (0-23)        : ";
    cin >> jamMasuk;
    cout << "Menit masuk (0-59)      : ";
    cin >> menitMasuk;
    cout << "Jam keluar (0-23)       : ";
    cin >> jamKeluar;
    cout << "Menit keluar (0-59)     : ";
    cin >> menitKeluar;

    Gaji karyawan(nip, nama, golongan, jamMasuk, menitMasuk, jamKeluar, menitKeluar);
    karyawan.proses();
    karyawan.cetak();
}

void modeDalamKelas() {
    Gaji karyawan;
    karyawan.inputDariKelas();
    karyawan.proses();
    karyawan.cetak();
}

int main() {
    int pilihan;

    do {
        cout << "\n=================================================" << endl;
        cout << "         PROGRAM GAJI KARYAWAN" << endl;
        cout << "=================================================" << endl;
        cout << "1. Input Konstan (Hardcode)" << endl;
        cout << "2. Input Lewat Constructor" << endl;
        cout << "3. Input Dari Dalam Kelas" << endl;
        cout << "0. Keluar" << endl;
        cout << "Pilih menu : ";
        cin >> pilihan;

        switch (pilihan) {
            case 1:
                modeHardcode();
                break;
            case 2:
                modeConstructor();
                break;
            case 3:
                modeDalamKelas();
                break;
            case 0:
                cout << "\nProgram selesai." << endl;
                break;
            default:
                cout << "Pilihan tidak valid, silakan coba lagi." << endl;
        }
    } while (pilihan != 0);

    return 0;
}