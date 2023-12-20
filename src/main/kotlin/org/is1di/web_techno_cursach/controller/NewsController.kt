package org.is1di.web_techno_cursach.controller

import jakarta.validation.Valid
import org.is1di.web_techno_cursach.model.NewsDTO
import org.is1di.web_techno_cursach.service.NewsService
import org.is1di.web_techno_cursach.util.WebUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
@RequestMapping("/news")
class NewsController(
    private val newsService: NewsService
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("newses", newsService.findAll())
        return "news/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("news") newsDTO: NewsDTO): String = "news/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("news") @Valid newsDTO: NewsDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!bindingResult.hasFieldErrors("title") && newsService.titleExists(newsDTO.title)) {
            bindingResult.rejectValue("title", "Exists.news.title")
        }
        if (bindingResult.hasErrors()) {
            return "news/add"
        }
        newsService.create(newsDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("news.create.success"))
        return "redirect:/news"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Long, model: Model): String {
        model.addAttribute("news", newsService.get(id))
        return "news/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Long,
        @ModelAttribute("news") @Valid newsDTO: NewsDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        val currentNewsDTO: NewsDTO = newsService.get(id)
        if (!bindingResult.hasFieldErrors("title") &&
                !newsDTO.title!!.equals(currentNewsDTO.title, ignoreCase = true) &&
                newsService.titleExists(newsDTO.title)) {
            bindingResult.rejectValue("title", "Exists.news.title")
        }
        if (bindingResult.hasErrors()) {
            return "news/edit"
        }
        newsService.update(id, newsDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("news.update.success"))
        return "redirect:/news"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long, redirectAttributes: RedirectAttributes):
            String {
        newsService.delete(id)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO,
                WebUtils.getMessage("news.delete.success"))
        return "redirect:/news"
    }

}
