import React from "react";
import PropTypes from "prop-types";
import Comment from "./Comment";
import axios from "axios";
import Button from "react-bootstrap/Button";
import CommentModal from "./CommentModal";

class Comments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: [],
            commentModalVisible: false
        };
        this.openCommentModal = this.openCommentModal.bind(this);
        this.closeCommentModal = this.closeCommentModal.bind(this);
        this.saveComment = this.saveComment.bind(this);
    }

    componentDidMount() {
        const {bookId} = this.props;
        axios.get(`/api/book/${bookId}/comments`)
            .then(commentsResponse => this.setState({
                comments: commentsResponse.data
            }))
            .catch(error => console.log(error));
    }

    openCommentModal() {
        this.setState({
            commentModalVisible: true
        });
    }

    closeCommentModal() {
        this.setState({
            commentModalVisible: false
        });
    }

    saveComment(comment) {
        const {bookId} = this.props;
        axios.post(`/api/book/${bookId}/comment`, comment)
            .then(newCommentResponse => this.setState({
                comments: [
                    ...this.state.comments,
                    newCommentResponse.data
                ]
            }))
            .catch(error => console.log(error));
    }

    render() {
        const {comments, commentModalVisible} = this.state;
        return <>
            <h2>Комментарии</h2>
            <Button onClick={this.openCommentModal}>Написать комментарий</Button>
            <ul className="list-unstyled">
                {comments.map(comment =>
                    <Comment comment={comment} key={comment.id}/>)}
            </ul>
            {commentModalVisible &&
            <CommentModal onClose={this.closeCommentModal} onSubmit={this.saveComment}/>}
        </>
    }
}


Comments.propTypes = {
    bookId: PropTypes.string.isRequired,
}

export default Comments;