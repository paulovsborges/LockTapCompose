package domain.utils

import com.pvsb.domain.entity.Contact

object DummyContactData {
    val dummyContacts = List(5) {
        Contact(
            "$it",
            "john doe $it",
            "123",
            null,
            false
        )
    }
}
