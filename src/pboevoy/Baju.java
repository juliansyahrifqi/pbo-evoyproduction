package pboevoy;

import java.io.*;
import java.sql.*;

public class Baju{
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    public void menuBaju() {
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tambah Data Baju             |");
        System.out.println("|   2. Tampilkan Data Baju          |");
        System.out.println("|   3. Ubah Data Baju               |");
        System.out.println("|   4. Cari Data Baju               |");
        System.out.println("|   5. Hapus Data Baju              |");
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
                    tambahDataBaju();
                    break;
                case 2:
                    tampilDataBaju();
                    break;
                case 3:
                    ubahDataBaju();
                    break;
                case 4:
                    cariDataBaju();
                    break;
                case 5:
                    hapusDataBaju();
                    break;
                default:
                    System.out.println("Pilihan salah");
                    //
                    //
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void tambahDataBaju() {
        db.connect();
        try {
        
            System.out.format("======================================%n");
            System.out.format("|          TAMBAH DATA BAJU          |%n");
            System.out.format("+====================================+%n");

            System.out.print("|  Kode Baju : ");
            String kode_baju = input.readLine();

            System.out.print("|  Nama Baju : ");
            String nama_baju = input.readLine();
            
            System.out.print("|  Stok Baju : ");
            int stok_baju = Integer.parseInt(input.readLine());
            
            System.out.print("|  Harga Baju : ");
            int harga_baju = Integer.parseInt(input.readLine());
            
            String sql = "INSERT INTO `evoyproduction`.`baju` (`kode_baju`, `nama_baju`, `stok_baju`, `harga`)"
                    + "VALUES ('%s', '%s', '%d', '%d');";
            
            sql = String.format(sql, kode_baju, nama_baju, stok_baju, harga_baju);
            
            db.getStatement().execute(sql);
            
            int intBaris = db.getStatement().executeUpdate(sql);
                if ( intBaris > 0 ) {
                    System.out.println("Penambahan Data berhasil");
                }
                else {
                    System.out.println("Penambahan Data gagal");
                }  
        }
        catch(IOException | NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void tampilDataBaju() {
        db.connect();
        
        String sql = "SELECT * FROM baju";
        try { 
            
            rs = db.getStatement().executeQuery(sql);
              
            String tbl = "| %-11s | %-19s | %-6d | %-13d | %n";

            System.out.format("=============================================================%n");
            System.out.format("|                   Data Baju Evoy Production                |%n");
            System.out.format("+=============+=====================+========+===============+%n");
            System.out.format("|  Kode Baju  |      Nama Baju      |  Stok  |     Harga     |%n");
            System.out.format("+=============+=====================+========+===============+%n");


            while(rs.next()) {

               String kodeBaju = rs.getString("kode_baju");
               String namaBaju = rs.getString("nama_baju");
               int stokBaju    = rs.getInt("stok_baju");
               int hargaBaju   = rs.getInt("harga");

               System.out.format(tbl, kodeBaju, namaBaju, stokBaju, hargaBaju);
            }
            System.out.format("+=============+=====================+========+===============+%n");
           
            db.closeConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    

    public void ubahDataBaju() 
    {
        db.connect();
        try 
	{  
            
            System.out.format("======================================%n");
            System.out.format("|           UBAH DATA BAJU           |%n");
            System.out.format("+====================================+%n");
        
            // ambil input dari user
            System.out.print("Kode baju yang mau diedit: ");
            String kode_baju = input.readLine().trim();
           
            System.out.print("Nama baju : ");
            String nama_baju = input.readLine().trim();
            
            System.out.print("Jumlah : ");
            int stok_baju = Integer.parseInt(input.readLine());
            
            System.out.print("Harga : ");
            int harga = Integer.parseInt(input.readLine());

            // query update
            String sql = "UPDATE baju SET nama_baju='%s', stok_baju=%d, harga=%d WHERE kode_baju='%s'";
            sql = String.format(sql, nama_baju, stok_baju, harga, kode_baju);

            // update data baju
            db.getStatement().execute(sql);
        } 
        catch (IOException | NumberFormatException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
    public void cariDataBaju() {
      
        try 
        {
            db.connect();

            System.out.print("Cari Kode Baju : ");
            String kode_baju = input.readLine();
            
            String sql = String.format("SELECT * FROM baju WHERE kode_baju = '%s';", kode_baju);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl = "| %-11s | %-19s | %-6d | %-13d | %n";

            System.out.format("=============================================================%n");
            System.out.format("|                   Data Baju Evoy Production                |%n");
            System.out.format("+=============+=====================+========+===============+%n");
            System.out.format("|  Kode Baju  |      Nama Baju      |  Stok  |     Harga     |%n");
            System.out.format("+=============+=====================+========+===============+%n");
            
            while(rs.next()) 
            {
                kode_baju = rs.getString("kode_baju");
                String nama_baju = rs.getString("nama_baju");
                int stok_baju = rs.getInt("stok_baju");
                int harga = rs.getInt("harga");
                
                System.out.format(tbl, kode_baju, nama_baju, stok_baju, harga);
            }
            System.out.format("+=============+=====================+========+===============+%n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void hapusDataBaju()
    {
         db.connect();
         try
         {
             
        System.out.format("======================================%n");
        System.out.format("|          HAPUS DATA BAJU           |%n");
        System.out.format("+====================================+%n");
         // ambil input dari user
        System.out.print("Kode baju yang mau dihapus: ");
        String kode_baju = input.readLine().trim();
        
        // buat query hapus
        String sql = String.format("DELETE FROM baju WHERE `kode_baju` ='%s'", kode_baju);
        // hapus data
        db.getStatement().execute(sql);
        
        System.out.println("Data telah terhapus...");
         } 
        catch (IOException | SQLException e)
        {
            System.out.println(e);
        }
    }
}
