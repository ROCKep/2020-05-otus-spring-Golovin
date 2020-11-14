import React from 'react';
import './style/App.css';
import Books from "./components/Books";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import "bootstrap/dist/css/bootstrap.min.css";
import BookDetails from "./components/BookDetails";
import {BrowserRouter, Link, Route} from "react-router-dom";

class App extends React.Component {

    render() {
        return (
            <div className="App">
                <BrowserRouter>
                    <Navbar bg="dark" variant="dark">
                        <Navbar.Brand as={Link} to="/">
                            Библиотека
                        </Navbar.Brand>
                    </Navbar>
                    <Container>
                        <Route exact path="/" component={Books}/>
                        <Route path="/book/:id" component={BookDetails}/>
                    </Container>
                </BrowserRouter>
            </div>
        );
    }
}

export default App;
