package ru.otus.department;

import ru.otus.atmbox.AtmBoxObserver;

import java.util.HashSet;
import java.util.Set;

public class DepartmentAtm {

    private Set<AtmBoxObserver> atmBoxObserverSet = new HashSet<>();

    public void registerAtmObserver(AtmBoxObserver atmBoxObserver) {
        atmBoxObserverSet.add(atmBoxObserver);
    }

    public void unregisterAtmObserver(AtmBoxObserver atmBoxObserver) {
        atmBoxObserverSet.remove(atmBoxObserver);
    }

    public void resetAllAtmState() {
        atmBoxObserverSet.forEach(AtmBoxObserver::resetState);
    }


    public void printAllAtmBalance() {
        atmBoxObserverSet.forEach(atmBoxObserver -> System.out.println("Atm code: " + atmBoxObserver.getAtmCode() + "; Balance: " + atmBoxObserver.getBalance()));
    }
}
