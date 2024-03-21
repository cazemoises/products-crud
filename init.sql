CREATE TABLE product IF NOT EXISTS (
    id UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    "description" TEXT NOT NULL
);

CREATE TABLE "user" IF NOT EXISTS (
    id UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE order IF NOT EXISTS (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE order_status IF NOT EXISTS (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order(id)
);

CREATE TABLE order_history IF NOT EXISTS (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order(id)
);