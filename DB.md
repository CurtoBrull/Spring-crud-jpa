# DB Data

## Name
spring_crud_jpa

## Tables
```sql
CREATE TABLE `spring_crud_jpa`.`products` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    `price` INT NULL,
    `description` TEXT NULL,
PRIMARY KEY (`id`));
```