/*
Nama Program : KoordinatKartesiusOOP.cpp
Nama Kelompok: Muhammad Yunus Habiby (140810250014)
               Azrel Sakhi Reswara (140810250098)
               Muhammad Kemal Firdaus (140810250101)
Tanggal Buat : 5 Oktober 2026
Deskripsi    : Program OOP Koordinat Kartesian dengan Passing Object & Menu (C++)
               Dengan Menu class dan validator
*/

#include <iostream>
#include <cmath>
#include <limits>
using namespace std;

//class 1
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

    static float bacaFloatValidasi(const string& prompt) {
        float nilai;
        while (true) {
            cout << prompt;
            if (cin >> nilai) {
                cin.ignore(numeric_limits<streamsize>::max(), '\n'); // buang sisa input
                return nilai;
            }
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "  [!] Input harus berupa angka, tidak boleh huruf atau simbol!" << endl;
        }
    }
};

//class 2
class Menu {
public:
    static int tampilkanMenuUtama() {
        cout << "\n========================================" << endl;
        cout << "       MENU UTAMA OBJEK KOORDINAT" << endl;
        cout << "========================================" << endl;
        cout << "1. Objek 1 (Default Constructor : (2, 1))" << endl;
        cout << "2. Objek 2 (Set via Setter      : (6, 3))" << endl;
        cout << "3. Objek 3 (Input 2 Titik oleh User)" << endl;
        cout << "4. Keluar Program" << endl;

        return Validator::bacaAngkaValidasi("Pilihan Objek (1-4): ", 1, 4);
    }

    static int tampilkanMenuMetode() {
        cout << "\n========================================" << endl;
        cout << "   PILIHAN METODE UNTUK PERHITUNGAN" << endl;
        cout << "========================================" << endl;
        cout << "1. Cara 1 (Fungsi Return)" << endl;
        cout << "2. Cara 2 (Prosedur Void)" << endl;

        return Validator::bacaAngkaValidasi("Pilih Metode (1-2): ", 1, 2);
    }

    static int tampilkanMenuProses() {
        cout << "\n--- PILIHAN PROSES PERHITUNGAN ---" << endl;
        cout << "1. Titik Tengah (dengan titik pembanding)" << endl;
        cout << "2. Pencerminan Terhadap Sumbu X" << endl;
        cout << "3. Pencerminan Terhadap Sumbu Y" << endl;
        cout << "4. Hitung Jarak (dengan titik pembanding)" << endl;

        return Validator::bacaAngkaValidasi("Pilih Proses (1-4): ", 1, 4);
    }

    static void tampilkanPesan(const string& pesan) {
        cout << pesan << endl;
    }
};

//class 3
class Koordinat {
private:
    float absis;   // Sumbu X
    float ordinat; // Sumbu Y

public:
    // --- Constructor ---
    Koordinat() {
        this->absis = 0;
        this->ordinat = 0;
    }

    Koordinat(float absis, float ordinat) {
        this->absis = absis;
        this->ordinat = ordinat;
    }

    // --- Setter & Getter ---
    void setAbsis(float absis) {
        this->absis = absis;
    }
    void setOrdinat(float ordinat) {
        this->ordinat = ordinat;
    }
    void setKoordinat(float absis, float ordinat) {
        this->absis = absis;
        this->ordinat = ordinat;
    }

    float getAbsis() const {
        return absis;
    }
    float getOrdinat() const {
        return ordinat;
    }

    // --- Input & Output (Dalam Class) ---
    void inputKoordinat() {
    absis   = Validator::bacaFloatValidasi("  Masukkan Absis (X)   : ");
    ordinat = Validator::bacaFloatValidasi("  Masukkan Ordinat (Y) : ");
    }

    void printKoordinat() const {
        cout << "(" << absis << ", " << ordinat << ")" << endl;
    }

    // --- CARA 1: Method Fungsi Return ---
    Koordinat titikTengahReturn(const Koordinat& P) const {
        return Koordinat((P.absis + this->absis) / 2.0f, (P.ordinat + this->ordinat) / 2.0f);
    }

    Koordinat cerminSumbuXReturn() const {
        return Koordinat(this->absis, -this->ordinat);
    }

    Koordinat cerminSumbuYReturn() const {
        return Koordinat(-this->absis, this->ordinat);
    }

    float hitungJarakReturn(const Koordinat& P) const {
        return sqrt(pow(P.absis - this->absis, 2) + pow(P.ordinat - this->ordinat, 2));
    }

    // --- CARA 2: Method Prosedur Void ---
    void titikTengahVoid(const Koordinat& P1, const Koordinat& P2) {
        this->absis = (P1.absis + P2.absis) / 2.0f;
        this->ordinat = (P1.ordinat + P2.ordinat) / 2.0f;
    }

    void cerminSumbuXVoid() {
        this->ordinat = -this->ordinat;
    }
    void cerminSumbuYVoid() {
        this->absis = -this->absis;
    }

    void hitungJarakVoid(const Koordinat& P1, const Koordinat& P2, float& hasilJarak) {
        hasilJarak = sqrt(pow(P2.absis - P1.absis, 2) + pow(P2.ordinat - P1.ordinat, 2));
    }
};

// ============================================
// CLASS: AplikasiKoordinat
// ============================================
class AplikasiKoordinat {
public:
    // Output Luar Class
    static void printKoordinatLuar(const Koordinat& K) {
        cout << "Nilai Absis = " << K.getAbsis() << ", Nilai Ordinat = " << K.getOrdinat() << endl;
    }

    // Sub Menu Pilihan Perhitungan
    static void subMenuProses(Koordinat& ttkAktif, Koordinat& ttkPembanding, string namaObjek) {
        int cara = Menu::tampilkanMenuMetode();
        int proses = Menu::tampilkanMenuProses();

        cout << "\n>>> HASIL PERHITUNGAN <<<" << endl;
        if (cara == 1) { // CARA 1 (RETURN)
            if (proses == 1) {
                Koordinat ttkHasil = ttkAktif.titikTengahReturn(ttkPembanding);
                cout << "Titik Tengah = ";
                ttkHasil.printKoordinat();
            } else if (proses == 2) {
                Koordinat ttkHasil = ttkAktif.cerminSumbuXReturn();
                cout << "Hasil Pencerminan Sumbu X = ";
                ttkHasil.printKoordinat();
            } else if (proses == 3) {
                Koordinat ttkHasil = ttkAktif.cerminSumbuYReturn();
                cout << "Hasil Pencerminan Sumbu Y = ";
                ttkHasil.printKoordinat();
            } else if (proses == 4) {
                cout << "Jarak ke Titik Pembanding = " <<
                ttkAktif.hitungJarakReturn(ttkPembanding) << endl;
            }
        } else {
            if (proses == 1) {
                Koordinat ttkHasil;
                ttkHasil.titikTengahVoid(ttkAktif, ttkPembanding);
                cout << "Titik Tengah = ";
                ttkHasil.printKoordinat();
            } else if (proses == 2) {
                Koordinat temp = ttkAktif;
                temp.cerminSumbuXVoid();
                cout << "Hasil Pencerminan Sumbu X = ";
                temp.printKoordinat();
            } else if (proses == 3) {
                Koordinat temp = ttkAktif;
                temp.cerminSumbuYVoid();
                cout << "Hasil Pencerminan Sumbu Y = ";
                temp.printKoordinat();
            } else if (proses == 4) {
                float jarak;
                Koordinat ttkHasil;
                ttkHasil.hitungJarakVoid(ttkAktif, ttkPembanding, jarak);
                cout << "Jarak ke Titik Pembanding = " << jarak << endl;
            }
        }
    }

    // Method Utama
    static void jalankan() {
        // Objek 1 & 2 Didefinisikan Awal
        Koordinat ttk1(2, 1); // Objek 1: Constructor Parameter
        Koordinat ttk2;       // Objek 2: Setter
        ttk2.setKoordinat(6, 3);

        int pilihanObjek;
        do {
            pilihanObjek = Menu::tampilkanMenuUtama();

            switch (pilihanObjek) {
                case 1:
                    cout << "\n[OBJEK 1 DIPILIH]" << endl;
                    cout << "Detail Koordinat Utama (Output Luar): ";
                    printKoordinatLuar(ttk1);
                    cout << "Titik Pembanding (ttk2): ";
                    ttk2.printKoordinat();
                    subMenuProses(ttk1, ttk2, "OBJEK 1");
                    break;
                case 2:
                    cout << "\n[OBJEK 2 DIPILIH]" << endl;
                    cout << "Detail Koordinat Utama (Output Dalam): ";
                    ttk2.printKoordinat();
                    cout << "Titik Pembanding (ttk1): ";
                    ttk1.printKoordinat();
                    subMenuProses(ttk2, ttk1, "OBJEK 2");
                    break;
                case 3: {
                    Koordinat ttkUser1, ttkUser2;
                    cout << "\n[OBJEK 3 DIPILIH - INPUT 2 TITIK KOORDINAT]\n" << endl;
                    cout << "--- Input Titik Pertama (Titik Utama) ---" << endl;
                    ttkUser1.inputKoordinat();
                    cout << "--- Input Titik Kedua (Titik Pembanding) ---" << endl;
                    ttkUser2.inputKoordinat();

                    cout << "\nDetail Titik 1: ";
                    ttkUser1.printKoordinat();
                    cout << "Detail Titik 2: ";
                    ttkUser2.printKoordinat();

                    subMenuProses(ttkUser1, ttkUser2, "OBJEK 3");
                    break;
                }
                case 4:
                    Menu::tampilkanPesan("\nProgram selesai! Terima kasih.");
                    break;
            }
        } while (pilihanObjek != 4);
    }
};

int main() {
    AplikasiKoordinat::jalankan();
    return 0;
}
