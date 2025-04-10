CREATE TABLE "Main"."Sessions"
(
    "ID"        uuid    NOT NULL UNIQUE PRIMARY KEY ,
    "UserId"    integer NOT NULL REFERENCES "Main"."Users",
    "ExpiresAt" timestamp
);