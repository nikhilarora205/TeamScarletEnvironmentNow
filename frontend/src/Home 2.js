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

const home = () => {
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
                        <br></br><br></br><br></br><br></br>
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
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	);
}
export default home;