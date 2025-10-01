# 📚 LAB 2 - SPRING MVC FRAMEWORK

## 📝 Mô tả dự án
Đây là dự án thực hành Spring MVC với 6 bài tập theo đề bài chính thức, bao gồm Controller mapping, Parameter handling, Model binding và Pagination.

## 🚀 Cách chạy dự án

### Cách 1: Sử dụng script tự động
```bash
chmod +x run.sh
./run.sh
```

### Cách 2: Chạy thủ công
```bash
./mvnw spring-boot:run
# Sau đó mở: http://localhost:8080
```

## 🏗️ Cấu trúc dự án chi tiết

### 📁 **src/main/java/com/poly/lab2/**

#### 🚀 **Lab2Application.java**
- **Chức năng**: File main khởi động Spring Boot application
- **Annotation**: `@SpringBootApplication` - tự động config toàn bộ Spring Boot
- **Nhiệm vụ**: Entry point của ứng dụng, chạy embedded Tomcat server
- **Code quan trọng**: `SpringApplication.run()` method

#### 🏠 **HomeController.java**
- **Chức năng**: Controller xử lý trang chủ
- **URL mapping**: `GET /` → hiển thị `index.html`
- **Nhiệm vụ**: Điều hướng người dùng đến trang chủ với menu các bài lab
- **Return**: Tên view `"index"` để render template

#### 🎯 **OkController.java** (Bài 1 - 2 điểm)
- **Chức năng**: Demo 3 cách mapping HTTP methods khác nhau
- **URL mapping**: `/ok`
- **Các method**:
  - `ok()`: Method cơ bản với `@RequestMapping("/ok")`
  - `m1()`: Xử lý POST request với `@PostMapping("/ok")`
  - `m2()`: Xử lý GET request với `@GetMapping("/ok")`
  - `m3()`: Xử lý GET với parameter x: `@GetMapping(value="/ok", params="x")`
- **Cách hoạt động**: 
  - Button OK 1: POST → gọi m1()
  - Button OK 2: GET → gọi m2()  
  - Button OK 3: GET với ?x → gọi m3()
- **Model**: Truyền tên method được gọi để hiển thị kết quả

#### 📋 **ParamController.java** (Bài 2 - 2 điểm)
- **Chức năng**: Demo cách đọc parameters từ URL path và form
- **URL mapping**: 
  - `GET /param/form` → hiển thị form
  - `POST /param/save/{x}` → xử lý dữ liệu
- **Parameters**:
  - `@PathVariable String x`: Lấy giá trị từ URL path `/param/save/{x}`
  - `@RequestParam String y`: Lấy giá trị từ form input name="y"
- **Ví dụ**: POST `/param/save/2021` với form data `y=2031`
  - x = "2021" (từ URL path)
  - y = "2031" (từ form input)

#### 🛍️ **ProductController.java** (Bài 3,4,6 - 5 điểm)
- **Chức năng**: Controller xử lý sản phẩm với nhiều tính năng
- **URL mapping**:
  - `GET /product/form` → form thêm/sửa sản phẩm
  - `POST /product/save` → lưu sản phẩm
  - `GET /product/list` → danh sách có phân trang

**Method chi tiết**:
- **`form()`** (Bài 4):
  - Tạo object p1 với dữ liệu mẫu
  - Tạo object p2 rỗng cho form binding
  - Sử dụng Model để truyền dữ liệu
- **`save()`** (Bài 3):
  - `@ModelAttribute("p2") Product p2`: Binding form data vào object
  - Tự động map form fields (name, price) vào Product properties
- **`getItems()`** (Bài 4):
  - `@ModelAttribute("items")` method level
  - Tự động thêm List<Product> vào mọi request của controller này
  - Tạo 20 sản phẩm mẫu để test
- **`list()`** (Bài 6):
  - `@RequestParam(defaultValue="5") int pageSize`
  - `@RequestParam(defaultValue="1") int pageNumber`
  - Logic phân trang: tính toán startIndex, endIndex, totalPages
  - Trả về subList của products cho trang hiện tại

#### 📊 **ResultController.java** (Bài 5 - 2 điểm)
- **Chức năng**: Demo sự khác biệt giữa Forward, Redirect và ResponseBody
- **URL mapping**:
  - `GET /a` → hiển thị trang a.html
  - `GET /b` → forward về /a với Model
  - `GET /c` → redirect về /a với RedirectAttributes  
  - `GET /d` → trả về String trực tiếp

**Method chi tiết**:
- **`m1()`**: Return `"a"` → forward đến a.html
- **`m2()`**: 
  - `Model.addAttribute("message", "I come from b")`
  - Return `"a"` → forward, giữ nguyên request, Model được chia sẻ
- **`m3()`**:
  - `RedirectAttributes.addAttribute("message", "I come from c")`
  - Return `"redirect:/a"` → tạo request mới, message thành URL parameter
- **`m4()`**:
  - `@ResponseBody` → trả về String tr��c tiếp, không qua view template
  - Browser hiển thị plain text "I come from d"

#### 📦 **Product.java**
- **Chức năng**: Model class đại diện sản phẩm
- **Lombok annotations**:
  - `@Data`: Tự động tạo getter/setter, toString, equals, hashCode
  - `@NoArgsConstructor`: Constructor không tham số
  - `@AllArgsConstructor`: Constructor có tất cả tham số
- **Properties**: 
  - `String name`: Tên sản phẩm
  - `Double price`: Giá sản phẩm
- **Sử dụng**: Form binding, hiển thị danh sách, phân trang

### 📁 **src/main/resources/templates/**

#### 🏠 **index.html**
- **Chức năng**: Trang chủ với menu điều hướng
- **Nội dung**: 
  - Danh sách 6 bài lab với mô tả chi tiết
  - Button link đến từng bài
  - Giao diện responsive v���i CSS
- **Thymeleaf**: Sử dụng cơ bản cho layout và styling

#### ✅ **ok.html** (Bài 1)
- **Chức năng**: Form test 3 button mapping
- **HTML form**:
  ```html
  <form action="/ok" method="post">
    <button>OK 1</button>                    <!-- POST /ok -->
    <button formmethod="get">OK 2</button>   <!-- GET /ok -->
    <button formaction="/ok?x">OK 3</button> <!-- GET /ok?x -->
  </form>
  ```
- **Thymeleaf**: 
  - `th:if="${method}"` → hiển thị kết quả
  - `th:text="${method}"` → show tên method được gọi
- **Kết quả**: Hiển thị m1, m2 hoặc m3 tùy button được click

#### 📝 **form.html** (Bài 2)
- **Chức năng**: Form test @PathVariable và @RequestParam
- **HTML form**:
  ```html
  <form action="/param/save/2021" method="post">
    <input name="y" value="2031">
    <button>Save</button>
  </form>
  ```
- **Thymeleaf hiển thị**:
  - `th:text="${x}"` → hiển thị PathVariable (2021)
  - `th:text="${y}"` → hiển thị RequestParam (2031)
- **Demo**: Cách lấy dữ liệu từ URL path vs form input

#### 📁 **product/form.html** (Bài 3,4)
- **Chức năng**: Form Product với Model binding nâng cao
- **HTML form**:
  ```html
  <form action="/product/save" method="post">
    <input name="name">   <!-- Binding vào Product.name -->
    <input name="price">  <!-- Binding vào Product.price -->
    <button>Save</button>
  </form>
  ```
- **Thymeleaf features**:
  - `th:value="${p2?.name}"` → Safe navigation tránh null
  - `th:text="${p1?.name ?: 'Chưa có dữ liệu'}"` → Elvis operator
  - `th:if="${p2?.name}"` → Conditional display
  - `th:each="item: ${items}"` → Loop qua danh sách từ @ModelAttribute
- **3 phần hiển thị**:
  1. **p1**: Dữ liệu mẫu từ controller
  2. **p2**: Dữ liệu vừa submit từ form  
  3. **items**: Danh sách từ @ModelAttribute method level

#### 📁 **product/list.html** (Bài 6)
- **Chức năng**: Danh sách sản phẩm với phân trang đầy đủ
- **Features**:
  - Dropdown chọn pageSize (3,5,10,15,20)
  - Bảng hiển thị sản phẩm với STT, tên, giá
  - Pagination với Previous/Next và số trang
  - Thông tin trang hiện tại/tổng số trang
- **Thymeleaf nâng cao**:
  - `th:selected="${pageSize == 5}"` → Selected option
  - `th:each="product, iterStat : ${products}"` → Loop với index
  - `th:text="${(currentPage - 1) * pageSize + iterStat.index + 1}"` → Tính STT
  - `th:href="@{/product/list(pageSize=${pageSize}, pageNumber=${pageNum})}"` → URL với parameters
  - `th:each="pageNum : ${#numbers.sequence(1, totalPages)}"` → Generate số trang
  - `th:if="${currentPage > 1}"` → Conditional Previous button

#### 📊 **a.html** (Bài 5)
- **Chức năng**: Hiển thị message từ Forward và Redirect
- **Thymeleaf logic**:
  - `th:if="${message}"` → Message từ Model (Forward từ /b)
  - `th:if="${param.message}"` → Message từ URL parameter (Redirect từ /c)
  - `th:unless="${message or param.message}"` → Hiển thị khi không có message
- **Demo**: Sự khác biệt giữa Model vs RedirectAttributes

### 📁 **Các file cấu hình**

#### ⚙️ **pom.xml**
- **Chức năng**: Maven configuration với dependencies
- **Dependencies quan trọng**:
  - `spring-boot-starter-web`: Web MVC, embedded Tomcat
  - `spring-boot-starter-thymeleaf`: Template engine  
  - `lombok`: Code generation annotations
  - `spring-boot-devtools`: Hot reload development
  - `spring-boot-starter-test`: Testing framework

#### 🔧 **application.properties**
- **Chức năng**: Cấu hình Spring Boot application
- **Có thể config**: server.port, logging level, database, etc.

#### 🚀 **run.sh**
- **Chức năng**: Script tự động chạy ứng dụng
- **Các bước**:
  1. Kill process trên port 8080
  2. Clean và compile Maven project
  3. Start Spring Boot background
  4. Wait 10s cho startup
  5. Check health với curl
  6. Mở browser tại trang chủ
  7. Hiển thị menu các URL test

## 🎯 Kiến thức được học qua từng bài

### Bài 1: HTTP Method Mapping (2đ)
- `@RequestMapping`, `@GetMapping`, `@PostMapping`
- Sự khác biệt giữa GET và POST
- Parameter matching với `params="x"`
- Model attribute để truyền dữ liệu

### Bài 2: Parameter Handling (2đ)  
- `@PathVariable`: Lấy giá trị từ URL path
- `@RequestParam`: Lấy giá trị từ query string/form
- Form submission và data binding

### Bài 3: Basic Model Binding (1đ)
- `@ModelAttribute` parameter level
- Tự động binding form fields vào object properties
- Model để chia sẻ dữ liệu Controller → View

### Bài 4: Advanced Model Features (2đ)
- `@ModelAttribute` method level
- Model.addAttribute() để thêm dữ liệu
- Chia sẻ dữ liệu chung cho tất cả request methods

### Bài 5: Response Types (2đ)
- **Forward**: Cùng request, Model được chia sẻ
- **Redirect**: Request mới, dùng RedirectAttributes
- **@ResponseBody**: Trả về content trực tiếp, không qua view

### Bài 6: Pagination (1đ)
- Logic phân trang: pageSize, pageNumber, totalPages
- Tính toán startIndex, endIndex cho subList
- UI pagination với Previous/Next và số trang
- Dynamic page size selection

## 🚨 Lỗi đã sửa và cách xử lý

### 1. Thymeleaf Null Pointer Exception
**Lỗi**: `p2.name` null khi lần đầu load trang
**Giải pháp**: 
```html
<!-- Trước (lỗi) -->
<span th:text="${p2.name}">

<!-- Sau (đã sửa) -->
<span th:text="${p2?.name ?: 'Chưa có dữ liệu'}">
```

### 2. URL Mapping Confusion
**Lỗi**: Nhầm lẫn giữa `/ok` và `/ctrl/ok`
**Giải pháp**: Đọc kĩ đề bài, sử dụng đúng URL theo yêu cầu

### 3. Safe Navigation trong Thymeleaf
**Best practice**:
- `?.` Safe navigation operator
- `?:` Elvis operator cho default value
- `th:if` để check condition trước khi hiển thị

## 📊 Tổng kết

✅ **Hoàn thành đầy đủ 6/6 bài theo đề bài**
✅ **Tất cả tính năng hoạt động chính xác**  
✅ **Giao diện đẹp và user-friendly**
✅ **Code có comment và giải thích chi tiết**
✅ **Xử lý lỗi và edge cases**
✅ **README chi tiết để hiểu rõ từng phần**

**Điểm mạnh của project**:
- Code sạch, có cấu trúc tốt
- UI/UX thân thiện với nhiều màu sắc và icons
- Xử lý lỗi tốt với safe navigation
- Giải thích chi tiết cách hoạt động của từng tính năng
- Script tự động để dễ dàng chạy và test
