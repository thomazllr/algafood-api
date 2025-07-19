insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');
insert into cozinha (nome) values ('Brasileira');
insert into cozinha (nome) values ('Japonesa');
insert into cozinha (nome) values ('Italiana');
insert into cozinha (nome) values ('Americana');

INSERT INTO estado (nome) VALUES ('São Paulo');
INSERT INTO estado (nome) VALUES ('Rio de Janeiro');
INSERT INTO estado (nome) VALUES ('Minas Gerais');
INSERT INTO estado (nome) VALUES ('Bahia');
INSERT INTO estado (nome) VALUES ('Paraná');
INSERT INTO estado (nome) VALUES ('Pernambuco');
INSERT INTO estado (nome) VALUES ('Ceará');
INSERT INTO estado (nome) VALUES ('Rio Grande do Sul');
INSERT INTO estado (nome) VALUES ('Goiás');
INSERT INTO estado (nome) VALUES ('Amazonas');


INSERT INTO cidade (nome, estado_id) VALUES ('São Paulo', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Campinas', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Rio de Janeiro', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Niterói', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Belo Horizonte', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Salvador', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Curitiba', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Recife', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Fortaleza', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Alegre', 8);

INSERT INTO forma_pagamento (descricao) VALUES ('Cartão de Crédito');
INSERT INTO forma_pagamento (descricao) VALUES ('Cartão de Débito');
INSERT INTO forma_pagamento (descricao) VALUES ('Dinheiro');

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) values ('Thai Gourmet', 10.00, 1, '12345-678', 'Rua das Flores', '100', 'Apto 101', 'Centro', 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Tuk tuk comida', 15.00, 2, utc_timestamp, utc_timestamp);

insert into permissao (nome, descricao) values ( 'CONSULTAR_COZINHAS', 'Permite Consultar Cozinha');
insert into permissao (nome, descricao) values ( 'EDITAR_COZINHAS', 'Permite Consultar Cozinha');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2,3), (3,2), (3,3)

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pad Thai', 'Macarrão de arroz tailandês com camarão', 39.90, true, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Curry Vermelho', 'Frango ao curry vermelho com arroz de jasmim', 42.50, true, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Rolinhos Primavera', 'Rolinhos fritos recheados com legumes', 19.00, true, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Khao Pad', 'Arroz frito tailandês com frango e ovo', 32.00, true, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Massaman Curry', 'Curry suave com carne e batatas', 45.00, true, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Satay de Frango', 'Espetinhos grelhados com molho de amendoim', 28.50, true, 3);
