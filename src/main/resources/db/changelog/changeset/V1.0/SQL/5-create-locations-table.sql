CREATE TABLE "Main"."Locations"
(
    "ID"        serial  NOT NULL UNIQUE PRIMARY KEY,
    "Name"      text    NOT NULL,
    "UserId"    integer NOT NULL REFERENCES "Main"."Users",
    "Latitude"  decimal NOT NULL,
    "Longitude" decimal NOT NULL
);