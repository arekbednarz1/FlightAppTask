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
            <a class="active" href="#home">MAIN</a>
            <a href="#news">News</a>
            <a href="#contact">Contact</a>
            <a href="#about">About</a>
          </div>
        )
    }
}export default PageHead;