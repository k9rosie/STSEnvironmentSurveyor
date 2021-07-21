package ie.k9ros.stsenvironmentsurveyor.utils

inline fun <R, T> bounded(maxSize: Int, placeholder: T, contents: List<R?>?, body: (item: R) -> T): List<T> =
    if (contents != null)
        List(maxSize) { i ->
            contents.getOrNull(i)?.let { body(it) } ?: placeholder
        }
    else
        List(maxSize) { placeholder }

fun <T> bounded(maxSize: Int, placeholder: T): List<T> =
    List(maxSize) { placeholder }
