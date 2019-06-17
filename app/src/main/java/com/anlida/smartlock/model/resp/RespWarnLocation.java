package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespWarnLocation {

    /**
     * msg : success
     * code : 0
     * data : [{"id":6,"createBy":null,"updateBy":null,"createDate":null,"updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":null,"phone":null,"name":null,"warningType":null,"status":0,"longitude":"118.6282","latitude":"32.059","content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":null,"updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"1234567","uname":null,"phone":null,"name":null,"warningType":null,"status":0,"longitude":"118.6252","latitude":"32.059","content":null}]
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
         * id : 6
         * createBy : null
         * updateBy : null
         * createDate : null
         * updateDate : null
         * remarks : null
         * delFlag : null
         * sort : null
         * pageNum : null
         * pageSize : null
         * imei : 123456
         * uname : null
         * phone : null
         * name : null
         * warningType : null
         * status : 0
         * longitude : 118.6282
         * latitude : 32.059
         * content : null
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
        private Object uname;
        private Object phone;
        private Object name;
        private Object warningType;
        private int status;
        private double longitude;
        private double latitude;
        private Object content;

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

        public Object getUname() {
            return uname;
        }

        public void setUname(Object uname) {
            this.uname = uname;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getWarningType() {
            return warningType;
        }

        public void setWarningType(Object warningType) {
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
    }
}
