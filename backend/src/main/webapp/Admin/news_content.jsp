<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <title>Nội Dung Bài Viết - Quản Lý Đèn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin.css">
</head>

<body>
    <div class="container">
        <!-- SIDEBAR -->
        <%@ include file="siderbar.jsp" %>

        <!-- MAIN CONTENT -->
        <div class="main-content">
            <div class="header">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <a href="${pageContext.request.contextPath}/admin/news" style="text-decoration: none; color: #718096; font-size: 20px;">
                        <i class="bi bi-arrow-left"></i> 🔙
                    </a>
                    <h1>Nội Dung: ${article.title}</h1>
                </div>
                <div class="user-info">
                    <div class="avatar">
                        ${sessionScope.user.name.charAt(0)}
                    </div>

                    <div>
                        <div style="font-weight: 600;">${sessionScope.user.name}</div>
                        <div style="font-size: 12px; color: #718096;">
                            <c:choose>
                                <c:when test="${sessionScope.user.roleId == 1}">Quản trị viên</c:when>
                                <c:otherwise>Nhân viên</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <div class="table-container">
                <div class="table-header">
                    <h2>Chi Tiết Nội Dung</h2>
                    <button class="btn btn-primary" onclick="openModal()">
                        + Thêm Nội Dung
                    </button>
                </div>

                <div class="content-list">
                    <c:choose>
                        <c:when test="${not empty contents}">
                            <c:forEach items="${contents}" var="content">
                                <div class="content-item">
                                    <div class="content-preview">
                                        <span class="content-type-badge type-${content.contentType}">${content.contentType}</span>
                                        <span style="color: #718096; font-size: 12px; margin-left: 10px;">Thứ tự: ${content.displayOrder}</span>

                                        <div class="content-body">
                                            <c:choose>
                                                <c:when test="${content.contentType == 'IMAGE'}">
                                                    <div class="content-image">
                                                        <img src="${content.content}" alt="Content Image">
                                                        <div style="font-size: 12px; color: #718096; margin-top: 5px;">${content.content}</div>
                                                    </div>
                                                </c:when>

                                                <c:when test="${content.contentType == 'HEADING'}">
                                                    <h3 style="margin: 0;">${content.content}</h3>
                                                </c:when>

                                                <c:when test="${content.contentType == 'QUOTE'}">
                                                    <blockquote style="border-left: 4px solid #cbd5e0; padding-left: 10px; margin: 0; color: #4a5568; font-style: italic;">
                                                        ${content.content}
                                                    </blockquote>
                                                </c:when>

                                                <c:otherwise>
                                                    <div class="content-text">${content.content}</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                    <div class="content-actions">
                                        <button class="btn btn-sm btn-edit" onclick="editContent('${content.id}', '${content.contentType}', '${content.displayOrder}', '${content.content}')">Sửa</button>
                                        <form action="${pageContext.request.contextPath}/admin/news-content" method="post" onsubmit="return confirm('Bạn có chắc muốn xóa nội dung này?');">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="articleId" value="${article.id}">
                                            <input type="hidden" name="contentId" value="${content.id}">
                                            <button type="submit" class="btn btn-sm btn-delete">Xóa</button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <p style="text-align: center; color: #718096; padding: 20px;">Chưa có nội dung chi tiết nào.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- thêm/sửa nd -->
    <div id="contentModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">Thêm Nội Dung Mới</h3>
                <button class="close-btn" onclick="closeModal()">×</button>
            </div>

            <form id="contentForm" action="${pageContext.request.contextPath}/admin/news-content" method="post">
                <input type="hidden" name="action" id="formAction" value="add">
                <input type="hidden" name="articleId" value="${article.id}">
                <input type="hidden" name="contentId" id="contentId">

                <div class="form-group">
                    <label>Loại Nội Dung</label>
                    <select name="contentType" id="contentType" required onchange="toggleContentInput()">
                        <option value="TEXT">Đoạn văn bản (Text)</option>
                        <option value="IMAGE">Hình ảnh (Image)</option>
                        <option value="HEADING">Tiêu đề phụ (Heading)</option>
                        <option value="QUOTE">Trích dẫn (Quote)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thứ tự hiển thị</label>
                    <input type="number" name="displayOrder" id="displayOrder" placeholder="Để trống sẽ tự động thêm vào cuối">
                </div>

                <div class="form-group">
                    <label id="contentLabel">Nội Dung</label>
                    <textarea name="content" id="contentText" rows="5" style="width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px;"></textarea>
                    <input type="url" name="content" id="contentUrl" style="display: none; width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px;" placeholder="Nhập link ảnh...">
                </div>

                <div class="form-actions">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal() {
            document.getElementById('contentModal').classList.add('active');
            document.getElementById('modalTitle').innerText = 'Thêm Nội Dung Mới';
            document.getElementById('formAction').value = 'add';
            document.getElementById('contentId').value = '';
            document.getElementById('contentForm').reset();
            toggleContentInput();
        }

        function closeModal() {
            document.getElementById('contentModal').classList.remove('active');
        }

        function editContent(id, type, order, content) {
            document.getElementById('contentModal').classList.add('active');
            document.getElementById('modalTitle').innerText = 'Cập Nhật Nội Dung';
            document.getElementById('formAction').value = 'update';
            document.getElementById('contentId').value = id;

            document.getElementById('contentType').value = type;
            document.getElementById('displayOrder').value = order;

            toggleContentInput();

            if (type === 'IMAGE') {
                document.getElementById('contentUrl').value = content;
                document.getElementById('contentText').value = content;
            } else {
                document.getElementById('contentText').value = content;
                document.getElementById('contentUrl').value = content;
            }
        }

        function toggleContentInput() {
            const type = document.getElementById('contentType').value;
            const textInput = document.getElementById('contentText');
            const urlInput = document.getElementById('contentUrl');
            const label = document.getElementById('contentLabel');

            if (type === 'IMAGE') {
                textInput.style.display = 'none';
                textInput.disabled = true;
                urlInput.style.display = 'block';
                urlInput.disabled = false;
                label.innerText = 'Link Hình Ảnh';
            } else {
                textInput.style.display = 'block';
                textInput.disabled = false;
                urlInput.style.display = 'none';
                urlInput.disabled = true;
                label.innerText = 'Nội Dung Văn Bản';
            }
        }

        // Hiển thị thông báo
        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            const message = urlParams.get('message');
            if (message) {
                alert(decodeURIComponent(message));
                window.history.replaceState({}, document.title, window.location.pathname + window.location.search.replace(/[\?&]message=[^&]+/, '').replace(/^&/, '?'));
            }
        };
    </script>
</body>

</html>