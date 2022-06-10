create table entrega (
	id bigint NOT NULL AUTO_INCREMENT,
    cliente_id bigint NOT NULL,
    taxa decimal(10, 2) NOT NULL,
    status varchar(50) NOT NULL,
    data_pedido datetime NOT NULL,
    data_finalizacao datetime,
    
    destinatario_nome varchar(250) NOT NULL,
    destinatario_logradouro varchar(250) NOT NULL,
    destinatario_numero varchar(250) NOT NULL,
    destinatario_complemento varchar(250) NOT NULL,
    destinatario_bairro varchar(250) NOT NULL,
    
    primary key(id)
);

alter table entrega add constraint fk_entrega_cliente
foreign key (cliente_id) references Cliente(id);