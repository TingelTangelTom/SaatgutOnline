
DROP TABLE IF EXISTS steuerklasse;
CREATE TABLE steuerklasse (
  steuerklasse_id int NOT NULL auto_increment,
  steuerklasse_titel varchar(32) NOT NULL,
  steuerklasse_beschreibung varchar(255) NOT NULL,
  PRIMARY KEY (steuerklasse_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS steuersatz;
CREATE TABLE steuersatz (
  steuersatz_id int NOT NULL auto_increment,
  steuerklasse_id int NOT NULL,
  steuersatz float NOT NULL,
  steuersatz_beschreibung varchar(255) NOT NULL,
  PRIMARY KEY (steuersatz_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS kategorie;
CREATE TABLE kategorie (
  kategorie_id int unsigned NOT NULL AUTO_INCREMENT,
  kategorie_name varchar(255) NOT NULL,
  eltern_id int unsigned,
  sortier_reihenfolge int,
  PRIMARY KEY (kategorie_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS dokumente;
CREATE TABLE dokumente (
  dokumente_id int(11) NOT NULL auto_increment,
  dokumente_dateiname varchar(64) default NULL,
  zuletzt_geaendert datetime NULL,
  erstellt datetime NOT NULL,
  PRIMARY KEY (dokumente_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS produkt;
CREATE TABLE produkt (
  produkt_id int unsigned NOT NULL AUTO_INCREMENT,
  kategorie_id int unsigned NOT NULL,
  produkt_bestand int NOT NULL,
  produkt_bild varchar(64),
  produkt_preis float NOT NULL,
  produkt_gewicht float,
  produkt_steuer_id int unsigned,
  produkt_datum_hinzugefuegt datetime NOT NULL,
  produkt_datum_geaendert datetime,
  PRIMARY KEY (produkt_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS produktbeschreibung;
CREATE TABLE produktbeschreibung (
  produkt_id int unsigned NOT NULL AUTO_INCREMENT,
  sprache_id int unsigned NOT NULL,
  produkt_name varchar(255) NOT NULL,
  produkt_beschreibung text,
  produkt_suchbegriffe varchar(255),
  produkt_angesehen int unsigned DEFAULT 0,
  PRIMARY KEY (produkt_id, sprache_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS produktmerkmal;
CREATE TABLE produktmerkmal (
  produktmerkmal_id int NOT NULL,
  sprache_id int unsigned NOT NULL,
  produktmerkmal_name varchar(255) NOT NULL
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS produktmerkmal_zuordnung;
CREATE TABLE produktmerkmal_zuordnung (
  produkt_id int unsigned NOT NULL,
  sprache_id int unsigned NOT NULL,
  produktmerkmal_id int unsigned NOT NULL,
  produktmerkmal_wert varchar(255) NOT NULL
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS bestellung;
CREATE TABLE bestellung (
  bestellung_id int NOT NULL auto_increment,
  kunde_id int NOT NULL,
  kunde_vorname varchar(255) NOT NULL,
  kunde_nachname varchar(255) NOT NULL,
  kunde_firma varchar(255),
  kunde_strasse varchar(255) NOT NULL,
  kunde_hausnummer varchar(255) NOT NULL,
  kunde_stadt varchar(255) NOT NULL,
  kunde_postleitzahl varchar(255) NOT NULL,
  kunde_bundesland varchar(255),
  kunde_land varchar(255) NOT NULL,
  kunde_telefon varchar(255) NOT NULL,
  kunde_email_adresse varchar(255) NOT NULL,
  rechnung_kunde_id int NOT NULL,
  rechnung_kunde_vorname varchar(255) NOT NULL,
  rechnung_kunde_nachname varchar(255) NOT NULL,
  rechnung_kunde_firma varchar(255),
  rechnung_kunde_strasse varchar(255) NOT NULL,
  rechnung_kunde_hausnummer varchar(255) NOT NULL,
  rechnung_kunde_stadt varchar(255) NOT NULL,
  rechnung_kunde_postleitzahl varchar(255) NOT NULL,
  rechnung_kunde_bundesland varchar(255),
  rechnung_kunde_land varchar(255) NOT NULL,
  rechnung_kunde_telefon varchar(255) NOT NULL,
  rechnung_kunde_email_adresse varchar(255) NOT NULL,
  lieferung_kunde_id int NOT NULL,
  lieferung_kunde_vorname varchar(255) NOT NULL,
  lieferung_kunde_nachname varchar(255) NOT NULL,
  lieferung_kunde_firma varchar(255),
  lieferung_kunde_strasse varchar(255) NOT NULL,
  lieferung_kunde_hausnummer varchar(255) NOT NULL,
  lieferung_kunde_stadt varchar(255) NOT NULL,
  lieferung_kunde_postleitzahl varchar(255) NOT NULL,
  lieferung_kunde_bundesland varchar(255),
  lieferung_kunde_land varchar(255) NOT NULL,
  lieferung_kunde_telefon varchar(255) NOT NULL,
  lieferung_kunde_email_adresse varchar(255) NOT NULL,
  zahlungsmethode varchar(255) NOT NULL,
  datum_kauf datetime,
  datum_versendet datetime,
  bestellstatus_id int(5) NOT NULL,
  waehrungs_id char(3),
  kaufpreis float,
  bestellung_gesamtpreis_brutto float NOT NULL,
  bestellung_gesamtpreis_netto float NOT NULL,
  bestellung_gesamtpreis_steuer float NOT NULL,
  bestellung_gesamtpreis_versand float NOT NULL,
  PRIMARY KEY (bestellung_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS bestellung_produkte;
CREATE TABLE bestellung_produkte (
  bestellung_produkte_id int NOT NULL auto_increment,
  bestellung_id int NOT NULL,
  produkt_id int NOT NULL,
  produkt_name varchar(64) NOT NULL,
  produkt_preis_brutto float NOT NULL,
  produkt_preis_netto float NOT NULL,
  produkt_steuer float NOT NULL,
  produkt_anzahl int(2) NOT NULL,
  PRIMARY KEY (bestellung_produkte_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS bestellstatus;
CREATE TABLE bestellstatus (
   bestellstatus_id int NOT NULL auto_increment,
   status_id int DEFAULT '0' NOT NULL,
   sprache_id int DEFAULT '1' NOT NULL,
   bestellstatus_name varchar(32) NOT NULL,
   PRIMARY KEY (bestellstatus_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS kunde;
CREATE TABLE kunde (
  kunde_id int NOT NULL,
  kunde_geschlecht char(1),
  kunde_vorname varchar(255) NOT NULL,
  kunde_nachname varchar(255) NOT NULL,
  kunde_geburtsdatum datetime NOT NULL,
  kunde_firma varchar(255),
  kunde_strasse varchar(255) NOT NULL,
  kunde_hausnummer varchar(255) NOT NULL,
  kunde_stadt varchar(255) NOT NULL,
  kunde_postleitzahl varchar(255) NOT NULL,
  kunde_bundesland varchar(255),
  kunde_land varchar(255) NOT NULL,
  kunde_telefon varchar(255) NOT NULL,
  kunde_email_adresse varchar(255) NOT NULL,
  kunde_passwort varchar(60) NOT NULL,
  kunde_newsletter char(1),
  PRIMARY KEY (kunde_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS warenkorb;
CREATE TABLE warenkorb (
  warenkorb_id int NOT NULL auto_increment,
  kunde_id int NOT NULL,
  products_id tinytext NOT NULL,
  produkt_anzahl int(2) NOT NULL,
  hinzugefuegt_am datetime NOT NULL,
  PRIMARY KEY (warenkorb_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS warenkorb_produkte;
CREATE TABLE warenkorb_produkte (
  warenkorb_produkte_id int NOT NULL auto_increment,
  warenkorb_id int NOT NULL,
  produkt_id int NOT NULL,
  PRIMARY KEY (warenkorb_produkte_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS newsletter;
CREATE TABLE newsletter (
  newsletter_id int NOT NULL auto_increment,
  name varchar(255) NOT NULL,
  inhalt text NOT NULL,
  hinzugefuegt_am datetime NOT NULL,
  versendet_am datetime,
  PRIMARY KEY (newsletter_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS sprache;
CREATE TABLE sprache (
  sprache_id int NOT NULL auto_increment,
  name varchar(32)  NOT NULL,
  bild varchar(64),
  PRIMARY KEY (sprache_id)
);

DROP TABLE IF EXISTS land;
CREATE TABLE land (
  land_id int NOT NULL auto_increment,
  land_name varchar(255) NOT NULL,
  land_iso_code_2 char(2) NOT NULL,
  land_iso_code_3 char(3) NOT NULL,
  PRIMARY KEY (land_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS bundesland;
CREATE TABLE bundesland (
  bundesland_id int NOT NULL auto_increment,
  bundesland_land_id int NOT NULL,
  bundesland_code varchar(32) NOT NULL,
  bundesland_name varchar(255) NOT NULL,
  PRIMARY KEY (bundesland_id)
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

/*
DROP TABLE IF EXISTS produktmerkmal;
CREATE TABLE produktmerkmal (
  sprache_id int unsigned NOT NULL,
  produktmerkmal_id int NOT NULL,
  produktmerkmal_name varchar(255) NOT NULL
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS produktmerkmal_zuordnung;
CREATE TABLE produktmerkmal_zuordnung (
  produkt_id int unsigned NOT NULL,
  sprache_id int unsigned NOT NULL,
  produktmerkmal_id int unsigned NOT NULL,
  produktmerkmal_wert varchar(255) NOT NULL
) ENGINE=MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;
  */
  
  
# Produktmerkmale
INSERT INTO produktmerkmal VALUES (1, 1, 'Frostbeständigkeit');
INSERT INTO produktmerkmal VALUES (2, 1, 'frost resistance');
INSERT INTO produktmerkmal VALUES (1, 2, 'Standort');
INSERT INTO produktmerkmal VALUES (2, 2, 'place');
INSERT INTO produktmerkmal VALUES (1, 3, 'Nährstoffbedarf');
INSERT INTO produktmerkmal VALUES (2, 3, 'nutritional requirement');
INSERT INTO produktmerkmal VALUES (1, 4, 'Luftfeuchtigkeit');
INSERT INTO produktmerkmal VALUES (2, 4, 'air humidity');
INSERT INTO produktmerkmal VALUES (1, 5, 'Boden');
INSERT INTO produktmerkmal VALUES (2, 5, 'ground');
INSERT INTO produktmerkmal VALUES (1, 6, 'Wasser');
INSERT INTO produktmerkmal VALUES (2, 6, 'water');

# Produktmerkmale Zuordnung
INSERT INTO produktmerkmal_zuordnung VALUES (1, 1, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 1, 2, 'Wert1de');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 2, 1, 'Wert2');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 2, 2, 'Wert2de');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 3, 1, 'Wert3');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 3, 2, 'Wert3de');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 4, 1, 'Wert4');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 4, 2, 'Wert4de');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 5, 1, 'Wert5');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 5, 2, 'Wert5de');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 6, 1, 'Wert6');
INSERT INTO produktmerkmal_zuordnung VALUES (1, 6, 2, 'Wert6de');

INSERT INTO produktmerkmal_zuordnung VALUES (2, 1, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 1, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 2, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 2, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 3, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 3, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 4, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 4, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 5, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 5, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 6, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (2, 6, 2, 'Wert1');

INSERT INTO produktmerkmal_zuordnung VALUES (3, 1, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 1, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 2, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 2, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 3, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 3, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 4, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 4, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 5, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 5, 2, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 6, 1, 'Wert1');
INSERT INTO produktmerkmal_zuordnung VALUES (3, 6, 2, 'Wert1');

# Sprachen
INSERT INTO sprache VALUES (1, 'en', 'Englisch');
INSERT INTO sprache VALUES (2, 'de', 'Deutsch');

# Steuerklassen
INSERT INTO steuerklasse VALUES (1, 'Normaler Steuersatz', 'Alle Waren, die nicht dem erm&auml;&szlig;igten Steuersatz unterliegen.');
INSERT INTO steuerklasse VALUES (2, 'Erm&auml;&szlig;igter Steuersatz', 'Lebensmittel, B&uuml;cher oder Zeitungen, aber auch Leistungen im &ouml;ffentlichen Nahverkehr oder Kulturangebote');

# Steuersätze
INSERT INTO steuersatz VALUES (1, 1, 19.00, 'Normaler Steuersatz');
INSERT INTO steuersatz VALUES (2, 2, 7.00, 'Ermäßigter Steuersatz');

# Kategorien
INSERT INTO kategorie VALUES (1, 'Palmen', '0', '0');
INSERT INTO kategorie VALUES (2, 'Bananenstauden', '0', '1');
INSERT INTO kategorie VALUES (3, 'Frostharte Arten', '1', '0');
INSERT INTO kategorie VALUES (4, 'Winterharte Arten', '1', '1');
INSERT INTO kategorie VALUES (5, 'Bedingt winterharte Arten', '1', '2');
INSERT INTO kategorie VALUES (6, 'Frostharte Arten', '2', '0');
INSERT INTO kategorie VALUES (7, 'Winterharte Arten', '2', '1');
INSERT INTO kategorie VALUES (8, 'Bedingt winterharte Arten', '2', '2');

# Produkte
INSERT INTO produkt values (1, 1, 10, 'resources/bilder/phoenix_canariensis.jpg', 100.10, 23.5, 1, now(), NULL);
INSERT INTO produkt values (2, 1, 20, 'resources/bilder/phoenix_canariensis.jpg', 40.10, 11.0, 1, now(), NULL);
INSERT INTO produkt values (3, 1, 25, 'resources/bilder/phoenix_canariensis.jpg', 10, 5.0, 1, now(), NULL);

# Produktmerkmale
#INSERT INTO produkt values (1, 1, 1, 'resources/bilder/phoenix_canariensis.jpg', 100.10, 23.5, 1, now(), NULL);

# Produktbeschreibung Deutsch
INSERT INTO produktbeschreibung values (1, 1, 'Kanarische Dattelpalme - Phoenix canariensis', 'Lieferhöhe ca. 300-350cm mit Topf Wichtig! Die Pflanzen standen in den Töpfen auf dem Feld und wurzelten in den Boden. Beim verladen werden diese von den Boden gekappt und zusammen gebunden. Dies ist nicht schlimm und wird immer so gemacht wenn diese in größere Töpfe gepflanzt werden.
Man muss nur darauf achten das sie bei Ankunft sofort einige Zeit im Wasser stehen, damit der Ballen sich vollsaugen kann. Dann in einen größeren Topf pflanzen und diese noch einige Zeit zusammen gebunden halten. Das hilft der Pflanze besser zu wurzeln. SIe sollte in dieser Zeit warm stehen und nicht austrocknen. Wenig Geld für eine große Palme, die wenn man dieses beachtet viel Freude bereitet.', 'Palme Phoenix winterhart', NULL);
INSERT INTO produktbeschreibung values (2, 1, 'Winterharte Hanfpalme - Trachycarpus fortunei ', '', 'Palme Trachycarpus winterhart', NULL);
INSERT INTO produktbeschreibung values (3, 1, 'CHILENISCHE HONIGPALME - Jubaea chilensis 50-60', 'Die Honigpalme Jubaea chilensis, welche wie der Name verrät aus Chile stammt, ist eine sehr langsam wachsende, pflegeleichte Fiederpalme. Mit bis zu 1,5 Meter  dicken Stamm und Blättern, die bis zu 5m lang werden können, ist sie die größte Fiederpalme, die man in Deutschland frei auspflanzen kann. Ausgewachsene Exemplare von rund 25m
 sind bereits weit über 100 Jahre alt. Die Frosttoleranz liegt bei ?10/ 13°C, kurzzeitig sogar bis ?16°C, solange das Herz trocken bleibt. Sie braucht einen vollsonnigen Standort und mäßig viel Wasser im Sommer. Im Winter sollte auch die ersten Jahre ein Regenschutz angebracht werden. Lieferhöhe ca. 3,00m mit Topf und ca. 50-60cm Stamm.
 Sie trägt erst ab einem Alter von 60 Jahren gelbe Beeren an großen Blütenständen, deren Samen man essen kann und die sowohl wie eine kleine Kokosnuss aussehen als auch wie eine schmecken. Jubaea chilensis ist neben der Hanfpalme eine weitere Palmenart, die in Deutschland und in Teilen Europas frei ausgepflanzt werden kann.', 'Palme winterhart Honigpalme', NULL);

# Produktbeschreibung Englisch 
INSERT INTO produktbeschreibung values (4, 1, 'Kanarische Dattelpalme - Phoenix canariensis', 'Lieferhöhe ca. 300-350cm mit Topf Wichtig! Die Pflanzen standen in den Töpfen auf dem Feld und wurzelten in den Boden. Beim verladen werden diese von den Boden gekappt und zusammen gebunden. Dies ist nicht schlimm und wird immer so gemacht wenn diese in größere Töpfe gepflanzt werden.
Man muss nur darauf achten das sie bei Ankunft sofort einige Zeit im Wasser stehen, damit der Ballen sich vollsaugen kann. Dann in einen größeren Topf pflanzen und diese noch einige Zeit zusammen gebunden halten. Das hilft der Pflanze besser zu wurzeln. SIe sollte in dieser Zeit warm stehen und nicht austrocknen. Wenig Geld für eine große Palme, die wenn man dieses beachtet viel Freude bereitet.', 'Palme Phoenix winterhart', NULL);
INSERT INTO produktbeschreibung values (5, 1, 'Winterharte Hanfpalme - Trachycarpus fortunei ', '', 'Palme Trachycarpus winterhart', NULL);
INSERT INTO produktbeschreibung values (6, 1, 'CHILENISCHE HONIGPALME - Jubaea chilensis 50-60', 'Die Honigpalme Jubaea chilensis, welche wie der Name verrät aus Chile stammt, ist eine sehr langsam wachsende, pflegeleichte Fiederpalme. Mit bis zu 1,5 Meter  dicken Stamm und Blättern, die bis zu 5m lang werden können, ist sie die größte Fiederpalme, die man in Deutschland frei auspflanzen kann. Ausgewachsene Exemplare von rund 25m
 sind bereits weit über 100 Jahre alt. Die Frosttoleranz liegt bei ?10/ 13°C, kurzzeitig sogar bis ?16°C, solange das Herz trocken bleibt. Sie braucht einen vollsonnigen Standort und mäßig viel Wasser im Sommer. Im Winter sollte auch die ersten Jahre ein Regenschutz angebracht werden. Lieferhöhe ca. 3,00m mit Topf und ca. 50-60cm Stamm.
 Sie trägt erst ab einem Alter von 60 Jahren gelbe Beeren an großen Blütenständen, deren Samen man essen kann und die sowohl wie eine kleine Kokosnuss aussehen als auch wie eine schmecken. Jubaea chilensis ist neben der Hanfpalme eine weitere Palmenart, die in Deutschland und in Teilen Europas frei ausgepflanzt werden kann.', 'Palme winterhart Honigpalme', NULL);
 
# Länder
INSERT INTO land VALUES (81, '  Deutschland', 'DE', 'DEU');
INSERT INTO land VALUES (14, '  Österreich', 'AT', 'AUT');
INSERT INTO land VALUES (204, '  Schweiz', 'CH', 'CHE');
INSERT INTO land VALUES (240, ' -------------', '', '');
INSERT INTO land VALUES (1, 'Afghanistan', 'AF', 'AFG');
INSERT INTO land VALUES (2, 'Albania', 'AL', 'ALB');
INSERT INTO land VALUES (3, 'Algeria', 'DZ', 'DZA');
INSERT INTO land VALUES (4, 'American Samoa', 'AS', 'ASM');
INSERT INTO land VALUES (5, 'Andorra', 'AD', 'AND');
INSERT INTO land VALUES (6, 'Angola', 'AO', 'AGO');
INSERT INTO land VALUES (7, 'Anguilla', 'AI', 'AIA');
INSERT INTO land VALUES (8, 'Antarctica', 'AQ', 'ATA');
INSERT INTO land VALUES (9, 'Antigua and Barbuda', 'AG', 'ATG');
INSERT INTO land VALUES (10, 'Argentina', 'AR', 'ARG');
INSERT INTO land VALUES (11, 'Armenia', 'AM', 'ARM');
INSERT INTO land VALUES (12, 'Aruba', 'AW', 'ABW');
INSERT INTO land VALUES (13, 'Australia', 'AU', 'AUS');
INSERT INTO land VALUES (15, 'Azerbaijan', 'AZ', 'AZE');
INSERT INTO land VALUES (16, 'Bahamas', 'BS', 'BHS');
INSERT INTO land VALUES (17, 'Bahrain', 'BH', 'BHR');
INSERT INTO land VALUES (18, 'Bangladesh', 'BD', 'BGD');
INSERT INTO land VALUES (19, 'Barbados', 'BB', 'BRB');
INSERT INTO land VALUES (20, 'Belarus', 'BY', 'BLR');
INSERT INTO land VALUES (21, 'Belgium', 'BE', 'BEL');
INSERT INTO land VALUES (22, 'Belize', 'BZ', 'BLZ');
INSERT INTO land VALUES (23, 'Benin', 'BJ', 'BEN');
INSERT INTO land VALUES (24, 'Bermuda', 'BM', 'BMU');
INSERT INTO land VALUES (25, 'Bhutan', 'BT', 'BTN');
INSERT INTO land VALUES (26, 'Bolivia', 'BO', 'BOL');
INSERT INTO land VALUES (27, 'Bosnia and Herzegowina', 'BA', 'BIH');
INSERT INTO land VALUES (28, 'Botswana', 'BW', 'BWA');
INSERT INTO land VALUES (29, 'Bouvet Island', 'BV', 'BVT');
INSERT INTO land VALUES (30, 'Brazil', 'BR', 'BRA');
INSERT INTO land VALUES (31, 'British Indian Ocean Territory', 'IO', 'IOT');
INSERT INTO land VALUES (32, 'Brunei Darussalam', 'BN', 'BRN');
INSERT INTO land VALUES (33, 'Bulgaria', 'BG', 'BGR');
INSERT INTO land VALUES (34, 'Burkina Faso', 'BF', 'BFA');
INSERT INTO land VALUES (35, 'Burundi', 'BI', 'BDI');
INSERT INTO land VALUES (36, 'Cambodia', 'KH', 'KHM');
INSERT INTO land VALUES (37, 'Cameroon', 'CM', 'CMR');
INSERT INTO land VALUES (38, 'Canada', 'CA', 'CAN');
INSERT INTO land VALUES (39, 'Cape Verde', 'CV', 'CPV');
INSERT INTO land VALUES (40, 'Cayman Islands', 'KY', 'CYM');
INSERT INTO land VALUES (41, 'Central African Republic', 'CF', 'CAF');
INSERT INTO land VALUES (42, 'Chad', 'TD', 'TCD');
INSERT INTO land VALUES (43, 'Chile', 'CL', 'CHL');
INSERT INTO land VALUES (44, 'China', 'CN', 'CHN');
INSERT INTO land VALUES (45, 'Christmas Island', 'CX', 'CXR');
INSERT INTO land VALUES (46, 'Cocos (Keeling) Islands', 'CC', 'CCK');
INSERT INTO land VALUES (47, 'Colombia', 'CO', 'COL');
INSERT INTO land VALUES (48, 'Comoros', 'KM', 'COM');
INSERT INTO land VALUES (49, 'Congo', 'CG', 'COG');
INSERT INTO land VALUES (50, 'Cook Islands', 'CK', 'COK');
INSERT INTO land VALUES (51, 'Costa Rica', 'CR', 'CRI');
INSERT INTO land VALUES (52, 'Cote D''Ivoire', 'CI', 'CIV');
INSERT INTO land VALUES (53, 'Croatia', 'HR', 'HRV');
INSERT INTO land VALUES (54, 'Cuba', 'CU', 'CUB');
INSERT INTO land VALUES (55, 'Cyprus', 'CY', 'CYP');
INSERT INTO land VALUES (56, 'Czech Republic', 'CZ', 'CZE');
INSERT INTO land VALUES (57, 'Denmark', 'DK', 'DNK');
INSERT INTO land VALUES (58, 'Djibouti', 'DJ', 'DJI');
INSERT INTO land VALUES (59, 'Dominica', 'DM', 'DMA');
INSERT INTO land VALUES (60, 'Dominican Republic', 'DO', 'DOM');
INSERT INTO land VALUES (61, 'East Timor', 'TP', 'TMP');
INSERT INTO land VALUES (62, 'Ecuador', 'EC', 'ECU');
INSERT INTO land VALUES (63, 'Egypt', 'EG', 'EGY');
INSERT INTO land VALUES (64, 'El Salvador', 'SV', 'SLV');
INSERT INTO land VALUES (65, 'Equatorial Guinea', 'GQ', 'GNQ');
INSERT INTO land VALUES (66, 'Eritrea', 'ER', 'ERI');
INSERT INTO land VALUES (67, 'Estonia', 'EE', 'EST');
INSERT INTO land VALUES (68, 'Ethiopia', 'ET', 'ETH');
INSERT INTO land VALUES (69, 'Falkland Islands (Malvinas)', 'FK', 'FLK');
INSERT INTO land VALUES (70, 'Faroe Islands', 'FO', 'FRO');
INSERT INTO land VALUES (71, 'Fiji', 'FJ', 'FJI');
INSERT INTO land VALUES (72, 'Finland', 'FI', 'FIN');
INSERT INTO land VALUES (73, 'France', 'FR', 'FRA');
INSERT INTO land VALUES (74, 'France, Metropolitan', 'FX', 'FXX');
INSERT INTO land VALUES (75, 'French Guiana', 'GF', 'GUF');
INSERT INTO land VALUES (76, 'French Polynesia', 'PF', 'PYF');
INSERT INTO land VALUES (77, 'French Southern Territories', 'TF', 'ATF');
INSERT INTO land VALUES (78, 'Gabon', 'GA', 'GAB');
INSERT INTO land VALUES (79, 'Gambia', 'GM', 'GMB');
INSERT INTO land VALUES (80, 'Georgia', 'GE', 'GEO');
INSERT INTO land VALUES (82, 'Ghana', 'GH', 'GHA');
INSERT INTO land VALUES (83, 'Gibraltar', 'GI', 'GIB');
INSERT INTO land VALUES (84, 'Greece', 'GR', 'GRC');
INSERT INTO land VALUES (85, 'Greenland', 'GL', 'GRL');
INSERT INTO land VALUES (86, 'Grenada', 'GD', 'GRD');
INSERT INTO land VALUES (87, 'Guadeloupe', 'GP', 'GLP');
INSERT INTO land VALUES (88, 'Guam', 'GU', 'GUM');
INSERT INTO land VALUES (89, 'Guatemala', 'GT', 'GTM');
INSERT INTO land VALUES (90, 'Guinea', 'GN', 'GIN');
INSERT INTO land VALUES (91, 'Guinea-bissau', 'GW', 'GNB');
INSERT INTO land VALUES (92, 'Guyana', 'GY', 'GUY');
INSERT INTO land VALUES (93, 'Haiti', 'HT', 'HTI');
INSERT INTO land VALUES (94, 'Heard and Mc Donald Islands', 'HM', 'HMD');
INSERT INTO land VALUES (95, 'Honduras', 'HN', 'HND');
INSERT INTO land VALUES (96, 'Hong Kong', 'HK', 'HKG');
INSERT INTO land VALUES (97, 'Hungary', 'HU', 'HUN');
INSERT INTO land VALUES (98, 'Iceland', 'IS', 'ISL');
INSERT INTO land VALUES (99, 'India', 'IN', 'IND');
INSERT INTO land VALUES (100, 'Indonesia', 'ID', 'IDN');
INSERT INTO land VALUES (101, 'Iran (Islamic Republic of)', 'IR', 'IRN');
INSERT INTO land VALUES (102, 'Iraq', 'IQ', 'IRQ');
INSERT INTO land VALUES (103, 'Ireland', 'IE', 'IRL');
INSERT INTO land VALUES (104, 'Israel', 'IL', 'ISR');
INSERT INTO land VALUES (105, 'Italy', 'IT', 'ITA');
INSERT INTO land VALUES (106, 'Jamaica', 'JM', 'JAM');
INSERT INTO land VALUES (107, 'Japan', 'JP', 'JPN');
INSERT INTO land VALUES (108, 'Jordan', 'JO', 'JOR');
INSERT INTO land VALUES (109, 'Kazakhstan', 'KZ', 'KAZ');
INSERT INTO land VALUES (110, 'Kenya', 'KE', 'KEN');
INSERT INTO land VALUES (111, 'Kiribati', 'KI', 'KIR');
INSERT INTO land VALUES (112, 'Korea, Democratic People''s Republic of', 'KP', 'PRK');
INSERT INTO land VALUES (113, 'Korea, Republic of', 'KR', 'KOR');
INSERT INTO land VALUES (114, 'Kuwait', 'KW', 'KWT');
INSERT INTO land VALUES (115, 'Kyrgyzstan', 'KG', 'KGZ');
INSERT INTO land VALUES (116, 'Lao People''s Democratic Republic', 'LA', 'LAO');
INSERT INTO land VALUES (117, 'Latvia', 'LV', 'LVA');
INSERT INTO land VALUES (118, 'Lebanon', 'LB', 'LBN');
INSERT INTO land VALUES (119, 'Lesotho', 'LS', 'LSO');
INSERT INTO land VALUES (120, 'Liberia', 'LR', 'LBR');
INSERT INTO land VALUES (121, 'Libyan Arab Jamahiriya', 'LY', 'LBY');
INSERT INTO land VALUES (122, 'Liechtenstein', 'LI', 'LIE');
INSERT INTO land VALUES (123, 'Lithuania', 'LT', 'LTU');
INSERT INTO land VALUES (124, 'Luxembourg', 'LU', 'LUX');
INSERT INTO land VALUES (125, 'Macau', 'MO', 'MAC');
INSERT INTO land VALUES (126, 'Macedonia, The Former Yugoslav Republic of', 'MK', 'MKD');
INSERT INTO land VALUES (127, 'Madagascar', 'MG', 'MDG');
INSERT INTO land VALUES (128, 'Malawi', 'MW', 'MWI');
INSERT INTO land VALUES (129, 'Malaysia', 'MY', 'MYS');
INSERT INTO land VALUES (130, 'Maldives', 'MV', 'MDV');
INSERT INTO land VALUES (131, 'Mali', 'ML', 'MLI');
INSERT INTO land VALUES (132, 'Malta', 'MT', 'MLT');
INSERT INTO land VALUES (133, 'Marshall Islands', 'MH', 'MHL');
INSERT INTO land VALUES (134, 'Martinique', 'MQ', 'MTQ');
INSERT INTO land VALUES (135, 'Mauritania', 'MR', 'MRT');
INSERT INTO land VALUES (136, 'Mauritius', 'MU', 'MUS');
INSERT INTO land VALUES (137, 'Mayotte', 'YT', 'MYT');
INSERT INTO land VALUES (138, 'Mexico', 'MX', 'MEX');
INSERT INTO land VALUES (139, 'Micronesia, Federated States of', 'FM', 'FSM');
INSERT INTO land VALUES (140, 'Moldova, Republic of', 'MD', 'MDA');
INSERT INTO land VALUES (141, 'Monaco', 'MC', 'MCO');
INSERT INTO land VALUES (142, 'Mongolia', 'MN', 'MNG');
INSERT INTO land VALUES (143, 'Montserrat', 'MS', 'MSR');
INSERT INTO land VALUES (144, 'Morocco', 'MA', 'MAR');
INSERT INTO land VALUES (145, 'Mozambique', 'MZ', 'MOZ');
INSERT INTO land VALUES (146, 'Myanmar', 'MM', 'MMR');
INSERT INTO land VALUES (147, 'Namibia', 'NA', 'NAM');
INSERT INTO land VALUES (148, 'Nauru', 'NR', 'NRU');
INSERT INTO land VALUES (149, 'Nepal', 'NP', 'NPL');
INSERT INTO land VALUES (150, 'Netherlands', 'NL', 'NLD');
INSERT INTO land VALUES (151, 'Netherlands Antilles', 'AN', 'ANT');
INSERT INTO land VALUES (152, 'New Caledonia', 'NC', 'NCL');
INSERT INTO land VALUES (153, 'New Zealand', 'NZ', 'NZL');
INSERT INTO land VALUES (154, 'Nicaragua', 'NI', 'NIC');
INSERT INTO land VALUES (155, 'Niger', 'NE', 'NER');
INSERT INTO land VALUES (156, 'Nigeria', 'NG', 'NGA');
INSERT INTO land VALUES (157, 'Niue', 'NU', 'NIU');
INSERT INTO land VALUES (158, 'Norfolk Island', 'NF', 'NFK');
INSERT INTO land VALUES (159, 'Northern Mariana Islands', 'MP', 'MNP');
INSERT INTO land VALUES (160, 'Norway', 'NO', 'NOR');
INSERT INTO land VALUES (161, 'Oman', 'OM', 'OMN');
INSERT INTO land VALUES (162, 'Pakistan', 'PK', 'PAK');
INSERT INTO land VALUES (163, 'Palau', 'PW', 'PLW');
INSERT INTO land VALUES (164, 'Panama', 'PA', 'PAN');
INSERT INTO land VALUES (165, 'Papua New Guinea', 'PG', 'PNG');
INSERT INTO land VALUES (166, 'Paraguay', 'PY', 'PRY');
INSERT INTO land VALUES (167, 'Peru', 'PE', 'PER');
INSERT INTO land VALUES (168, 'Philippines', 'PH', 'PHL');
INSERT INTO land VALUES (169, 'Pitcairn', 'PN', 'PCN');
INSERT INTO land VALUES (170, 'Poland', 'PL', 'POL');
INSERT INTO land VALUES (171, 'Portugal', 'PT', 'PRT');
INSERT INTO land VALUES (172, 'Puerto Rico', 'PR', 'PRI');
INSERT INTO land VALUES (173, 'Qatar', 'QA', 'QAT');
INSERT INTO land VALUES (174, 'Reunion', 'RE', 'REU');
INSERT INTO land VALUES (175, 'Romania', 'RO', 'ROM');
INSERT INTO land VALUES (176, 'Russian Federation', 'RU', 'RUS');
INSERT INTO land VALUES (177, 'Rwanda', 'RW', 'RWA');
INSERT INTO land VALUES (178, 'Saint Kitts and Nevis', 'KN', 'KNA');
INSERT INTO land VALUES (179, 'Saint Lucia', 'LC', 'LCA');
INSERT INTO land VALUES (180, 'Saint Vincent and the Grenadines', 'VC', 'VCT');
INSERT INTO land VALUES (181, 'Samoa', 'WS', 'WSM');
INSERT INTO land VALUES (182, 'San Marino', 'SM', 'SMR');
INSERT INTO land VALUES (183, 'Sao Tome and Principe', 'ST', 'STP');
INSERT INTO land VALUES (184, 'Saudi Arabia', 'SA', 'SAU');
INSERT INTO land VALUES (185, 'Senegal', 'SN', 'SEN');
INSERT INTO land VALUES (186, 'Seychelles', 'SC', 'SYC');
INSERT INTO land VALUES (187, 'Sierra Leone', 'SL', 'SLE');
INSERT INTO land VALUES (188, 'Singapore', 'SG', 'SGP');
INSERT INTO land VALUES (189, 'Slovakia (Slovak Republic)', 'SK', 'SVK');
INSERT INTO land VALUES (190, 'Slovenia', 'SI', 'SVN');
INSERT INTO land VALUES (191, 'Solomon Islands', 'SB', 'SLB');
INSERT INTO land VALUES (192, 'Somalia', 'SO', 'SOM');
INSERT INTO land VALUES (193, 'South Africa', 'ZA', 'ZAF');
INSERT INTO land VALUES (194, 'South Georgia and the South Sandwich Islands', 'GS', 'SGS');
INSERT INTO land VALUES (195, 'Spain', 'ES', 'ESP');
INSERT INTO land VALUES (196, 'Sri Lanka', 'LK', 'LKA');
INSERT INTO land VALUES (197, 'St. Helena', 'SH', 'SHN');
INSERT INTO land VALUES (198, 'St. Pierre and Miquelon', 'PM', 'SPM');
INSERT INTO land VALUES (199, 'Sudan', 'SD', 'SDN');
INSERT INTO land VALUES (200, 'Suriname', 'SR', 'SUR');
INSERT INTO land VALUES (201, 'Svalbard and Jan Mayen Islands', 'SJ', 'SJM');
INSERT INTO land VALUES (202, 'Swaziland', 'SZ', 'SWZ');
INSERT INTO land VALUES (203, 'Sweden', 'SE', 'SWE');
INSERT INTO land VALUES (205, 'Syrian Arab Republic', 'SY', 'SYR');
INSERT INTO land VALUES (206, 'Taiwan', 'TW', 'TWN');
INSERT INTO land VALUES (207, 'Tajikistan', 'TJ', 'TJK');
INSERT INTO land VALUES (208, 'Tanzania, United Republic of', 'TZ', 'TZA');
INSERT INTO land VALUES (209, 'Thailand', 'TH', 'THA');
INSERT INTO land VALUES (210, 'Togo', 'TG', 'TGO');
INSERT INTO land VALUES (211, 'Tokelau', 'TK', 'TKL');
INSERT INTO land VALUES (212, 'Tonga', 'TO', 'TON');
INSERT INTO land VALUES (213, 'Trinidad and Tobago', 'TT', 'TTO');
INSERT INTO land VALUES (214, 'Tunisia', 'TN', 'TUN');
INSERT INTO land VALUES (215, 'Turkey', 'TR', 'TUR');
INSERT INTO land VALUES (216, 'Turkmenistan', 'TM', 'TKM');
INSERT INTO land VALUES (217, 'Turks and Caicos Islands', 'TC', 'TCA');
INSERT INTO land VALUES (218, 'Tuvalu', 'TV', 'TUV');
INSERT INTO land VALUES (219, 'Uganda', 'UG', 'UGA');
INSERT INTO land VALUES (220, 'Ukraine', 'UA', 'UKR');
INSERT INTO land VALUES (221, 'United Arab Emirates', 'AE', 'ARE');
INSERT INTO land VALUES (222, 'United Kingdom', 'GB', 'GBR');
INSERT INTO land VALUES (223, 'United States', 'US', 'USA');
INSERT INTO land VALUES (224, 'United States Minor Outlying Islands', 'UM', 'UMI');
INSERT INTO land VALUES (225, 'Uruguay', 'UY', 'URY');
INSERT INTO land VALUES (226, 'Uzbekistan', 'UZ', 'UZB');
INSERT INTO land VALUES (227, 'Vanuatu', 'VU', 'VUT');
INSERT INTO land VALUES (228, 'Vatican City State (Holy See)', 'VA', 'VAT');
INSERT INTO land VALUES (229, 'Venezuela', 'VE', 'VEN');
INSERT INTO land VALUES (230, 'Viet Nam', 'VN', 'VNM');
INSERT INTO land VALUES (231, 'Virgin Islands (British)', 'VG', 'VGB');
INSERT INTO land VALUES (232, 'Virgin Islands (U.S.)', 'VI', 'VIR');
INSERT INTO land VALUES (233, 'Wallis and Futuna Islands', 'WF', 'WLF');
INSERT INTO land VALUES (234, 'Western Sahara', 'EH', 'ESH');
INSERT INTO land VALUES (235, 'Yemen', 'YE', 'YEM');
INSERT INTO land VALUES (236, 'Yugoslavia', 'YU', 'YUG');
INSERT INTO land VALUES (237, 'Zaire', 'ZR', 'ZAR');
INSERT INTO land VALUES (238, 'Zambia', 'ZM', 'ZMB');
INSERT INTO land VALUES (239, 'Zimbabwe', 'ZW', 'ZWE');

# Bundesländer Germany
INSERT INTO bundesland VALUES (79,81,'NDS','Niedersachsen');
INSERT INTO bundesland VALUES (80,81,'BAW','Baden-W&uuml;rttemberg');
INSERT INTO bundesland VALUES (81,81,'BAY','Bayern');
INSERT INTO bundesland VALUES (82,81,'BER','Berlin');
INSERT INTO bundesland VALUES (83,81,'BRG','Brandenburg');
INSERT INTO bundesland VALUES (84,81,'BRE','Bremen');
INSERT INTO bundesland VALUES (85,81,'HAM','Hamburg');
INSERT INTO bundesland VALUES (86,81,'HES','Hessen');
INSERT INTO bundesland VALUES (87,81,'MEC','Mecklenburg-Vorpommern');
INSERT INTO bundesland VALUES (88,81,'NRW','Nordrhein-Westfalen');
INSERT INTO bundesland VALUES (89,81,'RHE','Rheinland-Pfalz');
INSERT INTO bundesland VALUES (90,81,'SAR','Saarland');
INSERT INTO bundesland VALUES (91,81,'SAS','Sachsen');
INSERT INTO bundesland VALUES (92,81,'SAC','Sachsen-Anhalt');
INSERT INTO bundesland VALUES (93,81,'SCN','Schleswig-Holstein');
INSERT INTO bundesland VALUES (94,81,'THE','Th&uuml;ringen');

# Bundesländer Österreich
INSERT INTO bundesland VALUES (95,14,'WI','Wien');
INSERT INTO bundesland VALUES (96,14,'NO','Nieder&ouml;sterreich');
INSERT INTO bundesland VALUES (97,14,'OO','Ober&ouml;sterreich');
INSERT INTO bundesland VALUES (98,14,'SB','Salzburg');
INSERT INTO bundesland VALUES (99,14,'KN','K&auml;rnten');
INSERT INTO bundesland VALUES (100,14,'ST','Steiermark');
INSERT INTO bundesland VALUES (101,14,'TI','Tirol');
INSERT INTO bundesland VALUES (102,14,'BL','Burgenland');
INSERT INTO bundesland VALUES (103,14,'VB','Voralberg');

# Bundesländer Schweiz
INSERT INTO bundesland VALUES (104,204,'AG','Aargau');
INSERT INTO bundesland VALUES (105,204,'AI','Appenzell Innerrhoden');
INSERT INTO bundesland VALUES (106,204,'AR','Appenzell Ausserrhoden');
INSERT INTO bundesland VALUES (107,204,'BE','Bern');
INSERT INTO bundesland VALUES (108,204,'BL','Basel-Landschaft');
INSERT INTO bundesland VALUES (109,204,'BS','Basel-Stadt');
INSERT INTO bundesland VALUES (110,204,'FR','Freiburg');
INSERT INTO bundesland VALUES (111,204,'GE','Genf');
INSERT INTO bundesland VALUES (112,204,'GL','Glarus');
INSERT INTO bundesland VALUES (113,204,'JU','GraubÃ¼nden');
INSERT INTO bundesland VALUES (114,204,'JU','Jura');
INSERT INTO bundesland VALUES (115,204,'LU','Luzern');
INSERT INTO bundesland VALUES (116,204,'NE','Neuenburg');
INSERT INTO bundesland VALUES (117,204,'NW','Nidwalden');
INSERT INTO bundesland VALUES (118,204,'OW','Obwalden');
INSERT INTO bundesland VALUES (119,204,'SG','St. Gallen');
INSERT INTO bundesland VALUES (120,204,'SH','Schaffhausen');
INSERT INTO bundesland VALUES (121,204,'SO','Solothurn');
INSERT INTO bundesland VALUES (122,204,'SZ','Schwyz');
INSERT INTO bundesland VALUES (123,204,'TG','Thurgau');
INSERT INTO bundesland VALUES (124,204,'TI','Tessin');
INSERT INTO bundesland VALUES (125,204,'UR','Uri');
INSERT INTO bundesland VALUES (126,204,'VD','Waadt');
INSERT INTO bundesland VALUES (127,204,'VS','Wallis');
INSERT INTO bundesland VALUES (128,204,'ZG','Zug');
INSERT INTO bundesland VALUES (129,204,'ZH','ZÃ¼rich');

# Bundesländer USA
INSERT INTO bundesland VALUES (1,223,'AL','Alabama');
INSERT INTO bundesland VALUES (2,223,'AK','Alaska');
INSERT INTO bundesland VALUES (3,223,'AS','American Samoa');
INSERT INTO bundesland VALUES (4,223,'AZ','Arizona');
INSERT INTO bundesland VALUES (5,223,'AR','Arkansas');
INSERT INTO bundesland VALUES (6,223,'AF','Armed Forces Africa');
INSERT INTO bundesland VALUES (7,223,'AA','Armed Forces Americas');
INSERT INTO bundesland VALUES (8,223,'AC','Armed Forces Canada');
INSERT INTO bundesland VALUES (9,223,'AE','Armed Forces Europe');
INSERT INTO bundesland VALUES (10,223,'AM','Armed Forces Middle East');
INSERT INTO bundesland VALUES (11,223,'AP','Armed Forces Pacific');
INSERT INTO bundesland VALUES (12,223,'CA','California');
INSERT INTO bundesland VALUES (13,223,'CO','Colorado');
INSERT INTO bundesland VALUES (14,223,'CT','Connecticut');
INSERT INTO bundesland VALUES (15,223,'DE','Delaware');
INSERT INTO bundesland VALUES (16,223,'DC','District of Columbia');
INSERT INTO bundesland VALUES (17,223,'FM','Federated States Of Micronesia');
INSERT INTO bundesland VALUES (18,223,'FL','Florida');
INSERT INTO bundesland VALUES (19,223,'GA','Georgia');
INSERT INTO bundesland VALUES (20,223,'GU','Guam');
INSERT INTO bundesland VALUES (21,223,'HI','Hawaii');
INSERT INTO bundesland VALUES (22,223,'ID','Idaho');
INSERT INTO bundesland VALUES (23,223,'IL','Illinois');
INSERT INTO bundesland VALUES (24,223,'IN','Indiana');
INSERT INTO bundesland VALUES (25,223,'IA','Iowa');
INSERT INTO bundesland VALUES (26,223,'KS','Kansas');
INSERT INTO bundesland VALUES (27,223,'KY','Kentucky');
INSERT INTO bundesland VALUES (28,223,'LA','Louisiana');
INSERT INTO bundesland VALUES (29,223,'ME','Maine');
INSERT INTO bundesland VALUES (30,223,'MH','Marshall Islands');
INSERT INTO bundesland VALUES (31,223,'MD','Maryland');
INSERT INTO bundesland VALUES (32,223,'MA','Massachusetts');
INSERT INTO bundesland VALUES (33,223,'MI','Michigan');
INSERT INTO bundesland VALUES (34,223,'MN','Minnesota');
INSERT INTO bundesland VALUES (35,223,'MS','Mississippi');
INSERT INTO bundesland VALUES (36,223,'MO','Missouri');
INSERT INTO bundesland VALUES (37,223,'MT','Montana');
INSERT INTO bundesland VALUES (38,223,'NE','Nebraska');
INSERT INTO bundesland VALUES (39,223,'NV','Nevada');
INSERT INTO bundesland VALUES (40,223,'NH','New Hampshire');
INSERT INTO bundesland VALUES (41,223,'NJ','New Jersey');
INSERT INTO bundesland VALUES (42,223,'NM','New Mexico');
INSERT INTO bundesland VALUES (43,223,'NY','New York');
INSERT INTO bundesland VALUES (44,223,'NC','North Carolina');
INSERT INTO bundesland VALUES (45,223,'ND','North Dakota');
INSERT INTO bundesland VALUES (46,223,'MP','Northern Mariana Islands');
INSERT INTO bundesland VALUES (47,223,'OH','Ohio');
INSERT INTO bundesland VALUES (48,223,'OK','Oklahoma');
INSERT INTO bundesland VALUES (49,223,'OR','Oregon');
INSERT INTO bundesland VALUES (50,223,'PW','Palau');
INSERT INTO bundesland VALUES (51,223,'PA','Pennsylvania');
INSERT INTO bundesland VALUES (52,223,'PR','Puerto Rico');
INSERT INTO bundesland VALUES (53,223,'RI','Rhode Island');
INSERT INTO bundesland VALUES (54,223,'SC','South Carolina');
INSERT INTO bundesland VALUES (55,223,'SD','South Dakota');
INSERT INTO bundesland VALUES (56,223,'TN','Tennessee');
INSERT INTO bundesland VALUES (57,223,'TX','Texas');
INSERT INTO bundesland VALUES (58,223,'UT','Utah');
INSERT INTO bundesland VALUES (59,223,'VT','Vermont');
INSERT INTO bundesland VALUES (60,223,'VI','Virgin Islands');
INSERT INTO bundesland VALUES (61,223,'VA','Virginia');
INSERT INTO bundesland VALUES (62,223,'WA','Washington');
INSERT INTO bundesland VALUES (63,223,'WV','West Virginia');
INSERT INTO bundesland VALUES (64,223,'WI','Wisconsin');
INSERT INTO bundesland VALUES (65,223,'WY','Wyoming');
