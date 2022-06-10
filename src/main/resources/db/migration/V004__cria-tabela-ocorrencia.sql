create table ocorrencia(
	id bigint NOT NULL AUTO_INCREMENT,
    entrega_id bigint NOT NULL,
    descricao text NOT NULL, 
    data_registro datetime NOT NULL,
    
    primary key(id)
);

alter table ocorrencia add constraint fk_ocorrencia_id
foreign key (entrega_id) references entrega(id);