
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Edd','Doble d','Edds@Persona.com','2009-12-12','abf1564b-3758-4170-8602-3303df17b37c_dobled.jfif');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Lucas','Santos','Lucas@Persona.com','2016-08-27','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Luis','Solis','Ls@Persona.com','1993-02-16','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Lucas','Moy','Lucas@Persona.com','2020-03-09','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Sergio','Vergara','Telechia@Persona.com','2010-12-08','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Hashirama','Senju','Mokuton@Konoha.com','1888-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Andres','Sanabria','San@Persona.com','2006-07-11','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Tulio','Valencia','Tv@Persona.com','2008-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Tuli','Valencia','Tv@Persona.com','2015-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Ezequiel','Nuñez','Tv@Persona.com','2009-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Luis','Diaz','Liver@Persona.com','2016-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Laura','Pausini','Pausini@Persona.com','2022-08-27','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Lucia','Bermudez','Lb@Persona.com','2018-05-20','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Kelly','Ramos','Kll@Persona.com','2014-04-19','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Sandra','Rivas','San@Persona.com','2007-06-29','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Sara','Momtalbo','Momtalbo@Persona.com','2003-01-16','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Lucy','Vallecilla','Luv@Persona.com','1988-11-16','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Camilo','Val','Xv@Persona.com','2000-02-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Maria','Candelo','Mc@Persona.com','2018-12-10','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Patricia','Salsedo','Salsa@Persona.com','2011-07-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Estela','Vallesteros','Vallesteros@Persona.com','2009-12-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Soledad','Valencia','Sol@Persona.com','1009-11-12','');
insert into clientes(nombre,apellido,email,fecha_creacion,foto)values('Sara','Momtalbo','Momtalbo@Persona.com','2011-01-10','');

/*DATOS DE LA TABLA PRODUCTOS*/
INSERT INTO productos (nombre,precio,create_at)values('Panasonic Pantalla LCD',259990,NOW());

INSERT INTO productos (nombre,precio,create_at)values('Sony Camara digital DSC-W20B',123490,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Applw ipod shuffle',1499990,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Sony Notebook Z110',37990,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Hewlett Packar Multifuncional F2280',6990,NOW());

INSERT INTO productos (nombre,precio,create_at)values('Bianchi Bicleta Aro 26',69990,NOW());
INSERT INTO productos (nombre,precio,create_at)values('TAMAGOTCHI',1300,NOW());
INSERT INTO productos (nombre,precio,create_at)values('GAME BOY',2182,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Maquina de escribir',5068,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Abacco',410,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Celular Motorola',180,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Moto 5X 700',180,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Sanduchera Digital',180,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Secador de pelo LG',180,NOW());
INSERT INTO productos (nombre,precio,create_at)values('Tostadora SANSUNG',180,NOW());




/*DATOS TABLA FACTURAS*/
INSERT INTO facturas(descripcion,observaciones,cliente_id,fecha_creacion)values('Factura equipos de oficina','Para reemplazar aquellos elementos que se encuentran en mal estado',1,NOW());

/*DATOS TABLA FACTURA ITEM*/
INSERT INTO factura_item(cantidad,factura_id,producto_id)values(1,1,1);
INSERT INTO factura_item(cantidad,factura_id,producto_id)values(2,1,4);
INSERT INTO factura_item(cantidad,factura_id,producto_id)values(1,1,5);
INSERT INTO factura_item(cantidad,factura_id,producto_id)values(1,1,2);

INSERT INTO facturas(descripcion,observaciones,cliente_id,fecha_creacion)values('Factura Bicicleta','Es necesario que los empleados hagan deporte',1,NOW());
INSERT INTO factura_item(cantidad,factura_id,producto_id)values(3,2,6);

/*ROLES Y ACCESOS*/

insert into users(username,password,enabled) values('homero','$2a$10$RFlC/TKq3EUtdvo84P9.UOhDmiTGFl/Ay8s2TqtikVzg9iAoTj8Xa',1);
insert into users(username,password,enabled) values('roma','$2a$10$kvrb0b5RHMD3Az0Ex0bytOysZTdoaxFWHaIHLc6Z00pyFeLT40hp6',1);

insert into authorities(user_id,authority) values(1,'ROLE_USER');
insert into authorities(user_id,authority) values(2,'ROLE_ADMIN');
insert into authorities(user_id,authority) values(2,'ROLE_USER');