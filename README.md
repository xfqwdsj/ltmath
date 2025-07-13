# ltmath

![Maven Central Version](https://img.shields.io/maven-central/v/top.ltfan.math/math)

A Kotlin multiplatform library for mathematical operations and utilities.

## Getting Started

To use `ltmath` in your Kotlin Multiplatform project, add the following dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("top.ltfan.math:math:<version>")
}
```

Or if you are using Gradle Version Catalogs, add the following to your `gradle/libs.versions.toml`:

```toml
[versions]
ltmath = "<version>"

[libraries]
ltmath = { module = "top.ltfan.math:math", version.ref = "math" }
```

Make sure your `settings.gradle.kts` includes the repository:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
```

## Features

### Angle

Represents an angle in degrees or radians, providing conversion, normalization, and arithmetic operations.

Here's a quick example of how to use the `Angle` class:

```kotlin
// Basic usage of the Angle class

val a = 180.degrees
val b = Angle.fromRadians(PI)
val c = 1.piRadians
```

```kotlin
// Angle operations

val sum = a + b // Adds two angles, result in the type of the first operand (Degrees)
val normalized = sum.normalized // Normalizes the angle to the range [0, 360) for degrees or [0, 2π) for radians

val radians = a.radians // Converts degrees to radians, resulting in a `Double`
val degrees = b.degrees // Converts radians to degrees, resulting in a `Double`
val value = c.radians // Gets the value in radians, which is a `Double`
```

```kotlin
// Trigonometric functions. Shortcuts of `kotlin.math` functions for `Angle` type.

val sineValue = a.sin // Computes the sine of the angle, resulting in a `Double`

val angleFromAtan2 =
    atan2(1.0, 1.0) // Computes the angle from the arctangent of two values, resulting in an `Angle.Radians`
```

## Platforms

- JVM (including Android)
- macOS (x64, arm64)
- iOS (SimulatorArm64, X64, Arm64)
- Linux (x64, arm64)
- watchOS (SimulatorArm64, X64, Arm32, Arm64, DeviceArm64)
- tvOS (SimulatorArm64, X64, Arm64)
- Android Native (X64, X86, Arm64, Arm32)
- Windows (mingwX64)
- JavaScript (Browser, Node.js)
- WebAssembly (Browser, Node.js, D8)

For more details, see the [Kotlin Multiplatform documentation](https://kotlinlang.org/docs/multiplatform.html).

For Kotlin/Native support, see
the [Kotlin/Native documentation](https://kotlinlang.org/docs/native-target-support.html).

## Contributing

We welcome contributions! Please submit issues or pull requests for any bugs, features, or improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
