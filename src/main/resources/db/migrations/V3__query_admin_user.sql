INSERT INTO USERS (
    ID_USER, 
    CLIENT_NAME, 
    EMAIL, 
    PASSWORD, 
    USER_PIX_KEY, 
    USERNAME, 
    BET_MAX_VALUE, 
    REGISTER_DATE
) VALUES (
    100, 
    'Administrador', 
    'admin@dominio.com', 
    '$2a$10$PEHLAhyE/9sqNxkB6cup/.iOiVfdPPD8vwVmLGRKQj4AzWt1O9PsK',  
    'admin-pix-chave', 
    'admin', 
    10000.00, 
    CURRENT_TIMESTAMP
);
