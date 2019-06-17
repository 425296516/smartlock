package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespPeopleManager {


    /**
     * msg : success
     * code : 0
     * page : {"total":61,"list":[{"userId":217,"username":"herundong","password":"e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819","salt":"HN4mLkVYD43PPHYElkY0","email":null,"mobile":"134841515","status":1,"roleIdList":null,"createUserId":215,"createTime":"2019-04-28 15:47:47","date":null,"identity":1,"officeId":null,"logonname":"何润东","gender":1,"idNumber":"320812648154187","jobNumber":null,"userAddress":"阿萨是瑞典v","discountRate":null,"discountMethod":null,"lastLogonIp":null,"lastLogonTime":null,"openId":null,"sessionId":null,"sessionKey":null,"photo":null,"officeName":null,"roleName":null,"recommenedWayId":null,"sysOffice":null,"materialBrandId":null,"materialTypeId":null,"designer":null,"createUserName":null,"value":null,"score":0,"parentId":null,"wxnumber":null,"city":null,"nopassword":"e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819","highSeasPool":0,"officename":null},{"userId":216,"username":"guozhiwei","password":"c4ee3352f96d383ec70afc6c55b605ec38dbef79fe8e4d382212a79468bd04df","salt":"71XrbF1EiWm27gzjiby1","email":null,"mobile":"sdfsadfsdaf","status":1,"roleIdList":null,"createUserId":215,"createTime":"2019-04-28 13:38:23","date":null,"identity":1,"officeId":null,"logonname":"郭志伟","gender":1,"idNumber":"sdfsadfsa","jobNumber":null,"userAddress":"sdfsadf","discountRate":null,"discountMethod":null,"lastLogonIp":null,"lastLogonTime":null,"openId":null,"sessionId":null,"sessionKey":null,"photo":null,"officeName":null,"roleName":null,"recommenedWayId":null,"sysOffice":null,"materialBrandId":null,"materialTypeId":null,"designer":null,"createUserName":null,"value":null,"score":0,"parentId":null,"wxnumber":null,"city":null,"nopassword":"c4ee3352f96d383ec70afc6c55b605ec38dbef79fe8e4d382212a79468bd04df","highSeasPool":0,"officename":null}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":31,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8}
     */

    private String msg;
    private int code;
    private PageBean page;

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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * total : 61
         * list : [{"userId":217,"username":"herundong","password":"e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819","salt":"HN4mLkVYD43PPHYElkY0","email":null,"mobile":"134841515","status":1,"roleIdList":null,"createUserId":215,"createTime":"2019-04-28 15:47:47","date":null,"identity":1,"officeId":null,"logonname":"何润东","gender":1,"idNumber":"320812648154187","jobNumber":null,"userAddress":"阿萨是瑞典v","discountRate":null,"discountMethod":null,"lastLogonIp":null,"lastLogonTime":null,"openId":null,"sessionId":null,"sessionKey":null,"photo":null,"officeName":null,"roleName":null,"recommenedWayId":null,"sysOffice":null,"materialBrandId":null,"materialTypeId":null,"designer":null,"createUserName":null,"value":null,"score":0,"parentId":null,"wxnumber":null,"city":null,"nopassword":"e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819","highSeasPool":0,"officename":null},{"userId":216,"username":"guozhiwei","password":"c4ee3352f96d383ec70afc6c55b605ec38dbef79fe8e4d382212a79468bd04df","salt":"71XrbF1EiWm27gzjiby1","email":null,"mobile":"sdfsadfsdaf","status":1,"roleIdList":null,"createUserId":215,"createTime":"2019-04-28 13:38:23","date":null,"identity":1,"officeId":null,"logonname":"郭志伟","gender":1,"idNumber":"sdfsadfsa","jobNumber":null,"userAddress":"sdfsadf","discountRate":null,"discountMethod":null,"lastLogonIp":null,"lastLogonTime":null,"openId":null,"sessionId":null,"sessionKey":null,"photo":null,"officeName":null,"roleName":null,"recommenedWayId":null,"sysOffice":null,"materialBrandId":null,"materialTypeId":null,"designer":null,"createUserName":null,"value":null,"score":0,"parentId":null,"wxnumber":null,"city":null,"nopassword":"c4ee3352f96d383ec70afc6c55b605ec38dbef79fe8e4d382212a79468bd04df","highSeasPool":0,"officename":null}]
         * pageNum : 1
         * pageSize : 2
         * size : 2
         * startRow : 1
         * endRow : 2
         * pages : 31
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
         * navigateFirstPage : 1
         * navigateLastPage : 8
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
             * userId : 217
             * username : herundong
             * password : e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819
             * salt : HN4mLkVYD43PPHYElkY0
             * email : null
             * mobile : 134841515
             * status : 1
             * roleIdList : null
             * createUserId : 215
             * createTime : 2019-04-28 15:47:47
             * date : null
             * identity : 1
             * officeId : null
             * logonname : 何润东
             * gender : 1
             * idNumber : 320812648154187
             * jobNumber : null
             * userAddress : 阿萨是瑞典v
             * discountRate : null
             * discountMethod : null
             * lastLogonIp : null
             * lastLogonTime : null
             * openId : null
             * sessionId : null
             * sessionKey : null
             * photo : null
             * officeName : null
             * roleName : null
             * recommenedWayId : null
             * sysOffice : null
             * materialBrandId : null
             * materialTypeId : null
             * designer : null
             * createUserName : null
             * value : null
             * score : 0
             * parentId : null
             * wxnumber : null
             * city : null
             * nopassword : e154649d35f042a47fffa943c8ed4361f78a28c3a3b69e0120bf11dc7bfd7819
             * highSeasPool : 0
             * officename : null
             */

            private int userId;
            private String username;
            private String password;
            private String salt;
            private Object email;
            private String mobile;
            private int status;
            private Object roleIdList;
            private int createUserId;
            private String createTime;
            private Object date;
            private int identity;
            private Object officeId;
            private String logonname;
            private int gender;
            private String idNumber;
            private Object jobNumber;
            private String userAddress;
            private Object discountRate;
            private Object discountMethod;
            private Object lastLogonIp;
            private Object lastLogonTime;
            private Object openId;
            private Object sessionId;
            private Object sessionKey;
            private Object photo;
            private Object officeName;
            private Object roleName;
            private Object recommenedWayId;
            private Object sysOffice;
            private Object materialBrandId;
            private Object materialTypeId;
            private Object designer;
            private Object createUserName;
            private Object value;
            private int score;
            private Object parentId;
            private Object wxnumber;
            private Object city;
            private String nopassword;
            private int highSeasPool;
            private Object officename;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getRoleIdList() {
                return roleIdList;
            }

            public void setRoleIdList(Object roleIdList) {
                this.roleIdList = roleIdList;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getDate() {
                return date;
            }

            public void setDate(Object date) {
                this.date = date;
            }

            public int getIdentity() {
                return identity;
            }

            public void setIdentity(int identity) {
                this.identity = identity;
            }

            public Object getOfficeId() {
                return officeId;
            }

            public void setOfficeId(Object officeId) {
                this.officeId = officeId;
            }

            public String getLogonname() {
                return logonname;
            }

            public void setLogonname(String logonname) {
                this.logonname = logonname;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public Object getJobNumber() {
                return jobNumber;
            }

            public void setJobNumber(Object jobNumber) {
                this.jobNumber = jobNumber;
            }

            public String getUserAddress() {
                return userAddress;
            }

            public void setUserAddress(String userAddress) {
                this.userAddress = userAddress;
            }

            public Object getDiscountRate() {
                return discountRate;
            }

            public void setDiscountRate(Object discountRate) {
                this.discountRate = discountRate;
            }

            public Object getDiscountMethod() {
                return discountMethod;
            }

            public void setDiscountMethod(Object discountMethod) {
                this.discountMethod = discountMethod;
            }

            public Object getLastLogonIp() {
                return lastLogonIp;
            }

            public void setLastLogonIp(Object lastLogonIp) {
                this.lastLogonIp = lastLogonIp;
            }

            public Object getLastLogonTime() {
                return lastLogonTime;
            }

            public void setLastLogonTime(Object lastLogonTime) {
                this.lastLogonTime = lastLogonTime;
            }

            public Object getOpenId() {
                return openId;
            }

            public void setOpenId(Object openId) {
                this.openId = openId;
            }

            public Object getSessionId() {
                return sessionId;
            }

            public void setSessionId(Object sessionId) {
                this.sessionId = sessionId;
            }

            public Object getSessionKey() {
                return sessionKey;
            }

            public void setSessionKey(Object sessionKey) {
                this.sessionKey = sessionKey;
            }

            public Object getPhoto() {
                return photo;
            }

            public void setPhoto(Object photo) {
                this.photo = photo;
            }

            public Object getOfficeName() {
                return officeName;
            }

            public void setOfficeName(Object officeName) {
                this.officeName = officeName;
            }

            public Object getRoleName() {
                return roleName;
            }

            public void setRoleName(Object roleName) {
                this.roleName = roleName;
            }

            public Object getRecommenedWayId() {
                return recommenedWayId;
            }

            public void setRecommenedWayId(Object recommenedWayId) {
                this.recommenedWayId = recommenedWayId;
            }

            public Object getSysOffice() {
                return sysOffice;
            }

            public void setSysOffice(Object sysOffice) {
                this.sysOffice = sysOffice;
            }

            public Object getMaterialBrandId() {
                return materialBrandId;
            }

            public void setMaterialBrandId(Object materialBrandId) {
                this.materialBrandId = materialBrandId;
            }

            public Object getMaterialTypeId() {
                return materialTypeId;
            }

            public void setMaterialTypeId(Object materialTypeId) {
                this.materialTypeId = materialTypeId;
            }

            public Object getDesigner() {
                return designer;
            }

            public void setDesigner(Object designer) {
                this.designer = designer;
            }

            public Object getCreateUserName() {
                return createUserName;
            }

            public void setCreateUserName(Object createUserName) {
                this.createUserName = createUserName;
            }

            public Object getValue() {
                return value;
            }

            public void setValue(Object value) {
                this.value = value;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public Object getWxnumber() {
                return wxnumber;
            }

            public void setWxnumber(Object wxnumber) {
                this.wxnumber = wxnumber;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public String getNopassword() {
                return nopassword;
            }

            public void setNopassword(String nopassword) {
                this.nopassword = nopassword;
            }

            public int getHighSeasPool() {
                return highSeasPool;
            }

            public void setHighSeasPool(int highSeasPool) {
                this.highSeasPool = highSeasPool;
            }

            public Object getOfficename() {
                return officename;
            }

            public void setOfficename(Object officename) {
                this.officename = officename;
            }
        }
    }
}
