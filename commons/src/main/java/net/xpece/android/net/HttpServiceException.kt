package net.xpece.android.net


open class HttpServiceException(val method: String, val url: String, val httpCode: Int, val httpMessage: String) : RuntimeException() {
    val isSuccess: Boolean
        get() = httpCode in 200..299
    val isClientError: Boolean
        get() = httpCode in 400..499
    val isServerError: Boolean
        get() = httpCode in 500..599

    override val message: String
        get() = "$httpCode $httpMessage"

    open val diagnosticMessage: String
        get() = "$message by $method $url"

    companion object {
        fun from(request: okhttp3.Request, response: retrofit2.Response<out Any>) =
                from(request, response.raw())

        fun from(request: okhttp3.Request, response: okhttp3.Response) =
                HttpServiceException(request.method(), request.url().toString(), response.code(), response.message())
    }
}
