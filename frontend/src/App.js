import FlightComponent from './component/FlightComponent';
import PageHead from './component/PageHead';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import CargoComponent from './component/CargoComponent';

function App() {
  return (
    <div>
      <Router>
         <div className="container">
             <PageHead/>
             <div className="container">
       <Switch>
         <Route path="/flight" component= {FlightComponent}/>
         <Route path="/cargo" component= {CargoComponent}/>

          <FlightComponent/>
      </Switch>
    </div>
    </div>
    </Router>
    </div>
  );
}






export default App;
