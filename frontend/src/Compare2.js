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
		address2: "",
		masterData: [],
		masterData2: []
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

		const masterResponse = await fetch('/api/getData/'+this.address)
		const masterResponseBody = await masterResponse.json();
		this.setState({masterData: masterResponseBody,isLoading: true });

		// check if data wasn't able to be scraped
		if(this.state.masterData[0]["airData"].length == 0) this.state.masterData[0]["airData"] = "zero";
		if(this.state.masterData[1]["waterData"].length == 0) this.state.masterData[1]["waterData"] = "zero";

		const masterResponse2 = await fetch('/api/getData/'+this.address2)
		const masterResponseBody2 = await masterResponse2.json();
		this.setState({masterData2: masterResponseBody2,isLoading: true });

		// check if data wasn't able to be scraped
		if(this.state.masterData2[0]["airData"].length == 0) this.state.masterData2[0]["airData"] = "zero";
		if(this.state.masterData2[1]["waterData"].length == 0) this.state.masterData2[1]["waterData"] = "zero";

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
			{this.state.masterData[0]["airData"] !== "zero" &&
					<MyBarChart
				data={this.state.masterData[0]["airData"]}
				textAlign = 'top'/>
			}

			<br></br><br></br>
		{this.state.masterData[0]["airData"] === "zero" &&

				<p>No data available</p>

		}
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
			{this.state.masterData2[0]["airData"] !== "zero" &&
					<MyBarChart
				data={this.state.masterData2[0]["airData"]}
				textAlign = 'top'/>
			}

			<br></br><br></br>
		{this.state.masterData2[0]["airData"] === "zero" &&

				<p>No data available</p>

		}
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
			<td valign="top" style ={subheaderStyle}>
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
		<div class="column">
			<table align="center">
			<tbody>
			<tr>
			<td valign="top" style ={subheaderStyle}>
			<br></br><br></br><br></br><br></br>
		<header>Tree Pollen: </header>
		<br></br>
		<p style= {allergenInfoStyle}>{this.state.masterData2[3]["Tree Pollen"]}</p>
			<br></br><br></br>
		<header>Grass Pollen: </header>
		<br></br>
		<p style= {allergenInfoStyle}>{this.state.masterData2[3]["Grass Pollen"]}</p>
			<br></br><br></br>
		<header>Ragweed Pollen: </header>
		<br></br>
		<p style= {allergenInfoStyle}>{this.state.masterData2[3]["Ragweed Pollen"]}</p>
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
			<td valign="top" style ={subheaderStyle}>
			{this.state.masterData[1]["waterData"] !== "zero" &&
					<MyBarChart
				data={this.state.masterData[1]["waterData"]}
				textAlign = 'top'/>
			}

			<br></br><br></br>
		{this.state.masterData[1]["waterData"] === "zero" &&

				<p>No data available</p>

		}
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
			{this.state.masterData2[1]["waterData"] !== "zero" &&
					<MyBarChart
				data={this.state.masterData2[1]["waterData"]}
				textAlign = 'top'/>
			}

			<br></br><br></br>
		{this.state.masterData2[1]["waterData"] === "zero" &&

				<p>No data available</p>

		}
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
			<td valign="top" style ={subheaderStyle}>
			<MyBarChart3 data={this.state.masterData[2]["natData"]}/>
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
			<MyBarChart3 data={this.state.masterData2[2]["natData"]}/>
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