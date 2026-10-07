/*
Nama Program : KoordinatKartesiusOOP.cpp
Nama Kelompok: Muhammad Yunus Habiby (140810250014)
               Azrel Sakhi Reswara (140810250098)
               Muhammad Kemal Firdaus (1408102500101)
Tanggal Buat : 29 September 2026
Deskripsi    : Program OOP Koordinat Kartesian dengan Passing Object & Menu (C++)
*/

#include <iostream>
#include <cmath>
using namespace std;

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
        cout << "  Masukkan Absis (X)   : "; cin >> absis;
        cout << "  Masukkan Ordinat (Y) : "; cin >> ordinat;
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

class AplikasiKoordinat {
public:
    // Output Luar Class
    static void printKoordinatLuar(const Koordinat& K) {
        cout << "Nilai Absis = " << K.getAbsis() << ", Nilai Ordinat = " << K.getOrdinat() << endl;
    }

    // Sub Menu Pilihan Perhitungan
    static void subMenuProses(Koordinat& ttkAktif, Koordinat& ttkPembanding, string namaObjek) {
        int cara, proses;
        Koordinat ttkHasil;

        cout << "\n========================================" << endl;
        cout << "   PILIHAN METODE UNTUK " << namaObjek << endl;
        cout << "========================================" << endl;
        cout << "1. Cara 1 (Fungsi Return)" << endl;
        cout << "2. Cara 2 (Prosedur Void)" << endl;
        cout << "Pilih Metode (1-2): "; 
        cin >> cara;

        cout << "\n--- PILIHAN PROSES PERHITUNGAN ---" << endl;
        cout << "1. Titik Tengah (dengan titik pembanding)" << endl;
        cout << "2. Pencerminan Terhadap Sumbu X" << endl;
        cout << "3. Pencerminan Terhadap Sumbu Y" << endl;
        cout << "4. Hitung Jarak (dengan titik pembanding)" << endl;
        cout << "Pilih Proses (1-4): "; 
        cin >> proses;

        cout << "\n>>> HASIL PERHITUNGAN <<<" << endl;
        if (cara == 1) { // CARA 1 (RETURN)
            if (proses == 1) {
                ttkHasil = ttkAktif.titikTengahReturn(ttkPembanding);
                cout << "Titik Tengah = "; 
                ttkHasil.printKoordinat();
            } else if (proses == 2) {
                ttkHasil = ttkAktif.cerminSumbuXReturn();
                cout << "Hasil Pencerminan Sumbu X = "; 
                ttkHasil.printKoordinat();
            } else if (proses == 3) {
                ttkHasil = ttkAktif.cerminSumbuYReturn();
                cout << "Hasil Pencerminan Sumbu Y = "; 
                ttkHasil.printKoordinat();
            } else if (proses == 4) {
                cout << "Jarak ke Titik Pembanding = " << 
                ttkAktif.hitungJarakReturn(ttkPembanding) << endl;
            }
        } else { // CARA 2 (VOID)
            if (proses == 1) {
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
            cout << "\n========================================" << endl;
            cout << "       MENU UTAMA OBJEK KOORDINAT" << endl;
            cout << "========================================" << endl;
            cout << "1. Objek 1 (Default Constructor : (2, 1))" << endl;
            cout << "2. Objek 2 (Set via Setter      : (6, 3))" << endl;
            cout << "3. Objek 3 (Input 2 Titik oleh User)" << endl;
            cout << "4. Keluar Program" << endl;
            cout << "Pilihan Objek (1-4): "; 
            cin >> pilihanObjek;

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
                    cout << "\nProgram selesai!" << endl;
                    break;
                default:
                    cout << "\nPilihan tidak valid!" << endl;
                    break;
            }
        } while (pilihanObjek != 4);
    }
};

// Entry Point
int main() {
    AplikasiKoordinat::jalankan();
    return 0;
}