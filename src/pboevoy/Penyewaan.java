package pboevoy;

import java.io.*;
import java.sql.*;

public class Penyewaan {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    Pelanggan pelanggan = new Pelanggan();
    char y, n;
    int denda;
    
    public void sewa_baju() {
        try {
            db.connect();
            
            System.out.format("================================================%n");
            System.out.format("|            SEWA BAJU EVOY PRODUCTION         |%n");
            System.out.format("+==============================================+%n");

            System.out.print("|  No Sewa \t\t   : ");
            String no_sewa = input.readLine();

            System.out.print("|  ID Pelanggan \t   : ");
            String id_pelanggan = input.readLine();
            
            System.out.print("|  Nama Baju \t\t   : ");
            String nama_baju = input.readLine();
            
            System.out.print("|  Jumlah  \t\t   : ");
            int qty = Integer.parseInt(input.readLine());

            System.out.print("|  Tgl Sewa (YYYY-MM-DD)   : ");
            String tgl_sewa = input.readLine();

            System.out.print("|  Tgl Kembali (YYYY-MM-DD): ");
            String tgl_kembali = input.readLine();
            
            System.out.print("|  DP Sewa \t\t   : ");
            int dp_sewa = Integer.parseInt(input.readLine());
     
            //Ambil kode baju
            String getKodeBaju = String.format("SELECT kode_baju FROM baju WHERE nama_baju = '%s'", nama_baju);
            rs = db.getStatement().executeQuery(getKodeBaju);
            
            rs.next();
            String kode_baju = rs.getString("kode_baju");
            
            //Ambil harga baju dari tabel baju yang disewa
            String getHarga = String.format("SELECT harga FROM baju WHERE nama_baju = '%s'", nama_baju);
            getHarga = String.format(getHarga, nama_baju);
            db.getStatement().execute(getHarga);
            
            rs = db.getStatement().executeQuery(getHarga);
            
            rs.next();
            int harga = rs.getInt("harga");
           
            // Menghitung total bayar
            int total_bayar = harga * qty;
                    
            //Tambah data sewa
            String tambah_sewa = "INSERT INTO sewa (`no_sewa`, `id_pelanggan`, `tgl_sewa`, `tgl_kembali`, `total_bayar`, `dp_sewa`) "
                   + "VALUES ('%s', '%s', '%s', '%s', '%d', '%d');";
            tambah_sewa = String.format(tambah_sewa, no_sewa, id_pelanggan,  tgl_sewa, tgl_kembali, total_bayar, dp_sewa);
            
            //Cek ID Pelanggan apakah sudah terdaftar
            String cek_id = String.format("SELECT id_pelanggan from pelanggan WHERE id_pelanggan='%s';", id_pelanggan);
                
            rs = db.getStatement().executeQuery(cek_id);
            
            if(rs.next() == true) //Jika data pelanggan ada 
            { 
                db.getStatement().execute(tambah_sewa);
            } else 
            {
                System.out.println("Data pelanggan dengan " + id_pelanggan + "belum terdaftar");
                System.out.print("Daftarkan pelanggan ? ");
                
                char daftar = (char)input.read();
                
                switch(daftar) {
                    case 'y': 
                        pelanggan.tambahDataPelanggan();
                    break;
                    
                    case 'n':
                        System.exit(0);
                    break;
                       
                    default:
                        System.out.println("Masukkan salah");
                    break;
                }
            }
            
            //Menghitung jml_hari
            String getJmlHari = String.format("SELECT DATEDIFF (tgl_kembali, tgl_sewa) AS jml_hari FROM sewa WHERE no_sewa = '%s'", no_sewa);
            getJmlHari = String.format(getJmlHari, no_sewa);
            db.getStatement().execute(getJmlHari);
           
            rs = db.getStatement().executeQuery(getJmlHari);
            
            rs.next();
            int jml_hari = rs.getInt("jml_hari");
            
            if(jml_hari > 3) 
            {
                denda = harga;
            } 
            else 
            {
                denda = 0;
            }
            
            int updateBayar = denda + total_bayar;
            
            //Tambah detail_sewa
            String tambah_detail_sewa = "INSERT INTO detail_sewa (no_sewa, kode_baju, qty, jml_hari, denda, total_bayar) "
                    + "VALUES ('%s', '%s', '%d', '%d', '%d', '%d');";
            tambah_detail_sewa = String.format(tambah_detail_sewa, no_sewa, kode_baju, qty, jml_hari, denda, updateBayar);
            db.getStatement().execute(tambah_detail_sewa);
            
            //Update total bayar di tabel sewa
            String update_sewa = "UPDATE sewa SET total_bayar = '%d' WHERE no_sewa = '%s';";
            update_sewa = String.format(update_sewa, updateBayar, no_sewa);
            
            db.getStatement().execute(update_sewa);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
    }
}
