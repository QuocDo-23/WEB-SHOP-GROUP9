<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống Kê</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin/admin.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>

</head>

<body>
<div class="container">
    <jsp:include page="siderbar.jsp"/>

    <div class="main-content">
        <h1>📈 Thống Kê</h1>

        <!-- ================= 4 Ô THỐNG KÊ ================= -->
        <div class="stats-grid">

            <div class="stat-card">
                <div class="stat-value">
                    <fmt:formatNumber value="${totalRevenue}" groupingUsed="true"/>đ
                </div>
                <div class="stat-label">Tổng doanh thu</div>
            </div>

            <div class="stat-card">
                <div class="stat-value">${totalOrders}</div>
                <div class="stat-label">Tổng đơn hàng</div>
            </div>

            <div class="stat-card">
                <div class="stat-value">${processingOrders}</div>
                <div class="stat-label">Đơn đang xử lý</div>
            </div>

            <div class="stat-card">
                <div class="stat-value">${totalCustomers}</div>
                <div class="stat-label">Khách hàng</div>
            </div>

        </div>

        <!-- ================= BIỂU ĐỒ DOANH THU ================= -->
        <div style="
    width: 700px;
    height: 400px;
    margin: 0 auto 60px auto;
    text-align: center;">

        <h3>Doanh thu 6 tháng gần nhất</h3>
            <canvas id="revenueChart"></canvas>
        </div>

        <!-- ================= BIỂU ĐỒ TRẠNG THÁI ================= -->
        <div style="
            width: 500px;
            height: 350px;
            margin: 60px auto;
            text-align: center;">
            <h3>Tỉ lệ trạng thái đơn hàng</h3>
            <canvas id="statusChart"></canvas>
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
                backgroundColor: '#4f46e5',
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                },
                datalabels: {
                    color: '#ffffff',
                    anchor: 'center',
                    align: 'center',
                    font: {
                        weight: 'bold',
                        size: 12
                    },
                    formatter: function (value) {
                        return (value / 1_000_000).toFixed(1) + ' tr';
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function (value) {
                            return value.toLocaleString('vi-VN') + ' đ';
                        }
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    });



    /* ================= TRẠNG THÁI ================= */
    if (window.statusChart instanceof Chart) {
        window.statusChart.destroy();
    }

    window.statusChart = new Chart(document.getElementById('statusChart'), {
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
</script>

</body>
</html>
