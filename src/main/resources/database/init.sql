-- Table: public.authors

-- DROP TABLE IF EXISTS public.authors;

CREATE TABLE IF NOT EXISTS public.authors
(
    id integer NOT NULL DEFAULT nextval('authors_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    date_of_birth date,
    gender character varying(10) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    additional_column character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT authors_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.authors
    OWNER to postgres;

ALTER TABLE authors ADD COLUMN death_date DATE;