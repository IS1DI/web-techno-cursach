package org.is1di.web_techno_cursach.controller

import jakarta.validation.Valid
import org.is1di.web_techno_cursach.model.ContactsDTO
import org.is1di.web_techno_cursach.service.ContactsService
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
@RequestMapping("/contacts")
class ContactsController(
    private val contactsService: ContactsService
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("contactses", contactsService.findAll())
        return "contacts/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("contacts") contactsDTO: ContactsDTO): String = "contacts/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("contacts") @Valid contactsDTO: ContactsDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!bindingResult.hasFieldErrors("fullName") &&
                contactsService.fullNameExists(contactsDTO.fullName)) {
            bindingResult.rejectValue("fullName", "Exists.contacts.fullName")
        }
        if (bindingResult.hasErrors()) {
            return "contacts/add"
        }
        contactsService.create(contactsDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("contacts.create.success"))
        return "redirect:/contacts"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Long, model: Model): String {
        model.addAttribute("contacts", contactsService.get(id))
        return "contacts/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Long,
        @ModelAttribute("contacts") @Valid contactsDTO: ContactsDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        val currentContactsDTO: ContactsDTO = contactsService.get(id)
        if (!bindingResult.hasFieldErrors("fullName") &&
                !contactsDTO.fullName!!.equals(currentContactsDTO.fullName, ignoreCase = true) &&
                contactsService.fullNameExists(contactsDTO.fullName)) {
            bindingResult.rejectValue("fullName", "Exists.contacts.fullName")
        }
        if (bindingResult.hasErrors()) {
            return "contacts/edit"
        }
        contactsService.update(id, contactsDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("contacts.update.success"))
        return "redirect:/contacts"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long, redirectAttributes: RedirectAttributes):
            String {
        contactsService.delete(id)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO,
                WebUtils.getMessage("contacts.delete.success"))
        return "redirect:/contacts"
    }

}
