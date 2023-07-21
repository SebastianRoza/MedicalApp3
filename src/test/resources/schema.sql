CREATE TABLE IF NOT EXISTS patient (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  id_Card_No VARCHAR(255) NOT NULL,
  first_Name VARCHAR(255) NOT NULL,
  last_Name VARCHAR(255) NOT NULL,
  phone_Number VARCHAR(255) NOT NULL,
  birthday DATE NOT NULL
);



INSERT INTO patient (email,password,id_Card_No,first_Name,last_Name,phone_Number,birthday) VALUES ('seba.r@gmail.com','koty123','435515','Seba','Kowalski','545543252','1999-12-15');
INSERT INTO patient (email,password,id_Card_No,first_Name,last_Name,phone_Number,birthday) VALUES ('marek.k@gmail.com','pomirdo1','65423','Marek','Komarczyk','8765634','2001-11-13');
INSERT INTO patient (email,password,id_Card_No,first_Name,last_Name,phone_Number,birthday) VALUES ('tomek.p@gmail.com','marchew1','654234','tomek','Pawlowski','4326622','12000-09-02');

CREATE TABLE IF NOT EXISTS visit (
id INT AUTO_INCREMENT PRIMARY KEY,
VISIT_TIME TIMESTAMP NOT NULL,
FOREIGN KEY (PATIENT_ID) REFERENCES patient(id)
);

INSERT INTO visit (VISIT_TIME,PATIENT_ID) VALUES ('2034-12-12T12:00:00',NULL);
INSERT INTO visit (VISIT_TIME,PATIENT_ID) VALUES ('2031-12-12T14:00:00',1);