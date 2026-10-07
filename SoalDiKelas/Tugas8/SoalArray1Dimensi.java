/*
Nama Program   : Program Array 1 Dimensi (java)
Nama & NPM     : Muhammad Yunus Habiby (140810250014)
                 Azrel Sakhi Reswara (140810250098)
                 Muhammad Kemal Firdaus (140810250101)
Tanggal Dibuat : 7 Oktober 2026
Deskripsi      : Program untuk mencari nilai rata-rata, nilai tertinggi, nilai terendah dan sorting dari
kumpulan data bertipe array of integer.
*/

package GithubRepo.Kelompok6PBO.Tugas8;

import java.util.Scanner;
import java.util.Arrays;

// Kelas OOP untuk memproses array
class Array1D {
    private int[] data;

    // Method untuk menginput data ke dalam array
    public void inputData(Scanner scanner) {
        System.out.print("Masukkan jumlah elemen array: ");
        int size = scanner.nextInt();
        data = new int[size];
        
        for (int i = 0; i < data.length; i++) {
            System.out.print("Masukkan elemen ke-" + (i + 1) + ": ");
            data[i] = scanner.nextInt();
        }
    }

    public double getAverage() {
        if (data == null || data.length == 0) return 0;
        double sum = 0;
        for (int num : data) sum += num;
        return sum / data.length;
    }

    public int getMax() {
        if (data == null || data.length == 0) return 0;
        int max = data[0];
        for (int num : data) if (num > max) max = num;
        return max;
    }

    public int getMin() {
        if (data == null || data.length == 0) return 0;
        int min = data[0];
        for (int num : data) if (num < min) min = num;
        return min;
    }

    public void sortArray() {
        if (data != null) {
            Arrays.sort(data);
            System.out.println("Array berhasil diurutkan.");
        }
    }

    public void displayArray() {
        if (data != null) {
            System.out.println("Data saat ini: " + Arrays.toString(data));
        }
    }
    
    public boolean isReady() {
        return data != null;
    }
}


public class SoalArray1Dimensi {

     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Array1D arr = new Array1D();
        int pilihan;

        do {
            System.out.println("\n=== MENU OPERASI ARRAY 1D ===");
            System.out.println("1. Input Data");
            System.out.println("2. Cari Nilai Rata-rata");
            System.out.println("3. Cari Nilai Tertinggi");
            System.out.println("4. Cari Nilai Terendah");
            System.out.println("5. Sorting Array");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    arr.inputData(scanner);
                    arr.displayArray();
                    break;
                case 2:
                    if (arr.isReady()) System.out.println("Rata-rata: " + arr.getAverage());
                    else System.out.println("Data kosong! Silakan input data dulu.");
                    break;
                case 3:
                    if (arr.isReady()) System.out.println("Nilai Tertinggi: " + arr.getMax());
                    else System.out.println("Data kosong!");
                    break;
                case 4:
                    if (arr.isReady()) System.out.println("Nilai Terendah: " + arr.getMin());
                    else System.out.println("Data kosong!");
                    break;
                case 5:
                    if (arr.isReady()) {
                        arr.sortArray();
                        arr.displayArray();
                    } else System.out.println("Data kosong!");
                    break;
                case 6:
                    System.out.println("Keluar dari program...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 6);
        
        scanner.close();
    }
    
}
