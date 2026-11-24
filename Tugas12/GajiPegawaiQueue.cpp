/*
Nama Program   : GajiPegawaiQueue.cpp
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 24 November 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai
                 menggunakan Queue, Inheritance, dan Abstract Class
*/

#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

class Waktu {
private:
    int jam;
    int menit;
    int detik;

public:
    Waktu(int jam = 0, int menit = 0, int detik = 0) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    void set_jam(int jam) { this->jam = jam; }
    void set_menit(int menit) { this->menit = menit; }
    void set_detik(int detik) { this->detik = detik; }
    void set_waktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    int get_jam() { return jam; }
    int get_menit() { return menit; }
    int get_detik() { return detik; }

    static int baca_angka_validasi(string prompt, int min_val, int max_val) {
        int nilai;
        while (true) {
            cout << prompt;
            if (cin >> nilai) {
                if (nilai >= min_val && nilai <= max_val) {
                    cin.ignore(1000, '\n');
                    return nilai;
                } else {
                    cout << "  [!] Nilai tidak valid! Harus antara " << min_val << " sampai " << max_val << "." << endl;
                }
            } else {
                cout << "  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!" << endl;
                cin.clear();
                cin.ignore(1000, '\n');
            }
        }
    }

    void input_waktu(string jenis) {
        cout << "Masukkan Waktu " << jenis << ":" << endl;
        jam = baca_angka_validasi("  Jam (0-23)   : ", 0, 23);
        menit = baca_angka_validasi("  Menit (0-59) : ", 0, 59);
        detik = baca_angka_validasi("  Detik (0-59) : ", 0, 59);
    }

    Waktu hitung_selisih(Waktu akhir) {
        int awal_sec = jam * 3600 + menit * 60 + detik;
        int akhir_sec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int diff = akhir_sec - awal_sec;

        if (diff < 0) {
            diff += 24 * 3600;
        }

        return Waktu(diff / 3600, (diff % 3600) / 60, diff % 60);
    }

    string pad_nol(int angka) {
        return (angka < 10 ? "0" : "") + to_string(angka);
    }

    string format_waktu() {
        return pad_nol(jam) + ":" + pad_nol(menit) + ":" + pad_nol(detik);
    }

    string format_lembur() {
        return to_string(jam) + ":" + pad_nol(menit) + ":" + pad_nol(detik);
    }
};

// Abstract Class (sesuai konsep Abstraksi, tidak bisa diinstansiasi langsung)
class Person {
private:
    string nama;
    string alamat;

public:
    Person(string nama = "", string alamat = "") {
        this->nama = nama;
        this->alamat = alamat;
    }

    string get_nama() { return nama; }
    string get_alamat() { return alamat; }

    void set_nama(string nama) { this->nama = nama; }
    void set_alamat(string alamat) { this->alamat = alamat; }

    // Pure virtual function (method abstrak tanpa body)
    virtual void proses() = 0;
    virtual void cetak_row(int no) = 0;
};

class Pegawai : public Person {
private:
    string nip;
    int gol;
    Waktu datang;
    Waktu pulang;
    Waktu lama_kerja;
    Waktu lama_lembur;
    long gaji_harian;
    long uang_lembur;
    long total_gaji;
    string status_peringatan;

public:
    Pegawai(string nip = "", string nama = "", string alamat = "", int gol = 0,
            Waktu datang = Waktu(), Waktu pulang = Waktu())
        : Person(nama, alamat) {
        this->nip = nip;
        this->gol = gol;
        this->datang = datang;
        this->pulang = pulang;
        this->gaji_harian = 0;
        this->uang_lembur = 0;
        this->total_gaji = 0;
    }

    void input_pegawai() {
        string nama_in, alamat_in;
        cout << "Masukkan NIP          : ";
        getline(cin, nip);
        cout << "Masukkan Nama         : ";
        getline(cin, nama_in);
        set_nama(nama_in);
        cout << "Masukkan Alamat       : ";
        getline(cin, alamat_in);
        set_alamat(alamat_in);

        gol = Waktu::baca_angka_validasi("Masukkan Golongan(1-4): ", 1, 4);
        datang.input_waktu("Datang");
        pulang.input_waktu("Pulang");
        cout << "----------------------------------" << endl;
    }

    void proses() override {
        lama_kerja = datang.hitung_selisih(pulang);

        int detik_kerja = lama_kerja.get_jam() * 3600 + lama_kerja.get_menit() * 60 + lama_kerja.get_detik();
        int detik_8_jam = 8 * 3600;

        if (detik_kerja >= detik_8_jam) {
            status_peringatan = "ok";
            int sisa_lembur = detik_kerja - detik_8_jam;
            lama_lembur = Waktu(sisa_lembur / 3600, (sisa_lembur % 3600) / 60, sisa_lembur % 60);
        } else {
            status_peringatan = "peringatan";
            lama_lembur = Waktu(0, 0, 0);
        }

        int jam_lembur_aktif = lama_lembur.get_jam();

        if (gol == 1) {
            gaji_harian = 150000;
            uang_lembur = jam_lembur_aktif * 50000;
        } else if (gol == 2) {
            gaji_harian = 200000;
            uang_lembur = jam_lembur_aktif * 75000;
        } else if (gol == 3) {
            gaji_harian = 400000;
            uang_lembur = jam_lembur_aktif * 150000;
        } else if (gol == 4) {
            gaji_harian = 500000;
            uang_lembur = jam_lembur_aktif * 200000;
        } else {
            gaji_harian = 0;
            uang_lembur = 0;
        }

        total_gaji = gaji_harian + uang_lembur;
    }

    string format_rupiah(long angka) {
        string str = to_string(angka);
        int n = str.length() - 3;
        while (n > 0) {
            str.insert(n, ".");
            n -= 3;
        }
        return str;
    }

    void cetak_row(int no) override {
        cout << "| " << left << setw(2) << no
             << " | " << left << setw(3) << nip
             << " | " << left << setw(10) << get_nama()
             << " | " << left << setw(10) << get_alamat()
             << " | " << left << setw(3) << gol
             << " | " << left << setw(8) << datang.format_waktu()
             << " | " << left << setw(8) << pulang.format_waktu()
             << " | " << left << setw(8) << lama_kerja.format_waktu()
             << " | " << left << setw(10) << lama_lembur.format_lembur()
             << " | " << right << setw(11) << format_rupiah(gaji_harian)
             << " | " << right << setw(11) << format_rupiah(uang_lembur)
             << " | " << right << setw(11) << format_rupiah(total_gaji)
             << " | " << left << setw(10) << status_peringatan << " |" << endl;
    }
};

class Queue {
private:
    Pegawai* data;
    int kapasitas;
    int depan;
    int jumlah;

public:
    Queue(int kapasitas) {
        this->kapasitas = kapasitas;
        this->data = new Pegawai[kapasitas];
        this->depan = 0;
        this->jumlah = 0;
    }

    ~Queue() {
        delete[] data;
    }

    bool is_empty() {
        return jumlah == 0;
    }

    bool is_full() {
        return jumlah == kapasitas;
    }

    void enqueue(Pegawai item) {
        if (is_full()) {
            cout << "Antrian penuh!" << endl;
            return;
        }
        int belakang = (depan + jumlah) % kapasitas;
        data[belakang] = item;
        jumlah++;
    }

    Pegawai dequeue() {
        if (is_empty()) {
            cout << "Antrian kosong!" << endl;
            return Pegawai();
        }
        Pegawai item = data[depan];
        depan = (depan + 1) % kapasitas;
        jumlah--;
        return item;
    }
};

class QueuePegawai : public Queue {
public:
    QueuePegawai(int kapasitas) : Queue(kapasitas) {}

    void input_daftar_pegawai() {
        int urutan = 1;
        while (!is_full()) {
            cout << "\n--- Input Pegawai ke-" << urutan << " ---" << endl;
            Pegawai p;
            p.input_pegawai();
            enqueue(p);
            urutan++;
        }
    }

    void HardcodeData() {
        enqueue(Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 30, 0), Waktu(16, 0, 0)));
        enqueue(Pegawai("004", "Ida", "Uber 3", 2, Waktu(9, 0, 0), Waktu(21, 30, 0)));
        enqueue(Pegawai("005", "Adi", "Riung 10", 1, Waktu(12, 40, 0), Waktu(23, 30, 0)));
    }

    void cetak_laporan() {
        cout << "\nDaftar Gaji Harian PT Informatika" << endl;
        string garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+";
        cout << garis << endl;
        cout << "| No | NIP | Nama       | Alamat     | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |" << endl;
        cout << garis << endl;

        int no = 1;
        while (!is_empty()) {
            Pegawai p = dequeue();
            p.proses();
            p.cetak_row(no);
            no++;
        }
        cout << garis << endl;
    }
};

class Menu {
public:
    static void tampilkan_pesan(string pesan) {
        cout << pesan << endl;
    }

    void jalankan_menu() {
        while (true) {
            cout << "\n=================================================" << endl;
            cout << "         PROGRAM GAJI HARIAN PT INFORMATIKA" << endl;
            cout << "=================================================" << endl;
            cout << "1. Input Data Pegawai" << endl;
            cout << "2. Gunakan Data Hardcode Pegawai" << endl;
            cout << "0. Keluar" << endl;

            int pilihan = Waktu::baca_angka_validasi("Pilih menu : ", 0, 2);

            if (pilihan == 1) {
                QueuePegawai que_dinamis(3);
                que_dinamis.input_daftar_pegawai();
                que_dinamis.cetak_laporan();
            } else if (pilihan == 2) {
                QueuePegawai que_hardcode(3);
                que_hardcode.HardcodeData();
                que_hardcode.cetak_laporan();
            } else if (pilihan == 0) {
                tampilkan_pesan("\nProgram selesai. Terima kasih!");
                break;
            } else {
                tampilkan_pesan("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }
};

int main() {
    Menu menu;
    menu.jalankan_menu();
    return 0;
}