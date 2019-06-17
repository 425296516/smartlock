package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespWarnRecord {


    /**
     * msg : success
     * code : 0
     * data : {"total":52,"list":[{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":5,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:57:59","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"1234567","uname":"罗纳尔多","phone":"13405514397","name":null,"warningType":"温度过高","status":1,"longitude":"118.6252","latitude":"32.059","content":null}],"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 52
         * list : [{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":8,"createBy":null,"updateBy":null,"createDate":"2019-05-09 16:10:17","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":7,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:23","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.6282","latitude":"32.059","content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":"118.1282","latitude":"32.059","content":null},{"id":6,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:58:21","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"123456","uname":"朱翔","phone":"1","name":null,"warningType":"温度过高","status":1,"longitude":null,"latitude":null,"content":null},{"id":5,"createBy":null,"updateBy":null,"createDate":"2019-05-09 15:57:59","updateDate":null,"remarks":null,"delFlag":null,"sort":null,"pageNum":null,"pageSize":null,"imei":"1234567","uname":"罗纳尔多","phone":"13405514397","name":null,"warningType":"温度过高","status":1,"longitude":"118.6252","latitude":"32.059","content":null}]
         * pageNum : 1
         * pageSize : 10
         * size : 10
         * startRow : 1
         * endRow : 10
         * pages : 6
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6]
         * navigateFirstPage : 1
         * navigateLastPage : 6
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * id : 8
             * createBy : null
             * updateBy : null
             * createDate : 2019-05-09 16:10:17
             * updateDate : null
             * remarks : null
             * delFlag : null
             * sort : null
             * pageNum : null
             * pageSize : null
             * imei : 123456
             * uname : 朱翔
             * phone : 1
             * name : null
             * warningType : 温度过高
             * status : 1
             * longitude : 118.6282
             * latitude : 32.059
             * content : null
             */

            private int id;
            private String createBy;
            private String updateBy;
            private String createDate;
            private String updateDate;
            private String remarks;
            private String delFlag;
            private String sort;
            private String pageNum;
            private String pageSize;
            private String imei;
            private String uname;
            private String phone;
            private String name;
            private String warningType;
            private String status;//status 1未处理 2已处理
            private String longitude;
            private String latitude;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getWarningType() {
                return warningType;
            }

            public void setWarningType(String warningType) {
                this.warningType = warningType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
