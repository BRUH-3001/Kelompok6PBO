#include <iostream>
#include <cmath>
using namespace std;

class Koordinat {
private:
    double x;
    double y;

public:
    // constructor
    Koordinat(double x = 0, double y = 0) {
        this->x = x;
        this->y = y;
    }

    // setter 
    void setKoordinat(double x, double y) {
        this->x = x;
        this->y = y;
    }

    // getter 
    double getX() const { return x; }
    double getY() const { return y; }

    void inputKoordinat() {
        cout << "Masukkan nilai x: ";
        cin >> x;
        cout << "Masukkan nilai y: ";
        cin >> y;
    }

    void tampilkan() const {
        cout << "(" << x << ", " << y << ")";
    }

    double hitungJarak(const Koordinat &lain) const {
        double dx = lain.x - x;
        double dy = lain.y - y;
        return sqrt(dx * dx + dy * dy);
    }

    Koordinat hitungTitikTengah(const Koordinat &lain) const {
        double midX = (x + lain.x) / 2.0;
        double midY = (y + lain.y) / 2.0;
        return Koordinat(midX, midY);
    }


    // Method mirror 

    Koordinat cerminSumbuX() const {
        return Koordinat(x, -y);
    }

    Koordinat cerminSumbuY() const {
        return Koordinat(-x, y);
    }

    Koordinat cerminOrigin() const {
        return Koordinat(-x, -y);
    }

    Koordinat cerminGarisYX() const {
        return Koordinat(y, x);
    }
};

void tampilkanMenu() {
    cout << "1. Titik A & B - nilai konstan\n";
    cout << "2. Titik A & B - input dari luar class, lewat constructor\n";
    cout << "3. Titik A & B - input dari dalam class (inputKoordinat())\n";
    cout << "0. Keluar\n";
}

void tampilkanCermin(const Koordinat &titik, const string &label) {
    cout << "Cermin " << label << " sumbu X    = "; titik.cerminSumbuX().tampilkan(); cout << "\n";
    cout << "Cermin " << label << " sumbu Y    = "; titik.cerminSumbuY().tampilkan(); cout << "\n";
    cout << "Cermin " << label << " origin     = "; titik.cerminOrigin().tampilkan(); cout << "\n";
    cout << "Cermin " << label << " garis y=x  = "; titik.cerminGarisYX().tampilkan(); cout << "\n";
}

void prosesHasil(Koordinat a, Koordinat b) {
    cout << "\nTitik A = "; a.tampilkan();
    cout << "\nTitik B = "; b.tampilkan();
    cout << "\n";

    double jarak = a.hitungJarak(b);
    Koordinat tengah = a.hitungTitikTengah(b);

    cout << "Jarak A ke B       : " << jarak << "\n";
    cout << "Titik tengah A-B   : "; tengah.tampilkan();
    cout << "\n\n";

    cout << "-- Pencerminan Titik A --\n";
    tampilkanCermin(a, "A");
    cout << "\n-- Pencerminan Titik B --\n";
    tampilkanCermin(b, "B");
}

void jalankanCara1() {
    cout << "\n>> Titik konstan: A(2, 3), B(8, 11)\n";
    Koordinat a(2, 3);
    Koordinat b(8, 11);
    prosesHasil(a, b);
}

void jalankanCara2() {
    cout << "\n>> Input diambil DI LUAR class, lalu dioper ke constructor\n";
    double x1, y1, x2, y2;
    cout << "Masukkan x1 y1 untuk titik A: ";
    cin >> x1 >> y1;
    cout << "Masukkan x2 y2 untuk titik B: ";
    cin >> x2 >> y2;

    Koordinat a(x1, y1);
    Koordinat b(x2, y2);
    prosesHasil(a, b);
}

void jalankanCara3() {
    cout << "\n>> Objek dibuat kosong, input diminta DI DALAM class\n";
    Koordinat a, b;
    cout << "Titik A:\n";
    a.inputKoordinat();
    cout << "Titik B:\n";
    b.inputKoordinat();
    prosesHasil(a, b);
}

int main() {
    cout << "=======================================\n";
    cout << "           PROGRAM KOORDINAT           \n";
    cout << "=======================================\n";

    int pilihan = -1;
    while (pilihan != 0) {
        tampilkanMenu();
        cout << "Pilih menu: ";
        cin >> pilihan;

        if (pilihan == 1) jalankanCara1();
        else if (pilihan == 2) jalankanCara2();
        else if (pilihan == 3) jalankanCara3();
        else if (pilihan == 0) cout << "Terima kasih, program selesai.\n";
        else cout << ">> Pilihan tidak valid, coba lagi.\n";
    }

    return 0;
}
