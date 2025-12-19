<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="./CSS/style.css">
    <link rel="stylesheet" href="./CSS/news.css">
    <link rel="stylesheet" href="./CSS/sub_login.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link href='https://fonts.googleapis.com/css?family=Monsieur La Doulaise' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Literata' rel='stylesheet'>
    <title>Tin Tức - LightUp</title>
</head>

<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="link-page" id="up">
        <div class="containt_link">
            <a href="index.jsp"><i class="bi bi-house"></i> Trang chủ </a>
            <span> /</span>
            <a href="">Tin tức</a>
        </div>
    </div>
    <div class="search-overlay" id="searchOverlay" onclick="closeSearchPanel()"></div>

    <section class="section_1_news" id="home">
        <div class="cont">
            <div class="section_1_content">
                <h1>Lightup</h1>
                <span class="highlight">TIN TỨC</span>
            </div>
        </div>
        <div class="ux-shape-divider ux-shape-divider--bottom">
            <svg viewBox="0 0 1000 100" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="none">
                <path class="ux-shape-fill"
                      d="M1000 100H0V80H479.686C481.808 80 483.843 80.8429 485.343 82.3432L497.879 94.8787C499.05 96.0503 500.95 96.0503 502.121 94.8787L514.657 82.3431C516.157 80.8428 518.192 80 520.314 80H1000V100Z">
                </path>
            </svg>
        </div>
    </section>

    <!-- Featured Articles Section -->
    <div class="section_2_news">
        <div class="section-header">
            <h2>Bài viết nổi bật</h2>
            <div class="divider"></div>
        </div>

        <c:if test="${not empty featuredArticles && featuredArticles.size() > 0}">
            <!-- Main Featured Article -->
            <div class="new_1">
                <div class="cont">
                    <img src="${featuredArticles[0].mainImg}"
                         alt="${featuredArticles[0].title}">
                    <div class="title">
                        <div class="title_r">
                            <div class="title_last">
                                <span>${featuredArticles[0].categoryName != null ? featuredArticles[0].categoryName : 'Bản tin LightUP'}</span>
                                <li><fmt:formatDate value="${featuredArticles[0].dateOfPosting}"
                                                    pattern="dd/MM/yyyy"/></li>
                            </div>
                            <div class="name">
                                <a href="news-detail?id=${featuredArticles[0].id}">${featuredArticles[0].title}</a>
                            </div>
                        </div>
                        <div class="title_l">
                            <a href="news-detail?id=${featuredArticles[0].id}"> <i class="bi bi-arrow-up-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Secondary Featured Articles -->
            <div class="new_2">
                <div class="container">
                    <c:forEach var="article" items="${featuredArticles}" begin="1" end="3">
                        <div class="card">
                            <div class="card_img">
                                <img src="${article.mainImg}"
                                     alt="${article.title}"
                                     onerror="this.src='https://via.placeholder.com/500x300?text=No+Image'">
                            </div>
                            <div class="title">
                                <div class="title_r">
                                    <div class="title_last">
                                        <span>${article.categoryName != null ? article.categoryName : 'Bản tin LightUP'}</span>
                                        <li><fmt:formatDate value="${article.dateOfPosting}" pattern="dd/MM/yyyy"/></li>
                                    </div>
                                    <div class="name">
                                        <a href="news-detail?id=${article.id}">${article.title}</a>
                                    </div>
                                </div>
                                <div class="title_l">
                                    <a href="news-detail?id=${article.id}"> <i class="bi bi-arrow-up-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>

    <!-- All Articles Section -->
    <section class="section_3_news">
        <div class="container">
            <div class="header">
                <h1 id="normal-news">Bài viết</h1>
                <div class="sort-dropdown">
                    <label>Sắp xếp theo:</label>
                    <select id="sortSelect" onchange="sortArticles(this.value)">
                        <option value="newest" ${param.sort == 'newest' || empty param.sort ? 'selected' : ''}>Mới
                            nhất
                        </option>
                        <option value="oldest" ${param.sort == 'oldest' ? 'selected' : ''}>Cũ nhất</option>
                    </select>
                </div>
            </div>

            <div class="articles-list">
                <c:forEach var="article" items="${articles}">
                    <div class="article-card" data-page="${currentPage}">
                        <div class="article-image">
                            <img src="${article.mainImg}"
                                 alt="${article.title}"
                                 onerror="this.src='https://via.placeholder.com/500x300?text=No+Image'">
                        </div>
                        <div class="article-content">
                            <h2 class="article-title">
                                <a href="news-detail?id=${article.id}">${article.title}</a>
                            </h2>
                            <p class="article-description">
                                    <%--                                <c:choose>--%>
                                    <%--                                    <c:when test="${article.description.length() > 150}">--%>
                                    <%--                                        ${article.description.substring(0, 150)}...--%>
                                    <%--                                    </c:when>--%>
                                    <%--                                    <c:otherwise>--%>
                                    <%--                                        ${article.description}--%>
                                    <%--                                    </c:otherwise>--%>
                                    <%--                                </c:choose>--%>
                            </p>
                            <div class="article-meta">
                                <strong>${article.categoryName != null ? article.categoryName : 'Tin tức LightUP'}</strong>
                                <span>•</span>
                                <span><fmt:formatDate value="${article.dateOfPosting}" pattern="dd/MM/yyyy"/></span>
                            </div>
                        </div>
                        <div class="title_l">
                            <a href="news-detail?id=${article.id}"> <i class="bi bi-arrow-up-right"></i></a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="news?page=${currentPage - 1}&sort=${param.sort}#normal-news" class="prev-btn">←
                            Trước</a>
                    </c:if>
                    <c:if test="${currentPage == 1}">
                        <span class="prev-btn disabled">← Trước</span>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage == i}">
                                <span class="page-btn active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="news?page=${i}&sort=${param.sort}#normal-news"
                                   class="page-btn">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="news?page=${currentPage + 1}&sort=${param.sort}#normal-news" class="next-btn">Sau →</a>
                    </c:if>
                    <c:if test="${currentPage == totalPages}">
                        <span class="next-btn disabled">Sau →</span>
                    </c:if>
                </div>
            </c:if>
        </div>
    </section>
    <jsp:include page="footer.jsp"/>


    <a href="#up">
        <button id="scrollToTopBtn">
            <i class="bi bi-chevron-up"></i>
        </button>
    </a>
</main>

<script src="./JS/index.js"></script>
<script>
    function sortArticles(sortBy) {
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('sort', sortBy);
        urlParams.set('page', '1'); // Reset to page 1 when sorting
        window.location.href = 'news?' + urlParams.toString();
    }
</script>
</body>

</html>