package com.example.giaybongda.controller;

import com.example.giaybongda.model.Bill;
import com.example.giaybongda.model.Customer;
import com.example.giaybongda.model.Product;
import com.example.giaybongda.service.AdminService;
import com.example.giaybongda.service.CustomerService;
import com.example.giaybongda.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminDashboard(HttpSession session, Model model,
                                 @RequestParam(name= "bPage", defaultValue = "0") int bPage,
                                 @RequestParam(name= "cPage", defaultValue = "0") int cPage,
                                 @RequestParam(name= "pPage", defaultValue = "0") int pPage,
                                 @RequestParam(name = "tab", required = false, defaultValue = "orders") String activeTab,
                                 @RequestParam(name = "filterType", required = false, defaultValue = "all") String filterType,
                                 @RequestParam(name = "dateInput", required = false) String dateInput, // Dùng cho theo ngày
                                 @RequestParam(name = "monthInput", required = false) Integer monthInput, // Dùng cho tháng
                                 @RequestParam(name = "quarterInput", required = false) Integer quarterInput, // Dùng cho quý
                                 @RequestParam(name = "yearInput", required = false) Integer yearInput, // Dùng cho năm
                                 Pageable pageable) {

        String role = (String) session.getAttribute("role");

        // chưa đăng nhập hoặc không phải admin cho về trang chủ
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/";
        }
        int pageSize = 10;

        // lấy danh sách khách hàng
        Page<Customer> customerPage = adminService.findAllCustomers(cPage,pageSize);
        // lấy danh sách hóa đơn
        Page<Bill> billPage = adminService.getAllBills(bPage,pageSize);
        // lấy danh sách sản phẩm
        Page<Product> productPage = adminService.findAllProducts(pPage,pageSize);

        Long totalRevenue;
        Long totalBills;
        Long totalProducts = adminService.getTotalProducts();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        boolean isFiltered = false;

        int currentYear = (yearInput != null) ? yearInput : LocalDate.now().getYear();

        try {
            switch (filterType) {
                case "date":
                    if (dateInput != null && !dateInput.isEmpty()) {
                        startDate = LocalDate.parse(dateInput);
                        endDate = startDate;
                        isFiltered = true;
                    }
                    break;
                case "month":
                    if (monthInput != null) {
                        startDate = LocalDate.of(currentYear, monthInput, 1);
                        endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
                        isFiltered = true;
                    }
                    break;
                case "quarter":
                    if (quarterInput != null) {
                        int startMonth = (quarterInput - 1) * 3 + 1;
                        startDate = LocalDate.of(currentYear, startMonth, 1);
                        endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
                        isFiltered = true;
                    }
                    break;
                case "year":
                    startDate = LocalDate.of(currentYear, 1, 1);
                    endDate = LocalDate.of(currentYear,     12, 31);
                    isFiltered = true;
                    break;
                default:
                    isFiltered = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isFiltered = false;
        }

        if (isFiltered) {
            totalRevenue = adminService.getTotalRevenuesByDateRange(startDate, endDate);
            totalBills = adminService.getBillsCountByDateRange(startDate, endDate);
        } else {
            totalRevenue = adminService.getTotalRevenue();
            totalBills = adminService.getTotalBills();
        }



        model.addAttribute("customerPage", customerPage);
        model.addAttribute("billPage", billPage);
        model.addAttribute("productPage", productPage);

        model.addAttribute("currentbPage", bPage);
        model.addAttribute("currentcPage", cPage);
        model.addAttribute("currentpPage", pPage);

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalBills", totalBills);
        model.addAttribute("totalProducts", totalProducts);

        model.addAttribute("activeTab", activeTab);

        model.addAttribute("selectedFilter", filterType);
        model.addAttribute("selectedDate", dateInput);
        model.addAttribute("selectedMonth", monthInput);
        model.addAttribute("selectedQuarter", quarterInput);
        model.addAttribute("selectedYear", yearInput != null ? yearInput : LocalDate.now().getYear());

        return "Admin/manage";
        }
    @PostMapping("/toggle-status")
    @ResponseBody
    public Map<String, Object> toggleStatus(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        Product product = productService.getById(id);
        if (product != null) {
            product.setTinhtrang(product.getTinhtrang() == 1 ? 0 : 1);
            productService.save(product);
            response.put("success", true);
            response.put("newStatus", product.getTinhtrang());
        } else {
            response.put("success", false);
        }
        return response;
    }

    @GetMapping("/export")
    public void exportProducts(HttpServletResponse response) throws IOException {
        List<Product> products = productService.getAll();
        // thiết lập thông tin trả về cho trình duyệt
        // Content-Type: báo cho browser biết đây là file Excel .xlsx
        // Content-Disposition: bắt buộc trình duyệt tải file về thay vì hiển thị trên web
        // filename=products.xlsx: tên file tải xuống
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerValue = "attachment; filename=products.xlsx";
        response.setHeader("Content-Disposition", headerValue);
        // tạo 1 file excel
        try (Workbook workbook = new XSSFWorkbook()) {
            CreationHelper createHelper = workbook.getCreationHelper();
            // tạo sheet tên là products
            Sheet sheet = workbook.createSheet("Products");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"mahh", "tenhh", "giamgia", "hinh", "maloai", "dacbiet", "soluotxem", "ngaylap", "mota", "tinhtrang"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Date cell style
            CellStyle dateCellStyle = workbook.createCellStyle();
            short dateFormat = createHelper.createDataFormat().getFormat("yyyy-mm-dd");
            dateCellStyle.setDataFormat(dateFormat);

            int rowIdx = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(product.getMahh());
                row.createCell(1).setCellValue(product.getTenhh() != null ? product.getTenhh() : "");
                row.createCell(2).setCellValue(product.getGiamgia());
                row.createCell(3).setCellValue(product.getHinh() != null ? product.getHinh() : "");
                row.createCell(4).setCellValue(product.getMaloai());
                row.createCell(5).setCellValue(product.getDacbiet());
                row.createCell(6).setCellValue(product.getSoluotxem());
                Cell dateCell = row.createCell(7);
                if (product.getNgaylap() != null) {
                    dateCell.setCellValue(product.getNgaylap());
                    dateCell.setCellStyle(dateCellStyle);
                }
                row.createCell(8).setCellValue(product.getMota() != null ? product.getMota() : "");
                row.createCell(9).setCellValue(product.getTinhtrang());
            }

            // Autosize columns: tự động co giãn độ rộng của cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // ghi dữ liệu ra response output stream
            workbook.write(response.getOutputStream());
            // trình duyệt tự tải xuống file excel
            response.getOutputStream().flush();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

}
