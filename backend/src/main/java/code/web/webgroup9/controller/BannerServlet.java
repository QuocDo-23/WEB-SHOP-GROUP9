package code.web.webgroup9.controller;

import code.web.webgroup9.model.Banner;
import code.web.webgroup9.service.BannerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BannerServlet", value = "/home")
public class BannerServlet extends HttpServlet {
    private BannerService bannerService;

    public void init() throws ServletException{
        bannerService = new BannerService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Banner> HBanner = bannerService.getBannerByPosition("home");

        request.setAttribute("banners", HBanner);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}