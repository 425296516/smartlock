package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespProvinCity {
    /**
     * province : 310000
     * provinceName : 上海市
     * list : [{"city":"310000","cityName":"上海市"}]
     */

    private String province;
    private String provinceName;
    private boolean select;

    private List<ListBean> list;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * city : 310000
         * cityName : 上海市
         */

        private String city;
        private String cityName;
        private boolean select;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}
