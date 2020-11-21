import React from "react";
import PropTypes from "prop-types";
import Media from "react-bootstrap/Media";

class Comment extends React.Component {
    render() {
        const {comment} = this.props;
        return <Media as="li">
            <Media.Body>
                <h5>{comment.user}</h5>
                <p>{comment.content}</p>
            </Media.Body>
        </Media>;
    }
}

Comment.propTypes = {
    comment: PropTypes.object.isRequired,
}

export default Comment;