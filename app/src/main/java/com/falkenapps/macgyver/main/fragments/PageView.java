package com.falkenapps.macgyver.main.fragments;

import com.falkenapps.macgyver.common.Professional;

import java.util.List;

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public interface PageView {
    void updateProfessionalList(List<Professional> professionalList);
    void addProfessional(Professional professional);
    void updateProfessional(Professional professional);
    void removeProfessional(String key);
    void removeAllProfessionals();
    void setProfessionalList(List<Professional> professionalList);

}
