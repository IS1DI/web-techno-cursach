package org.is1di.web_techno_cursach.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


class ContactsDTO {

    var id: Long? = null

    @NotNull
    @Size(max = 255)
    var email: String? = null

    @NotNull
    @Size(max = 255)
    var phone: String? = null

    @NotNull
    @Size(max = 255)
    var fullName: String? = null

}
