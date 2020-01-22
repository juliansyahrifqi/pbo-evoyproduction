package pboevoy;

import java.io.*;
import java.sql.*;

public class Pembayaran {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    Pelanggan pelanggan = new Pelanggan();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    char y, n;
    
    public void bayar() {
        try {
            db.connect();

           System.out.print("|  No Sewa : ");
           String no_sewa = input.readLine();

            System.out.print("|  ID Pelanggan \t: ");
            String id_pelanggan = input.readLine();
            
            System.out.print("|  Baju yang dipesan : \t");
            String nama_baju = input.readLine();
            
           System.out.print("|  Jumlah Baju : \t");
            int jml_baju = Integer.parseInt(input.readLine());

           System.out.print("|  Tgl Sewa (YYYY-MM-DD) \t: ");
            String tgl_sewa = input.readLine();

            System.out.print("|  Tgl Kembali (YYYY-MM/-DD \t: ");
           String tgl_kembali = input.readLine();
            
            System.out.print("| Total Bayar : \t");
            int total_bayar = Integer.parseInt(input.readLine());

           System.out.print("|  DP Sewa \t: ");
           int dp_sewa = Integer.parseInt(input.readLine());

            //Insert to table sewa
            String sql = "INSERT INTO sewa (`no_sewa`, `id_pelanggan, `tgl_sewa`, `tgl_kembali`, `total_bayar`, `dp_sewa`) "
                   + "VALUES ('%s', '%s', '%s', '%s', '%d', '%d') ";
            sql = String.format(sql, no_sewa, id_pelanggan,  tgl_sewa, tgl_kembali, total_bayar, dp_sewa);
            
            
            //Cek ID Pelanggan apakah sudah terdaftar
            String sql2 = String.format("SELECT id_pelanggan from pelanggan WHERE id_pelanggan='%s'", id_pelanggan);
                
            rs = db.getStatement().executeQuery(sql2);
            
            
            if(rs.next() == true) {
                System.out.println("Data pelanggan dengan " + id_pelanggan+ " sudah ada");
                db.getStatement().execute(sql);
            } else {
                System.out.println("Data pelanggan dengan " + id_pelanggan + "belum terdaftar");
                System.out.print("Apakah pelanggan akan didaftarkan ? ");
                char daftar = input.readLine().charAt(0);
                
                if(daftar == y ) {
                    pelanggan.tambahDataPelanggan();
                } else if (daftar == n) {
                    
                }
            }
    
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
    }
}
