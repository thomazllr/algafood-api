create table pedido (
	id bigint not null auto_increment,
	taxa_frete decimal(10,2) not null,
	valor_total decimal(10,2) not null,

	data_criacao datetime not null,
	data_confirmacao datetime null,
	data_cancelamento datetime null,
	data_entrega datetime null,
    status varchar(10) not null,

    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    forma_pagamento_id bigint not null,

	endereco_cidade_id bigint not null,
	endereco_cep varchar(9) not null,
	endereco_logradouro varchar(100) not null,
	endereco_numero varchar(20) not null,
	endereco_complemento varchar(60) null,
	endereco_bairro varchar(60) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table item_pedido (
	id bigint not null auto_increment,
    produto_id bigint not null,
    pedido_id bigint not null,

    quantidade int not null,
    preco_unitario decimal(10,2) not null,
    preco_Total decimal(10,2) not null,
    observacao varchar(20) null,

    primary key (id)
) engine=InnoDB default charset=utf8;

alter table pedido add constraint fk_pedido_usuario_cliente
    foreign key (usuario_cliente_id) references usuario (id);

alter table pedido add constraint fk_pedido_restaurante
    foreign key (restaurante_id) references restaurante (id);

alter table pedido add constraint fk_pedido_forma_pagamento
    foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table item_pedido add constraint fk_item_pedido_pedido
    foreign key (pedido_id) references pedido (id);

alter table item_pedido add constraint fk_item_pedido_produto
    foreign key (produto_id) references produto (id);
