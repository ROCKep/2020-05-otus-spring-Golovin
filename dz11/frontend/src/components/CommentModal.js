import React from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import PropTypes from "prop-types";

class CommentModal extends React.Component {
    state = {
        comment: {
            user: "",
            content: ""
        }
    }

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    handleSubmit() {
        this.props.onSubmit(this.state.comment);
        this.props.onClose();
    }

    render() {
        const {onClose} = this.props;
        const {comment} = this.state;
        return <Modal show onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Новый комментарий</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>Имя пользователя</Form.Label>
                        <Form.Control value={comment.user}
                                      onChange={(e) => this.setState({comment: {...comment, user: e.target.value}})}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Комментарий</Form.Label>
                        <Form.Control as="textarea" rows={3} value={comment.content}
                                      onChange={(e) => this.setState({comment: {...comment, content: e.target.value}})}/>
                    </Form.Group>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="primary" onClick={this.handleSubmit}>Сохранить</Button>
            </Modal.Footer>
        </Modal>
    }
}

CommentModal.propTypes = {
    onClose: PropTypes.func.isRequired,
    onSubmit: PropTypes.func.isRequired
}

export default CommentModal;