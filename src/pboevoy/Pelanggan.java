package pboevoy;

import java.io.*;
import java.sql.*;

public class Pelanggan {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    public void menuPelanggan() {
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tambah Data Pelanggan        |");
        System.out.println("|   2. Tampilkan Data Pelanggan     |");
        System.out.println("|   3. Ubah Data Pelanggan          |");
        System.out.println("|   4. Cari Data Pelanggan          |");
        System.out.println("|   5. Hapus Data Pelanggan         |");
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
                    tambahDataPelanggan();
                    break;
                case 2:
                    tampilDataPelanggan();
                    break;
                case 3:
                    ubahDataPelanggan();
                    break;
                case 4:
                    cariDataPelanggan();
                    break;
                case 5:
                    hapusDataPelanggan();
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
    
    public void tambahDataPelanggan() 
    {
        try 
        {     
            db.connect();
            
            System.out.format("======================================%n");
            System.out.format("|        TAMBAH DATA PELANGGAN       |%n");
            System.out.format("+====================================+%n");
            
            System.out.print("|  ID_Pelanggan : ");
            String id_pelanggan = input.readLine();
              
            System.out.print("|  Nama \t: ");
            String nama = input.readLine();
            
            System.out.print("|  Alamat \t: ");
            String alamat = input.readLine();
            
            System.out.print("|  Kota \t: ");
            String kota = input.readLine();
            
            System.out.print("|  No. Tlp \t: ");
            String no_tlp = input.readLine();
            
            String sql = "INSERT INTO `evoyproduction`.`pelanggan` (`id_pelanggan`, `Nama`, `Alamat`, `kota`, `no_tlp`) "
                    + "VALUES ('%s', '%s', '%s', '%s', '%s');";
            sql = String.format(sql, id_pelanggan, nama, alamat, kota, no_tlp);
           
            db.getStatement().execute(sql);
                      
            int intBaris = db.getStatement().executeUpdate(sql);
                if ( intBaris > 0 ) {
                    System.out.println("Penambahan Data berhasil");
                }
                else {
                    System.out.println("Penambahan Data gagal");
                }
        }
        catch(IOException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void tampilDataPelanggan() 
    {   
        try {
            db.connect();
            
            String sql = "SELECT * FROM pelanggan";
            rs = db.getStatement().executeQuery(sql);

            String tbl ="| %-12s | %-12s | %-14s | %-8s | %-12s | %n";

            System.out.format("=========================================================================%n");
            System.out.format("|                       Data Pelanggan Evoy Production                   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            System.out.format("| ID_Pelanggan |     Nama     |     Alamat     |   Kota   |    No. Tlp   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");

            while(rs.next()) {

               String idPelanggan = rs.getString("id_pelanggan");
               String namaPelanggan = rs.getString("nama");
               String alamat = rs.getString("alamat");
               String kota = rs.getString("kota");
               String no_tlp = rs.getString("no_tlp");

               System.out.format(tbl, idPelanggan, namaPelanggan, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+================+==========+==============+%n");

            db.closeConnection();
        }
        catch(Exception e) {
            System.out.println(e);
        }  
    }
    
    public void ubahDataPelanggan()
    {
        db.connect();
        try 
            {  
            // ambil input dari user
            System.out.print("ID Pelanggan yang mau diedit: ");
            String id_pelanggan = input.readLine().trim();

            System.out.print("Nama : ");
            String nama= input.readLine().trim();

            System.out.print("Alamat : ");
            String alamat = input.readLine().trim();

            System.out.print("Kota : ");
            String kota = input.readLine().trim();

            System.out.print("No. Telp : ");
            String no_tlp = input.readLine().trim();

            // query update
            String sql = "UPDATE `evoyproduction`.`pelanggan` SET `nama`='%s', `alamat`='%s', `kota`='%s', `no_tlp`='%s' WHERE `id_pelanggan`='%s'";
            sql = String.format(sql, nama, alamat, kota, no_tlp, id_pelanggan);

            // update data baju
            boolean status = db.getStatement().execute(sql);
            if (status != true) {
                System.out.println("Data berhasil diubah");
            } 
            else {
                System.out.println("Data gagal diubah");
            }
        } 
        catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
    public void cariDataPelanggan() 
    {
        try 
        {
            db.connect();
            
            System.out.printf("Masukkan ID :");
            String id_pelanggan = input.readLine();
            
            String sql = String.format("SELECT * FROM pelanggan WHERE `id_pelanggan` ='%s'", id_pelanggan);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-12s | %-12s | %-14s | %-8s | %-12s | %n";

            System.out.format("=========================================================================%n");
            System.out.format("|                       Data Pelanggan Evoy Production                   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            System.out.format("| ID_Pelanggan |     Nama     |     Alamat     |   Kota   |    No. Tlp   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            
            while(rs.next()) {
                id_pelanggan = rs.getString("id_pelanggan");
                String nama = rs.getString("nama");
                String alamat = rs.getString("alamat");
                String kota = rs.getString("kota");
                String no_tlp = rs.getString("no_tlp");
                
                System.out.format(tbl, id_pelanggan, nama, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+================+==========+==============+%n");   
        }
        catch(Exception e) {
            System.out.println(e);
        }       
    }
    
    public void hapusDataPelanggan()
    {
        try 
        {
        // ambil input dari user
            System.out.print("ID Pelanggan yang mau dihapus: ");
            String id_pelanggan = input.readLine().trim();
        
            // buat query hapus
            String sql = String.format("DELETE FROM baju WHERE `id_pelanggan` ='%s'", id_pelanggan);
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
