CREATE DATABASE IF NOT EXISTS complete_fitness_tracker_db;

ALTER DATABASE complete_fitness_tracker_db
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE complete_fitness_tracker_db;

CREATE TABLE IF NOT EXISTS athletes(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username    VARCHAR(20) NOT NULL,
  password    VARCHAR(20) NOT NULL,
  email VARCHAR(50),
  UNIQUE(username)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS meals(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  athlete_id INT(4) UNSIGNED NOT NULL,
  meal_name VARCHAR(20),
  meal_date DATE,
  calories DECIMAL(10,3),
  fat DECIMAL(10,3),
  carbohydrates DECIMAL(10,3),
  protein DECIMAL(10,3),
  FOREIGN KEY (athlete_id) REFERENCES athletes(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS foods(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  meal_id INT(4) UNSIGNED NOT NULL,
  servings DECIMAL(10,3),
  food_name VARCHAR(20),
  calories DECIMAL(10,3),
  fat DECIMAL(10,3),
  carbohydrates DECIMAL(10,3),
  protein DECIMAL(10,3),
  FOREIGN KEY (meal_id) REFERENCES meals(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS weight_measurements(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  athlete_id INT(4) UNSIGNED NOT NULL,
  weight DECIMAL(10, 3),
  date DATE,
  INDEX(weight),
  FOREIGN KEY (athlete_id) REFERENCES athletes(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS workouts(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  athlete_id INT(4) UNSIGNED NOT NULL,
  workout_name VARCHAR(20),
  category VARCHAR(20),
  workout_date DATE,
  FOREIGN KEY (athlete_id) REFERENCES athletes(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS exercises(
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  workout_id INT(4) UNSIGNED NOT NULL,
  exercise_name VARCHAR(20),
  weight DECIMAL(10, 3),
  reps DECIMAL(10, 3),
  sets DECIMAL(10, 3),
  elapsed_time DECIMAL(10, 3),
  sequence_number INT(4),
  exercise_date DATE,
  FOREIGN KEY (workout_id) REFERENCES workouts(id)
) engine=InnoDB;
