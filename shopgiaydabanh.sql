-- phpMyAdmin SQL Dump
-- version 6.0.0-dev+20251015.833796a1df
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 13, 2025 at 09:16 AM
-- Server version: 8.4.3
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopgiaydabanh`
--

-- --------------------------------------------------------

--
-- Table structure for table `binhluan1`
--

CREATE TABLE `binhluan1` (
  `mabl` int NOT NULL,
  `mahh` int NOT NULL,
  `makh` int NOT NULL,
  `ngaybl` date NOT NULL,
  `noidung` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `idcomment` int NOT NULL,
  `idkh` int NOT NULL,
  `idhanghoa` int NOT NULL,
  `content` text NOT NULL,
  `luotthich` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `cthanghoa`
--

CREATE TABLE `cthanghoa` (
  `idhanghoa` int NOT NULL,
  `idmau` int NOT NULL,
  `idsize` int NOT NULL,
  `dongia` float NOT NULL,
  `soluongton` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `cthanghoa`
--

INSERT INTO `cthanghoa` (`idhanghoa`, `idmau`, `idsize`, `dongia`, `soluongton`) VALUES
(1, 1, 1, 3500000, 10),
(1, 1, 2, 3500000, 10),
(1, 4, 3, 3500000, 5),
(2, 2, 2, 2800000, 15),
(2, 2, 3, 2800000, 15),
(2, 3, 2, 2800000, 8),
(3, 5, 1, 2500000, 12),
(3, 5, 2, 2500000, 12),
(4, 3, 3, 3200000, 7),
(4, 4, 3, 3200000, 7),
(5, 1, 1, 3500000, 10),
(6, 1, 2, 3500000, 10),
(7, 4, 1, 3500000, 5),
(8, 2, 2, 2800000, 15),
(9, 2, 3, 2800000, 15),
(10, 3, 2, 2800000, 8),
(11, 5, 1, 2500000, 12),
(12, 3, 2, 2500000, 12),
(13, 3, 3, 3200000, 7),
(14, 4, 3, 3200000, 7),
(15, 1, 1, 3500000, 10),
(16, 1, 2, 3500000, 10),
(17, 4, 1, 3500000, 5),
(18, 2, 2, 2800000, 15),
(19, 2, 3, 2800000, 15),
(20, 3, 2, 2800000, 8),
(21, 5, 1, 2500000, 12),
(22, 5, 2, 2500000, 12),
(23, 3, 3, 3200000, 7),
(24, 4, 3, 3200000, 7),
(25, 1, 2, 3500000, 10),
(26, 4, 1, 3500000, 5),
(27, 3, 2, 2800000, 15),
(28, 2, 3, 2800000, 15),
(29, 3, 2, 2800000, 8),
(30, 5, 1, 2500000, 12),
(31, 5, 2, 2500000, 12),
(32, 3, 3, 3200000, 7);

-- --------------------------------------------------------

--
-- Table structure for table `cthoadon`
--

CREATE TABLE `cthoadon` (
  `masohd` int NOT NULL,
  `mahh` int NOT NULL,
  `soluongmua` int NOT NULL,
  `mausac` varchar(20) NOT NULL,
  `size` int NOT NULL,
  `thanhtien` int NOT NULL,
  `tinhtrang` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `hanghoa`
--

CREATE TABLE `hanghoa` (
  `mahh` int NOT NULL,
  `tenhh` varchar(60) NOT NULL,
  `giamgia` float NOT NULL,
  `hinh` varchar(50) NOT NULL,
  `maloai` int NOT NULL,
  `mathuonghieu` int DEFAULT NULL,
  `dacbiet` bit(1) NOT NULL,
  `soluotxem` int NOT NULL,
  `ngaylap` date NOT NULL,
  `mota` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `hanghoa`
--

INSERT INTO `hanghoa` (`mahh`, `tenhh`, `giamgia`, `hinh`, `maloai`, `mathuonghieu`, `dacbiet`, `soluotxem`, `ngaylap`, `mota`) VALUES
(1, 'Adidas Top Sala Competion', 0, 'giay1-IC.jpg', 3, 2, b'1', 170, '2025-10-01', 'Giày đá bóng trong nhà Adidas Top Sala Competition với thiết kế nhẹ, đế phẳng chống trơn trượt và khả năng kiểm soát bóng tối ưu.'),
(2, 'Adidas X 19.1 IN Mutator', 0, 'giay2-IC.jpg', 3, 2, b'1', 123, '2025-10-02', 'Adidas X 19.1 Mutator phiên bản indoor, thiết kế hiện đại, upper mỏng nhẹ giúp cảm giác bóng chân thực và linh hoạt.'),
(3, 'Adidas X Speedflow ', 300000, 'giay3-IC.jpg', 3, 2, b'0', 80, '2025-09-20', 'Giày Adidas X Speedflow .1 với công nghệ Speedframe giúp tăng tốc nhanh và ôm chân cực kỳ thoải mái.\r\n'),
(4, 'Adidas Predator 25 League', 0, 'giay4-TF.jpg', 2, 2, b'0', 96, '2025-09-25', 'Adidas Predator 25 League mang lại khả năng kiểm soát bóng tối đa, phù hợp cho cầu thủ chơi kiểm soát.'),
(5, 'Adidas F50 League', 0, 'giay5-TF.jpg', 2, 2, b'0', 96, '2025-09-25', 'Dòng giày Adidas F50 League nổi bật với thiết kế gọn nhẹ, hỗ trợ tốc độ và di chuyển linh hoạt.'),
(6, 'Adidas Predator 25 Pro', 0, 'giay6-TF.jpg', 2, 2, b'1', 1, '2025-10-01', 'Phiên bản cao cấp Adidas Predator 25 Pro, chất liệu upper Controlskin giúp kiểm soát bóng hoàn hảo.'),
(7, 'Adidas Predator HG', 0, 'giay7-FG.jpg', 1, 2, b'1', 0, '2025-10-02', 'Giày đá banh Adidas Predator HG, thiết kế cho sân cỏ cứng, ôm chân, hỗ trợ kiểm soát bóng và bứt tốc tối đa.'),
(8, 'Adidas F50 Elite', 0, 'giay3-IC.jpg', 1, 2, b'0', 0, '2025-09-20', 'Giày đá banh cao cấp dành cho sân cỏ tự nhiên, thiết kế siêu nhẹ giúp bứt tốc, hỗ trợ tối đa khi di chuyển và sút bóng.'),
(9, 'Adidas Predator FG', 0, 'giay9-FG.jpg', 1, 2, b'0', 0, '2025-09-25', 'Giày đá bóng nổi bật với thiết kế gọn nhẹ, hỗ trợ tốc độ và di chuyển linh hoạt.'),
(10, 'Mizuno Morelia Neo IV Beta Elite', 0, 'mizuno5.jpg', 1, 4, b'0', 0, '2025-10-06', 'Giày Mizuno Morelia Neo IV Beta Elite với trọng lượng siêu nhẹ và upper da Kangaroo cao cấp.'),
(11, 'Mizuno Alpha II Elite', 0, 'mizuno6.jpg', 1, 4, b'0', 0, '2025-09-25', 'Mizuno Alpha II Elite mang lại sự cân bằng giữa tốc độ và độ ổn định khi thi đấu.'),
(12, 'Mizuno Morelia Neo Sala β JAPAN', 100000, 'mizuno3.jpg', 2, 4, b'0', 3, '2025-10-06', 'Giày futsal cao cấp Mizuno Morelia Neo Sala Beta Japan, chế tác thủ công tại Nhật, đem lại cảm giác bóng tuyệt hảo.'),
(13, 'Mizuno Morelia Sala Pro', 0, 'mizuno4.jpg', 2, 4, b'0', 0, '2025-09-25', 'Giày Mizuno Morelia Sala Pro được ưa chuộng bởi sự bền bỉ, thoải mái và khả năng kiểm soát bóng tốt.'),
(14, 'Mizuno Morelia Sala Elite In', 0, 'mizuno1.jpg', 3, 4, b'0', 0, '2025-10-06', 'Mizuno Morelia Sala Elite IN được thiết kế cho cầu thủ futsal chuyên nghiệp, da mềm mại và độ bám sàn tuyệt vời.'),
(15, 'Mizuno Monarcida Neo', 0, 'mizuno2.jpg', 2, 4, b'0', 0, '2025-09-25', 'Giày được ưa chuộng bởi sự bền bỉ, thoải mái và khả năng kiểm soát bóng tốt.'),
(16, 'Nike Air Zoom Mercurial Vapor 16 Elite', 0, 'nike1.jpg', 1, 1, b'0', 0, '2025-09-25', 'Nike Air Zoom Mercurial Vapor 16 Elite với công nghệ Zoom Air mang đến cảm giác bật nhạy và tốc độ vượt trội.'),
(17, 'Nike Air Zoom Mercurial Vapor 16 Pro', 0, 'nike2.jpg', 1, 1, b'0', 0, '2025-10-06', 'Phiên bản Pro của Nike Vapor 16 mang lại cảm giác ôm chân và khả năng tăng tốc mạnh mẽ.'),
(18, ' Nike Tiempo Legend 10 Academy', 100000, 'nike3.jpg', 1, 1, b'0', 1, '2025-09-25', ' Giày Nike Tiempo Legend 10 Academy với upper FlyTouch Lite mềm mại, hỗ trợ kiểm soát bóng tối đa.'),
(19, 'Nike Luna Gato II', 0, 'nike4.jpg', 3, 1, b'0', 0, '2025-09-25', 'Nike Lunar Gato II là lựa chọn hàng đầu cho cầu thủ futsal, với đế Lunar mềm mại và độ bám cao.'),
(20, 'Nike Tiempo Legend 10 Academy', 0, 'nike5.jpg', 3, 1, b'0', 0, '2025-10-06', 'Giày Nike Tiempo Legend 10 Academy với upper FlyTouch Lite mềm mại, hỗ trợ kiểm soát bóng tối đa.'),
(21, 'Nike Tiempo Legend 10 Pro Academy', 0, 'nike8.jpg', 2, 1, b'0', 0, '2025-09-25', 'iày Nike Tiempo Legend 10 Academy với upper FlyTouch Lite mềm mại, hỗ trợ kiểm soát bóng tối đa.'),
(22, 'Nike Phantom 6 Low Academy', 0, 'nike7.jpg', 2, 1, b'0', 0, '2025-09-25', 'Giày Nike Phantom 6 Low Academy với form ôm chân và khả năng kiểm soát bóng chính xác.'),
(23, 'Nike Zoom Mercurial Superfly 10 Academy', 0, 'nike9.jpg', 2, 1, b'0', 0, '2025-10-06', 'hiên bản Superfly 10 Academy tích hợp cổ cao Dynamic Fit và đệm Zoom Air giúp di chuyển nhanh nhẹn.\r\n'),
(24, 'Nike Street Gato', 0, 'nike6.jpg', 3, 1, b'0', 0, '2025-09-25', 'Giày Nike Street Gato mang phong cách đường phố, phù hợp chơi bóng tự do và futsal.'),
(25, 'Puma Ultra 1.3 Pro Court', 0, 'puma1.jpg', 3, 3, b'0', 0, '2025-10-06', 'Giày PUMA Ultra 1.3 Pro Court nhẹ và bền, phù hợp thi đấu futsal cường độ cao.'),
(26, 'Puma Ultra 6 Pro Cage', 0, 'puma2.jpg', 3, 3, b'0', 0, '2025-09-25', 'Giày Puma Ultra 6 Pro Cage nhẹ và bền, phù hợp thi đấu futsal cường độ cao.'),
(27, 'Puma Future 8 Pro Cage', 0, 'puma3.jpg', 2, 3, b'0', 2, '2025-10-06', 'PUMA Future 8 Pro Cage với dây Netfit linh hoạt, giúp ôm chân và kiểm soát bóng tốt hơn.'),
(28, 'Puma Ultra 6 Pro Cage Blue', 0, 'puma4.jpg', 2, 3, b'0', 0, '2025-09-25', 'Giày PUMA Ultra 6 Pro Cage nổi bật với thiết kế tốc độ, phần đế cao su chống trượt cực tốt.'),
(29, 'Puma Ultra 6 Match Re-Charge TT', 0, 'puma5.jpg', 2, 3, b'0', 0, '2025-10-06', 'PUMA Ultra 6 Match TT với công nghệ Re-Charge giúp hoàn trả năng lượng khi chạy, phù hợp sân cỏ nhân tạo.'),
(30, 'Puma Ultra 5 Ultimate', 0, 'puma6.jpg', 1, 3, b'0', 0, '2025-09-25', 'Giày PUMA Ultra 5 Ultimate với upper siêu nhẹ và đế Speedplate mang lại tốc độ vượt trội trên sân.\r\n'),
(31, 'Puma Ultra Match LL ', 150000, 'puma7.jpg', 1, 3, b'0', 4, '2025-10-06', 'Puma Ultra Match LL với dây Netfit linh hoạt, giúp ôm chân và kiểm soát bóng tốt hơn.'),
(32, 'Puma Ultra 6 Pro Cage Blue', 0, 'puma4.jpg', 2, 3, b'0', 0, '2025-09-25', 'Giày PUMA Ultra 6 Pro Cage nổi bật với thiết kế tốc độ, phần đế cao su chống trượt cực tốt.');

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `masohd` int NOT NULL,
  `makh` int NOT NULL,
  `ngaydat` date NOT NULL,
  `tongtien` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `makh` int NOT NULL,
  `tenkh` varchar(50) NOT NULL,
  `username` varchar(25) NOT NULL,
  `matkhau` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `diachi` text,
  `sodienthoai` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`makh`, `tenkh`, `username`, `matkhau`, `email`, `diachi`, `sodienthoai`) VALUES
(1, 'tú trần', 'tutran', '8f8e2909a8f683c31159ee51d593a642', 'tu@gmail.com', 'hcm', '9090789678'),
(2, 'minh minh', 'minhminh', '8f8e2909a8f683c31159ee51d593a642', 'minh@gmail.com', 'bình định', '90907896789'),
(3, 'teo', 'teoteo', '3ff19fad9f5844248f601ab23381cc88', 'teo123@gmail.com', 'hcm', '9090789698'),
(4, 'ý nhi', 'nhinhi', '87f038af05196e3dfa958a521f6f400e', 'ngvynhi.itc@gmail.com', 'thoại ngọc hầu', '9090789699'),
(5, 'an new', 'namnam', '206a93184bcd24a5625312acf1a03922', 'nam@gmail.com', 'hcm', '13245'),
(6, 'an an', 'namnamnew', 'af57f975857768de31f3c083a1c163cc', 'chautrantrucly@gmail.com', 'hcm', '13245'),
(7, 'tuấn', 'tuantuan', '206a93184bcd24a5625312acf1a03922', 'tuan@gmail.com', 'hcm', '23455'),
(8, 'thúy', 'thuybui', '206a93184bcd24a5625312acf1a03922', 'thuy@gmail.com', 'hcm', '124324234'),
(9, 'nam anh', 'namnamanh', '206a93184bcd24a5625312acf1a03922', 'namanh@gmail.com', 'hcm', '123456'),
(10, 'Huỳnh Ngọc Hà', 'hnh6625', '$2a$10$8zhomFSpsdwP9iKBzv7GT.ygCfxuwFhV7q..vCYNAefXhvV5J8QlK', 'huynhha6625@gmail.com', 'fgg', '0902540103');

-- --------------------------------------------------------

--
-- Table structure for table `loai`
--

CREATE TABLE `loai` (
  `maloai` int NOT NULL,
  `tenloai` varchar(50) NOT NULL,
  `idmenu` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `loai`
--

INSERT INTO `loai` (`maloai`, `tenloai`, `idmenu`) VALUES
(1, 'Sân Cỏ Tự Nhiên (FG)', 3),
(2, 'Sân Cỏ Nhân Tạo (TF)', 3),
(3, 'Futsal (IC)', 3);

-- --------------------------------------------------------

--
-- Table structure for table `mausac`
--

CREATE TABLE `mausac` (
  `Idmau` int NOT NULL,
  `mausac` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `mausac`
--

INSERT INTO `mausac` (`Idmau`, `mausac`) VALUES
(1, 'Đỏ'),
(2, 'Đen'),
(3, 'Trắng'),
(4, 'Xanh Dương'),
(5, 'Vàng');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `idnv` int NOT NULL,
  `hoten` varchar(250) NOT NULL,
  `dia` text NOT NULL,
  `username` varchar(250) NOT NULL,
  `matkhau` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`idnv`, `hoten`, `dia`, `username`, `matkhau`) VALUES
(1, 'nguyễn hạo vy', 'hcm', 'admin', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `sizegiay`
--

CREATE TABLE `sizegiay` (
  `Idsize` int NOT NULL,
  `size` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `sizegiay`
--

INSERT INTO `sizegiay` (`Idsize`, `size`) VALUES
(1, 40),
(2, 41),
(3, 42),
(4, 43),
(5, 44);

-- --------------------------------------------------------

--
-- Table structure for table `thuonghieu`
--

CREATE TABLE `thuonghieu` (
  `mathuonghieu` int NOT NULL,
  `tenthuonghieu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `thuonghieu`
--

INSERT INTO `thuonghieu` (`mathuonghieu`, `tenthuonghieu`) VALUES
(1, 'Nike'),
(2, 'Adidas'),
(3, 'Puma'),
(4, 'Mizuno');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `binhluan1`
--
ALTER TABLE `binhluan1`
  ADD PRIMARY KEY (`mabl`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`idcomment`);

--
-- Indexes for table `cthanghoa`
--
ALTER TABLE `cthanghoa`
  ADD PRIMARY KEY (`idhanghoa`,`idmau`,`idsize`);

--
-- Indexes for table `cthoadon`
--
ALTER TABLE `cthoadon`
  ADD PRIMARY KEY (`masohd`,`mahh`);

--
-- Indexes for table `hanghoa`
--
ALTER TABLE `hanghoa`
  ADD PRIMARY KEY (`mahh`),
  ADD KEY `fk_hanghoa_thuonghieu` (`mathuonghieu`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`masohd`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`makh`);

--
-- Indexes for table `loai`
--
ALTER TABLE `loai`
  ADD PRIMARY KEY (`maloai`);

--
-- Indexes for table `mausac`
--
ALTER TABLE `mausac`
  ADD PRIMARY KEY (`Idmau`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`idnv`);

--
-- Indexes for table `sizegiay`
--
ALTER TABLE `sizegiay`
  ADD PRIMARY KEY (`Idsize`);

--
-- Indexes for table `thuonghieu`
--
ALTER TABLE `thuonghieu`
  ADD PRIMARY KEY (`mathuonghieu`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `binhluan1`
--
ALTER TABLE `binhluan1`
  MODIFY `mabl` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `idcomment` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hanghoa`
--
ALTER TABLE `hanghoa`
  MODIFY `mahh` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `masohd` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `makh` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `loai`
--
ALTER TABLE `loai`
  MODIFY `maloai` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `mausac`
--
ALTER TABLE `mausac`
  MODIFY `Idmau` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `idnv` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sizegiay`
--
ALTER TABLE `sizegiay`
  MODIFY `Idsize` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `thuonghieu`
--
ALTER TABLE `thuonghieu`
  MODIFY `mathuonghieu` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hanghoa`
--
ALTER TABLE `hanghoa`
  ADD CONSTRAINT `fk_hanghoa_thuonghieu` FOREIGN KEY (`mathuonghieu`) REFERENCES `thuonghieu` (`mathuonghieu`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
