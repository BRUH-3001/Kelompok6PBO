/*
Nama Program   : GajiAppLembur.cpp
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 25 Oktober 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai (C++)
                 menggunakan Array of Object (5 Class: Waktu, Pegawai, ArrayPegawai, Menu, dan Person) dengan inheritance.
*/

#include <iostream>
#include <string>
#include <iomanip>
#include <limits>
#include <cstdio>

class Waktu{
private:
    int jam, menit, detik;

public:
    // constructor kosong
    Waktu(){
        this->jam = 0;
        this->menit = 0;
        this->detik = 0;
    }

    // constructor berparameter
    Waktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    // setter
    void setJam(int jam) { 
        this->jam = jam; 
    }
    void setMenit(int menit) { 
        this->menit = menit; 
    }
    void setDetik(int detik) { 
        this->detik = detik; 
    }
    void setWaktu(int jam, int menit, int detik) {
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    // getter
    int getJam() { 
        return jam; 
    }
    int getMenit() { 
        return menit; 
    }
    int getDetik() { 
        return detik; 
    }

    static int bacaAngkaValidasi(std::string prompt, int minVal, int maxVal) {
        int nilai;
        while (true) {
            std::cout << prompt;
            if (std::cin >> nilai) {
                if (nilai >= minVal && nilai <= maxVal) {
                    return nilai;
                } else {
                    std::cout << "  [!] Nilai tidak valid! Harus antara " << minVal << " sampai " << maxVal << "." << std::endl;
                }
            } else {
                std::cout << "  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!" << std::endl;
                std::cin.clear();
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        }
    }

    void inputWaktu(std::string jenis) {
        std::cout << "Masukkan Waktu " << jenis << ":" << std::endl;
        this->jam = bacaAngkaValidasi("  Jam (0-23)   : ", 0, 23);
        this->menit = bacaAngkaValidasi("  Menit (0-59) : ", 0, 59);
        this->detik = bacaAngkaValidasi("  Detik (0-59) : ", 0, 59);
    }

    Waktu hitungSelisih(Waktu akhir) {
        int awalSec = this->jam * 3600 + this->menit * 60 + this->detik;
        int akhirSec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik;
        int selisih = akhirSec - awalSec;

        if (selisih < 0) {
            selisih += 24 * 3600;
        }

        return Waktu(selisih / 3600, (selisih % 3600) / 60, selisih % 60);
    }

    std::string formatWaktu() {
        char buffer[10];
        snprintf(buffer, sizeof(buffer), "%02d:%02d:%02d", jam, menit, detik);
        return std::string(buffer);
    }

    std::string formatLembur() {
        char buffer[10];
        snprintf(buffer, sizeof(buffer), "%d:%02d:%02d", jam, menit, detik);
        return std::string(buffer);
    }
};

class Person {
protected:
    std::string nama;
    std::string alamat;

public:
    // contructor kosong
    Person(){
        this->nama = "";
        this->alamat = "";
    }
    
    // contructor berparameter
    Person(std::string nama, std::string alamat){
        this->nama = nama;
        this->alamat = alamat;
    }

    void inputPerson(){
        std::cout << "Masukkan Nama         : ";
        std::getline(std::cin, nama);
        std::cout << "Masukkan Alamat       : ";
        std::getline(std::cin, alamat);
    }

    std::string getNama(){
        return nama;
    }
    std::string getAlamat(){
        return alamat;
    }
};

class Pegawai : protected Person {
private:
    std::string nip;
    int gol;
    Waktu datang;
    Waktu pulang;

    Waktu lamaKerja;
    Waktu lamaLembur;
    double gajiHarian;
    double uangLembur;
    double totalGaji;
    std::string statusPeringatan;

public:
    // contructor kosong
    Pegawai() : Person() {
        this->nip = "";
        this->gol = 0;
        this->gajiHarian = 0;
        this->uangLembur = 0;
        this->totalGaji = 0;
        this->statusPeringatan = "";
    }

    //constructor berparameter
    Pegawai(std::string nip, std::string nama, std::string alamat, int gol, Waktu datang, Waktu pulang) : Person(nama, alamat) {
        this->nip = nip;
        this->gol = gol;
        this->datang = datang;
        this->pulang = pulang;
        this->gajiHarian = 0;
        this->uangLembur = 0;
        this->totalGaji = 0;
        this->statusPeringatan = "";
    }

    void inputPegawai() {
        std::cout << "Masukkan NIP          : ";
        std::cin >> nip;
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        
        inputPerson();

        gol = Waktu::bacaAngkaValidasi("Masukkan Golongan(1-4): ", 1, 4);
        datang.inputWaktu("Datang");
        pulang.inputWaktu("Pulang");
        std::cout << "----------------------------------" << std::endl;
    }

    void proses() {
        lamaKerja = datang.hitungSelisih(pulang);

        int detikKerja = lamaKerja.getJam() * 3600 + lamaKerja.getMenit() * 60 + lamaKerja.getDetik();
        int detik8Jam = 8 * 3600;

        if (detikKerja >= detik8Jam) {
            statusPeringatan = "ok";
            int sisaLembur = detikKerja - detik8Jam;
            lamaLembur = Waktu(sisaLembur / 3600, (sisaLembur % 3600) / 60, sisaLembur % 60);
        } else {
            statusPeringatan = "peringatan";
            lamaLembur = Waktu(0, 0, 0);
        }

        int jamLemburAktif = lamaLembur.getJam();

        switch (gol) {
            case 1:
                gajiHarian = 150000;
                uangLembur = jamLemburAktif * 50000;
                break;
            case 2:
                gajiHarian = 200000;
                uangLembur = jamLemburAktif * 75000;
                break;
            case 3:
                gajiHarian = 400000;
                uangLembur = jamLemburAktif * 150000;
                break;
            case 4:
                gajiHarian = 500000;
                uangLembur = jamLemburAktif * 200000;
                break;
            default:
                gajiHarian = 0;
                uangLembur = 0;
                break;
        }
        totalGaji = gajiHarian + uangLembur;
    }

    static std::string formatRupiah(double nilai) {
        std::string str = std::to_string((long long)nilai);
        int n = str.length();
        for (int i = n - 3; i > 0; i -= 3) {
            str.insert(i, ".");
        }
        return str;
    }

    void cetakRow(int no) {
        printf("| %-2d | %-3s | %-10s | %-10s | %-3d | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |\n",
               no, nip.c_str(), nama.c_str(), alamat.c_str(), gol,
               datang.formatWaktu().c_str(), pulang.formatWaktu().c_str(),
               lamaKerja.formatWaktu().c_str(),
               lamaLembur.formatLembur().c_str(),
               formatRupiah(gajiHarian).c_str(),
               formatRupiah(uangLembur).c_str(),
               formatRupiah(totalGaji).c_str(),
               statusPeringatan.c_str());
    }

    double getTotalGaji(){
        return totalGaji;
    }
};

class ArrayPegawai {
private:
    Pegawai listPegawai[3]; 
    int jumlahData;

public:
    // contructor berparameter
    ArrayPegawai(int kapasitas = 3) {
        this->jumlahData = kapasitas;
    }

    void inputDaftarPegawai() {
        for (int i = 0; i < jumlahData; i++) {
            std::cout << "\n--- Input Pegawai ke-" << (i + 1) << " ---" << std::endl;
            listPegawai[i].inputPegawai();
        }
    }

    void HardcodeData() {
        listPegawai[0] = Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 0, 0), Waktu(17, 15, 10));
        listPegawai[1] = Pegawai("004", "Ida", "Uber 3", 2, Waktu(9, 0, 0), Waktu(21, 30, 0));
        listPegawai[2] = Pegawai("005", "Adi", "Riung 10", 1, Waktu(12, 40, 0), Waktu(23, 30, 0));
    }

    void prosesSemua() {
        for (int i = 0; i < jumlahData; i++) {
            listPegawai[i].proses();
        }
    }

    void cetakLaporan() {
        std::cout << "\nDaftar Gaji Harian PT Informatika" << std::endl;
        std::string garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+";
        std::cout << garis << std::endl;
        printf("| %-2s | %-3s | %-10s | %-10s | %-3s | %-8s | %-8s | %-8s | %-10s | %11s | %11s | %11s | %-10s |\n",
               "No", "NIP", "Nama", "Alamat", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur", "Gaji Harian", "Lembur", "Total", "Status");
        std::cout << garis << std::endl;

        double totalGajiKeseluruhan = 0;
        double gajiMax = listPegawai[0].getTotalGaji();
        double gajiMin = listPegawai[0].getTotalGaji();

        for (int i = 0; i < jumlahData; i++) {
            listPegawai[i].cetakRow(i + 1);

            double g = listPegawai[i].getTotalGaji();
            totalGajiKeseluruhan += g;
            if(g > gajiMax){
                gajiMax = g;
            }
            if (g < gajiMin){
                gajiMin = g;
            }
        }
        std::cout << garis << std::endl;

        double rataRata = totalGajiKeseluruhan / jumlahData;
        std::cout << "Rata-rata Gaji : Rp " << Pegawai::formatRupiah(rataRata) << std::endl;
        std::cout << "Gaji Tertinggi : Rp " << Pegawai::formatRupiah(gajiMax) << std::endl;
        std::cout << "Gaji Terendah  : Rp " << Pegawai::formatRupiah(gajiMin) << std::endl;
    }
};

class Menu {
public:
    static void tampilkanPesan(std::string pesan) {
        std::cout << pesan << std::endl;
    }

    void jalankanMenu() {
        int pilihan;
        do {
            std::cout << "\n=================================================" << std::endl;
            std::cout << "         PROGRAM GAJI HARIAN PT INFORMATIKA" << std::endl;
            std::cout << "=================================================" << std::endl;
            std::cout << "1. Input Data Pegawai Dinamis (3 Objek)" << std::endl;
            std::cout << "2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)" << std::endl;
            std::cout << "0. Keluar" << std::endl;

            pilihan = Waktu::bacaAngkaValidasi("Pilih menu : ", 0, 2);

            switch (pilihan) {
                case 1: {
                    ArrayPegawai arrDinamis(3);
                    arrDinamis.inputDaftarPegawai();
                    arrDinamis.prosesSemua();
                    arrDinamis.cetakLaporan();
                    break;
                }
                case 2: {
                    ArrayPegawai arrHardcode(3);
                    arrHardcode.HardcodeData();
                    arrHardcode.prosesSemua();
                    arrHardcode.cetakLaporan();
                    break;
                }
                case 0:
                    tampilkanPesan("\nProgram selesai. Terima kasih!");
                    break;
                default:
                    tampilkanPesan("Pilihan tidak valid, silakan coba lagi.");
            }
        } while (pilihan != 0);
    }
};

int main() {
    Menu menu;
    menu.jalankanMenu();
    return 0;
}