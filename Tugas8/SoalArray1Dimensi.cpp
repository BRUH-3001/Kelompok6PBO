/*
Nama Program   : Program Array 1 Dimensi (C++)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 7 Oktober 2026
Deskripsi      : Program untuk mencari nilai rata-rata, nilai tertinggi, nilai terendah dan sorting dari
kumpulan data bertipe array of integer.
*/

#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>

using namespace std;

// Kelas OOP untuk memproses array
class Array1D {
private:
    vector<int> data;

public:
    void inputData() {
        int size, val;
        cout << "Masukkan jumlah elemen array: ";
        cin >> size;
        data.clear();
        for (int i = 0; i < size; i++) {
            cout << "Masukkan elemen ke-" << (i + 1) << ": ";
            cin >> val;
            data.push_back(val);
        }
    }

    double getAverage() {
        if (data.empty()) return 0;
        double sum = accumulate(data.begin(), data.end(), 0.0);
        return sum / data.size();
    }

    int getMax() {
        if (data.empty()) return 0;
        return *max_element(data.begin(), data.end());
    }

    int getMin() {
        if (data.empty()) return 0;
        return *min_element(data.begin(), data.end());
    }

    void sortArray() {
        if (!data.empty()) {
            sort(data.begin(), data.end());
            cout << "Array berhasil diurutkan.\n";
        }
    }

    void displayArray() {
        if (!data.empty()) {
            cout << "Data saat ini: ";
            for (int num : data) cout << num << " ";
            cout << "\n";
        }
    }

    bool isReady() {
        return !data.empty();
    }
};

// Fungsi main yang bertindak sebagai menu
int main() {
    Array1D arr;
    int pilihan;

    do {
        cout << "\n=== MENU OPERASI ARRAY 1D ===\n";
        cout << "1. Input Data\n";
        cout << "2. Cari Nilai Rata-rata\n";
        cout << "3. Cari Nilai Tertinggi\n";
        cout << "4. Cari Nilai Terendah\n";
        cout << "5. Sorting Array\n";
        cout << "6. Keluar\n";
        cout << "Pilih menu: ";
        cin >> pilihan;

        switch (pilihan) {
            case 1:
                arr.inputData();
                arr.displayArray();
                break;
            case 2:
                if (arr.isReady()) cout << "Rata-rata: " << arr.getAverage() << "\n";
                else cout << "Data kosong! Silakan input data dulu.\n";
                break;
            case 3:
                if (arr.isReady()) cout << "Nilai Tertinggi: " << arr.getMax() << "\n";
                else cout << "Data kosong!\n";
                break;
            case 4:
                if (arr.isReady()) cout << "Nilai Terendah: " << arr.getMin() << "\n";
                else cout << "Data kosong!\n";
                break;
            case 5:
                if (arr.isReady()) {
                    arr.sortArray();
                    arr.displayArray();
                } else cout << "Data kosong!\n";
                break;
            case 6:
                cout << "Keluar dari program...\n";
                break;
            default:
                cout << "Pilihan tidak valid!\n";
        }
    } while (pilihan != 6);

    return 0;
}