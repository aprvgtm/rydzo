# Rydzo Microservices - Corrected Architecture

Rydzo is a **multi-modal transportation platform** with:

## Three Main Services:

### 1. LOCAL CARPOOL SERVICE (Port: 8083)
- Users create/join local city rides
- Share car rides within same city
- Share expenses
- Recurring daily routes
- Advance booking (7+ days)
- Regular users driving their own cars

### 2. INTERCITY POOL SERVICE (Port: 8084)
- Long-distance rides between cities
- Multiple stops/stations
- Advance booking (14+ days)
- Premium pricing structure
- Refund/cancellation policies
- Regular users driving intercity routes

### 3. QUICK RIDE SERVICE (Port: 8085)
- **VENDOR PLATFORM** for local operators
- Vendors (who don't have their own app) list their vehicles:
  - **Cars** (taxis, from regular taxi operators)
  - **Bikes** (from bike rental shops)
  - **Scooties** (from rental services)
  - **Autos** (from auto-rickshaw operators)
- Vendors set their own pricing & availability
- Verified users book from vendors' vehicles
- Vendors earn commission per ride
- Real-time order management for vendors
- Vendor dashboard to track earnings

## Microservices Structure:

```
rydzo-platform/
├── rydzo-api-gateway/              # API Gateway
├── rydzo-service-registry/         # Eureka Server
├── rydzo-config-server/            # Config Server
│
├── rydzo-user-service/             # User Management & Verification
│   ├── User registration
│   ├── Document upload (KYC/DL/Aadhar)
│   ├── Verification status
│   └── User profiles
│
├── rydzo-vendor-service/           # Vendor Management (NEW - For Quick Ride)
│   ├── Vendor registration
│   ├── Vendor verification
│   ├── Vendor dashboard
│   ├── Earnings management
│   └── Vendor analytics
│
├── rydzo-vehicle-service/          # Vehicle Management
│   ├── User vehicles (for carpool)
│   ├── Vendor vehicles (for quick ride)
│   ├── Vehicle verification (RC, Insurance)
│   ├── Vehicle photos & details
│   └── Pricing management
│
├── rydzo-local-carpool-service/    # Local City Rides
│   ├── Create local rides
│   ├── Join rides
│   ├── Recurring routes
│   ├── Route management
│   └── Expense sharing
│
├── rydzo-intercity-pool-service/   # Intercity Rides
│   ├── Intercity routes
│   ├── Stop/Station management
│   ├── Advanced scheduling
│   └── Route pricing
│
├── rydzo-quickride-service/        # Vendor Platform
│   ├── Real-time ride requests
│   ├── Vendor routing engine
│   ├── Vehicle availability
│   ├── Pricing rules
│   └── Vendor order queue
│
├── rydzo-booking-service/          # Booking Management
│   ├── Multi-service bookings
│   ├── Confirmation workflow
│   ├── Cancellation management
│   └── Booking history
│
├── rydzo-payment-service/          # Payment Processing
│   ├── Multiple payment methods
│   ├── Wallet system
│   ├── Commission calculation
│   └── Invoice generation
│
├── rydzo-notification-service/     # Notifications
│   ├── Email notifications
│   ├── SMS alerts
│   ├── Push notifications
│   └── Broadcast messages
│
├── rydzo-common-library/           # Shared Code
│   ├── DTOs
│   ├── Utilities
│   ├── Constants
│   └── Base classes
│
└── docker-compose.yml              # All services deployment
```

## Key Models:

### User
- Personal details
- Document verification status
- Account type (Individual)
- Ratings & reviews
- Booking history

### Vendor (NEW)
- Business details
- Document verification
- Vehicle count
- Ratings
- Commission settings
- Bank details (for payout)
- Subscription/plan

### Vehicle
- Owner (User or Vendor)
- Type (Car, Bike, Scooty, Auto)
- Registration details
- Insurance info
- Photos & documents
- Pricing rules

### Quick Ride Orders (NEW)
- Customer request
- Assigned vendor
- Vehicle details
- Driver details
- Pricing (vendor's own)
- Real-time tracking
- Payment & tip

## Data Isolation:

Each service has separate database:
- `rydzo_users_db`
- `rydzo_vendors_db`
- `rydzo_vehicles_db`
- `rydzo_local_carpool_db`
- `rydzo_intercity_pool_db`
- `rydzo_quickride_db`
- `rydzo_bookings_db`
- `rydzo_payments_db`

## Quick Ride Flow (Vendor Platform):

1. **Vendor Registration**
   - Vendor uploads business documents
   - Admin verification
   - Approval/Rejection

2. **Vehicle Registration**
   - Vendor adds vehicles (cars, bikes, scooties)
   - Document upload (RC, Insurance)
   - Vehicle photos & details
   - Set pricing per km

3. **Customer Books**
   - Customer requests ride
   - System matches best vendor/vehicle
   - Vendor receives notification
   - Vendor accepts/rejects
   - Real-time tracking

4. **Payment & Payout**
   - Customer charges based on vendor's pricing
   - Commission deducted
   - Vendor payout weekly/monthly

---

Ready to implement each microservice?

