import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route} from "react-router-dom";
import './index.css';
import Devices from "./components/Devices";
import DeviceDetails from "./components/DeviceDetails";
import Link from "react-router-dom/es/Link";
import Notification from "./components/Notification";

class App extends React.Component {
    render() {
        return (
            <div>
                <Link to=""><h1 className="header">Smart AC Portal</h1></Link>
                <Notification/>
                <Route path="/" exact component={Devices}/>
                <Route path="/device/:deviceId" component={DeviceDetails}/>
            </div>
        );
    }
}

ReactDOM.render(
    <Router>
        <App />
    </Router>,
    document.getElementById('root')
);
