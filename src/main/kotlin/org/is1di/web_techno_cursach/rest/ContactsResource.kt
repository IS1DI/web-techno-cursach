package org.is1di.web_techno_cursach.rest

import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.lang.Void
import org.is1di.web_techno_cursach.model.ContactsDTO
import org.is1di.web_techno_cursach.service.ContactsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    value = ["/api/contactss"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class ContactsResource(
    private val contactsService: ContactsService
) {

    @GetMapping
    fun getAllContactss(): ResponseEntity<List<ContactsDTO>> =
            ResponseEntity.ok(contactsService.findAll())

    @GetMapping("/{id}")
    fun getContacts(@PathVariable(name = "id") id: Long): ResponseEntity<ContactsDTO> =
            ResponseEntity.ok(contactsService.get(id))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createContacts(@RequestBody @Valid contactsDTO: ContactsDTO): ResponseEntity<Long> {
        val createdId = contactsService.create(contactsDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateContacts(@PathVariable(name = "id") id: Long, @RequestBody @Valid
            contactsDTO: ContactsDTO): ResponseEntity<Long> {
        contactsService.update(id, contactsDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteContacts(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        contactsService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
