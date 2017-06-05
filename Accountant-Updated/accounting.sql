-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 05, 2017 at 11:35 PM
-- Server version: 5.7.11
-- PHP Version: 7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `accounting`
--

-- --------------------------------------------------------

--
-- Table structure for table `major_brand`
--

CREATE TABLE `major_brand` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `major_brand`
--

INSERT INTO `major_brand` (`id`, `name`) VALUES
(2, 'short'),
(3, 'trouser'),
(4, 'socks'),
(5, 'Blazer'),
(1, 'T-shirt');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `barcode` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `quantity` int(11) NOT NULL,
  `customer_price` int(11) NOT NULL,
  `supplier_price` int(11) NOT NULL,
  `major_id` int(11) NOT NULL,
  `sub_id` int(11) NOT NULL,
  `unit_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`barcode`, `name`, `quantity`, `customer_price`, `supplier_price`, `major_id`, `sub_id`, `unit_id`, `supplier_id`) VALUES
('100', 'lacoste', 0, 90, 80, 1, 1, 1, 1),
('101', 'Hatric', 10, 145, 120, 3, 2, 1, 2),
('102', 'polo', 16, 60, 45, 1, 1, 1, 2),
('104', 'lacoste', 20, 40, 30, 1, 1, 1, 1),
('105', 'f', 18, 20, 10, 1, 1, 1, 1),
('106', 'H&M', 9, 145, 100, 3, 2, 1, 1),
('103', 'Converse', 4, 100, 85, 1, 1, 1, 2),
('107', 'Cotonil', 50, 20, 15, 4, 1, 1, 3),
('108', 'Ali', 50, 20, 10, 2, 2, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_bill`
--

CREATE TABLE `purchase_bill` (
  `ssn` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `date_time` varchar(50) NOT NULL,
  `bill_type` varchar(10) NOT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase_bill`
--

INSERT INTO `purchase_bill` (`ssn`, `total`, `date_time`, `bill_type`, `supplier_id`) VALUES
(1, 335, '2016/12/21 20:28:04', 'Cash', 1),
(2, 340, '2016/12/23 19:12:43', 'Cash', 2),
(3, 0, '2016/12/26 14:58:05', 'Cash', 1),
(4, 210, '2017/01/23 15:58:12', 'Cash', 2);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_bill_items`
--

CREATE TABLE `purchase_bill_items` (
  `id` int(11) NOT NULL,
  `product_barcode` int(11) NOT NULL,
  `purchase_bill_ssn` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase_bill_items`
--

INSERT INTO `purchase_bill_items` (`id`, `product_barcode`, `purchase_bill_ssn`) VALUES
(1, 100, 1),
(2, 101, 1),
(3, 102, 1),
(4, 104, 1),
(5, 105, 1),
(6, 110, 1),
(7, 100, 2),
(8, 101, 2),
(9, 102, 2),
(10, 103, 2),
(11, 105, 2),
(12, 100, 3),
(13, 102, 3),
(14, 103, 3),
(15, 100, 4),
(16, 101, 4),
(17, 105, 4);

-- --------------------------------------------------------

--
-- Table structure for table `sales_bill`
--

CREATE TABLE `sales_bill` (
  `ssn` int(11) NOT NULL,
  `total` int(20) NOT NULL,
  `profit` int(20) NOT NULL,
  `date_time` varchar(40) NOT NULL,
  `bill_type` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales_bill`
--

INSERT INTO `sales_bill` (`ssn`, `total`, `profit`, `date_time`, `bill_type`) VALUES
(18, 150, 25, '2016/12/22 21:59:53', 'Cash'),
(16, 1710, 190, '2016/12/21 20:09:51', 'Cash'),
(17, -90, -10, '2016/12/21 20:18:48', 'Returned'),
(15, 90, 10, '2016/12/21 20:05:05', 'Cash'),
(19, 245, 60, '2016/12/24 11:58:05', 'Cash'),
(20, 150, 25, '2016/12/26 15:02:59', 'Cash'),
(21, 290, 55, '2017/01/23 14:24:12', 'Cash'),
(22, 170, 35, '2017/02/10 17:22:27', 'Cash');

-- --------------------------------------------------------

--
-- Table structure for table `sales_bill_items`
--

CREATE TABLE `sales_bill_items` (
  `id` int(11) NOT NULL,
  `product_barcode` int(11) NOT NULL,
  `sales_bill_ssn` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales_bill_items`
--

INSERT INTO `sales_bill_items` (`id`, `product_barcode`, `sales_bill_ssn`) VALUES
(48, 100, 16),
(47, 100, 16),
(46, 100, 16),
(45, 100, 16),
(44, 100, 16),
(43, 100, 16),
(56, 100, 16),
(55, 100, 16),
(54, 100, 16),
(53, 100, 16),
(52, 100, 16),
(51, 100, 16),
(50, 100, 16),
(49, 100, 16),
(58, 100, 16),
(57, 100, 16),
(42, 100, 15),
(59, 100, 16),
(60, 100, 16),
(61, 100, 16),
(62, 100, 17),
(63, 100, 18),
(64, 102, 18),
(65, 106, 19),
(66, 103, 19),
(67, 100, 20),
(68, 102, 20),
(69, 100, 21),
(70, 102, 21),
(71, 105, 21),
(72, 55555, 21),
(73, 102, 22),
(74, 100, 22),
(75, 105, 22);

-- --------------------------------------------------------

--
-- Table structure for table `sub_brand`
--

CREATE TABLE `sub_brand` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sub_brand`
--

INSERT INTO `sub_brand` (`id`, `name`) VALUES
(1, 'shirt'),
(2, 'bagy');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `phone`, `address`) VALUES
(1, 'ali osama', '01097170941', 'badr'),
(2, 'ahmed', '010044065', 'badr'),
(3, 'Mahmoud', '01060413213', 'Shibin El-Kom');

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE `unit` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`id`, `name`) VALUES
(1, 'piece'),
(2, 'cartoon'),
(3, 'none');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `major_brand`
--
ALTER TABLE `major_brand`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD UNIQUE KEY `barcode` (`barcode`);

--
-- Indexes for table `purchase_bill`
--
ALTER TABLE `purchase_bill`
  ADD PRIMARY KEY (`ssn`);

--
-- Indexes for table `purchase_bill_items`
--
ALTER TABLE `purchase_bill_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_bill`
--
ALTER TABLE `sales_bill`
  ADD PRIMARY KEY (`ssn`);

--
-- Indexes for table `sales_bill_items`
--
ALTER TABLE `sales_bill_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sub_brand`
--
ALTER TABLE `sub_brand`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `major_brand`
--
ALTER TABLE `major_brand`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `purchase_bill`
--
ALTER TABLE `purchase_bill`
  MODIFY `ssn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `purchase_bill_items`
--
ALTER TABLE `purchase_bill_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `sales_bill`
--
ALTER TABLE `sales_bill`
  MODIFY `ssn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `sales_bill_items`
--
ALTER TABLE `sales_bill_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;
--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
