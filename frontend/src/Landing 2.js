import React from 'react';

const Landing = () => {
    return (
    <div>
        <p>Location: </p>
        <input
            type="text"
            value= '100 Orvieto Cove, Liberty Hill, TX, 78642'
            width= '10000px'
            fontSize = '12px'
            textAlign = 'top'
         />
         <p>click this button to get statistics on this location: </p>
         <button GO/>
        <p>click this button to add location: </p>
         <button ADD LOCATION/>
    </div>
    );
}
 
export default Landing;