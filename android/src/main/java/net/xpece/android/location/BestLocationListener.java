package net.xpece.android.location;

import android.location.Location;
import android.os.Bundle;

import net.xpece.android.location.BestLocationProvider.LocationType;

/**
 * @author https://github.com/salendron/BestLocationProvider-Android
 */
public abstract class BestLocationListener {

  public void onLocationUpdate(Location location, LocationType type, boolean isFresh) {}

  public void onLocationUpdateTimeoutExceeded(LocationType type) {}

  public void onStatusChanged(String provider, int status, Bundle extras) {}

  public void onProviderEnabled(String provider) {}

  public void onProviderDisabled(String provider) {}
}
