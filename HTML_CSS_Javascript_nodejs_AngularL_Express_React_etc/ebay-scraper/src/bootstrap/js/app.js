import React from 'react'
import Router from 'react-router'
import Route from 'react-router'
import Link from 'react-router'
import IndexRoute from 'react-router'

//var Item = require('./components/Item.js');

/*

	TODO:
	
		[x] Sign-in / up page
		[] About Page
		[x] Remove Services page
		[x] Remove Contact page
		[] Define Item Obejct - Steve's gonna try this.
		[] Add item functionality
			- Popup? or something
			- Fills in item object data (Form)
		
		COMPONENTS:
			
			[] Add item entries
			[] Item frame
		
		SERVER: 
		
			[] Users
			[] Ebay search (url is in the doc)


*/


var App = React.createClass({
  render: function() {
	return (
	  <div>
	   <nav className="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div className="container">
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
						<a href="#/about-us">About</a>
					</li>
					
				</ul>
			</div>
		</div>
	</nav>

		<div className="container">
		  {this.props.children}
		</div>
	  </div>
	);
  }
});

var Login = React.createClass({
	render: function() { // remove a tag in button
		return (
			<div className="container">
				<br /> 
				<div className="text-center">
					<h1>eBay Scraper</h1><br />
					<span className="pt15">username : </span><input type="text" /><br /><br />
					<span className="pt15">password : </span><input type="password" /><br /><br />
					<button type="submit" className="btn btn-primary">
						<a href="#/dashboard" className="white">login</a>
					</button><br /><br />
					<p>or</p>
					<a href="#/sign-up">sign-up</a>
				</div>
			</div>
		);
	}
});

var Dashboard = React.createClass({
	getInitialState: function() {
		return {loggedIn: false};
	},
	
	addItem: function(event) {
		document.getElementById('itemList').insertAdjacentHTML('beforeend', '<div>Item should appear</div>');
		document.getElementById('itemList').insertAdjacentHTML('beforeend', '<Item name="bob"></Item>');
	},
  
	render() {
		return (
		<div>
		<div className="container">

			<div className="row">

				<div className="col-md-3">
					<p className="lead">Shop Name</p>
					<div id="itemList" className="list-group">
						<button className="btn btn-success" type="button" onClick={this.addItem}>Add Item</button>
					</div>
				</div>

				<div className="col-md-9">

					<div className="row">

						<div className="col-sm-4 col-lg-4 col-md-4">
							<div className="thumbnail">
								<img src="http://placehold.it/320x150" alt=""/>
								<div className="caption">
									<h4 className="pull-right">$24.99</h4>
									<h4><a href="#">First Product</a>
									</h4>
									<p>See more snippets like this online store item at <a target="_blank" href="http://www.bootsnipp.com">Bootsnipp - http://bootsnipp.com</a>.</p>
								</div>
								<div className="ratings">
									<p className="pull-right">15 reviews</p>
									<p>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
									</p>
								</div>
							</div>
						</div>

						<div className="col-sm-4 col-lg-4 col-md-4">
							<div className="thumbnail">
								<img src="http://placehold.it/320x150" alt=""/>
								<div className="caption">
									<h4 className="pull-right">$64.99</h4>
									<h4><a href="#">Second Product</a>
									</h4>
									<p>This is a short description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
								</div>
								<div className="ratings">
									<p className="pull-right">12 reviews</p>
									<p>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star-empty"></span>
									</p>
								</div>
							</div>
						</div>

						<div className="col-sm-4 col-lg-4 col-md-4">
							<div className="thumbnail">
								<img src="http://placehold.it/320x150" alt=""/>
								<div className="caption">
									<h4 className="pull-right">$74.99</h4>
									<h4><a href="#">Third Product</a>
									</h4>
									<p>This is a short description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
								</div>
								<div className="ratings">
									<p className="pull-right">31 reviews</p>
									<p>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star-empty"></span>
									</p>
								</div>
							</div>
						</div>
	
						<div className="col-sm-4 col-lg-4 col-md-4">
							<div className="thumbnail">
								<img src="http://placehold.it/320x150" alt=""/>
								<div className="caption">
									<h4 className="pull-right">$84.99</h4>
									<h4><a href="#">Fourth Product</a>
									</h4>
									<p>This is a short description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
								</div>
								<div className="ratings">
									<p className="pull-right">6 reviews</p>
									<p>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star-empty"></span>
										<span className="glyphicon glyphicon-star-empty"></span>
									</p>
								</div>
							</div>
						</div>
	
						<div className="col-sm-4 col-lg-4 col-md-4">
							<div className="thumbnail">
								<img src="http://placehold.it/320x150" alt=""/>
								<div className="caption">
									<h4 className="pull-right">$94.99</h4>
									<h4><a href="#">Fifth Product</a>
									</h4>
									<p>This is a short description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
								</div>
								<div className="ratings">
									<p className="pull-right">18 reviews</p>
									<p>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star"></span>
										<span className="glyphicon glyphicon-star-empty"></span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div className="container">
	
			<hr/>
	
			<footer>
				<div className="row">
					<div className="col-lg-12">
						<p>Copyright &copy; Your Website 2014</p>
					</div>
				</div>
			</footer>
	
		</div>
	</div>
	);
  }
});

var AboutUs = React.createClass({
  render() {
	return (
	  <div>
		<img src="http://pcwallart.com/images/utah-mountains-wallpaper-2.jpg" className="stretch" align="middle" alt="" />
		<div id="about-us">
			<h1>About Us</h1>
			<h4>Team Members</h4>
			<ul>
				<li>Cody Burt</li>
				<li>Steve Snyder</li>
				<li>Ben Thompson</li>
			</ul>
		</div>
	  </div>
	);
  }
});

var SignUp = React.createClass({
  render() {
	return (
	  <div>
		<h1>Sign up</h1>
		<p>This is another page</p>
	  </div>
	);
  }
});



// Run the routes --- there is an attribute of route called onEnter < then you can add some kind of auth
var routes = (
	<Router>
		<Route name="app" path="/" component={App}>
			<IndexRoute component={Login}/>
			<Route name="page" path="/about-us" component={AboutUs} />
			<Route name="dashboard" path="/dashboard" component={Dashboard} /> // 
			<Route name="signup" path="/sign-up" component={SignUp}/> // Sign up page
		</Route>
	</Router>
);

ReactDOM.render(routes, document.getElementById('content'));



