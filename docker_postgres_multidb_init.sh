#!/bin/bash
set -e

# Create the databases
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE "Shop";
    CREATE DATABASE "ShopTest";
EOSQL

# Apply the SQL initialization script to both databases
for DB in Shop ShopTest; do
    echo "Initializing database: $DB"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname="$DB" -f /docker-entrypoint-initdb.d/init.sql
done
