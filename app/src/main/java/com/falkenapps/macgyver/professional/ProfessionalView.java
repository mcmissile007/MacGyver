package com.falkenapps.macgyver.professional;

import com.falkenapps.macgyver.common.Comment;

import java.util.List;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

public interface ProfessionalView {

    void addComment(Comment comment);
    void setCommentList(List<Comment> commentList);
    void showDialog();
    void hideDialog();
    void showMessageUserIsNotSigned();
    void showAddToFavoritesSucceed();
    void showAddToFavoritesWithError();
    void showDeleteFromFavoritesSucceed();
    void showDeleteFromFavoritesWithError();

    void showDeleteFromFavorites();
}
