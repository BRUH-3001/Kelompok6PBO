class Gaji:
    def __init__(self, nip="", nama="", golongan=0):
        self.__nip = nip
        self.__nama = nama
        self.__golongan = golongan

        self.__gapok = 0.0
        self.__tunjangan = 0.0
        self.__potongan = 0.0
        self.__gaji_total = 0.0

    # setter
    def set_nip(self, nip):
        self.__nip = nip

    def set_nama(self, nama):
        self.__nama = nama

    def set_golongan(self, golongan):
        self.__golongan = golongan

    # getter
    def get_nip(self):
        return self.__nip

    def get_nama(self):
        return self.__nama

    def get_golongan(self):
        return self.__golongan

    def get_gapok(self):
        return self.__gapok

    def get_tunjangan(self):
        return self.__tunjangan

    def get_potongan(self):
        return self.__potongan

    def get_gaji_total(self):
        return self.__gaji_total

    def input_gaji(self):
        self.__nip = input("Masukkan NIP            : ")
        self.__nama = input("Masukkan nama lengkap   : ")
        self.__golongan = int(input("Masukkan golongan (1-4) : "))

    def __hitung_gapok(self):
        tabel_gapok = {1: 1500000, 2: 2000000, 3: 3000000, 4: 5000000}
        self.__gapok = tabel_gapok.get(self.__golongan, 0)

    def __hitung_potongan(self):
        tabel_persen = {1: 0.01, 2: 0.02, 3: 0.02, 4: 0.04}
        persen = tabel_persen.get(self.__golongan, 0)
        self.__potongan = self.__gapok * persen

    def __hitung_tunjangan(self):
        tabel_persen = {1: 0.10, 2: 0.12, 3: 0.12, 4: 0.15}
        persen = tabel_persen.get(self.__golongan, 0)
        self.__tunjangan = self.__gapok * persen

    def proses(self):
        self.__hitung_gapok()
        self.__hitung_potongan()
        self.__hitung_tunjangan()
        self.__gaji_total = self.__gapok + self.__tunjangan - self.__potongan

    @staticmethod
    def __format_rupiah(nilai):
        return f"{nilai:,.0f}".replace(",", ".")

    def cetak(self):
        garis = ("+----------------+----------------------+-----+-------------"
                  "+-------------+-------------+-------------+")
        print(garis)
        print(f"| {'NIP':<14} | {'Nama':<20} | {'Gol':<3} | {'Gaji Pokok':<11} "
              f"| {'Tunjangan':<11} | {'Potongan':<11} | {'Gaji Total':<11} |")
        print(garis)
        print(f"| {self.__nip:<14} | {self.__nama:<20} | {self.__golongan:<3} "
              f"| {self.__format_rupiah(self.__gapok):>11} "
              f"| {self.__format_rupiah(self.__tunjangan):>11} "
              f"| {self.__format_rupiah(self.__potongan):>11} "
              f"| {self.__format_rupiah(self.__gaji_total):>11} |")
        print(garis)


def main():
    pilihan = None
    jumlah_objek = 0

    while True:
        print("\n=================================================")
        print("         PROGRAM GAJI KARYAWAN (jalankan objek)")
        print("=================================================")
        print(f"Objek yang sudah dijalankan : {jumlah_objek}")
        print("1. Jalankan objek Gaji baru (input data karyawan)")
        print("0. Keluar")
        try:
            pilihan = int(input("Pilih menu : "))
        except ValueError:
            pilihan = -1

        if pilihan == 1:
            jumlah_objek += 1
            print(f"\n--- Objek ke-{jumlah_objek} ---")
            karyawan = Gaji()
            karyawan.input_gaji()
            karyawan.proses()
            karyawan.cetak()
        elif pilihan == 0:
            if jumlah_objek < 3:
                print("\nProgram ini wajib menjalankan minimal 3 objek sebelum keluar.")
                print(f"Objek yang baru dijalankan: {jumlah_objek}. Silakan lanjutkan.")
                pilihan = -1
            else:
                print(f"\nProgram selesai. Total objek yang dijalankan: {jumlah_objek}")
        else:
            print("Pilihan tidak valid, silakan coba lagi.")

        if pilihan == 0 and jumlah_objek >= 3:
            break


if __name__ == "__main__":
    main()
