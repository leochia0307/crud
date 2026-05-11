package com.leoh.crud.controller;

import com.leoh.crud.model.Customer;
import com.leoh.crud.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// https://www.youtube.com/watch?v=C12_XykFevQ
@Controller
public class HomeController {


    private final CustomerService customerService;

    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 新增
    // 連結到新增表單
    @GetMapping("/create")
    public String create(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "create";
    }

    // 表單資料填寫完成後，儲存資料，回到首頁
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "/create";
        }

        customerService.save(customer);
        redirectAttributes.addFlashAttribute(
                "message",
                "新增資料成功"
        );
        return "redirect:/";
    }

    // 修改
    @GetMapping("customer/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id).orElse(null);
        model.addAttribute("customer", customer);
        return "create";
    }

    // 刪除
    @GetMapping("customer/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        customerService.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "message",
                "客戶資料刪除成功"
        );
        return "redirect:/";
    }

    // 首頁
    @GetMapping("/")
    public String home(@RequestParam(value = "name", defaultValue = "") String name,
                       Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        model.addAttribute("allCustomers", allCustomers);
        return "home";
    }

    // 查詢
    @GetMapping("customer/{id}")
    public String show(@PathVariable long id, Model model) {
        customerService.findById(id).ifPresent(customer -> model.addAttribute("customer", customer));
        return "show";
    }


}
