package com.lankahardwared.lankahw.control;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

;

/*
 * added @rashmi
 * bcz required to change location service
 */

public class LocationProvider implements LocationListener {

    public static final int LOCATION_TYPE_GPS = 100901;
    public static final int LOCATION_TYPE_NETWORK = 100902;
    public static final int LOCATION_TYPE_PASSIVE = 100903;
    public static final long LOCATION_SWITCH_DELAY = 10000;

    private static final String LOG_TAG = LocationProvider.class.getSimpleName();

    private LocationManager locationManager;
    private ICustomLocationListener customLocationListener;
    private OnGPSTimeoutListener onGPSTimeoutListener;

    private Handler locationHandler;
    private Runnable switchRunnable, notifyRunnable;

    private long timeOutDuration, gpsTimeOutDuration;

    private boolean gpsActive, networkActive, passiveActive;

    private LocationListener locationListener;

    private int locationType;

    private boolean gotLocation;
    private boolean firstAttempt, needsNotify;

    /**
     * Use this constructor to create a CustomLocationListenerService object without a timeout.
     *
     * @param locationManager        A LocationManager object.
     * @param customLocationListener A custom listener object to implement pre, on progress, post,
     *                               on unable execution methods.
     */
    public LocationProvider(LocationManager locationManager,
                            ICustomLocationListener customLocationListener) {

        this.locationManager = locationManager;
        this.customLocationListener = customLocationListener;
        this.timeOutDuration = 0;

        gpsActive = false;
        networkActive = false;

        locationHandler = new Handler();
        locationListener = LocationProvider.this;

        firstAttempt = true;
        needsNotify = false;

        // Set the location switch offset to default value
        gpsTimeOutDuration = LOCATION_SWITCH_DELAY;

    }

    /**
     * Use this constructor to create a CustomLocationListenerService object with a timeout.
     *
     * @param locationManager        A LocationManager object.
     * @param customLocationListener A custom listener object to implement pre, on progress, post,
     *                               on unable execution methods.
     * @param timeOutDuration        Time out time in milliseconds. (0 if unlimited)
     */
    public LocationProvider(LocationManager locationManager,
                            ICustomLocationListener customLocationListener,
                            long timeOutDuration) {

        this.locationManager = locationManager;
        this.customLocationListener = customLocationListener;
        this.timeOutDuration = timeOutDuration;

        gpsActive = false;
        networkActive = false;

        locationHandler = new Handler();
        locationListener = LocationProvider.this;

        firstAttempt = true;
        needsNotify = false;

        // Set the location switch offset to default value
        gpsTimeOutDuration = LOCATION_SWITCH_DELAY;

    }

    /**
     * Set a GPS Timeout listener. This will execute the provided interface method onGPSTimeOut() when
     * the time exceeds the timeout duration.
     *
     * @param onGPSTimeoutListener The interface object to execute on time out.
     * @param timeOut              The duration of the time out. (Pass 0 to keep default value)
     *                             throws UnsupportedOperationException When GPS is not available
     */
    public void setOnGPSTimeoutListener(OnGPSTimeoutListener onGPSTimeoutListener,
                                        long timeOut) throws UnsupportedOperationException {
        refreshLocationParams();
        if (gpsActive) {
            this.onGPSTimeoutListener = onGPSTimeoutListener;
            if (timeOut > 0) this.gpsTimeOutDuration = timeOut;
            needsNotify = true;
        } else {
            throw new UnsupportedOperationException("GPS not available to set a timeout listener");
        }

    }

//    public void setNeedsNotify(boolean needsNotify) {
//        this.needsNotify = needsNotify;
//    }

    /**
     * Begin the GPS/Network locating process.
     */
    public void startLocating() {

        requestLocationUpdates();

    }

    private void refreshLocationParams() {
        try {
            gpsActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            gpsActive = false;
        }

        try {
            networkActive = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            networkActive = false;
        }

        try {
            passiveActive = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            passiveActive = false;
        }

        Log.d(LOG_TAG, "GPS State : " + gpsActive
                + "\nNetwork State : " + networkActive
                + "\nPassive State : " + passiveActive);

    }

    private void requestLocationUpdates() {

        refreshLocationParams();

        boolean possibleToGetLocation;

        if (networkActive) {

            if (gpsActive) {
                // Request timed location update using GPS
                requestGPSLocation();
                locationType = LOCATION_TYPE_GPS;

                if (firstAttempt && needsNotify && onGPSTimeoutListener != null) {
                    // Show a message to move to a more clear area
                    if (notifyRunnable == null) {
                        notifyRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!gotLocation) {
                                    stopLocating();
                                    onGPSTimeoutListener.onGPSTimeOut();
                                }
                                firstAttempt = false;
                            }
                        };
                    }

                    // Notify the user to move to a clear area

                    locationHandler.postDelayed(notifyRunnable, gpsTimeOutDuration);

                    possibleToGetLocation = true;

                } else {
                    // Switch to network as usual
                    if (switchRunnable == null) {
                        switchRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!gotLocation) {
                                    locationManager.removeUpdates(locationListener);
                                    requestNetworkLocation();
                                    locationType = LOCATION_TYPE_NETWORK;
                                }
                            }
                        };
                    }

                    // Stop getting the location from GPS and get from network if taking too long (more than 20s)
                    locationHandler.postDelayed(switchRunnable, LOCATION_SWITCH_DELAY);
                    possibleToGetLocation = true;
                    Log.d(LOG_TAG, "Scheduled location type switch");
                }


            } else {
                // Request location update using network
                requestNetworkLocation();
                locationType = LOCATION_TYPE_NETWORK;
                possibleToGetLocation = true;
            }
        } else {
            if (gpsActive) {
                // Only GPS active. Request location from GPS
                requestGPSLocation();
                locationType = LOCATION_TYPE_GPS;
                possibleToGetLocation = true;
            } else {
                // Absolutely unable to get location.
                customLocationListener.onUnableToGetLocation();
                possibleToGetLocation = false;
            }
        }

        if (possibleToGetLocation) {
            if (timeOutDuration > 0) {
                // There's a timeout defined. Therefore terminate the location accessing process
                Runnable terminateProcess = new Runnable() {
                    @Override
                    public void run() {
                        stopLocating();
                    }
                };
                locationHandler.postDelayed(terminateProcess, timeOutDuration);
            }
        }

    }

//    private void requestLocationUpdates() {
//
//        refreshLocationParams();
//
//        boolean possibleToGetLocation = false;
//
//        if (passiveActive) {
//        	
////        	requestNetworkLocation();
////            locationType = LOCATION_TYPE_NETWORK;
//        	
//            if (gpsActive) {
//                // Request timed location update using GPS
//                requestGPSLocation();
//                locationType = LOCATION_TYPE_GPS;
//
//                if (firstAttempt && needsNotify && onGPSTimeoutListener != null) {
//                    // Show a message to move to a more clear area
//                    if (notifyRunnable == null) {
//                        notifyRunnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!gotLocation) {
//                                    stopLocating();
//                                    onGPSTimeoutListener.onGPSTimeOut();
//                                }
//                                firstAttempt = false;
//                            }
//                        };
//                    }
//
//                    // Notify the user to move to a clear area
//                    locationHandler.postDelayed(notifyRunnable, gpsTimeOutDuration);
//                    possibleToGetLocation = true;
//
//                } else {
//                    // Switch to network as usual
//                    if (switchRunnable == null) {
//                        switchRunnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!gotLocation) {
//                                    locationManager.removeUpdates(locationListener);
//                                    requestPassiveLocation();
//                                    locationType = LOCATION_TYPE_PASSIVE;
//                                }
//                            }
//                        };
//                    }
//
//                    // Stop getting the location from GPS and get from network if taking too long (more than 20s)
//                    locationHandler.postDelayed(switchRunnable, ValueHolder.LOCATION_SWITCH_DELAY);
//                    possibleToGetLocation = true;
//                    Log.d(LOG_TAG, "Scheduled location type switch");
//                }
//
//
//            } else {
//                // Request location update using network
//                requestNetworkLocation();
//                locationType = LOCATION_TYPE_NETWORK;
//                possibleToGetLocation = true;
//            }
//        } else {
//            if (gpsActive) {
//                // Only GPS active. Request location from GPS
//                requestGPSLocation();
//                locationType = LOCATION_TYPE_GPS;
//                possibleToGetLocation = true;
//            } else {
//                // Absolutely unable to get location.
//                customLocationListener.onUnableToGetLocation();
//                possibleToGetLocation = false;
//            }
//        }
//
//        if (possibleToGetLocation) {
//            if (timeOutDuration > 0) {
//                // There's a timeout defined. Therefore terminate the location accessing process
//                Runnable terminateProcess = new Runnable() {
//                    @Override
//                    public void run() {
//                        stopLocating();
//                    }
//                };
//                locationHandler.postDelayed(terminateProcess, timeOutDuration);
//            }
//        }
//
//    }

    /**
     * Force stop the geo locating process.
     */
    public void stopLocating() {
        locationManager.removeUpdates(locationListener);
        if (!gotLocation) {
            customLocationListener.onUnableToGetLocation();
            Log.d(LOG_TAG, "Terminating the locating process");
            locationHandler.removeCallbacks(notifyRunnable);
        }
    }

    private void requestGPSLocation() {
        customLocationListener.onProgress(LOCATION_TYPE_GPS);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void requestNetworkLocation() {
        customLocationListener.onProgress(LOCATION_TYPE_NETWORK);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void requestPassiveLocation() {
        customLocationListener.onProgress(LOCATION_TYPE_PASSIVE);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d(LOG_TAG, "Latitude : " + location.getLatitude()
                    + ", Longitude : " + location.getLongitude());
            customLocationListener.onGotLocation(location, locationType);
            locationManager.removeUpdates(locationListener);
            gotLocation = true;
        } else customLocationListener.onUnableToGetLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        customLocationListener.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        customLocationListener.onProviderDisabled(provider);
    }


    public @interface LocationType {
    }

    public interface ICustomLocationListener {
        void onProviderEnabled(String provider);

        void onProviderDisabled(String provider);

        void onUnableToGetLocation();

        void onGotLocation(Location location, @LocationType int locationType);

        void onProgress(@LocationType int type);

//        void onGPSTimeout();
    }

    public interface OnGPSTimeoutListener {
        void onGPSTimeOut();
    }

}

