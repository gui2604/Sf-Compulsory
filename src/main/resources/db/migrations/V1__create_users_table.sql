CREATE TABLE users (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    register_date DATE DEFAULT CURRENT_DATE,
    bet_max_value DOUBLE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_pix_key VARCHAR(255)
);
