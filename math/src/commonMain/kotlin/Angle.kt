package top.ltfan.math

import kotlin.math.*

/**
 * Represents an angle in either degrees or radians.
 */
sealed class Angle : Comparable<Angle> {
    /**
     * Represents an angle in degrees.
     *
     * @property value The angle value in degrees.
     */
    data class Degrees(val value: Double) : Angle() {
        /**
         * Constructs a [Degrees] instance from a [Number].
         */
        constructor(value: Number) : this(value.toDouble())

        /**
         * Converts this angle to radians.
         */
        fun toRadians(): Radians = Radians(value * (PI / 180))
    }

    /**
     * Represents an angle in radians.
     *
     * @property value The angle value in radians.
     */
    data class Radians(val value: Double) : Angle() {
        /**
         * Constructs a [Radians] instance from a [Number].
         */
        constructor(value: Number) : this(value.toDouble())

        /**
         * Converts this angle to degrees.
         */
        fun toDegrees(): Degrees = Degrees(value * (180 / PI))
    }

    /**
     * Returns the angle's value in degrees.
     */
    val degrees
        get() = when (this) {
            is Degrees -> this
            is Radians -> toDegrees()
        }.value

    /**
     * Returns the angle's value in radians.
     */
    val radians
        get() = when (this) {
            is Degrees -> toRadians()
            is Radians -> this
        }.value

    /**
     * Returns a normalized angle, ensuring the value is within the range of 0 to 360 degrees for degrees
     * or 0 to 2π radians for radians.
     */
    val normalized
        get() = when (this) {
            is Degrees -> Degrees(value % 360).let { if (it.value < 0) Degrees(it.value + 360) else it }
            is Radians -> Radians(value % (2 * PI)).let { if (it.value < 0) Radians(it.value + 2 * PI) else it }
        }

    /**
     * Returns the sine of the angle in radians.
     */
    val sin inline get() = sin(radians)

    /**
     * Returns the cosine of the angle in radians.
     */
    val cos inline get() = cos(radians)

    /**
     * Returns the tangent of the angle in radians.
     */
    val tan inline get() = tan(radians)

    companion object {
        /**
         * Represents an angle of 0 degrees.
         */
        val ZERO: Angle = fromDegrees(0)

        /**
         * Creates an [Angle] from a value in degrees.
         */
        fun fromDegrees(value: Number): Angle = Degrees(value.toDouble())

        /**
         * Creates an [Angle] from a value in radians.
         */
        fun fromRadians(value: Number): Angle = Radians(value.toDouble())
    }

    /**
     * Adds another angle to this angle.
     */
    operator fun plus(other: Angle): Angle {
        return when (this) {
            is Degrees -> when (other) {
                is Degrees -> Degrees(this.value + other.value)
                is Radians -> Degrees(this.value + other.toDegrees().value)
            }

            is Radians -> when (other) {
                is Degrees -> Radians(this.value + other.toRadians().value)
                is Radians -> Radians(this.value + other.value)
            }
        }
    }

    /**
     * Subtracts another angle from this angle.
     */
    operator fun minus(other: Angle): Angle {
        return when (this) {
            is Degrees -> when (other) {
                is Degrees -> Degrees(this.value - other.value)
                is Radians -> Degrees(this.value - other.toDegrees().value)
            }

            is Radians -> when (other) {
                is Degrees -> Radians(this.value - other.toRadians().value)
                is Radians -> Radians(this.value - other.value)
            }
        }
    }

    /**
     * Multiplies this angle by a factor.
     */
    operator fun times(factor: Number): Angle {
        return when (this) {
            is Degrees -> Degrees(this.value * factor.toDouble())
            is Radians -> Radians(this.value * factor.toDouble())
        }
    }

    /**
     * Divides this angle by another angle, returning the ratio of their values.
     */
    operator fun div(divisor: Angle): Double {
        return when (this) {
            is Degrees -> when (divisor) {
                is Degrees -> this.value / divisor.value
                is Radians -> this.value / divisor.toDegrees().value
            }

            is Radians -> when (divisor) {
                is Degrees -> this.value / divisor.toRadians().value
                is Radians -> this.value / divisor.value
            }
        }
    }

    /**
     * Divides this angle by a number, returning a new angle.
     */
    operator fun div(divisor: Number): Angle {
        return when (this) {
            is Degrees -> Degrees(this.value / divisor.toDouble())
            is Radians -> Radians(this.value / divisor.toDouble())
        }
    }

    /**
     * Calculates the remainder of this angle when divided by another angle.
     */
    operator fun rem(other: Angle): Angle {
        return when (this) {
            is Degrees -> when (other) {
                is Degrees -> Degrees(this.value % other.value)
                is Radians -> Degrees(this.value % other.toDegrees().value)
            }

            is Radians -> when (other) {
                is Degrees -> Radians(this.value % other.toRadians().value)
                is Radians -> Radians(this.value % other.value)
            }
        }
    }

    /**
     * Returns the negation of this angle.
     */
    operator fun unaryMinus(): Angle {
        return when (this) {
            is Degrees -> Degrees(-this.value)
            is Radians -> Radians(-this.value)
        }
    }

    /**
     * Compares this angle with another angle.
     */
    override operator fun compareTo(other: Angle): Int {
        val epsilon = 1e-10
        val diff = when (this) {
            is Degrees -> when (other) {
                is Degrees -> this.value - other.value
                is Radians -> this.value - other.toDegrees().value
            }

            is Radians -> when (other) {
                is Degrees -> this.value - other.toRadians().value
                is Radians -> this.value - other.value
            }
        }
        return when {
            abs(diff) < epsilon -> 0
            diff > 0 -> 1
            else -> -1
        }
    }

    /**
     * Returns a string representation of the angle.
     */
    override fun toString(): String {
        return when (this) {
            is Degrees -> "$value°"
            is Radians -> "${toDegrees()}°"
        }
    }
}

/**
 * Converts a [Number] to an [Angle] in degrees.
 */
val Number.degrees get() = Angle.fromDegrees(this)

/**
 * Converts a [Number] to an [Angle] in radians.
 */
val Number.radians get() = Angle.fromRadians(this)

/**
 * Converts a [Number] to an [Angle] in radians, using the value as a multiple of π.
 */
val Number.piRadians get() = Angle.fromRadians(toDouble() * PI)

/**
 * Multiplies an [Angle] by a [Number], returning a new [Angle].
 */
operator fun Number.times(angle: Angle) = angle * this

/**
 * Calculates the arcsine of a [Number] and returns an [Angle] in radians.
 */
val Number.asin get() = Angle.fromRadians(asin(toDouble()))

/**
 * Calculates the arccosine of a [Number] and returns an [Angle] in radians.
 */
val Number.acos get() = Angle.fromRadians(acos(toDouble()))

/**
 * Calculates the arctangent of a [Number] and returns an [Angle] in radians.
 */
val Number.atan get() = Angle.fromRadians(atan(toDouble()))

/**
 * Calculates the arctangent of the quotient of two [Number] values and returns an [Angle] in radians.
 */
fun atan2(y: Number, x: Number) = Angle.fromRadians(kotlin.math.atan2(y.toDouble(), x.toDouble()))
