import React from 'react';
import '../App.css';


class PageHead extends React.Component{


    constructor(props){
        super(props)

        this.state= {

        }
    }


    render(){
        return (
            <div class="topnav">
            <a href="/flight">FLIGHT LIST</a>
            <a href="/cargo">CARGO LIST</a>
            <a href="/addFlight">NEW FLIGHT</a>
        
          </div>
        )
    }
}export default PageHead;