// Compare2.js
import React, { Component } from 'react';
import './App.css';
import MyBarChart from './MyBarChart';
import MyBarChart2 from './MyBarChart2';
import MyBarChart3 from './MyBarChart3';

import air from './air.png';
import water from './water.png';
import allergy from './allergy.png';
import disaster from './disaster.png';

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


const headerStyle = {
    color: 'Black',
    backgroundColor: 'lightGray',
    fontSize: '28px',
    textAlign: 'left'
} 

const topMarginStyle ={
    marginTop: '10rem'
}

class Compare2 extends Component{
	
	state = {
			isLoading: true,
			address: "",
			air: [],
			allergen: [],
			water: [],
			natural: [],
			address2: "",
			air2: [],
			allergen2: [],
			water2: [],
			natural2: [],
			bar1: [],
			bar2: [],
			bar3: [],
			bar1_: [],
			bar2_: [],
			bar3_: []
		  };

	async componentDidMount() {

			var path = window.location.href;
			// console.log(window.location.pathname);
			// console.log(path);
			// ***** change depending on website you are deploying to *****
			path = path.replace("http://localhost:3000/compare2/", "");
			// console.log(path);
			const addresses = path.split("?");
			// console.log(addresses);
			this.address = addresses[0];
			this.address2 = addresses[1];
			
			
			//
			// have to separate both addresses
			// this.address = "100 Orvieto Cove, Liberty Hill, TX, 78642";
			// this.address2 = "15504 Cinca Terra Drive, Austin, TX, 78738";
			//

			// debug statements
			// console.log(this.address);
			// console.log(this.address2);
		
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
			this.setState({ allergen: allergenBody, isLoading:true});
			// console.log(this.state.allergen);
			

			// // second address data
			const AQIResponse2 = await fetch('/api/AQIData/'+this.address2);
			// const AQIResponse = await fetch('/api/AQIData');
			const AQIBody2 = await AQIResponse2.json();
			this.setState({ air2: AQIBody2, isLoading: true });
			// console.log(this.state.air2["airData"]);

			const waterResponse2 = await fetch('/api/waterData/'+this.address2);
			// const waterResponse = await fetch('/api/waterData');
		    const waterBody2 = await waterResponse2.json();
			this.setState({ water2: waterBody2, isLoading: true });
			// console.log(this.state.water2["waterData"]);

			const natResponse2 = await fetch('/api/naturalDisasters/'+this.address2);
			// const natResponse = await fetch('/api/naturalDisasters');
		    const natBody2 = await natResponse2.json();
			this.setState({ natural2: natBody2, isLoading: true });
			// console.log(this.state.natural2["natData"]);
			
			const allergenResponse2 = await fetch('/api/allergenData/' + this.address2);
			const allergenBody2 = await allergenResponse2.json();
			this.setState({ allergen2: allergenBody2, isLoading:false});
			// console.log(this.state.allergen2);

			// const bar1Response = await fetch('/api/barChart1/'+this.address);
			// const bar1Body = await bar1Response.json();
			// this.setState({ bar1: bar1Body,isLoading: true });
			// console.log(this.state.bar1["bar1Data"]);

			// const allergenResponse = await fetch('/api/allergenData/' + this.address);
			// const allergenBody = await allergenResponse.json();
			// this.setState({ allergen: allergenBody, isLoading:true});
			// console.log(this.state.allergen);

			// const allergenResponse2 = await fetch('/api/allergenData/' + this.address2);
			// const allergenBody2 = await allergenResponse2.json();
			// this.setState({ allergen2: allergenBody2, isLoading:true});
			// console.log(this.state.allergen2);

		    // const bar2Response = await fetch('/api/barChart2/'+this.address);
			// const bar2Body = await bar2Response.json();
			// this.setState({ bar2: bar2Body,isLoading: true });
			// console.log(this.state.bar2["bar2Data"]);

			// const bar3Response = await fetch('/api/barChart3/'+this.address);
			// const bar3Body = await bar3Response.json();
			// this.setState({ bar3: bar3Body,isLoading: true });
			// console.log(this.state.bar3["bar3Data"]);

			// const bar1_Response = await fetch('/api/barChart1/'+this.address2);
			// const bar1_Body = await bar1_Response.json();
			// this.setState({ bar1_: bar1_Body,isLoading: true });
			// console.log(this.state.bar1_["bar1Data"]);

		    // const bar2_Response = await fetch('/api/barChart2/'+this.address2);
			// const bar2_Body = await bar2_Response.json();
			// this.setState({ bar2_: bar2_Body,isLoading: true });
			// console.log(this.state.bar2_["bar2Data"]);

			// const bar3_Response = await fetch('/api/barChart3/'+this.address2);
			// const bar3_Body = await bar3_Response.json();
			// this.setState({ bar3_: bar3_Body,isLoading: false });
			// console.log(this.state.bar3_["bar3Data"]);
			
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
			<div>
				<head><link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /></head>
				<header style={headerStyle}>
		          ENVIRONMENT NOW
		      	</header>
		      	<br></br><br></br>

				  <div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<h3>Location 1: {this.address.replace(/%20/gi," ")}</h3>
						</div>
						<div class="column">
							<h3>Location 2: {this.address2.replace(/%20/gi," ")}</h3>
						</div>
					</div>
					<div class="ui inverted vertical divider">VERSUS</div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">							        
							<table align="center">
								<tbody>
									<tr>
										<td valign="top" style ={subheaderStyle}>     
											<MyBarChart 
											data={this.state.air["airData"]}
											textAlign = 'top' 
											/>
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
									<td valign="top">
										<MyBarChart 
										data={this.state.air2["airData"]}
										textAlign = 'top' 
										/>
										<br></br><br></br>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				<div class="ui inverted vertical divider">AIR QUALITY <img src={air} style={imageStyle} alt="air"/></div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
							<tbody>
								<tr>
										<td valign="top">
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
						<div class="column">
							<table align="center">
							<tbody>
								<tr>
									<td valign="top">
										<br></br><br></br><br></br><br></br>
											<header>Tree Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen2["Tree Pollen"]}</p>
											<br></br><br></br>
											<header>Grass Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen2["Grass Pollen"]}</p>
											<br></br><br></br>
											<header>Ragweed Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.allergen2["Ragweed Pollen"]}</p>
											<br></br><br></br>
									</td>
								</tr>
							</tbody>	
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">ALLERGENS <img src={allergy} style={imageStyle} alt="pollen"/></div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
								<tbody>
									<tr>
										<td valign="top"> 
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
									<td valign="top"> 
										<MyBarChart2 
										data={this.state.water2["waterData"]}
										align = 'top' />
										<br></br><br></br>
									</td>
								</tr>
							</tbody>
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">WATER QUALITY <img src={water} style={imageStyle} alt="water"/></div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
								<tbody>
											<tr>
												<td valign="top">
													<MyBarChart3 data={this.state.natural["natData"]}/>
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
									
										<td valign="top">
												<MyBarChart3 data={this.state.natural2["natData"]}/>
												<br></br><br></br>
										</td>
										
									</tr>
									
								</tbody>
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">NATURAL DISASTERS <img src={disaster} style={imageStyle} alt="disaster"/></div>
				</div>

		      
				<br></br><br></br><br></br><br></br>
				
			</div>
			);
		}
	
}
		
export default Compare2;