package com.anlida.smartlock.model.resp;

public class UserInfo {


    /**
     * msg : success
     * code : 0
     * data : 20000
     * expire : 7200
     * user : {"userId":1,"username":"admin","password":"cb51d1637729cb637ef4fd2b0a532605d4ac10ddba81fa8dead4bad267b8e563","salt":"OXnf2zjCilWlVtsyWnby","email":"root@TongShan.io","mobile":"12345678973","status":1,"roleIdList":null,"createUserId":1,"createTime":"2016-11-11 11:11:11","date":"2018-12-12","identity":1,"officeId":1,"logonname":"超级管理员","gender":1,"idNumber":null,"jobNumber":"1","userAddress":null,"discountRate":"0.1","discountMethod":null,"lastLogonIp":null,"lastLogonTime":"2019-04-19T09:07:08.000+0000","openId":null,"sessionId":null,"sessionKey":null,"photo":null,"officeName":null,"roleName":null,"recommenedWayId":null,"sysOffice":null,"materialBrandId":null,"materialTypeId":null,"designer":null,"createUserName":null,"value":null,"score":6,"parentId":null,"wxnumber":null,"city":null,"nopassword":null,"highSeasPool":null,"officename":""}
     * token : 99b3ff0bb6e1e7491799538182bac222
     */

    private String msg;
    private int code;
    private int data;
    private int expire;
    private UserBean user;
    private String token;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * userId : 1
         * username : admin
         * password : cb51d1637729cb637ef4fd2b0a532605d4ac10ddba81fa8dead4bad267b8e563
         * salt : OXnf2zjCilWlVtsyWnby
         * email : root@TongShan.io
         * mobile : 12345678973
         * status : 1
         * roleIdList : null
         * createUserId : 1
         * createTime : 2016-11-11 11:11:11
         * date : 2018-12-12
         * identity : 1
         * officeId : 1
         * logonname : 超级管理员
         * gender : 1
         * idNumber : null
         * jobNumber : 1
         * userAddress : null
         * discountRate : 0.1
         * discountMethod : null
         * lastLogonIp : null
         * lastLogonTime : 2019-04-19T09:07:08.000+0000
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
         * score : 6
         * parentId : null
         * wxnumber : null
         * city : null
         * nopassword : null
         * highSeasPool : null
         * officename :
         */

        private String userId;
        private String username;
        private String password;
        private String salt;
        private String email;
        private String mobile;
        private int status;
        private String roleIdList;
        private int createUserId;
        private String createTime;
        private String date;
        private int identity;
        private int officeId;
        private String logonname;
        private int gender;
        private String idNumber;
        private String jobNumber;
        private String userAddress;
        private String discountRate;
        private String discountMethod;
        private String lastLogonIp;
        private String lastLogonTime;
        private String openId;
        private String sessionId;
        private String sessionKey;
        private String photo;
        private String officeName;
        private String roleName;
        private String recommenedWayId;
        private String sysOffice;
        private String materialBrandId;
        private String materialTypeId;
        private String designer;
        private String createUserName;
        private String value;
        private int score;
        private String parentId;
        private String wxnumber;
        private String city;
        private String nopassword;
        private String highSeasPool;
        private String officename;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
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

        public String getRoleIdList() {
            return roleIdList;
        }

        public void setRoleIdList(String roleIdList) {
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public int getOfficeId() {
            return officeId;
        }

        public void setOfficeId(int officeId) {
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

        public String getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(String discountRate) {
            this.discountRate = discountRate;
        }

        public String getDiscountMethod() {
            return discountMethod;
        }

        public void setDiscountMethod(String discountMethod) {
            this.discountMethod = discountMethod;
        }

        public String getLastLogonIp() {
            return lastLogonIp;
        }

        public void setLastLogonIp(String lastLogonIp) {
            this.lastLogonIp = lastLogonIp;
        }

        public String getLastLogonTime() {
            return lastLogonTime;
        }

        public void setLastLogonTime(String lastLogonTime) {
            this.lastLogonTime = lastLogonTime;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getOfficeName() {
            return officeName;
        }

        public void setOfficeName(String officeName) {
            this.officeName = officeName;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRecommenedWayId() {
            return recommenedWayId;
        }

        public void setRecommenedWayId(String recommenedWayId) {
            this.recommenedWayId = recommenedWayId;
        }

        public String getSysOffice() {
            return sysOffice;
        }

        public void setSysOffice(String sysOffice) {
            this.sysOffice = sysOffice;
        }

        public String getMaterialBrandId() {
            return materialBrandId;
        }

        public void setMaterialBrandId(String materialBrandId) {
            this.materialBrandId = materialBrandId;
        }

        public String getMaterialTypeId() {
            return materialTypeId;
        }

        public void setMaterialTypeId(String materialTypeId) {
            this.materialTypeId = materialTypeId;
        }

        public String getDesigner() {
            return designer;
        }

        public void setDesigner(String designer) {
            this.designer = designer;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getWxnumber() {
            return wxnumber;
        }

        public void setWxnumber(String wxnumber) {
            this.wxnumber = wxnumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNopassword() {
            return nopassword;
        }

        public void setNopassword(String nopassword) {
            this.nopassword = nopassword;
        }

        public String getHighSeasPool() {
            return highSeasPool;
        }

        public void setHighSeasPool(String highSeasPool) {
            this.highSeasPool = highSeasPool;
        }

        public String getOfficename() {
            return officename;
        }

        public void setOfficename(String officename) {
            this.officename = officename;
        }
    }
}
