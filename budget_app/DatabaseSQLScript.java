package budget_app;

public @interface DatabaseSQLScript {
   /**

    -- MySQL Workbench Forward Engineering

    SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
    SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
    SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
        -- Schema BudgetAppDB
-- -----------------------------------------------------

        -- -----------------------------------------------------
        -- Schema BudgetAppDB
-- -----------------------------------------------------
    CREATE SCHEMA IF NOT EXISTS `BudgetAppDB` DEFAULT CHARACTER SET utf8 ;
    USE `BudgetAppDB` ;

-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Users`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Users` (
            `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Income`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Income` (
            `income_id` INT NOT NULL AUTO_INCREMENT,
  `income_amount` DOUBLE NOT NULL,
            `income_source` VARCHAR(45) NOT NULL,
  `income_per_month` INT NOT NULL,
            `is_one_time` TINYINT(1) NOT NULL,
  `user_id` INT NOT NULL,
    PRIMARY KEY (`income_id`),
    INDEX `user_id_1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `BudgetAppDB`.`Users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Accounts`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Accounts` (
            `account_id` INT NOT NULL AUTO_INCREMENT,
  `account_name` VARCHAR(100) NOT NULL,
  `bank_name` VARCHAR(100) NOT NULL,
  `account_balance` DOUBLE NOT NULL,
            `user_id` INT NOT NULL,
    PRIMARY KEY (`account_id`),
    INDEX `user_id_2_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `BudgetAppDB`.`Users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Debts`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Debts` (
            `debt_id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
            `interest_rate` DOUBLE NOT NULL,
            `lender_name` VARCHAR(100) NOT NULL,
  `debt_due_date` INT(31) NULL,
  `user_id` INT NOT NULL,
    PRIMARY KEY (`debt_id`),
    INDEX `user_id_3_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id_3`
    FOREIGN KEY (`user_id`)
    REFERENCES `BudgetAppDB`.`Users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Expenses`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Expenses` (
            `expense_id` INT NOT NULL AUTO_INCREMENT,
  `expense_name` VARCHAR(100) NOT NULL,
  `expense_category` VARCHAR(100) NOT NULL,
  `expense_amount` DOUBLE NOT NULL,
            `user_id` INT NOT NULL,
    PRIMARY KEY (`expense_id`),
    INDEX `user_id_5_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id_5`
    FOREIGN KEY (`user_id`)
    REFERENCES `BudgetAppDB`.`Users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
        -- Table `BudgetAppDB`.`Custom_Goals`
            -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `BudgetAppDB`.`Custom_Goals` (
            `custom_goal_id` INT NOT NULL AUTO_INCREMENT,
  `goal_name` VARCHAR(125) NOT NULL,
  `monthly_contribution` DOUBLE NOT NULL,
            `total_needed` DOUBLE NOT NULL,
            `amount_saved` DOUBLE NOT NULL DEFAULT 0,
            `user_id` INT NOT NULL,
    PRIMARY KEY (`custom_goal_id`),
    INDEX `user_id_3_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id_4`
    FOREIGN KEY (`user_id`)
    REFERENCES `BudgetAppDB`.`Users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


    SET SQL_MODE=@OLD_SQL_MODE;
    SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
    SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
 */
}
