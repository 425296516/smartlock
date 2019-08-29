package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespWarnLocation {


    /**
     * msg : success
     * code : 0
     * data : [{"id":11,"createBy":null,"updateBy":null,"createDate":null,"updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"1","uname":"2","phone":"5","idCard":"4","name":null,"warningType":"CO2报警","status":1,"longitude":"118.7178250000","latitude":"32.2045140000","content":null,"createTime":null,"updateTime":null},{"id":13,"createBy":null,"updateBy":null,"createDate":null,"updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"3","uname":"1","phone":"4","idCard":"3","name":null,"warningType":"CO2报警","status":1,"longitude":"118.7178250000","latitude":"32.1045140000","content":null,"createTime":null,"updateTime":null}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * createBy : null
         * updateBy : null
         * createDate : null
         * updateDate : null
         * remarks : null
         * delFlag : null
         * sort : null
         * pageNum : null
         * pageSize : null
         * imei : 1
         * uname : 2
         * phone : 5
         * idCard : 4
         * name : null
         * warningType : CO2报警
         * status : 1
         * longitude : 118.7178250000
         * latitude : 32.2045140000
         * content : null
         * createTime : null
         * updateTime : null
         */

        private int id;
        private Object createBy;
        private Object updateBy;
        private Object createDate;
        private Object updateDate;
        private Object remarks;
        private Object delFlag;
        private Object sort;
        private Object pageNum;
        private Object pageSize;
        private String imei;
        private String uname;
        private String phone;
        private String idCard;
        private Object name;
        private String bloodType;
        private String warningType;
        private int status;
        private double longitude;
        private double latitude;
        private Object content;
        private Object createTime;
        private Object updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getPageNum() {
            return pageNum;
        }

        public void setPageNum(Object pageNum) {
            this.pageNum = pageNum;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public String getWarningType() {
            return warningType;
        }

        public void setWarningType(String warningType) {
            this.warningType = warningType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
