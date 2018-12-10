import React from "react";
import {Link} from "react-router-dom";
import Api from "../Api";
import Table from "react-bootstrap/es/Table";
import {FormControl} from "react-bootstrap";
import Panel from "react-bootstrap/es/Panel";
import moment from "moment";

class Devices extends React.Component {
    constructor(props) {
        super(props);
        this.state = {devices: []};
        this.searchChanged = this.searchChanged.bind(this);
    }

    componentDidMount() {
        this.searchDevices();
    }

    searchDevices(serialNo) {
        Api.getDevices(serialNo).then(response => this.setState({devices: response.data}));
    }

    searchChanged(e) {
        this.searchDevices(e.target.value);
    }

    render() {
        return (
            <Panel>
                <Panel.Heading>
                    <Panel.Title componentClass="h3">AC devices</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    <FormControl
                        type="text"
                        placeholder="Search by serial number"
                        onChange={this.searchChanged}/><br/>
                    <Table>
                        <thead>
                        <tr>
                            <th>Serial No</th>
                            <th>Firmware Version</th>
                            <th>Registration Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.devices.map(device =>
                            <tr key={device.serialNo}>
                                <td><Link to={'/device/' + device.serialNo}>{device.serialNo}</Link></td>
                                <td> {device.firmwareVersion} </td>
                                <td> {moment(device.registerationDate).format("YYYY-MM-DD HH:mm")}</td>
                            </tr>)}
                        </tbody>
                    </Table>
                </Panel.Body>
            </Panel>
        );
    }
}

export default Devices;