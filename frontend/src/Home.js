// Home.js
import React, { Component } from 'react';
import './App.css';
import MyBarChart from './MyBarChart';
import MyBarChart2 from './MyBarChart2';
import MyBarChart3 from './MyBarChart3';
import air from './air.png';
import water from './water.png';
import allergy from './allergy.png';
import disaster from './disaster.png';

const headerStyle = {
    color: 'Black',
    backgroundColor: 'lightGray',
    fontSize: '28px',
    textAlign: 'left'
} 

const allergenInfoStyle = {
	color: 'white',
	fontSize: '40px'
}

const subheaderStyle = {
	font: "Montserrat",
	fontSize: '20px'
}

const imageStyle ={
	width: '40px',
	height: '40px'
}

const backdropStyle = {
	backgroundColor: 'darkGray'
}


class Home extends Component{

	state = {
			isLoading: true,
			address: "",
			air: [],
			allergen: [],
			water: [],
			natural: []
		  };

	async componentDidMount() {

			this.address = window.location.pathname;
			this.address = this.address.replace("/compare/", "");
			// URL converts spaces to %20


			// console.log(this.address);
		
			const AQIResponse = await fetch('/api/AQIData/'+this.address);
			// const AQIResponse = await fetch('/api/AQIData');
			const AQIBody = await AQIResponse.json();
			this.setState({ air: AQIBody, isLoading: true });
			// console.log(this.state.air["airData"]);

			const waterResponse = await fetch('/api/waterData/'+this.address);
			// const waterResponse = await fetch('/api/waterData');
		    const waterBody = await waterResponse.json();
			this.setState({ water: waterBody, isLoading: true });
			// console.log(this.state.water["waterData"]);

			const natResponse = await fetch('/api/naturalDisasters/'+this.address);
			// const natResponse = await fetch('/api/naturalDisasters');
		    const natBody = await natResponse.json();
			this.setState({ natural: natBody, isLoading: true });
			// console.log(this.state.natural["natData"]);
			
			const allergenResponse = await fetch('/api/allergenData/' + this.address);
			const allergenBody = await allergenResponse.json();
			this.setState({ allergen: allergenBody, isLoading:false});
			// console.log(this.state.allergen);

			// const bar1Response = await fetch('/api/barChart1/'+this.address);
			// const bar1Body = await bar1Response.json();
			// this.setState({ bar1: bar1Body,isLoading: true });
			// console.log(this.state.bar1["bar1Data"]);

		    // const bar2Response = await fetch('/api/barChart2/'+this.address);
			// const bar2Body = await bar2Response.json();
			// this.setState({ bar2: bar2Body,isLoading: true });
			// console.log(this.state.bar2["bar2Data"]);

			// const bar3Response = await fetch('/api/barChart3/'+this.address);
			// const bar3Body = await bar3Response.json();
			// this.setState({ bar3: bar3Body,isLoading: false });
			// console.log(this.state.bar3["bar3Data"]);



//			var regex = /%20/gi;
//			this.address = this.address.replace(regex, " ");
	}
	
	render() {
		
		const {natural, isLoading} = this.state;

	    if (isLoading) {
	      return (
			  <div>
			  	<br></br><br></br><br></br><br></br>
		  		<p>Loading...</p>
			  </div>
		  );
	    }
		
		return(
			
			<div style={backdropStyle}>
			<head> <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /> </head>
			<header style={headerStyle}>
	          ENVIRONMENT NOW
	      </header>
	      <br></br>
		
	      <h3> Location: {this.address.replace(/%20/gi," ")}</h3>
		  <div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">							        
							<table align="center">
								<tbody>
									<tr>
										<td valign="top" style ={subheaderStyle}>
										<img src={air} style={imageStyle} alt="air"/>
											AIR                      
											<MyBarChart 
											data={this.state.air["airData"]}
											textAlign = 'top' 
											/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="column">							
							<table align="center">
								<tbody>
									<tr>
										<td valign="top" style ={subheaderStyle}>
											<img src={allergy} style={imageStyle} alt="pollen"/>
											ALLERGENS
											<br></br><br></br><br></br><br></br>
											<header>Tree Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen["Tree Pollen"]}</p>
											<br></br><br></br>
											<header>Grass Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen["Grass Pollen"]}</p>
											<br></br><br></br>
											<header>Ragweed Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen["Ragweed Pollen"]}</p>
											<br></br><br></br>
										</td>
									</tr>
								</tbody>
						</table>
						</div>
					</div>
				<div class="ui inverted vertical divider">EV NOW</div>
				</div>


				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">							        
							<table align="center">
								<tbody>
									<tr>
										<td valign="top" style ={subheaderStyle}>
												<img src={water} style={imageStyle} alt="water"/>
												WATER
												<MyBarChart2 
												data={this.state.water["waterData"]}
												align = 'top' />
												<br></br><br></br>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="column">							
							<table align="center">
							<tbody>
								<tr>
									<td valign="top" style ={subheaderStyle}>
										<img src={disaster} style={imageStyle} alt="disaster"/>
										LAND
										<MyBarChart3 data={this.state.natural["natData"]}/>
										<br></br><br></br>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				<div class="ui inverted vertical divider">EV NOW</div>
				</div>
		</div>
		);
	}
}
export default Home;