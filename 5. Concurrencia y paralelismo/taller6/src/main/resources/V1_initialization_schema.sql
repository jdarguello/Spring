CREATE TABLE Producto (
    producto_id     BIGSERIAL PRIMARY KEY NOT NULL,
    nombre          VARCHAR(255) NOT NULL,
    descripcion     TEXT,
    precio          FLOAT NOT NULL,
    inventario      INTEGER
);