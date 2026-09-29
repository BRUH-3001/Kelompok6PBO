/*
Nama Program   : Program Selisih Waktu (C++)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 28/9/2026 
Deskripsi      : Program OOP untuk mencari selisih waktu antara dua objek Waktu,
                 dengan dua cara proses (Cara 1: fungsi return, Cara 2: void),
                 dan input/output baik di dalam maupun di luar class.
                 3 objek dibuat dengan 3 cara berbeda.
*/

#include <iostream>
#include <string>
#include <cstdio>
#include <cstdlib>
using namespace std;

class Waktu {
private:
    int jam;
    int menit;
    int detik;

    int keTotalDetik() {
        return jam * 3600 + menit * 60 + detik;
    }

public:
    Waktu() {
        jam = 0;
        menit = 0;
        detik = 0;
    }

    Waktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    void setWaktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    void inputWaktu() {
        string baris;
        cout << "Masukkan jam   : ";
        getline(cin, baris); jam = stoi(baris);
        cout << "Masukkan menit : ";
        getline(cin, baris); menit = stoi(baris);
        cout << "Masukkan detik : ";
        getline(cin, baris); detik = stoi(baris);
    }

    int getJam() { return jam; }
    int getMenit() { return menit; }
    int getDetik() { return detik; }

    void tampilkanWaktu() {
        printf("%02d:%02d:%02d\n", jam, menit, detik);
    }

    // Cara 2 void
    void selisihWaktu(Waktu w1, Waktu w2) {
        int totalDetik1 = w1.keTotalDetik();
        int totalDetik2 = w2.keTotalDetik();
        int selisih = abs(totalDetik1 - totalDetik2);

        jam = selisih / 3600;
        menit = (selisih % 3600) / 60;
        detik = selisih % 60;
    }

    // Cara 1 return
    Waktu selisihWaktu2(Waktu w) {
        int totalDetikThis = keTotalDetik();
        int totalDetikW = w.keTotalDetik();
        int selisih = abs(totalDetikThis - totalDetikW);

        int jamHasil = selisih / 3600;
        int menitHasil = (selisih % 3600) / 60;
        int detikHasil = selisih % 60;

        return Waktu(jamHasil, menitHasil, detikHasil);
    }
};

void tampilkanMenu();
int inputPilihan();
int inputAngka(string label);
void tampilkanKeduaHasil(Waktu w1, Waktu w2);
void jalankanObjek1();
void jalankanObjek2();
void jalankanObjek3();

int main() {
    int pilihan;

    cout << "=======================================" << endl;
    cout << "   PROGRAM SELISIH WAKTU" << endl;
    cout << "=======================================" << endl;

    do {
        tampilkanMenu();
        pilihan = inputPilihan();

        switch (pilihan) {
            case 1: jalankanObjek1(); break;
            case 2: jalankanObjek2(); break;
            case 3: jalankanObjek3(); break;
            case 0: cout << "Terima kasih, program selesai." << endl; break;
            default: cout << ">> Pilihan tidak valid, coba lagi." << endl;
        }
    } while (pilihan != 0);

    return 0;
}

void tampilkanMenu() {
    cout << "\n---------------- MENU ----------------" << endl;
    cout << "1. Objek 1 - via constructor dg konstanta" << endl;
    cout << "2. Objek 2 - via constructor, input di luar class" << endl;
    cout << "3. Objek 3 - input di dalam class (pakai inputWaktu())" << endl;
    cout << "0. Keluar" << endl;
    cout << "Pilih menu: ";
}

int inputPilihan() {
    string baris;
    int p = -1;
    getline(cin, baris);
    try {
        p = stoi(baris);
    } catch (...) {
        p = -1;
    }
    return p;
}

int inputAngka(string label) {
    cout << label;
    string baris;
    getline(cin, baris);
    return stoi(baris);
}

void tampilkanKeduaHasil(Waktu w1, Waktu w2) {
    cout << "Waktu 1 : "; w1.tampilkanWaktu();
    cout << "Waktu 2 : "; w2.tampilkanWaktu();

    Waktu hasilReturn = w1.selisihWaktu2(w2);
    cout << "Selisih (cara return) : "; hasilReturn.tampilkanWaktu();

    Waktu hasilVoid;
    hasilVoid.selisihWaktu(w1, w2);
    cout << "Selisih (cara void)   : "; hasilVoid.tampilkanWaktu();
}

void jalankanObjek1() {
    cout << "\n>> Objek 1: nilai konstan lewat constructor" << endl;
    Waktu w1(8, 0, 0);
    Waktu w2(17, 15, 10);
    tampilkanKeduaHasil(w1, w2);
}

void jalankanObjek2() {
    cout << "\n>> Objek 2: input diambil DI LUAR class, lalu dioper ke constructor" << endl;
    cout << "Waktu 1:" << endl;
    int jam1 = inputAngka("  Jam   : ");
    int menit1 = inputAngka("  Menit : ");
    int detik1 = inputAngka("  Detik : ");
    Waktu w1(jam1, menit1, detik1);

    cout << "Waktu 2:" << endl;
    int jam2 = inputAngka("  Jam   : ");
    int menit2 = inputAngka("  Menit : ");
    int detik2 = inputAngka("  Detik : ");
    Waktu w2(jam2, menit2, detik2);

    tampilkanKeduaHasil(w1, w2);
}

void jalankanObjek3() {
    cout << "\n>> Objek 3: objek dibuat kosong, input diminta DI DALAM class" << endl;
    Waktu w1;
    cout << "Waktu 1:" << endl;
    w1.inputWaktu();

    Waktu w2;
    cout << "Waktu 2:" << endl;
    w2.inputWaktu();

    tampilkanKeduaHasil(w1, w2);
}
