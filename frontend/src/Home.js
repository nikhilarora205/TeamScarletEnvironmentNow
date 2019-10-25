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
			air: [],
			allergen: [],
			water: [],
			natural: []
		  };

	async componentDidMount() {
		
			const AQIResponse = await fetch('/api/AQIData');
			const AQIBody = await AQIResponse.json();
			this.setState({ air: AQIBody, isLoading: true });
			console.log(this.state.air);
			
			// response = await fetch('/api/allergenData');
		    // body = await response.json();
			// this.setState({ allergen: body, isLoading: true });
			// console.log(this.state.allergen);

			const waterResponse = await fetch('/api/waterData');
		    const waterBody = await waterResponse.json();
			this.setState({ water: waterBody, isLoading: true });
			console.log(this.state.water["contaminants"]);

			const natResponse = await fetch('/api/naturalDisasters');
		    const natBody = await natResponse.json();
			this.setState({ natural: natBody, isLoading: false });
			console.log(this.state.natural);
	}
	
	render() {
		
		const {natural, isLoading} = this.state;

	    if (isLoading) {
	      return <p>Loading...</p>;
	    }
		
		return(
			
			<div>
			<header style={headerStyle}>
	          ENVIRONMENT NOW
	      </header>
	      <br></br><br></br>
	      <p>Location: </p>
	        <input
	            type="text"
	            value= '100 Orvieto Cove, Liberty Hill, TX, 78642'
	            width= '10000px'
	            fontSize = '12px'
	            textAlign = 'top'
	         />
	        	<table>
				<tbody>
					<tr>
						<td> 
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
	                        <br></br>
							<p>PM2.5: {this.state.air["PM2.5"]}</p>
							<p>Ozone: {this.state.air["Ozone"]}</p>
							<p>PM10: {this.state.air["PM10"]}</p>
							<br></br><br></br><br></br>
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
						<td> 
	                        WATER
	                        <br></br>
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

							<br></br>
							<p>{this.state.water["contaminants"][0]["contaminant"]}: {this.state.water["contaminants"][0]["level"]}</p>
							<p>{this.state.water["contaminants"][1]["contaminant"]}: {this.state.water["contaminants"][1]["level"]}</p>
							<p>{this.state.water["contaminants"][2]["contaminant"]}: {this.state.water["contaminants"][2]["level"]}</p>
							<p>{this.state.water["contaminants"][3]["contaminant"]}: {this.state.water["contaminants"][3]["level"]}</p>
							
						</td>
						<td>
	                        LAND
	                        <br></br>
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

							<br></br>
							<p>Total Disasters: {this.state.natural["Total Disasters"]}</p>
							<p>Flood: {this.state.natural["Flood"]}</p>
							<p>Severe Storm: {this.state.natural["Severe Storm"]}</p>
							<p>Fire: {this.state.natural["Fire"]}</p>
							<p>Hurricane: {this.state.natural["Hurricane"]}</p>
						</td>
					</tr>
				</tbody>
			</table>
			
			
		</div>
		);
	}
}
export default Home;