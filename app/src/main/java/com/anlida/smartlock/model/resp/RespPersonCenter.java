package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespPersonCenter {


    /**
     * msg : success
     * code : 0
     * data : [{"id":3,"createBy":null,"updateBy":null,"createDate":"2019-06-10 10:27:57","updateDate":null,"remarks":null,"delFlag":0,"sort":null,"pageNum":null,"pageSize":null,"userId":1,"contructionId":null,"contructionName":"","bys":"","workType":"","name":"","workId":"","idCard":"","phone":"","address":""},{"id":2,"createBy":null,"updateBy":null,"createDate":"2019-05-29 14:20:10","updateDate":null,"remarks":null,"delFlag":0,"sort":null,"pageNum":null,"pageSize":null,"userId":1,"contructionId":null,"contructionName":"一号工地","bys":"水电工","workType":null,"name":"朱翔","workId":"123456","idCard":"32082619970705361X","phone":"13405514397","address":"湖北省武汉市江岸区"}]
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
         * id : 3
         * createBy : null
         * updateBy : null
         * createDate : 2019-06-10 10:27:57
         * updateDate : null
         * remarks : null
         * delFlag : 0
         * sort : null
         * pageNum : null
         * pageSize : null
         * userId : 1
         * contructionId : null
         * contructionName :
         * bys :
         * workType :
         * name :
         * workId :
         * idCard :
         * phone :
         * address :
         */

        private String id;
        private String createBy;
        private String updateBy;
        private String createDate;
        private String updateDate;
        private String remarks;
        private String delFlag;
        private String sort;
        private String pageNum;
        private String pageSize;
        private String userId;
        private String contructionId;
        private String contructionName;
        private String bys;
        private String workType;
        private String name;
        private String workId;
        private String idCard;
        private String phone;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContructionId() {
            return contructionId;
        }

        public void setContructionId(String contructionId) {
            this.contructionId = contructionId;
        }

        public String getContructionName() {
            return contructionName;
        }

        public void setContructionName(String contructionName) {
            this.contructionName = contructionName;
        }

        public String getBys() {
            return bys;
        }

        public void setBys(String bys) {
            this.bys = bys;
        }

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
