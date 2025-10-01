#!/bin/bash

echo "=== LAB2 Spring Boot Auto Runner ==="

# Kill any process running on port 8080
echo "🔄 Đang dừng các process trên port 8080..."
lsof -ti:8080 | xargs kill -9 2>/dev/null || echo "✅ Không có process nào chạy trên port 8080"

# Clean and compile
echo "🧹 Đang clean và compile project..."
./mvnw clean compile -q

# Start Spring Boot in background
echo "🚀 Đang khởi động Spring Boot..."
./mvnw spring-boot:run &
SPRING_PID=$!

# Wait for Spring Boot to start
echo "⏳ Đang chờ Spring Boot khởi động..."
sleep 10

# Check if Spring Boot started successfully
if curl -s http://localhost:8080 > /dev/null; then
    echo "✅ Spring Boot đã khởi động thành công!"

    # Chỉ mở trang chủ
    echo "🌐 Đang mở trang chủ..."
    open http://localhost:8080

    echo ""
    echo "=== 🎉 Ứng dụng đã sẵn sàng! ==="
    echo ""
    echo "📋 Trang chủ: http://localhost:8080"
    echo "    → Từ đây bạn có thể chọn bài lab muốn test"
    echo ""
    echo "🧪 Hoặc truy cập trực tiếp:"
    echo "  • http://localhost:8080/ok (Bài 1 - OK Controller)"
    echo "  • http://localhost:8080/param/form (Bài 2 - Parameters)"
    echo "  • http://localhost:8080/product/form (Bài 3,4 - Product Form)"
    echo "  • http://localhost:8080/product/list (Bài 6 - Phân trang)"
    echo "  • http://localhost:8080/a (Bài 5 - Result Controller)"
    echo ""
    echo "❌ Nhấn Ctrl+C để dừng ứng dụng"
else
    echo "❌ Lỗi: Spring Boot không thể khởi động!"
    exit 1
fi

# Wait for Spring Boot process
wait $SPRING_PID
