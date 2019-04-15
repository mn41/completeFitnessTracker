INSERT IGNORE INTO athletes (username, password, email) VALUES ('abc', '123', 'a@b.com');
INSERT IGNORE INTO athletes (username, password, email) VALUES ('abcd', '1234', 'ad@bc.com');
INSERT IGNORE INTO athletes (username, password, email) VALUES ('deleteuser', 'deletepass', 'ead@bc.com');
INSERT IGNORE INTO athletes (username, password, email) VALUES ('abcd3', '12345', 'ead@bc.com');


INSERT IGNORE INTO meals (athlete_id, meal_name, meal_date, calories, fat, carbohydrates, protein) VALUES (1,'Breakfast', '2013-01-01', 170, 10, 10, 10);
INSERT IGNORE INTO meals (athlete_id, meal_name, meal_date, calories, fat, carbohydrates, protein) VALUES (1,'Lunch', '2013-01-01', 170, 10, 10, 10);
INSERT IGNORE INTO meals (athlete_id, meal_name, meal_date, calories, fat, carbohydrates, protein) VALUES (1,'Dinner', '2013-01-02', 1700, 100, 100, 100);
INSERT IGNORE INTO meals (athlete_id, meal_name, meal_date, calories, fat, carbohydrates, protein) VALUES (2,'Dinner', '2013-01-02', 1700, 100, 100, 100);


INSERT IGNORE INTO foods (meal_id, servings, food_name, calories, fat, carbohydrates, protein) VALUES (1, 1, 'test_food', 17, 1, 1, 1);
INSERT IGNORE INTO foods (meal_id, servings, food_name, calories, fat, carbohydrates, protein) VALUES (1, 1, 'test_food2', 17, 1, 1, 1);
INSERT IGNORE INTO foods (meal_id, servings, food_name, calories, fat, carbohydrates, protein) VALUES (2, 1, 'test_food3', 17, 1, 1, 1);
INSERT IGNORE INTO foods (meal_id, servings, food_name, calories, fat, carbohydrates, protein) VALUES (2, 1, 'test_food4', 17, 1, 1, 1);

INSERT IGNORE INTO workouts (athlete_id, workout_name, category, workout_date) VALUES (1, 'SS', 'Weightlifting', '2013-01-01');
INSERT IGNORE INTO workouts (athlete_id, workout_name, category, workout_date) VALUES (1, 'SS', 'Weightlifting', '2013-01-02');
INSERT IGNORE INTO workouts (athlete_id, workout_name, category, workout_date) VALUES (1, 'SS', 'Weightlifting', '2013-01-04');
INSERT IGNORE INTO workouts (athlete_id, workout_name, category, workout_date) VALUES (2, 'SS', 'Weightlifting', '2013-01-01');

INSERT IGNORE INTO exercises (workout_id, exercise_name, weight, reps, sets, elapsed_time, sequence_number, exercise_date) VALUES (1, 'Squat', 135, 5, 3, 5, 1, '2013-01-02');
INSERT IGNORE INTO exercises (workout_id, exercise_name, weight, reps, sets, elapsed_time, sequence_number, exercise_date) VALUES (1, 'Squat', 135, 5, 3, 5, 1, '2013-01-02');
INSERT IGNORE INTO exercises (workout_id, exercise_name, weight, reps, sets, elapsed_time, sequence_number, exercise_date) VALUES (1, 'Squat', 135, 5, 3, 5, 1, '2013-01-02');
INSERT IGNORE INTO exercises (workout_id, exercise_name, weight, reps, sets, elapsed_time, sequence_number, exercise_date) VALUES (1, 'Bench Press', 75, 3, 5, 5, 1, '2013-01-02');
INSERT IGNORE INTO exercises (workout_id, exercise_name, weight, reps, sets, elapsed_time, sequence_number, exercise_date) VALUES (2, 'Squat', 135, 5, 3, 5, 1, '2013-01-01');

INSERT IGNORE INTO weight_measurements (athlete_id, weight, date) VALUES (1,123,'2013-01-01');
INSERT IGNORE INTO weight_measurements (athlete_id, weight, date) VALUES (1,123,'2013-01-02');
INSERT IGNORE INTO weight_measurements (athlete_id, weight, date) VALUES (1,123,'2013-01-03');
INSERT IGNORE INTO weight_measurements (athlete_id, weight, date) VALUES (3,123,'2013-01-03');
