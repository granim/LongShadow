/*# connect to mysql and run as root user*/
/*##create database*/

CREATE ls_dev;
CREATE ls_prod;

CREATE USER 'ls_dev_user'@'localhost' IDENTIFIED BY 'grant';
CREATE USER 'ls_prod_user'@'localhost' IDENTIFIED BY 'grant';


/*#Database Grants*/
GRANT SELECT ls_dev.* to 'ls_dev_user'@'localhost';
GRANT INSERT ls_dev.* to 'ls_dev_user'@'localhost';
GRANT DELETE ls_dev.* to 'ls_dev_user'@'localhost';
GRANT UPDATE ls_dev.* to 'ls_dev_user'@'localhost';
GRANT SELECT ls_prod.* to 'ls_prod_user'@'localhost';
GRANT INSERT ls_prod.* to 'ls_prod_user'@'localhost';
GRANT DELETE ls_prod.* to 'ls_prod_user'@'localhost';
GRANT UPDATE ls_prod.* to 'ls_prod_user'@'localhost';