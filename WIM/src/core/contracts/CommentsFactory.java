package core.contracts;

import models.contracts.Comment;
import models.contracts.Member;

public interface CommentsFactory {
Comment createComment(String comment, Member author);

}
