SET foreign_key_checks = 0;
use mydb;

INSERT INTO `categories` VALUES (5,'Design patterns'),(1,'Java'),(4,'MySQL'),(2,'Python'),(3,'Ruby');

INSERT INTO `groups` VALUES (1,1,'SuperAdmin'),(2,2,'Admin'),(3,3,'Teacher'),(4,4,'Student');


INSERT INTO `roles` VALUES (1,'SuperAdmin'),(2,'Admin'),(3,'Teacher'),(4,'Student');

INSERT INTO `users` VALUES (1,1,'Super','Super','Admin','super','d8578edf8458ce06fbc5bb76a58c5ca4','2014-04-16 16:50:34'),(2,2,'Admin','number','One','admin1','asdfg','2014-04-16 16:51:09'),(3,3,'Ivan','Ivanovych','Ivanenko','teacher01','1qaz2wsx','2014-04-16 16:52:25'),(4,4,'Vasyl','Vasylyovych','Pupkin','student001','password1','2014-04-16 16:53:48'),(5,4,'Olexandra','Petrivna','Serova','student002',',kfylsyrj','2014-04-16 16:56:01'),(6,4,'name1','mname1','sname1','student003','gsgdsgf','2014-04-16 17:11:20'),(7,2,'Admin','number','Two','admin2','zxcvbnm','2014-04-16 17:11:52');

INSERT INTO `users_has_categories` VALUES (3,1),(3,2),(3,3);
