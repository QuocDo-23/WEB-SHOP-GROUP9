<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <title>Sản Phẩm - Quản Lý Đèn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin_products.css">
</head>
<body>
<div class="container">
    <jsp:include page="siderbar.jsp"/>

    <div class="main-content">
        <div class="header">
            <h1>Quản Lý Sản Phẩm</h1>
            <div class="user-info">
                <div class="avatar">Q</div>
                <div>
                    <div style="font-weight: 600;">Admin</div>
                    <div style="font-size: 12px; color: #718096;">Quản trị viên</div>
                </div>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-row">
            <div class="stat-card">
                <div class="stat-icon blue">📦</div>
                <div class="stat-info">
                    <h3><c:out value="${totalProducts}" default="0"/></h3>
                    <p>Tổng Sản Phẩm</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon green">✅</div>
                <div class="stat-info">
                    <h3><c:out value="${activeProducts}" default="0"/></h3>
                    <p>Đang Bán</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon yellow">⚠️</div>
                <div class="stat-info">
                    <h3><c:out value="${lowStockProducts}" default="0"/></h3>
                    <p>Sắp Hết Hàng</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon red">❌</div>
                <div class="stat-info">
                    <h3><c:out value="${outOfStockProducts}" default="0"/></h3>
                    <p>Hết Hàng</p>
                </div>
            </div>
        </div>

        <!-- Product Table -->
        <div class="table-container">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" style="margin-bottom: 20px; padding: 15px; background: #d1fae5; color: #065f46; border-radius: 8px;">
                    ✅ ${successMessage}
                </div>
            </c:if>
            <div class="table-header">
                <h2>Danh Sách Sản Phẩm</h2>
                <div class="table-controls">
                    <form method="get" action="${pageContext.request.contextPath}/admin/products"
                          style="display: flex; gap: 10px; flex-wrap: wrap;" autocomplete="off">
                        <div class="search-box">
                            <input type="text" name="search" placeholder="Tìm kiếm sản phẩm..."
                                   value="<c:out value='${searchParam}'/>">
                        </div>
                        <select class="filter-select" name="categoryId">
                            <option value="">Tất cả danh mục</option>
                            <c:if test="${not empty categories}">
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}" ${categoryParam == cat.id ? 'selected' : ''}>
                                            ${cat.name}
                                    </option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select class="filter-select" name="status">
                            <option value="">Tất cả trạng thái</option>
                            <option value="active" <c:if test="${statusParam == 'active'}">selected</c:if>>Đang bán
                            </option>
                            <option value="inactive" <c:if test="${statusParam == 'inactive'}">selected</c:if>>Ngừng
                                bán
                            </option>
                            <option value="out_of_stock" <c:if test="${statusParam == 'out_of_stock'}">selected</c:if>>
                                Hết hàng
                            </option>
                        </select>
                        <button type="submit" class="btn btn-primary">🔍 Lọc</button>
                        <a href="${pageContext.request.contextPath}/admin/products/add" class="btn btn-primary">
                            ➕ Thêm Sản Phẩm
                        </a>

                    </form>
                </div>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Sản Phẩm</th>
                    <th>Danh Mục</th>
                    <th>Giá</th>
                    <th>Tồn Kho</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty products}">
                        <tr>
                            <td colspan="6" style="text-align: center; padding: 40px; color: #718096;">
                                Không tìm thấy sản phẩm nào
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>
                                    <div class="product-cell">
                                        <c:choose>
                                            <c:when test="${not empty product.mainImage}">
                                                <img src="<c:out value='${product.mainImage}'/>"
                                                     alt="<c:out value='${product.name}'/>"
                                                     class="product-img"
                                                     loading="lazy"
                                                     onerror="this.src='https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png'">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png"
                                                     alt="No Image"
                                                     class="product-img">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="product-info">
                                            <h4><c:out value="${product.name}"/></h4>
                                            <p>⭐ <fmt:formatNumber value="${product.review}" pattern="0.0"/> • ID:
                                                #${product.id}
                                            </p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <span class="badge badge-info">
                                        <c:choose>
                                            <c:when test="${not empty product.categoryName}">
                                                <c:out value="${product.categoryName}"/>
                                            </c:when>
                                            <c:otherwise>Chưa phân loại</c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                                <td class="price">
                                    <fmt:formatNumber value="${product.price}" pattern="#,###"/> ₫
                                </td>
                                <td>
                                    <div class="stock">
                                        <c:choose>
                                            <c:when test="${product.inventoryQuantity == 0}">
                                                <span class="stock-dot low"></span>
                                            </c:when>
                                            <c:when test="${product.inventoryQuantity <= 20}">
                                                <span class="stock-dot medium"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="stock-dot high"></span>
                                            </c:otherwise>
                                        </c:choose>
                                            ${product.inventoryQuantity}
                                    </div>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${product.inventoryQuantity == 0}">
                                            <span class="badge badge-danger">Hết hàng</span>
                                        </c:when>
                                        <c:when test="${product.status == 'inactive'}">
                                            <span class="badge badge-warning">Ngừng bán</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-success">Đang bán</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/admin/products/view?id=${product.id}"
                                           class="btn btn-sm btn-view" title="Xem chi tiết">👁️</a>
                                        <a href="${pageContext.request.contextPath}/admin/products/edit?id=${product.id}"
                                           class="btn btn-sm btn-edit" title="Chỉnh sửa">✏️</a>
                                        <a href="javascript:void(0)"
                                           onclick="if(confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) window.location.href='${pageContext.request.contextPath}/admin/products/delete?id=${product.id}'"
                                           class="btn btn-sm btn-delete" title="Xóa">🗑️</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <!-- Pagination -->
            <c:if test="${totalPages >= 1}">
                <div class="pagination">
                    <!-- Previous button -->
                    <c:choose>
                        <c:when test="${currentPages > 1}">
                            <c:url var="prevUrl" value="/admin/products">
                                <c:param name="page" value="${currentPages - 1}"/>
                                <c:if test="${not empty searchParam}">
                                    <c:param name="search" value="${searchParam}"/>
                                </c:if>
                                <c:if test="${not empty categoryParam}">
                                    <c:param name="categoryId" value="${categoryParam}"/>
                                </c:if>
                                <c:if test="${not empty statusParam}">
                                    <c:param name="status" value="${statusParam}"/>
                                </c:if>
                            </c:url>
                            <a href="${prevUrl}" class="prev-btn">← Trước</a>
                        </c:when>
                        <c:otherwise>
                            <span class="prev-btn disabled">← Trước</span>
                        </c:otherwise>
                    </c:choose>

                    <!-- Page numbers -->
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:url var="pageUrl" value="/admin/products">
                            <c:param name="page" value="${i}"/>
                            <c:if test="${not empty searchParam}">
                                <c:param name="search" value="${searchParam}"/>
                            </c:if>
                            <c:if test="${not empty categoryParam}">
                                <c:param name="categoryId" value="${categoryParam}"/>
                            </c:if>
                            <c:if test="${not empty statusParam}">
                                <c:param name="status" value="${statusParam}"/>
                            </c:if>
                        </c:url>

                        <a href="${pageUrl}" class="page-btn ${i == currentPages ? 'active' : ''}">
                                ${i}
                        </a>
                    </c:forEach>

                    <!-- Next button -->
                    <c:choose>
                        <c:when test="${currentPages < totalPages}">
                            <c:url var="nextUrl" value="/admin/products">
                                <c:param name="page" value="${currentPages + 1}"/>
                                <c:if test="${not empty searchParam}">
                                    <c:param name="search" value="${searchParam}"/>
                                </c:if>
                                <c:if test="${not empty categoryParam}">
                                    <c:param name="categoryId" value="${categoryParam}"/>
                                </c:if>
                                <c:if test="${not empty statusParam}">
                                    <c:param name="status" value="${statusParam}"/>
                                </c:if>
                            </c:url>
                            <a href="${nextUrl}" class="next-btn">Sau →</a>
                        </c:when>
                        <c:otherwise>
                            <span class="next-btn disabled">Sau →</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>

</body>

</html>