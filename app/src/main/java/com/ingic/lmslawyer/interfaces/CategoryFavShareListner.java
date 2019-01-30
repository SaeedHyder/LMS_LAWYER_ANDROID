package com.ingic.lmslawyer.interfaces;

import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;

public interface CategoryFavShareListner {

    void share(NewsfeedEnt item, int position);
    void favorite(NewsfeedEnt item, int position);
}
