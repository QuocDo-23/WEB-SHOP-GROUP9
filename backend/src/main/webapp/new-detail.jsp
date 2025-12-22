<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${article.title}</title>
    <link rel="stylesheet" href="CSS/news_details.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="CSS/style.css">
    <link rel="stylesheet" href="CSS/sub_login.css">

</head>

<body>
<!--Header-->
<jsp:include page="header.jsp"/>

<div class="link-page" id="up">
    <div class="containt_link">
        <a href="./index.html"><i class="bi bi-house"></i> Trang chủ </a>
        <span> /</span>
        <a href="news.html">Tin tức</a>
        <span> /</span>
        <b>${article.title}</b>
    </div>
</div>

<main>
    <section class="container_1">
        <div class="title">

            <h1>${article.title}</h1>

            <c:forEach items="${contents}" var="c">
                <c:choose>

                    <c:when test="${c.contentType == 'TEXT'}">
                        <p>${c.content}</p>
                    </c:when>

                    <c:when test="${c.contentType == 'IMAGE_URL'}">
                        <div class="img">
                            <img src="${c.content}" alt="">
                        </div>
                    </c:when>

                    <c:when test="${c.contentType == 'VIDEO_EMBED'}">
                        <iframe src="${c.content}" frameborder="0"></iframe>
                    </c:when>

                    <c:when test="${c.contentType == 'HEADING'}">
                        <h2>${c.content}</h2>
                    </c:when>

                    <c:when test="${c.contentType == 'BLOCKQUOTE'}">
                        <blockquote>${c.content}</blockquote>
                    </c:when>

                    <c:when test="${c.contentType == 'CODE_BLOCK'}">
                        <pre><code>${c.content}</code></pre>
                    </c:when>

                </c:choose>
            </c:forEach>

        </div>
    </section>

    <section class="container_2">
        <div class="news">
            <h2>Tin liên quan</h2>

            <div class="article-card" data-page="1">
                <div class="article-image">
                    <img src="https://i1-giadinh.vnecdn.net/2023/03/02/setop-1677718671-1677718682-6756-1677718754.jpg?w=500&h=300&q=100&dpr=2&fit=crop&s=oe9JH4fyZe2kF7EJumgLxA"
                         alt="Bản tin 1">
                </div>
                <div class="article-content">
                    <h2 class="article-title"><a href="news_details.html">Lumi ra mắt bộ sưu tập đèn Lumi
                        Lighting</a> </h2>
                    <p class="article-description">Với gần 30 mẫu thiết kế đèn, Lumi Lighting ứng dụng được vào
                        hầu hết không gian nội thất và ngoại thất, phù hợp nhiều phong cách kiến trúc. </p>
                    <div class="article-meta">
                        <strong>Tin tức LightUP</strong>
                        <span>•</span>
                        <span>25/10/2025</span>
                    </div>
                </div>
            </div>

            <div class="article-card" data-page="1">
                <div class="article-image">
                    <img src="https://i1-giadinh.vnecdn.net/2023/03/02/setop-1677718671-1677718682-6756-1677718754.jpg?w=500&h=300&q=100&dpr=2&fit=crop&s=oe9JH4fyZe2kF7EJumgLxA"
                         alt="Bản tin 1">
                </div>
                <div class="article-content">
                    <h2 class="article-title"><a href="news_details.html">Lumi ra mắt bộ sưu tập đèn Lumi
                        Lighting</a> </h2>
                    <p class="article-description">Với gần 30 mẫu thiết kế đèn, Lumi Lighting ứng dụng được vào
                        hầu hết không gian nội thất và ngoại thất, phù hợp nhiều phong cách kiến trúc. </p>
                    <div class="article-meta">
                        <strong>Tin tức LightUP</strong>
                        <span>•</span>
                        <span>25/10/2025</span>
                    </div>
                </div>
            </div>
            <div class="article-card" data-page="1">
                <div class="article-image">
                    <img src="https://i1-giadinh.vnecdn.net/2023/03/02/setop-1677718671-1677718682-6756-1677718754.jpg?w=500&h=300&q=100&dpr=2&fit=crop&s=oe9JH4fyZe2kF7EJumgLxA"
                         alt="Bản tin 1">
                </div>
                <div class="article-content">
                    <h2 class="article-title"><a href="news_details.html">Lumi ra mắt bộ sưu tập đèn Lumi
                        Lighting</a> </h2>
                    <p class="article-description">Với gần 30 mẫu thiết kế đèn, Lumi Lighting ứng dụng được vào
                        hầu hết không gian nội thất và ngoại thất, phù hợp nhiều phong cách kiến trúc. </p>
                    <div class="article-meta">
                        <strong>Tin tức LightUP</strong>
                        <span>•</span>
                        <span>25/10/2025</span>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Footer -->
<jsp:include page="footer.jsp"/>
<script src="./JS/index.js"></script>
</body>
</html>
