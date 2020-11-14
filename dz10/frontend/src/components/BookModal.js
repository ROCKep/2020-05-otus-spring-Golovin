import React from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Select from "react-select";
import axios from "axios";
import PropTypes from "prop-types";

class BookModal extends React.Component {

    initialFormState() {
        return {
            name: "",
            releaseYear: "",
            author: null,
            genres: []
        }
    };

    constructor(props) {
        super(props);

        this.state = {
            bookDetails: props.bookDetails || this.initialFormState(),
            allAuthors: [],
            allGenres: [],
            edit: Boolean(props.bookDetails)
        }

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        Promise.all([
            axios.get("/api/authors"),
            axios.get("/api/genres")
        ])
            .then(([authorsResponse, genresResponse]) =>
                this.setState({
                    allAuthors: authorsResponse.data,
                    allGenres: genresResponse.data
                })
            )
            .catch((err) => console.log(err));
    }

    handleSubmit() {
        const {bookDetails} = this.state;
        this.props.onSubmit(bookDetails);
        this.props.onClose();
    }

    render() {
        const {bookDetails, allAuthors, allGenres, edit} = this.state;
        const {onClose} = this.props;

        return allAuthors.length > 0 && allGenres.length > 0 &&
        <Modal show onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>{edit ? "Редактирование" : "Добавление"} книги</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>Название</Form.Label>
                        <Form.Control value={bookDetails.name}
                                      onChange={(e) =>
                                          this.setState({bookDetails: {...bookDetails, name: e.target.value}})}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Год выпуска</Form.Label>
                        <Form.Control value={bookDetails.releaseYear}
                                      onChange={(e) =>
                                          this.setState({bookDetails: {...bookDetails, releaseYear: e.target.value}})}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Автор</Form.Label>
                        <Select options={allAuthors} getOptionValue={o => o.id} getOptionLabel={o => o.name}
                                value={allAuthors.find(a => a.name === bookDetails.author)}
                                onChange={a => this.setState({bookDetails: {...bookDetails, author: a.name}})}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Жанры</Form.Label>
                        <Select isMulti options={allGenres} getOptionValue={o => o.id} getOptionLabel={o => o.name}
                                value={allGenres.filter(g => bookDetails.genres.includes(g.name))}
                                onChange={gs => this.setState({bookDetails: {...bookDetails, genres: gs ? gs.map(g => g.name) : []}})}/>
                    </Form.Group>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="primary" onClick={this.handleSubmit}>Сохранить</Button>
            </Modal.Footer>
        </Modal>
    }
}

BookModal.propTypes = {
    onClose: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired,
    bookDetails: PropTypes.object
}

export default BookModal;