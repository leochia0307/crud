package com.leoh.crud.controller;

import com.leoh.crud.model.Customer;
import com.leoh.crud.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

// https://www.youtube.com/watch?v=C12_XykFevQ
@Controller
public class HomeController {


    private final CustomerService customerService;

    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 新增
    // 1. 新增：顯示新增表單（剛才漏掉的這個）
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "create";
    }
    // 連結到新增表單
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {

        // 1. 基本欄位驗證（如名稱未填、格式不對等）
        if (bindingResult.hasErrors()) {
            return "create"; // 注意：這裡原本是 "/create"，有些環境會找不到，改成 "create" 更安全
        }

        // 2. 檢查 Email 是否重複
        Optional<Customer> existingCustomer = customerService.findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {
            // 如果是「新增」(id 為 null)，或是「修改」時該 Email 被「別的客戶」使用了
            if (customer.getId() == null || !existingCustomer.get().getId().equals(customer.getId())) {

                // 手動向 BindingResult 注入錯誤訊息，這樣 Thymeleaf 就能在 email 欄位下方顯示錯誤
                bindingResult.rejectValue("email", "error.customer", "此電子信箱已被其他客戶使用！");
                return "create";
            }
        }

        // 3. 儲存並轉址
        customerService.save(customer);
        redirectAttributes.addFlashAttribute(
                "message",
                customer.getId() == null ? "新增資料成功" : "修改資料成功"
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
    // 修改首頁：加入分頁參數，預設每頁 5 筆
    @GetMapping("/")
    public String home(@RequestParam(value = "page", defaultValue = "1") int page,
                       Model model) {

        int pageSize = 5; // 限制一次最多顯示 5 筆

        // 取得分頁資料
        Page<Customer> customerPage = customerService.getCustomersPage(page, pageSize);

        // 傳遞到前端模板
        model.addAttribute("allCustomers", customerPage.getContent()); // 當前頁面的客戶清單
        model.addAttribute("currentPage", page);                       // 當前頁碼
        model.addAttribute("totalPages", customerPage.getTotalPages());   // 總頁數
        model.addAttribute("totalItems", customerPage.getTotalElements()); // 總筆數

        return "home";
    }

    // 查詢
    @GetMapping("customer/{id}")
    public String show(@PathVariable long id, Model model) {
        customerService.findById(id).ifPresent(customer -> model.addAttribute("customer", customer));
        return "show";
    }


}
