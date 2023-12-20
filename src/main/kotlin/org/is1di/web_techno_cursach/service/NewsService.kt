package org.is1di.web_techno_cursach.service

import org.is1di.web_techno_cursach.domain.News
import org.is1di.web_techno_cursach.model.NewsDTO
import org.is1di.web_techno_cursach.repos.NewsRepository
import org.is1di.web_techno_cursach.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class NewsService(
    private val newsRepository: NewsRepository
) {

    fun findAll(): List<NewsDTO> {
        val news = newsRepository.findAll(Sort.by("id"))
        return news.stream()
                .map { news -> mapToDTO(news, NewsDTO()) }
                .toList()
    }

    fun `get`(id: Long): NewsDTO = newsRepository.findById(id)
            .map { news -> mapToDTO(news, NewsDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(newsDTO: NewsDTO): Long {
        val news = News()
        mapToEntity(newsDTO, news)
        return newsRepository.save(news).id!!
    }

    fun update(id: Long, newsDTO: NewsDTO) {
        val news = newsRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(newsDTO, news)
        newsRepository.save(news)
    }

    fun delete(id: Long) {
        newsRepository.deleteById(id)
    }

    private fun mapToDTO(news: News, newsDTO: NewsDTO): NewsDTO {
        newsDTO.id = news.id
        newsDTO.title = news.title
        newsDTO.content = news.content
        return newsDTO
    }

    private fun mapToEntity(newsDTO: NewsDTO, news: News): News {
        news.title = newsDTO.title
        news.content = newsDTO.content
        return news
    }

    fun titleExists(title: String?): Boolean = newsRepository.existsByTitleIgnoreCase(title)

}
