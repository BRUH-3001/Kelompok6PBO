"""
Nama Program : KoordinatKartesianOOP.py
Nama Kelompok: Muhammad Yunus Habiby (140810250014)
               Azrel Sakhi Reswara (140810250098)
               Muhammad Kemal Firdaus (1408102500101)
Tanggal Buat : 6 Oktober 2026
Deskripsi    : Program OOP Koordinat Kartesian dengan Class Menu & Validator
"""

import math

# ==========================================
# CLASS VALIDATOR (Handling Error Input)
# ==========================================
class Validator:
    @staticmethod
    def baca_angka_float(prompt):
        """Memvalidasi input agar selalu berupa angka (bisa desimal)."""
        while True:
            try:
                teks = input(prompt)
                return float(teks)
            except ValueError:
                print("  [!] Input tidak valid! Harus berupa angka (tidak boleh huruf/simbol).")

    @staticmethod
    def baca_pilihan_menu(prompt, min_val, max_val):
        """Memvalidasi input integer khusus untuk rentang pilihan menu."""
        while True:
            try:
                teks = input(prompt)
                nilai = int(teks)
                if min_val <= nilai <= max_val:
                    return nilai
                else:
                    print(f"  [!] Pilihan tidak valid! Harus antara {min_val} sampai {max_val}.")
            except ValueError:
                print("  [!] Input harus berupa angka bulat untuk memilih menu!")


# ==========================================
# CLASS KOORDINAT
# ==========================================
class Koordinat:
    def __init__(self, absis=0.0, ordinat=0.0):
        self.__absis = float(absis)
        self.__ordinat = float(ordinat)

    # --- Setter & Getter ---
    def set_absis(self, absis): 
        self.__absis = float(absis)

    def set_ordinat(self, ordinat): 
        self.__ordinat = float(ordinat)

    def set_koordinat(self, absis, ordinat):
        self.__absis = float(absis)
        self.__ordinat = float(ordinat)

    def get_absis(self): 
        return self.__absis

    def get_ordinat(self): 
        return self.__ordinat

    # --- Input & Output ---
    def input_koordinat(self):
        # Menggunakan class Validator untuk input koordinat
        self.__absis = Validator.baca_angka_float("  Masukkan Absis (X)   : ")
        self.__ordinat = Validator.baca_angka_float("  Masukkan Ordinat (Y) : ")

    def print_koordinat(self):
        print(f"({self.__absis}, {self.__ordinat})")

    # --- CARA 1: Method Fungsi Return ---
    def titik_tengah_return(self, P):
        return Koordinat((P.__absis + self.__absis) / 2.0, (P.__ordinat + self.__ordinat) / 2.0)

    def cermin_sumbu_x_return(self):
        return Koordinat(self.__absis, -self.__ordinat)

    def cermin_sumbu_y_return(self):
        return Koordinat(-self.__absis, self.__ordinat)

    def hitung_jarak_return(self, P):
        return math.sqrt((P.__absis - self.__absis)**2 + (P.__ordinat - self.__ordinat)**2)

    # --- CARA 2: Method Prosedur Void / Mutator ---
    def titik_tengah_void(self, P1, P2):
        self.__absis = (P1.__absis + P2.__absis) / 2.0
        self.__ordinat = (P1.__ordinat + P2.__ordinat) / 2.0

    def cermin_sumbu_x_void(self):
        self.__ordinat = -self.__ordinat

    def cermin_sumbu_y_void(self):
        self.__absis = -self.__absis

    def hitung_jarak_void(self, P1, P2):
        d = math.sqrt((P2.__absis - P1.__absis)**2 + (P2.__ordinat - P1.__ordinat)**2)
        print(f"Jarak ke Titik Pembanding = {d}")


# ==========================================
# CLASS MENU (Menangani Tampilan & Alur)
# ==========================================
class MenuKoordinat:
    @staticmethod
    def print_koordinat_luar(K):
        print(f"Nilai Absis = {K.get_absis()}, Nilai Ordinat = {K.get_ordinat()}")

    def sub_menu_proses(self, ttk_aktif, ttk_pembanding, nama_objek):
        ttk_hasil = Koordinat()

        print("\n========================================")
        print(f"   PILIHAN METODE UNTUK {nama_objek}")
        print("========================================")
        print("1. Cara 1 (Fungsi Return)")
        print("2. Cara 2 (Prosedur Void)")
        
        # Validasi input menu metode (1-2)
        cara = Validator.baca_pilihan_menu("Pilih Metode (1-2): ", 1, 2)

        print("\n--- PILIHAN PROSES PERHITUNGAN ---")
        print("1. Titik Tengah (dengan titik pembanding)")
        print("2. Pencerminan Terhadap Sumbu X")
        print("3. Pencerminan Terhadap Sumbu Y")
        print("4. Hitung Jarak (dengan titik pembanding)")
        
        # Validasi input menu proses (1-4)
        proses = Validator.baca_pilihan_menu("Pilih Proses (1-4): ", 1, 4)

        print(f"\n>>> HASIL PERHITUNGAN <<<")
        if cara == 1:  # CARA 1 (RETURN)
            if proses == 1:
                ttk_hasil = ttk_aktif.titik_tengah_return(ttk_pembanding)
                print("Titik Tengah = ", end="")
                ttk_hasil.print_koordinat()
            elif proses == 2:
                ttk_hasil = ttk_aktif.cermin_sumbu_x_return()
                print("Hasil Pencerminan Sumbu X = ", end="")
                ttk_hasil.print_koordinat()
            elif proses == 3:
                ttk_hasil = ttk_aktif.cermin_sumbu_y_return()
                print("Hasil Pencerminan Sumbu Y = ", end="")
                ttk_hasil.print_koordinat()
            elif proses == 4:
                print(f"Jarak ke Titik Pembanding = {ttk_aktif.hitung_jarak_return(ttk_pembanding)}")

        else:  # CARA 2 (VOID)
            if proses == 1:
                ttk_hasil.titik_tengah_void(ttk_aktif, ttk_pembanding)
                print("Titik Tengah = ", end="")
                ttk_hasil.print_koordinat()
            elif proses == 2:
                temp = Koordinat(ttk_aktif.get_absis(), ttk_aktif.get_ordinat())
                temp.cermin_sumbu_x_void()
                print("Hasil Pencerminan Sumbu X = ", end="")
                temp.print_koordinat()
            elif proses == 3:
                temp = Koordinat(ttk_aktif.get_absis(), ttk_aktif.get_ordinat())
                temp.cermin_sumbu_y_void()
                print("Hasil Pencerminan Sumbu Y = ", end="")
                temp.print_koordinat()
            elif proses == 4:
                ttk_hasil.hitung_jarak_void(ttk_aktif, ttk_pembanding)

    def jalankan(self):
        # Objek 1 & 2 Didefinisikan Awal
        ttk1 = Koordinat(2, 1)  
        ttk2 = Koordinat()       
        ttk2.set_koordinat(6, 3)

        while True:
            print("\n========================================")
            print("       MENU UTAMA OBJEK KOORDINAT")
            print("========================================")
            print("1. Objek 1 (Default Constructor : (2, 1))")
            print("2. Objek 2 (Set via Setter      : (6, 3))")
            print("3. Objek 3 (Input 2 Titik oleh User)")
            print("4. Keluar Program")
            
            # Validasi input menu utama (1-4)
            pilihan_objek = Validator.baca_pilihan_menu("Pilihan Objek (1-4): ", 1, 4)

            if pilihan_objek == 1:
                print("\n[OBJEK 1 DIPILIH]")
                print("Detail Koordinat Utama (Output Luar): ", end="")
                self.print_koordinat_luar(ttk1)
                print("Titik Pembanding (ttk2): ", end="")
                ttk2.print_koordinat()
                self.sub_menu_proses(ttk1, ttk2, "OBJEK 1")
                
            elif pilihan_objek == 2:
                print("\n[OBJEK 2 DIPILIH]")
                print("Detail Koordinat Utama (Output Dalam): ", end="")
                ttk2.print_koordinat()
                print("Titik Pembanding (ttk1): ", end="")
                ttk1.print_koordinat()
                self.sub_menu_proses(ttk2, ttk1, "OBJEK 2")
                
            elif pilihan_objek == 3:
                ttk_user1 = Koordinat()
                ttk_user2 = Koordinat()
                print("\n[OBJEK 3 DIPILIH - INPUT 2 TITIK KOORDINAT]\n")
                print("--- Input Titik Pertama (Titik Utama) ---")
                ttk_user1.input_koordinat()
                print("--- Input Titik Kedua (Titik Pembanding) ---")
                ttk_user2.input_koordinat()

                print("\nDetail Titik 1: ", end="")
                ttk_user1.print_koordinat()
                print("Detail Titik 2: ", end="")
                ttk_user2.print_koordinat()

                self.sub_menu_proses(ttk_user1, ttk_user2, "OBJEK 3")
                
            elif pilihan_objek == 4:
                print("\nProgram selesai!")
                break


# ==========================================
# MAIN EXECUTION
# ==========================================
if __name__ == "__main__":
    aplikasi = MenuKoordinat()
    aplikasi.jalankan()