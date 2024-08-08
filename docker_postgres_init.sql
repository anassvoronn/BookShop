-- Table: public.authors

-- DROP TABLE IF EXISTS public.authors;
CREATE SEQUENCE authors_id_seq START 101;
CREATE SEQUENCE books_id_seq START 101;

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

CREATE TABLE IF NOT EXISTS public.books
(
    id bigint NOT NULL DEFAULT nextval('books_id_seq'::regclass),
    title character varying(255) COLLATE pg_catalog."default",
    publishingyear integer,
    genre character varying(50) COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.books
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.author_to_book
(
    authorid integer,
    bookid integer
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.author_to_book
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.book_views
(
    bookid integer,
    viewcount integer
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.book_views
    OWNER to postgres;