--liquibase formatted sql
--changeset santhosh:7

CREATE TABLE `family_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `family_id` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `area_details` int NOT NULL,
  `door_no` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `respondent_name` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `mobile_number` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status_of_house` int DEFAULT NULL,
  `type_of_house` int DEFAULT NULL,
  `wet_land_in_acres` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N/A',
  `dry_land_in_acres` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N/A',
  `motor_vechicles` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N/A',
  `livestock_details` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N/A',
  `toilet_facility_at_home` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N/A',
  `deleted_by` int DEFAULT NULL,
  `is_deleted` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'N',
  `created_by` int DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ;
