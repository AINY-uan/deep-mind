package org.ainy.deepmind.util;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 阿拉丁省油的灯
 * @description 省份数据
 * @date 2020-07-28 19:43
 */
public class China {

    @Data
    public static class Province {

        /**
         * 省份代码
         */
        private String provinceCode;
        /**
         * 省份名称
         */
        private String provinceName;
        /**
         * 市列表
         */
        private List<City> cityList;
    }

    @Data
    public static class City {

        /**
         * 市代码
         */
        private String cityCode;
        /**
         * 市名称
         */
        private String cityName;
        /**
         * 区列表
         */
        private List<Area> areaList;
    }

    @Data
    public static class Area {

        /**
         * 区代码
         */
        private String areaCode;
        /**
         * 区名称
         */
        private String areaName;
    }

    public static void main(String[] args) {

        try {
            // 2019年11月中华人民共和国县以上行政区划代码网页
            Document doc = Jsoup.connect("http://www.mca.gov.cn/article/sj/xzqh/2019/2019/201912251506.html").maxBodySize(0).get();
            Elements elements = doc.getElementsByClass("xl7128029");
            // 省和市
            Elements elementsProAndCity = doc.getElementsByClass("xl7028029");
            List<String> stringListProAndCity = elementsProAndCity.eachText();
            List<String> stringList = elements.eachText();
            List<String> stringName = new ArrayList<>();
            List<String> stringCode = new ArrayList<>();
            stringListProAndCity.addAll(stringList);
            for (int i = 0; i < stringListProAndCity.size(); i++) {
                if (i % 2 == 0) {
                    // 地区代码
                    stringCode.add(stringListProAndCity.get(i));
                } else {
                    // 地区名字
                    stringName.add(stringListProAndCity.get(i));
                }
            }
            // 正常情况 两个 list size 应该 一样
            System.out.println("stringName  size= " + stringName.size() + "   stringCode   size= " + stringCode.size());
            if (stringName.size() != stringCode.size()) {
                throw new RuntimeException("数据错误");
            }
            List<Province> provinceList = processData(stringName, stringCode);

            System.out.println(provinceList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Province> processData(List<String> stringName, List<String> stringCode) {

        List<Province> provinceList = new ArrayList<>();

        // 获取省
        for (int i = 0; i < stringCode.size(); i++) {
            String provinceName = stringName.get(i);
            String provinceCode = stringCode.get(i);
            if (provinceCode.endsWith("0000")) {
                Province province = new Province();
                province.setProvinceCode(provinceCode);
                province.setProvinceName(provinceName);
                provinceList.add(province);
                List<City> cities = new ArrayList<>();
                province.setCityList(cities);
            }
        }

        // 获取市
        for (Province value : provinceList) {

            String provinceName = value.getProvinceName();
            String provinceCode = value.getProvinceCode();
            // 直辖市 城市和省份名称一样
            if (provinceName.contains("北京") || provinceName.contains("上海") || provinceName.contains("天津") || provinceName.contains("重庆")) {
                City city = new City();
                List<Area> areas = new ArrayList<>();
                city.setCityName(provinceName);
                city.setCityCode(provinceCode);
                city.setAreaList(areas);
                value.getCityList().add(city);
            } else {
                for (int j = 0; j < stringCode.size(); j++) {
                    String cityName = stringName.get(j);
                    String cityCode = stringCode.get(j);
                    if (!cityCode.equals(provinceCode)) {
                        if (cityCode.startsWith(provinceCode.substring(0, 2))) {
                            if (cityCode.endsWith("00")) {
                                City city = new City();
                                List<Area> areas = new ArrayList<>();
                                city.setCityName(cityName);
                                city.setCityCode(cityCode);
                                city.setAreaList(areas);
                                value.getCityList().add(city);
                            }
                        }
                    }
                }
            }
        }

        // 获取区县
        for (Province province : provinceList) {

            List<City> cities = province.getCityList();
            for (City city : cities) {
                // 遍历获取县区
                String cityCode = city.getCityCode();
                String cityName = city.getCityName();
                for (int k = 0; k < stringCode.size(); k++) {
                    String areaName = stringName.get(k);
                    String areaCode = stringCode.get(k);
                    if (cityName.contains("北京") || cityName.contains("上海") || cityName.contains("天津") || cityName.contains("重庆")) {
                        if (!province.getProvinceCode().equals(areaCode) && areaCode.startsWith(province.getProvinceCode().substring(0, 2))) {
                            Area area = new Area();
                            area.setAreaName(areaName);
                            area.setAreaCode(areaCode);
                            city.getAreaList().add(area);
                        }
                    } else {
                        if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
                            Area area = new Area();
                            area.setAreaName(areaName);
                            area.setAreaCode(areaCode);
                            city.getAreaList().add(area);
                        }
                    }

                }

            }
        }

        // 已经处理的数据移除
        List<String> stringNameList = new ArrayList<>(stringName);
        List<String> stringCodeList = new ArrayList<>(stringCode);

        for (Province province : provinceList) {
            stringNameList.remove(province.getProvinceName());
            stringCodeList.remove(province.getProvinceCode());
            List<City> cities = province.getCityList();
            for (City city : cities) {
                stringNameList.remove(city.getCityName());
                stringCodeList.remove(city.getCityCode());
                List<Area> listArea = city.getAreaList();
                for (Area area : listArea) {
                    stringNameList.remove(area.getAreaName());
                    stringCodeList.remove(area.getAreaCode());
                }
            }
        }

        // 处理石河子 特殊 市，City Code 不以00结尾
        for (Province province : provinceList) {
            for (int k = 0; k < stringCodeList.size(); k++) {
                if (stringCodeList.get(k).startsWith(province.getProvinceCode().substring(0, 2))) {
                    City city = new City();
                    List<Area> areas = new ArrayList<>();
                    city.setCityName(stringCodeList.get(k));
                    city.setCityCode(stringNameList.get(k));
                    city.setAreaList(areas);
                    province.getCityList().add(city);
                }
            }
        }

        return provinceList;
    }
}
