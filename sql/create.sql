-- DROP DATABASE mypois;
CREATE DATABASE mypois WITH TEMPLATE = template_postgis;

SELECT postgis_full_version();

-- DROP TABLE myakws;
CREATE TABLE myakws(id bigint NOT NULL, description character varying(100), location geometry, CONSTRAINT myakws_pkey PRIMARY KEY (id));
