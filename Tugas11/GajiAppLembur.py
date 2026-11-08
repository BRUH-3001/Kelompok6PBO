"""
Nama Program   : GajiAppLembur.py
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 8 November 2026
Deskripsi      : Program menghitung total gaji harian dan lembur pegawai (Python)
                 serta biaya parkir kendaraan (Mobil, Truk, Motor) menggunakan
                 Array of Object, Inheritance, dan Polimorfisme
                 (10 Class: Waktu, Person, Pegawai, ArrayPegawai, Kendaraan,
                 Mobil, Truk, Motor, ArrayKendaraan, Menu).
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

class ArrayPegawai:
    def __init__(self, kapasitas):
        self.__jumlah_data = kapasitas
        self.__list_pegawai = [Pegawai() for _ in range(kapasitas)]

    def input_daftar_pegawai(self):
        for i in range(self.__jumlah_data):
            print(f"\n--- Input Pegawai ke-{i + 1} ---")
            self.__list_pegawai[i].input_pegawai()

    def HardcodeData(self):
        self.__list_pegawai[0] = Pegawai("001", "Ali", "Dago 4", 3, Waktu(8, 30, 0), Waktu(16, 0, 0))
        self.__list_pegawai[1] = Pegawai("004", "Ida", "Uber 3", 2, Waktu(9, 0, 0), Waktu(21, 30, 0))
        self.__list_pegawai[2] = Pegawai("005", "Adi", "Riung 10", 1, Waktu(12, 40, 0), Waktu(23, 30, 0))

    def proses_semua(self):
        for p in self.__list_pegawai:
            if p is not None:
                p.proses()

    def cetak_laporan(self):
        print("\nDaftar Gaji Harian PT Informatika")
        garis = "+----+-----+------------+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+"
        print(garis)
        print("| No | NIP | Nama       | Alamat     | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |")
        print(garis)

        for i, p in enumerate(self.__list_pegawai):
            if p is not None:
                p.cetak_row(i + 1)
        print(garis)

class Kendaraan:
    LEBAR_KOLOM = (2, 10, 5, 6, 10, 7, 8, 8, 8, 12, 7, 9)
    JUDUL_KOLOM = ("No", "Plat", "Jenis", "Lokasi", "Pemilik", "Status",
                   "Masuk", "Keluar", "Durasi", "Tarif", "Tagih", "Biaya")

    def __init__(self, plat="", pemilik=None, masuk=None, keluar=None, lokasi="Luar"):
        self.__plat = plat
        self.__pemilik = pemilik
        self.__masuk = masuk if masuk else Waktu()
        self.__keluar = keluar if keluar else Waktu()
        self.__lokasi = lokasi

    def get_plat(self):
        return self.__plat

    def get_pemilik(self):
        return self.__pemilik

    def get_lokasi(self):
        return self.__lokasi

    def get_biaya(self):
        return self.__biaya

    def get_jenis(self):
        return "Kendaraan"

    def boleh_di_dalam(self):
        return True

    def hitung_biaya(self):
        raise NotImplementedError("hitung_biaya() harus di-override oleh class turunan")

    def adalah_pegawai(self):
        return isinstance(self.__pemilik, Pegawai)

    def jam_tagih(self):
        detik = self.__durasi.get_jam() * 3600 + self.__durasi.get_menit() * 60 + self.__durasi.get_detik()
        jam = (detik + 3599) // 3600
        return max(jam, 1)

    @staticmethod
    def format_rupiah(angka):
        return f"{int(angka):,}".replace(",", ".")

    def input_kendaraan(self):
        self.__plat = input("Masukkan Plat Nomor   : ")
        while True:
            pilih = Waktu.baca_angka_validasi("Lokasi (1=Luar, 2=Dalam): ", 1, 2)
            if pilih == 2 and not self.boleh_di_dalam():
                print(f"  [!] {self.get_jenis()} hanya boleh parkir di area Luar gedung!")
            else:
                self.__lokasi = "Dalam" if pilih == 2 else "Luar"
                break
        status = Waktu.baca_angka_validasi("Pemilik (1=Umum, 2=Pegawai): ", 1, 2)
        if status == 2:
            nip = input("Masukkan NIP Pegawai  : ")
            nama = input("Masukkan Nama Pegawai : ")
            self.__pemilik = Pegawai(nip, nama)
        else:
            self.__pemilik = None
        self.__masuk.input_waktu("Masuk")
        self.__keluar.input_waktu("Keluar")
        print("-" * 34)

    def proses(self):
        self.__durasi = self.__masuk.hitung_selisih(self.__keluar)
        self.__biaya, self.__ket_tarif, self.__ket_tagih = self.hitung_biaya()

    def cetak_row(self, no):
        pemilik = self.__pemilik.get_nama() if self.__pemilik else "-"
        status = "Pegawai" if self.adalah_pegawai() else "Umum"
        print(f"| {no:<2} | {self.__plat:<10} | {self.get_jenis():<5} | {self.__lokasi:<6} | "
              f"{pemilik:<10} | {status:<7} | "
              f"{self.__masuk.format_waktu():<8} | {self.__keluar.format_waktu():<8} | "
              f"{self.__durasi.format_waktu():<8} | {self.__ket_tarif:<12} | "
              f"{self.__ket_tagih:<7} | {self.format_rupiah(self.__biaya):>9} |")

class Mobil(Kendaraan):
    TARIF_UMUM = 3000
    TARIF_PEGAWAI = 6000

    def get_jenis(self):
        return "Mobil"

    def hitung_biaya(self):
        if self.adalah_pegawai():
            return self.TARIF_PEGAWAI, "Pegawai/hari", "1 hari"
        jam = self.jam_tagih()
        return jam * self.TARIF_UMUM, "Umum/jam", f"{jam} jam"

class Truk(Kendaraan):
    TARIF_UMUM = 5000

    def get_jenis(self):
        return "Truk"

    def boleh_di_dalam(self):
        return False

    def hitung_biaya(self):
        jam = self.jam_tagih()
        return jam * self.TARIF_UMUM, "Umum/jam", f"{jam} jam"

class Motor(Kendaraan):
    TARIF_UMUM = 2000
    TARIF_PEGAWAI = 3000

    def get_jenis(self):
        return "Motor"

    def hitung_biaya(self):
        if self.adalah_pegawai():
            return self.TARIF_PEGAWAI, "Pegawai/hari", "1 hari"
        jam = self.jam_tagih()
        return jam * self.TARIF_UMUM, "Umum/jam", f"{jam} jam"

class ArrayKendaraan:
    def __init__(self, kapasitas):
        self.__jumlah_data = kapasitas
        self.__list_kendaraan = [None] * kapasitas

    def input_daftar_kendaraan(self):
        for i in range(self.__jumlah_data):
            print(f"\n--- Input Kendaraan ke-{i + 1} ---")
            jenis = Waktu.baca_angka_validasi("Jenis (1=Mobil, 2=Truk, 3=Motor): ", 1, 3)
            if jenis == 1:
                k = Mobil()
            elif jenis == 2:
                k = Truk()
            else:
                k = Motor()
            k.input_kendaraan()
            self.__list_kendaraan[i] = k

    def HardcodeData(self):
        self.__list_kendaraan[0] = Mobil("B 1234 ABC", Pegawai("001", "Ali", "Dago 4"), Waktu(8, 0, 0), Waktu(17, 0, 0), "Dalam")
        self.__list_kendaraan[1] = Motor("D 5678 XY", Pegawai("004", "Ida", "Uber 3"), Waktu(9, 0, 0), Waktu(18, 30, 0), "Luar")
        self.__list_kendaraan[2] = Truk("B 9012 TRK", Pegawai("005", "Adi", "Riung 10"), Waktu(7, 0, 0), Waktu(10, 15, 0), "Luar")
        self.__list_kendaraan[3] = Mobil("D 1111 UM", None, Waktu(10, 0, 0), Waktu(12, 30, 0), "Dalam")
        self.__list_kendaraan[4] = Truk("F 2222 TK", None, Waktu(13, 0, 0), Waktu(13, 40, 0), "Luar")
        self.__list_kendaraan[5] = Motor("B 3333 MT", None, Waktu(20, 0, 0), Waktu(23, 30, 0), "Luar")

    def proses_semua(self):
        for k in self.__list_kendaraan:
            if k is not None:
                k.proses()

    def cetak_laporan(self):
        print("\nDaftar Biaya Parkir Gedung PT Informatika")
        garis = "+" + "+".join("-" * (w + 2) for w in Kendaraan.LEBAR_KOLOM) + "+"
        judul = "| " + " | ".join(f"{j:<{w}}" for j, w in zip(Kendaraan.JUDUL_KOLOM, Kendaraan.LEBAR_KOLOM)) + " |"
        print(garis)
        print(judul)
        print(garis)

        total_kendaraan = 0
        total_biaya = 0
        rincian = {"Mobil": [0, 0], "Truk": [0, 0], "Motor": [0, 0]}
        for i, k in enumerate(self.__list_kendaraan):
            if k is not None:
                k.cetak_row(i + 1)
                total_kendaraan += 1
                total_biaya += k.get_biaya()
                rincian[k.get_jenis()][0] += 1
                rincian[k.get_jenis()][1] += k.get_biaya()
        print(garis)

        print(f"Total Kendaraan  : {total_kendaraan}")
        for jenis, (jumlah, biaya) in rincian.items():
            print(f"  {jenis:<6}         : {jumlah} kendaraan, Rp {Kendaraan.format_rupiah(biaya)}")
        print(f"Total Pendapatan : Rp {Kendaraan.format_rupiah(total_biaya)}")

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
            print("3. Input Data Parkir")
            print("4. Gunakan Data Hardcode Parkir")
            print("0. Keluar")

            pilihan = Waktu.baca_angka_validasi("Pilih menu : ", 0, 4)

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
            elif pilihan == 3:
                jumlah = Waktu.baca_angka_validasi("Jumlah kendaraan (1-10) : ", 1, 10)
                arr_parkir = ArrayKendaraan(jumlah)
                arr_parkir.input_daftar_kendaraan()
                arr_parkir.proses_semua()
                arr_parkir.cetak_laporan()
            elif pilihan == 4:
                arr_parkir_hc = ArrayKendaraan(6)
                arr_parkir_hc.HardcodeData()
                arr_parkir_hc.proses_semua()
                arr_parkir_hc.cetak_laporan()
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
