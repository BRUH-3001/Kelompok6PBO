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

public:
    Gaji() {
        nip = "";
        nama = "";
        golongan = 0;
        gapok = tunjangan = potongan = gajiTotal = 0;
    }

    Gaji(string nip, string nama, int golongan) {
        this->nip = nip;
        this->nama = nama;
        this->golongan = golongan;
        gapok = tunjangan = potongan = gajiTotal = 0;
    }

    void setNip(string nip) { this->nip = nip; }
    void setNama(string nama) { this->nama = nama; }
    void setGolongan(int golongan) { this->golongan = golongan; }

    string getNip() { return nip; }
    string getNama() { return nama; }
    int getGolongan() { return golongan; }
    float getGapok() { return gapok; }
    float getTunjangan() { return tunjangan; }
    float getPotongan() { return potongan; }
    float getGajiTotal() { return gajiTotal; }

    void inputGaji() {
        cout << "Masukkan NIP            : ";
        cin >> nip;
        cout << "Masukkan nama lengkap   : ";
        cin.ignore();
        getline(cin, nama);
        cout << "Masukkan golongan (1-4) : ";
        cin >> golongan;
    }

    void proses() {
        hitungGapok();
        hitungPotongan();
        hitungTunjangan();
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
    }
};

int main() {
    int pilihan;
    int jumlahObjek = 0;

    do {
        cout << "\n=================================================" << endl;
        cout << "         PROGRAM GAJI KARYAWAN (jalankan objek)" << endl;
        cout << "=================================================" << endl;
        cout << "Objek yang sudah dijalankan : " << jumlahObjek << endl;
        cout << "1. Jalankan objek Gaji baru (input data karyawan)" << endl;
        cout << "0. Keluar" << endl;
        cout << "Pilih menu : ";
        cin >> pilihan;

        if (pilihan == 1) {
            jumlahObjek++;
            cout << "\n--- Objek ke-" << jumlahObjek << " ---" << endl;
            Gaji karyawan;
            karyawan.inputGaji();
            karyawan.proses();
            karyawan.cetak();
        } else if (pilihan == 0) {
            if (jumlahObjek < 3) {
                cout << "\nProgram ini wajib menjalankan minimal 3 objek sebelum keluar." << endl;
                cout << "Objek yang baru dijalankan: " << jumlahObjek << ". Silakan lanjutkan." << endl;
                pilihan = -1;
            } else {
                cout << "\nProgram selesai. Total objek yang dijalankan: " << jumlahObjek << endl;
            }
        } else {
            cout << "Pilihan tidak valid, silakan coba lagi." << endl;
        }
    } while (!(pilihan == 0 && jumlahObjek >= 3));

    return 0;
}