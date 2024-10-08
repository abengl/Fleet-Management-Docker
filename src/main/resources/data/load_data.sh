
#!/bin/bash

# Define the directory containing the SQL files
DATA_DIR="/docker-entrypoint-initdb.d"

# Wait for PostgreSQL to start (with a timeout of 60 seconds)
echo "Waiting for PostgreSQL to be ready..."
TIMEOUT=60
while ! pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" && [ $TIMEOUT -gt 0 ]; do
    sleep 2
    TIMEOUT=$((TIMEOUT - 2))
    echo "Still waiting for PostgreSQL... $TIMEOUT seconds left"
done

if [ $TIMEOUT -le 0 ]; then
    echo "PostgreSQL is not ready after waiting. Exiting."
    exit 1
fi

echo "PostgreSQL is ready. Loading SQL files..."

# Load data from each SQL file into the PostgreSQL database in alphabetical/numeric order
for file in $(ls "$DATA_DIR"/*.sql | sort); do
    echo "Loading $file..."
    if psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "$file"; then
        echo "Successfully loaded $file"
    else
        echo "Error loading $file"
        exit 1  # Stop execution on error
    fi
done

echo "Data loaded successfully."

## Define the directory containing the SQL files
#DATA_DIR="/docker-entrypoint-initdb.d"
#
#chmod 755 "$DATA_DIR"
#chmod 755 "$DATA_DIR"/*.sql
#
## Wait for PostgreSQL to start
#until pg_isready -U "$POSTGRES_USER"; do
#    echo "Waiting for PostgreSQL to be ready..."
#    sleep 2
#done
#
## Load data from each SQL file into the PostgreSQL database
#for file in "$DATA_DIR"/*.sql; do
#    echo "Loading $file..."
#    if psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "$file"; then
#        echo "Successfully loaded $file"
#    else
#        echo "Error loading $file"
#        echo "Output:"
#        psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "$file"  # This will give more output
#    fi
#done
