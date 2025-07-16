insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk tuk comida', 15, 2);

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
