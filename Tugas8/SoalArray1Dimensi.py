"""
Nama Program   : Program Array 1 Dimensi (Python)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 7 Oktober 2026
Deskripsi      : Program untuk mencari nilai rata-rata, nilai tertinggi, nilai terendah dan sorting dari
kumpulan data bertipe array of integer.
"""

# Kelas OOP untuk memproses array
class Array1D:
    def __init__(self):
        self.data = []

    def input_data(self):
        try:
            size = int(input("Masukkan jumlah elemen array: "))
            self.data = []
            for i in range(size):
                val = int(input(f"Masukkan elemen ke-{i + 1}: "))
                self.data.append(val)
        except ValueError:
            print("Masukan tidak valid! Harus berupa angka.")

    def get_average(self):
        if not self.data: return 0
        return sum(self.data) / len(self.data)

    def get_max(self):
        if not self.data: return 0
        return max(self.data)

    def get_min(self):
        if not self.data: return 0
        return min(self.data)

    def sort_array(self):
        if self.data:
            self.data.sort()
            print("Array berhasil diurutkan.")

    def display_array(self):
        if self.data:
            print(f"Data saat ini: {self.data}")
            
    def is_ready(self):
        return len(self.data) > 0


# Fungsi utama yang bertindak murni sebagai menu
def main():
    arr = Array1D()
    
    while True:
        print("\n=== MENU OPERASI ARRAY 1D ===")
        print("1. Input Data")
        print("2. Cari Nilai Rata-rata")
        print("3. Cari Nilai Tertinggi")
        print("4. Cari Nilai Terendah")
        print("5. Sorting Array")
        print("6. Keluar")
        
        pilihan = input("Pilih menu: ")
        
        if pilihan == '1':
            arr.input_data()
            arr.display_array()
        elif pilihan == '2':
            if arr.is_ready():
                print(f"Rata-rata: {arr.get_average()}")
            else:
                print("Data kosong! Silakan input data dulu.")
        elif pilihan == '3':
            if arr.is_ready():
                print(f"Nilai Tertinggi: {arr.get_max()}")
            else:
                print("Data kosong!")
        elif pilihan == '4':
            if arr.is_ready():
                print(f"Nilai Terendah: {arr.get_min()}")
            else:
                print("Data kosong!")
        elif pilihan == '5':
            if arr.is_ready():
                arr.sort_array()
                arr.display_array()
            else:
                print("Data kosong!")
        elif pilihan == '6':
            print("Keluar dari program...")
            break
        else:
            print("Pilihan tidak valid!")

if __name__ == "__main__":
    main()