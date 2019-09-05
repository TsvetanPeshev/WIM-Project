package core.factories;

import core.contracts.CommentsFactory;
import models.comments.Comments;
import models.contracts.Comment;
import models.contracts.Member;

public class CommentsFactoryImpl implements CommentsFactory {

    @Override
    public Comment createComment(String comment, Member author) {
        return new Comments(comment, author);
    }
}
