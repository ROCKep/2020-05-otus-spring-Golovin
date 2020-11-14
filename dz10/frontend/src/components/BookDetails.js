import React from "react";
import Button from "react-bootstrap/Button";
import Comments from "./Comments";
import axios from "axios";
import BookModal from "./BookModal";
import { withRouter } from 'react-router';

class BookDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            bookModalVisible: false,
            bookDetails: null
        };
        this.closeBookModal = this.closeBookModal.bind(this);
        this.openBookModal = this.openBookModal.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
    }

    componentDidMount() {
        const {match: {params: {id}}} = this.props;
        axios.get(`/api/book/${id}`)
            .then(response =>
                this.setState({
                    bookDetails: response.data
                }))
            .catch(error => console.log(error));
    }

    openBookModal() {
        this.setState({bookModalVisible: true});
    }

    closeBookModal() {
        this.setState({bookModalVisible: false});
    }

    saveBook(editedBookDetails) {
        axios.put(`/api/book/${editedBookDetails.id}`, editedBookDetails)
            .then(() => this.setState({bookDetails: editedBookDetails}))
            .catch(error => console.log(error));
    }

    deleteBook() {
        const {history, match: {params: {id}}} = this.props;
        axios.delete(`/api/book/${id}`)
            .then(() => {
                history.push("/");
            })
            .catch(error => console.log(error));
    }

    render() {
        const {bookDetails, bookModalVisible} = this.state;
        return bookDetails && <>
            <h1>{bookDetails.name}</h1>
            <h2>Информация</h2>
            <p>
                <span>Автор: </span>
                <span>{bookDetails.author}</span>
            </p>
            {bookDetails.releaseYear &&
            <p>
                <span>Год выпуска: </span>
                <span>{bookDetails.releaseYear}</span>
            </p>
            }
            <p>
                <span>Жанры: </span>
                <span>{bookDetails.genres.join(", ")}</span>
            </p>
            <Button onClick={this.openBookModal}>Редактировать книгу</Button>
            <Button onClick={this.deleteBook}>Удалить книгу</Button>
            <Comments bookId={bookDetails.id}/>
            {bookModalVisible &&
                <BookModal bookDetails={bookDetails} onClose={this.closeBookModal} onSubmit={this.saveBook}/>
            }

        </>
    }
}

export default withRouter(BookDetails);