package com.rydzo.common.constant;

public class RydzoConstants {
    public static final String API_VERSION = "v1";
    public static final String BASE_API_PATH = "/api/" + API_VERSION;
    
    // Service URLs
    public static final String USER_SERVICE_URL = "http://rydzo-user-service:8081";
    public static final String VENDOR_SERVICE_URL = "http://rydzo-vendor-service:8082";
    public static final String VEHICLE_SERVICE_URL = "http://rydzo-vehicle-service:8083";
    public static final String LOCAL_CARPOOL_SERVICE_URL = "http://rydzo-local-carpool-service:8084";
    public static final String INTERCITY_POOL_SERVICE_URL = "http://rydzo-intercity-pool-service:8085";
    public static final String BOOKING_SERVICE_URL = "http://rydzo-booking-service:8086";
    public static final String PAYMENT_SERVICE_URL = "http://rydzo-payment-service:8087";
    public static final String NOTIFICATION_SERVICE_URL = "http://rydzo-notification-service:8088";
    
    // Default values
    public static final double DEFAULT_RATING = 5.0;
    public static final int DEFAULT_RIDES = 0;
}

