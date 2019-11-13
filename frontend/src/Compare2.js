// Compare2.js
import React, { Component } from 'react';
import './App.css';
import MyBarChart from './MyBarChart';
import MyBarChart2 from './MyBarChart2';
import MyBarChart3 from './MyBarChart3';
import MyBarChart4 from './MyBarChart4';
import data from './data.json';
import data2 from './data2.json';
import data3 from './data3.json';
import data4 from './data4.json';
import Bar from 'react-meter-bar';

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
			natural2: []
		  };

	async componentDidMount() {

			var path = window.location.href;
			console.log(window.location.pathname);
			console.log(path);
			// ***** change depending on website you are deploying to *****
			path = path.replace("http://localhost:3000/compare2/", "");
			console.log(path);
			const addresses = path.split("?");
			console.log(addresses);
			this.address = addresses[0];
			this.address2 = addresses[1];
			// URL converts spaces to %20
//			this.address = this.address.replace("%20", " ");
			
			//
			// have to separate both addresses
			// this.address = "100 Orvieto Cove, Liberty Hill, TX, 78642";
			// this.address2 = "15504 Cinca Terra Drive, Austin, TX, 78738";
			//

			console.log(this.address);
			console.log(this.address2);
		
			const AQIResponse = await fetch('/api/AQIData/'+this.address);
			// const AQIResponse = await fetch('/api/AQIData');
			const AQIBody = await AQIResponse.json();
			this.setState({ air: AQIBody, isLoading: true });
			console.log(this.state.air);
			
			// response = await fetch('/api/allergenData');
		    // body = await response.json();
			// this.setState({ allergen: body, isLoading: true });
			// console.log(this.state.allergen);

			const waterResponse = await fetch('/api/waterData/'+this.address);
			// const waterResponse = await fetch('/api/waterData');
		    const waterBody = await waterResponse.json();
			this.setState({ water: waterBody, isLoading: true });
			console.log(this.state.water["contaminants"]);

			const natResponse = await fetch('/api/naturalDisasters/'+this.address);
			// const natResponse = await fetch('/api/naturalDisasters');
		    const natBody = await natResponse.json();
			this.setState({ natural: natBody, isLoading: true });
			console.log(this.state.natural);
			
			
			// second address data
			const AQIResponse2 = await fetch('/api/AQIData/'+this.address2);
			// const AQIResponse = await fetch('/api/AQIData');
			const AQIBody2 = await AQIResponse2.json();
			this.setState({ air2: AQIBody2, isLoading: true });
			console.log(this.state.air2);
			
			// response = await fetch('/api/allergenData');
		    // body = await response.json();
			// this.setState({ allergen: body, isLoading: true });
			// console.log(this.state.allergen);

			const waterResponse2 = await fetch('/api/waterData/'+this.address2);
			// const waterResponse = await fetch('/api/waterData');
		    const waterBody2 = await waterResponse2.json();
			this.setState({ water2: waterBody2, isLoading: true });
			console.log(this.state.water2["contaminants"]);

			const natResponse2 = await fetch('/api/naturalDisasters/'+this.address2);
			// const natResponse = await fetch('/api/naturalDisasters');
		    const natBody2 = await natResponse2.json();
			this.setState({ natural2: natBody2, isLoading: false });
			console.log(this.state.natural2);
			
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
										<td valign="top">
											<br></br><br></br>
											<header>AQI Index: </header>                        
											<Bar
											labels={[0,50,100,150,200,250,300,350,400,450,]}
											labelColor="steelblue"
											progress={80}
											barColor="#fff34b"
											seperatorColor="hotpink"
											style={topMarginStyle}
											/>
											<MyBarChart 
											data={data}
											textAlign = 'top' 
											/>
											<br></br><br></br><br></br>
											<p>PM2.5: {this.state.air["PM2.5"]}</p>
											<p>Ozone: {this.state.air["Ozone"]}</p>
											<p>PM10: {this.state.air["PM10"]}</p>
											<br></br><br></br><br></br>
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
										<br></br><br></br>
										<header>AQI Index: </header>                        
										<Bar
										labels={[0,50,100,150,200,250,300,350,400,450,]}
										labelColor="steelblue"
										progress={80}
										barColor="#fff34b"
										seperatorColor="hotpink"
										style={topMarginStyle}
										/>
										<MyBarChart 
										data={data}
										textAlign = 'top' 
										/>
										<br></br><br></br><br></br>
										<p>PM2.5: {this.state.air2["PM2.5"]}</p>
										<p>Ozone: {this.state.air2["Ozone"]}</p>
										<p>PM10: {this.state.air2["PM10"]}</p>
										<br></br><br></br><br></br>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				<div class="ui inverted vertical divider">AIR QUALITY</div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
							<tbody>
								<tr>
										<td valign="top">
											<br></br><br></br>
											<header>Allergen Index: </header>
											<Bar
											labels={[0,10,20,30,40,50,60,70,80,90,100]}
											labelColor="steelblue"
											progress={20}
											barColor='green'
											seperatorColor="hotpink"
											style={topMarginStyle}
											/>
											<MyBarChart4 data={data4} style={topMarginStyle}/>

											<br></br><br></br><br></br>
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
										<br></br><br></br>
										<header>Allergen Index: </header>
										<Bar
										labels={[0,10,20,30,40,50,60,70,80,90,100]}
										labelColor="steelblue"
										progress={20}
										barColor='green'
										seperatorColor="hotpink"
										style={topMarginStyle}
										/>
										<MyBarChart4 data={data4} style={topMarginStyle}/>

										<br></br><br></br><br></br>
									</td>
								</tr>
							</tbody>	
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">ALLERGENS</div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
								<tbody>
									<tr>
										<td valign="top"> 
											<br></br><br></br>
											<br></br><br></br>
											<header>Water Danger Levels: </header>
											<Bar
											labels={[0,10,20,30,40,50,60,70,80,90,100]}
											labelColor="steelblue"
											progress={70}
											barColor="#fff34b"
											seperatorColor="hotpink"
											style={topMarginStyle}
											/>
											<MyBarChart2 
											data={data2}
											align = 'top' />

											<br></br><br></br><br></br>
											<p>{this.state.water["contaminants"][0]["contaminant"]}: {this.state.water["contaminants"][0]["level"]}</p>
											<p>{this.state.water["contaminants"][1]["contaminant"]}: {this.state.water["contaminants"][1]["level"]}</p>
											<p>{this.state.water["contaminants"][2]["contaminant"]}: {this.state.water["contaminants"][2]["level"]}</p>
											<p>{this.state.water["contaminants"][3]["contaminant"]}: {this.state.water["contaminants"][3]["level"]}</p>
											
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
										<br></br><br></br>
										<br></br><br></br>
										<header>Water Danger Levels: </header>
										<Bar
										labels={[0,10,20,30,40,50,60,70,80,90,100]}
										labelColor="steelblue"
										progress={70}
										barColor="#fff34b"
										seperatorColor="hotpink"
										style={topMarginStyle}
										/>
										<MyBarChart2 
										data={data2}
										align = 'top' />

										<br></br><br></br><br></br>
										<p>{this.state.water2["contaminants"][0]["contaminant"]}: {this.state.water2["contaminants"][0]["level"]}</p>
										<p>{this.state.water2["contaminants"][1]["contaminant"]}: {this.state.water2["contaminants"][1]["level"]}</p>
										<p>{this.state.water2["contaminants"][2]["contaminant"]}: {this.state.water2["contaminants"][2]["level"]}</p>
										<p>{this.state.water2["contaminants"][3]["contaminant"]}: {this.state.water2["contaminants"][3]["level"]}</p>
										
									</td>
								</tr>
							</tbody>
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">WATER QUALITY</div>
				</div>

				<div class="ui inverted segment">
					<div class="ui very relaxed two column grid">
						<div class="column">
							<table align="center">
								<tbody>
											<tr>
												<td valign="top">
													<br></br><br></br>
													<br></br><br></br>
													<header>Land Activity: </header>
													<Bar
													labels={[0,10,20,30,40,50,60,70,80,90,100]}
													labelColor="steelblue"
													progress={10}
													barColor='green'
													seperatorColor="hotpink"
													style={topMarginStyle}
													/>
													<MyBarChart3 data={data3}/>

													<br></br><br></br><br></br>
													<p>Storm: {this.state.natural["Storm"]}</p>
													<p>Earthquake: {this.state.natural["Earthquake"]}</p>
													<p>Wildfire: {this.state.natural["Wildfire"]}</p>
													<p>Flood: {this.state.natural["Flood"]}</p>
													<p>Drought: {this.state.natural["Drought"]}</p>
													<p>Extreme Temperature: {this.state.natural["Extreme Temperature"]}</p>
													<p>Land Slide: {this.state.natural["Land Slide"]}</p>
													<p>Volcanic Activity: {this.state.natural["Volcanic Activity"]}</p>
													<p>Epidemic: {this.state.natural["Epidemic"]}</p>

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
												<br></br><br></br>
												<br></br><br></br>
												<header>Land Activity: </header>
												<Bar
												labels={[0,10,20,30,40,50,60,70,80,90,100]}
												labelColor="steelblue"
												progress={10}
												barColor='green'
												seperatorColor="hotpink"
												style={topMarginStyle}
												/>
												<MyBarChart3 data={data3}/>

												<br></br><br></br><br></br>
												<p>Storm: {this.state.natural2["Storm"]}</p>
												<p>Earthquake: {this.state.natural2["Earthquake"]}</p>
												<p>Wildfire: {this.state.natural2["Wildfire"]}</p>
												<p>Flood: {this.state.natural2["Flood"]}</p>
												<p>Drought: {this.state.natural2["Drought"]}</p>
												<p>Extreme Temperature: {this.state.natural2["Extreme Temperature"]}</p>
												<p>Land Slide: {this.state.natural2["Land Slide"]}</p>
												<p>Volcanic Activity: {this.state.natural2["Volcanic Activity"]}</p>
												<p>Epidemic: {this.state.natural2["Epidemic"]}</p>

										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="ui inverted vertical divider">NATURAL DISASTERS</div>
				</div>

		      
				<br></br><br></br><br></br><br></br>
				
			</div>
			);
		}
	
}
		
export default Compare2;