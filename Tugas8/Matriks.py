"""
Nama Program   : Program Penjumlahan dan Perkalian Matriks (python)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 13 Oktober 2026
Deskripsi      : Program OOP array 2 dimensi untuk menjumlahkan dan
                 mengalikan 2 buah matriks dengan 2 class: MyMatriks dan Menu.
                 Proses dibuat dalam 2 bentuk: void (passing object) dan
                 fungsi (return object).
"""

INT_MIN = -2**31
INT_MAX = 2**31 - 1


# class 1
class MyMatriks:
    # constructor (atribut: baris, kolom, nilai)
    def __init__(self, baris=2, kolom=2):
        self.__baris = baris
        self.__kolom = kolom
        self.__nilai = [[0] * kolom for _ in range(baris)]

    # setter & getter
    def set_ordo(self, baris, kolom):
        self.__baris = baris
        self.__kolom = kolom
        self.__nilai = [[0] * kolom for _ in range(baris)]

    def get_baris(self):
        return self.__baris

    def get_kolom(self):
        return self.__kolom

    # input
    def isi_matriks(self, nama):
        print(f"Isi Matriks {nama} ({self.__baris}x{self.__kolom})")
        for i in range(self.__baris):
            for j in range(self.__kolom):
                self.__nilai[i][j] = MyMatriks.baca_int(
                    f"  Masukkan nilai {nama}[{i + 1},{j + 1}] : ",
                    INT_MIN, INT_MAX)

    # helper: baca bilangan bulat dengan validasi
    @staticmethod
    def baca_int(prompt, minimum, maksimum):
        while True:
            try:
                nilai = int(input(prompt).strip())
                if minimum <= nilai <= maksimum:
                    return nilai
                print(f"  [!] Nilai harus antara {minimum} sampai {maksimum}.")
            except ValueError:
                print("  [!] Input harus berupa bilangan bulat!")

    # syarat operasi
    def bisa_dijumlah(self, B):
        return self.__baris == B.__baris and self.__kolom == B.__kolom

    def bisa_dikali(self, B):
        return self.__kolom == B.__baris

    # proses bentuk void (passing object): hasil disimpan di objek pemanggil (self)
    def add_matriks(self, A, B):
        self.set_ordo(A.__baris, A.__kolom)
        for i in range(self.__baris):
            for j in range(self.__kolom):
                self.__nilai[i][j] = A.__nilai[i][j] + B.__nilai[i][j]

    def mul_matriks(self, A, B):
        self.set_ordo(A.__baris, B.__kolom)
        for i in range(A.__baris):
            for j in range(B.__kolom):
                jumlah = 0
                for k in range(A.__kolom):
                    jumlah += A.__nilai[i][k] * B.__nilai[k][j]
                self.__nilai[i][j] = jumlah

    # proses bentuk fungsi (return object): self = matriks kiri, parameter = matriks kanan
    def plus_matriks(self, B):
        C = MyMatriks(self.__baris, self.__kolom)
        for i in range(self.__baris):
            for j in range(self.__kolom):
                C.__nilai[i][j] = self.__nilai[i][j] + B.__nilai[i][j]
        return C

    def kali_matriks(self, B):
        C = MyMatriks(self.__baris, B.__kolom)
        for i in range(self.__baris):
            for j in range(B.__kolom):
                jumlah = 0
                for k in range(self.__kolom):
                    jumlah += self.__nilai[i][k] * B.__nilai[k][j]
                C.__nilai[i][j] = jumlah
        return C

    # output
    def cetak_matriks(self, judul):
        print(f"{judul} ({self.__baris}x{self.__kolom})")
        for i in range(self.__baris):
            for j in range(self.__kolom):
                print(f"{self.__nilai[i][j]:6d}", end="")
            print()


# class 2
class Menu:
    # constructor
    def __init__(self):
        self.__A = MyMatriks()
        self.__B = MyMatriks()
        self.__pilihan = -1
        self.__sudah_isi = False

    # output
    def tampilkan_menu_utama(self):
        print("\n=================================================")
        print("            PROGRAM OPERASI MATRIKS")
        print("=================================================")
        print("1. Input Matriks A dan B")
        print("2. Penjumlahan A + B")
        print("3. Perkalian A x B")
        print("0. Keluar")

    @staticmethod
    def tampilkan_pesan(pesan):
        print(pesan)

    # input
    def input_pilihan(self):
        self.__pilihan = MyMatriks.baca_int("Pilih menu : ", 0, 3)

    def input_matriks(self):
        print("\nOrdo Matriks A:")
        bA = MyMatriks.baca_int("  Jumlah baris : ", 1, 10)
        kA = MyMatriks.baca_int("  Jumlah kolom : ", 1, 10)
        self.__A.set_ordo(bA, kA)

        print("\nOrdo Matriks B:")
        bB = MyMatriks.baca_int("  Jumlah baris : ", 1, 10)
        kB = MyMatriks.baca_int("  Jumlah kolom : ", 1, 10)
        self.__B.set_ordo(bB, kB)

        print()
        self.__A.isi_matriks("A")
        print()
        self.__B.isi_matriks("B")

        print("\n-------------------------------------")
        self.__A.cetak_matriks("Matriks A")
        self.__B.cetak_matriks("Matriks B")
        self.__sudah_isi = True

    # proses
    def proses_jumlah(self):
        print("\n=== PENJUMLAHAN A + B ===")
        if self.__A.bisa_dijumlah(self.__B):
            C = MyMatriks()
            C.add_matriks(self.__A, self.__B)                 # bentuk void
            C.cetak_matriks("Hasil (void, C.add_matriks(A, B))")

            D = self.__A.plus_matriks(self.__B)               # bentuk fungsi
            D.cetak_matriks("Hasil (fungsi, A.plus_matriks(B))")
        else:
            Menu.tampilkan_pesan("Tidak bisa dijumlahkan: ordo A dan B harus sama.")

    def proses_kali(self):
        print("\n=== PERKALIAN A x B ===")
        if self.__A.bisa_dikali(self.__B):
            E = MyMatriks()
            E.mul_matriks(self.__A, self.__B)                 # bentuk void
            E.cetak_matriks("Hasil (void, E.mul_matriks(A, B))")

            F = self.__A.kali_matriks(self.__B)               # bentuk fungsi
            F.cetak_matriks("Hasil (fungsi, A.kali_matriks(B))")
        else:
            Menu.tampilkan_pesan(
                "Tidak bisa dikalikan: jumlah kolom A harus sama dengan jumlah baris B.")

    def proses_pilihan(self):
        if self.__pilihan == 1:
            self.input_matriks()
        elif self.__pilihan == 2:
            if self.__sudah_isi:
                self.proses_jumlah()
            else:
                Menu.tampilkan_pesan("Data belum diisi! Pilih menu 1 terlebih dahulu.")
        elif self.__pilihan == 3:
            if self.__sudah_isi:
                self.proses_kali()
            else:
                Menu.tampilkan_pesan("Data belum diisi! Pilih menu 1 terlebih dahulu.")
        elif self.__pilihan == 0:
            Menu.tampilkan_pesan("\nProgram selesai. Terima kasih!")
        else:
            Menu.tampilkan_pesan("Pilihan tidak valid, silakan coba lagi.")

    def jalankan(self):
        while True:
            self.tampilkan_menu_utama()
            self.input_pilihan()
            self.proses_pilihan()
            if self.__pilihan == 0:
                break


# program utama
if __name__ == "__main__":
    menu = Menu()
    menu.jalankan()
