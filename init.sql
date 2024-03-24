CREATE TABLE "product" (
    id UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    "description" TEXT NOT NULL,
    quantity INT NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE
);

CREATE TABLE customer (
    id UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE
);

CREATE TABLE "order" (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE order_status (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "order"(id)
);

CREATE TABLE order_history (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "order"(id)
);