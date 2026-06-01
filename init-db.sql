-- Create rydzo database user with proper permissions
CREATE USER rydzo_user WITH PASSWORD 'rydzo_password';
ALTER USER rydzo_user CREATEDB;

-- Create all databases for microservices
CREATE DATABASE rydzo_users_db OWNER rydzo_user;
CREATE DATABASE rydzo_vendors_db OWNER rydzo_user;
CREATE DATABASE rydzo_vehicles_db OWNER rydzo_user;
CREATE DATABASE rydzo_local_carpool_db OWNER rydzo_user;
CREATE DATABASE rydzo_intercity_pool_db OWNER rydzo_user;
CREATE DATABASE rydzo_bookings_db OWNER rydzo_user;
CREATE DATABASE rydzo_payments_db OWNER rydzo_user;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE rydzo_users_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_vendors_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_vehicles_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_local_carpool_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_intercity_pool_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_bookings_db TO rydzo_user;
GRANT ALL PRIVILEGES ON DATABASE rydzo_payments_db TO rydzo_user;

