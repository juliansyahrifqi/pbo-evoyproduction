package pboevoy;

import java.sql.*;
import java.io.*;

public class EvoyProduction  {
    
    static ResultSet rs;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB("root", "");
        ClearScreen cls = new ClearScreen();
        Baju baju = new Baju();
        Pelanggan pelanggan = new Pelanggan();
        Penyewaan sewa = new Penyewaan();
        Pembayaran pembayaran = new Pembayaran();
        
        db.connect();
        
        try {   
            while (!db.getConnection().isClosed()) {
                
                cls.clrscr();
                System.out.format("=====================================%n");
                System.out.format("|          SEWA BAJU ADAT           |%n");
                System.out.format("|         EVOY PRODUCTION           |%n");
                System.out.format("=====================================%n");
                System.out.println("|   1. Data Pelanggan               |");
                System.out.println("|   2. Data Baju                    |");
                System.out.println("|   3. Ubah Data                    |");
                System.out.println("|   4. Penyewaan Baju               |");
                System.out.println("|   5. Pembayaran Sewa              |");
                System.out.println("|   0. Keluar                       |");
                System.out.format("=====================================%n");
                System.out.println("");
                System.out.print("Pilihan Anda : ");

                int pilihan = Integer.parseInt(input.readLine());

                switch(pilihan) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1: 
                        cls.clrscr();
                        pelanggan.menuPelanggan();
                        break;
                    case 2:
                        baju.menuBaju();
                        break;
                    case 3:
                        //menuUbahData();
                        break;
                    case 4:
                        sewa.sewa_baju();
                        break;
                    case 5:
                        pembayaran.bayar_sewa();
                        break;
                    default:
                        System.out.println("Pilihan salah");
                        //clrscr();
                }        
            }        
            db.closeConnection();
        }  
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}