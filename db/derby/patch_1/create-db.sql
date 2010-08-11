CREATE TABLE tss_data_source (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_data_source_name on tss_data_source(name);

CREATE TABLE tss_data_provider (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_data_provider_name on tss_data_provider(name);

CREATE TABLE tss_data_field (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_data_field_name on tss_data_field(name);

CREATE TABLE tss_observation_time (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_observation_time_name on tss_observation_time(name);

CREATE TABLE tss_identification_scheme (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_identification_scheme_name on tss_identification_scheme(name);

CREATE TABLE tss_identifier_bundle (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255)
);
CREATE UNIQUE INDEX idx_identifier_bundle_name on tss_identifier_bundle(name);

CREATE TABLE tss_meta_data (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	active INTEGER NOT NULL
	  CONSTRAINT active_constraint CHECK ( active IN (0, 1)),
	bundle_id BIGINT NOT NULL
	  constraint fk_tsk_bundle  REFERENCES tss_identifier_bundle(id),
	data_source_id BIGINT NOT NULL
	  constraint fk_tsk_data_source  REFERENCES tss_data_source(id),
	data_provider_id BIGINT NOT NULL
	  constraint fk_tsk_data_provider  REFERENCES tss_data_provider(id),
	data_field_id BIGINT NOT NULL
	  constraint fk_tsk_data_field  REFERENCES tss_data_field(id),
	observation_time_id BIGINT NOT NULL
	  constraint fk_tsk_observation_time  REFERENCES tss_observation_time(id)
);
CREATE INDEX idx_meta_data ON tss_meta_data (active, data_source_id, data_provider_id, data_field_id, observation_time_id);

CREATE TABLE tss_data_point (
	meta_data_id BIGINT NOT NULL
	  constraint fk_dp_meta_data  REFERENCES tss_meta_data (id),
	ts_date date NOT NULL,
	value DOUBLE NOT NULL,
	PRIMARY KEY (meta_data_id, ts_date)
);

CREATE TABLE tss_data_point_delta (
	meta_data_id BIGINT NOT NULL
	  constraint fk_dp_delta_meta_data  REFERENCES tss_meta_data (id),
	time_stamp TIMESTAMP NOT NULL,
	ts_date date NOT NULL,
	old_value DOUBLE NOT NULL,
	operation char(1) NOT NULL
	 CONSTRAINT operation_constraint CHECK ( operation IN ('I', 'U', 'D', 'Q')),
	PRIMARY KEY (meta_data_id, time_stamp, ts_date)
);

CREATE TABLE tss_identifier (
	id BIGINT NOT NULL
	  PRIMARY KEY
	  GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	bundle_id BIGINT NOT NULL
	  constraint fk_identifier_bundle  REFERENCES tss_identifier_bundle(id),
	identification_scheme_id BIGINT NOT NULL
	  constraint fk_identifier_identification_scheme  REFERENCES tss_identification_scheme(id),
	identifier_value VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX idx_identifier on tss_identifier (identification_scheme_id, identifier_value);
CREATE INDEX idx_dsi_identifier ON tss_identifier(identifier_value);
