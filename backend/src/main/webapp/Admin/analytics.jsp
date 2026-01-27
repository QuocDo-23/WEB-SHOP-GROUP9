<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống Kê</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<div class="container">
    <jsp:include page="siderbar.jsp"/>

    <div class="main-content">
        <h1>📈 Thống Kê</h1>

        <!-- ================= DOANH THU ================= -->
        <div style="width: 700px; height: 400px; margin-bottom: 60px;">
            <h3>Doanh thu 6 tháng gần nhất</h3>
            <canvas id="revenueChart"></canvas>
        </div>

        <!-- ================= TRẠNG THÁI ================= -->
        <div style="width: 500px; height: 350px;">
            <h3>Tỉ lệ trạng thái đơn hàng</h3>
            <canvas id="statusChart"></canvas>
        </div>

        <!-- ================= TOP SẢN PHẨM ================= -->
        <div style="width: 700px; height: 400px; margin-top: 60px;">
            <h3>Top 5 sản phẩm bán chạy</h3>
            <canvas id="topProductChart"></canvas>
        </div>
    </div>
</div>

<script>
    /* ================= DOANH THU ================= */
    const revenueLabels = [
        <c:forEach var="item" items="${monthlyRevenue}">
        "${item.month}",
        </c:forEach>
    ];

    const revenueData = [
        <c:forEach var="item" items="${monthlyRevenue}">
        ${item.revenue},
        </c:forEach>
    ];

    new Chart(document.getElementById('revenueChart'), {
        type: 'bar',
        data: {
            labels: revenueLabels,
            datasets: [{
                label: 'Doanh thu',
                data: revenueData,
                backgroundColor: '#4f46e5'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    /* ================= TRẠNG THÁI ================= */
    const statusCanvas = document.getElementById('statusChart');

    if (window.statusChart instanceof Chart) {
        window.statusChart.destroy();
    }

    window.statusChart = new Chart(statusCanvas, {
        type: 'doughnut',
        data: {
            labels: ['Đang xử lý', 'Đang giao', 'Đã giao', 'Đã huỷ'],
            datasets: [{
                data: [
                    ${pending},
                    ${delivering},
                    ${delivered},
                    ${cancelled}
                ],
                backgroundColor: [
                    '#facc15',
                    '#38bdf8',
                    '#22c55e',
                    '#ef4444'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    /* ================= TOP SẢN PHẨM ================= */
    const productLabels = [
        <c:forEach var="p" items="${topProducts}">
        "${p.productName}",
        </c:forEach>
    ];

    const productData = [
        <c:forEach var="p" items="${topProducts}">
        ${p.totalSold},
        </c:forEach>
    ];

    new Chart(document.getElementById('topProductChart'), {
        type: 'bar',
        data: {
            labels: productLabels,
            datasets: [{
                label: 'Số lượng bán',
                data: productData,
                backgroundColor: '#0ea5e9'
            }]
        },
        options: {
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false
        }
    });
</script>

</body>
</html>
