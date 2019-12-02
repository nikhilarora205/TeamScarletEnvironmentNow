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
			address: "",
			isLoading: true,
			masterData: []
		  };

	async componentDidMount() {
			console.log("BEGIN TEST");

			this.address = window.location.pathname;
			this.address = this.address.replace("/compare/", "");
			console.log(this.address);
			
			const masterResponse = await fetch('/api/getData/'+this.address)
			const masterResponseBody = await masterResponse.json();
			this.setState({masterData: masterResponseBody,isLoading: true });
			//console.log(this.state.masterData[0]["airData"]);
			//console.log(this.state.masterData[1]);
			//console.log(this.state.masterData[2]);
			//console.log(this.state.masterData[3]);
			this.setState({isLoading: false });
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
											data={this.state.masterData[0]["airData"]}
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
											<p style= {allergenInfoStyle}>{this.state.masterData[3]["Tree Pollen"]}</p>
											<br></br><br></br>
											<header>Grass Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.masterData[3]["Grass Pollen"]}</p>
											<br></br><br></br>
											<header>Ragweed Pollen: </header>
											<br></br>
											<p style= {allergenInfoStyle}>{this.state.masterData[3]["Ragweed Pollen"]}</p>
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
												data={this.state.masterData[1]["waterData"]}
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
										<MyBarChart3 data={this.state.masterData[2]["natData"]}/>
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