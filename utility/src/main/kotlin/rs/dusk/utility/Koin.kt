package rs.dusk.utility

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.ext.getFloatProperty
import org.koin.java.KoinJavaComponent.getKoin

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 26, 2020
 */

inline fun <reified T> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T = getKoin().get(qualifier, parameters)

fun getProperty(key: String): String = getKoin().getProperty(key)!!

@Suppress("UNCHECKED_CAST")
fun <T> getProperty(key: String, defaultValue: T): T = getKoin().getProperty(key, defaultValue.toString()) as T

fun getIntProperty(key: String): Int = getKoin().getProperty(key)!!.toInt()

fun getFloatProperty(key: String, defaultValue: Float = Float.MIN_VALUE): Float =
    getKoin().getFloatProperty(key, defaultValue)!!

fun getIntProperty(key: String, defaultValue: Int): Int =
    getKoin().getProperty(key)?.toIntOrNull() ?: defaultValue

inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = getKoin().inject(qualifier, parameters = parameters)

inline fun <reified S, reified P> bind(
    noinline parameters: ParametersDefinition? = null
): S = getKoin().bind<S, P>(parameters)