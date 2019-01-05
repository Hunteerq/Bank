DROP TABLE IF EXISTS client_account;
DROP TABLE IF EXISTS currency_exchange;
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS card_type;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS account_type;
DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS recurring_transfer;

CREATE TABLE login (
  username VARCHAR(20) PRIMARY KEY,
  password VARCHAR(32) NOT NULL,
  color SMALLINT NOT NULL DEFAULT 100
);

CREATE TABLE currency (
  id SERIAL PRIMARY KEY,
  name VARCHAR(3) NOT NULL,
  type VARCHAR(16) NOT NULL
);

CREATE TABLE currency_exchange (
  id SERIAL PRIMARY KEY,
  rate DOUBLE PRECISION NOT NULL,
  currency_id INTEGER NOT NULL, FOREIGN KEY(currency_id) REFERENCES currency(id)
);

CREATE TABLE account_type (
  id SERIAL PRIMARY KEY,
  type VARCHAR(20) NOT NULL
);

CREATE TABLE account (
  number VARCHAR(16) PRIMARY KEY,
  balance DOUBLE PRECISION NOT NULL,
  currency_id INTEGER NOT NULL, FOREIGN KEY(currency_id) REFERENCES currency(id),
  account_type_id INTEGER NOT NULL, FOREIGN KEY(account_type_id) REFERENCES account_type(id),
  opening_date DATE NOT NULL,
  protection BOOLEAN NOT NULL,
  active BOOLEAN NOT NULL
);

CREATE TABLE card_type (
  id SERIAL PRIMARY KEY,
  type VARCHAR(20) NOT NULL
);

CREATE TABLE card (
  number VARCHAR(16) PRIMARY KEY,
  account_number VARCHAR(16) NOT NULL, FOREIGN KEY(account_number) REFERENCES account(number),
  cvv VARCHAR(3) NOT NULL,
  expiration_date DATE NOT NULL,
  type_id INTEGER NOT NULL, FOREIGN KEY(type_id) REFERENCES card_type(id),
  active BOOLEAN NOT NULL,
  daily_contactless_limit DOUBLE PRECISION,
  daily_total_limit DOUBLE PRECISION,
  daily_web_limit DOUBLE PRECISION
);

CREATE TABLE address (
  id SERIAL PRIMARY KEY,
  street VARCHAR(30) NOT NULL,
  street_number VARCHAR(12) NOT NULL,
  flat_number INTEGER,
  postal_code VARCHAR(8) NOT NULL,
  city VARCHAR(30) NOT NULL,
  country VARCHAR(30) NOT NULL
);

CREATE TABLE client (
  pesel VARCHAR(11) PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  surname VARCHAR(20) NOT NULL,
  email VARCHAR(40) NOT NULL,
  address_id INTEGER NOT NULL, FOREIGN KEY(address_id) REFERENCES address(id),
  username VARCHAR(20) NOT NULL, FOREIGN KEY(username) REFERENCES login(username)
);

CREATE TABLE client_account (
  id SERIAL PRIMARY KEY,
  client_pesel VARCHAR(11) NOT NULL, FOREIGN KEY(client_pesel) REFERENCES client(pesel),
  account_number VARCHAR(16) NOT NULL, FOREIGN KEY(account_number) REFERENCES account(number)
);

CREATE TABLE loan(
  id SERIAL PRIMARY KEY,
  account_number VARCHAR(16) NOT NULL, FOREIGN KEY(account_number) REFERENCES account(number),
  amount DOUBLE PRECISION NOT NULL,
  begin_date DATE NOT NULL,
  months_amount SMALLINT NOT NULL,
  already_paid DOUBLE PRECISION NOT NULL CHECK(already_paid<=amount),
  id_currency SMALLINT NOT NULL
);

CREATE TABLE recurring_transfer (
  id SERIAL PRIMARY KEY,
  begin_date BIGINT NOT NULL,
  cycle BIGINT NOT NULL,
  account_number VARCHAR(16) NOT NULL,
  amount DOUBLE PRECISION NOT NULL
);

--Currencies
INSERT INTO currency VALUES(1, 'PLN', 'currency');
INSERT INTO currency_exchange VALUES(1, 1.0, 1);

INSERT INTO currency VALUES(2, 'USD', 'currency');
INSERT INTO currency_exchange VALUES(2, 3.7782, 2);

INSERT INTO currency VALUES(3, 'GBP', 'currency');
INSERT INTO currency_exchange VALUES(3, 4.7933, 3);

INSERT INTO currency VALUES(4, 'EUR', 'currency');
INSERT INTO currency_exchange VALUES(4, 4.2853, 4);

INSERT INTO currency VALUES(5, 'CHF', 'currency');
INSERT INTO currency_exchange VALUES(5, 3.7765, 5);

INSERT INTO currency VALUES(6, 'KRW', 'currency');
INSERT INTO currency_exchange VALUES(6, 0.003393, 6);

INSERT INTO currency VALUES(7, 'JPY', 'currency');
INSERT INTO currency_exchange VALUES(7, 0.03326, 7);

INSERT INTO currency VALUES(8, 'RUB', 'currency');
INSERT INTO currency_exchange VALUES(8, 0.0566, 8);

INSERT INTO currency VALUES(9, 'HKD', 'currency');
INSERT INTO currency_exchange VALUES(9, 0.4807, 9);

INSERT INTO currency VALUES(10, 'SGD', 'currency');
INSERT INTO currency_exchange VALUES(10, 2.7514, 10);

INSERT INTO currency VALUES (11, 'NZD', 'currency');
INSERT INTO currency_exchange VALUES (11, 2.6141, 11);

INSERT INTO currency VALUES(12, 'RON', 'currency');
INSERT INTO currency_exchange VALUES(12, 0.9202, 12);

--Cryptocurrencies
INSERT INTO currency VALUES(13, 'BTC', 'crypto');
INSERT INTO currency_exchange VALUES(13, 14835.58, 13);

INSERT INTO currency VALUES(14, 'ETH', 'crypto');
INSERT INTO currency_exchange VALUES(14, 413.10, 14);

INSERT INTO currency VALUES(15, 'BCH', 'crypto');
INSERT INTO currency_exchange VALUES(15, 597.52, 15);

INSERT INTO currency VALUES(16, 'LTC', 'crypto');
INSERT INTO currency_exchange VALUES(16, 120.72, 16);

INSERT INTO currency VALUES(17, 'ADA', 'crypto');
INSERT INTO currency_exchange VALUES(17, 0.14, 17);

INSERT INTO currency VALUES(18, 'NEO', 'crypto');
INSERT INTO currency_exchange VALUES(18, 28.34, 18);

INSERT INTO currency VALUES(19, 'IOT', 'crypto');
INSERT INTO currency_exchange VALUES(19, 1.02, 19);

INSERT INTO currency VALUES(20, 'LSK', 'crypto');
INSERT INTO currency_exchange VALUES(20, 5.33, 20);

INSERT INTO currency VALUES(21, 'XRB', 'crypto');
INSERT INTO currency_exchange VALUES(21, 6.21, 21);

INSERT INTO currency VALUES(22, 'ZEC', 'crypto');
INSERT INTO currency_exchange VALUES (22, 278.84, 22);

INSERT INTO currency VALUES(23, 'BCN', 'crypto');
INSERT INTO currency_exchange VALUES(23, 0.00280, 23);



--Merta
INSERT INTO address(street, street_number, flat_number, postal_code, city, country)
VALUES ('Andrych', '398', NULL, '35-691', 'Krakow', 'Poland');
INSERT INTO login VALUES('fmerta', MD5('flak35'), 35);
INSERT INTO client(pesel, name, surname, email, address_id, username)
VALUES('96121935590', 'Filip', 'Merta', 'filip.merta@gmail.com', 1, 'fmerta');
INSERT INTO account_type VALUES (1, 'student_account');
INSERT INTO currency VALUES (1, 'PLN');
INSERT INTO account VALUES ('2222444466661111', 35695, 1, 1, now(), false, false);
INSERT INTO client_account(client_pesel, account_number) VALUES ('96121935590', '2222444466661111');
INSERT INTO card VALUES ('2874922495120811', '2222444466661111', '978', '12-11-2022', 4, true, 250.0, 1000.0, 1000.0);





--Kulig
INSERT INTO address(street, street_number, flat_number, postal_code, city, country)
VALUES('Sarmacka', '1a', 4, '30-711', 'Kraków', 'Poland');
INSERT INTO login VALUES('hunteerq', MD5('admin'), 100);
INSERT INTO client VALUES('97121004520', 'Artur', 'Kulig', 'artur.kulig6@gmail.com', 2, 'hunteerq');
INSERT INTO account VALUES('1111444422221111', 2137, 1, 1, now(), false, false);
INSERT INTO account VALUES('4444555522228888', 4179, 1, 1, now(), false, false);
INSERT INTO account VALUES('9999111122223333', 9612452, 1, 1, now(), false, false);
INSERT INTO account VALUES('9999000099990000', 0, 13, 1, now(), false, false);
INSERT INTO client_account(client_pesel, account_number) VALUES('97121004520', '1111444422221111');
INSERT INTO client_account(client_pesel, account_number) VALUES('97121004520', '4444555522228888');
INSERT INTO client_account(client_pesel, account_number) VALUES('97121004520', '9999111122223333');
INSERT INTO client_account(client_pesel, account_number) VALUES('97121004520', '9999000099990000');

--Krzemień
INSERT INTO login VALUES ('fkamien', MD5('dupa'), 2);
INSERT INTO address VALUES(3, 'Felicjanek', '16', 7, '31-104', 'Kraków', 'Poland');
INSERT INTO client VALUES('97022512512', 'Filip', 'Krzemień', 'filip.krz@interia.pl', 3, 'fkamien');
INSERT INTO account VALUES('6911221371488420', 100000000, 1, 1, now(), false, false)
INSERT INTO client_account(client_pesel, account_number) VALUES('97022512512', '6911221371488420');




INSERT INTO card_type VALUES (1, 'normal');
INSERT INTO card_type VALUES (2, 'gold');
INSERT INTO card_type VALUES (3, 'basic');
INSERT INTO card_type VALUES (4, 'student');
INSERT INTO card_type VALUES (5, 'platinum');

--Cards Kulig
INSERT INTO card VALUES ('4444222255551111', '9999111122223333', '421', '11-11-2021', 1, true, 250.0, 1000.0, 1000);
INSERT INTO card VALUES ('6666222299990000', '9999000099990000', '712', '11-12-2021', 2, true, 250.0, 1000.0, 1000);
INSERT INTO card VALUES ('3333777711112222', '9999000099990000', '123', '11-11-2022', 5, true, 250.0, 1000.0, 1000.0);
INSERT INTO card VALUES ('6612551299880121', '9999000099990000', '742', '12-11-2022', 3, true, 250.0, 1000.0, 1000.0);
INSERT INTO card VALUES ('8672521495820321', '9999000099990000', '478', '12-11-2022', 4, true, 250.0, 1000.0, 1000.0);


--Getting all info about clients and accounts
SELECT client.*, account.* FROM client
INNER JOIN client_account ON client.pesel = client_account.client_pesel
INNER JOIN account ON account.number = client_account.account_number

--Getting all info about clients and cards

SELECT client.*, card.* FROM client
INNER JOIN client_account ON client_account.client_pesel = client.pesel
INNER JOIN account ON account.number = client_account.account_number
INNER JOIN card ON card.account_number = account.number

--Getting credit cards and credit cards type from specified username
SELECT card.number, card_type.type FROM card 
INNER JOIN account ON account.number = card.account_number
INNER JOIN client_account ON client_account.account_number = account.number
INNER JOIN client ON client.pesel = client_account.client_pesel AND client.username = XXXXXX
INNER JOIN card_type ON card_type.id = card.type_id 

--Getting all currencies info
SELECT currency.name, currency_exchange.rate FROM currency
INNER JOIN currency_exchange on currency.id = currency_exchange.currency_id