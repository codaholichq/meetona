CREATE TABLE departments (
  id UUID NOT NULL,
   created_by VARCHAR(50),
   created_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_by VARCHAR(50),
   updated_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   version INTEGER,
   name VARCHAR(50) NOT NULL,
   lead VARCHAR(50) NOT NULL,
   CONSTRAINT pk_departments PRIMARY KEY (id)
);


CREATE TABLE units (
  id UUID NOT NULL,
   created_by VARCHAR(50),
   created_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_by VARCHAR(50),
   updated_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   version INTEGER,
   name VARCHAR(255) NOT NULL,
   address VARCHAR(255) NOT NULL,
   CONSTRAINT pk_units PRIMARY KEY (id)
);


CREATE TABLE meetings (
  id UUID NOT NULL,
   created_by VARCHAR(50),
   created_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_by VARCHAR(50),
   updated_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   version INTEGER,
   member_count INTEGER NOT NULL,
   prayer_point VARCHAR(255) NOT NULL,
   unit_id UUID,
   CONSTRAINT pk_meetings PRIMARY KEY (id)
);
ALTER TABLE meetings ADD CONSTRAINT FK_MEETINGS_ON_UNIT FOREIGN KEY (unit_id) REFERENCES units (id);


CREATE TABLE members (
  id UUID NOT NULL,
   created_by VARCHAR(50),
   created_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_by VARCHAR(50),
   updated_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   version INTEGER,
   first_name VARCHAR(50) NOT NULL,
   middle_name VARCHAR(50),
   last_name VARCHAR(50) NOT NULL,
   gender VARCHAR(5) NOT NULL,
   email VARCHAR(255) NOT NULL,
   phone_number VARCHAR(11) NOT NULL,
   birth_date date NOT NULL,
   marriage_date date NOT NULL,
   marital_status VARCHAR(7) NOT NULL,
   unit_id UUID,
   department_id UUID,
   CONSTRAINT pk_members PRIMARY KEY (id)
);
ALTER TABLE members ADD CONSTRAINT uc_members_email UNIQUE (email);
ALTER TABLE members ADD CONSTRAINT uc_members_phonenumber UNIQUE (phone_number);
ALTER TABLE members ADD CONSTRAINT FK_MEMBERS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id);
ALTER TABLE members ADD CONSTRAINT FK_MEMBERS_ON_UNIT FOREIGN KEY (unit_id) REFERENCES units (id);


CREATE TABLE users (
  id UUID NOT NULL,
   created_by VARCHAR(50),
   created_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_by VARCHAR(50),
   updated_dt TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   version INTEGER,
   username VARCHAR(50) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   roles VARCHAR(255) NOT NULL,
   member_id UUID,
   is_email_verified BOOLEAN,
   CONSTRAINT pk_users PRIMARY KEY (id)
);
ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_MEMBER FOREIGN KEY (member_id) REFERENCES members (id);