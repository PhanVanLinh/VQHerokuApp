package vn.linh.vqherokuapp.data.model

import com.google.gson.annotations.Expose
import java.util.*

data class User(
    @Expose
    val name: String,
    @Expose
    val image: String,
    @Expose
    val items: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (image != other.image) return false
        if (!Arrays.equals(items, other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + Arrays.hashCode(items)
        return result
    }
}