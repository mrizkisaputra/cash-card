CREATE TABLE cashcards
(
    id     character varying(255) NOT NULL,
    amount numeric(38, 2),
    owner  character varying(255) NOT NULL
);

ALTER TABLE ONLY cashcards
    ADD CONSTRAINT ukig1uwfp7a8q6qaesclvgg17k2 UNIQUE (owner);

ALTER TABLE ONLY cashcards
    ADD CONSTRAINT cashcards_pkey PRIMARY KEY (id);
