import math


class Koordinat:
    def __init__(self, x=0, y=0):
        self.__x = x
        self.__y = y

    # setter 
    def set_koordinat(self, x, y):
        self.__x = x
        self.__y = y

    # getter 
    def get_x(self):
        return self.__x

    def get_y(self):
        return self.__y

    def input_koordinat(self):
        self.__x = float(input("Masukkan nilai x: "))
        self.__y = float(input("Masukkan nilai y: "))

    def tampilkan(self):
        print(f"({self.__x}, {self.__y})", end="")

    def hitung_jarak(self, lain):
        dx = lain.__x - self.__x
        dy = lain.__y - self.__y
        return math.sqrt(dx ** 2 + dy ** 2)

    def hitung_titik_tengah(self, lain):
        mid_x = (self.__x + lain.__x) / 2
        mid_y = (self.__y + lain.__y) / 2
        return Koordinat(mid_x, mid_y)

    # Method mirror 

    def cermin_sumbu_x(self):
        return Koordinat(self.__x, -self.__y)

    def cermin_sumbu_y(self):
        return Koordinat(-self.__x, self.__y)

    def cermin_origin(self):
        return Koordinat(-self.__x, -self.__y)

    def cermin_garis_yx(self):
        return Koordinat(self.__y, self.__x)


def tampilkan_menu():
    print("1. Titik A & B - nilai konstan")
    print("2. Titik A & B - input dari luar class, lewat constructor")
    print("3. Titik A & B - input dari dalam class (input_koordinat())")
    print("0. Keluar")


def tampilkan_cermin(titik, label):
    print(f"Cermin {label} sumbu X    = ", end=""); titik.cermin_sumbu_x().tampilkan(); print()
    print(f"Cermin {label} sumbu Y    = ", end=""); titik.cermin_sumbu_y().tampilkan(); print()
    print(f"Cermin {label} origin     = ", end=""); titik.cermin_origin().tampilkan(); print()
    print(f"Cermin {label} garis y=x  = ", end=""); titik.cermin_garis_yx().tampilkan(); print()


def proses_hasil(a, b):
    print("\nTitik A = ", end=""); a.tampilkan()
    print("\nTitik B = ", end=""); b.tampilkan()
    print()

    jarak = a.hitung_jarak(b)
    tengah = a.hitung_titik_tengah(b)

    print(f"Jarak A ke B       : {jarak}")
    print("Titik tengah A-B   : ", end=""); tengah.tampilkan()
    print("\n")

    print("-- Pencerminan Titik A --")
    tampilkan_cermin(a, "A")
    print("\n-- Pencerminan Titik B --")
    tampilkan_cermin(b, "B")


def jalankan_cara1():
    print("\n>> Titik konstan: A(2, 3), B(8, 11)")
    a = Koordinat(2, 3)
    b = Koordinat(8, 11)
    proses_hasil(a, b)


def jalankan_cara2():
    print("\n>> Input diambil DI LUAR class, lalu dioper ke constructor")
    x1, y1 = map(float, input("Masukkan x1 y1 untuk titik A (pisah spasi): ").split())
    x2, y2 = map(float, input("Masukkan x2 y2 untuk titik B (pisah spasi): ").split())

    a = Koordinat(x1, y1)
    b = Koordinat(x2, y2)
    proses_hasil(a, b)


def jalankan_cara3():
    print("\n>> Objek dibuat kosong, input diminta DI DALAM class")
    a = Koordinat()
    b = Koordinat()
    print("Titik A:")
    a.input_koordinat()
    print("Titik B:")
    b.input_koordinat()
    proses_hasil(a, b)


def main():
    print("=======================================")
    print("   PROGRAM KOORDINAT (JARAK & TITIK TENGAH)")
    print("=======================================")

    pilihan = None
    while pilihan != 0:
        tampilkan_menu()
        try:
            pilihan = int(input("Pilih menu: "))
        except ValueError:
            pilihan = -1

        if pilihan == 1:
            jalankan_cara1()
        elif pilihan == 2:
            jalankan_cara2()
        elif pilihan == 3:
            jalankan_cara3()
        elif pilihan == 0:
            print("Terima kasih, program selesai.")
        else:
            print(">> Pilihan tidak valid, coba lagi.")


main()
