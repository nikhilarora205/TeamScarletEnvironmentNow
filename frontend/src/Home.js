// Home.js
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


			console.log(this.address);
		
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
			this.setState({ natural: natBody, isLoading: false });
			console.log(this.state.natural);
			
//			var regex = /%20/gi;
//			this.address = this.address.replace(regex, " ");
	}
	
	render() {
		
		const {natural, isLoading} = this.state;

	    if (isLoading) {
	      return <p>Loading...</p>;
	    }
		
		return(
			
			<div>
			<head> <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /> </head>
			<header style={headerStyle}>
	          ENVIRONMENT NOW
	      </header>
	      <br></br><br></br>
	      <h3>Location: {this.address.replace(/%20/gi," ")}</h3>
	      
	        	<table align="center">
				<tbody>
					<tr>
						<td valign="top"> 
	                        AIR
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
						<td valign="top">
							ALLERGENS
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
						</td>
	                        
							
						
						<td valign="top"> 
	                        WATER
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
						<td valign="top">
	                        LAND
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
		);
	}
}
export default Home;