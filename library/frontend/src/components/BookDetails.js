import React from "react";
import Button from "react-bootstrap/Button";
import PropTypes from "prop-types";
import Comments from "./Comments";
// import CommentModal from "./CommentModal";
import axios from "axios";
import BookModal from "./BookModal";
import { withRouter } from 'react-router';

class BookDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            newCommentModalVisible: false,
            bookModalVisible: false,
            bookDetails: null
        };
        this.closeBookModal = this.closeBookModal.bind(this);
        this.openBookModal = this.openBookModal.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
        // this.handleNewCommentModalClose = this.handleNewCommentModalClose.bind(this);
        // this.handleNewCommentModalOpen = this.handleNewCommentModalOpen.bind(this);
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

    // handleNewCommentModalClose() {
    //     this.setState({newCommentModalVisible: false});
    // }
    //
    // handleNewCommentModalOpen() {
    //     this.setState({newCommentModalVisible: true});
    // }

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
        const {bookDetails, bookModalVisible, newCommentModalVisible} = this.state;
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

BookDetails.propTypes = {
    match: PropTypes.object.isRequired,
}

export default withRouter(BookDetails);