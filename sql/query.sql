-- get all data (with and without SRID) in germany
SELECT ma.id, ma.description, ST_AsText(ma.location), ST_AsEWKT(ma.location)
FROM myakws AS ma
WHERE ST_Within(ma.location, GeometryFromText('POLYGON((5.5 47, 15.5 47, 15.5 55, 5.5 55, 5.5 47))', 4326))
order by ma.description