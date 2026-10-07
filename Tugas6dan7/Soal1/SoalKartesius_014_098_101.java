import java.util.Scanner;

class Titik {

    private double x, y;

    public Titik() {
        this.x = 0;
        this.y = 0;
    }

    public Titik(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Titik titikTengah (Titik titikLain) {
        double tx = (this.x + titikLain.x) / 2;
        double ty = (this.y + titikLain.y) / 2;
        return new Titik(tx, ty);
    }

    public Titik cerminX() {
        return new Titik(this.x, -this.y);
    }

    public Titik cerminY() {
        return new Titik(-this.x, this.y);
    }

    public void inputDariDalam(Scanner scan) {
        System.out.print("Masukkan nilai x: ");
        this.x = scan.nextDouble();
        System.out.print("Masukkan nilai y: ");
        this.y = scan.nextDouble();
    }

    @Override 
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
} 

public class SoalKartesius_014_098_101 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== MENU KOORDINAT KARTESIUS ===");
            System.out.println("1. Input Konstan (Hardcode)");
            System.out.println("2. Input Lewat Constructor");
            System.out.println("3. Input Dari Dalam Kelas");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scan.nextInt();

            switch (pilihan) {
                case 1:
                    modeKonstan();
                    break;
                case 2:
                    modeConstructor(scan);
                    break;
                case 3:
                    modeInputDalam(scan);
                    break;
                case 0:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);

        scan.close();
    }

    private static void modeKonstan() {
        Titik a = new Titik(2, 3);
        Titik b = new Titik(6, 7);
        tampilkanHasil(a, b);
    }

    private static void modeConstructor(Scanner scan) {
        System.out.print("Masukkan X1: ");
        double x1 = scan.nextDouble();
        System.out.print("Masukkan Y1: ");
        double y1 = scan.nextDouble();
        Titik a = new Titik(x1, y1);

        System.out.print("Masukkan X2: ");
        double x2 = scan.nextDouble();
        System.out.print("Masukkan Y2: ");
        double y2 = scan.nextDouble();
        Titik b = new Titik(x2, y2);

        tampilkanHasil(a, b);
    }

    private static void modeInputDalam(Scanner scan) {
        Titik a = new Titik();
        System.out.println("Input Titik A:");
        a.inputDariDalam(scan);

        Titik b = new Titik();
        System.out.println("Input Titik B:");
        b.inputDariDalam(scan);

        tampilkanHasil(a, b);
    }

    private static void tampilkanHasil(Titik a, Titik b) {
        System.out.println("Titik A: " + a);
        System.out.println("Titik B: " + b);
        System.out.println("Titik Tengah A & B: " + a.titikTengah(b));
        System.out.println("Cermin A terhadap sumbu X: " + a.cerminX());
        System.out.println("Cermin A terhadap sumbu Y: " + a.cerminY());
        System.out.println("Cermin B terhadap sumbu X: " + b.cerminX());
        System.out.println("Cermin B terhadap sumbu Y: " + b.cerminY());
    }
}
