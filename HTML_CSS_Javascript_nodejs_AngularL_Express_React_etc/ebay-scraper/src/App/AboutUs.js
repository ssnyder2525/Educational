/*

TODO
	[] Add something under your name (Cody, Steve)

*/


/* React Dependancies */
import React from 'react';



var AboutUs = React.createClass({
  render () {
	return (
		<div className="aboutUsImage">
			<br /><br /><br />
			<div className="text-center">
				<h1>Our Team</h1>
				<br />
				<div className="aboutUsOuter">
				<div className="aboutUsPerson">
					<img className="profileImg" src="https://scontent-sjc2-1.xx.fbcdn.net/hphotos-ash2/v/t1.0-9/1014015_617913758276640_1410926177_n.jpg?oh=c297dec123b67000cceae09048014d4f&oe=56D8DE04"/>
					<h4>Cody Burt</h4>
				</div>
					<br />
				<div className="aboutUsPerson">
					<img className="profileImg" src="https://scontent-sjc2-1.xx.fbcdn.net/hphotos-prn2/v/t1.0-9/10710521_10152931809484050_7217761176826974126_n.jpg?oh=ef205b678c6cb0eeef68398ea2b78bc2&oe=56EBC9FC"/>
					<h4>Ben Thompson</h4>
				</div>
					<br />
				<div className="aboutUsPerson">
					<img className="profileImg" src="https://scontent-sjc2-1.xx.fbcdn.net/hphotos-xat1/t31.0-8/10887543_893948523978267_5436835274656427405_o.jpg"/>
					<h4>Steve Snyder</h4>
				</div>
				</div>
				
			</div>
		</div>
	);
  }
});



export default AboutUs;