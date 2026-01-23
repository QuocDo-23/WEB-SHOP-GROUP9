<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Sản Phẩm - ${product.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin_products.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/products_setting.css">


    <style>
        /*.detail-container {*/
        /*    max-width: 1000px;*/
        /*    margin: 20px auto;*/
        /*    background: white;*/
        /*    padding: 30px;*/
        /*    border-radius: 12px;*/
        /*    box-shadow: 0 2px 8px rgba(0,0,0,0.1);*/
        /*}*/
        /*.detail-header {*/
        /*    display: flex;*/
        /*    justify-content: space-between;*/
        /*    align-items: center;*/
        /*    margin-bottom: 30px;*/
        /*    padding-bottom: 20px;*/
        /*    border-bottom: 2px solid #e2e8f0;*/
        /*}*/
        /*.product-image-large {*/
        /*    width: 100%;*/
        /*    max-width: 500px;*/
        /*    height: auto;*/
        /*    border-radius: 8px;*/
        /*    margin: 20px auto;*/
        /*    display: block;*/
        /*}*/
        /*.detail-section {*/
        /*    margin-bottom: 30px;*/
        /*}*/
        /*.detail-section h4 {*/
        /*    font-size: 18px;*/
        /*    margin-bottom: 15px;*/
        /*    color: #2d3748;*/
        /*    padding-bottom: 10px;*/
        /*    border-bottom: 2px solid #e2e8f0;*/
        /*}*/
        /*.detail-grid {*/
        /*    display: grid;*/
        /*    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));*/
        /*    gap: 15px;*/
        /*    margin-top: 15px;*/
        /*}*/
        /*.detail-item {*/
        /*    display: flex;*/
        /*    flex-direction: column;*/
        /*    gap: 5px;*/
        /*}*/
        /*.detail-label {*/
        /*    font-weight: 600;*/
        /*    color: #4a5568;*/
        /*    font-size: 14px;*/
        /*}*/
        /*.detail-value {*/
        /*    color: #2d3748;*/
        /*    font-size: 15px;*/
        /*}*/
        /*.btn-group {*/
        /*    display: flex;*/
        /*    gap: 10px;*/
        /*}*/
    </style>
</head>
<body>
<div class="container">
    <jsp:include page="siderbar.jsp"/>

    <div class="main-content">

        <div class="form-container">
            <div class="form-header">
                <h1>👁️ Chi Tiết Sản Phẩm</h1>
                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/admin/products/edit?id=${product.id}"
                       class="btn btn-primary">✏️ Chỉnh sửa</a>
                    <a href="${pageContext.request.contextPath}/admin/products"
                       class="btn btn-secondary">← Quay lại</a>
                </div>
            </div>

            <img src="${product.mainImage}" alt="${product.name}" class="product-image-large"/>
            <div class="form-section">
                <div class="form-section-title">📋 Thông Tin Cơ Bản</div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Tên Sản Phẩm: </label>
                        <input type="text" name="productName" required
                               value="${product.name}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Danh Mục: </label>
                        <select name="categoryId" disabled>
                            <option value="">${product.categoryName}</option>
                        </select>
                    </div>
                </div>

                <div class="form-row triple">
                    <div class="form-group">
                        <label>Giá (VNĐ)</label>
                        <input type="number" name="price"
                               value="${product.price}" readonly>
                    </div>

                    <div class="form-group">
                        <label>Số Lượng Tồn Kho:</label>
                        <input type="number" name="stock"
                               value="${product.inventoryQuantity != null ? product.inventoryQuantity : 0}"
                               readonly>
                    </div>

                    <div class="form-group">
                        <label>Đánh Giá Trung Bình</label>
                        <input type="number" name="review"
                               value="${product.review}"
                               readonly>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Giảm Giá</label>
                        <select name="discountId"  disabled>
                            <option value="">${product.discountRate}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Trạng Thái </label>
                        <select name="status" disabled>
                            <option value="" >${product.status} </option>
                        </select>
                    </div>
                </div>
            </div>

            <%--        PHẦN 2: Chi Tiết Sản Phẩm--%>
            <div class="form-section">
                <div class="form-section-title">🔧 Chi Tiết Sản Phẩm</div>

                <div class="form-row full">
                    <div class="form-group">
                        <label>Mô Tả Sản Phẩm</label>
                        <textarea name="description" readonly>${product.description}</textarea>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Chất Liệu</label>
                        <input type="text" name="material" value="${product.material}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Điện Áp</label>
                        <input type="text" name="voltage" value="${product.voltage}" readonly>
                    </div>
                </div>

                <div class="form-row triple">
                    <div class="form-group">
                        <label>Kích Thước</label>
                        <input type="text" name="dimension" value="${product.dimensions}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Loại Đèn</label>
                        <input type="text" name="type" value="${product.type}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Bảo Hành (Tháng)</label>
                        <input type="text" name="warranty" value="${product.warranty}" readonly>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Màu Sắc / Nhiệt Độ Màu</label>
                        <input type="text" name="color" value="${product.color}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Phong Cách Thiết Kế</label>
                        <input type="text" name="style" value="${product.style}" readonly>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/admin/products"
                   class="btn btn-secondary">Đóng</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>