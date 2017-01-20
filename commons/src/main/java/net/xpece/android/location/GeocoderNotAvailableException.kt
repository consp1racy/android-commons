package net.xpece.android.location

/**
 * @author Eugen on 20.12.2016.
 */

class GeocoderNotAvailableException : Exception {
    constructor() : super()
    constructor(cause: Throwable?) : super(cause)
}
