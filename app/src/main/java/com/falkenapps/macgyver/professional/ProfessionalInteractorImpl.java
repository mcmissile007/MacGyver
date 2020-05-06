package com.falkenapps.macgyver.professional;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

class ProfessionalInteractorImpl implements ProfessionalInteractor {
    private ProfessionalRepository professionalRepository;

    @Override
    public void addProfessionalToFavorites(String userKey, String professionalKey, SingleObserver singleObserver) {
        this.professionalRepository.addProfessionalToFavorites(userKey,professionalKey,singleObserver);
    }

    @Override
    public void deleteProfessionalFromFavorites(String userKey, String professionalKey, SingleObserver singleObserver) {
        this.professionalRepository.deleteProfessionalFromFavorites(userKey,professionalKey,singleObserver);
    }

    public ProfessionalInteractorImpl(){
        this.professionalRepository = new ProfessionalRepositoryImpl();
    }
    @Override
    public void getComments(String professionalKey, Observer observer) {
        professionalRepository.getComments(professionalKey,observer);

    }

    @Override
    public void getCommentList(String professionalKey, Observer observer) {
        professionalRepository.getComments(professionalKey,observer);

    }

    @Override
    public void stop() {
        professionalRepository.stop();
    }

    @Override
    public void start() {
        professionalRepository.start();

    }
}
