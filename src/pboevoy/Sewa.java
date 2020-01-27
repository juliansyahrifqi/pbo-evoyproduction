package pboevoy;

import java.io.*;
import java.sql.*;

public class Sewa {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    ClearScreen cls = new ClearScreen();
    
    public void menuSewa() {
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tampilkan Data Sewa          |");
        System.out.println("|   2. Ubah Data Sewa               |");
        System.out.println("|   3. Cari Data Sewa               |");          
        System.out.println("|   4. Hapus Data Pelanggan         |");
        System.out.println("|   0. Keluar                       |");
        System.out.format("=====================================%n");
        System.out.println("");
        System.out.print("Pilihan Anda : ");
        
        try {
            int pilihan = Integer.parseInt(input.readLine());
         
            switch(pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1: 
                    //;
                    break;
                case 2:
                    //;
                    break;
                case 3:
                    //;
                    break;
                case 4:
                    //menuCariPelanggan();
                    break;
                case 5:
                    //hapusDataPelanggan();
                    break;
                default:
                    System.out.println("Pilihan salah");
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tampilDataSewa() {
        
    }
}
