import React from "react";
import Api from "../Api";
import ToggleButtonGroup from "react-bootstrap/es/ToggleButtonGroup";
import ToggleButton from "react-bootstrap/es/ToggleButton";
import moment from "moment";
import Table from "react-bootstrap/es/Table";
import Panel from "react-bootstrap/es/Panel";
import {CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis} from 'recharts';

class DeviceDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            sensorReadings: [], endDate: moment().startOf('day'),
            chartType: ["humidity", "temperature", "carbonMonoxide", "healthStatus"]
        };
        this.deviceId = props.match.params.deviceId;
        this.dateRangeChanged = this.dateRangeChanged.bind(this);
        this.chartTypeChanged = this.chartTypeChanged.bind(this);
    }

    componentDidMount() {
        this.fetchSensorReadings(moment().startOf('day'));
    }

    fetchSensorReadings(endDate) {
        Api.getSensorReadings(this.deviceId, endDate.valueOf())
            .then(response => this.setState({sensorReadings: response.data}));
    }

    dateRangeChanged(dateRange) {
        let endDate;
        if (dateRange === 'day') {
            endDate = moment().startOf('day');
        } else if (dateRange === 'week') {
            endDate = moment().startOf('isoWeek');
        } else if (dateRange === 'month') {
            endDate = moment().startOf('month');
        } else if (dateRange === 'year') {
            endDate = moment().startOf('year');
        }
        this.setState({...this.state, endDate});
        this.fetchSensorReadings(endDate);
    }

    chartTypeChanged(chartType) {
        this.setState({...this.state, chartType});
    }

    render() {
        return (
            <Panel>
                <Panel.Heading>
                    <Panel.Title componentClass="h3">Sensor readings for device: {this.deviceId}</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    Show sensor readings for <ToggleButtonGroup type="radio" name="dateRange"
                                                                defaultValue="day" onChange={this.dateRangeChanged}>
                    <ToggleButton value={"day"}>Today</ToggleButton>
                    <ToggleButton value={"week"}>This Week</ToggleButton>
                    <ToggleButton value={"month"}>This Month</ToggleButton>
                    <ToggleButton value={"year"}>This Year</ToggleButton>
                </ToggleButtonGroup><br/>
                    <LineChart width={1000} height={400} data={this.state.sensorReadings}
                               margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                        <XAxis dataKey="date"/>
                        <YAxis interval={100}/>
                        <CartesianGrid strokeDasharray="3 3"/>
                        <Legend verticalAlign="top" height={36}/>
                        <Tooltip/>
                        <Line type="monotone" dot={false} dataKey="humidity" stroke="#0078ff"/>
                    </LineChart>
                    <LineChart width={1000} height={400} data={this.state.sensorReadings}
                               margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                        <XAxis dataKey="date"/>
                        <YAxis interval={100}/>
                        <CartesianGrid strokeDasharray="3 3"/>
                        <Legend verticalAlign="top" height={36}/>
                        <Tooltip/>
                        <Line type="monotone" dot={false} dataKey="temperature" stroke="#D81800"/>
                    </LineChart>
                    <LineChart width={1000} height={400} data={this.state.sensorReadings}
                               margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                        <XAxis dataKey="date"/>
                        <YAxis interval={100}/>
                        <CartesianGrid strokeDasharray="3 3"/>
                        <Legend verticalAlign="top" height={36}/>
                        <Tooltip/>
                        <Line type="monotone" dot={false} dataKey="carbonMonoxide" stroke="#5b641c"/>
                    </LineChart>
                    <br/>
                    <Table striped bordered condensed hover className="readingsTable">
                        <thead>
                        <tr>
                            <th>Temperature</th>
                            <th>Humidity</th>
                            <th>Carbon Monoxide</th>
                            <th>Health Status</th>
                            <th>Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.sensorReadings.map(reading =>
                            <tr key={reading.id}>
                                <td> {reading.temperature} </td>
                                <td> {reading.humidity} </td>
                                <td> {reading.carbonMonoxide} </td>
                                <td> {reading.healthStatus} </td>
                                <td> {moment(reading.date).format("YYYY-MM-DD HH:mm")} </td>
                            </tr>)}
                        </tbody>
                    </Table>
                </Panel.Body>
            </Panel>
        );
    }
}

export default DeviceDetails;