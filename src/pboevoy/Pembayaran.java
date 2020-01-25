package pboevoy;

import java.io.*;
import java.sql.*;

public class Pembayaran {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    char y, n;
    int denda;
    
    public void bayar() {
        try {
            Pelanggan pelanggan = new Pelanggan();
            db.connect();

            System.out.print("|  No Sewa : ");
            String no_sewa = input.readLine();

            System.out.print("|  ID Pelanggan \t: ");
            String id_pelanggan = input.readLine();
            
            System.out.print("|  Baju yang dipesan : \t");
            String nama_baju = input.readLine();
            
            System.out.print("|  Jumlah Baju : \t");
            int qty = Integer.parseInt(input.readLine());

            System.out.print("|  Tgl Sewa (YYYY-MM-DD) \t: ");
            String tgl_sewa = input.readLine();

            System.out.print("|  Tgl Kembali (YYYY-MM/-DD \t: ");
            String tgl_kembali = input.readLine();
            
            System.out.print("|  DP Sewa \t: ");
            int dp_sewa = Integer.parseInt(input.readLine());
   
            //Ambil isi dari qty 
            String getQty  = String.format("SELECT qty FROM detail_sewa WHERE qty = '%d'", qty);
            rs =  db.getStatement().executeQuery(getQty);
            
            rs.next();
            int jumlah = rs.getInt("qty");
            
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
           
            //Menghitung total bayar
            int total_bayar = harga * jumlah;
                    
            //Tambah data sewa
            String tambah_sewa = "INSERT INTO sewa (`no_sewa`, `id_pelanggan`, `tgl_sewa`, `tgl_kembali`, `total_bayar`, `dp_sewa`) "
                   + "VALUES ('%s', '%s', '%s', '%s', '%d', '%d') ";
            tambah_sewa = String.format(tambah_sewa, no_sewa, id_pelanggan,  tgl_sewa, tgl_kembali, total_bayar, dp_sewa);
            
            //Tambah detail_sewa
            //String tambah_detail_sewa = "INSERT INTO detail_sewa (`no_sewa`, `kode_baju`, `qty`, `jml_hari`, `denda`, `total_bayar) "
                  //  + "VALUES ('%s', '%s', '%d', '%d'";
            //tambah_detail_sewa = String.format(tambah_detail_sewa, no_sewa, kode_baju, qty, jml_hari, denda, total_bayar);
            
            //Cek ID Pelanggan apakah sudah terdaftar
            String sql2 = String.format("SELECT id_pelanggan from pelanggan WHERE id_pelanggan='%s'", id_pelanggan);
                
            rs = db.getStatement().executeQuery(sql2);
            
            if(rs.next() == true) { //Jika data pelanggan ada 
                System.out.println("Data Pelanggan dengan " + id_pelanggan + " sudah terdaftar");
                db.getStatement().execute(tambah_sewa);
                //db.getStatement().execute(tambah_detail_sewa);
            } else {
                System.out.println("Data pelanggan dengan " + id_pelanggan + "belum terdaftar");
                System.out.print("Apakah pelanggan akan didaftarkan ? ");
                char daftar = (char)input.read();
                
                if(daftar != y ) {
                    pelanggan.tambahDataPelanggan();
                } else if (daftar == n) {
                    System.exit(0);
                } else {
                    System.out.println("Salah");
                }
            }
            
            //Menghitung jml_hari
            String getJmlHari = String.format("SELECT DATEDIFF (tgl_kembali, tgl_sewa) AS jml_hari FROM sewa WHERE no_sewa = '%s'", no_sewa);
            getJmlHari = String.format(getJmlHari, no_sewa);
            db.getStatement().execute(getJmlHari);
           
            rs = db.getStatement().executeQuery(getJmlHari);
            
            rs.next();
            int jml_hari = rs.getInt("jml_hari");
            
            System.out.println(jml_hari);
         
    
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
    }
}
