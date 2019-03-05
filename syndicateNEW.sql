/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50508
Source Host           : localhost:3306
Source Database       : syndicate

Target Server Type    : MYSQL
Target Server Version : 50508
File Encoding         : 65001

Date: 2016-03-02 16:18:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `acces_privilages`
-- ----------------------------
DROP TABLE IF EXISTS `acces_privilages`;
CREATE TABLE `acces_privilages` (
  `acces_privilages_id` int(11) NOT NULL AUTO_INCREMENT,
  `adminPanel` tinyint(1) DEFAULT NULL,
  `adminStudentDetails` tinyint(1) DEFAULT NULL,
  `adminAttendanceDetails` tinyint(1) DEFAULT NULL,
  `adminEmployeeDetails` tinyint(1) DEFAULT NULL,
  `adminExpenses` tinyint(1) DEFAULT NULL,
  `adminTeacherDetails` tinyint(1) DEFAULT NULL,
  `adminSalaryInvoice` tinyint(1) DEFAULT NULL,
  `adminIncomeDetails` tinyint(1) DEFAULT NULL,
  `studentRegistration` tinyint(1) DEFAULT NULL,
  `expenses` tinyint(1) DEFAULT NULL,
  `expensesTeacherPayments` tinyint(1) DEFAULT NULL,
  `expensesSalary` tinyint(1) DEFAULT NULL,
  `expensesOtherPayments` tinyint(1) DEFAULT NULL,
  `employeeRegistration` tinyint(1) DEFAULT NULL,
  `teacherRegistration` tinyint(1) DEFAULT NULL,
  `userRegistration` tinyint(1) DEFAULT NULL,
  `classDetails` tinyint(1) DEFAULT NULL,
  `teacherTimetable` tinyint(1) DEFAULT NULL,
  `mainTimetable` tinyint(1) DEFAULT NULL,
  `attendance` tinyint(1) DEFAULT NULL,
  `leaves` tinyint(1) DEFAULT NULL,
  `studentPayments` tinyint(1) DEFAULT NULL,
  `jobTitle` tinyint(1) DEFAULT NULL,
  `subject` tinyint(1) DEFAULT NULL,
  `qualification` tinyint(1) DEFAULT NULL,
  `backup` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`acces_privilages_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acces_privilages
-- ----------------------------
INSERT INTO `acces_privilages` VALUES ('1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `acces_privilages` VALUES ('2', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '1', '0', '0', '0', '1', '1', '1', '0', '0', '1', '0', '1', '0', '0');
INSERT INTO `acces_privilages` VALUES ('3', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '1', '0', '1', '0', '0');

-- ----------------------------
-- Table structure for `admin_panel`
-- ----------------------------
DROP TABLE IF EXISTS `admin_panel`;
CREATE TABLE `admin_panel` (
  `admin_id` varchar(5) NOT NULL,
  `company_email` varchar(45) DEFAULT NULL,
  `company_mobile` int(10) DEFAULT NULL,
  `company_office` int(10) DEFAULT NULL,
  `student_reg_fee` double DEFAULT NULL,
  `default_leaves` int(11) DEFAULT NULL,
  `employee_salary_payday` double DEFAULT NULL,
  `etf/epf_employee` varchar(5) DEFAULT NULL,
  `etf/epf_company` varchar(5) DEFAULT NULL,
  `commision_rate` double DEFAULT NULL,
  `user_type_id` varchar(10) NOT NULL,
  PRIMARY KEY (`admin_id`),
  KEY `fk_admin_panel_user_type1_idx` (`user_type_id`),
  CONSTRAINT `fk_admin_panel_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_panel
-- ----------------------------
INSERT INTO `admin_panel` VALUES ('1', 'company@gmail.com', '712341234', '812345654', '500', '24', '300', '8', '12', '20', '1');

-- ----------------------------
-- Table structure for `attendance`
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `in_time` time DEFAULT NULL,
  `out_time` time DEFAULT NULL,
  `emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `fk_attendance_employee_reg1_idx` (`emp_id`),
  CONSTRAINT `fk_attendance_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES ('1', '2016-02-18', '08:30:00', '16:30:00', 'EMP0001');
INSERT INTO `attendance` VALUES ('2', '2016-02-27', '01:34:29', '09:57:21', 'EMP0002');

-- ----------------------------
-- Table structure for `basic_salary`
-- ----------------------------
DROP TABLE IF EXISTS `basic_salary`;
CREATE TABLE `basic_salary` (
  `basic_salary_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `job_title_id` int(11) NOT NULL,
  PRIMARY KEY (`basic_salary_id`),
  KEY `fk_basic_salary_job_title1_idx` (`job_title_id`),
  CONSTRAINT `fk_basic_salary_job_title1` FOREIGN KEY (`job_title_id`) REFERENCES `job_title` (`job_title_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of basic_salary
-- ----------------------------
INSERT INTO `basic_salary` VALUES ('1', '6000', '1');
INSERT INTO `basic_salary` VALUES ('2', '5500', '2');
INSERT INTO `basic_salary` VALUES ('3', '4000', '3');
INSERT INTO `basic_salary` VALUES ('4', '3000', '4');

-- ----------------------------
-- Table structure for `class_details`
-- ----------------------------
DROP TABLE IF EXISTS `class_details`;
CREATE TABLE `class_details` (
  `class_id` int(11) NOT NULL,
  `day` varchar(10) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `duration` double DEFAULT NULL,
  `subject_id` varchar(10) NOT NULL,
  `class_type_id` varchar(10) NOT NULL,
  `teacher_id` varchar(10) NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `fk_class_details_subject1_idx` (`subject_id`),
  KEY `fk_class_details_class_type1_idx` (`class_type_id`),
  KEY `fk_class_details_teacher_reg1_idx` (`teacher_id`),
  CONSTRAINT `fk_class_details_class_type1` FOREIGN KEY (`class_type_id`) REFERENCES `class_type` (`class_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_details_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_details_teacher_reg1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_reg` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_details
-- ----------------------------
INSERT INTO `class_details` VALUES ('1', 'Monday', '08:00:08', '2.3', '1', '2', 'TEC0001');
INSERT INTO `class_details` VALUES ('2', 'Wednesday', '08:00:00', '2', '2', '1', 'TEC0002');
INSERT INTO `class_details` VALUES ('3', 'Monday', '09:00:00', '3', '3', '3', 'TEC0003');
INSERT INTO `class_details` VALUES ('4', 'Monday', '02:00:00', '2', '3', '2', 'TEC0003');
INSERT INTO `class_details` VALUES ('5', 'Saturday', '08:00:00', '5', '4', '1', 'TEC0002');
INSERT INTO `class_details` VALUES ('6', 'Friday', '07:00:00', '5', '1', '1', 'TEC0001');

-- ----------------------------
-- Table structure for `class_type`
-- ----------------------------
DROP TABLE IF EXISTS `class_type`;
CREATE TABLE `class_type` (
  `class_type_id` varchar(10) NOT NULL,
  `class_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`class_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_type
-- ----------------------------
INSERT INTO `class_type` VALUES ('1', 'Group');
INSERT INTO `class_type` VALUES ('2', 'Hall');
INSERT INTO `class_type` VALUES ('3', 'Individual');

-- ----------------------------
-- Table structure for `employee_reg`
-- ----------------------------
DROP TABLE IF EXISTS `employee_reg`;
CREATE TABLE `employee_reg` (
  `emp_id` varchar(10) NOT NULL,
  `nic` varchar(10) DEFAULT NULL,
  `f_name` varchar(15) DEFAULT NULL,
  `l_name` varchar(25) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `mobile_no` int(11) DEFAULT NULL,
  `home_no` int(11) DEFAULT NULL,
  `no` varchar(10) DEFAULT NULL,
  `street1` varchar(40) DEFAULT NULL,
  `street2` varchar(45) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `available_leaves` int(11) DEFAULT NULL,
  `qualification_id` varchar(10) NOT NULL,
  `salutation_id` varchar(10) NOT NULL,
  `gender_id` varchar(10) NOT NULL,
  `job_title_id` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `fk_employee_reg_qualification1_idx` (`qualification_id`),
  KEY `fk_employee_reg_salutation1_idx` (`salutation_id`),
  KEY `fk_employee_reg_gender1_idx` (`gender_id`),
  KEY `fk_employee_reg_job_title1_idx` (`job_title_id`),
  CONSTRAINT `fk_employee_reg_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_reg_job_title1` FOREIGN KEY (`job_title_id`) REFERENCES `job_title` (`job_title_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_reg_qualification1` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`qualification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_reg_salutation1` FOREIGN KEY (`salutation_id`) REFERENCES `salutation` (`salutation_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee_reg
-- ----------------------------
INSERT INTO `employee_reg` VALUES ('EMP0001', '955303245', 'Vidyani', 'Gamlath', 'vgamlath@gmail.com', '1234567888', '812345345', '22/1', 'egodawaththa', 'Thalawathura', 'Muruthagahamula', '2016-02-18', '1', '22', '3', '3', '2', '1');
INSERT INTO `employee_reg` VALUES ('EMP0002', '123456789', 'kamal', 'amal', 'randika.help@gmail.com', '1234567890', '1234567890', '1', 'kandy', 'kandy', 'kandy', '2016-02-21', '1', '19', '2', '2', '1', '3');
INSERT INTO `employee_reg` VALUES ('EMP0003', '123456789', 'Randika', 'lakmal', 'randika.help@gmail.com', '123456789', '123456789', '2', 'kandy', 'kandy', 'kandy', '2016-02-27', '1', '24', '3', '1', '1', '2');

-- ----------------------------
-- Table structure for `etf_epf`
-- ----------------------------
DROP TABLE IF EXISTS `etf_epf`;
CREATE TABLE `etf_epf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `salary_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_etf_epf_salary1_idx` (`salary_id`),
  CONSTRAINT `fk_etf_epf_salary1` FOREIGN KEY (`salary_id`) REFERENCES `salary` (`salary_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of etf_epf
-- ----------------------------
INSERT INTO `etf_epf` VALUES ('1', '480', '1');
INSERT INTO `etf_epf` VALUES ('2', '480', '2');
INSERT INTO `etf_epf` VALUES ('3', '480', '3');
INSERT INTO `etf_epf` VALUES ('4', '320', '15');
INSERT INTO `etf_epf` VALUES ('5', '480', '16');
INSERT INTO `etf_epf` VALUES ('6', '480', '17');

-- ----------------------------
-- Table structure for `expence`
-- ----------------------------
DROP TABLE IF EXISTS `expence`;
CREATE TABLE `expence` (
  `expence_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `expence_type` varchar(20) DEFAULT NULL,
  `salary_invoice_id` varchar(10) DEFAULT NULL,
  `teacher_payment_invoice_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`expence_id`),
  KEY `fk_expence_salary_invoice1_idx` (`salary_invoice_id`),
  KEY `fk_expence_teacher_payment_invoice1_idx` (`teacher_payment_invoice_id`),
  CONSTRAINT `fk_expence_salary_invoice1` FOREIGN KEY (`salary_invoice_id`) REFERENCES `salary_invoice` (`salary_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_expence_teacher_payment_invoice1` FOREIGN KEY (`teacher_payment_invoice_id`) REFERENCES `teacher_payment_invoice` (`teacher_payment_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of expence
-- ----------------------------
INSERT INTO `expence` VALUES ('1', '6450', '2016-02-19', 'Salary ', 'Salary', 'SLI0001', null);
INSERT INTO `expence` VALUES ('2', '6120', '2016-02-23', 'Salary ', 'Salary', 'SLI0002', null);
INSERT INTO `expence` VALUES ('3', '1200', '2016-02-23', 'ETF/EPF ', 'ETF/EPF', 'SLI0002', null);
INSERT INTO `expence` VALUES ('4', '4730', '2016-02-27', 'Salary ', 'Salary', 'SLI0004', null);
INSERT INTO `expence` VALUES ('5', '800', '2016-02-27', 'ETF/EPF ', 'ETF/EPF', 'SLI0004', null);
INSERT INTO `expence` VALUES ('6', '4120', '2016-02-27', 'Salary ', 'Salary', 'SLI0005', null);
INSERT INTO `expence` VALUES ('7', '1200', '2016-02-27', 'ETF/EPF ', 'ETF/EPF', 'SLI0005', null);
INSERT INTO `expence` VALUES ('8', '200', '2016-02-27', 'Food', 'Other', null, null);
INSERT INTO `expence` VALUES ('10', '4120', '2016-02-28', 'Salary ', 'Salary', 'SLI0006', null);
INSERT INTO `expence` VALUES ('11', '1200', '2016-02-28', 'ETF/EPF ', 'ETF/EPF', 'SLI0006', null);

-- ----------------------------
-- Table structure for `gender`
-- ----------------------------
DROP TABLE IF EXISTS `gender`;
CREATE TABLE `gender` (
  `gender_id` varchar(10) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gender
-- ----------------------------
INSERT INTO `gender` VALUES ('1', 'Male');
INSERT INTO `gender` VALUES ('2', 'Female');

-- ----------------------------
-- Table structure for `guardion_details`
-- ----------------------------
DROP TABLE IF EXISTS `guardion_details`;
CREATE TABLE `guardion_details` (
  `guardion_id` varchar(10) NOT NULL,
  `f_name` varchar(15) DEFAULT NULL,
  `l_name` varchar(25) DEFAULT NULL,
  `type` varchar(15) DEFAULT NULL,
  `mobile_no` int(11) DEFAULT NULL,
  `home_no` int(11) DEFAULT NULL,
  `salutation_id` varchar(10) NOT NULL,
  PRIMARY KEY (`guardion_id`),
  KEY `fk_guardion_details_salutation1_idx` (`salutation_id`),
  CONSTRAINT `fk_guardion_details_salutation1` FOREIGN KEY (`salutation_id`) REFERENCES `salutation` (`salutation_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of guardion_details
-- ----------------------------
INSERT INTO `guardion_details` VALUES ('1', 'Namal', 'Rathna', 'Parent', '767887788', '867887767', '1');
INSERT INTO `guardion_details` VALUES ('2', 'vidyani', 'gamlath', 'Guardian', '755739593', '812345433', '1');

-- ----------------------------
-- Table structure for `income`
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income` (
  `income_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `emp_id` varchar(10) NOT NULL,
  `student_invoice_id` varchar(10) NOT NULL,
  PRIMARY KEY (`income_id`),
  KEY `fk_income_employee_reg1_idx` (`emp_id`),
  KEY `fk_income_student_payment_invoice1_idx` (`student_invoice_id`),
  CONSTRAINT `fk_income_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_income_student_payment_invoice1` FOREIGN KEY (`student_invoice_id`) REFERENCES `student_payment_invoice` (`student_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES ('1', '2016-02-18', '15:00:00', '500', 'EMP0001', 'SPI0001');
INSERT INTO `income` VALUES ('2', '2016-02-18', '15:12:00', '400', 'EMP0001', 'SPI0002');
INSERT INTO `income` VALUES ('3', '2016-02-27', '22:28:18', '0', 'EMP0002', 'SPI0004');
INSERT INTO `income` VALUES ('4', '2016-02-27', '22:33:39', '0', 'EMP0002', 'SPI0005');
INSERT INTO `income` VALUES ('5', '2016-02-27', '22:35:09', '0', 'EMP0002', 'SPI0006');
INSERT INTO `income` VALUES ('6', '2016-02-27', '22:37:46', '0', 'EMP0002', 'SPI0007');
INSERT INTO `income` VALUES ('7', '2016-02-27', '22:40:28', '0', 'EMP0002', 'SPI0008');
INSERT INTO `income` VALUES ('8', '2016-03-01', '18:55:10', '400', 'EMP0002', 'SPI0009');
INSERT INTO `income` VALUES ('9', '2016-03-01', '18:58:31', '300', 'EMP0002', 'SPI0010');
INSERT INTO `income` VALUES ('10', '2016-03-01', '19:18:00', '500', 'EMP0002', 'SPI0011');
INSERT INTO `income` VALUES ('12', '2016-03-02', '14:58:42', '300', 'EMP0002', 'SPI0013');
INSERT INTO `income` VALUES ('13', '2016-03-02', '15:02:44', '400', 'EMP0002', 'SPI0014');
INSERT INTO `income` VALUES ('14', '2016-03-02', '15:56:35', '400', 'EMP0002', 'SPI0015');

-- ----------------------------
-- Table structure for `job_title`
-- ----------------------------
DROP TABLE IF EXISTS `job_title`;
CREATE TABLE `job_title` (
  `job_title_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) DEFAULT NULL,
  `acces_privilages_id` int(11) NOT NULL,
  PRIMARY KEY (`job_title_id`),
  KEY `fk_job_title_acces_privilages1_idx` (`acces_privilages_id`),
  CONSTRAINT `fk_job_title_acces_privilages1` FOREIGN KEY (`acces_privilages_id`) REFERENCES `acces_privilages` (`acces_privilages_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job_title
-- ----------------------------
INSERT INTO `job_title` VALUES ('1', 'Admin', '1');
INSERT INTO `job_title` VALUES ('2', 'Manager', '1');
INSERT INTO `job_title` VALUES ('3', 'co-ordinater', '2');
INSERT INTO `job_title` VALUES ('4', 'cashier', '3');

-- ----------------------------
-- Table structure for `leaves`
-- ----------------------------
DROP TABLE IF EXISTS `leaves`;
CREATE TABLE `leaves` (
  `leave_id` int(11) NOT NULL AUTO_INCREMENT,
  `taken_leaves` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`leave_id`),
  KEY `fk_leaves_employee_reg1_idx` (`emp_id`),
  CONSTRAINT `fk_leaves_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leaves
-- ----------------------------
INSERT INTO `leaves` VALUES ('2', '1', '2016-02-19', 'Feeling sick tired', 'EMP0001');
INSERT INTO `leaves` VALUES ('3', '1', '2016-02-22', 'private', 'EMP0001');
INSERT INTO `leaves` VALUES ('4', '2', '2016-02-29', 'Private ', 'EMP0002');
INSERT INTO `leaves` VALUES ('5', '2', '2016-02-29', 'private', 'EMP0002');
INSERT INTO `leaves` VALUES ('6', '1', '2016-02-28', 'private', 'EMP0002');

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `user_name` varchar(50) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `system_id` varchar(10) DEFAULT NULL,
  `user_type_id` varchar(10) NOT NULL,
  `emp_id` varchar(10) DEFAULT NULL,
  `teacher_id` varchar(10) DEFAULT NULL,
  `recovery_id` int(11) NOT NULL,
  PRIMARY KEY (`user_name`),
  KEY `fk_login_user_type_idx` (`user_type_id`),
  KEY `fk_login_employee_reg1_idx` (`emp_id`),
  KEY `fk_login_teacher_reg1_idx` (`teacher_id`),
  KEY `fk_login_recovery1_idx` (`recovery_id`),
  CONSTRAINT `fk_login_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_login_recovery1` FOREIGN KEY (`recovery_id`) REFERENCES `recovery` (`recovery_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_login_teacher_reg1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_reg` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_login_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('randika', 'help', '1', '1', null, null, '1');
INSERT INTO `login` VALUES ('test', 'test123', 'EMP0002', '3', 'EMP0002', null, '2');

-- ----------------------------
-- Table structure for `medium`
-- ----------------------------
DROP TABLE IF EXISTS `medium`;
CREATE TABLE `medium` (
  `medium_id` varchar(10) NOT NULL,
  `medium` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`medium_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medium
-- ----------------------------
INSERT INTO `medium` VALUES ('1', 'Sinhala');
INSERT INTO `medium` VALUES ('2', 'English');
INSERT INTO `medium` VALUES ('3', 'Tamil');

-- ----------------------------
-- Table structure for `qualification`
-- ----------------------------
DROP TABLE IF EXISTS `qualification`;
CREATE TABLE `qualification` (
  `qualification_id` varchar(10) NOT NULL,
  `qualification` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`qualification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qualification
-- ----------------------------
INSERT INTO `qualification` VALUES ('1', 'O/L');
INSERT INTO `qualification` VALUES ('2', 'A/L');
INSERT INTO `qualification` VALUES ('3', 'BSc');
INSERT INTO `qualification` VALUES ('4', 'MSc');

-- ----------------------------
-- Table structure for `recovery`
-- ----------------------------
DROP TABLE IF EXISTS `recovery`;
CREATE TABLE `recovery` (
  `recovery_id` int(11) NOT NULL AUTO_INCREMENT,
  `quaction1` varchar(255) DEFAULT NULL,
  `quaction2` varchar(255) DEFAULT NULL,
  `answar1` varchar(45) DEFAULT NULL,
  `answar2` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`recovery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recovery
-- ----------------------------
INSERT INTO `recovery` VALUES ('1', 'game', 'colour', 'football', 'blue');
INSERT INTO `recovery` VALUES ('2', 'test', 'test', 'test', 'test');

-- ----------------------------
-- Table structure for `salary`
-- ----------------------------
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary` (
  `salary_id` int(11) NOT NULL AUTO_INCREMENT,
  `month` date DEFAULT NULL,
  `attendance` int(11) DEFAULT NULL,
  `taken_leaves` int(11) DEFAULT NULL,
  `payment` double DEFAULT NULL,
  `emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`salary_id`),
  KEY `fk_salary_employee_reg1_idx` (`emp_id`),
  CONSTRAINT `fk_salary_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salary
-- ----------------------------
INSERT INTO `salary` VALUES ('1', '2016-02-19', '10', '1', '6450', 'EMP0001');
INSERT INTO `salary` VALUES ('2', '2016-02-20', '10', '1', '5970', 'EMP0001');
INSERT INTO `salary` VALUES ('3', '2016-02-23', '1', '2', '6120', 'EMP0001');
INSERT INTO `salary` VALUES ('10', '2016-02-25', '2', '2', '6420', 'EMP0001');
INSERT INTO `salary` VALUES ('11', '2016-02-25', '2', '2', '4420', 'EMP0001');
INSERT INTO `salary` VALUES ('15', '2016-02-27', '1', '21', '4730', 'EMP0002');
INSERT INTO `salary` VALUES ('16', '2016-02-27', '1', '2', '4120', 'EMP0001');
INSERT INTO `salary` VALUES ('17', '2016-02-28', '1', '2', '4120', 'EMP0001');

-- ----------------------------
-- Table structure for `salary_advance`
-- ----------------------------
DROP TABLE IF EXISTS `salary_advance`;
CREATE TABLE `salary_advance` (
  `salary_advance_id` int(11) NOT NULL,
  `month` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`salary_advance_id`),
  KEY `fk_salary_advance_employee_reg1_idx` (`emp_id`),
  CONSTRAINT `fk_salary_advance_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salary_advance
-- ----------------------------
INSERT INTO `salary_advance` VALUES ('1', '2016-02-09', '2000', 'EMP0001');

-- ----------------------------
-- Table structure for `salary_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `salary_invoice`;
CREATE TABLE `salary_invoice` (
  `salary_invoice_id` varchar(10) NOT NULL,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `salary_id` int(11) DEFAULT NULL,
  `salary_advance_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`salary_invoice_id`),
  KEY `fk_salary_invoice_salary1_idx` (`salary_id`),
  KEY `fk_salary_invoice_salary_advance1_idx` (`salary_advance_id`),
  CONSTRAINT `fk_salary_invoice_salary1` FOREIGN KEY (`salary_id`) REFERENCES `salary` (`salary_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salary_invoice_salary_advance1` FOREIGN KEY (`salary_advance_id`) REFERENCES `salary_advance` (`salary_advance_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salary_invoice
-- ----------------------------
INSERT INTO `salary_invoice` VALUES ('SLI0001', '5970', '2016-02-19', 'salary', '1', null);
INSERT INTO `salary_invoice` VALUES ('SLI0002', '6120', '2016-02-23', 'salary', '3', null);
INSERT INTO `salary_invoice` VALUES ('SLI0003', '4420', '2016-02-25', 'salary', '11', null);
INSERT INTO `salary_invoice` VALUES ('SLI0004', '4730', '2016-02-27', 'salary', '15', null);
INSERT INTO `salary_invoice` VALUES ('SLI0005', '4120', '2016-02-27', 'salary', '16', null);
INSERT INTO `salary_invoice` VALUES ('SLI0006', '4120', '2016-02-28', 'salary', '17', null);

-- ----------------------------
-- Table structure for `salutation`
-- ----------------------------
DROP TABLE IF EXISTS `salutation`;
CREATE TABLE `salutation` (
  `salutation_id` varchar(10) NOT NULL,
  `salutation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`salutation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salutation
-- ----------------------------
INSERT INTO `salutation` VALUES ('1', 'Mr');
INSERT INTO `salutation` VALUES ('2', 'Mrs');
INSERT INTO `salutation` VALUES ('3', 'Miss');
INSERT INTO `salutation` VALUES ('4', 'Rev');

-- ----------------------------
-- Table structure for `student_payment`
-- ----------------------------
DROP TABLE IF EXISTS `student_payment`;
CREATE TABLE `student_payment` (
  `student_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payment_status` varchar(5) DEFAULT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`student_payment_id`),
  KEY `fk_student_payment_class_details1_idx` (`class_id`),
  CONSTRAINT `fk_student_payment_class_details1` FOREIGN KEY (`class_id`) REFERENCES `class_details` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_payment
-- ----------------------------
INSERT INTO `student_payment` VALUES ('1', '2016-02-18', '400', 'Regul', '1');
INSERT INTO `student_payment` VALUES ('2', '2016-02-18', '0', 'Free', '1');
INSERT INTO `student_payment` VALUES ('3', '2016-02-27', '0', 'regul', '1');
INSERT INTO `student_payment` VALUES ('4', '2016-02-27', '0', 'free', '1');
INSERT INTO `student_payment` VALUES ('5', '2016-02-27', '0', 'free', '1');
INSERT INTO `student_payment` VALUES ('6', '2016-02-27', '0', 'free', '1');
INSERT INTO `student_payment` VALUES ('7', '2016-02-27', '0', 'free', '1');
INSERT INTO `student_payment` VALUES ('8', '2016-03-01', '400', 'regul', '1');
INSERT INTO `student_payment` VALUES ('9', '2016-03-01', '400', 'regul', '2');
INSERT INTO `student_payment` VALUES ('10', '2016-03-01', '300', 'regul', '1');
INSERT INTO `student_payment` VALUES ('11', '2016-03-01', '300', 'regul', '2');
INSERT INTO `student_payment` VALUES ('12', '2016-03-02', '300', 'regul', '1');
INSERT INTO `student_payment` VALUES ('13', '2016-03-02', '300', 'regul', '1');
INSERT INTO `student_payment` VALUES ('14', '2016-03-02', '300', 'regul', '2');
INSERT INTO `student_payment` VALUES ('15', '2016-03-02', '400', 'regul', '1');
INSERT INTO `student_payment` VALUES ('16', '2016-03-02', '400', 'regul', '2');
INSERT INTO `student_payment` VALUES ('17', '2016-03-02', '400', 'regul', '1');
INSERT INTO `student_payment` VALUES ('18', '2016-03-02', '400', 'regul', '6');

-- ----------------------------
-- Table structure for `student_payment_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `student_payment_invoice`;
CREATE TABLE `student_payment_invoice` (
  `student_invoice_id` varchar(10) NOT NULL,
  `date` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `student_payment_id` int(11) DEFAULT NULL,
  `student_id` varchar(10) NOT NULL,
  PRIMARY KEY (`student_invoice_id`),
  KEY `fk_student_payment_invoice_student_payment1_idx` (`student_payment_id`),
  KEY `fk_student_payment_invoice_student_reg1_idx` (`student_id`),
  CONSTRAINT `fk_student_payment_invoice_student_payment1` FOREIGN KEY (`student_payment_id`) REFERENCES `student_payment` (`student_payment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_payment_invoice_student_reg1` FOREIGN KEY (`student_id`) REFERENCES `student_reg` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_payment_invoice
-- ----------------------------
INSERT INTO `student_payment_invoice` VALUES ('SPI0001', '2016-02-18', '500', 'Reg', null, 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0002', '2016-02-18', '400', 'Class', '1', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0003', '2016-02-18', '0', 'Class', '2', 'STU0002');
INSERT INTO `student_payment_invoice` VALUES ('SPI0004', '2016-02-27', '0', 'Hall', '3', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0005', '2016-02-27', '0', 'Hall', '4', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0006', '2016-02-27', '0', 'Hall', '5', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0007', '2016-02-27', '0', 'Hall', '6', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0008', '2016-02-27', '0', 'Hall', '7', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0009', '2016-03-01', '400', 'Hall', '8', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0010', '2016-03-01', '300', 'Hall', '10', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0011', '2016-03-01', '500', 'reg', null, 'STU0003');
INSERT INTO `student_payment_invoice` VALUES ('SPI0012', '2016-03-02', '300', 'Hall', '12', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0013', '2016-03-02', '300', 'Hall', '13', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0014', '2016-03-02', '400', 'Hall', '15', 'STU0001');
INSERT INTO `student_payment_invoice` VALUES ('SPI0015', '2016-03-02', '400', '', '17', 'STU0001');

-- ----------------------------
-- Table structure for `student_reg`
-- ----------------------------
DROP TABLE IF EXISTS `student_reg`;
CREATE TABLE `student_reg` (
  `student_id` varchar(10) NOT NULL,
  `f_name` varchar(15) DEFAULT NULL,
  `l_name` varchar(25) DEFAULT NULL,
  `school` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `mobile_no` int(11) DEFAULT NULL,
  `no` varchar(10) DEFAULT NULL,
  `street1` varchar(45) DEFAULT NULL,
  `street2` varchar(45) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `salutation_id` varchar(10) NOT NULL,
  `gender_id` varchar(10) NOT NULL,
  `guardion_id` varchar(10) NOT NULL,
  `emp_id` varchar(10) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `fk_student_reg_salutation1_idx` (`salutation_id`),
  KEY `fk_student_reg_gender1_idx` (`gender_id`),
  KEY `fk_student_reg_guardion_details1_idx` (`guardion_id`),
  KEY `fk_student_reg_employee_reg1_idx` (`emp_id`),
  CONSTRAINT `fk_student_reg_employee_reg1` FOREIGN KEY (`emp_id`) REFERENCES `employee_reg` (`emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_reg_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_reg_guardion_details1` FOREIGN KEY (`guardion_id`) REFERENCES `guardion_details` (`guardion_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_reg_salutation1` FOREIGN KEY (`salutation_id`) REFERENCES `salutation` (`salutation_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_reg
-- ----------------------------
INSERT INTO `student_reg` VALUES ('STU0001', 'Kamal', 'Rathna', 'WSSCK', '1995-09-08', '756788766', '34e', 'Kasbawa rd', 'Maha Muhuda', 'Kanthale', '2016-02-18', '1', '1', '1', '1', 'EMP0001');
INSERT INTO `student_reg` VALUES ('STU0002', 'Sunil', 'Rathna', 'WSSCK', '1997-09-23', '765666787', '23', 'mahiyangane', 'dambana', 'kanthale', '2016-02-18', '1', '1', '1', '1', 'EMP0001');
INSERT INTO `student_reg` VALUES ('STU0003', 'wenura', 'de silva', 'KCK', '1994-06-30', '451121221', '19/3', 'wassath ganwathura para', 'sarama ussan yana para ', 'melbourne', '2016-03-01', '1', '1', '1', '2', 'EMP0002');

-- ----------------------------
-- Table structure for `student_timetable`
-- ----------------------------
DROP TABLE IF EXISTS `student_timetable`;
CREATE TABLE `student_timetable` (
  `class_id` int(11) NOT NULL,
  `student_id` varchar(10) NOT NULL,
  KEY `fk_student_timetable_class_details1_idx` (`class_id`),
  KEY `fk_student_timetable_student_reg1_idx` (`student_id`),
  CONSTRAINT `fk_student_timetable_class_details1` FOREIGN KEY (`class_id`) REFERENCES `class_details` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_timetable_student_reg1` FOREIGN KEY (`student_id`) REFERENCES `student_reg` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_timetable
-- ----------------------------
INSERT INTO `student_timetable` VALUES ('1', 'STU0001');
INSERT INTO `student_timetable` VALUES ('2', 'STU0001');

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `subject_id` varchar(10) NOT NULL,
  `subject_name` varchar(45) DEFAULT NULL,
  `medium_id` varchar(10) NOT NULL,
  PRIMARY KEY (`subject_id`),
  KEY `fk_subject_medium1_idx` (`medium_id`),
  CONSTRAINT `fk_subject_medium1` FOREIGN KEY (`medium_id`) REFERENCES `medium` (`medium_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '8-Maths', '1');
INSERT INTO `subject` VALUES ('2', '8-Sinhala', '1');
INSERT INTO `subject` VALUES ('3', '8-Maths', '2');
INSERT INTO `subject` VALUES ('4', '9-Science', '1');

-- ----------------------------
-- Table structure for `teacher_payment`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_payment`;
CREATE TABLE `teacher_payment` (
  `teacher_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `teacher_id` varchar(10) NOT NULL,
  PRIMARY KEY (`teacher_payment_id`),
  KEY `fk_teacher_payment_teacher_reg1_idx` (`teacher_id`),
  CONSTRAINT `fk_teacher_payment_teacher_reg1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_reg` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_payment
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_payment_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_payment_invoice`;
CREATE TABLE `teacher_payment_invoice` (
  `teacher_payment_invoice_id` varchar(10) NOT NULL,
  `date` date DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `teacher_payment_id` int(11) NOT NULL,
  PRIMARY KEY (`teacher_payment_invoice_id`),
  KEY `fk_teacher_payment_invoice_teacher_payment1_idx` (`teacher_payment_id`),
  CONSTRAINT `fk_teacher_payment_invoice_teacher_payment1` FOREIGN KEY (`teacher_payment_id`) REFERENCES `teacher_payment` (`teacher_payment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_payment_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_reg`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_reg`;
CREATE TABLE `teacher_reg` (
  `teacher_id` varchar(10) NOT NULL,
  `nic` varchar(10) DEFAULT NULL,
  `f_name` varchar(15) DEFAULT NULL,
  `l_name` varchar(25) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  `mobile_no` int(11) DEFAULT NULL,
  `home_no` int(11) DEFAULT NULL,
  `no` varchar(10) DEFAULT NULL,
  `street1` varchar(45) DEFAULT NULL,
  `street2` varchar(45) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `qualification_id` varchar(10) NOT NULL,
  `salutation_id` varchar(10) NOT NULL,
  `gender_id` varchar(10) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `fk_teacher_reg_qualification1_idx` (`qualification_id`),
  KEY `fk_teacher_reg_salutation1_idx` (`salutation_id`),
  KEY `fk_teacher_reg_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_teacher_reg_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_reg_qualification1` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`qualification_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_reg_salutation1` FOREIGN KEY (`salutation_id`) REFERENCES `salutation` (`salutation_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_reg
-- ----------------------------
INSERT INTO `teacher_reg` VALUES ('TEC0001', '878798675V', 'Amal', 'Perera', 'amal@gmail.com', '756789876', '812345654', '22/9', 'gemba rd', 'pokuna', 'kandy', '2016-02-19', '1', '1', '1', '2');
INSERT INTO `teacher_reg` VALUES ('TEC0002', '861234565V', 'Dilusha', 'Kottage', 'senani@gmail.com', '764567567', '812333656', '45/a', 'moladanda', 'Kiribathkumbura', 'Peradeniya', '2016-02-19', '1', '1', '2', '2');
INSERT INTO `teacher_reg` VALUES ('TEC0003', '456456456V', 'Kamalai', 'Perera', 'kamali@gmail.com', '763456765', '813456787', '56', 'sanda para', 'haputhale', 'ahasa', '2016-02-19', '0', '1', '1', '2');

-- ----------------------------
-- Table structure for `teacher_time_table`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_time_table`;
CREATE TABLE `teacher_time_table` (
  `time_table_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_fee` double DEFAULT NULL,
  `teacher_id` varchar(10) NOT NULL,
  `subject_id` varchar(10) NOT NULL,
  `class_type_id` varchar(10) NOT NULL,
  PRIMARY KEY (`time_table_id`),
  KEY `fk_teacher_time_table_teacher_reg1_idx` (`teacher_id`),
  KEY `fk_teacher_time_table_subject1_idx` (`subject_id`),
  KEY `fk_teacher_time_table_class_type1_idx` (`class_type_id`),
  CONSTRAINT `fk_teacher_time_table_class_type1` FOREIGN KEY (`class_type_id`) REFERENCES `class_type` (`class_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_time_table_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_time_table_teacher_reg1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_reg` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_time_table
-- ----------------------------
INSERT INTO `teacher_time_table` VALUES ('1', '400', 'TEC0001', '1', '1');
INSERT INTO `teacher_time_table` VALUES ('2', '1000', 'TEC0003', '3', '1');
INSERT INTO `teacher_time_table` VALUES ('3', '300', 'TEC0002', '2', '3');
INSERT INTO `teacher_time_table` VALUES ('4', '450', 'TEC0001', '4', '2');

-- ----------------------------
-- Table structure for `user_type`
-- ----------------------------
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type` (
  `user_type_id` varchar(10) NOT NULL,
  `user_type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_type
-- ----------------------------
INSERT INTO `user_type` VALUES ('1', 'Admin');
INSERT INTO `user_type` VALUES ('2', 'Manager');
INSERT INTO `user_type` VALUES ('3', 'Employee');
