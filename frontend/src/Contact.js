import React from 'react';

import 'bootstrap/dist/css/bootstrap.min.css';
import { Component } from 'react';

class Contact extends Component{

   render(){

      return (

         <div>
          <h1>Contact US</h1>
          <h5>
                    <table textAlignment = 'center'>
                        <tbody>
                        <tr>
                        <td>Name</td>
                        <td>Email</td>
                        <td>Github</td>
                        </tr>
                        <tr>
                        <td>Khalid Ahmad</td>
                        <td>Khalid.ashraf.ahmad@gmail.com</td>
                        <td>https://github.com/KhalidAhmadIMRTL</td>
                        </tr>
                        <tr>
                        <td>Nikhil Arora</td>
                        <td>nikhilarora@utexas.edu</td>
                        <td>https://github.com/nikhilarora205</td>
                        </tr>
                        <tr>
                        <td>Chris Classie</td>
                        <td>stayclassie8@gmail.com </td>
                        <td>https://github.com/StayClassie</td>
                        </tr>
                        <tr>
                        <td>Gabriel Darnell</td>
                        <td>gdarnell@utexas.edu</td>
                        <td>https://github.com/gkd248</td>
                        </tr>
                        <tr>
                        <td>David Fernandez</td>
                        <td>davidaf@utexas.edu</td>
                        <td>https://github.com/davidf2020</td>
                        </tr>
                        <tr>
                        <td>Sammy Samman</td>
                        <td>s98samman@gmail.com</td>
                        <td>https://github.com/ss78832</td>
                        </tr>
                        </tbody>
                        </table>
         </h5>
          <p>Thanks!</p>
       </div>

      )

      }
   }
 
export default Contact;