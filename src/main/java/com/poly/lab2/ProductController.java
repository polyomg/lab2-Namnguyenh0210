package com.poly.lab2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    // Bài 3: Form đơn giản với Product binding
    @GetMapping("/product/form")
    public String form(Model model) {
        Product p1 = new Product();
        p1.setName("iPhone 30");
        p1.setPrice(5000.0);

        // Tạo p2 rỗng để tránh lỗi null
        Product p2 = new Product();

        model.addAttribute("p1", p1);
        model.addAttribute("p2", p2);
        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(@ModelAttribute("p2") Product p2, Model model) {
        // Giữ lại p1 để hiển thị
        Product p1 = new Product();
        p1.setName("iPhone 30");
        p1.setPrice(5000.0);
        model.addAttribute("p1", p1);

        return "product/form";
    }

    // @ModelAttribute method level - tự động thêm vào mọi request
    @ModelAttribute("items")
    public List<Product> getItems() {
        List<Product> products = new ArrayList<>();
        // Tạo 20 sản phẩm cho bài 6
        for(int i = 1; i <= 20; i++) {
            products.add(new Product("Sản phẩm " + i, i * 100.0));
        }
        return products;
    }

    // Bài 6: Phân trang sản phẩm
    @GetMapping("/product/list")
    public String list(@RequestParam(defaultValue = "5") int pageSize,
                      @RequestParam(defaultValue = "1") int pageNumber,
                      Model model) {
        List<Product> allProducts = getItems();

        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalProducts);

        List<Product> pageProducts = allProducts.subList(startIndex, endIndex);

        model.addAttribute("products", pageProducts);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        return "product/list";
    }
}
