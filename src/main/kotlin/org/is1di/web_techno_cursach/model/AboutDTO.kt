package org.is1di.web_techno_cursach.model

import jakarta.validation.constraints.Size


class AboutDTO {

    var id: Long? = null

    var description: String? = null

    @Size(max = 255)
    var title: String? = null

}
