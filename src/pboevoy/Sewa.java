
package pboevoy;

import java.io.*;
import java.sql.*;

public class Sewa {
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    ClearScreen cls = new ClearScreen();
    String no_sewa, nama_baju, nama, tgl_sewa, tgl_kembali, status;
    int qty, jml_hari, denda, total_bayar, dp_sewa;
    
    public void menuDataSewa() {
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tampil Data Sewa             |");
        System.out.println("|   2. Ubah Data Sewa               |");
        System.out.println("|   3. Cari Data Sewa               |");
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
                    menuTampilSewa();
                    break;
                case 2:
                    menuUbahSewa();
                    break;
                case 3:
                    menuCariSewa();
                    break;
                default:
                    System.out.println("Pilihan salah");
                    break;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void menuTampilSewa() {
        System.out.format("==========================================%n");
        System.out.format("|         TAMPIL DATA PELANGGAN          |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Tampil Semua Data Sewa             |");
        System.out.println("| 2. Tampil Data Sewa Yang Belum Lunas  |");
        System.out.println("| 0. Keluar                             |");
        System.out.format("=========================================%n");
        System.out.println("");
        System.out.print("Pilihan Anda : ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch(pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1: 
                    tampilDataSewa();
                    break;
                case 2:
                    tampilSewaBelumLunas();
                    break;
                default:
                    System.out.println("Pilihan Salah");
                    break;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilDataSewa() {
        try {
            db.connect();
            cls.clrscr();

            String sql = "SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM detail_sewa "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju`"
                    + "ORDER BY no_sewa";
            rs = db.getStatement().executeQuery(sql);

            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

               String no_sewa = rs.getString("no_sewa");
               String nama_pelanggan = rs.getString("nama");
               String nama_baju = rs.getString("nama_baju");
               int qty = rs.getInt("qty");
               String tgl_sewa = rs.getString("tgl_sewa");
               int dp_sewa = rs.getInt("dp_sewa");
               int total_bayar = rs.getInt("total_bayar");
               String status = rs.getString("status");

               System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        catch(Exception e) {
            System.out.println(e);
        }  
    }
    
    public void tampilSewaBelumLunas() {
        try {
            db.connect();
            cls.clrscr();

            String sql = "SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM detail_sewa "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` WHERE sewa.`status` = 'BELUM LUNAS' "
                    + "ORDER BY no_sewa";
            rs = db.getStatement().executeQuery(sql);

            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

               String no_sewa = rs.getString("no_sewa");
               String nama_pelanggan = rs.getString("nama");
               String nama_baju = rs.getString("nama_baju");
               int qty = rs.getInt("qty");
               String tgl_sewa = rs.getString("tgl_sewa");
               int dp_sewa = rs.getInt("dp_sewa");
               int total_bayar = rs.getInt("total_bayar");
               String status = rs.getString("status");

               System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        catch(Exception e) {
            System.out.println(e);
        }  
    } 
    
    public void menuCariSewa() {
        System.out.format("==========================================%n");
        System.out.format("|         TAMPIL DATA PELANGGAN          |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Cari Data Dengan Nomor Sewa        |");
        System.out.println("| 2. Cari Data Dengan Nama Penyewa      |");
        System.out.println("| 2. Cari Data Dengan Nama Baju         |");
        System.out.println("| 0. Keluar                             |");
        System.out.format("=========================================%n");
        System.out.println("");
        System.out.print("Pilihan Anda : ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch(pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1: 
                    cariDataNomorSewa();
                    break;
                case 2:
                    cariDataNamaPenyewa();
                    break;
                case 3:
                    cariDataNamaBaju();
                    break;
                default:
                    System.out.println("Pilihan Salah");
                    break;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
   
    public void menuUbahSewa() {
        System.out.format("==========================================%n");
        System.out.format("|         TAMPIL DATA PELANGGAN          |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Edit Data Tanggal  Sewa            |");
        System.out.println("| 2. Edit Data DP Sewa                  |");
        System.out.println("| 3. Edit Data Status Sewa              |");
        System.out.println("| 4. Edit Semua Data  Sewa              |");
        System.out.println("| 0. Keluar                             |");
        System.out.format("=========================================%n");
        System.out.println("");
        System.out.print("Pilihan Anda : ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch(pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1: 
                    ubahDataTanggalSewa();
                    break;
                case 2:
                    ubahDataDPSewa();
                    break;
                case 3:
                    ubahDataStatusSewa();
                    break;
                case 4:
                    ubahDataSewa();
                    break;
                default:
                    System.out.println("Pilihan Salah");
                    break;
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void ubahDataTanggalSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|            UBAH DATA SEWA          |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa: ");
            no_sewa = input.readLine();

            System.out.print("| Tanggal Sewa \t: ");
            tgl_sewa= input.readLine();

            System.out.print("| Tanggal Kembali   : ");
            tgl_kembali = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `tgl_sewa`='%s', `tgl_kembali`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, tgl_sewa, tgl_kembali, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if (hasil > 0) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else {
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        } 
        catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
    public void ubahDataDPSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|            UBAH DATA SEWA          |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa: ");
            no_sewa = input.readLine();

            System.out.print("| Down Payment (DP) \t: ");
            dp_sewa = Integer.parseInt(input.readLine());


            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `dp_sewa`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, dp_sewa, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if (hasil > 0) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else {
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        } 
        catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
     public void ubahDataStatusSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|            UBAH DATA SEWA          |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa: ");
            no_sewa = input.readLine();

            System.out.print("| Status : ");
            status = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `status`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, status, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if (hasil > 0) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else {
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        } 
        catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }
    }
     
    public void ubahDataSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|            UBAH DATA SEWA          |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa: ");
            no_sewa = input.readLine();

            System.out.print("| Tanggal Sewa \t: ");
            tgl_sewa= input.readLine();

            System.out.print("| Tanggal Kembali   : ");
            tgl_kembali = input.readLine();

            System.out.print("| Down Payment (DP) \t: ");
            dp_sewa = Integer.parseInt(input.readLine());

            System.out.print("| Status : ");
            status = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `tgl_sewa`='%s', `tgl_kembali`='%s',`dp_sewa`='%s', `status`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, tgl_sewa, tgl_kembali, dp_sewa, status, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if (hasil > 0) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else {
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        } 
        catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
    public void cariDataNomorSewa() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.print("Masukkan nomor sewa (S??) :");
            no_sewa = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM detail_sewa "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` WHERE detail_sewa.`no_sewa` ='%s' "
                    + "ORDER BY no_sewa", no_sewa);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

               String no_sewa = rs.getString("no_sewa");
               String nama_pelanggan = rs.getString("nama");
               String nama_baju = rs.getString("nama_baju");
               int qty = rs.getInt("qty");
               String tgl_sewa = rs.getString("tgl_sewa");
               int dp_sewa = rs.getInt("dp_sewa");
               int total_bayar = rs.getInt("total_bayar");
               String status = rs.getString("status");

               System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
        public void cariDataNamaPenyewa() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.print("Masukkan nama penyewa :");
            nama = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM pelanggan "
                    + "INNER JOIN sewa ON pelanggan.`id_pelanggan` = sewa.`id_pelanggan` "
                    + "INNER JOIN detail_sewa ON sewa.`no_sewa` = detail_sewa.`no_sewa` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` WHERE pelanggan.`nama` ='%s' "
                    + "ORDER BY no_sewa", nama);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

               String no_sewa = rs.getString("no_sewa");
               String nama_pelanggan = rs.getString("nama");
               String nama_baju = rs.getString("nama_baju");
               int qty = rs.getInt("qty");
               String tgl_sewa = rs.getString("tgl_sewa");
               int dp_sewa = rs.getInt("dp_sewa");
               int total_bayar = rs.getInt("total_bayar");
               String status = rs.getString("status");

               System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void cariDataNamaBaju() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.print("Masukkan nama baju :");
            nama_baju = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM baju "
                    + "INNER JOIN detail_sewa ON baju.`kode_baju` = detail_sewa.`kode_baju` "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` WHERE baju.`nama_baju` ='%s' "
                    + "ORDER BY no_sewa", nama_baju);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

               String no_sewa = rs.getString("no_sewa");
               String nama_pelanggan = rs.getString("nama");
               String nama_baju = rs.getString("nama_baju");
               int qty = rs.getInt("qty");
               String tgl_sewa = rs.getString("tgl_sewa");
               int dp_sewa = rs.getInt("dp_sewa");
               int total_bayar = rs.getInt("total_bayar");
               String status = rs.getString("status");

               System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    

    
}
