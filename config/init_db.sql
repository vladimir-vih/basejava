CREATE TABLE resume
(
    uuid character(36) NOT NULL
        CONSTRAINT resume_pkey
            PRIMARY KEY,
    full_name text NOT NULL

);

CREATE TABLE contact
(
    id SERIAL,
    resume_uuid character (36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type text NOT NULL,
    value text NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);

CREATE TABLE section
(
    id SERIAL,
    resume_uuid character(36) REFERENCES resume (uuid) ON DELETE CASCADE,
    type text NOT NULL,
    value text NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index ON section (resume_uuid, type);
