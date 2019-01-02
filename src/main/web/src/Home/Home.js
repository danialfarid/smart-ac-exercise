import React, {Component} from 'react';
import Devices from "../components/Devices";

class Home extends Component {
    login() {
        this.props.auth.login();
    }

    render() {
        const {isAuthenticated} = this.props.auth;
        return (
            <div className="container">
                {isAuthenticated() && (<Devices/>)}
            </div>
        );
    }
}

export default Home;
