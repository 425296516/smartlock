package com.anlida.smartlock.model.resp;

import java.util.List;

public class RespRankingListBean {
        /**
         * total : 3
         * pages : 1
         * pageSize : 10
         * list : [{"ranking":1,"userId":"D67356C5B88645059ECD49EB28F95386","busGroup":"北京公交集团","name":"unknow","headImg":null,"data":76},{"ranking":2,"userId":"E7644E15EB9640FA802598CE600DFD56","busGroup":"北京公交集团","name":"heyapeng","headImg":"http://40.73.4.33/user-images/975FEAD815D74906A6C05552854C19BC.png","data":59},{"ranking":3,"userId":"BBBEDB2D344443E7B34930EADDC02AC3","busGroup":"北京公交集团","name":"zhangyuan","headImg":null,"data":58}]
         */

        private int total;
        private int pages;
        private int pageSize;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * ranking : 1
             * userId : D67356C5B88645059ECD49EB28F95386
             * busGroup : 北京公交集团
             * name : unknow
             * headImg : null
             * data : 76.0
             */

            private int ranking;
            private String userId;
            private String busGroup;
            private String name;
            private String headImg;
            private String data;

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getBusGroup() {
                return busGroup;
            }

            public void setBusGroup(String busGroup) {
                this.busGroup = busGroup;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }
}
