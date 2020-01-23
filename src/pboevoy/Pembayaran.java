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

          //  System.out.print("|  ID Pelanggan \t: ");
          //  String id_pelanggan = input.readLine();
            
         System.out.print("|  Baju yang dipesan : \t");
          String nama_baju = input.readLine();
            
         System.out.print("|  Jumlah Baju : \t");
            int qty = Integer.parseInt(input.readLine());

         //  System.out.print("|  Tgl Sewa (YYYY-MM-DD) \t: ");
         //   String tgl_sewa = input.readLine();

         //   System.out.print("|  Tgl Kembali (YYYY-MM/-DD \t: ");
        //  String tgl_kembali = input.readLine();
            
        
          // System.out.print("|  DP Sewa \t: ");
          //int dp_sewa = Integer.parseInt(input.readLine());

            //Insert to table sewa
          //  String sql = "INSERT INTO sewa (`no_sewa`, `id_pelanggan, `tgl_sewa`, `tgl_kembali`, `total_bayar`, `dp_sewa`) "
            //       + "VALUES ('%s', '%s', '%s', '%s', '%d', '%d') ";
           // sql = String.format(sql, no_sewa, id_pelanggan,  tgl_sewa, tgl_kembali,  dp_sewa);
            
            String sql = "INSERT INTO detail_sewa (`qty`) VALUES ('%d')";
            sql = String.format(sql, qty);
            db.getStatement().execute(sql);
            
           String getQty  = String.format("SELECT qty FROM detail_sewa WHERE qty = '%d'", qty);
           
           rs =  db.getStatement().executeQuery(getQty);
           
           rs.next();
           int jumlah = rs.getInt("qty");
           
            String getHarga = String.format("SELECT harga FROM baju WHERE nama_baju = '%s'", nama_baju);
            getHarga = String.format(getHarga, nama_baju);
            
            db.getStatement().execute(getHarga);
            
            rs = db.getStatement().executeQuery(getHarga);
           
           rs.next();
           int harga = rs.getInt("harga");
           
           
           int total_bayar = harga * jumlah;
           String getTotalBayar = "INSERT INTO sewa (`no_sewa`, `total_bayar`) VALUES ('%s', '%d')";
           getTotalBayar = String.format(getTotalBayar, no_sewa, total_bayar);
           
           db.getStatement().execute(getTotalBayar);
              
            //Cek ID Pelanggan apakah sudah terdaftar
            //String sql2 = String.format("SELECT id_pelanggan from pelanggan WHERE id_pelanggan='%s'", id_pelanggan);
                
            //rs = db.getStatement().executeQuery(sql2);
            
            
            //if(rs.next() == true) {
              //  System.out.println("Data Pelanggan dengan " + id_pelanggan + " sudah terdaftar");
              //  db.getStatement().execute(sql);
           // } else {
              //  System.out.println("Data pelanggan dengan " + id_pelanggan + "belum terdaftar");
              //  System.out.print("Apakah pelanggan akan didaftarkan ? ");
              //  char daftar = input.readLine().charAt(0);
                
              //  if(daftar == y ) {
               //     pelanggan.tambahDataPelanggan();
               // } else if (daftar == n) {
                    
               //}
         //   }
    
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
    }
}
