CREATE DATABASE test;
USE test;
DROP TABLE  user_t;

-- Create User table
CREATE TABLE User (
    user_id INT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

-- Insert data into User table
INSERT INTO User (user_id, username) VALUES
(1, 'John Doe'),
(2, 'Jane Don'),
(3, 'Alice Jones'),
(4, 'Lisa Romero');

-- Create Training_details table
CREATE TABLE Training_details (
    user_training_id INT PRIMARY KEY,
    user_id INT,
    training_id INT,
    training_date DATE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Insert data into Training_details table
INSERT INTO Training_details (user_training_id, user_id, training_id, training_date) VALUES
(1, 1, 1, '2015-08-02'),
(2, 2, 1, '2015-08-03'),
(3, 3, 2, '2015-08-02'),
(4, 4, 2, '2015-08-04'),
(5, 2, 2, '2015-08-03'),
(6, 1, 1, '2015-08-02'),
(7, 3, 2, '2015-08-04'),
(8, 4, 3, '2015-08-03'),
(9, 1, 4, '2015-08-03'),
(10, 3, 1, '2015-08-02'),
(11, 4, 2, '2015-08-04'),
(12, 3, 2, '2015-08-02'),
(13, 1, 1, '2015-08-02'),
(14, 4, 3, '2015-08-03');

select * from User;
select * from Training_details;

SELECT
    u.user_id,
    u.username,
    td.training_id,
    td.training_date,
    COUNT(*) AS count
FROM
    User u
JOIN
    Training_details td ON u.user_id = td.user_id
WHERE
    td.training_date IN (
        SELECT training_date
        FROM Training_details
        WHERE user_id = u.user_id AND training_id = td.training_id
        GROUP BY user_id, training_id, training_date
        HAVING COUNT(*) > 1
    )
GROUP BY
    u.user_id,
    u.username,
    td.training_id,
    td.training_date
HAVING
    COUNT(*) > 1
ORDER BY
    td.training_date DESC;

