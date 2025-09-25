-- CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

-- Insertar categorías
insert into categorias (name, description) values ('fruits', 'fruits');
insert into categorias (name, description) values ('drinks', 'drinks');
insert into categorias (name, description) values ('meat', 'meat');
insert into categorias (name, description) values ('fish', 'fish');
insert into categorias (name, description) values ('dairy', 'dairy');
insert into categorias (name, description) values ('snacks', 'snacks');
insert into categorias (name, description) values ('vegetables', 'vegetables');

-- Insertar productos
insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Chicken - Base', 973.79, 10, 8.99, 1);  -- (categoria 'meat')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Apple', 2.99, 50, 1.25, 1);  -- (categoria 'fruits')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Orange Juice', 3.49, 30, 2.00, 2);  -- (categoria 'drinks')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Beef Steak', 12.99, 20, 5.50, 3);  -- (categoria 'meat')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Salmon Fillet', 15.99, 15, 6.75, 4);  -- (categoria 'fish')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Milk', 1.99, 100, 0.80, 5);  -- (categoria 'dairy')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Cheese', 5.49, 40, 2.50, 5);  -- (categoria 'dairy')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Potato Chips', 2.99, 200, 1.00, 6);  -- (categoria 'snacks')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Carrot', 1.29, 150, 0.60, 7);  -- (categoria 'vegetables')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Lettuce', 1.79, 120, 0.80, 7);  -- (categoria 'vegetables')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Banana', 1.39, 80, 0.90, 1);  -- (categoria 'fruits')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Coca-Cola', 1.99, 60, 0.80, 2);  -- (categoria 'drinks')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Pineapple', 3.99, 25, 1.60, 1);  -- (categoria 'fruits')

insert into productos (name, price, stock, manufacturing_cost, category_id)
values ('Shrimp', 8.99, 10, 4.50, 4);  -- (categoria 'fish')
