import axios from "axios";

window.API_URL = process.env.NODE_ENV === 'development' ? 'http://127.0.0.1:8080/' : '../';

class Api {
    static getDevices(serialNo) {
        return axios.get(window.API_URL + 'ac' + "?" +
            (serialNo ? "serialNo=" + encodeURIComponent(serialNo) : ""));
    }

    static getSensorReadings(deviceId, endDate) {
        return axios.get(window.API_URL + 'ac/' + deviceId + "/sensorReading?endDate=" + endDate);
    }

    static getNotifications() {
        return axios.get(window.API_URL + 'notifications');
    }

    static resolveNotification(notifId) {
        return axios.post(window.API_URL + 'notifications/' + notifId + '/resolve');
    }
}

export default Api;