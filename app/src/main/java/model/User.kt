package model
data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val profileImage: Int
) {
    val firstName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
}