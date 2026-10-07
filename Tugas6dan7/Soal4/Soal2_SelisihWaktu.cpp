/*
Nama Program   : Program Selisih Waktu (C++)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 06 Oktober 2026
Deskripsi      : Program OOP untuk mencari selisih waktu dengan Validator Class (C++)
*/

#include <iostream>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <limits>
using namespace std;

class Validator {
public:
    static int bacaAngkaValidasi(const string& prompt, int min, int max) {
        int nilai;
        while (true) {
            cout << prompt;
            if (!(cin >> nilai)) {
                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
                cout << "  [!] Input harus berupa angka, tidak boleh huruf atau simbol!" << endl;
                continue;
            }

            if (nilai >= min && nilai <= max) {
                return nilai;
            } else {
                cout << "  [!] Nilai tidak valid! Harus antara " << min << " sampai " << max << "." << endl;
            }
        }
    }
};

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
        this->jam = Validator::bacaAngkaValidasi("  Masukkan jam (0-23)   : ", 0, 23);
        this->menit = Validator::bacaAngkaValidasi("  Masukkan menit (0-59) : ", 0, 59);
        this->detik = Validator::bacaAngkaValidasi("  Masukkan detik (0-59) : ", 0, 59);
    }

    int getJam() { return jam; }
    int getMenit() { return menit; }
    int getDetik() { return detik; }

    void tampilkanWaktu() {
        printf("%02d:%02d:%02d\n", jam, menit, detik);
    }

    void selisihWaktu(Waktu w1, Waktu w2) {
        int totalDetik1 = w1.keTotalDetik();
        int totalDetik2 = w2.keTotalDetik();
        int selisih = abs(totalDetik1 - totalDetik2);

        jam = selisih / 3600;
        menit = (selisih % 3600) / 60;
        detik = selisih % 60;
    }

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

class Menu {
public:
    static void tampilkanMenu() {
        cout << "\n---------------- MENU ----------------" << endl;
        cout << "1. Objek 1 - via constructor dg konstanta" << endl;
        cout << "2. Objek 2 - via constructor, input di luar class" << endl;
        cout << "3. Objek 3 - input di dalam class (pakai inputWaktu())" << endl;
        cout << "0. Keluar" << endl;
    }

    static void tampilkanKeduaHasil(Waktu w1, Waktu w2) {
        cout << "\nWaktu 1 : ";
        w1.tampilkanWaktu();
        cout << "Waktu 2 : ";
        w2.tampilkanWaktu();

        Waktu hasilReturn = w1.selisihWaktu2(w2);
        cout << "Selisih (cara return) : ";
        hasilReturn.tampilkanWaktu();

        Waktu hasilVoid;
        hasilVoid.selisihWaktu(w1, w2);
        cout << "Selisih (cara void)   : ";
        hasilVoid.tampilkanWaktu();
    }

    static void jalankanObjek1() {
        cout << "\n>> Objek 1: nilai konstan lewat constructor" << endl;
        Waktu w1(8, 0, 0);
        Waktu w2(17, 15, 10);
        tampilkanKeduaHasil(w1, w2);
    }

    static void jalankanObjek2() {
        cout << "\n>> Objek 2: input diambil DI LUAR class, lalu dioper ke constructor" << endl;
        cout << "Waktu 1:" << endl;
        int jam1 = Validator::bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        int menit1 = Validator::bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        int detik1 = Validator::bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
        Waktu w1(jam1, menit1, detik1);

        cout << "Waktu 2:" << endl;
        int jam2 = Validator::bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        int menit2 = Validator::bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        int detik2 = Validator::bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
        Waktu w2(jam2, menit2, detik2);

        tampilkanKeduaHasil(w1, w2);
    }

    static void jalankanObjek3() {
        cout << "\n>> Objek 3: objek dibuat kosong, input diminta DI DALAM class" << endl;
        Waktu w1;
        cout << "Waktu 1:" << endl;
        w1.inputWaktu();

        Waktu w2;
        cout << "Waktu 2:" << endl;
        w2.inputWaktu();

        tampilkanKeduaHasil(w1, w2);
    }

    static void tampilkanMenuUtama() {
        int pilihan;

        cout << "=======================================" << endl;
        cout << "    PROGRAM SELISIH WAKTU (C++)" << endl;
        cout << "=======================================" << endl;

        do {
            tampilkanMenu();
            pilihan = Validator::bacaAngkaValidasi("Pilih menu (0-3): ", 0, 3);

            switch (pilihan) {
            case 1:
                jalankanObjek1();
                break;
            case 2:
                jalankanObjek2();
                break;
            case 3:
                jalankanObjek3();
                break;
            case 0:
                cout << "\nTerima kasih, program selesai." << endl;
                break;
            }
        } while (pilihan != 0);
    }
};

int main() {
    Menu::tampilkanMenuUtama();
    return 0;
}