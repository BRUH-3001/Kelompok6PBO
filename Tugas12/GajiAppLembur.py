"""
Nama Program   : GajiAppLembur.py
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 24 November 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai
                 menggunakan Queue berbasis Array dan Inheritance
"""

class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.__jam = jam
        self.__menit = menit
        self.__detik = detik

    def set_jam(self, jam): self.__jam = jam
    def set_menit(self, menit): self.__menit = menit
    def set_detik(self, detik): self.__detik = detik
    def set_waktu(self, jam, menit, detik):
        self.__jam = jam
        self.__menit = menit
        self.__detik = detik

    def get_jam(self):
        return self.__jam
    def get_menit(self):
        return self.__menit
    def get_detik(self):
        return self.__detik

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
        self.__jam = Waktu.baca_angka_validasi("  Jam (0-23)   : ", 0, 23)
        self.__menit = Waktu.baca_angka_validasi("  Menit (0-59) : ", 0, 59)
        self.__detik = Waktu.baca_angka_validasi("  Detik (0-59) : ", 0, 59)

    def hitung_selisih(self, akhir):
        awal_sec = self.__jam * 3600 + self.__menit * 60 + self.__detik
        akhir_sec = akhir.__jam * 3600 + akhir.__menit * 60 + akhir.__detik
        diff = akhir_sec - awal_sec

        if diff < 0:
            diff += 24 * 3600

        return Waktu(diff // 3600, (diff % 3600) // 60, diff % 60)

    def format_waktu(self):
        return f"{self.__jam:02d}:{self.__menit:02d}:{self.__detik:02d}"

    def format_lembur(self):
        return f"{self.__jam}:{self.__menit:02d}:{self.__detik:02d}"

class Person:
    def __init__(self, nama="", alamat=""):
        self.__nama = nama
        self.__alamat = alamat

    def get_nama(self):
        return self.__nama

    def get_alamat(self):
        return self.__alamat

    def set_nama(self, nama):
        self.__nama = nama

    def set_alamat(self, alamat):
        self.__alamat = alamat

class Pegawai(Person):
    def __init__(self, nip="", nama="", alamat="", gol=0, datang=None, pulang=None):
        super().__init__(nama, alamat)
        self.__nip = nip
        self.__gol = gol
        self.__datang = datang if datang else Waktu()
        self.__pulang = pulang if pulang else Waktu()

    def input_pegawai(self):
        self.__nip = input("Masukkan NIP          : ")
        self.set_nama(input("Masukkan Nama         : "))
        self.set_alamat(input("Masukkan Alamat       : "))
        self.__gol = Waktu.baca_angka_validasi("Masukkan Golongan(1-4): ", 1, 4)
        self.__datang.input_waktu("Datang")
        self.__pulang.input_waktu("Pulang")
        print("-" * 34)

    def proses(self):
        self.__lama_kerja = self.__datang.hitung_selisih(self.__pulang)

        detik_kerja = self.__lama_kerja.get_jam() * 3600 + self.__lama_kerja.get_menit() * 60 + self.__lama_kerja.get_detik()
        detik_8_jam = 8 * 3600

        if detik_kerja >= detik_8_jam:
            self.__status_peringatan = "ok"
            sisa_lembur = detik_kerja - detik_8_jam
            self.__lama_lembur = Waktu(sisa_lembur // 3600, (sisa_lembur % 3600) // 60, sisa_lembur % 60)
        else:
            self.__status_peringatan = "peringatan"
            self.__lama_lembur = Waktu(0, 0, 0)

        jam_lembur_aktif = self.__lama_lembur.get_jam()

        if self.__gol == 1:
            self.__gaji_harian, self.__uang_lembur = 150000, jam_lembur_aktif * 50000
        elif self.__gol == 2:
            self.__gaji_harian, self.__uang_lembur = 200000, jam_lembur_aktif * 75000
        elif self.__gol == 3:
            self.__gaji_harian, self.__uang_lembur = 400000, jam_lembur_aktif * 150000
        elif self.__gol == 4:
            self.__gaji_harian, self.__uang_lembur = 500000, jam_lembur_aktif * 200000
        else:
            self.__gaji_harian, self.__uang_lembur = 0, 0

        self.__total_gaji = self.__gaji_harian + self.__uang_lembur

    def format_rupiah(self, angka):
        return f"{int(angka):,}".replace(",", ".")

    def cetak_row(self, no):
        print(f"| {no:<2} | {self.__nip:<3} | {self.get_nama():<10} | {self.get_alamat():<10} | {self.__gol:<3} | "
              f"{self.__datang.format_waktu():<8} | {self.__pulang.format_waktu():<8} | "
              f"{self.__lama_kerja.format_waktu():<8} | {self.__lama_lembur.format_lembur():<10} | "
              f"{self.format_rupiah(self.__gaji_harian):>11} | {self.format_rupiah(self.__uang_lembur):>11} | "
              f"{self.format_rupiah(self.__total_gaji):>11} | {self.__status_peringatan:<10} |")

class Queue:
    def __init__(self, kapasitas):
        self.__data = [None] * kapasitas
        self.__depan = 0
        self.__jumlah = 0

    def is_empty(self):
        return self.__jumlah == 0

    def is_full(self):
        return self.__jumlah == len(self.__data)

    def enqueue(self, item):
        if self.is_full():
            raise OverflowError("Antrian penuh")
        belakang = (self.__depan + self.__jumlah) % len(self.__data)
        self.__data[belakang] = item
        self.__jumlah += 1

    def dequeue(self):
        if self.is_empty():
            raise IndexError("Antrian kosong")
        item = self.__data[self.__depan]
        self.__data[self.__depan] = None
        self.__depan = (self.__depan + 1) % len(self.__data)
        self.__jumlah -= 1
        return item

class QueuePegawai(Queue):
    def input_daftar_pegawai(self):
        urutan = 1
        while not self.is_full():
            print(f"\n--- Input Pegawai ke-{urutan} ---")
            p = Pegawai()
            p.input_pegawai()
            self.enqueue(p)
            urutan += 1

    def HardcodeData(self):
        self.enqueue(Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 30, 0), Waktu(16, 0, 0)))
        self.enqueue(Pegawai("004", "Ida", "Uber 3", 2, Waktu(9, 0, 0), Waktu(21, 30, 0)))
        self.enqueue(Pegawai("005", "Adi", "Riung 10", 1, Waktu(12, 40, 0), Waktu(23, 30, 0)))

    def cetak_laporan(self):
        print("\nDaftar Gaji Harian PT Informatika")
        garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+"
        print(garis)
        print("| No | NIP | Nama       | Alamat     | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |")
        print(garis)

        no = 1
        while not self.is_empty():
            p = self.dequeue()
            p.proses()
            p.cetak_row(no)
            no += 1
        print(garis)

class Menu:
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
            print("1. Input Data Pegawai")
            print("2. Gunakan Data Hardcode Pegawai")
            print("0. Keluar")

            pilihan = Waktu.baca_angka_validasi("Pilih menu : ", 0, 2)

            if pilihan == 1:
                que_dinamis = QueuePegawai(3)
                que_dinamis.input_daftar_pegawai()
                que_dinamis.cetak_laporan()
            elif pilihan == 2:
                que_hardcode = QueuePegawai(3)
                que_hardcode.HardcodeData()
                que_hardcode.cetak_laporan()
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
