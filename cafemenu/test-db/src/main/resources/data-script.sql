INSERT INTO item_table (item_id, item_name, item_price) VALUES (1, 'Burger', 5.0);
INSERT INTO item_table (item_id, item_name, item_price) VALUES (2, 'Nuggets', 3.0);
INSERT INTO item_table (item_id, item_name, item_price) VALUES (3, 'French fried', 2.5);
INSERT INTO item_table (item_id, item_name, item_price) VALUES (4, 'Coffee', 1.5);
INSERT INTO item_table (item_id, item_name, item_price) VALUES (5, 'Tea', 1.0);

<<<<<<< HEAD
INSERT INTO order_table (order_id, order_employee_id, order_status) VALUES (1, 21, 1);
INSERT INTO order_table (order_id, order_employee_id, order_status) VALUES (2, 21, 1);
INSERT INTO order_table (order_id, order_employee_id, order_status) VALUES (3, 15, 1);
INSERT INTO order_table (order_id, order_employee_id, order_status) VALUES (4, 12, 1);

INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (1, 1, 'Burger', 5.0, 1);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (1, 4, 'Coffee', 1.5, 1);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (1, 5, 'Tea', 1.0, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (2, 1, 'Burger', 5.0, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (2, 3, 'French fried', 2.5, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (3, 4, 'Coffee', 1.5, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (3, 5, 'Tea', 1.0, 3);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (4, 2, 'Nuggets', 3.0, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (4, 3, 'French fried', 2.5, 3);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (4, 4, 'Coffee', 1.5, 2);
INSERT INTO item_in_order (iio_order_id, iio_item_id, iio_item_name, iio_item_price, iio_item_count) VALUES (4, 5, 'Tea', 1.0, 1);
=======
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (1, 1, 1);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (1, 2, 3);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (1, 4, 1);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (2, 4, 2);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (2, 5, 1);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (3, 1, 1);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (3, 3, 2);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (3, 2, 1);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (3, 4, 4);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (4, 4, 10);
INSERT INTO order_table (order_id, order_item_id, order_item_count) VALUES (4, 5, 12);

INSERT INTO orderslist_table (ol_order_id, ol_employee_id, ol_order_status) VALUES (1, 5, 2);
INSERT INTO orderslist_table (ol_order_id, ol_employee_id, ol_order_status) VALUES (2, 7, 1);
INSERT INTO orderslist_table (ol_order_id, ol_employee_id, ol_order_status) VALUES (3, 12, 1);
INSERT INTO orderslist_table (ol_order_id, ol_employee_id, ol_order_status) VALUES (4, 11, 0);
>>>>>>> 12411dfee4c748110da942719896b9673b8ed7d1
