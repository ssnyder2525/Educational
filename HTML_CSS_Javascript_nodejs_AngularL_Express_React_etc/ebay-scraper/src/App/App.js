/*

TODO:
	[] 

*/


/* React Dependancies */
import React from 'react';
import { Link } from 'react-router';



var App = React.createClass({
	
	logout() {
		this.props.logout()
	},
	
	render () {
		
		var isLoggedIn = Object.keys(this.props.getUser()).length > 0; // for showing the logout button
		
		return (
			<div>
				<nav className="navbar navbar-inverse navbar-fixed-top" role="navigation">
					<div>
						<div className="navbar-header">
							<button type="button" className="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
								<span className="sr-only">Toggle navigation</span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
							<Link to="#" className="navbar-brand">Home</Link>
						</div>
						<div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul className="nav navbar-nav">
								<li>
									<a href="#/about-us">About us</a>
								</li>
							</ul>
							{ isLoggedIn ? <a id="logoutButton" href="#/login" onClick={this.logout} className="navbar-brand">logout</a>: ""}
						</div>
					</div>
				</nav>
			
				<div className= "removeUpperPadding">
					{this.props.children}
				</div>
			</div>
		);
	}
});



export default App;