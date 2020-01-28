package pboevoy;

import java.io.*;
import java.sql.*;

public class Pembayaran extends ClearScreen{
    
    ResultSet rs;
    ConnectDB db = new ConnectDB("root", "");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    public void bayar_sewa() {
    try {
        db.connect();
        clrscr();
            
        System.out.format("=============================================%n");
        System.out.format("|            PEMBAYARAN SEWA BAJU           |%n");
        System.out.format("+===========================================+%n");

        System.out.print("|  Masukkan No Sewa   : ");
        String no_sewa = input.readLine();
        
        //Perintah query ambil kode_sewa
        String get_no_sewa = String.format("SELECT no_sewa FROM sewa WHERE no_sewa = '%s'", no_sewa);
        
        rs = db.getStatement().executeQuery(get_no_sewa);
        
        if(rs.next() == false) {
            System.out.println("\nNo sewa " + no_sewa +" tidak ditemukan");
            System.out.print("Tekan enter untuk kembali");
            input.readLine();
        }
        
        //Perintah query ambil dp sewa
        String getDP = String.format("SELECT dp_sewa FROM sewa WHERE no_sewa = '%s';", no_sewa);
        
        //Eksekusi query ambil dp sewa
        rs = db.getStatement().executeQuery(getDP);
        
        rs.next();
        int dp_sewa = rs.getInt("dp_sewa");
              
        //Perintah query ambil subtotal
        String getTotal = String.format("SELECT SUM(total_bayar) AS subtotal "
                    + "FROM detail_sewa "
                    + "WHERE no_sewa = '%s'", no_sewa);
        
        //Eksekusi query ambil subtotal    
        rs = db.getStatement().executeQuery(getTotal);

        rs.next();
        int total_harga = rs.getInt("subtotal");
        
        //harga total diambil dari total bayar - dp
        int subtotal = total_harga - dp_sewa;
        
        //Perintah query update status di tabel sewa
        String updateStatus = "UPDATE sewa SET status = 'LUNAS' where no_sewa = '%s'";
        updateStatus = String.format(updateStatus, no_sewa);
          
        //Perintah query list barang yang dibayar
        String bayar = String.format("SELECT pelanggan.`nama`, baju.`nama_baju`, baju.`harga`, detail_sewa.`qty`, detail_sewa.`jml_hari`, "
                + "detail_sewa.`denda`, sewa.`dp_sewa`, detail_sewa.`total_bayar` FROM detail_sewa "
                + "INNER JOIN sewa ON detail_sewa.`no_sewa` = sewa.`no_sewa` "
                + "INNER JOIN baju ON detail_sewa.`kode_baju` = baju.`kode_baju` "
                + "INNER JOIN pelanggan ON sewa.`id_pelanggan` = pelanggan.`id_pelanggan` "
                + "WHERE detail_sewa.`no_sewa` = '%s';", no_sewa);
        
        //Eksekusi query pembayaran
        rs = db.getStatement().executeQuery(bayar);
        
        //Tampilan query pembayaran
        String tbl ="| %-13s | %-3d | %-9d | %-4d | %-7d |%-7d | %-7d |%n";

        System.out.println("=======================================================================");
        System.out.println("                            PEMBAYARAN SEWA BAJU                       ");
        System.out.println("                               EVOY PRODUCTION                         ");
        System.out.println("No Sewa : " + no_sewa);
        System.out.format("+===============+=====+===========+======+========+=========+=========%n");
        System.out.format("|   Nama Baju   | Qty |   Harga   | Lama |   DP   |  Denda  |  Total  |%n");
        System.out.format("+===============+=====+===========+======+========+=========+=========+%n");
            
        while(rs.next()) {
            String nama_baju = rs.getString("nama_baju");
            int qty = rs.getInt("qty");
            int harga = rs.getInt("harga");
            int lama = rs.getInt("jml_hari");
            int dp = rs.getInt("dp_sewa");
            int denda = rs.getInt("denda");
            int total = rs.getInt("total_bayar");
            
            System.out.format(tbl, nama_baju, qty, harga, lama, dp, denda, total);
        }

        System.out.format("+===============+=====+===========+======+========+=========+=========+%n");
        System.out.println("\t\t\t\t\t      Harga Total : Rp. " + total_harga );
        System.out.println("\t\t\t\t\t      DP Sewa     : Rp. " + dp_sewa );
        System.out.format("-----------------------------------------------------------------------%n");
        System.out.println("\t\t\t\t\t      Subtotal    : Rp. " + subtotal );
        System.out.format("=======================================================================%n");

        System.out.print("Apakah anda ingin membayar y/n ? ");
        String pilihan = input.readLine();

        switch (pilihan) {
            case "y":
                System.out.println("=======================");
                System.out.println("   Pembayaran Sukses   ");
                System.out.println("      TERIMA KASIH     ");
                System.out.println("=======================");
                db.getStatement().execute(updateStatus);
            break;
            case "n":
                System.err.println("Pembayaran Gagal !");
            break;
            default:
                System.err.println("Pilihan salah");
            break;              
        }   
        System.out.print("Tekan enter untuk kembali");
        input.readLine();
    }
    catch(Exception e) {
        System.out.println(e);
    }
}
}
