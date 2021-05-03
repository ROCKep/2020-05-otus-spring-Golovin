import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PropTypes from "prop-types";
import {Link} from "react-router-dom";
import "../style/Book.css";

class Book extends React.Component {

    render() {
        const {book} = this.props;
        return <Row>
            <Col md={10}>
                <p>
                    <span><Link to={`/book/${book.id}`}>{book.name}</Link> </span>
                    <span>{book.author + (book.releaseYear ? ` (${book.releaseYear})` : "")}</span>
                </p>
            </Col>
        </Row>;
    }
}

Book.propTypes = {
    book: PropTypes.object.isRequired,
};

export default Book;