package com.oxyhotel.common.serialization

import java.util.Date
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializes java.util.Date as epoch milliseconds to keep JSON payloads simple.
 */
object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateAsLong", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }
}
