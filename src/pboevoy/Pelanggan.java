package pboevoy;

import java.io.*;
import java.sql.*;

public class Pelanggan {
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    ClearScreen cls = new ClearScreen();
    String id_pelanggan, nama, alamat, kota, no_tlp;
    
    public void menuPelanggan() {
        cls.clrscr();
        System.out.format("=====================================%n");
        System.out.format("|          SEWA BAJU ADAT           |%n");
        System.out.format("|         EVOY PRODUCTION           |%n");
        System.out.format("=====================================%n");
        System.out.println("|   1. Tambah Data Pelanggan        |");
        System.out.println("|   2. Tampilkan Data Pelanggan     |");
        System.out.println("|   3. Ubah Data Pelanggan          |");
        System.out.println("|   4. Cari Data Pelanggan          |");
        System.out.println("|   5. Hapus Data Pelanggan         |");
        System.out.println("|   6. Kembali                      |");
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
                    menuCariPelanggan();
                    break;
                case 5:
                    hapusDataPelanggan();
                    break;
                case 6:
                default:
                    System.out.println("Pilihan salah");
                    break;
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
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|        TAMBAH DATA PELANGGAN       |%n");
            System.out.format("+====================================+%n");
            
            System.out.print("|  ID_Pelanggan : ");
            id_pelanggan = input.readLine();
              
            System.out.print("|  Nama \t: ");
            nama = input.readLine();
            
            System.out.print("|  Alamat \t: ");
            alamat = input.readLine();
            
            System.out.print("|  Kota \t: ");
            kota = input.readLine();
            
            System.out.print("|  No. Tlp \t: ");
            no_tlp = input.readLine();
            
            String sql = "INSERT INTO `evoyproduction`.`pelanggan` (`id_pelanggan`, `Nama`, `Alamat`, `kota`, `no_tlp`) "
                    + "VALUES ('%s', '%s', '%s', '%s', '%s');";
            sql = String.format(sql, id_pelanggan, nama, alamat, kota, no_tlp);
                       
            int intBaris = db.getStatement().executeUpdate(sql);
                if ( intBaris > 0 ) {
                    System.out.println("\nPenambahan Data berhasil");
                }
                else {
                    System.err.println("Penambahan Data gagal");
                }
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
        }
        catch(SQLIntegrityConstraintViolationException e) {
            try {
                System.err.println("\nID Pelanggan " + id_pelanggan + " sudah ada");
                System.out.print("Silahkan masukkan ID yang lain!");
                input.readLine();
                menuPelanggan();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
        catch(SQLException e) {
            try {
                System.err.println("\nID Pelanggan terlalu panjang!");
                System.out.print("Silahkan masukkan ID yang lain!");
                input.readLine();
                menuPelanggan();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilDataPelanggan() 
    {   
        try {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            String sql = "SELECT * FROM pelanggan";
            rs = db.getStatement().executeQuery(sql);

            String tbl ="| %-12s | %-12s | %-31s | %-8s | %-13s | %n";

            System.out.format("==========================================================================================+%n");
            System.out.format("|                               Data Pelanggan Evoy Production                            |%n");
            System.out.format("+==============+==============+=================================+==========+===============+%n");
            System.out.format("| ID_Pelanggan |     Nama     |     \t    Alamat  \t \t|   Kota   |    No. Tlp    |%n");
            System.out.format("+==============+==============+=================================+==========+===============+%n");
            
            while(rs.next()) {

               id_pelanggan = rs.getString("id_pelanggan");
               nama = rs.getString("nama");
               alamat = rs.getString("alamat");
               kota = rs.getString("kota");
               no_tlp = rs.getString("no_tlp");

               System.out.format(tbl, id_pelanggan, nama, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+==================================+==========+==============+%n");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
        }
        catch(Exception e) {
            System.err.println(e);
        }  
    }
   
    public void ubahDataPelanggan()
    {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|          UBAH DATA PELANGGAN       |%n");
            System.out.format("+====================================+%n");
        
        try 
        {  
            // Input dari user
            System.out.print("| ID Pelanggan Yang Di Edit : ");
            id_pelanggan = input.readLine();

            System.out.print("| Edit Nama \t: ");
            nama= input.readLine();

            System.out.print("| Edit Alamat   : ");
            alamat = input.readLine();

            System.out.print("| Edit Kota \t: ");
            kota = input.readLine();

            System.out.print("| Edit No. Telp : ");
            no_tlp = input.readLine();

            // Perintah query update
            String sql = "UPDATE `evoyproduction`.`pelanggan` SET `nama`='%s', `alamat`='%s', `kota`='%s', `no_tlp`='%s' WHERE `id_pelanggan`='%s'";
            sql = String.format(sql, nama, alamat, kota, no_tlp, id_pelanggan);

            // Menyimpan nilai hasil queri
            int hasil = db.getStatement().executeUpdate(sql);
            
            String get_id_pelanggan = String.format("SELECT id_pelanggan FROM pelanggan where id_pelanggan = '%s'", id_pelanggan);
            rs = db.getStatement().executeQuery(get_id_pelanggan);
          
            //Jika ada baris yang terpengaruh dari hasil query
            if ((hasil > 0) && (rs.next() == true)) {
                System.out.println("\nStatus : Data berhasil diubah");
            } 
            else if ((hasil == 0) && (rs.next() == false)) {
                System.err.println("\nID Pelanggan " + id_pelanggan + " tidak ditemukan!");
                System.err.println("Status : Data gagal diubah");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }
    }
    
    public void menuCariPelanggan() {
        cls.clrscr();
        System.out.format("======================================%n");
        System.out.format("|           CARI DATA PELANGGAN       |%n");
        System.out.format("+=====================================+%n");
        System.out.println("| 1. Cari Pelanggan berdasarkan ID   |");
        System.out.println("| 2. Cari Pelanggan berdasarkan Nama |");
        System.out.println("| 3. Cari Pelanggan berdasarkan Kota |");
        System.out.println("| 4. Kembali                         |");
        System.out.println("| 0. Keluar                          |");
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
                    cariDataPelangganID();
                    break;
                case 2:
                    cariDataPelangganNama();
                    break;
                case 3:
                    cariDataPelangganKota();
                    break;
                case 4:
                    menuPelanggan();
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
    
    public void cariDataPelangganID() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("| CARI DATA PELANGGAN BERDASARKAN ID |%n");
            System.out.format("+====================================+%n");
            
            System.out.print("Masukkan ID :");
            id_pelanggan = input.readLine();
              
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
                nama = rs.getString("nama");
                alamat = rs.getString("alamat");
                kota = rs.getString("kota");
                no_tlp = rs.getString("no_tlp");
                
                System.out.format(tbl, id_pelanggan, nama, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+================+==========+==============+%n"); 
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data pelanggan dengan ID "+id_pelanggan+" tidak ditemukan");
            }
           
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();       
        }
        catch(Exception e) {
            System.err.println(e);
        }       
    }
    
    public void cariDataPelangganNama() {
        try {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("=======================================%n");
            System.out.format("| CARI DATA PELANGGAN BERDASARKAN NAMA |%n");
            System.out.format("+======================================+%n");
            
            System.out.print("Masukkan Nama : ");
            nama = input.readLine();
              
            String sql = String.format("SELECT * FROM pelanggan WHERE `nama` ='%s'", nama);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-12s | %-12s | %-14s | %-8s | %-12s | %n";

            System.out.format("=========================================================================%n");
            System.out.format("|                       Data Pelanggan Evoy Production                   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            System.out.format("| ID_Pelanggan |     Nama     |     Alamat     |   Kota   |    No. Tlp   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            
            while(rs.next()) {
                id_pelanggan = rs.getString("id_pelanggan");
                nama = rs.getString("nama");
                alamat = rs.getString("alamat");
                kota = rs.getString("kota");
                no_tlp = rs.getString("no_tlp");
                
                System.out.format(tbl, id_pelanggan, nama, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+================+==========+==============+%n"); 
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data pelanggan dengan nama "+nama+" tidak ditemukan");
            }
           
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
            
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public void cariDataPelangganKota() 
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("=======================================%n");
            System.out.format("| CARI DATA PELANGGAN BERDASARKAN KOTA |%n");
            System.out.format("+======================================+%n");
            
            System.out.print("Masukkan Kota : ");
            kota = input.readLine();
                
            String sql = String.format("SELECT * FROM pelanggan WHERE kota ='%s'", kota);
            
            rs = db.getStatement().executeQuery(sql);
            
            String tbl ="| %-12s | %-12s | %-14s | %-8s | %-12s | %n";

            System.out.format("=========================================================================%n");
            System.out.format("|                       Data Pelanggan Evoy Production                   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            System.out.format("| ID_Pelanggan |     Nama     |     Alamat     |   Kota   |    No. Tlp   |%n");
            System.out.format("+==============+==============+================+==========+==============+%n");
            
            while(rs.next()) {
                id_pelanggan = rs.getString("id_pelanggan");
                nama = rs.getString("nama");
                alamat = rs.getString("alamat");
                kota = rs.getString("kota");
                no_tlp = rs.getString("no_tlp");
                
                System.out.format(tbl, id_pelanggan, nama, alamat, kota, no_tlp);
            }
            System.out.format("+==============+==============+================+==========+==============+%n"); 
            
            // Open new statement for resultset
            rs = db.getStatement().executeQuery(sql);
            
            if(rs.next() == false ) {
                System.out.println("Data pelanggan dari kota "+kota+" tidak ditemukan");
            }
            
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
            
        }
        catch(Exception e) {
            System.out.println(e);
        }       
    }
    
    public void hapusDataPelanggan()
    {
        try 
        {
            db.connect();
            cls.clrscr(); //Method clear screen
            
            System.out.format("======================================%n");
            System.out.format("|        HAPUS DATA PELANGGAN         |%n");
            System.out.format("+====================================+%n");
            
            //Input user
            System.out.print("| ID Pelanggan yang mau dihapus: ");
            id_pelanggan = input.readLine();
            
            //Perintah query hapus data
            String sql = String.format("DELETE FROM pelanggan WHERE id_pelanggan = '%s'", id_pelanggan);
            
            //Eksekusi query hapus data
            int hasil = db.getStatement().executeUpdate(sql);
            
            if(hasil > 0) {
                System.out.println("\nData berhasil dihapus");
            } else {
                System.err.println("\nData gagal dihapus");
            }
             
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
            menuPelanggan();
        } 
        catch(SQLIntegrityConstraintViolationException e) {
            try {
                System.err.println("\nID Pelanggan " + id_pelanggan + " tidak bisa dihapus");
                System.out.print("Silahkan masukkan ID yang lain!");
                input.readLine();
                menuPelanggan();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
        catch (Exception e) 
        {
            System.err.println(e);
        }
    }
}
