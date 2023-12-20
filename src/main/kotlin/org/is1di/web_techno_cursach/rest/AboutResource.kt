package org.is1di.web_techno_cursach.rest

import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.lang.Void
import org.is1di.web_techno_cursach.model.AboutDTO
import org.is1di.web_techno_cursach.service.AboutService
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
    value = ["/api/abouts"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AboutResource(
    private val aboutService: AboutService
) {

    @GetMapping
    fun getAllAbouts(): ResponseEntity<List<AboutDTO>> = ResponseEntity.ok(aboutService.findAll())

    @GetMapping("/{id}")
    fun getAbout(@PathVariable(name = "id") id: Long): ResponseEntity<AboutDTO> =
            ResponseEntity.ok(aboutService.get(id))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createAbout(@RequestBody @Valid aboutDTO: AboutDTO): ResponseEntity<Long> {
        val createdId = aboutService.create(aboutDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateAbout(@PathVariable(name = "id") id: Long, @RequestBody @Valid aboutDTO: AboutDTO):
            ResponseEntity<Long> {
        aboutService.update(id, aboutDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteAbout(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        aboutService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
