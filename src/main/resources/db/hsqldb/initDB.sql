DROP TABLE foods IF EXISTS;
DROP TABLE meals IF EXISTS;
DROP TABLE weight_measurements IF EXISTS;
DROP TABLE athletes IF EXISTS;
DROP TABLE workouts IF EXISTS;
DROP TABLE exercises IF EXISTS;

CREATE TABLE athletes (
  id      INTEGER IDENTITY PRIMARY KEY,
  username    VARCHAR(20) NOT NULL UNIQUE,
  password    VARCHAR(20) NOT NULL,
  email     VARCHAR(50)
)

CREATE TABLE meals (
  id         INTEGER IDENTITY PRIMARY KEY,
  athlete_id INTEGER NOT NULL,
  meal_name VARCHAR(20),
  meal_date DATE,
  calories DECIMAL,
  fat DECIMAL,
  carbohydrates DECIMAL,
  protein DECIMAL,
);

ALTER TABLE meals ADD CONSTRAINT fk_meals_athletes FOREIGN KEY (athlete_id) REFERENCES athletes (id);
CREATE INDEX meals_athlete_id ON meals (athlete_id);

CREATE TABLE foods (
  id         INTEGER IDENTITY PRIMARY KEY,
  meal_id      INTEGER NOT NULL,
  servings DECIMAL,
  food_name VARCHAR(20),
  calories DECIMAL,
  fat DECIMAL,
  carbohydrates DECIMAL,
  protein DECIMAL,
);
ALTER TABLE foods ADD CONSTRAINT fk_foods_meals FOREIGN KEY (meal_id) REFERENCES meals (id) ON DELETE CASCADE;
CREATE INDEX foods_meal_id ON foods (meal_id);

CREATE TABLE weight_measurements (
  id         INTEGER IDENTITY PRIMARY KEY,
  athlete_id INTEGER,
  weight DECIMAL,
  date DATE,
);
ALTER TABLE weight_measurements ADD CONSTRAINT fk_weight_measurements_athletes FOREIGN KEY (athlete_id) REFERENCES athletes (id);
CREATE INDEX weight_measurements_athlete_id ON weight_measurements (athlete_id);

CREATE TABLE workouts (
  id         INTEGER IDENTITY PRIMARY KEY,
  athlete_id INTEGER NOT NULL,
  workout_name VARCHAR(20),
  category VARCHAR(20),
  workout_date DATE,
);

ALTER TABLE workouts ADD CONSTRAINT fk_workouts_athletes FOREIGN KEY (athlete_id) REFERENCES athletes (id);
CREATE INDEX workouts_athlete_id ON workouts (athlete_id);

CREATE TABLE exercises (
  id         INTEGER IDENTITY PRIMARY KEY,
  workout_id      INTEGER NOT NULL,
  exercise_name VARCHAR(20),
  weight DECIMAL,
  reps DECIMAL,
  sets DECIMAL,
  elapsed_time DECIMAL,
  sequence_number INTEGER,
  exercise_date DATE,
);

ALTER TABLE exercises ADD CONSTRAINT fk_exercises_workouts FOREIGN KEY (workout_id) REFERENCES workouts (id) ON DELETE CASCADE;
CREATE INDEX exercises_workout_id ON exercises (workout_id);
