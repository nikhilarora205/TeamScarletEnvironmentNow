import React from 'react';
import  Khalid from './Khalid.jpeg';
import Nikhil from './Nikhil.png';
import Sammy from './Sammy.jpg';
import David from './David.jpeg';
import Chris from './Chris.jpeg';
import Gabe from './Gabe.jpeg';

const imgStyle = {
    height: '155px',
    width: '150px'
} 


const About = () => {
    return (
<div>
   <title>React App</title>
  <body>
      <title class="header"> "About Page" </title>

      <h1 class="header"> About</h1>

      <h2 class="header"> Description: </h2>
          <h3 class="innerText"> Environment Now is our vision for a web application that will provide an accessible, detailed list of relevant factors for considering the environmental conditions of an area. There are many use cases and users for such an application. Environment Now might be used by people seeking to buy a new home who would like to understand the conditions they would potentially live in. Individuals and families who are looking to purchase homes in a new area may want to access a detailed breakdown of the pollutants and disaster risks of an area to understand if and how they could be negatively affected. Moreover, health conscious, asthmatic, and allergen-sensitive people would likely be interested in such a product to assess the need for air or water filtration systems in their area. Finally, environmental activists who are concerned with the conditions in an area can easily use Environment Now to collect and distribute relevant information to their peers to call for action. The target audience of Environment Now is anybody who wants relevant environmental information about an area’s air quality, tap water quality, and/or natural disaster risk. </h3>
          <br></br>
      <h2 class="header"> Group Name: </h2>
          <h3 class="innerText"> Scarlet</h3>
          <br></br>
      <h2 class="header"> Group Members </h2>
          <br></br>
          <h3 class="names">  Khalid Ahmad:</h3>
          <img src={Khalid} style={imgStyle} alt= 'Khalid'/>

              <p>Bio: Senior at the University of Texas with a Software Design Tech core</p>
              <p>Major: ECE</p>
              <p>Responsibilities: Front End Development.</p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          <h3 class = "names">  Nikhil Arora:</h3>
          <img src={Nikhil} style={imgStyle} alt = 'Nikhil'/>

              <p>Bio: 3rd Year ECE @ UT, Software Engineering Track </p>
              <p>Major: ECE</p>
              <p>Responsibilities: About Page/API Call for Git, Establishing React/Spring Code Base</p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          <h3 class="names">  Chris Classie:</h3>
          <img src={Chris} style={imgStyle} alt = 'Chris'/>

              <p>Bio: Integrated Masters student with a focus in software engineering</p>
              <p>Major: ECE</p>
              <p>Responsibilities: Created Initial URL, Set Up GCP</p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          <h3 class="names">  Gabriel Darnell:</h3>
          <img src={Gabe} style={imgStyle} alt = 'Gabe'/>

              <p>Bio: </p>
              <p>Major: ECE</p>
              <p>Responsibilities: About Page/API Call for Git</p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          <h3 class="names">  David Fernandez:</h3>
          <img src={David.JPG} style={imgStyle} alt = 'David'/>

              <p>Bio: I’m a senior electrical and computer engineering student, with a software engineering track at the University of Texas at Austin </p>
              <p>Major: ECE</p>
              <p>Responsibilities: GCP App Engine Integration, Establish React/Spring Code Base</p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          <h3 class="names">  Sammy Samman:</h3>
          <img src={Sammy} style={imgStyle} alt = 'Sammy'/>

              <p>Bio: Sammy is a EE student with a software engineering primary tech core. After graduation, he plans on working industry before pursuing a degree in law. </p>
              <p>Major: ECE</p>
              <p>Responsibilities: Phase 1 Report, UML Diagram Design, Proposal Editing/Communications for Approval, About Page Population </p>
              <p># of Commits: </p>
              <p># of Issues:  </p>
              <p># of Unit Tests: </p> 
          <br></br>
          
      <h2 class="header"> Stats: </h2>
          <br></br>
      <h2 class="header"> Data: </h2>
          <a href="https://www.epa.gov/air-trends/air-quality-cities-and-counties">Link to Air Quality Data</a>
          <br></br>
          <a href="https://www.ewg.org/tapwater/">Link to Tap Water Data</a>
          <br></br>
          <a href="https://www.adt.com/natural-disasters">Link to Natural Disaster Data</a>
          <br></br>
      <h2 class="header"> Tools: For the front end, we have used html, css, javascript, and React to design our webpages. Our back end uses Spring as a framework for Java programming. We also used balsamiq initially to design the basic layout of our web application. Our UML diagrams were designed with draw.io.</h2>
          <br></br>

      <a href="https://github.com/ss78832/EnvironmentNow">Link to GitHub Repo</a>
      <br></br>
      <p id="output"></p>
      </body>
   </div>
    );
}
 
export default About;
