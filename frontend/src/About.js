import React, { Component } from 'react';
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

class About extends Component{

    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: []
        };
      }

    componentDidMount() {
        fetch("https://api.github.com/repos/nikhilarora205/TeamScarletEnvironmentNow/stats/contributors")
          .then(res => res.json())
          .then(
            (result) => {
              this.setState({
                isLoaded: true,
                items: result
                
              });
            },
            // Note: it's important to handle errors here
            // instead of a catch() block so that we don't swallow
            // exceptions from actual bugs in components.
            (error) => {
              this.setState({
                isLoaded: false,
                error
              });
            }
          )
      }

      render() {
        const { error, isLoaded, items } = this.state;
        if (error) {
          return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
          return <div>Loading...</div>;
        } else {
            //  {items.author}, was trying to just output something to the screen to see that the call was working
            //console.log(this.state)
           // console.log(this.state.items)
           // console.log(this.state.items[0].author.login)
          //  return <About items={this.state.items}/>

          return (
            <div>
            <title>React App</title>
           <body>
               <title class="header"> "About Page" </title>
         
               <h1 class="header"> About</h1>
         
               <h2 class="header"> Description: </h2>
                   <p class="innerText"> Environment Now is our vision for a web application that will provide an accessible, detailed list of 
                   relevant factors for considering the environmental conditions of an area. There are many use cases and users for such an 
                   application. Environment Now might be used by people seeking to buy a new home who would like to understand the conditions 
                   they would potentially live in. Individuals and families who are looking to purchase homes in a new area may want to access a 
                   detailed breakdown of allergens, pollutants, and disaster risks of an area to understand if and how they could be negatively 
                   affected. Moreover, health conscious, asthmatic, and allergen-sensitive people would likely be interested in such a product to 
                   assess the need for air or water filtration systems in their area. Finally, environmental activists who are concerned with the 
                   conditions in an area can easily use Environment Now to collect and distribute relevant information to their peers to call for 
                   action. There are existing applications that provide basic environmental information on specific areas, but they don’t discuss 
                   the whole picture, nor do they provide a specific breakdown of the pollutants. For example, airnow.gov provides an AQI score and 
                   a basic breakdown of a few elements of the index, but it does not give the entire composition of the AQI, nor does it provide 
                   information relating to tap water contamination levels or natural disaster data. Ewg.org provides tap water contamination data, 
                   but lacks other relevant environmental information. This makes Environment Now the first application to bring together all 
                   relevant factors of an environment in a snapshot which gives it a competitive advantage over other applications that look at 
                   specific areas.  
                     </p>
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
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   <h3 class = "names">  Nikhil Arora:</h3>
                   <img src={Nikhil} style={imgStyle} alt = 'Nikhil'/>
         
                       <p>Bio: 3rd Year ECE @ UT, Software Engineering Track </p>
                       <p>Major: ECE</p>
                       <p>Responsibilities: About Page/API Call for Git, Establishing React/Spring Code Base</p>
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   <h3 class="names">  Chris Classie:</h3>
                   <img src={Chris} style={imgStyle} alt = 'Chris'/>
         
                       <p>Bio: </p>
                       <p>Major: ECE</p>
                       <p>Responsibilities: Created Initial URL, Set Up GCP</p>
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   <h3 class="names">  Gabriel Darnell:</h3>
                   <img src={Gabe} style={imgStyle} alt = 'Gabe'/>
         
                       <p>Bio: Integrated Masters student with a focus in software engineering</p>
                       <p>Major: ECE</p>
                       <p>Responsibilities: About Page/API Call for Git, Proposal Editing/Approval</p>
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   <h3 class="names">  David Fernandez:</h3>
                   <img src={David.JPG} style={imgStyle} alt = 'David'/>
         
                       <p>Bio: I’m a senior electrical and computer engineering student, with a software engineering track at the University of Texas at Austin </p>
                       <p>Major: ECE</p>
                       <p>Responsibilities: GCP App Engine Integration, Establish React/Spring Code Base</p>
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   <h3 class="names">  Sammy Samman:</h3>
                   <img src={Sammy} style={imgStyle} alt = 'Sammy'/>
         
                       <p>Bio: Sammy is a EE student with a software engineering primary tech core. After graduation, he plans on working industry before pursuing a degree in law. </p>
                       <p>Major: ECE</p>
                       <p>Responsibilities: Phase 1 Report, UML Diagram Design, Proposal Editing/Communications for Approval</p>
                       <p># of Commits: See stats</p>
                       <p># of Issues:  See stats</p>
                       <p># of Unit Tests: See stats</p> 
                   <br></br>
                   
               <h2 class="header"> Stats: </h2>
               {items.map((messageObj) => {
                 return(
                     <div> 
                         <p>Author: {messageObj.author.login}</p>
                         <p> Total Commits: {messageObj.total}</p> 
                         <p> Total Unit Tests: 0</p>
                         <p> Total Issues: 0</p>
                         <br></br>
                     </div>
                 );
                 })}
               
                  <br></br>
               <h2 class="header"> Data: </h2>
                   <a href="https://www.epa.gov/air-trends/air-quality-cities-and-counties">Link to Air Quality Data</a>
                   <br></br>
                   <a href="https://www.ewg.org/tapwater/">Link to Tap Water Data</a>
                   <br></br>
                   <a href="https://www.adt.com/natural-disasters">Link to Natural Disaster Data</a>
                   <br></br>
               <h2 class="header"> Tools: </h2>
                 <p> We used the React framework for our front end development and Spring for our back end. We also utilized React's tutorials along with Github and our Data Sources API's to dynamically populate information.</p>
                   <br></br>
         
               <a href="https://github.com/nikhilarora205/TeamScarletEnvironmentNow">Link to GitHub Repo</a>
               <br></br>
               <p id="output"></p>
               </body>
            </div>
          );
        }
          
      }
}

export default About;