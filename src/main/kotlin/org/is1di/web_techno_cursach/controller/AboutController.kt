package org.is1di.web_techno_cursach.controller

import jakarta.validation.Valid
import org.is1di.web_techno_cursach.model.AboutDTO
import org.is1di.web_techno_cursach.service.AboutService
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
@RequestMapping("/abouts")
class AboutController(
    private val aboutService: AboutService
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("abouts", aboutService.findAll())
        return "about/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("about") aboutDTO: AboutDTO): String = "about/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("about") @Valid aboutDTO: AboutDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "about/add"
        }
        aboutService.create(aboutDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("about.create.success"))
        return "redirect:/abouts"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Long, model: Model): String {
        model.addAttribute("about", aboutService.get(id))
        return "about/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Long,
        @ModelAttribute("about") @Valid aboutDTO: AboutDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "about/edit"
        }
        aboutService.update(id, aboutDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("about.update.success"))
        return "redirect:/abouts"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long, redirectAttributes: RedirectAttributes):
            String {
        aboutService.delete(id)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO,
                WebUtils.getMessage("about.delete.success"))
        return "redirect:/abouts"
    }

}
