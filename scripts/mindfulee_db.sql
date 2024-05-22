/*
 Navicat Premium Data Transfer

 Source Server         : PostGres
 Source Server Type    : PostgreSQL
 Source Server Version : 160001 (160001)
 Source Host           : 127.0.0.1:5432
 Source Catalog        : mindfulee
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160001 (160001)
 File Encoding         : 65001

 Date: 22/05/2024 17:21:50
*/


-- ----------------------------
-- Sequence structure for activity_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."activity_seq";
CREATE SEQUENCE "public"."activity_seq" 
INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for activity_type_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."activity_type_seq";
CREATE SEQUENCE "public"."activity_type_seq" 
INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Sequence structure for app_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."app_user_seq";
CREATE SEQUENCE "public"."app_user_seq" 
INCREMENT 1
MAXVALUE 9223372036854775807
CACHE 1;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS "public"."activity";
CREATE TABLE "public"."activity" (
  "id" int4 NOT NULL,
  "version" int4 NOT NULL,
  "activity_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "intent_type" int4 NOT NULL,
  "activity_type_id" int4 NOT NULL,
  "activity_status" int4 NOT NULL,
  "date_added" timestamptz(6) NOT NULL,
  "date_started" timestamptz(6) NOT NULL,
  "date_ended" timestamptz(6) NOT NULL,
  "date_updated" timestamptz(6) NOT NULL,
  "user_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO "public"."activity" VALUES (0, 1, 'Test', 1, 0, 1, '2024-05-20 14:06:09.207519+03', '2024-05-20 15:08:00+03', '2024-05-20 16:06:00+03', '2024-05-21 08:28:45.964735+03', 0);
INSERT INTO "public"."activity" VALUES (2, 1, 'Test3', 1, 5, 2, '2024-05-20 14:08:55.145373+03', '2024-05-30 15:08:00+03', '2024-05-31 16:08:00+03', '2024-05-21 08:29:12.923835+03', 0);
INSERT INTO "public"."activity" VALUES (4, 1, 'Test6', 0, 5, 0, '2024-05-20 14:55:58.039105+03', '2024-05-29 15:54:00+03', '2024-05-31 16:54:00+03', '2024-05-21 08:29:16.83291+03', 0);
INSERT INTO "public"."activity" VALUES (5, 1, 'Test6', 3, 4, 0, '2024-05-20 14:57:28.874033+03', '2024-05-29 17:55:00+03', '2024-05-31 19:54:00+03', '2024-05-21 08:29:25.602579+03', 0);
INSERT INTO "public"."activity" VALUES (11, 1, 'Hobby with Family', 3, 4, 0, '2024-05-21 08:00:04.613828+03', '2024-05-21 08:57:00+03', '2024-05-21 09:57:00+03', '2024-05-22 16:19:08.159051+03', 0);
INSERT INTO "public"."activity" VALUES (16, 3, 'Excel forever', 0, 0, 0, '2024-05-21 09:45:22.393822+03', '2024-05-21 07:45:00+03', '2024-05-21 14:45:00+03', '2024-05-22 16:30:17.139475+03', 0);
INSERT INTO "public"."activity" VALUES (25, 1, 'Sleep', 3, 7, 4, '2024-05-22 16:33:05.438937+03', '2024-05-23 00:01:00+03', '2024-05-23 07:00:00+03', '2024-05-22 16:34:29.346667+03', 0);
INSERT INTO "public"."activity" VALUES (24, 2, 'Gaming', 2, 11, 0, '2024-05-22 16:15:49.728719+03', '2024-05-23 10:15:00+03', '2024-05-23 12:15:00+03', '2024-05-22 16:36:09.020021+03', 0);
INSERT INTO "public"."activity" VALUES (14, 3, 'Introspect', 0, 5, 0, '2024-05-21 08:01:58.625394+03', '2024-05-23 09:00:00+03', '2024-05-23 11:00:00+03', '2024-05-22 16:39:20.060035+03', 0);
INSERT INTO "public"."activity" VALUES (26, 1, 'Sleep', 3, 7, 0, '2024-05-22 16:43:03.983752+03', '2024-05-23 00:00:00+03', '2024-05-23 07:00:00+03', '2024-05-22 16:43:11.54911+03', 0);
INSERT INTO "public"."activity" VALUES (27, 0, 'Sleep', 0, 7, 0, '2024-05-22 16:43:57.510032+03', '2024-05-23 00:00:00+03', '2024-05-23 07:00:00+03', '2024-05-22 16:43:57.510032+03', 0);
INSERT INTO "public"."activity" VALUES (12, 4, 'F Test 9', 0, 5, 2, '2024-05-21 08:01:09.240284+03', '2024-05-21 09:00:00+03', '2024-05-21 10:00:00+03', '2024-05-21 09:00:16.220755+03', 0);
INSERT INTO "public"."activity" VALUES (17, 0, 'Family Dinner', 0, 8, 0, '2024-05-21 09:50:51.885181+03', '2024-05-21 10:50:00+03', '2024-05-21 11:50:00+03', '2024-05-21 09:50:51.885181+03', 0);
INSERT INTO "public"."activity" VALUES (9, 4, 'FT 8', 3, 2, 4, '2024-05-21 07:59:55.259471+03', '2024-05-21 08:57:00+03', '2024-05-21 09:57:00+03', '2024-05-21 09:51:23.057833+03', 0);
INSERT INTO "public"."activity" VALUES (28, 1, 'Sleep', 3, 7, 4, '2024-05-22 16:44:39.054601+03', '2024-05-23 00:44:00+03', '2024-05-23 07:00:00+03', '2024-05-22 16:44:44.031008+03', 0);
INSERT INTO "public"."activity" VALUES (13, 2, 'Test 9', 0, 5, 3, '2024-05-21 08:01:46.316755+03', '2024-05-23 09:00:00+03', '2024-05-30 10:00:00+03', '2024-05-22 10:59:14.59231+03', 0);
INSERT INTO "public"."activity" VALUES (29, 1, 'Sleep', 0, 9, 4, '2024-05-22 16:45:57.121936+03', '2024-05-23 00:45:00+03', '2024-05-23 07:46:00+03', '2024-05-22 16:46:05.662575+03', 0);
INSERT INTO "public"."activity" VALUES (30, 1, 'Sleep', 3, 12, 4, '2024-05-22 16:47:39.939272+03', '2024-05-23 00:47:00+03', '2024-05-23 07:48:00+03', '2024-05-22 16:48:51.241479+03', 0);
INSERT INTO "public"."activity" VALUES (32, 1, 'Meditate', 3, 6, 4, '2024-05-22 16:49:36.043984+03', '2024-05-23 01:00:00+03', '2024-05-23 02:00:00+03', '2024-05-22 16:49:42.37977+03', 0);
INSERT INTO "public"."activity" VALUES (31, 1, 'Make my Bed', 1, 13, 4, '2024-05-22 16:48:27.865809+03', '2024-05-23 07:00:00+03', '2024-05-23 07:02:00+03', '2024-05-22 16:52:24.095483+03', 0);
INSERT INTO "public"."activity" VALUES (15, 5, 'Excel cleanup', 0, 0, 1, '2024-05-21 09:44:49.530572+03', '2024-05-22 10:44:00+03', '2024-05-22 18:44:00+03', '2024-05-22 17:02:19.552815+03', 0);
INSERT INTO "public"."activity" VALUES (20, 0, 'Dinner', 1, 8, 0, '2024-05-22 13:59:07.405631+03', '2024-05-22 17:58:00+03', '2024-05-22 19:58:00+03', '2024-05-22 13:59:07.405631+03', 0);
INSERT INTO "public"."activity" VALUES (6, 5, 'Test7', 2, 3, 2, '2024-05-21 07:56:47.387551+03', '2024-05-22 08:53:00+03', '2024-05-22 09:53:00+03', '2024-05-22 13:59:18.290815+03', 0);
INSERT INTO "public"."activity" VALUES (10, 7, 'FRT 3', 3, 3, 2, '2024-05-21 08:00:00.166595+03', '2024-05-22 08:57:00+03', '2024-05-22 09:57:00+03', '2024-05-22 13:59:42.087386+03', 0);
INSERT INTO "public"."activity" VALUES (7, 4, 'Test8', 0, 0, 4, '2024-05-21 07:59:42.543735+03', '2024-05-22 08:57:00+03', '2024-05-22 09:57:00+03', '2024-05-22 14:00:13.720832+03', 0);
INSERT INTO "public"."activity" VALUES (8, 3, 'Test8', 3, 1, 3, '2024-05-21 07:59:49.418486+03', '2024-05-22 08:57:00+03', '2024-05-22 09:57:00+03', '2024-05-22 14:17:59.936851+03', 0);
INSERT INTO "public"."activity" VALUES (18, 1, 'Yoga Nidhra', 3, 6, 4, '2024-05-22 11:11:38.079788+03', '2024-05-22 12:10:00+03', '2024-05-22 13:10:00+03', '2024-05-22 15:53:35.86086+03', 0);
INSERT INTO "public"."activity" VALUES (19, 1, 'Walk', 0, 9, 0, '2024-05-22 13:58:06.919263+03', '2024-05-22 13:58:00+03', '2024-05-22 21:57:00+03', '2024-05-22 15:59:42.379428+03', 0);
INSERT INTO "public"."activity" VALUES (22, 0, 'Stare at the wall', 1, 6, 0, '2024-05-22 16:13:32.801127+03', '2024-05-22 20:12:00+03', '2024-05-22 22:13:00+03', '2024-05-22 16:13:32.801127+03', 0);
INSERT INTO "public"."activity" VALUES (21, 1, 'Nap', 0, 7, 4, '2024-05-22 15:59:36.208774+03', '2024-05-22 10:00:00+03', '2024-05-22 15:59:00+03', '2024-05-22 16:13:45.427991+03', 0);
INSERT INTO "public"."activity" VALUES (1, 4, 'Test21', 3, 5, 4, '2024-05-20 14:08:09.823458+03', '2024-05-20 15:09:00+03', '2024-05-20 16:07:00+03', '2024-05-22 16:14:13.75777+03', 0);
INSERT INTO "public"."activity" VALUES (23, 1, 'Game with friend', 3, 4, 0, '2024-05-22 16:14:43.673308+03', '2024-05-23 09:14:00+03', '2024-05-23 20:14:00+03', '2024-05-22 16:14:57.828523+03', 0);
INSERT INTO "public"."activity" VALUES (34, 0, 'Sleep', 3, 7, 0, '2024-05-22 17:02:58.837587+03', '2024-05-23 10:15:00+03', '2024-05-23 18:00:00+03', '2024-05-22 17:02:58.837587+03', 0);
INSERT INTO "public"."activity" VALUES (35, 0, 'Meditate', 3, 6, 0, '2024-05-22 17:03:33.839099+03', '2024-05-23 00:44:00+03', '2024-05-23 00:47:00+03', '2024-05-22 17:03:33.839099+03', 0);
INSERT INTO "public"."activity" VALUES (33, 1, 'Bed', 0, 13, 4, '2024-05-22 16:52:51.689767+03', '2024-05-23 10:15:00+03', '2024-05-23 10:20:00+03', '2024-05-22 17:04:01.680252+03', 0);
INSERT INTO "public"."activity" VALUES (36, 0, 'Dust off shelves', 3, 14, 0, '2024-05-22 17:04:35.228122+03', '2024-05-23 10:15:00+03', '2024-05-23 10:35:00+03', '2024-05-22 17:04:35.228122+03', 0);

-- ----------------------------
-- Table structure for activity_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."activity_type";
CREATE TABLE "public"."activity_type" (
  "id" int4 NOT NULL,
  "version" int4 NOT NULL,
  "activity_category" int4 DEFAULT 0,
  "activity_type_name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of activity_type
-- ----------------------------
INSERT INTO "public"."activity_type" VALUES (5, 0, 2, 'PERSONAL_DEVELOPMENT_TASK');
INSERT INTO "public"."activity_type" VALUES (10, 0, 0, 'Test');
INSERT INTO "public"."activity_type" VALUES (7, 0, 0, 'Sleep');
INSERT INTO "public"."activity_type" VALUES (8, 0, 6, 'Dinner with Family');
INSERT INTO "public"."activity_type" VALUES (9, 0, 0, 'Rest');
INSERT INTO "public"."activity_type" VALUES (6, 0, 1, 'Meditate');
INSERT INTO "public"."activity_type" VALUES (11, 0, 9, 'Gaming');
INSERT INTO "public"."activity_type" VALUES (0, 0, 3, 'Demo Work');
INSERT INTO "public"."activity_type" VALUES (1, 0, 4, 'Demo Fun');
INSERT INTO "public"."activity_type" VALUES (2, 0, 5, 'Demo Family');
INSERT INTO "public"."activity_type" VALUES (3, 0, 6, 'Demo Friend');
INSERT INTO "public"."activity_type" VALUES (4, 0, 7, 'Demo Hobby');
INSERT INTO "public"."activity_type" VALUES (12, 0, 0, 'Long Sleep');
INSERT INTO "public"."activity_type" VALUES (13, 0, 4, 'Make Bed');
INSERT INTO "public"."activity_type" VALUES (14, 0, 4, 'Dust Shelves');

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."app_user";
CREATE TABLE "public"."app_user" (
  "id" int4 NOT NULL,
  "version" int4 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(60) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO "public"."app_user" VALUES (0, 0, 'test_user', '$2a$10$BZDRAD0YfkAaA5ovGma6Au8i336bGVbnBLnYPdDu29ygdvkNlF/vW');
INSERT INTO "public"."app_user" VALUES (10, 0, 'andrew', '$2a$10$oFJQBjilwSUO.nT4vQRGj.YhsFNuBZE/lKhOAa7T79MZ8g423K5L6');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."activity_seq"
OWNED BY "public"."activity"."id";
SELECT setval('"public"."activity_seq"', 36, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."activity_type_seq"
OWNED BY "public"."activity_type"."id";
SELECT setval('"public"."activity_type_seq"', 14, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."app_user_seq"
OWNED BY "public"."app_user"."id";
SELECT setval('"public"."app_user_seq"', 10, true);

-- ----------------------------
-- Primary Key structure for table activity
-- ----------------------------
ALTER TABLE "public"."activity" ADD CONSTRAINT "Activity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table activity_type
-- ----------------------------
ALTER TABLE "public"."activity_type" ADD CONSTRAINT "Activity_Type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table app_user
-- ----------------------------
ALTER TABLE "public"."app_user" ADD CONSTRAINT "user_name" UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table app_user
-- ----------------------------
ALTER TABLE "public"."app_user" ADD CONSTRAINT "App_User_pkey" PRIMARY KEY ("id");
