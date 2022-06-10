create table cliente(
	id bigint NOT NULL AUTO_INCREMENT,
    nome varchar(100) NOT NULL, 
    email varchar(255) NOT NULL,
    telefone varchar(25) NOT NULL,
    
    primary key(id)
);