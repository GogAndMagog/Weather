CREATE TABLE "Main"."Sessions"
(
    "ID"        uuid    NOT NULL UNIQUE,
    "UserId"    integer NOT NULL REFERENCES "Main"."Users",
    "ExpiresAt" timestamp
);