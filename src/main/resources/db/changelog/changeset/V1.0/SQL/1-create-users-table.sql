CREATE TABLE "Main"."Users"
(
    "ID"       serial      NOT NULL UNIQUE,
    "Login"    varchar(15) NOT NULL UNIQUE,
    "Password" text        NOT NULL,
    PRIMARY KEY ("ID")
);
