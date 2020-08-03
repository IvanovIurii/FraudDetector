INSERT INTO "user" (name, email, last_location, frequency, balance) VALUES
('Bob', 'bob@gmail.com', '213.61.89.122', 10.0, 5000.0),
('Alice', 'alice@gmail.com', '213.61.89.122', 1.0, 2000.0),
('Mark', 'mark@gmail.com', '213.61.89.122', 0.0, 50.0);

INSERT INTO "transaction" (uuid, amount, timestamp, status, user_id) VALUES
('edb6ca20-4932-40ce-9063-c9b79d501b70', 100, '2020-01-01', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b71', 200, '2020-01-02', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b72', 300, '2020-01-03', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b73', 400, '2020-02-01', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b74', 500, '2020-03-01', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b75', 600, '2020-03-02', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b76', 999, '2020-03-05', 'fail', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b78', 700, '2020-03-07', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b79', 800, '2020-05-07', 'success', 1),
('edb6ca20-4932-40ce-9063-c9b79d501b89', 900, '2020-08-01', 'success', 1),

('adb6ca20-4932-40ce-9063-c9b79d501b70', 2000, '2020-01-01', 'success', 2);