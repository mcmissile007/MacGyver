package com.falkenapps.macgyver.favorites;

import com.falkenapps.macgyver.common.Professional;

import java.util.List;

/**
 * Created by FalkenApps:falken on 7/6/17.
 */

public interface FavoritesView {
    void addProfessional(Professional professional);
    void deleteProfessional(Professional professional);
    void setProfessionalList(List<Professional> professionalList);
    void showDialog();
    void hideDialog();
    int getProfessionalListSize();

}
