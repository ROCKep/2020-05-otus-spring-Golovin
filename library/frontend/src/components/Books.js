import React from "react"
import Book from "./Book";
import Button from "react-bootstrap/Button";
import BookModal from "./BookModal";
import axios from "axios";


class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [],
            bookModalVisible: false
        }
        this.openBookModal = this.openBookModal.bind(this);
        this.closeBookModal = this.closeBookModal.bind(this);
        this.saveBook = this.saveBook.bind(this);
    }

    openBookModal() {
        this.setState({bookModalVisible: true})
    }

    closeBookModal() {
        this.setState({bookModalVisible: false});
    }

    saveBook(bookDetails) {
        if (bookDetails) {
            axios.post("/api/book", bookDetails)
                .then(newBookDetailsResponse => this.setState({
                    books: [
                        ...this.state.books,
                        {
                            id: newBookDetailsResponse.data.id,
                            name: bookDetails.name,
                            author: bookDetails.author,
                            releaseYear: bookDetails.releaseYear
                        }
                    ]
                }))
                .catch(error => console.log(error));
        }
    }

    componentDidMount() {
        axios.get("/api/books")
            .then((response) =>
                this.setState({
                    books: response.data
                }))
            .catch(error => console.log(error));
    }

    render() {
        const {books, bookModalVisible} = this.state;
        return <>
            <h1>Все книги</h1>
            <Button onClick={this.openBookModal}>Добавить книгу</Button>
            {books.map(book =>
                <Book book={book} key={book.id}/>
            )}
            {bookModalVisible &&
                <BookModal onClose={this.closeBookModal} onSubmit={this.saveBook}/>}
        </>
    }
}

export default Books;