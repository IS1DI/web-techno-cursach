package org.is1di.web_techno_cursach.rest

import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.lang.Void
import org.is1di.web_techno_cursach.model.NewsDTO
import org.is1di.web_techno_cursach.service.NewsService
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
    value = ["/api/newss"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class NewsResource(
    private val newsService: NewsService
) {

    @GetMapping
    fun getAllNewss(): ResponseEntity<List<NewsDTO>> = ResponseEntity.ok(newsService.findAll())

    @GetMapping("/{id}")
    fun getNews(@PathVariable(name = "id") id: Long): ResponseEntity<NewsDTO> =
            ResponseEntity.ok(newsService.get(id))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createNews(@RequestBody @Valid newsDTO: NewsDTO): ResponseEntity<Long> {
        val createdId = newsService.create(newsDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateNews(@PathVariable(name = "id") id: Long, @RequestBody @Valid newsDTO: NewsDTO):
            ResponseEntity<Long> {
        newsService.update(id, newsDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteNews(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        newsService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
