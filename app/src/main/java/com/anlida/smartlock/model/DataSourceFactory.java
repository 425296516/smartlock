package com.anlida.smartlock.model;


public class DataSourceFactory {

    private static UserDataSource userDataSource;

    private static CarDataSource carDataSource;

    private static RankingDataSource rankingDataSource;

    /**
     * @return 用户数据源
     */
    public static UserDataSource getUserDataSource() {
        if (userDataSource == null) {
            userDataSource = new UserDataSourceImpl();
        }
        return userDataSource;
    }

    /**
     * @return 汽车数据源
     */
    public static CarDataSource getCarDataSource() {
        if (carDataSource == null) {
            carDataSource = new CarDataSourceImpl();
        }
        return carDataSource;
    }

    /**
     * @return 排行榜数据源
     */
    public static RankingDataSource getRankingDataSource() {
        if (rankingDataSource == null) {
            rankingDataSource = new RankingDataSourceImpl();
        }
        return rankingDataSource;
    }
}
