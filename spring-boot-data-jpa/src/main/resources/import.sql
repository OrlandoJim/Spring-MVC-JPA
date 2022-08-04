INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Orlando', 'Jimenez', 'orlando@mail.com', '1992-08-15','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Blanca Esthela', 'Juarez', 'blancaE@mail.com', '1989-05-11','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Gustavo', 'Jimenez', 'Gustavo@mail.com', '1998-09-01','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Mariel', 'Jimenez', 'mariel@mail.com', '1998-09-01','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Silvia', 'Velazquez', 'silvia@mail.com', '1966-06-20','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Oscar', 'Jimenez', 'oscar@mail.com', '1962-08-17','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Luis Mauricio', 'Lopez', 'luisao@mail.com', '1991-10-15','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Brian Daniel', 'Hawkins', 'brianD@mail.com', '1992-11-05','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Alfredo', 'Campos', 'Alfredoc@mail.com', '1992-05-11','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Martin Alejandro', 'Carreto', 'MartinC@mail.com', '1995-10-10','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Ricardo Alejandro', 'Carreto', 'ricky@mail.com', '2000-11-09','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Edgar', 'Juarez', 'edjugo@mail.com', '1993-10-30','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Victor', 'Ruvalcaba', 'VictorR@mail.com', '1988-08-11','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Juan Francisco', 'Vanoye', 'juanV@mail.com', '1992-08-17','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Jorge Ivan', 'Medel', 'jorgeIM@mail.com', '1992-08-31','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Jose Eduardo', 'Gutierrez', 'JoseE@mail.com', '1992-03-30','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Sara', 'Pinos', 'saraP@mail.com', '1958-08-19','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Miralda', 'De la cruz', 'blancaE@mail.com', '1975-07-13','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Cristiano Rolando', 'Dos santos', 'cr7E@mail.com', '1975-02-05','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Jesus Roberto', 'Gonzalez', 'jesusG@mail.com', '1989-05-11','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Marlene', 'Pinos', 'marleneP@mail.com', '1982-02-15','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Victor', 'Perea', 'victorP@mail.com', '1992-05-10','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Oscar', 'Flores', 'oscarF@mail.com', '1985-06-02','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Omar', 'Vazquez', 'OmarV@mail.com', '2001-09-01','');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Arturo', 'Galicia', 'arturoG@mail.com', '1992-03-11','');


INSERT INTO productos(nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Xbox Series X', 18050, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Control Elite Series 2', 4500, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Monitor 24 Samsung 3K', 9800, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Mouse gamer', 1500, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Call of Duty MW 2', 1200, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Silla gamer', 39000, NOW());

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) values ('Factura equipos gamer', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) values (1,1,1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) values (2,1,4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) values (1,1,5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) values (1,1,7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) values ('Factura juego', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) values (1,2,6);
