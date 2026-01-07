package code.web.webgroup9.service;

import code.web.webgroup9.dao.BannerDao;
import code.web.webgroup9.model.Banner;

import java.util.List;

public class BannerService {
    private final BannerDao bannerDao;

    public BannerService() {
        this.bannerDao = new BannerDao();
    }

    public List<Banner> getBannerByPosition(String position) {
        return bannerDao.getBannerByPosition(position);
    }
}
