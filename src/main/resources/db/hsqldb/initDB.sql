DROP TABLE vet_specialties IF EXISTS;
DROP TABLE vets IF EXISTS;
DROP TABLE specialties IF EXISTS;
DROP TABLE visits IF EXISTS;
DROP TABLE pets IF EXISTS;
DROP TABLE types IF EXISTS;
DROP TABLE owners IF EXISTS;
DROP TABLE roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE foods IF EXISTS;
DROP TABLE meals IF EXISTS;
DROP TABLE weight_measurements IF EXISTS;
DROP TABLE athletes IF EXISTS;
DROP TABLE workouts IF EXISTS;
DROP TABLE exercises IF EXISTS;


CREATE TABLE vets (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);
CREATE INDEX vets_last_name ON vets (last_name);

CREATE TABLE specialties (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX specialties_name ON specialties (name);

CREATE TABLE vet_specialties (
  vet_id       INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL
);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_vets FOREIGN KEY (vet_id) REFERENCES vets (id);
ALTER TABLE vet_specialties ADD CONSTRAINT fk_vet_specialties_specialties FOREIGN KEY (specialty_id) REFERENCES specialties (id);

CREATE TABLE types (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE owners (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX owners_last_name ON owners (last_name);

CREATE TABLE pets (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER NOT NULL
);
ALTER TABLE pets ADD CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
ALTER TABLE pets ADD CONSTRAINT fk_pets_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX pets_name ON pets (name);

CREATE TABLE visits (
  id          INTEGER IDENTITY PRIMARY KEY,
  pet_id      INTEGER NOT NULL,
  visit_date  DATE,
  description VARCHAR(255)
);
ALTER TABLE visits ADD CONSTRAINT fk_visits_pets FOREIGN KEY (pet_id) REFERENCES pets (id);
CREATE INDEX visits_pet_id ON visits (pet_id);

CREATE  TABLE users (
  username    VARCHAR(20) NOT NULL ,
  password    VARCHAR(20) NOT NULL ,
  enabled     BOOLEAN DEFAULT TRUE NOT NULL ,
  PRIMARY KEY (username)
);

CREATE TABLE roles (
  id              INTEGER IDENTITY PRIMARY KEY,
  username        VARCHAR(20) NOT NULL,
  role            VARCHAR(20) NOT NULL
);
ALTER TABLE roles ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username);
CREATE INDEX fk_username_idx ON roles (username);

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
