/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.1.35-MariaDB : Database - evoyproduction
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`evoyproduction` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `evoyproduction`;

/*Table structure for table `baju` */

DROP TABLE IF EXISTS `baju`;

CREATE TABLE `baju` (
  `kode_baju` varchar(5) NOT NULL,
  `nama_baju` varchar(30) DEFAULT NULL,
  `stok_baju` int(11) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`kode_baju`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `baju` */

insert  into `baju`(`kode_baju`,`nama_baju`,`stok_baju`,`harga`) values 
('B001','Adat Aceh',150,90000),
('B002','Adat Batak',125,100000),
('B003','Adat Gorontalo',120,90000),
('B004','Adat Jambi',120,110000),
('B005','Adat Lampung',100,100000),
('B006','Adat Riau',115,110000),
('B007','Adat Minang',105,120000),
('B008','Kebaya Jawa',150,80000),
('B009','Beskap Jawa',150,80000),
('B010','Adat Madura',120,90000),
('B011','Surjan Jogja',115,110000),
('B012','Beskap Sunda',150,80000),
('B013','Pangsi',150,75000),
('B014','Kebaya Sunda',104,90000),
('B015','Adat Bali',117,100000);

/*Table structure for table `detail_sewa` */

DROP TABLE IF EXISTS `detail_sewa`;

CREATE TABLE `detail_sewa` (
  `no_sewa` varchar(5) DEFAULT NULL,
  `kode_baju` varchar(5) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `jml_hari` int(11) DEFAULT NULL,
  `denda` int(11) DEFAULT '0',
  `total_bayar` int(11) DEFAULT '0',
  KEY `kode_baju` (`kode_baju`),
  KEY `no_sewa` (`no_sewa`),
  CONSTRAINT `detail_sewa_ibfk_2` FOREIGN KEY (`kode_baju`) REFERENCES `baju` (`kode_baju`),
  CONSTRAINT `detail_sewa_ibfk_3` FOREIGN KEY (`no_sewa`) REFERENCES `sewa` (`no_sewa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `detail_sewa` */

insert  into `detail_sewa`(`no_sewa`,`kode_baju`,`qty`,`jml_hari`,`denda`,`total_bayar`) values 
(NULL,NULL,2,NULL,0,0),
(NULL,NULL,2,NULL,0,0),
(NULL,NULL,2,NULL,0,0),
(NULL,NULL,NULL,5,0,0),
(NULL,NULL,NULL,5,0,0),
(NULL,NULL,NULL,5,0,0),
('S0003',NULL,NULL,5,0,0),
('S0003',NULL,NULL,5,0,0),
('S0003',NULL,NULL,5,0,270000);

/*Table structure for table `pelanggan` */

DROP TABLE IF EXISTS `pelanggan`;

CREATE TABLE `pelanggan` (
  `id_pelanggan` varchar(5) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `alamat` varchar(30) DEFAULT NULL,
  `kota` varchar(30) DEFAULT NULL,
  `no_tlp` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `pelanggan` */

insert  into `pelanggan`(`id_pelanggan`,`nama`,`alamat`,`kota`,`no_tlp`) values 
('ID001','Rafi Ahmad','Jl. Antapani','Bandung','087212321982'),
('ID002','Rifqi','Jl. Cibiru','Bandung','082912832912'),
('ID004','Akbar','Jl. Sumba','Bandung','987281982321'),
('ID010','Ujang','Jl. Kilang','Medan','098728192832');

/*Table structure for table `sewa` */

DROP TABLE IF EXISTS `sewa`;

CREATE TABLE `sewa` (
  `no_sewa` varchar(5) NOT NULL,
  `id_pelanggan` varchar(5) DEFAULT NULL,
  `tgl_sewa` date DEFAULT NULL,
  `tgl_kembali` date DEFAULT NULL,
  `total_bayar` int(11) DEFAULT NULL,
  `dp_sewa` int(11) DEFAULT '0',
  `status` varchar(15) DEFAULT 'BELUM LUNAS',
  PRIMARY KEY (`no_sewa`),
  KEY `id_pelanggan` (`id_pelanggan`),
  CONSTRAINT `sewa_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sewa` */

insert  into `sewa`(`no_sewa`,`id_pelanggan`,`tgl_sewa`,`tgl_kembali`,`total_bayar`,`dp_sewa`,`status`) values 
('S0002','ID002','2020-02-10','2020-02-12',200000,0,'BELUM LUNAS'),
('S0003','ID002','2020-12-15','2020-12-20',270000,0,'BELUM LUNAS'),
('S0006','ID002','2020-12-10','2020-12-14',200000,0,'BELUM LUNAS'),
('S0008','ID002','2020-10-10','2020-10-15',200000,0,'BELUM LUNAS');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
