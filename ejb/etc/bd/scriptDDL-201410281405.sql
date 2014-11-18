CREATE DATABASE `box` /*!40100 DEFAULT CHARACTER SET latin1 */;

create table tb_pessoa(
	id bigint auto_increment primary key,
    nome varchar(200),
    cpf varchar(200),
    data_nasc date,
    email varchar(200)
);



create table tb_grupo(
	id bigint auto_increment primary key,
    nome varchar(200),
    descricao varchar(1000)
);



create table tb_usuario(
	id bigint auto_increment primary key,
    login varchar(200),
    senha varchar(200),
    habilitado boolean,
    pessoa bigint
);
alter table tb_usuario add foreign key fk_pessoa(pessoa) 
	references tb_pessoa(id) on delete no action on update no action;

	
	
create table tb_usuario_grupo(
	usuario bigint not null,
    grupo bigint not null,
    primary key(usuario, grupo)
);
alter table tb_usuario_grupo add foreign key fk_usuario(usuario) 
	references tb_usuario(id) on delete no action on update no action;
alter table tb_usuario_grupo add foreign key fk_grupo(grupo) 
	references tb_grupo(id) on delete no action on update no action;

	
	
create table tb_tipo (
	id bigint primary key auto_increment,
    nome varchar(200),
    cor varchar(50)
);



create table tb_recurso (
	id bigint primary key auto_increment,
    descricao varchar(200),
    num_patrimonio varchar(50),
    tipo bigint
);
alter table tb_recurso add foreign key fk_tipo(tipo) 
	references tb_tipo(id) on delete no action on update no action;
ALTER TABLE `box`.`tb_recurso` 
	ADD COLUMN `ativo` TINYINT(1) NULL DEFAULT NULL AFTER `tipo`;
	
	
create table tb_agenda(
	id bigint primary key auto_increment,
    titulo_evento varchar(200),
    desc_evento varchar(1000),
    data_inicio datetime,
    data_fim datetime,
    data_notificacao datetime,
    recurso bigint,
    usuario bigint,
    pessoa bigint
);
alter table tb_agenda add foreign key fk_usuario_agenda(usuario) 
	references tb_usuario(id) on delete no action;    
alter table tb_agenda add foreign key fk_pessoa_agenda(pessoa) 
	references tb_pessoa(id) on delete no action;
alter table tb_agenda add foreign key fk_recurso_agenda(recurso) 
	references tb_recurso(id) on delete no action on update no action;  
	
create table tb_notificacao(
	id bigint primary key auto_increment,
    data_notificar date,
    data_envio date,
    remetente varchar(400),
    destinatario varchar(400),
    corpo varchar(3000),
    tipo varchar(200),
    titulo varchar(200)
);

ALTER TABLE `box`.`tb_notificacao` 
CHANGE COLUMN `data_notificar` `data_notificar` TIMESTAMP NULL DEFAULT NULL ;
