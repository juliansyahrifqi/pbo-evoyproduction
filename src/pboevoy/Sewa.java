package pboevoy;

import java.io.*;
import java.sql.*;

public class Sewa {
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    ClearScreen cls = new ClearScreen();
    String no_sewa, nama_baju, nama_pelanggan, tgl_sewa, tgl_kembali, status;
    int qty, jml_hari, denda, total_bayar, dp_sewa;
    
    public void menuDataSewa() {
        cls.clrscr();
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tampil Data Sewa             |");
        System.out.println("|   2. Ubah Data Sewa               |");
        System.out.println("|   3. Cari Data Sewa               |");
        System.out.println("|   4. Kembali                      |");
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
                case 4:
                    break;
                default:
                    System.out.println("Pilihan Salah!");
                    System.out.print("Tekan enter untuk kembali");
                    input.readLine();
            }
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void eksekusiQuery() {
        try {
            String tbl ="| %-3s | %-13s | %-15s | %-3d | %-10s | %-6d | %-6d | %-10s |%n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                                 Data Sewa Evoy Production                                |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
            System.out.format("|  No |     Nama      |       Baju      | Qty |  Tgl Sewa  |   DP   |  Total |   Status    |%n");
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");

            while(rs.next()) {

                no_sewa = rs.getString("no_sewa");
                nama_pelanggan = rs.getString("nama");
                nama_baju = rs.getString("nama_baju");
                qty = rs.getInt("qty");
                tgl_sewa = rs.getString("tgl_sewa");
                dp_sewa = rs.getInt("dp_sewa");
                total_bayar = rs.getInt("total_bayar");
                status = rs.getString("status");

                System.out.format(tbl, no_sewa, nama_pelanggan, nama_baju, qty, tgl_sewa, dp_sewa, total_bayar, status);
            }
            System.out.format("+=====================+=================+=====+============+========+========+=============+%n");
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void menuTampilSewa() {
        cls.clrscr();
        System.out.format("==========================================%n");
        System.out.format("|            TAMPIL DATA SEWA            |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Tampil Semua Data Sewa             |");
        System.out.println("| 2. Tampil Data Sewa Yang Belum Lunas  |");
        System.out.println("| 3. Kembali                            |");
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
                case 3:
                    menuDataSewa();
                    break;
                default:
                    System.err.println("Pilihan Salah");
                    break;
            }
        }
        catch(Exception e) {
            System.err.println(e);
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

            eksekusiQuery();
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuTampilSewa();
        }
        catch(Exception e) {
            System.err.println(e);
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

            eksekusiQuery();
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuTampilSewa();
        }
        catch(Exception e) {
            System.err.println(e);
        }  
    } 
    
    public void menuCariSewa() {
        cls.clrscr();
        System.out.format("==========================================%n");
        System.out.format("|              MENU CARI SEWA            |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Cari Data Berdasarkan Nomor Sewa   |");
        System.out.println("| 2. Cari Data Berdasarkan Nama Penyewa |");
        System.out.println("| 3. Cari Data Berdasarkan Nama Baju    |");
        System.out.println("| 4. Kembali                            |");
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
                    cariDataNamaPelanggan();
                    break;
                case 3:
                    cariDataNamaBaju();
                    break;
                case 4: 
                    menuDataSewa();
                    break;
                default:
                    System.out.println("Pilihan Salah");
                    break;
            }
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void cariDataNomorSewa() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("=====================================%n");
            System.out.format("|  CARI DATA SEWA BERDASARKAN NOMOR  | %n");
            System.out.format("+====================================+%n");
            
            System.out.print("Masukkan nomor sewa (S??) :");
            no_sewa = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM detail_sewa "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` WHERE detail_sewa.`no_sewa` ='%s' "
                    + "ORDER BY no_sewa", no_sewa);
            
            rs = db.getStatement().executeQuery(sql);
           
            eksekusiQuery();
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data sewa dengan nomor sewa "+no_sewa+" tidak ditemukan");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuCariSewa();
            
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
        
    public void cariDataNamaPelanggan() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("============================================%n");
            System.out.format("| CARI DATA SEWA BERDASARKAN NAMA PELANGGAN |%n");
            System.out.format("+===========================================+%n");
            
            System.out.print("Masukkan nama penyewa :");
            nama_pelanggan = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM pelanggan "
                    + "INNER JOIN sewa ON pelanggan.`id_pelanggan` = sewa.`id_pelanggan` "
                    + "INNER JOIN detail_sewa ON sewa.`no_sewa` = detail_sewa.`no_sewa` "
                    + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` WHERE pelanggan.`nama` ='%s' "
                    + "ORDER BY no_sewa", nama_pelanggan);
            
            rs = db.getStatement().executeQuery(sql);
            
            eksekusiQuery();  
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data sewa dengan nama pelanggan "+nama_pelanggan+" tidak ditemukan");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuCariSewa();
            
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void cariDataNamaBaju() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|   CARI DATA SEWA BERDASARKAN BAJU  |%n");
            System.out.format("+====================================+%n");
            
            System.out.print("Masukkan nama baju :");
            nama_baju = input.readLine();
            
            String sql = String.format("SELECT detail_sewa.`no_sewa`, pelanggan.`nama`, baju.`nama_baju`, detail_sewa.`qty`, sewa.`tgl_sewa`, sewa.`tgl_kembali`, "
                    + "sewa.`dp_sewa`, sewa.`total_bayar`, sewa.`status` FROM baju "
                    + "INNER JOIN detail_sewa ON baju.`kode_baju` = detail_sewa.`kode_baju` "
                    + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                    + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` WHERE baju.`nama_baju` ='%s' "
                    + "ORDER BY no_sewa", nama_baju);
            
            rs = db.getStatement().executeQuery(sql);
              
            eksekusiQuery();  
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data sewa dengan nama baju "+nama_baju+" tidak ditemukan");
            }
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuCariSewa();
            
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
   
    public void menuUbahSewa() {   
        cls.clrscr();
        System.out.format("==========================================%n");
        System.out.format("|              EDIT DATA SEWA            |%n");
        System.out.format("+========================================+%n");
        System.out.println("| 1. Edit Data Tanggal  Sewa            |");
        System.out.println("| 2. Edit Data DP Sewa                  |");
        System.out.println("| 3. Edit Data Status Sewa              |");
        System.out.println("| 4. Edit Semua Data  Sewa              |");
        System.out.println("| 5. Kembali                            |");
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
                    ubahSemuaDataSewa();
                    break;
                case 5:
                    menuDataSewa();
                    break;
                default:
                    System.err.println("Pilihan Salah");
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
            System.out.format("|       UBAH DATA TANGGAL SEWA       |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa\t  : ");
            no_sewa = input.readLine();

            System.out.print("| Tanggal Sewa \t  : ");
            tgl_sewa= input.readLine();

            System.out.print("| Tanggal Kembali : ");
            tgl_kembali = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `tgl_sewa`='%s', `tgl_kembali`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, tgl_sewa, tgl_kembali, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
            
            String get_id_pelanggan = String.format("SELECT no_sewa FROM sewa where no_sewa = '%s'", no_sewa);
            rs = db.getStatement().executeQuery(get_id_pelanggan);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if ((hasil > 0) && (rs.next() == true)) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else if ((hasil == 0) && (rs.next() == false)) {
                System.out.println("\nNo Sewa " + no_sewa + " tidak ditemukan!");
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuUbahSewa();
        } 
        catch(SQLException e) {
            try {
                System.err.println("\nTanggal yang dimasukkan salah");
                System.out.print("Silahkan masukkan tanggal yang lain!");
                input.readLine();
                menuUbahSewa();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    
    public void ubahDataDPSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|          UBAH DATA DP SEWA          |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa \t    : ");
            no_sewa = input.readLine();

            System.out.print("| Down Payment (DP) : ");
            dp_sewa = Integer.parseInt(input.readLine());

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `dp_sewa`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, dp_sewa, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            String get_id_pelanggan = String.format("SELECT no_sewa FROM sewa where no_sewa = '%s'", no_sewa);
            rs = db.getStatement().executeQuery(get_id_pelanggan);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if ((hasil > 0) && (rs.next() == true)) {
                System.out.println("Status : Data berhasil diubah");
            } 
            else if ((hasil == 0) && (rs.next() == false)) {
                System.out.println("\nNo Sewa " + no_sewa + " tidak ditemukan!");
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuUbahSewa();
        } 
        catch(NumberFormatException e) {
            try {
                System.err.println("\nDP Sewa harus berupa angka!");
                input.readLine();
                menuUbahSewa();
            } catch (IOException err) {
                err.printStackTrace();
            } 
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    
     public void ubahDataStatusSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|        UBAH DATA STATUS SEWA       |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa : ");
            no_sewa = input.readLine();

            System.out.print("| Status     : ");
            status = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `status`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, status, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            String get_id_pelanggan = String.format("SELECT no_sewa FROM sewa where no_sewa = '%s'", no_sewa);
            rs = db.getStatement().executeQuery(get_id_pelanggan);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if ((hasil > 0) && (rs.next() == true)) {
                System.out.println("\nStatus : Data berhasil diubah");
            } 
            else if ((hasil == 0) && (rs.next() == false)) {
                System.out.println("\nNo Sewa " + no_sewa + " tidak ditemukan!");
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuUbahSewa();
        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }
    }
     
    public void ubahSemuaDataSewa()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("===============================================%n");
            System.out.format("|              UBAH SEMUA DATA SEWA            |%n");
            System.out.format("+==============================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| Nomor Sewa \t\t   : ");
            no_sewa = input.readLine();

            System.out.print("| Tgl Sewa (YYYY-MM-DD)    : ");
            tgl_sewa= input.readLine();

            System.out.print("| Tgl Kembali (YYYY-MM-DD) : ");
            tgl_kembali = input.readLine();

            System.out.print("| Down Payment (DP) \t   : ");
            dp_sewa = Integer.parseInt(input.readLine());

            System.out.print("| Status \t\t   : ");
            status = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`sewa` SET `tgl_sewa`='%s', `tgl_kembali`='%s',`dp_sewa`='%s', `status`='%s' WHERE `no_sewa`='%s'";
            sql = String.format(sql, tgl_sewa, tgl_kembali, dp_sewa, status, no_sewa);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
          
            String get_id_pelanggan = String.format("SELECT no_sewa FROM sewa where no_sewa = '%s'", no_sewa);
            rs = db.getStatement().executeQuery(get_id_pelanggan);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if ((hasil > 0) && (rs.next() == true)) {
                System.out.println("\nStatus : Data berhasil diubah");
            } 
            else if ((hasil == 0) && (rs.next() == false)) {
                System.out.println("\nNo Sewa " + no_sewa + " tidak ditemukan!");
                System.out.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuUbahSewa();
        } 
        catch(SQLException e) {
            try {
                System.err.println("\nTanggal yang dimasukkan salah");
                System.out.print("Silahkan masukkan tanggal yang lain!");
                input.readLine();
                menuUbahSewa();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
        catch(NumberFormatException e) {
            try {
                System.err.println("\nDP sewa harus berupa angka!");
                input.readLine();
                menuUbahSewa();
            } catch (IOException err) {
                err.printStackTrace();
            } 
        }
        catch (Exception e) 
        {
            System.err.println(e);
        }
    } 
}
