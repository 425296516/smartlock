package com.anlida.smartlock.model.resp;

import java.util.List;

public class UserCarListResponse {
    private int total;
    private int pages;
    private int pageSize;
    private List<CarInfo> list;
    private Statistics statistics;

    public int getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<CarInfo> getList() {
        return list;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public static class Statistics {
        private int totalVehicle;
        private int runVehicle;
        private int stopVehicle;
        private int chargeVehicle;
        private int malfunctionVehicle;

        public int getMalfunctionVehicle() {
            return malfunctionVehicle;
        }

        public int getTotalVehicle() {
            return totalVehicle;
        }

        public int getRunVehicle() {
            return runVehicle;
        }

        public int getStopVehicle() {
            return stopVehicle;
        }

        public int getChargeVehicle() {
            return chargeVehicle;
        }
    }
}
