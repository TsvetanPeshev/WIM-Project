package models.comments;

import models.common.Validator;
import models.contracts.Comment;
import models.contracts.Member;

public class Comments implements Comment {
    private static final int COMMENT_MIN_LENGTH = 2;
    private static final int COMMENT_MAX_LENGTH = 250;
    private static final String COMMENT_LENGTH_ERROR_MESSAGE = "Comment cannot be less than %d and more than %d symbols";
    String comment;
    Member author;

    public Comments(String comment, Member author){
        setComment(comment);
        setAuthor(author);
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Member getAuthor() {
        return author;
    }

    private void setComment(String comment) {
        Validator.validateLength(comment.length(),
                COMMENT_MIN_LENGTH,
                COMMENT_MAX_LENGTH,
                COMMENT_LENGTH_ERROR_MESSAGE);
        this.comment = comment;
    }

    private void setAuthor(Member author) {
        this.author = author;
    }
}
