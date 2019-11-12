import React, { Component } from 'react';
import  Khalid from './Khalid.jpeg';
import Nikhil from './Nikhil.png';
import Sammy from './Sammy.jpg';
import David from './David.jpeg';
import Chris from './Chris.jpeg';
import Gabe from './Gabe.jpeg';
import './About.css';

const imgStyle = {
    height: '300px',
    width: '290px'
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
            <div className="about">
              <head><link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /></head>
              <title>React App</title>
              <body>
                <h1 class="text"> About</h1>
         
                <div class="ui message">
                <div class="header">Description</div>
                  <p class="text">
                  Environment Now is our vision for a web application that will provide an accessible, detailed list of 
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
                </div>
                  
                <h2 class="teamColor"> Team Scarlet</h2>
               
                <br></br>
                <table align="center"> 
                  <tr>
                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={Khalid} style={imgStyle} alt="Khalid"/></div>
                        <div class="content">
                          <div class="header">Khalid Ahmad</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                            <div class="description">Bio: Senior at the University of Texas with a Software Design Tech core<br />
                            Responsibilities: Front End Development<br />
                            Git Username: KhalidAhmadIMRTL<br />
                            # of Commits: {items[3].total}<br />
                            # of Issues:  0<br />
                            # of Unit Tests: 0
                            </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>

                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={Nikhil} style={imgStyle} alt="Nikhil"/></div>
                        <div class="content">
                          <div class="header">Nikhil Arora</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                          <div class="description">Bio: 3rd Year ECE @ UT, Software Engineering Track<br />
                          Responsibilities: About Page/API Call for Git, Establishing React/Spring Code Base<br />
                          Git Username: nikhilarora205<br />
                          # of Commits: {items[1].total}<br />
                          # of Issues:  0<br />
                          # of Unit Tests: 0
                          </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>

                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={Gabe} style={imgStyle} alt="Gabriel"/></div>
                        <div class="content">
                          <div class="header">Gabriel Darnell</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                          <div class="description">Bio: Integrated Masters student with a focus in software engineering<br />
                          Responsibilities: Full Stack Development, Front to Back and Back to Front end Integration<br />
                          Git Username: gkd248<br />
                          # of Commits: {items[2].total}<br />
                          # of Issues:  0<br />
                          # of Unit Tests: 0
                          </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>
                  </tr>

                  <tr>

                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={Chris} style={imgStyle} alt="Chris"/></div>
                        <div class="content">
                          <div class="header">Chris Classie</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                          <div class="description">Bio: 3rd Year ECE @ UT, Software Engineering Track<br />
                          Responsibilities: About Page/API Call for Git, Establishing React/Spring Code Base<br />
                          Git Username: StayClassie<br />
                          # of Commits: 0<br />
                          # of Issues:  0<br />
                          # of Unit Tests: See stats
                          </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>

                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={David} style={imgStyle} alt="David"/></div>
                        <div class="content">
                          <div class="header">David Fernandez</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                          <div class="description">Bio: I’m a senior electrical and computer engineering student, with a software engineering track at the University of Texas at Austin<br />
                          Responsibilities: GCP App Engine Integration, Establish React/Spring Code Base<br />
                          Git Username: davidf2020<br />
                          # of Commits: 0<br />
                          # of Issues:  0<br />
                          # of Unit Tests: See stats
                          </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>

                    <td valign="top">
                      <div class="ui card">
                        <div class="image"><img src={Sammy} style={imgStyle} alt="Sammy" /></div>
                        <div class="content">
                          <div class="header">Sammy Samman</div>
                          <div class="meta"><span class="date">ECE Major</span></div>
                          <div class="description">Bio: Sammy is a EE student with a software engineering primary tech core. After graduation, he plans on working industry before pursuing a degree in law. <br />
                          Responsibilities: Phase 1 Report, UML Diagram Design, Proposal Editing/Communications for Approval<br />
                          Git Username: ss78832<br />
                          # of Commits: {items[0].total}<br />
                          # of Issues:  0<br />
                          # of Unit Tests: 0
                          </div>
                        </div>
                        <div class="extra content">
                          <a>
                            <i aria-hidden="true" class="user icon"></i>
                            22 Friends
                          </a>
                        </div>
                      </div>
                    </td>
                  </tr>
                </table>
                  
                <br></br>
                <div class="ui message">
                  <div class="header">Github</div>
                  <br></br>
                  <a class="text" href="https://github.com/nikhilarora205/TeamScarletEnvironmentNow">Link to GitHub Repo</a>
                </div>
                
                  
                <div class="ui message">
                  <div class="header">Data</div>
                    <br></br>
                    <a class="text" href="https://www.epa.gov/air-trends/air-quality-cities-and-counties">Link to Air Quality Data</a>
                    <br></br>
                    <a class="text" href="https://www.ewg.org/tapwater/">Link to Tap Water Data</a>
                    <br></br>
                    <a class="text" href="https://www.adt.com/natural-disasters">Link to Natural Disaster Data</a>
                    <br></br>
                </div>
  
                <div class="ui message">
                  <div class="header">Tools</div>
                  <br></br>
                  <p class="text"> We used the React framework for our front end development and Spring for our back end. We also utilized React's tutorials along with Github and our Data Sources API's to dynamically populate information.</p>
                </div>         
               
                </body>
            </div>
          );
        }
      }
}

export default About;