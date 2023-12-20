package org.is1di.web_techno_cursach.service

import org.is1di.web_techno_cursach.domain.Contacts
import org.is1di.web_techno_cursach.model.ContactsDTO
import org.is1di.web_techno_cursach.repos.ContactsRepository
import org.is1di.web_techno_cursach.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class ContactsService(
    private val contactsRepository: ContactsRepository
) {

    fun findAll(): List<ContactsDTO> {
        val contactses = contactsRepository.findAll(Sort.by("id"))
        return contactses.stream()
                .map { contacts -> mapToDTO(contacts, ContactsDTO()) }
                .toList()
    }

    fun `get`(id: Long): ContactsDTO = contactsRepository.findById(id)
            .map { contacts -> mapToDTO(contacts, ContactsDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(contactsDTO: ContactsDTO): Long {
        val contacts = Contacts()
        mapToEntity(contactsDTO, contacts)
        return contactsRepository.save(contacts).id!!
    }

    fun update(id: Long, contactsDTO: ContactsDTO) {
        val contacts = contactsRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(contactsDTO, contacts)
        contactsRepository.save(contacts)
    }

    fun delete(id: Long) {
        contactsRepository.deleteById(id)
    }

    private fun mapToDTO(contacts: Contacts, contactsDTO: ContactsDTO): ContactsDTO {
        contactsDTO.id = contacts.id
        contactsDTO.email = contacts.email
        contactsDTO.phone = contacts.phone
        contactsDTO.fullName = contacts.fullName
        return contactsDTO
    }

    private fun mapToEntity(contactsDTO: ContactsDTO, contacts: Contacts): Contacts {
        contacts.email = contactsDTO.email
        contacts.phone = contactsDTO.phone
        contacts.fullName = contactsDTO.fullName
        return contacts
    }

    fun fullNameExists(fullName: String?): Boolean =
            contactsRepository.existsByFullNameIgnoreCase(fullName)

}
