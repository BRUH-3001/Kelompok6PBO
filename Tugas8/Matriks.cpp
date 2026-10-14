/*
Nama Program   : Program Penjumlahan dan Perkalian Matriks (C++)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 13 Oktober 2026
Deskripsi      : Program OOP array 2 dimensi untuk menjumlahkan dan
                 mengalikan 2 buah matriks dengan 2 class: MyMatriks dan Menu.
                 Proses dibuat dalam 2 bentuk: void (passing object) dan
                 fungsi (return object).
*/

#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <climits>
#include <cstdlib>
#include <stdexcept>

using namespace std;

//class 1
class MyMatriks {
private:
    //atribut
    int baris;
    int kolom;
    vector<vector<int> > nilai;

public:
    //constructor
    MyMatriks() {
        baris = 2;
        kolom = 2;
        nilai.assign(baris, vector<int>(kolom, 0));
    }

    MyMatriks(int baris, int kolom) {
        this->baris = baris;
        this->kolom = kolom;
        nilai.assign(baris, vector<int>(kolom, 0));
    }

    //setter & getter
    void setOrdo(int baris, int kolom) {
        this->baris = baris;
        this->kolom = kolom;
        nilai.assign(baris, vector<int>(kolom, 0));
    }

    int getBaris() const { return baris; }
    int getKolom() const { return kolom; }

    //input
    void isiMatriks(const string& nama) {
        cout << "Isi Matriks " << nama << " (" << baris << "x" << kolom << ")" << endl;
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                nilai[i][j] = bacaInt(
                    "  Masukkan nilai " + nama + "[" + to_string(i + 1) + "," +
                    to_string(j + 1) + "] : ", INT_MIN, INT_MAX);
            }
        }
    }

    // helper: baca bilangan bulat dengan validasi
    static int bacaInt(const string& prompt, int min, int max) {
        while (true) {
            cout << prompt;
            string token;
            if (!(cin >> token)) {          // input habis (EOF)
                cout << endl;
                exit(0);
            }
            try {
                size_t pos = 0;
                int nilai = stoi(token, &pos);
                if (pos != token.size()) {
                    throw invalid_argument("bukan bilangan bulat");
                }
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                cout << "  [!] Nilai harus antara " << min << " sampai " << max << "." << endl;
            } catch (const exception&) {    // invalid_argument / out_of_range
                cout << "  [!] Input harus berupa bilangan bulat!" << endl;
            }
        }
    }

    //syarat operasi
    bool bisaDijumlah(const MyMatriks& B) const {
        return this->baris == B.baris && this->kolom == B.kolom;
    }

    bool bisaDikali(const MyMatriks& B) const {
        return this->kolom == B.baris;
    }

    //proses bentuk void (passing object): hasil disimpan di objek pemanggil (this)
    void addMatriks(const MyMatriks& A, const MyMatriks& B) {
        setOrdo(A.baris, A.kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                this->nilai[i][j] = A.nilai[i][j] + B.nilai[i][j];
            }
        }
    }

    void mulMatriks(const MyMatriks& A, const MyMatriks& B) {
        setOrdo(A.baris, B.kolom);
        for (int i = 0; i < A.baris; i++) {
            for (int j = 0; j < B.kolom; j++) {
                int jumlah = 0;
                for (int k = 0; k < A.kolom; k++) {
                    jumlah += A.nilai[i][k] * B.nilai[k][j];
                }
                this->nilai[i][j] = jumlah;
            }
        }
    }

    //proses bentuk fungsi (return object): this = matriks kiri, parameter = matriks kanan
    MyMatriks plusMatriks(const MyMatriks& B) const {
        MyMatriks C(this->baris, this->kolom);
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                C.nilai[i][j] = this->nilai[i][j] + B.nilai[i][j];
            }
        }
        return C;
    }

    MyMatriks kaliMatriks(const MyMatriks& B) const {
        MyMatriks C(this->baris, B.kolom);
        for (int i = 0; i < this->baris; i++) {
            for (int j = 0; j < B.kolom; j++) {
                int jumlah = 0;
                for (int k = 0; k < this->kolom; k++) {
                    jumlah += this->nilai[i][k] * B.nilai[k][j];
                }
                C.nilai[i][j] = jumlah;
            }
        }
        return C;
    }

    //output
    void cetakMatriks(const string& judul) const {
        cout << judul << " (" << baris << "x" << kolom << ")" << endl;
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                cout << setw(6) << this->nilai[i][j];
            }
            cout << endl;
        }
    }
};

//class 2
class Menu {
private:
    MyMatriks A;
    MyMatriks B;
    int pilihan;
    bool sudahIsi;

public:
    //constructor
    Menu() {
        pilihan = -1;
        sudahIsi = false;
    }

    //output
    void tampilkanMenuUtama() {
        cout << "\n=================================================" << endl;
        cout << "            PROGRAM OPERASI MATRIKS" << endl;
        cout << "=================================================" << endl;
        cout << "1. Input Matriks A dan B" << endl;
        cout << "2. Penjumlahan A + B" << endl;
        cout << "3. Perkalian A x B" << endl;
        cout << "0. Keluar" << endl;
    }

    static void tampilkanPesan(const string& pesan) {
        cout << pesan << endl;
    }

    //input
    void inputPilihan() {
        pilihan = MyMatriks::bacaInt("Pilih menu : ", 0, 3);
    }

    void inputMatriks() {
        cout << "\nOrdo Matriks A:" << endl;
        int bA = MyMatriks::bacaInt("  Jumlah baris : ", 1, 10);
        int kA = MyMatriks::bacaInt("  Jumlah kolom : ", 1, 10);
        A.setOrdo(bA, kA);

        cout << "\nOrdo Matriks B:" << endl;
        int bB = MyMatriks::bacaInt("  Jumlah baris : ", 1, 10);
        int kB = MyMatriks::bacaInt("  Jumlah kolom : ", 1, 10);
        B.setOrdo(bB, kB);

        cout << endl;
        A.isiMatriks("A");
        cout << endl;
        B.isiMatriks("B");

        cout << "\n-------------------------------------" << endl;
        A.cetakMatriks("Matriks A");
        B.cetakMatriks("Matriks B");
        sudahIsi = true;
    }

    //proses
    void prosesJumlah() {
        cout << "\n=== PENJUMLAHAN A + B ===" << endl;
        if (A.bisaDijumlah(B)) {
            MyMatriks C;
            C.addMatriks(A, B);                      // bentuk void
            C.cetakMatriks("Hasil (void, C.addMatriks(A, B))");

            MyMatriks D = A.plusMatriks(B);          // bentuk fungsi
            D.cetakMatriks("Hasil (fungsi, A.plusMatriks(B))");
        } else {
            tampilkanPesan("Tidak bisa dijumlahkan: ordo A dan B harus sama.");
        }
    }

    void prosesKali() {
        cout << "\n=== PERKALIAN A x B ===" << endl;
        if (A.bisaDikali(B)) {
            MyMatriks E;
            E.mulMatriks(A, B);                      // bentuk void
            E.cetakMatriks("Hasil (void, E.mulMatriks(A, B))");

            MyMatriks F = A.kaliMatriks(B);          // bentuk fungsi
            F.cetakMatriks("Hasil (fungsi, A.kaliMatriks(B))");
        } else {
            tampilkanPesan("Tidak bisa dikalikan: jumlah kolom A harus sama dengan jumlah baris B.");
        }
    }

    void prosesPilihan() {
        switch (pilihan) {
            case 1:
                inputMatriks();
                break;
            case 2:
                if (sudahIsi) prosesJumlah();
                else tampilkanPesan("Data belum diisi! Pilih menu 1 terlebih dahulu.");
                break;
            case 3:
                if (sudahIsi) prosesKali();
                else tampilkanPesan("Data belum diisi! Pilih menu 1 terlebih dahulu.");
                break;
            case 0:
                tampilkanPesan("\nProgram selesai. Terima kasih!");
                break;
            default:
                tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
        }
    }

    void jalankan() {
        do {
            tampilkanMenuUtama();
            inputPilihan();
            prosesPilihan();
        } while (pilihan != 0);
    }
};

//program utama
int main() {
    Menu menu;
    menu.jalankan();
    return 0;
}
