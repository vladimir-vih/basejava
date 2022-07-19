-- Database: resumes

-- DROP DATABASE IF EXISTS resumes;

CREATE DATABASE resumes
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.resume

-- DROP TABLE IF EXISTS public.resume;

CREATE TABLE IF NOT EXISTS public.resume
(
    uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    full_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT resume_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.resume
    OWNER to postgres;


-- SEQUENCE: public.contact_id_seq

-- DROP SEQUENCE IF EXISTS public.contact_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.contact_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY contact.id;

ALTER SEQUENCE public.contact_id_seq
    OWNER TO postgres;


-- SEQUENCE: public.section_id_seq

-- DROP SEQUENCE IF EXISTS public.section_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.section_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY section.id;

ALTER SEQUENCE public.section_id_seq
    OWNER TO postgres;

-- Table: public.contact

-- DROP TABLE IF EXISTS public.contact;

CREATE TABLE IF NOT EXISTS public.contact
(
    id integer NOT NULL DEFAULT nextval('contact_id_seq'::regclass),
    resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    value text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT contact_uuid_type_index FOREIGN KEY (resume_uuid)
        REFERENCES public.resume (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contact
    OWNER to postgres;
-- Index: fki_contact_uuid_type_index

-- DROP INDEX IF EXISTS public.fki_contact_uuid_type_index;

CREATE INDEX IF NOT EXISTS fki_contact_uuid_type_index
    ON public.contact USING btree
    (resume_uuid COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

-- Table: public.section

-- DROP TABLE IF EXISTS public.section;

CREATE TABLE IF NOT EXISTS public.section
(
    id integer NOT NULL DEFAULT nextval('section_id_seq'::regclass),
    resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    value text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT section_resume_uuid_fk FOREIGN KEY (resume_uuid)
        REFERENCES public.resume (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.section
    OWNER to postgres;
-- Index: section_id_resume_uuid_index

-- DROP INDEX IF EXISTS public.section_id_resume_uuid_index;

CREATE UNIQUE INDEX IF NOT EXISTS section_id_resume_uuid_index
    ON public.section USING btree
    (id ASC NULLS LAST, resume_uuid COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

