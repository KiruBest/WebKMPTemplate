package ru.tsutsurin.webkmp

expect val platform: String

class Greeting {
    fun greeting() = "Hello, $platform!"
}
