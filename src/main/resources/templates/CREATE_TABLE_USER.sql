
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
