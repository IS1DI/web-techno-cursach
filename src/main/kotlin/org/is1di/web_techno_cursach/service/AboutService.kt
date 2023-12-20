package org.is1di.web_techno_cursach.service

import org.is1di.web_techno_cursach.domain.About
import org.is1di.web_techno_cursach.model.AboutDTO
import org.is1di.web_techno_cursach.repos.AboutRepository
import org.is1di.web_techno_cursach.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class AboutService(
    private val aboutRepository: AboutRepository
) {

    fun findAll(): List<AboutDTO> {
        val abouts = aboutRepository.findAll(Sort.by("id"))
        return abouts.stream()
                .map { about -> mapToDTO(about, AboutDTO()) }
                .toList()
    }

    fun `get`(id: Long): AboutDTO = aboutRepository.findById(id)
            .map { about -> mapToDTO(about, AboutDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(aboutDTO: AboutDTO): Long {
        val about = About()
        mapToEntity(aboutDTO, about)
        return aboutRepository.save(about).id!!
    }

    fun update(id: Long, aboutDTO: AboutDTO) {
        val about = aboutRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(aboutDTO, about)
        aboutRepository.save(about)
    }

    fun delete(id: Long) {
        aboutRepository.deleteById(id)
    }

    private fun mapToDTO(about: About, aboutDTO: AboutDTO): AboutDTO {
        aboutDTO.id = about.id
        aboutDTO.description = about.description
        aboutDTO.title = about.title
        return aboutDTO
    }

    private fun mapToEntity(aboutDTO: AboutDTO, about: About): About {
        about.description = aboutDTO.description
        about.title = aboutDTO.title
        return about
    }

}
