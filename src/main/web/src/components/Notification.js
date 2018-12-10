import React from "react";
import {Link} from "react-router-dom";
import Api from "../Api";
import Table from "react-bootstrap/es/Table";
import moment from "moment";
import Button from "react-bootstrap/es/Button";

class Notification extends React.Component {
    constructor(props) {
        super(props);
        this.state = {notifications: []};
        this.resolveNotif = this.resolveNotif.bind(this);
    }

    componentDidMount() {
        this.pullNotifications();
        setInterval(() => {
            this.pullNotifications();
        }, 10000);
    }

    pullNotifications() {
        Api.getNotifications().then(response => this.setState({notifications: response.data}));
    }

    notificationMessage(notif) {
        if (notif.sensorReading.carbonMonoxide > 9) {
            return "Carbon Monoxide above limit: " + notif.sensorReading.carbonMonoxide;
        } else {
            return "Unhealthy Device: " + notif.sensorReading.healthStatus;
        }
    }

    resolveNotif(notif, elem) {
        elem.blur();
        Api.resolveNotification(notif.id);
        this.pullNotifications();
    }

    render() {
        return (
            <div className="notif">
                {this.state.notifications ?
                    <Table>
                        <tbody>
                        {this.state.notifications.map(notif => {
                            return <tr key={notif.id}>
                                <td><Button onClick={(e) => this.resolveNotif(notif, e.target)}>Mark as
                                    resolved</Button></td>
                                <td>{moment(notif.date).format("YYYY-MM-DD HH:mm")}</td>
                                <td>{notif.sensorReading.serialNo}</td>
                                <td>{this.notificationMessage(notif)}</td>
                            </tr>
                        })}
                        </tbody>
                    </Table> : null}
            </div>
        );
    }
}

export default Notification;