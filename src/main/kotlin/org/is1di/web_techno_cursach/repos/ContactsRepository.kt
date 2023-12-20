package org.is1di.web_techno_cursach.repos

import org.is1di.web_techno_cursach.domain.Contacts
import org.springframework.data.jpa.repository.JpaRepository


interface ContactsRepository : JpaRepository<Contacts, Long> {

    fun existsByFullNameIgnoreCase(fullName: String?): Boolean

}
