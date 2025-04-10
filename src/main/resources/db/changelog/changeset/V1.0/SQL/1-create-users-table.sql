CREATE TABLE "Main"."Users"
(
    "ID"       serial      NOT NULL UNIQUE PRIMARY KEY ,
    "Login"    varchar(15) NOT NULL UNIQUE,
    "Password" text        NOT NULL,
    PRIMARY KEY ("ID")
);
