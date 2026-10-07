"""
Nama Program   : Program Gaji Lembur (Python)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 06/10/2026
Deskripsi      : Program menghitung total gaji yang diterima oleh karyawan dengan Validator Class (Python)
"""
class Validator:
    @staticmethod
    def baca_angka_validasi(prompt, min_val, max_val):
        while True:
            try:
                teks = input(prompt)
                nilai = int(teks)

                if min_val <= nilai <= max_val:
                    return nilai
                else:
                    print(f"  [!] Nilai tidak valid! Harus antara {min_val} sampai {max_val}.")
            except ValueError:
                print("  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!")

class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def input_waktu(self, jenis):
        print(f"Masukkan Waktu {jenis}:")
        self.jam = Validator.baca_angka_validasi("  Jam (0-23)   : ", 0, 23)
        self.menit = Validator.baca_angka_validasi("  Menit (0-59) : ", 0, 59)
        self.detik = Validator.baca_angka_validasi("  Detik (0-59) : ", 0, 59)

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

class Pegawai:
    def __init__(self, nip="", nama="", gol=0, datang=None, pulang=None):
        self.nip = nip
        self.nama = nama
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
        self.gol = Validator.baca_angka_validasi("Masukkan Golongan(1-4): ", 1, 4)
        self.datang.input_waktu("Datang")
        self.pulang.input_waktu("Pulang")
        print("-" * 34)

    def proses(self):
        self.lama_kerja = self.datang.hitung_selisih(self.pulang)
        
        detik_kerja = self.lama_kerja.jam * 3600 + self.lama_kerja.menit * 60 + self.lama_kerja.detik
        detik_8_jam = 8 * 3600

        if detik_kerja >= detik_8_jam:
            self.status_peringatan = "peringatan"
            sisa_lembur = detik_kerja - detik_8_jam
            self.lama_lembur = Waktu(sisa_lembur // 3600, (sisa_lembur % 3600) // 60, sisa_lembur % 60)
        else:
            self.status_peringatan = "ok"
            self.lama_lembur = Waktu(0, 0, 0)

        jam_lembur_aktif = self.lama_lembur.jam

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
        print(f"| {no:<2} | {self.nip:<3} | {self.nama:<10} | {self.gol:<3} | {self.datang.format_waktu():<8} | "
              f"{self.pulang.format_waktu():<8} | {self.lama_kerja.format_waktu():<8} | {self.lama_lembur.format_lembur():<10} | "
              f"{self.format_rupiah(self.gaji_harian):>11} | {self.format_rupiah(self.uang_lembur):>11} | "
              f"{self.format_rupiah(self.total_gaji):>11} | {self.status_peringatan:<10} |")

class AppMenu:
    @staticmethod
    def cetak_laporan(daftar):
        if not daftar:
            print("Data belum diisi!")
            return
            
        print("\nDaftar Gaji Harian PT Informatika")
        garis = "+----+-----+------------+-----+----------+----------+----------+------------+-------------+-------------+-------------+------------+"
        print(garis)
        print("| No | NIP | Nama       | Gol | Datang   | Pulang   | Lama     | Jam Lembur | Gaji Harian | Lembur      | Total       | Status     |")
        print(garis)
        for i, p in enumerate(daftar):
            p.cetak_row(i + 1)
        print(garis)

    @classmethod
    def menu_input_dinamis(cls):
        daftar_pegawai = []
        for i in range(3):
            print(f"\n--- Input Pegawai ke-{i + 1} ---")
            p = Pegawai()
            p.input_pegawai()
            p.proses()
            daftar_pegawai.append(p)
        cls.cetak_laporan(daftar_pegawai)

    @classmethod
    def menu_data_hardcode(cls):
        p1 = Pegawai("001", "Ali", 3, Waktu(8, 0, 0), Waktu(17, 15, 10))
        p2 = Pegawai("002", "Budi", 2, Waktu(7, 30, 0), Waktu(12, 0, 0))
        p3 = Pegawai("003", "Citra", 4, Waktu(8, 0, 0), Waktu(20, 30, 0))
        
        daftar_pegawai = [p1, p2, p3]
        for p in daftar_pegawai:
            p.proses()
            
        cls.cetak_laporan(daftar_pegawai)

    @classmethod
    def tampilkan_menu_utama(cls):
        while True:
            print("\n=================================================")
            print("        PROGRAM GAJI HARIAN PT INFORMATIKA")
            print("=================================================")
            print("1. Input Data Pegawai Dinamis (3 Objek)")
            print("2. Gunakan Data Hardcode (3 Objek)")
            print("0. Keluar")
            
            pilihan = Validator.baca_angka_validasi("Pilih menu (0-2) : ", 0, 2)

            if pilihan == 1:
                cls.menu_input_dinamis()
            elif pilihan == 2:
                cls.menu_data_hardcode()
            elif pilihan == 0:
                print("\nProgram selesai.")
                break

def main():
    AppMenu.tampilkan_menu_utama()


if __name__ == "__main__":
    main()