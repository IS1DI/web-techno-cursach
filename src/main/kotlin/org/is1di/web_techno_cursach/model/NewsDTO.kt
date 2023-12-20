package org.is1di.web_techno_cursach.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


class NewsDTO {

    var id: Long? = null

    @NotNull
    @Size(max = 255)
    var title: String? = null

    @NotNull
    var content: String? = null

}
