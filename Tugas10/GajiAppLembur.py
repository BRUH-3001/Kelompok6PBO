"""
Nama Program   : GajiAppLembur.py
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 25 Oktober 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai (Python)
                 menggunakan Array of Object dan Inheritance
                 (5 Class: Waktu, Person, Pegawai, ArrayPegawai, Menu).
"""

class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def set_jam(self, jam): self.jam = jam
    def set_menit(self, menit): self.menit = menit
    def set_detik(self, detik): self.detik = detik
    def set_waktu(self, jam, menit, detik):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def get_jam(self): 
        return self.jam
    def get_menit(self): 
        return self.menit
    def get_detik(self): 
        return self.detik

    @staticmethod
    def baca_angka_validasi(prompt, min_val, max_val):
        while True:
            try:
                nilai = int(input(prompt))
                if min_val <= nilai <= max_val:
                    return nilai
                else:
                    print(f"  [!] Nilai tidak valid! Harus antara {min_val} sampai {max_val}.")
            except ValueError:
                print("  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!")

    def input_waktu(self, jenis):
        print(f"Masukkan Waktu {jenis}:")
        self.jam = Waktu.baca_angka_validasi("  Jam (0-23)   : ", 0, 23)
        self.menit = Waktu.baca_angka_validasi("  Menit (0-59) : ", 0, 59)
        self.detik = Waktu.baca_angka_validasi("  Detik (0-59) : ", 0, 59)

    def hitung_selisih(self, akhir):
        awal_sec = self.jam * 3600 + self.menit * 60 + self.detik
        akhir_sec = akhir.jam * 3600 + akhir.menit * 60 + akhir.detik
        diff = akhir_sec - awal_sec

        if diff < 0:
            diff += 24 * 3600

        return Waktu(diff // 3600, (diff % 3600) // 60, diff % 60)

    def format_waktu(self):
        return f"{self.jam:02d}:{self.menit:02d}:{self.detik:02d}"

    def format_lembur(self):
        return f"{self.jam}:{self.menit:02d}:{self.detik:02d}"

class Person:
    def __init__(self, nama="", alamat=""):
        self.nama = nama
        self.alamat = alamat

    def get_nama(self):
        return self.nama

    def get_alamat(self):
        return self.alamat

    def set_nama(self, nama):
        self.nama = nama

    def set_alamat(self, alamat):
        self.alamat = alamat

class Pegawai(Person):
    def __init__(self, nip="", nama="", alamat="", gol=0, datang=None, pulang=None):
        super().__init__(nama, alamat)
        self.nip = nip
        self.gol = gol
        self.datang = datang if datang else Waktu()
        self.pulang = pulang if pulang else Waktu()
        self.lama_kerja = Waktu()
        self.lama_lembur = Waktu()
        self.gaji_harian = 0
        self.uang_lembur = 0
        self.total_gaji = 0
        self.status_peringatan = ""

    def input_pegawai(self):
        self.nip = input("Masukkan NIP          : ")
        self.nama = input("Masukkan Nama         : ")
        self.alamat = input("Masukkan Alamat       : ")
        self.gol = Waktu.baca_angka_validasi("Masukkan Golongan(1-4): ", 1, 4)
        self.datang.input_waktu("Datang")
        self.pulang.input_waktu("Pulang")
        print("-" * 34)

    def proses(self):
        self.lama_kerja = self.datang.hitung_selisih(self.pulang)

        detik_kerja = self.lama_kerja.get_jam() * 3600 + self.lama_kerja.get_menit() * 60 + self.lama_kerja.get_detik()
        detik_8_jam = 8 * 3600

        if detik_kerja >= detik_8_jam:
            self.status_peringatan = "ok"
            sisa_lembur = detik_kerja - detik_8_jam
            self.lama_lembur = Waktu(sisa_lembur // 3600, (sisa_lembur % 3600) // 60, sisa_lembur % 60)
        else:
            self.status_peringatan = "peringatan"
            self.lama_lembur = Waktu(0, 0, 0)

        jam_lembur_aktif = self.lama_lembur.get_jam()

        if self.gol == 1:
            self.gaji_harian, self.uang_lembur = 150000, jam_lembur_aktif * 50000
        elif self.gol == 2:
            self.gaji_harian, self.uang_lembur = 200000, jam_lembur_aktif * 75000
        elif self.gol == 3:
            self.gaji_harian, self.uang_lembur = 400000, jam_lembur_aktif * 150000
        elif self.gol == 4:
            self.gaji_harian, self.uang_lembur = 500000, jam_lembur_aktif * 200000
        else:
            self.gaji_harian, self.uang_lembur = 0, 0

        self.total_gaji = self.gaji_harian + self.uang_lembur

    def format_rupiah(self, angka):
        return f"{int(angka):,}".replace(",", ".")

    def cetak_row(self, no):
        print(f"| {no:<2} | {self.nip:<3} | {self.nama:<10} | {self.alamat:<10} | {self.gol:<3} | "
              f"{self.datang.format_waktu():<8} | {self.pulang.format_waktu():<8} | "
              f"{self.lama_kerja.format_waktu():<8} | {self.lama_lembur.format_lembur():<10} | "
              f"{self.format_rupiah(self.gaji_harian):>11} | {self.format_rupiah(self.uang_lembur):>11} | "
              f"{self.format_rupiah(self.total_gaji):>11} | {self.status_peringatan:<10} |")

class ArrayPegawai:
    def __init__(self, kapasitas):
        self.jumlah_data = kapasitas
        # menginstansiasi objek pegawai di setiap elemen array sejak constructor
        self.list_pegawai = [Pegawai() for _ in range(kapasitas)]

    def input_daftar_pegawai(self):
        for i in range(self.jumlah_data):
            print(f"\n--- Input Pegawai ke-{i + 1} ---")
            self.list_pegawai[i].input_pegawai()

    def HardcodeData(self):
        self.list_pegawai[0] = Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 30, 0), Waktu(16, 0, 0))
        self.list_pegawai[1] = Pegawai("004", "Ida", "Uber 3", 2, Waktu(9, 0, 0), Waktu(21, 30, 0))
        self.list_pegawai[2] = Pegawai("005", "Adi", "Riung 10", 1, Waktu(12, 40, 0), Waktu(23, 30, 0))

    def proses_semua(self):
        for p in self.list_pegawai:
            if p is not None:
                p.proses()

    def cetak_laporan(self):
        print("\nDaftar Gaji Harian PT Informatika")
        garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+"
        print(garis)
        print("| No | NIP | Nama       | Alamat     | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |")
        print(garis)

        for i, p in enumerate(self.list_pegawai):
            if p is not None:
                p.cetak_row(i + 1)
        print(garis)

class Menu:
    # constructor
    def __init__(self):
        pass

    @staticmethod
    def tampilkan_pesan(pesan):
        print(pesan)

    def jalankan_menu(self):
        while True:
            print("\n=================================================")
            print("         PROGRAM GAJI HARIAN PT INFORMATIKA")
            print("=================================================")
            print("1. Input Data Pegawai Dinamis (3 Objek)")
            print("2. Gunakan Data Hardcode (3 Objek - Sesuai Contoh)")
            print("0. Keluar")

            pilihan = Waktu.baca_angka_validasi("Pilih menu : ", 0, 2)

            if pilihan == 1:
                arr_dinamis = ArrayPegawai(3)
                arr_dinamis.input_daftar_pegawai()
                arr_dinamis.proses_semua()
                arr_dinamis.cetak_laporan()
            elif pilihan == 2:
                arr_hardcode = ArrayPegawai(3)
                arr_hardcode.HardcodeData()
                arr_hardcode.proses_semua()
                arr_hardcode.cetak_laporan()
            elif pilihan == 0:
                self.tampilkan_pesan("\nProgram selesai. Terima kasih!")
                break
            else:
                self.tampilkan_pesan("Pilihan tidak valid, silakan coba lagi.")


def main():
    menu = Menu()
    menu.jalankan_menu()


if __name__ == "__main__":
    main()