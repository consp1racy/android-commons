package net.xpece.android.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * @author https://github.com/salendron/BestLocationProvider-Android
 * @author Eugen Pechanec
 */
@SuppressLint("MissingPermission")
public class BestLocationProvider {

    private static final String TAG = BestLocationProvider.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    public @interface LocationType {}

    @LocationType public static final int TYPE_GPS = 0;
    @LocationType public static final int TYPE_CELL = 1;
    @LocationType public static final int TYPE_UNKNOWN = 2;

    private static final int TOO_OLD_LOCATION_DELTA = 1000 * 60 * 2;
    public static final float TOO_INACURATE_LOCATION_DELTA = 200;

    private WeakReference<Context> mContext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mLocation;

    private Timeout mGPSTimeout;
    private Timeout mCellTimeout;

    private BestLocationListener mListener;

    //config
    private final boolean mUseGPSLocation;
    private final boolean mUseCellLocation;
    private final long mMaxGPSLocationUpdateTimespan;
    private final long mMaxCellLocationUpdateTimespan;
    private final long mMinTime;
    private final float mMinDistance;

    public BestLocationProvider(Context context, boolean useGPSLocation, boolean useCellLocation,
                                long maxGPSLocationUpdateTimespan, long maxCellLocationUpdateTimespan, long minTime, float minDistance) {
        this.mContext = new WeakReference<>(context);
        this.mUseGPSLocation = useGPSLocation;
        this.mUseCellLocation = useCellLocation;
        this.mMaxGPSLocationUpdateTimespan = maxGPSLocationUpdateTimespan;
        this.mMaxCellLocationUpdateTimespan = maxCellLocationUpdateTimespan;
        this.mMinTime = minTime;
        this.mMinDistance = minDistance;

        initLocationListener();
        initLocationManager();
    }

    public void startLocationUpdatesWithListener(BestLocationListener listener) {
        this.mListener = listener;

        Location lastKnownLocationCell = null;
        Location lastKnownLocationGPS = null;

        if (mUseCellLocation) {
            if (mLocationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, mMinTime, mMinDistance, mLocationListener);

                if (this.mMaxCellLocationUpdateTimespan > 0) {
                    mCellTimeout = new Timeout();
                    mCellTimeout.setTimeout(this.mMaxCellLocationUpdateTimespan);
                    mCellTimeout.setLocationType(TYPE_CELL);
                    mCellTimeout.execute();
                }

                lastKnownLocationCell = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else {
                Log.e(TAG, "Network provider not present on the device!");
            }
        }

        if (mUseGPSLocation) {
            if (mLocationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mMinTime, mMinDistance, mLocationListener);

                if (this.mMaxGPSLocationUpdateTimespan > 0) {
                    mGPSTimeout = new Timeout();
                    mGPSTimeout.setTimeout(this.mMaxGPSLocationUpdateTimespan);
                    mGPSTimeout.setLocationType(TYPE_GPS);
                    mGPSTimeout.execute();
                }

                lastKnownLocationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                Log.e(TAG, "Network provider not present on the device!");
            }
        }

        if (lastKnownLocationCell != null && isBetterLocation(lastKnownLocationCell, mLocation)) {
            updateLocation(lastKnownLocationCell, TYPE_CELL, false);
        }

        if (lastKnownLocationGPS != null && isBetterLocation(lastKnownLocationGPS, mLocation)) {
            updateLocation(lastKnownLocationGPS, TYPE_GPS, false);
        }
    }

    public void stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener);

        //remove timeout threads
        if (mGPSTimeout != null) {
            try { mGPSTimeout.cancel(true); } catch (Exception e) { }
            mGPSTimeout = null;
        }

        if (mCellTimeout != null) {
            try { mCellTimeout.cancel(true); } catch (Exception e) { }
            mCellTimeout = null;
        }
    }

    private void restartTimeout(@LocationType int type) {

        if (type == TYPE_GPS) {
            if (mGPSTimeout != null) {
                try { mGPSTimeout.cancel(true); } catch (Exception e) { }
                mGPSTimeout = new Timeout();
                mGPSTimeout.setTimeout(this.mMaxGPSLocationUpdateTimespan);
                mGPSTimeout.setLocationType(TYPE_GPS);
                mGPSTimeout.execute();
            }
        }

        if (type == TYPE_CELL) {
            if (mCellTimeout != null) {
                try { mCellTimeout.cancel(true); } catch (Exception e) { }
                mCellTimeout = new Timeout();
                mCellTimeout.setTimeout(this.mMaxCellLocationUpdateTimespan);
                mCellTimeout.setLocationType(TYPE_CELL);
                mCellTimeout.execute();
            }
        }
    }

    private void updateLocation(Location location, @LocationType int type, boolean isFresh) {
        mLocation = location;
        mListener.onLocationUpdate(location, type, isFresh);
    }

    private void initLocationManager() {
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    }

    private void initLocationListener() {
        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (isBetterLocation(location, mLocation)) {
                    updateLocation(location, providerToLocationType(location.getProvider()), true);

                    if (providerToLocationType(location.getProvider()) == TYPE_CELL) {
                        if (mCellTimeout != null) {
                            mCellTimeout.resetTimeout();
                        }
                    }

                    if (providerToLocationType(location.getProvider()) == TYPE_GPS) {
                        if (mGPSTimeout != null) {
                            mGPSTimeout.resetTimeout();
                        }
                    }

                    Log.d(TAG, "NEW BEST LOCATION: " + locationToString(mLocation));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                mListener.onStatusChanged(provider, status, extras);
            }

            public void onProviderEnabled(String provider) {
                mListener.onProviderEnabled(provider);
            }

            public void onProviderDisabled(String provider) {
                mListener.onProviderDisabled(provider);
            }
        };
    }

    protected Context getContext() {
        return mContext.get();
    }

    public boolean isAnyProviderActive() {
        return mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            || mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @LocationType
    private int providerToLocationType(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            return TYPE_GPS;
        } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            return TYPE_CELL;
        } else {
            Log.w(TAG, "providerToLocationType Unknown Provider: " + provider);
            return TYPE_UNKNOWN;
        }
    }

    public String locationToString(Location l) {
        StringBuilder sb = new StringBuilder();

        sb.append("PROVIDER: ");
        sb.append(l.getProvider());
        sb.append(" - LAT: ");
        sb.append(l.getLatitude());
        sb.append(" - LON: ");
        sb.append(l.getLongitude());
        sb.append(" - BEARING: ");
        sb.append(l.getBearing());
        sb.append(" - ALT: ");
        sb.append(l.getAltitude());
        sb.append(" - SPEED: ");
        sb.append(l.getSpeed());
        sb.append(" - TIME: ");
        sb.append(l.getTime());
        sb.append(" - ACC: ");
        sb.append(l.getAccuracy());

        return sb.toString();
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TOO_OLD_LOCATION_DELTA;
        boolean isSignificantlyOlder = timeDelta < -TOO_OLD_LOCATION_DELTA;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > TOO_INACURATE_LOCATION_DELTA;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
            currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    //timeout threads
    private class Timeout extends AsyncTask<Void, Void, Void> {

        private @LocationType int mLocationType;
        private long mTimeout;
        private long mStartTime;

        public void setLocationType(@LocationType int locationtype) {
            mLocationType = locationtype;
        }

        public void setTimeout(long timeout) {
            mTimeout = timeout;
        }

        public void resetTimeout() {
            mStartTime = new Date().getTime();
        }

        @Override
        protected Void doInBackground(Void... params) {
            resetTimeout();

            try {
                while (new Date().getTime() < mStartTime + mTimeout) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
//        Timber.e("Timeout: Exception in doInBackground", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mListener.onLocationUpdateTimeoutExceeded(mLocationType);
            restartTimeout(mLocationType);
        }
    }

}
