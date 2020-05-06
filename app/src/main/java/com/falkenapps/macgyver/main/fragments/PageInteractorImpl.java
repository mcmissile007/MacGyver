package com.falkenapps.macgyver.main.fragments;

import com.falkenapps.macgyver.common.Search;

import io.reactivex.Observer;

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public class PageInteractorImpl implements PageInteractor{
    private PageRepository pageRepository;

    @Override
    public void startSearch(Search search) {
        pageRepository.startSearch(search);
    }

    private PageInteractorImpl()
    {
        pageRepository = PageRepositoryImpl.getInstance();
    }

    private static class PageInteractorImplHelper{
        private static  final PageInteractorImpl INSTANCE = new PageInteractorImpl();

    }

    public static PageInteractorImpl getInstance(){
        return PageInteractorImplHelper.INSTANCE;
    }




    @Override
    public void changeFilter(Search search) {
        pageRepository.changeFilter(search);
    }

    @Override
    public void notifyMeAddProfessional(Observer observer) {
        pageRepository.notifyMeAddProfessional(observer);

    }

    @Override
    public void notifyMeRemoveProfessional(Observer observer) {
        pageRepository.notifyMeRemoveProfessional(observer);

    }

    @Override
    public void notifyMeUpdateProfessional(Observer observer) {
        pageRepository.notifyMeUpdateProfessional(observer);

    }

    @Override
    public void notifyMeChangeFilter(Observer observer) {
        pageRepository.notifyMeChangeFilter(observer);

    }



    @Override
    public void removeMeFromAddObservers(Observer observer) {
        pageRepository.removeMeFromAddObservers(observer);

    }

    @Override
    public void removeMeFromUpdateObservers(Observer observer) {
        pageRepository.removeMeFromUpdateObservers(observer);

    }

    @Override
    public void removeMeFromRemoveObservers(Observer observer) {
        pageRepository.removeMeFromRemoveObservers(observer);

    }

    @Override
    public void removeMeFromChangeFilterObservers(Observer observer) {
        pageRepository.removeMeFromChangeFilterObservers(observer);

    }

    @Override
    public void removeMeFromCurrentProfesionalListObservers(Observer observer) {
        pageRepository.removeMeFromCurrentProfesionalListObservers(observer);

    }

    @Override
    public void addMeFromCurrentProfesionalListObservers(Observer observer) {
        pageRepository.addMeFromCurrentProfesionalListObservers(observer);
    }

    @Override
    public void getCurrentProfessionalsList() {
        pageRepository.getCurrentProfessionalsList();

    }




}
