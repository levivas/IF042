-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `answer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `is_true` tinyint(1) NOT NULL,
  `question_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`answer_id`),
  UNIQUE KEY `answer_id_UNIQUE` (`answer_id`),
  KEY `fk_answers_questions1_idx` (`question_id`),
  CONSTRAINT `fk_answers_questions1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (4,'SELECT * FROM',1,3),(5,'answer5',0,2),(6,'answer6',1,2),(24,'answer5',0,2),(25,'answer6',1,2),(26,'since ten years.',1,4),(27,'ten years ago.',0,4),(28,'for ten years.',0,4),(29,'ten years.',0,4),(30,' harder',1,5),(31,'hardest',0,5),(32,'hard',0,5),(33,'more hard',0,5),(34,'  still?',1,6),(35,'already?',0,6),(36,'now?',0,6),(37,' yet?',0,6),(38,' will have to.',1,7),(39,'would have to',0,7),(40,'had to',0,7),(41,'want to',0,7),(42,'not so clever than',1,8),(43,'not as clever than',0,8),(44,'not as clever as',0,8),(45,'not so clever as',0,8),(46,'11',1,9),(47,'22',0,9),(48,'answer1',1,10),(49,'answer2',0,10),(50,'answer1',0,11),(51,'answer2',0,11),(52,'answer4',0,11),(53,'answer1',1,11),(54,'answer2',0,11),(55,'answer4',0,11),(58,'111',1,13),(59,'222',0,13),(60,'333',0,13),(61,'111',1,14),(62,'222',0,14),(63,'333',0,14),(64,'444',0,14),(71,'ans1',1,18),(72,'ans2',0,18),(73,'ans3',0,18),(74,'qwe',1,19),(75,'sds',0,19),(78,'dasf',1,21),(79,'dsfs',0,21),(80,'fdgdfg',1,22),(81,'fgdgf',0,22),(86,'1',1,26),(87,'2',0,26),(88,'weqwqw',1,27),(89,'eqweqw',0,27);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `available_tests`
--

DROP TABLE IF EXISTS `available_tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `available_tests` (
  `avaliable_test_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `finish_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `session_test_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`avaliable_test_id`),
  KEY `fk_avaliable_tests_tests1_idx` (`test_id`),
  KEY `fk_avaliable_tests_students1_idx` (`user_id`),
  KEY `fk_session_tests_tests1` (`session_test_id`),
  CONSTRAINT `fk_avaliable_tests_students1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_avaliable_tests_tests1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_session_tests_tests1` FOREIGN KEY (`session_test_id`) REFERENCES `session_test` (`session_test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `available_tests`
--

LOCK TABLES `available_tests` WRITE;
/*!40000 ALTER TABLE `available_tests` DISABLE KEYS */;
INSERT INTO `available_tests` VALUES (1,1,23,'2014-05-20 17:00:00','2014-05-20 19:00:00',NULL);
/*!40000 ALTER TABLE `available_tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (5,'Design patterns'),(10,'English'),(1,'Java'),(4,'MySQL'),(9,'OOP'),(2,'Python'),(3,'Ruby');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `name_UNIQUE_Gr` (`name`),
  KEY `fk_groups_roles1_idx` (`role_id`),
  CONSTRAINT `fk_groups_roles1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,1,'SuperAdmin'),(2,2,'Admin'),(3,3,'Teacher'),(9,4,'IF-039 JAVA'),(10,4,'IF-042 JAVA');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `question_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` int(10) unsigned NOT NULL,
  `title` text NOT NULL,
  `rank` tinyint(4) NOT NULL,
  `type` tinyint(4) NOT NULL,
  PRIMARY KEY (`question_id`),
  KEY `fk_questions_tests_idx` (`test_id`),
  CONSTRAINT `fk_questions_tests` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (2,1,'Metaclass in Java - ...',2),(3,4,'Choose the worst query',5),(4,12,'I have been living in Madrid ......',4),(5,12,'This is the .......... thing I have ever done.',4),(6,12,'Have you finished with the newspaper ...',4),(7,12,'If I want to pass my exam, I ........ study harder',4),(8,12,'Michael is .............. his sister.',4),(9,11,'1111',11),(10,1,'question1',6),(11,1,'Superclass for all other classes in Java is named...',3),(13,11,'222',5),(14,11,'111223123123',5),(18,14,'Question1',5),(19,14,'Question2',5),(21,15,'Question1',5),(22,15,'Question2',5),(25,1,'asdasd',1),(26,3,'<html lang=\"en\" manifest=\"cache.appcache\"><head>\r\n  <meta charset=\"utf-8\">\r\n  <title>2048</title>\r\n\r\n  <link href=\"style/main.css\" rel=\"stylesheet\" type=\"text/css\">\r\n  <link rel=\"shortcut icon\" href=\"favicon.ico\">\r\n  <link rel=\"apple-touch-icon\" href=\"meta/apple-touch-icon.png\">\r\n  <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\r\n  <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\r\n\r\n  <meta name=\"HandheldFriendly\" content=\"True\">\r\n  <meta name=\"MobileOptimized\" content=\"320\">\r\n  <meta name=\"viewport\" content=\"width=device-width, target-densitydpi=160dpi, initial-scale=1.0, maximum-scale=1, user-scalable=no, minimal-ui\">\r\n  <meta name=\"format-detection\" content=\"telephone=no\">\r\n\r\n  <meta name=\"apple-itunes-app\" content=\"app-id=868076805\">\r\n\r\n  <meta property=\"og:title\" content=\"2048 game\">\r\n  <meta property=\"og:site_name\" content=\"2048 game\">\r\n  <meta property=\"og:description\" content=\"Join the numbers and get to the 2048 tile! Careful: this game is extremely addictive!\">\r\n  <meta property=\"og:image\" content=\"http://gabrielecirulli.github.io/2048/meta/og_image.png\">\r\n</head>\r\n<body data-twttr-rendered=\"true\">\r\n  <div class=\"container\">\r\n    <div class=\"heading\">\r\n      <h1 class=\"title\">2048</h1>\r\n      <div class=\"scores-container\">\r\n        <div class=\"score-container\">0</div>\r\n        <div class=\"best-container\">0</div>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"above-game\">\r\n      <p class=\"game-intro\">Join the numbers and get to the <strong>2048 tile!</strong></p>\r\n      <a class=\"restart-button\">New Game</a>\r\n    </div>\r\n\r\n    <div class=\"app-notice\">\r\n      <span class=\"notice-close-button\">x</span>\r\n      <p><strong class=\"important\">New:</strong> Get the new 2048 app for <a href=\"https://itunes.apple.com/us/app/2048-by-gabriele-cirulli/id868076805\" target=\"_blank\">iOS</a> and <a href=\"https://play.google.com/store/apps/details?id=com.gabrielecirulli.app2048\" target=\"_blank\">Android!</a></p>\r\n    </div>\r\n\r\n    <div class=\"game-container\">\r\n      <div class=\"game-message\">\r\n        <p></p>\r\n        <div class=\"lower\">\r\n         <a class=\"keep-playing-button\">Keep going</a>\r\n          <a class=\"retry-button\">Try again</a>\r\n          <div class=\"score-sharing\"></div>\r\n          <div class=\"mailing-list\">\r\n            <!-- MailChimp Signup Form -->\r\n            <form action=\"http://gabrielecirulli.us8.list-manage.com/subscribe/post?u=991201206817cfb4e4247ed6c&amp;id=7928ea817b\" method=\"post\" name=\"mc-embedded-subscribe-form\" class=\"mailing-list-form\" target=\"_blank\">\r\n              <strong>Get email updates from Gabriele</strong>\r\n\r\n              <input type=\"email\" value=\"\" name=\"EMAIL\" class=\"mailing-list-email-field\" placeholder=\"Your email address\" spellcheck=\"false\">\r\n\r\n              <!-- real people should not fill this in and expect good things - do not remove this or risk form bot signups-->\r\n              <div style=\"position: absolute; left: -9999px;\">\r\n                <input type=\"text\" name=\"b_991201206817cfb4e4247ed6c_7928ea817b\" value=\"\">\r\n              </div>\r\n\r\n              <input type=\"submit\" value=\"Go\" name=\"subscribe\" class=\"mailing-list-subscribe-button\">\r\n            </form>\r\n          </div>\r\n        </div>\r\n      </div>\r\n\r\n      <div class=\"grid-container\">\r\n        <div class=\"grid-row\">\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n        </div>\r\n        <div class=\"grid-row\">\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n        </div>\r\n        <div class=\"grid-row\">\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n        </div>\r\n        <div class=\"grid-row\">\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n          <div class=\"grid-cell\"></div>\r\n        </div>\r\n      </div>\r\n\r\n      <div class=\"tile-container\"><div class=\"tile tile-2 tile-position-2-1 tile-new\"><div class=\"tile-inner\">2</div></div><div class=\"tile tile-2 tile-position-4-1 tile-new\"><div class=\"tile-inner\">2</div></div></div>\r\n    </div>\r\n\r\n    <p class=\"game-explanation\">\r\n      <strong class=\"important\">How to play:</strong> Use your <strong>arrow keys</strong> to move the tiles. When two tiles with the same number touch, they <strong>merge into one!</strong>\r\n    </p>\r\n    <hr>\r\n    <p>\r\n    <strong class=\"important\">Note:</strong> The game on <a href=\"http://git.io/2048\">this site</a> is the original version of 2048. Apps for <a href=\"https://itunes.apple.com/us/app/2048-by-gabriele-cirulli/id868076805\" target=\"_blank\">iOS</a> and <a href=\"https://play.google.com/store/apps/details?id=com.gabrielecirulli.app2048\" target=\"_blank\">Android</a> are also available. Other versions are derivatives or fakes, and should be used with caution.\r\n    </p>\r\n    <hr>\r\n    <p>\r\n    Created by <a href=\"http://gabrielecirulli.com\" target=\"_blank\">Gabriele Cirulli.</a> Based on <a href=\"https://itunes.apple.com/us/app/1024!/id823499224\" target=\"_blank\">1024 by Veewo Studio</a> and conceptually similar to <a href=\"http://asherv.com/threes/\" target=\"_blank\">Threes by Asher Vollmer.</a>\r\n    </p>\r\n    <div class=\"sharing\">\r\n      <iframe id=\"twitter-widget-0\" scrolling=\"no\" frameborder=\"0\" allowtransparency=\"true\" src=\"http://platform.twitter.com/widgets/tweet_button.1400006231.html#_=1400257462553&amp;count=horizontal&amp;counturl=http%3A%2F%2Fgabrielecirulli.github.io%2F2048%2F&amp;id=twitter-widget-0&amp;lang=en&amp;original_referer=http%3A%2F%2Fgabrielecirulli.github.io%2F2048%2F&amp;size=m&amp;text=Check%20out%202048%2C%20a%20game%20where%20you%20join%20numbers%20to%20score%20high!%20%232048game&amp;url=http%3A%2F%2Fgit.io%2F2048&amp;via=gabrielecirulli\" class=\"twitter-share-button twitter-tweet-button twitter-share-button twitter-count-horizontal\" title=\"Twitter Tweet Button\" data-twttr-rendered=\"true\" style=\"width: 110px; height: 20px;\"></iframe>\r\n      <script async=\"\" src=\"//www.google-analytics.com/analytics.js\"></script><script id=\"twitter-wjs\" src=\"http://platform.twitter.com/widgets.js\"></script><script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?\'http\':\'https\';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\'://platform.twitter.com/widgets.js\';fjs.parentNode.insertBefore(js,fjs);}}(document, \'script\', \'twitter-wjs\');</script>\r\n\r\n      <form class=\"pp-donate\" action=\"https://www.paypal.com/cgi-bin/webscr\" method=\"post\" target=\"_blank\">\r\n        <input type=\"hidden\" name=\"cmd\" value=\"_s-xclick\">\r\n        <input type=\"hidden\" name=\"hosted_button_id\" value=\"NVNPJLTBZ8AME\">\r\n        <button name=\"submit\"><img src=\"meta/icon_pp.svg\">Donate</button>\r\n        <img alt=\"\" border=\"0\" src=\"https://www.paypalobjects.com/en_US/i/scr/pixel.gif\" width=\"1\" height=\"1\">\r\n      </form>\r\n\r\n      <span class=\"btc-donate\">\r\n        <a href=\"bitcoin:1Ec6onfsQmoP9kkL3zkpB6c5sA4PVcXU2i\">\r\n          <img src=\"meta/icon_bitcoin.svg\">Donate BTC\r\n        </a>\r\n        <span class=\"address\"><code>1Ec6onfsQmoP9kkL3zkpB6c5sA4PVcXU2i</code></span>\r\n      </span>\r\n\r\n    </div>\r\n  </div>\r\n\r\n  <script src=\"js/bind_polyfill.js\"></script>\r\n  <script src=\"js/classlist_polyfill.js\"></script>\r\n  <script src=\"js/animframe_polyfill.js\"></script>\r\n  <script src=\"js/keyboard_input_manager.js\"></script>\r\n  <script src=\"js/html_actuator.js\"></script>\r\n  <script src=\"js/grid.js\"></script>\r\n  <script src=\"js/tile.js\"></script>\r\n  <script src=\"js/local_storage_manager.js\"></script>\r\n  <script src=\"js/game_manager.js\"></script>\r\n  <script src=\"js/application.js\"></script>\r\n\r\n  <script>\r\n    (function(i,s,o,g,r,a,m){i[\"GoogleAnalyticsObject\"]=r;i[r]=i[r]||function(){\r\n    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\r\n    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\r\n    })(window,document,\"script\",\"//www.google-analytics.com/analytics.js\",\"ga\");\r\n\r\n    ga(\"create\", \"UA-42620757-2\", \"gabrielecirulli.github.io\");\r\n    ga(\"send\", \"pageview\");\r\n  </script>\r\n\r\n\r\n</body></html>',1),(27,13,'What is <button>asdasasd</button>',6);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'SuperAdmin'),(2,'Admin'),(3,'Teacher'),(4,'Student');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_answer`
--

DROP TABLE IF EXISTS `session_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_answer` (
  `session_answer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `session_question_id` int(10) unsigned NOT NULL,
  `answer_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`session_answer_id`),
  KEY `fk_session_answer_session_question1_idx` (`session_question_id`),
  KEY `fk_session_answer_answers1_idx` (`answer_id`),
  CONSTRAINT `fk_session_answer_session_question1` FOREIGN KEY (`session_question_id`) REFERENCES `session_question` (`session_question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_session_answer_answers1` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`answer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_answer`
--

LOCK TABLES `session_answer` WRITE;
/*!40000 ALTER TABLE `session_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_question`
--

DROP TABLE IF EXISTS `session_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_question` (
  `session_question_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `session_test_id` int(10) unsigned NOT NULL,
  `question_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`session_question_id`),
  UNIQUE KEY `session_question_id_UNIQUE` (`session_question_id`),
  KEY `fk_session_question_session_test1_idx` (`session_test_id`),
  KEY `fk_session_question_questions1_idx` (`question_id`),
  CONSTRAINT `fk_session_question_session_test1` FOREIGN KEY (`session_test_id`) REFERENCES `session_test` (`session_test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_session_question_questions1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_question`
--

LOCK TABLES `session_question` WRITE;
/*!40000 ALTER TABLE `session_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_test`
--

DROP TABLE IF EXISTS `session_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_test` (
  `session_test_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `finish_time` timestamp NULL DEFAULT NULL,
  `test_id` int(10) unsigned NOT NULL,
  `avaliable_test_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`session_test_id`,`avaliable_test_id`),
  UNIQUE KEY `session_test_id_UNIQUE` (`session_test_id`),
  KEY `fk_session_test_tests1_idx` (`test_id`),
  KEY `fk_session_test_available_tests1_idx` (`avaliable_test_id`),
  CONSTRAINT `fk_session_test_tests1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_session_test_available_tests1` FOREIGN KEY (`avaliable_test_id`) REFERENCES `available_tests` (`avaliable_test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_test`
--

LOCK TABLES `session_test` WRITE;
/*!40000 ALTER TABLE `session_test` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests` (
  `test_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int(10) unsigned NOT NULL,
  `name` varchar(70) NOT NULL,
  `grade` int(10) unsigned NOT NULL,
  `duration` int(5) NOT NULL,
  `edited` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `edited_by` varchar(100) DEFAULT NULL,
  `easy_questions` tinyint(4) NOT NULL,
  `normal_questions` tinyint(4) NOT NULL,
  `hard_questions` tinyint(4) NOT NULL,
  PRIMARY KEY (`test_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_tests_categories1_idx` (`category_id`),
  CONSTRAINT `fk_tests_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
INSERT INTO `tests` VALUES (1,5,'Java Core',10,34,'2014-05-16 13:22:13',NULL,NULL,'super'),(3,3,'Testing Rails application',40,40,'2014-05-16 13:24:41','2014-04-16 13:36:24','SuperAdmin','super'),(4,4,'MySQL basics',50,120,'2014-05-05 05:14:44','2014-04-16 13:38:27','Mr. MySQL','super'),(5,3,'test1',50,60,'2014-04-16 14:14:36','2014-04-16 14:14:37','',''),(11,1,'Java Basic',20,2,'2014-05-16 13:21:05','2014-05-06 10:06:57','super','super'),(12,10,'EnglishTest',20,2,'2014-05-06 10:15:08','2014-05-06 10:11:41','super','super'),(13,5,'newTest',6,10,'2014-05-16 13:26:52','2014-05-06 13:07:31','super','super'),(14,9,'module1',10,4,'2014-05-13 07:16:58',NULL,NULL,'super'),(15,9,'module2',10,4,'2014-05-13 07:19:19',NULL,NULL,'super'),(16,5,'fgdg',5,3,'2014-05-20 17:19:39','2014-05-20 17:19:29','super','super');
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests_results`
--

DROP TABLE IF EXISTS `tests_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests_results` (
  `test_result_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `mark_percents` int(10) unsigned NOT NULL,
  `pass_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_test_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`test_result_id`),
  KEY `fk_tests_results_tests1_idx` (`test_id`),
  KEY `fk_tests_results_students1_idx` (`user_id`),
  KEY `fk_tests_results_session_test1_idx` (`session_test_id`),
  CONSTRAINT `fk_tests_results_students1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tests_results_tests1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tests_results_session_test1` FOREIGN KEY (`session_test_id`) REFERENCES `session_test` (`session_test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests_results`
--

LOCK TABLES `tests_results` WRITE;
/*!40000 ALTER TABLE `tests_results` DISABLE KEYS */;
INSERT INTO `tests_results` VALUES (6,12,23,100,'2014-05-06 10:18:24',NULL),(7,12,23,80,'2014-05-06 12:16:31',NULL),(8,11,32,76,'2014-05-12 04:19:03',NULL),(9,11,33,52,'2014-05-12 05:22:30',NULL),(10,14,34,100,'2014-05-13 07:20:20',NULL),(11,15,34,50,'2014-05-13 07:20:26',NULL),(12,14,32,100,'2014-05-13 07:27:17',NULL),(13,12,34,40,'2014-05-13 07:54:36',NULL);
/*!40000 ALTER TABLE `tests_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `middle_name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `user_name` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `fk_students_groups1_idx` (`group_id`),
  CONSTRAINT `fk_students_groups1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'Super','Super','Admin','super','d8578edf8458ce06fbc5bb76a58c5ca4','2014-04-16 13:50:34'),(21,2,'Ivan','Vasylovych','Petrov','admin','21232f297a57a5a743894a0e4a801fc3','2014-05-19 04:35:58'),(22,3,'Igor','Petrovych','Pupkin','teacher','8d788385431273d11e8b43bb78f3aa41','2014-05-19 05:43:03'),(23,9,'Petro','Mykhalovych','Opkin','student','cd73502828457d15655bbd7a63fb0bc8','2014-05-20 06:44:41'),(26,3,'Stepan','Ivanovuch','Kozlenko','teacher1','41c8949aa55b8cb5dbec662f34b62df3','2014-05-20 05:35:42'),(27,2,'Dmytro','Dmytrovych','Kir','admin1','admin1','2014-05-19 04:35:26'),(30,9,'Mykola','Vasylovuch','Baran','qweqwe','qweqwe','2014-05-06 11:52:27'),(32,9,'Lisyu','Lis','Buben','Lys','1111','2014-05-14 08:16:11'),(33,10,'Ivan','Ivan','Ivan','Ivan','36e3491ba499df64611c82b6bcdd37d','2014-05-19 04:32:49'),(34,9,'new','new','new','1111','1111','2014-05-13 07:04:39'),(35,2,'qwerty','qwerty','qwerty','qwerty','qwerty','2014-05-14 08:16:15');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_categories`
--

DROP TABLE IF EXISTS `users_has_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_categories` (
  `users_user_id` int(10) unsigned NOT NULL,
  `categories_category_id` int(10) unsigned NOT NULL,
  KEY `fk_users_has_categories_categories1_idx` (`categories_category_id`),
  KEY `fk_users_has_categories_users1_idx` (`users_user_id`),
  CONSTRAINT `fk_users_has_categories_categories1` FOREIGN KEY (`categories_category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_categories_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_categories`
--

LOCK TABLES `users_has_categories` WRITE;
/*!40000 ALTER TABLE `users_has_categories` DISABLE KEYS */;
INSERT INTO `users_has_categories` VALUES (22,5),(22,1),(22,4),(22,2),(22,3),(26,1),(26,4);
/*!40000 ALTER TABLE `users_has_categories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-20 20:25:02
