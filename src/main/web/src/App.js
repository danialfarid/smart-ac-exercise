import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";
import Notification from "./components/Notification";
import {Route, Router} from "react-router-dom";
import history from "./history";
import DeviceDetails from "./components/DeviceDetails";
import Profile from "./components/Profile";

class App extends Component {
    login() {
        this.props.auth.login();
    }

    logout() {
        this.props.auth.logout();
    }

    componentDidMount() {
        const {renewSession} = this.props.auth;
        this.setState({profile: {}});

        if (localStorage.getItem('isLoggedIn') === 'true') {
            renewSession();
        }
    }

    render() {
        const {isAuthenticated} = this.props.auth;

        return (
            <div>
                <h1 className="header"><Link className="logo" to="/home">Smart AC Portal</Link>
                    <div className="login">
                        {isAuthenticated() && <Profile auth={this.props.auth}/>}
                        {
                            isAuthenticated() && (
                                <Link to={""} onClick={this.logout.bind(this)}>
                                    Log Out
                                </Link>
                            )
                        }
                        {
                            !isAuthenticated() && (
                                <Link to={""} onClick={this.login.bind(this)}>
                                    Log In
                                </Link>
                            )
                        }
                    </div>
                </h1>
                {
                    !isAuthenticated() && (
                        <h4>
                            &nbsp;&nbsp;You are not logged in! Please{' '}
                            <a
                                style={{cursor: 'pointer'}}
                                onClick={this.login.bind(this)}
                            >
                                Log In
                            </a>
                            {' '}to continue.
                        </h4>
                    )
                }
                {isAuthenticated() && (<Notification/>)}
                {isAuthenticated() && (
                    <Router history={history}>
                        <div>
                            <Route path="/device/:deviceId" component={DeviceDetails}/>
                        </div>
                    </Router>
                )}
            </div>
        );
    }
}

export default App;
