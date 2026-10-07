"""
Nama Program   : Program Selisih Waktu (Python - OOP)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 6 Oktober 2026
Deskripsi      : Program OOP dengan enkapsulasi untuk mencari selisih waktu
                 antara dua objek Waktu, dengan dua cara proses (Cara 1:
                 fungsi return, Cara 2: mirip void -- hasil disimpan ke objek
                 pemanggil), dan input/output baik di dalam maupun di luar
                 class. 3 objek dibuat dengan 3 cara berbeda.
                 Dengan Menu class dan validator.
"""

# class 1
class Validator:
    @staticmethod
    def baca_angka_validasi(prompt, min_val, max_val):
        """Baca input dengan validasi range"""
        while True:
            try:
                print(prompt, end="")
                nilai = int(input())
                if min_val <= nilai <= max_val:
                    return nilai
                else:
                    print(f"  [!] Nilai tidak valid! Harus antara {min_val} sampai {max_val}.")
            except ValueError:
                print("  [!] Input harus berupa angka bulat, tidak boleh huruf atau simbol!")


# class 2
class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.__jam = jam
        self.__menit = menit
        self.__detik = detik

    def set_waktu(self, jam, menit, detik):
        self.__jam = jam
        self.__menit = menit
        self.__detik = detik

    def input_waktu(self):
        """Input waktu dengan validasi di dalam class"""
        self.__jam = Validator.baca_angka_validasi("Masukkan jam   : ", 0, 23)
        self.__menit = Validator.baca_angka_validasi("Masukkan menit : ", 0, 59)
        self.__detik = Validator.baca_angka_validasi("Masukkan detik : ", 0, 59)

    def get_jam(self):
        return self.__jam

    def get_menit(self):
        return self.__menit

    def get_detik(self):
        return self.__detik

    def __ke_total_detik(self):
        return self.__jam * 3600 + self.__menit * 60 + self.__detik

    def tampilkan_waktu(self):
        print(f"{self.__jam:02d}:{self.__menit:02d}:{self.__detik:02d}")

    def selisih_waktu(self, w1, w2):
        """CARA 2: Void - hasil disimpan di object ini"""
        total_detik_1 = w1._Waktu__ke_total_detik()
        total_detik_2 = w2._Waktu__ke_total_detik()
        selisih = abs(total_detik_1 - total_detik_2)

        self.__jam = selisih // 3600
        self.__menit = (selisih % 3600) // 60
        self.__detik = selisih % 60

    def selisih_waktu2(self, w):
        """CARA 1: Return - mengembalikan object Waktu baru"""
        total_detik_self = self._Waktu__ke_total_detik()
        total_detik_w = w._Waktu__ke_total_detik()
        selisih = abs(total_detik_self - total_detik_w)

        jam_hasil = selisih // 3600
        menit_hasil = (selisih % 3600) // 60
        detik_hasil = selisih % 60

        return Waktu(jam_hasil, menit_hasil, detik_hasil)


# class 3
class Menu:
    @staticmethod
    def tampilkan_menu_utama():
        """Tampilkan menu utama"""
        print("\n---------------- MENU ----------------")
        print("1. Objek 1 - via constructor dg konstanta")
        print("2. Objek 2 - via constructor, input di luar class")
        print("3. Objek 3 - input di dalam class (pakai input_waktu())")
        print("0. Keluar")
        return Validator.baca_angka_validasi("Pilih menu: ", 0, 3)

    @staticmethod
    def tampilkan_pesan(pesan):
        """Tampilkan pesan"""
        print(pesan)


# fungsi main logic
def tampilkan_kedua_hasil(w1, w2):
    """Tampilkan hasil dengan kedua cara"""
    print("Waktu 1 : ", end="")
    w1.tampilkan_waktu()
    print("Waktu 2 : ", end="")
    w2.tampilkan_waktu()

    # cara 1 return
    hasil_return = w1.selisih_waktu2(w2)
    print("Selisih (cara return) : ", end="")
    hasil_return.tampilkan_waktu()

    # cara 2 void
    hasil_void = Waktu()
    hasil_void.selisih_waktu(w1, w2)
    print("Selisih (cara void)   : ", end="")
    hasil_void.tampilkan_waktu()


def jalankan_objek1():
    """Jalankan dengan Objek 1: konstanta"""
    print("\n>> Objek 1: nilai konstan lewat constructor")
    w1 = Waktu(8, 0, 0)
    w2 = Waktu(17, 15, 10)
    tampilkan_kedua_hasil(w1, w2)


def jalankan_objek2():
    """Jalankan dengan Objek 2: input di luar class"""
    print("\n>> Objek 2: input diambil DI LUAR class, lalu dioper ke constructor")
    print("Waktu 1:")
    jam1 = Validator.baca_angka_validasi("  Jam   : ", 0, 23)
    menit1 = Validator.baca_angka_validasi("  Menit : ", 0, 59)
    detik1 = Validator.baca_angka_validasi("  Detik : ", 0, 59)
    w1 = Waktu(jam1, menit1, detik1)

    print("Waktu 2:")
    jam2 = Validator.baca_angka_validasi("  Jam   : ", 0, 23)
    menit2 = Validator.baca_angka_validasi("  Menit : ", 0, 59)
    detik2 = Validator.baca_angka_validasi("  Detik : ", 0, 59)
    w2 = Waktu(jam2, menit2, detik2)

    tampilkan_kedua_hasil(w1, w2)


def jalankan_objek3():
    """Jalankan dengan Objek 3: input di dalam class"""
    print("\n>> Objek 3: objek dibuat kosong, input diminta DI DALAM class")
    w1 = Waktu()
    print("Waktu 1:")
    w1.input_waktu()

    w2 = Waktu()
    print("Waktu 2:")
    w2.input_waktu()

    tampilkan_kedua_hasil(w1, w2)


def main():
    """Program utama"""
    print("=======================================")
    print("   PROGRAM SELISIH WAKTU")
    print("=======================================")

    pilihan = None
    while pilihan != 0:
        pilihan = Menu.tampilkan_menu_utama()

        if pilihan == 1:
            jalankan_objek1()
        elif pilihan == 2:
            jalankan_objek2()
        elif pilihan == 3:
            jalankan_objek3()
        elif pilihan == 0:
            Menu.tampilkan_pesan("Terima kasih, program selesai.")
        else:
            Menu.tampilkan_pesan(">> Pilihan tidak valid, coba lagi.")


if __name__ == "__main__":
    main()
