ALTER TABLE `odontosys`.`tratamento_historico` DROP FOREIGN KEY `FKC7A16D263D654EA` ;
ALTER TABLE `odontosys`.`tratamento_historico` CHANGE COLUMN `Tratamento_id` `Tratamento_id` BIGINT(20) NOT NULL  , CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT  , 
  ADD CONSTRAINT `FKC7A16D263D654EA`
  FOREIGN KEY (`Tratamento_id` )
  REFERENCES `odontosys`.`tratamento` (`id` );

ALTER TABLE `odontosys`.`tratamento_historico` DROP FOREIGN KEY `FKC7A16D263D654EA` ;
ALTER TABLE `odontosys`.`tratamento_historico` CHANGE COLUMN `Tratamento_id` `Tratamento_id` BIGINT(20) NOT NULL  , 
  ADD CONSTRAINT `FKC7A16D263D654EA`
  FOREIGN KEY (`Tratamento_id` )
  REFERENCES `odontosys`.`tratamento` (`id` );

ALTER TABLE `odontosys`.`tratamento` ADD COLUMN `status` VARCHAR(45) NULL  AFTER `turma_id` ;

CREATE  TABLE `odontosys`.`pagamento` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `dt_pagamento` DATETIME NULL ,
  `valor` DECIMAL(8,2) NULL ,
  `id_conta` BIGINT NOT NULL ,
  `forma_pagamento` VARCHAR(45) NULL ,
  `num_cheque` VARCHAR(45) NULL ,
  `banco` VARCHAR(45) NULL ,
  `obs` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) );
